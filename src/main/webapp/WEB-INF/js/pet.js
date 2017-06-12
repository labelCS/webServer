var MsgMng = function () {
	/**
	* 将对应信息填充到对应的select
	* @ param renderId [string] 标签id
	* @ param data [array] 列表数据
	*/
	var updateList = function(renderId,data,selectTxt,callback){
		var sortData = data.sort(function(a,b){return a.name - b.name;});
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	    	var info = sortData[i];
	        if(selectTxt == info.name){
	    		options += '<option class="addoption" selected=true value="'+info.id+'">' + HtmlDecode3(info.name) +'</option>';
	    	}else{
	    		options += '<option class="addoption" value="'+info.id+'">' + HtmlDecode3(info.name) +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    if(callback){
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
	    	var info = sortData[i];
	        if(selectTxt == sortData[i].floor){
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'"  data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" selected=true value="'+sortData[i].mapId+'">' + sortData[i].floor +'</option>';
	    	}else{
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'"  data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" value="'+sortData[i].mapId+'">' + sortData[i].floor +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    if(callback){
	        callback();
	    }
	    return;
	};
	
	var updateFloorList1 = function(renderId,data,selectTxt,callback){
		$("#zSel").find("option").remove(); 
	    var sortData = data.sort(function(a,b){return a.floor - b.floor;});
	    var len = sortData.length;
	    var options = '<option value=""></option>';
	    for(var i=0;i<len;i++){
	    	var info = sortData[i];
	        if(selectTxt == sortData[i].floor){
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'"  data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" selected=true value="'+sortData[i].mapId+'">' + sortData[i].floor +'</option>';
	    	}else{
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'"  data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" value="'+sortData[i].mapId+'">' + sortData[i].floor +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    return;
	};
	
	var clacImgZoomParam = function( maxWidth, maxHeight, width, height,x,y,coordinate){  
	    var param = {top:0, left:0, width:width, height:height,x:x,y:y,coordinate:coordinate};  
	    rateWidth = width / maxWidth;  
	    rateHeight = height / maxHeight;  
	      
	    if( rateWidth > rateHeight ){  
	        param.width =  maxWidth;  
	        param.height = Math.round(height / rateWidth);  
	    }else{  
	        param.width = Math.round(width / rateHeight);  
	        param.height = maxHeight;  
	    }
	      
	    param.left = Math.round((maxWidth - param.width) / 2);  
	    param.top = Math.round((maxHeight - param.height) / 2); 
	    param.zoomScale = width / param.width;
	    return param;  
	};
    
    var deleteInput = function(id){
    	if(confirm(i18n_deleteInfo))
    	{
    	$.post("/sva/petAttributes/deleteData",{id:id},function(data){
    		if(!data.error){
        		var obj = document.getElementById(id);
        		obj=obj.parentNode;
        		obj=obj.parentNode;
        		obj.parentNode.removeChild(obj);
			}
    	});
    }else
    	{
    	return false;
    	}
    };
    
    var removeOption = function(renderId){
		$('#'+renderId+' .addoption').remove().trigger("liszt:updated");
	};

    return {
    	initSelect: function(){
    		$.get("/sva/store/api/getData?t="+Math.random(),function(data){
    			if(!data.error){
    				updateList("placeSel",data.data);
    			}
    		});
    	},
    	bindClickEvent: function(){
    		// 地点下拉列表修改 触发楼层下拉列表变化
    		$("#placeSel").on("change", function(){
    			var placeId = $("#placeSel").val();
    			yanzheng(this);
				$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
					if(!data.error){
						updateFloorList1("zSel",data.data);
					}
				});
	    		$("#search").hide();
			});
			// 楼层下拉列表修改 触发选择坐标时地图变化
			$("#zSel").on("change", function(){
				var lastVal = this.validform_lastval; 
				if (lastVal!=null) 
				{
					this.validform_lastval = null; 
				}
				var opts = $("#zSel option");
				var selectedOpt = opts[$(this)[0].selectedIndex];
				if($("#zSel").val() != " "){
					var width = $(selectedOpt).data("width"),
						height = $(selectedOpt).data("height"),
						path = $(selectedOpt).data("path"),
						scale = $(selectedOpt).data("scale");
						coordinate = $(selectedOpt).data("coordinate");
						x = $(selectedOpt).data("x");
						y = $(selectedOpt).data("y");
					var MAXWIDTH  = document.getElementById("body").offsetWidth * 0.8;  
					var MAXHEIGHT = 500;  	
					rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, width, height,x,y,coordinate);
					rect.scale = scale;
					$("#preview").empty();
					$("#preview").css({
						"position":"absolute",
						"width" : rect.width + "px",
						"height" : rect.height + "px",
						"x" : rect.x + "px",
						"y" : rect.y + "px",
						"coordinate" :rect.coordinate,
						"margin-left" : rect.left + 'px',
						"margin-top" : rect.top + 'px',
						"background-image": "url(../upload/" + path + ")",
						"background-size":"cover",
						"-moz-background-size": "cover"
					});
					petDataX =[];
					petDataY = [];
					$("#myCanvas").remove();
					var htm = '<canvas z-index="9999" id="myCanvas" onclick="petClick()" height="500" width='+rect.width+'></canvas>';
					$("#divCon").append(htm);	
					
					$("#myCanvas").css({
						"coordinate" :rect.coordinate,
						"margin-top" : rect.top + 'px',
						"position":"relative",
						"background-size":"cover",
						"-moz-background-size": "cover"

					});
					
					
					$("#areapreview").empty();
					$("#areapreview").css({
						"width" : rect.width + "px",
						"height" : rect.height + "px",
						"x" : rect.x + "px",
						"y" : rect.y + "px",
						"coordinate" :rect.coordinate,
						"margin-left" : rect.left + 'px',
						"margin-top" : rect.top + 'px',
						"background-image": "url(../upload/" + path + ")",
						"background-size":"cover",
						"-moz-background-size": "cover"
					});
					
					
				}else{
				}
	            var	floorNo= $("#zSel").val();
				var petName = $("#petNameId").val();
            	var range = $("#rangeId").val();
            	var coordinate = rect.coordinate;
				var params={zSel:floorNo,petName:petName};
            	$.ajax({
		              "dataType": 'json', 
		              "type": "POST", 
		              "url": "/sva/petLocation/getDataByPet", 
		              "data": params, 
		              "success": function(data){
		            	  if(data.error){
		            		  return false;
		            	  }else{
								for ( var i = 0; i < data.data.length; i++) {
									//	Ploy.paper.clear();
										var x =  data.data[i].actualPositionX;
										var y =  data.data[i].actualPositionY;
										AreaMakeRect(x,y);
										petDataX.push(x);
										petDataY.push(y);
									}
		            		

		            	  }
		              }
		        });
				
			});		

			

			
			$('#preview').click(function(e){
				
				var left=e.pageX;
				var top = e.pageY;
				var o = {
					left : left,
					top : top
				};
				var datas = Ploy.getData();
				if (datas.length < 2) {
					Ploy.makeRect('#preview', o);
					// Ploy.addPoint(top,left);
					var t = top - $('#preview').offset().top;
					var l = left - $('#preview').offset().left;
					if (datas.length < 1) {
						$("#x0").val(l);
						$("#y0").val(t);
					} else {
						$("#x1").val(l);
						$("#y1").val(t);
						$("#Ok").attr("disabled",false);
					}
				}
				
			});
			$(".clearPaper").on("click", function(e) {
				$("#Ok").attr("disabled","disabled");
				petDataX = [];
				petDataY = [];
				clearPetData();

			});
			
			$("#Ok").on("click",function(e){
				
                var	count= $("#countId").val();
                var	floorNo= $("#zSel").val();
				var petName = $("#petNameId").val();
            	var params ={
            			count:count,
            			x:petDataX,
            			y:petDataY,
            			floorNo:floorNo,
            			petType:petName
                	};

            	$.ajax({
		              "dataType": 'json', 
		              "type": "POST", 
		              "url": "/sva/petLocation/saveData", 
		              "data": params, 
		              "success": function(data){
		            	  if(data.error){
		            		  $(".sameInfo").addClass("Validform_wrong"); 
		            		  $(".sameInfo").text(i18n_sameName); 
		            		  return false;
		            	  }else{
		            		  alert("Success");

		            	  }
		              }
		        });
			
			
				
         	});
			function AreaMakeRect(x,y){
				//var width=document.getElementById("preview").style.width;
				//var height=document.getElementById("preview").style.height;
				//通过得到的实际米数计算出象数
				//$("#pointX1").val(((parseFloat(px1)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.x)).toFixed(2));
				var scale = rect.scale;
				px = ((parseFloat(x)+parseFloat(rect.x))*parseFloat(scale)/rect.zoomScale).toFixed(2);
				py = ((parseFloat(y)+parseFloat(rect.y))*parseFloat(scale)/rect.zoomScale).toFixed(2);
				var coordinate = rect.coordinate;
				switch (coordinate){
				case "ul":
					
         			break;
         		case "ll":
         			imagey = rect.height;
         			 px = px;
             		 py = imagey-py;
         			break;
         		case "ur":
         			imagex = rect.width ;
             		 px =imagex-px;
             		 py = py;             		
         			break;
         		case "lr":
         			imagex = rect.width ;
         			imagey = rect.height;
	             		 px = imagex-px;
	             		 py = imagey-py;
				}
				var y2 =  $('#myCanvas').offset().top;
				var x2 =  $('#myCanvas').offset().left;
				var range = $("#rangeId").val();
				

				var x=px;
				var y=py;

				//坐标 x,y
	
				var c=document.getElementById("myCanvas");
				var cxt=c.getContext("2d");
				cxt.beginPath();
				cxt.arc(x,y,range,0,Math.PI*2,true);
				cxt.closePath();
				cxt.strokeStyle="red";
				cxt.stroke();

				cxt.fillStyle="black";
				cxt.fillRect(x,y,1,1);
				
			    	   	
			}	
			
			$("a[data-type='preview']").on("click",function(e){
				if(typeof($(this).attr("disabled")) != "undefined"){
					e.preventDefault();
					return false;
				}
				Ploy.clearPaper();
		
				
			});
		
            $("input[data-type='del']").live("click",function(e){
           	 		var id = $(this).data("id");
           	 		var petName = $(this).data("petname");
    	        	var option  = document.getElementById("petName").options;
    	        	option.add(new Option(petName,petName));
    	        	deleteInput(id);
            });
//            $("input[data-type='fuhzi']").live("click",function(e){
//            	var xSpot = $(this).data("xspot"),
//            	ySpot = $(this).data("yspot"),
//            	x1Spot = $(this).data("x1spot"),
//            	y1Spot = $(this).data("y1spot"),
//            	floorNo = $(this).data("floorno"),
//            	categoryId = $(this).data("categoryid");
//            	id = $(this).data("id");
//            	deleteInput(xSpot, ySpot, x1Spot,y1Spot,floorNo,categoryId,id);
//            });
            $("#confirm").on("click",function(e){
                var	petName= $("#petName").val();
                var	viewRange= $("#viewRange").val();
                var	captureRange= $("#captureRange").val();
                var	probability= $("#probability").val();
                var	count= $("#count").val();
                var	floorNo= $("#zSel").val();
                var z = floorNo;
                var	id= $("#idid").val();
                $("#petName").blur();
                $("#viewRange").blur();
                $("#captureRange").blur();
                $("#probability").blur();
                $("#count").blur();
                var check = validForm.check(false);
            	var param ={
            			petName:petName,
            			viewRange:viewRange,
            			captureRange:captureRange,
            			probability:probability,
            			count:count,
            			id:id
                	};
            	if (check) {
            		$.ajax({
            			"dataType": 'json', 
            			"type": "POST", 
            			"url": "/sva/petAttributes/saveData", 
            			"data": param, 
            			"success": function(data){
            				if(data.error){
            					$(".sameInfo").addClass("Validform_wrong"); 
            					$(".sameInfo").text(i18n_sameName); 
            					return false;
            				}else{
            					
            					window.location.reload();
            				}
            			}
            		});
            		
				}

            	
            });
            
            $("input[data-type='edt']").live("click",function(e){
            	$(".demoform").Validform().resetForm();
            	$(".sameInfo").removeClass("Validform_wrong");
            	$(".sameInfo").text("");
	           	var rowObj = $(this)[0].parentNode.parentNode;
	           	var row = $(this).parent().parent();
	        	var data1 = oTable.fnGetData(row[0]);
	        	console.log(rowObj);
	        	var probability = data1.probability;
	        	var viewRange = data1.viewRange;
	        	var count = data1.count;
	        	var petName = data1.petName;
	        	var captureRange = data1.captureRange;
	        	var id = data1.id;
	        	$("#viewRange").val(viewRange);
	        	$("#probability").val(probability);
	        	$("#count").val(count);
	        	$("#captureRange").val(captureRange);
	        	var option  = document.getElementById("petName").options;
	        	var lengths = option.length;
	        	//判断是否编辑了多个选项
	        	if (lengths>petData.length+1) {
	        		document.getElementById("petName").options.length = 0;
	        		document.getElementById("petName").options.add(new Option(" "," "));
		        	for(i=0; i<petData.length; i++)
		        	{
		        		document.getElementById("petName").options.add(new Option(petData[i],petData[i]));
		        	}
		        	document.getElementById("petName").options.add(new Option(petName,petName));
		        	lengths = document.getElementById("petName").options.length;
				}

//	        /	判断是否编辑了同个选项

	        	var panduan = 0;
	        	for(i=0; i<lengths; i++)
	        		{
		        		if (option[i].value == petName)
		        		{
		        			panduan = 1;
						}
	        		}
	        	if (panduan!=1) 
	        	{
	        		option.add(new Option(petName,petName));
				}
	        	var lengths = option.length;
	        	for(i=0; i<lengths; i++)
	        		{
		        		if (option[i].value == petName)
		        		{
		        			option[i].selected = true;
						}
	        		}
	        	$("#idid").val(id);
	           	$("#editBox").show();
           	 
            });
            
            
            $("a[data-type='point']").live("click",function(e){

            	var captureRangeVal = $(this).data("capturerange");
            	var count = $(this).data("count");
            	var petName = $(this).data("petname");
            	$("#rangeId").val(captureRangeVal);
            	$("#countId").val(count);
            	$("#petNameId").val(petName);
            	

            });
            
    	},
        
        initMsgTable:function(){
        	$.get("/sva/petAttributes/api/getTableData?t="+Math.random(),function(data){
        		if(!data.error){
        			console.log(data);
        			if(oTable){oTable.fnDestroy();};
        			oTable = $('#table').dataTable({
        				"bProcessing": true,
        				"sDom": 'rt<"toolbar"lp<"clearer">>',
        				"sPaginationType": "full_numbers",
        				"aaData":data.data,
        				"bStateSave": true,
        				"aoColumnDefs": [
        					{ 
        						"aTargets": [0],
//        						"bVisible": false,
        						"mData": "petName"
        					},
        					{ 
        						"aTargets": [1],
        						"mData": "viewRange"
        					},
        					{ 
        						"aTargets": [2],
        						"mData": "captureRange"
        					},
        					{ 
        						"aTargets": [3],
        						"mData": "probability"
        					},
        					{ 
        						"aTargets": [4],
        						"mData": "count"
        					},
        					{
        	                    "aTargets": [5],
        	                    "bSortable": false,
        	                    "bFilter": false,
        	                    "mData": function(source, type, val) {
        	                        return "";
        	                    },
        	                    "mRender": function ( data, type, full ) {
        	                    	var html = "" +
        	                    	'<a data-toggle="modal" data-type="point" href="#myModal1" role="button" class="btn"  style="font-size:13px;id="'+full.id+'" data-petName="'+full.petName+'" data-count="'+full.count+'" data-captureRange="'+full.captureRange+'">'+i18n_petPosition+'</a>'+
        	                    		'<input type="button" data-type="edt" style="width: 54px;height:30px;font-size: 13px;font-family:inherit;" data-placeid="'+full.id+'" data-petName="'+full.petName+'" value="'+i18n_edit+'" id="'+full.id+' ">' +
        	                    		'<input type="button" data-type="del" style="width: 54px;height:30px;font-size: 13px;font-family:inherit;" data-floorNo="'+full.id+'" data-petName="'+full.petName+'" id="'+full.id+'" data-id="'+full.id+'" value="'+i18n_delete+'">';
        	                    		//'<a data-type="preview" role="button" class="btn"  style="font-size:13px;" data-floorNo="'+full.floorNo+'" id="'+full.id+'"  data-xSpot="'+full.xSpot+'" data-ySpot="'+full.ySpot+'" data-pictrue="'+full.pictruePath+'">'+i18n_Preview+'</a>';
        	                    		
        	                        return html;
        	                      }
        	                }
        				],
        				"fnCookieCallback": function (sName, oData, sExpires, sPath) {      
        					// Customise oData or sName or whatever else here     
        					var newObj = {iLength:oData.iLength};
        					return sName + "="+JSON.stringify(newObj)+"; expires=" + sExpires +"; path=" + sPath;    
        				}
        			});
        		} 
        		var all_options = ['皮卡丘','绿毛虫','小火龙','鬼斯通'];
        		var option  = document.getElementById("petName").options;
        		 var leng = all_options.length;
        		 for (i=0; i<data.data.length; i++)
        			 {

		        			  for(j=0; j<leng; j++)
		        				  {
			        				  if (all_options[j] == data.data[i].petName)
			        				  {
			        					  delete all_options[j];
			        				  }
		        				  
		        				 
	        			 }
        			 }
        		 for(i=0; i<leng; i++)
        			 {
	        			 if (all_options[i]!=undefined) 
	        			 {
							
	        				 option.add(new Option(all_options[i],all_options[i]));
	        				 petData.push(all_options[i]);
	        			 }
        			 }
        		 
        		 
        	});
        	
        	
        }
    };

}();
function checkMsg()
{
	var placeSel =$("#placeSel").val();
	var floor=$("select[name='floorNo']").find("option:selected").text();
	var areaName=$("input[name='areaName']").val();
	var xSpot=$("input[name='xSpot']").val();
	var ySpot=$("input[name='ySpot']").val();
	var x1Spot=$("input[name='x1Spot']").val();
	var y1Spot=$("input[name='y1Spot']").val();
	
	if (xSpot==""||ySpot==""||floor==""||x1Spot == "" || y1Spot=="" || placeSel == ""||areaName== "") {
		alert(i18n_info);
		return false;
	}

}

	
function addMap()
{
	$("#search").hide();
	$(".demoform").Validform().resetForm();
	$(".sameInfo").removeClass("Validform_wrong");
	$(".sameInfo").text("");
	clearinfo();
	$("#editBox").show();	
}
function hideAdd()
{
	$(".demoform").Validform().resetForm();
	clearinfo();
	$("#editBox").hide();
}

function clearinfo()
{
	$("#placeSel").val("");
	$("#zSel").val("");	
	$("#areaName").val("");	
	$("#pointX1").val("");
	$("#pointY1").val("");
	$("#pointX2").val("");
	$("#pointY2").val("");
	$("#idid").val("");	
	$("#zSel").find("option").remove();
	petDataX = [];
	petDataY = [];
}

function closeModel()
{
	$("#myModal").modal('hide');
	//$("#movie").empty();
}

function petClick()
{

	var y2 =  $('#myCanvas').offset().top;
	var x2 =  $('#myCanvas').offset().left;
	var range = $("#rangeId").val();
//	var x = document.getElementById('myCanvas').offsetLeft;
//	var y = document.getElementById('myCanvas').offsetTop;

	var x1=event.pageX;
	var y1=event.pageY

	//坐标 x,y
	var x = x1-x2;
	var y = y1-y2;
	var c=document.getElementById("myCanvas");
	var cxt=c.getContext("2d");
	cxt.beginPath();
	cxt.arc(x,y,range,0,Math.PI*2,true);
	cxt.closePath();
	cxt.strokeStyle="red";
	cxt.stroke();

	cxt.fillStyle="black";
	cxt.fillRect(x,y,1,1);
		
	//坐标转换
	var px1,py1;
	var coordinate = rect.coordinate;
	switch (coordinate){
	case "ul":
		 px1 = x;
 		 py1 = y;

		break;
	case "ll":
		imagey = rect.height;
		 px1 = x;
 		 py1 = imagey-y;	
		break;
	case "ur":
		imagex = rect.width ;
 		 px1 =imagex-x;
 		 py1 = y;
		break;
	case "lr":
		imagex = rect.width ;
		imagey = rect.height;
		px1 = imagex-x;
		py1 = imagey-y;
		
		break;
	}
		var scale = rect.scale;
		var zX = ((parseFloat(px1)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.x)).toFixed(2);
		var zY= ((parseFloat(py1)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.y)).toFixed(2);
	petDataX.push(zX);
	petDataY.push(zY);
	$("#Ok").attr("disabled",false);
}
	
function clearPetData()
{
	
	var c=document.getElementById("myCanvas");
	if (c!=null) {
		var cxt=c.getContext("2d");
		
		cxt.fillStyle="white";
		cxt.fillRect(0,0,rect.width,rect.height);
	}
}

$('#myModal1').on('hide.bs.modal', function () {
	window.location.reload();
	})