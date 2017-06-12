var PathPlan = function () {
    
    /**
    * 将对应信息填充到对应的select
    * @ param renderId [string] 标签id
    * @ param data [array] 列表数据
    */
    var updateList = function(renderId,data,selectTxt,callback){
        var sortData = data.sort(function(a, b) {
            return a.name - b.name;
        });
        var len = sortData.length;
        var options = '';
        for ( var i = 0; i < len; i++) {
            if(sortData[i].id == selectTxt){
                options += '<option class="addoption" selected value="'
                    + sortData[i].id + '">' + HtmlDecode3(sortData[i].name)
                    + '</option>';
            }else{
                options += '<option class="addoption" value="'
                    + sortData[i].id + '">' + HtmlDecode3(sortData[i].name)
                    + '</option>';
            }
        }
        removeOption(renderId);
        $('#' + renderId).append(options);
        if (callback) {
            callback();
        }
        return;
    };
    
    /**
    * 将对应信息填充到对应的select
    * @ param renderId [string] 标签id
    * @ param data [array] 列表数据
    */
    var updateFloorList = function(renderId,data,selectTxt,callback){
        var sortData = data.sort(function(a,b){return a.floor - b.floor;});
        var len = sortData.length;
        var options = '';
        for(var i=0;i<len;i++){
            if(sortData[i].floorNo == selectTxt){
                options += '<option class="addoption" selected value="'
                    + sortData[i].floorNo + '">' + sortData[i].floor
                    + '</option>';
            }else{
                options += '<option class="addoption" value="'
                    + sortData[i].floorNo + '">' + sortData[i].floor
                    + '</option>';
            }
        }
        removeOption(renderId);
        $('#' + renderId).append(options);
        if(callback){
            callback();
        }
        return;
    };
    
    var initMap = function (floorNo) {
        $("#mainContent").empty();
        $.post("/sva/heatmap/api/getMapInfoByPosition",{floorNo:floorNo},function(data){
            if(!data.error){
                if(data.bg){
                    // 变量赋值
                    var bgImg = data.bg;
                    var bgImgWidth = data.bgWidth;
                    var bgImgHeight = data.bgHeight;
                    var bgScale = data.scale;
                    // 获取xml文件数据
                    var placeId = $("#marketSel").val(),
                		floorNo = $("#floorSel").val(),
                		fileName = placeId + "_" + floorNo + "_pathplan.xml";
                    $.get("/sva/pathplan/api/getSimpleXmlData?fileName="+fileName,function(data){
                    	var option = {
                        		mapUrl : "url(../upload/" + bgImg + ")",
                    			mapWidth : bgImgWidth,
                    			mapHeight : bgImgHeight,
                    			mapScale : bgScale,
                    			containerId : "mainContent",
                    			lineColor : "#848484"
                        };
                    	if(!data.error){
                    		option["data"] = {"point":data.data.point,"data":data.data.data};
                    		$("#width3D").val(data.data.width3D);
                    		$("#height3D").val(data.data.height3D);
                    		$("#upperLeftX3D").val(data.data.upperLeftX3D);
                    		$("#upperLeftY3D").val(data.data.upperLeftY3D);
                    	}
                        
                        map = mpe.init(option);
                    });
                }
            }
        });
    };
    
    var removeOption = function(renderId){
        $('#'+renderId+' .addoption').remove().trigger("liszt:updated");
    };
    
    var changeFloor = function(placeId, floorNo,callback){
        $.post("/sva/heatmap/api/getFloorsByMarket", {
            placeId : placeId
        }, function(data) {
            if (!data.error) {
                var floors = data.data;
                updateFloorList("floorSel", floors, floorNo,function() {
                    $('#floorSel').trigger("liszt:updated");
                    if(callback){
                    	callback();
                    }
                });
            }
        });
    };
    
    var checkData = function(data){
    	console.log(data);
    	// 点
    	var points = data.point;
    	// 线
    	var relData = data.data;
    	// 计算可达点
    	var temp = [];
    	for(var j = 0; j<relData.length; j++){
    		temp.push(relData[j][0]);
    		temp.push(relData[j][1]);
		}
    	// 判断点是否可达
    	for(var i = 0; i<points.length; i++){
    		if(_.indexOf(temp,points[i].id) == -1){
    			return false;
    		}
    	}
    	return true;
    };
    
    var bindClickEvent = function(){
        // 地点下拉列表修改 触发楼层下拉列表变化
        $("#marketSel").chosen().change( function(){
            var placeId = $("#marketSel").val();
            changeFloor(placeId);
        });
        
        //  确认按钮点击  触发热力图刷新            
        $('#confirm').click(function(e){
            var placeId = $("#marketSel").val();
            floorNo = $("#floorSel").val();
            var floor = $("select[name='floorSelName']").find("option:selected").text();
            period = $("#periodSel").val();
            if (placeId=="") {
                $("#marketSel").blur();
                return false;
            }
            if (floorNo=="") {
                $("#floorSel").blur();
                return false;
            }
            $("#floorSel").blur();
            initMap(floorNo);
        });
        
        // 导出按钮点击，下载xml文件
        $("#exportButton").click(function(e){
        	var mapData = mpe.getData(map);
        	if(!checkData(mapData)){
        		alert("存在不可达点，请确认后再导出");
        		return;
        	}
        	
        	// 格式化数据，重新编号
        	var points = mapData.point;
        	// 排序
        	points.sort(function(a,b){return a.id - b.id;});
        	// 对关系数组里的点重新编号
        	var newMapData = {};
        	_.extend(newMapData, mapData);
        	var relData = newMapData.data;
        	for(var i = 0; i<relData.length; i++){
        		for(var j = 0; j<points.length; j++){
        			if(points[j].id == relData[i][0]){
        				relData[i][0] = j;
        			}else if(points[j].id == relData[i][1]){
        				relData[i][1] = j;
        			}
        		}
        	}
        	// 使用商场和楼层结合作为文件名
        	var placeId = $("#marketSel").val(),
            	floorNo = $("#floorSel").val(),
        		fileName = placeId + "_" + floorNo + "_pathplan.xml",
        		width3D = $("#width3D").val(),
        		height3D = $("#height3D").val(),
        		upperLeftX3D = $("#upperLeftX3D").val(),
        		upperLeftY3D = $("#upperLeftY3D").val(),
        		param = {
        			fileName:fileName,
        			data:newMapData,
        			optParam:{
        				width3D:width3D,
            			height3D:height3D,
            			upperLeftX3D:upperLeftX3D,
            			upperLeftY3D:upperLeftY3D
        			}        			
        		};
        	$.ajax({
        		url:"/sva/pathplan/api/createXml",
        		type:"post",
        		data:JSON.stringify(param),
        		contentType:'application/json',
        		dataType:"json",
        		success:function(data){
        			if(data.returnCode == 1){  
        				$("#downloadLink").attr("href", "/sva/upload/pathFile/"+data.file);
        				$("#downloadLink").attr("download",data.file); 
        				$("#downloadLink")[0].click();
        				// 导出后，更新页面上map插件里的data数据
        				$.get("/sva/pathplan/api/getSimpleXmlData?fileName="+fileName,function(data){
                        	if(!data.error){
                        		var option = {"data":{"point":data.data.point,"data":data.data.data}};
                        		mpe.setOption(map,option);
                        		mpe.refresh(map);
                        	}
                        });
            		}else{
            			alert("返回值：" +data.returnCode);
            		}
        		}
        	});
        });
    };
	
    //初始化下拉列表
    var initDropdown = function(){
        $("#marketSel").chosen({width:200});
        $("#floorSel").chosen({width:200});
        //将数据地点和楼层保存在cookie中
        var array=new Array();
        var arrayfloor=new Array();
        $.get("/sva/store/api/getData?t="+Math.random(),function(data){
            if(!data.error){
                var storeList = data.data;
                updateList("marketSel",storeList,"",function(){
                    $('#marketSel').trigger("liszt:updated");
                });
            }
        });
    }

    return {
    	//init初始化
    	init : function(){
    		initDropdown();
    		bindClickEvent();
    		
    	}

    };

}();