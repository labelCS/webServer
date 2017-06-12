/**
 * 移动端地图定位导航
 */
var Map = function(){
	/**
	 * 将对应信息填充到对应的select @ param renderId [string] 标签id @ param data [array]
	 *                   列表数据
	 */
	var updateList = function(renderId, data, selectTxt, callback) {
		var sortData = data.sort(function(a, b) {
			return a.name - b.name;
		});
		var len = sortData.length;
		var options = '';
		for ( var i = 0; i < len; i++) {
			if(sortData[i].id == selectTxt){
				options += '<input type="radio" name="radio-store" id="radio-store-'+i+'" data-name="'+sortData[i].name+'" value="'+sortData[i].id+'" checked="checked">'
				options += '<label for="radio-store-'+i+'">'+sortData[i].name+'</label>';
			}else{
				options += '<input type="radio" name="radio-store" id="radio-store-'+i+'" data-name="'+sortData[i].name+'" value="'+sortData[i].id+'">'
				options += '<label for="radio-store-'+i+'">'+sortData[i].name+'</label>';
			}
		}
		$('#' + renderId).empty();
		$('#' + renderId).append(options);
		if (callback) {
			callback();
		}
		return;
	};
	
	var getStores = function(){
		$.get("/sva/mobile/store/api/getData?t="+Math.random(), function(data) {
			if (!data.error) {
				var storeList = data.data;
				updateList("storeList", storeList);
			}
		});
	};
	
	var dataFilter = function(data, xo, yo, scale, width, height, coordinate){
		xo = parseFloat(xo);
		yo = parseFloat(yo);
		scale = parseFloat(scale);
		var point;
		switch(coordinate){
		case "ul":
			point ={
				x : (data.x/10+xo)*scale,
				y : (data.y/10+yo)*scale
			};
			break;
		case "ll":
			point ={
				x : (data.x/10+xo)*scale,
				y : height-(data.y/10+yo)*scale
			};
			break;
		case "ur":
			point ={
				x : width-(data.x/10+xo)*scale,
				y : (data.y/10+yo)*scale
			};
			break;
		case "lr":
			var point ={
				x : width-(data.x/10+xo)*scale,
				y : height-(data.y/10+yo)*scale
			};
			break;		
		}
		
		return point;
	};
	
	// 消息推送模块
	var showMessage = function(x, y, mapId){
		// 取出所处楼层的区域信息
		var currentAreaInfo = _.filter(areaInfo,function(el){
			var res = (el.maps.floorNo == mapId) && (x > el.xSpot && x < el.x1Spot && y > el.ySpot && y < el.y1Spot)
			return res}
		);
		// 所处区域的id数组
		var areaIds = _.pluck(currentAreaInfo, "id");
		if(!areaIds.length){
			return;
		}
		// 获取所处区域对应的推送消息和电子围栏
		$.ajax({
    		url:"/sva/api/getMessages",
    		type:"post",
    		data:JSON.stringify({areaId:areaIds}),
    		contentType:'application/json',
    		dataType:"json",
    		success:function(data){
    			if(data.message && data.message.length > 0){
    				var show = false;
    				var msgHtml = "";
        			for(var i=0; i<data.message.length; i++){
        				var msg = data.message[i];
        				var currentTime = new Date().getTime();
        				if(messageMapper[msg.id] && (messageMapper[msg.id] + msg.timeInterval*60000) > currentTime){
        					
        				}else{
        					show = true;
        					// 更新时间
        					messageMapper[msg.id] = currentTime;
        					// 弹出消息推送
        					msgHtml += '' +
        					'<a href="#messagePush'+i+'" data-rel="popup" data-position-to="window" data-transition="fade">'+
                            	'<img class="popphoto" src="/sva/upload/'+msg.pictruePath+'" style="width:300px;">' +
        					'</a>' ;//+
        					//'<div data-role="popup" id="messagePush'+i+'" data-overlay-theme="a" data-theme="d" data-corners="false">' +
                            //	'<a href="#" data-rel="back" data-role="button" data-theme="a" data-icon="delete" data-iconpos="notext" class="ui-btn-right">Close</a>' +
                            //	'<img class="popphoto" src="/sva/upload/'+msg.pictruePath+'" style="max-height:512px;">' +
                            //'</div>';
        				}
        			}
        			
        			if(show){
        				$("#popFromBottom").css("display","inline");
    					$("#popWrapper").append(msgHtml);
        			}
    			}
    		}
    	});
	};
	
	var bindClickEvent = function(){
		// 地点按钮点击事件
		$("#radioOk").on("click",function(){
			var radio = document.getElementsByName("radio-store");
			var selectId,storeName;
		    for (i=0; i<radio.length; i++) {  
		        if (radio[i].checked) {  
		        	storeId = radio[i].value;
		        	selectId = radio[i].id;
		        	storeName = $(radio[i]).data("name");
		        	break;
		        }  
		    }
		    $("#storeLink").text(storeName);
		    $("#storeName").text(storeName);
		});
		
		// 地图按钮点击事件
		$("#mapLink").on("click",function(){
			clearInterval(timer);
			// 获取地图数据
			$.post("/sva/mobile/store/api/getInfoByStore", {
				placeId : storeId
			}, function(data) {
				if (!data.error) {
					mapInfo = data.data.maps;
					areaInfo = data.data.areas;
					var sortData = mapInfo.sort(function(a, b) {
						return a.floor - b.floor;
					});
					mapId = sortData[0].floorNo;
					var len = sortData.length;
					var divHeight = $("#mapContainer").position().top;
					var options = '';
					for ( var i = 0; i < len; i++) {
						var top = divHeight + 5 + i*45;
						options += '<div class="floorDiv" data-floorNo="'
							+ sortData[i].floorNo + '" style="top:'+top+'px;left:15px;z-index:5;">' + sortData[i].floor + '</div>';
					}
					$(".floorDiv").remove();
					$("#mapContainer").append(options);
					mapObj = IndoorMap.init({
						mapUrl:"/sva/upload/" + sortData[0].svg, 
						containerId:"mapContainer",
						navToolId:"showNav",
						hammerId:"hammerCover",
						mapWidthOrign:screen.width-20,
						mapHeightOrign:$("#mapContainer").height(),
						mapId:sortData[0].floorNo,
						storeId:storeId
					});
					currentMap = sortData[0];
					console.log($("#mapContainer").height());
					$($(".floorDiv")[0]).addClass("active");
					//IndoorMap.addPoint(mapObj,ipToHex(localIp));
				}
			});
		});
		
		// 楼层选择事件
		$(".floorDiv").live("click",function(){
			$(".floorDiv").removeClass("active");
			$(this).addClass("active");
			clearInterval(timer);
			var selectedFloor = $(this).data("floorno");
			var svgImg,mapWidth,mapHeight;
			for ( var i = 0; i < mapInfo.length; i++) {
				if(mapInfo[i].floorNo == selectedFloor){
					svgImg = mapInfo[i].svg;
					mapWidth = mapInfo[i].imgWidth;
					mapHeight = mapInfo[i].imgHeight;
					mapId = mapInfo[i].mapId;
					currentMap = mapInfo[i];
					break;
				}
			}
			IndoorMap.changeOption(mapObj,{
				mapUrl:"../upload/" + svgImg,
				mapWidthOrign:mapWidth,
				mapHeightOrign:mapHeight,
				mapId:mapId
				}
			);
			//IndoorMap.addPoint(mapObj,ipToHex(localIp));
			
		});
		
		// 定位按钮点击事件
		$("#findPosition").on("click",function(){
			//$("#showNav").popup("open",{x:50,y:50});
			// 测试
			localIp = "192.168.10.112";
			if(!timer){
				timer = setInterval(function(){
					$.ajax({
		        		url:"/sva/api/getData",
		        		type:"post",
		        		data:JSON.stringify({ip:localIp}),
		        		contentType:'application/json',
		        		dataType:"json",
		        		success:function(data){
		        			if(data.data){
			        			var point = dataFilter(data.data,currentMap.xo, currentMap.yo, currentMap.scale, currentMap.imgWidth, currentMap.imgHeight, currentMap.coordinate);
			        			IndoorMap.refreshPoint(mapObj,point);
			        			// 推送消息
			        			showMessage(data.data.x/10, data.data.y/10, mapId);
		        			}
		        		}
		        	});
				},1000);
			}
			
		});
		
		// 消息推送弹出框关闭事件
		$("#closeMessage").on("click",function(e){
			$("#popFromBottom").css("display","none");
			$("#popWrapper").empty();
		});
		
		// 取消导航按钮点击事件
		$("#cancelNav").on("click", function(e){
			IndoorMap.clear(mapObj);
			$("#cancelNav").css("display","none");
		});
	};
	
	return {
		init : function(){
			getStores();
		},
		
		bindEvent : function(){
			bindClickEvent();
		}
	}
}();