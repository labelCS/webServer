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
	    var options = '' ;
	    for(var i=0;i<len;i++){
	    	var info = sortData[i];
	        if(selectTxt == sortData[i].floor){
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'"  data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" selected=true value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}else{
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'"  data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
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
	    var options = "<option value=''></option>";
	    for(var i=0;i<len;i++){
	    	var info = sortData[i];
	        if(selectTxt == sortData[i].floor){
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'"  data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" selected=true value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}else{
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'"  data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    return;
	};
	var updateList1 = function(renderId,data,selectTxt,callback){
		var sortData = data.sort(function(a,b){return a.name - b.name;});
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	    	var info = sortData[i];
	        if(selectTxt == info.areaName){
	    		options += '<option class="addoption" selected=true value="'+info.id+'">' + HtmlDecode3(info.areaName) +'</option>';
	    	}else{
	    		options += '<option class="addoption" value="'+info.id+'">' + HtmlDecode3(info.areaName) +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    if(callback){
	        callback();
	    }
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
    
    var deleteMsg = function(xSpot, ySpot, floorNo, id){
    	if(confirm(i18n_deleteInfo))
    	{
    	$.post("/sva/message/api/deleteData",{xSpot:xSpot, ySpot:ySpot, floorNo:floorNo},function(data){
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
    var deleteTicket = function(id){
    	if(confirm(i18n_deleteInfo))
    	{
    		$.post("/sva/message/api/deleteTicket",{id:id},function(data){
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
//    			yanzheng(this);
				$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
					if(!data.error){
						updateFloorList1("zSel",data.data);
					}
				});
				
	    		$('a[href="#myModal1"]').attr("disabled","disabled");
			}); 		
    		
			// 楼层下拉列表修改 触发选择坐标时地图变化
			$("#zSel").on("change", function(){
				var floorNo = $("#zSel").val();
	    		$.get("/sva/input/api/getTableDataByFloorNo",{floorNo:floorNo},function(data){
	    			if(!data.error){
	    				updateList1("shopId",data.data);
	    			}
	    		});	
				var lastVal = this.validform_lastval; 
				if (lastVal!=null) 
				{
					this.validform_lastval = null; 
				}
//				if (lastVal==null&&val==null)
//				{
//					
//				}

//				$("#zSel").blur();

//				if (val==" ") {
//					$("#zSel").find("span").addClass("Validform_wrong");
//					$("#zSel").addClass("Validform_error");
//				}
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
					$('a[href="#myModal1"]').attr("disabled",false);
				}else{
					$('a[href="#myModal1"]').attr("disabled","disabled");
				}
			});		
			$('a[href="#myModal1"]').on("click",function(e){
				if(typeof($(this).attr("disabled"))!="undefined"){
					e.preventDefault();
					return false;
				}
     			$("#infoScale").text("");
     			$("#alertBoxScale").hide();
				Ploy.clearPaper();
				var xo = $("#xSpotId").val(),
     				yo = $("#ySpotId").val(),
     				range = $("#rangeSpotId").val();
				if(xo && yo && range){
					var r = parseFloat(range)*parseFloat(rect.scale)/rect.zoomScale.toFixed(2),
						coordinate = rect.coordinate,
						xtemp = (parseFloat(xo)+parseFloat(rect.x))*parseFloat(rect.scale)/rect.zoomScale.toFixed(2),
						ytemp = (parseFloat(yo)+parseFloat(rect.y))*parseFloat(rect.scale)/rect.zoomScale.toFixed(2);
					
					switch (coordinate){
	         		case "ul":
	         			var px = xtemp;
	             		var py = ytemp;
	         			break;
	         		case "ll":
	         			imagey = rect.height;
	         			var px = xtemp;
	             		var py = imagey-ytemp;
	         			break;
	         		case "ur":
	         			imagex = rect.width;
	         			var px = imagex-xtemp;
	             		var py = ytemp;
	         			break;
	         		case "lr":
	         			imagex = rect.width ;
	         			imagey = rect.height;
	         			var px = imagex-xtemp;
	             		var py = imagey-ytemp;
	         			break;
	         		}
					Ploy.makeSquare(px,py,r,$('#preview').width(),$('#preview').height(),"preview");
				}
			});
			$('#preview').click(function(e){
				if(!Ploy.square){
					var left=e.pageX;
					var top=e.pageY;
					var t=top-$('#preview').offset().top;
					var l=left-$('#preview').offset().left;
					Ploy.makeSquare(l,t,50,$('#preview').width(),$('#preview').height(),"preview");					
				}
				
			});
         	$(".clearPaper").on("click",function(e){
         		Ploy.clearPaper();
         	});
         	
         	$("#Ok").on("click",function(e){	
         		//判断原点位置
         		var coordinate = rect.coordinate;
         		if(Ploy.square != null){
         			var range = Ploy.square.square.attr("width")/2,
	         			xtemp = Ploy.square.square.attr("x")+range,
	         			ytemp = Ploy.square.square.attr("y")+range;
	         		switch (coordinate){
	         		case "ul":
	         			var px = xtemp;
	             		var py = ytemp;
	         			break;
	         		case "ll":
	         			imagey = rect.height;
	         			var px = xtemp;
	             		var py = imagey-ytemp;
	         			break;
	         		case "ur":
	         			imagex = rect.width;
	         			var px = imagex-xtemp;
	             		var py = ytemp;
	         			break;
	         		case "lr":
	         			imagex = rect.width ;
	         			imagey = rect.height;
	         			var px = imagex-xtemp;
	             		var py = imagey-ytemp;
	         			break;
	         		}
         		}	
	         		
     			if(px){
     				var scale = rect.scale;
         			$("#xSpotId").val(((parseFloat(px)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.x)).toFixed(2));
         			$("#ySpotId").val(((parseFloat(py)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.y)).toFixed(2));
         			$("#rangeSpotId").val((range*rect.zoomScale/parseFloat(scale)).toFixed(2));
         			$("#myModal1").modal('hide');
         			$("#xSpotId").blur();
         			$("#ySpotId").blur();
         			$("#rangeSpotId").blur();
         			$("#alertBoxScale").hide();
         		}else{	         			
         			$("#infoScale").text(i18n_choose_title);
         			$("#alertBoxScale").show();
         		}
         	});

            $("input[data-type='del']").live("click",function(e){
        		var xSpot = $(this).data("xspot"),
           	 		ySpot = $(this).data("yspot"),
           	 		floorNo = $(this).data("floorno"),
        		id = $(this).data("id");
	           	 deleteMsg(xSpot, ySpot, floorNo, id);
            });
            $("input[data-type='ticketDel']").live("click",function(e){
	           	var rowObj = $(this)[0].parentNode.parentNode;
	           	var row = $(this).parent().parent();
	        	var data1 = oTable.fnGetData(row[0]);
            	var id = data1.id;
            	deleteTicket(id);
            });
            
            $("input[data-type='edt']").live("click",function(e){
            	$(".demoform").Validform().resetForm();
            	$('a[href="#myModal1"]').attr("disabled",false);
            	var placeId = $(this).data("placeid");
	           	var rowObj = $(this)[0].parentNode.parentNode;
	           	var row = $(this).parent().parent();
            	var shopId = $(this).data("shopid");
	        	var data1 = oTable.fnGetData(row[0]);
	           	var place = rowObj.childNodes[0].innerHTML,
	           		   areaName =rowObj.childNodes[2].innerHTML,
	           	       shopName =$(rowObj.childNodes[3].childNodes[0]).attr("title"),
//	           	 	   xSpot = rowObj.childNodes[2].innerHTML,
//	           	 	   ySpot = rowObj.childNodes[3].innerHTML,
	           	 	   floor = rowObj.childNodes[1].innerHTML,
//	           	 	   rangeSpot = rowObj.childNodes[4].innerHTML,
	           	 	   message = $(rowObj.childNodes[4].childNodes[0]).attr("title"),
	           	 	   timeInterval = rowObj.childNodes[5].innerHTML;
	           	 	   isEnable = rowObj.childNodes[6].innerHTML;
	           	if ( rowObj.childNodes[6].innerHTML==i18n_open) {
	           		 isEnable = "开启";
				}
	           	if ( rowObj.childNodes[6].innerHTML==i18n_close) {
	           		 isEnable = "关闭";
				}
	
	           	//MapMng.deleteMap(xSpot, ySpot, zSpot, place);
	           	$("#placeSel").val(placeId);
				$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
					if(!data.error){
						$("#isEnableId").val(isEnable);
						$("#textfield").val(data1.pictruePath);
						$("#textfield1").val(data1.moviePath);
						$("#textfield2").val(data1.ticketPath);
						$("#idid").val(data1.id);
						updateFloorList("zSel",data.data,floor,function(){$("#zSel").change();});
					}
				});
	    		$.get("/sva/input/api/getTableData",{placeId:placeId},function(data){
	    			if(!data.error){
	    				updateList1("shopId",data.data);
	    				$("#shopId").val(shopId);
	    			}
	    		});					
				$("input[name='shopName']").val(HtmlDecode2(shopName));
//	           	$("input[name='xSpot']").val(xSpot);
//	           	$("input[name='ySpot']").val(ySpot);
	           	//$("#zSel").val(zSpot);
//	           	$("input[name='rangeSpot']").val(rangeSpot);
	           	$("input[name='message']").val(message);
	           	$("input[name='timeInterval']").val(timeInterval);
	           	$("#enableSel").val(isEnable);
	           	$("#editBox").show();
           	 
            });
            
            $("input[data-type='ticketEdt']").live("click",function(e){
            	$(".demoform").Validform().resetForm();
	           	var rowObj = $(this)[0].parentNode.parentNode;
	           	var row = $(this).parent().parent();
	        	var data1 = oTable.fnGetData(row[0]);
	        	var ticketPath = data1.ticketPath;
	        	var msgName = data1.name;
	        	var chances = data1.chances;
	        	var id = data1.ticketId;
	        	var msgId = data1.msgId;
	        	$("#textfield3").val(ticketPath);
	        	$("#ticketPathId").val(ticketPath);
	        	$("#chancesId").val(chances);
	        	$("#msgIdId").val(msgId);
	        	$("#ticketId").val(id);
            });            
            
            $("input[data-type='fuzhi']").live("click",function(e){
            	$(".demoform").Validform().resetForm();
            	$('a[href="#myModal1"]').attr("disabled",false);
            	var placeId = $(this).data("placeid");
            	
	           	var rowObj = $(this)[0].parentNode.parentNode;
	           	var row = $(this).parent().parent();
            	var shopId = $(this).data("shopid");
	        	var data1 = oTable.fnGetData(row[0]);
	           	var place = rowObj.childNodes[0].innerHTML,
        		   		areaName =rowObj.childNodes[2].innerHTML,
	           	       shopName =$(rowObj.childNodes[3].childNodes[0]).attr("title"),
//	           	 	   xSpot = rowObj.childNodes[2].innerHTML,
//	           	 	   ySpot = rowObj.childNodes[3].innerHTML,
	           	 	   floor = rowObj.childNodes[1].innerHTML,
//	           	 	   rangeSpot = rowObj.childNodes[4].innerHTML,
	           	 	   message = $(rowObj.childNodes[4].childNodes[0]).attr("title"),
	           	 	   timeInterval = rowObj.childNodes[5].innerHTML;
	           	 	   isEnable = rowObj.childNodes[6].innerHTML;
	           	if ( rowObj.childNodes[6].innerHTML==i18n_open) {
	           		 isEnable = "开启";
				}
	           	if ( rowObj.childNodes[6].innerHTML==i18n_close) {
	           		 isEnable = "关闭";
				}
	
	           	//MapMng.deleteMap(xSpot, ySpot, zSpot, place);
	           	$("#placeSel").val(placeId);
				$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
					if(!data.error){
						$("#isEnableId").val(isEnable);
//						$("#textfield").val(data1.pictruePath);
//						$("#textfield1").val(data1.moviePath);
//						$("#idid").val(data1.id);
						updateFloorList("zSel",data.data,floor,function(){$("#zSel").change();});
					}
				});
	    		$.get("/sva/input/api/getTableDataById",{placeId:placeId},function(data){
	    			if(!data.error){
	    				updateList1("shopId",data.data);
	    				$("#shopId").val(shopId);
	    			}
	    		});					
				$("input[name='shopName']").val(HtmlDecode2(shopName));
//	           	$("input[name='xSpot']").val(xSpot);
//	           	$("input[name='ySpot']").val(ySpot);
	           	//$("#zSel").val(zSpot);
//	           	$("input[name='rangeSpot']").val(rangeSpot);
	           	$("input[name='message']").val(message);
	           	$("input[name='timeInterval']").val(timeInterval);
	           	$("#enableSel").val(isEnable);
	           	$("#editBox").show();
           	 
            });
            
            $("a[data-type='preview']").live("click",function(e){
            	$("#movieCover").show();
            	var pictrue = $(this).data("pictrue");
            	var movie = $(this).data("movie");
            	var bgImgStr = "/sva/upload/" + pictrue;
            	var bgMovie2 = "/sva/upload/" + movie;
            	var bgMovie  = "http://www.w3school.com.cn/i/movie.mp4" ;
            	//var html = '<video id="movieId" width="798px" height="426px" controls="controls" poster="/sva/images/logo.png" src ="'+bgMovie2+'"> </video>';
            	$("#pictureid").attr("src",bgImgStr);
            	$("#video").attr("src",bgMovie2);
            	//$("#movie").append(html);
            	$('#myTab a:last').tab('show');
            	$('#myTab a:first').tab('show');
            	$("#myModal").modal("show");
            	//$("#movie").append(html);
            });
            
            $("a[data-type='ticket']").live("click",function(e){
            	$("#myTicketModel").modal("show");
            	var msgId = $(this).data("id");
            	$("#msgIdId").val(msgId);
            	$.get("/sva/message/api/getTicketData?",{msgId:msgId},function(data){
            		if(!data.error){
            			if(oTable){oTable.fnDestroy();};
            			oTable = $('#tableTicket').dataTable({
            				"bProcessing": true,
            				"sDom": 'rt<"toolbar"lp<"clearer">>',
            				"sPaginationType": "full_numbers",
            				"aaData":data.data,
            				"bStateSave": true,
            				"aoColumnDefs": [
            					{ 
            						"aTargets": [0],
//            						"bVisible": false,
            						"mData": "msg.shopName",
            						"mRender": function ( data, type, full ) {
        								if (data.length>10) {
        									var html = data.substring(0,10)+"...";
        									html = '<span title="'+data+'">'+HtmlDecode3(html)+'</span>';
        										return html;
        								}
        								return '<span title="'+HtmlDecode3(data)+'">'+HtmlDecode3(data)+'</span>';
        							}
            					},
            					{ 
            						"aTargets": [1],
            						"mData": "chances"
            					},
            					{ 
            						"aTargets": [2],
            						"mData": "ticketPath"
            					},
            					{
            	                    "aTargets": [3],
            	                    "bSortable": false,
            	                    "bFilter": false,
            	                    "mData": function(source, type, val) {
            	                        return "";
            	                    },
            	                    "mRender": function ( data, type, full ) {
            	                    	var html = "" +
            	                    		'<input type="button" data-type="ticketEdt" style="width: 54px;height:30px;font-size: 13px;font-family:inherit;" data-placeid="'+full.placeId+'" data-shopId="'+full.shopId+'" data-xSpot="'+full.xSpot+'" data-ySpot="'+full.ySpot+'" data-floorno="'+full.floorNo+'" value="'+i18n_edit+'" id="'+full.id+' ">' +
            	                    		'<input type="button" data-type="ticketDel" style="width: 54px;height:30px;font-size: 13px;font-family:inherit;" data-floorNo="'+full.floorNo+'" data-shopId="'+full.shopId+'" id="'+full.id+'" data-id="'+full.id+'" data-xSpot="'+full.xSpot+'" data-ySpot="'+full.ySpot+'" value="'+i18n_delete+'">';
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
            	});           	
            });            
            
        	$("#fileField").on("change",function(e){
        		var file = this;
        		document.getElementById('textfield').value=file.value;	
        		$("#textfield").blur();
        	});
        	
        	$("#fileField1").on("change",function(e){
        		var file = this;
        		document.getElementById('textfield1').value=file.value;	
        		$("#textfield1").blur();
        	});
        	$("#fileField2").on("change",function(e){
        		var file = this;
        		document.getElementById('textfield2').value=file.value;	
        		$("#textfield2").blur();
        	});
        	$("#fileField3").on("change",function(e){
        		var file = this;
        		document.getElementById('textfield3').value=file.value;	
        		$("#ticketPathId").text(file.value);
        		$("#textfield3").blur();
        	});
            
            $("#play").click(function(e){
            	var m = document.getElementById("video");
            	$("#movieCover").hide();
            	m.play();
            });
            
            $('#myModal').on('hidden', function () {
            	$("#video").attr("src","");
            });
            
            $(function () { $('#myTicketModel').on('hidden.bs.modal', function () {
            	window.location.href=window.location.href;
            	})
            	});
    	},
        
        initMsgTable:function(){
        	$.get("/sva/message/api/getTableData?t="+Math.random(),function(data){
        		if(!data.error){
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
        						"mData": "store.name",
        						"mRender": function ( data, type, full ) {
    								if (data.length>10) {
    									var html = data.substring(0,10)+"...";
    									html = '<span title="'+data+'">'+HtmlDecode3(html)+'</span>';
    										return html;
    								}
    								return '<span title="'+HtmlDecode3(data)+'">'+HtmlDecode3(data)+'</span>';
    							}
        					},
        					{ 
        						"aTargets": [1],
        						"mData": "maps.floor"
        					},
        					{ 
        						"aTargets": [2],
        						"mData": "area.areaName"
        					},
        					{ 
        						"aTargets": [3],
        						"mData": "shopName", 
        						"mRender": function ( data, type, full ) {
    								if (data.length>10) {
    									var html = data.substring(0,10)+"...";
    									html = '<span title="'+data+'">'+HtmlDecode3(html)+'</span>';
    										return html;
    								}
    								return '<span title="'+HtmlDecode3(data)+'">'+HtmlDecode3(data)+'</span>';
        						}
        						
        						
        					},
        					{ 
        						"aTargets": [4],
        						"mData": "message",
	    	                    "mRender": function ( data, type, full ) {
									if (data.length>10) {
										var html = data.substring(0,10)+"...";
										html = '<span title="'+data+'">'+HtmlDecode3(html)+'</span>';
											return html;
									}
									return '<span title="'+HtmlDecode3(data)+'">'+HtmlDecode3(data)+'</span>';
	    	                    }		
        					},
        					{ 
        						"aTargets": [5],
        						"mData": "timeInterval"
        					},
        					{
        	                    "aTargets": [6],
        	                    "bSortable": true,
        	                    "bFilter": false,
        	                    "mData": function(source, type, val) {
        	                        return "";
        	                    },
        	                    "mRender": function ( data, type, full ) {
        	                    	var html ;
        	                    	if (full.isEnable=="开启") {
										html = i18n_open;
									}
        	                    	if (full.isEnable=="关闭") {
										html = i18n_close;
									}
        	                        return html;
        	                      }
        	                },
        					{
        	                    "aTargets": [7],
        	                    "bSortable": false,
        	                    "bFilter": false,
        	                    "mData": function(source, type, val) {
        	                        return "";
        	                    },
        	                    "mRender": function ( data, type, full ) {
        	                    	var html = "" +
        	                    		'<input type="button" style="width: 53px;height:30px;font-size: 13px;font-family:inherit;" data-type="fuzhi" data-placeid="'+full.store.id+'" data-shopId="'+full.area.id+'" data-id="'+full.maps.floorid+'" value="'+i18n_fuzhi+'">' +
        	                    		'<input type="button" data-type="edt" style="width: 54px;height:30px;font-size: 13px;font-family:inherit;" data-placeid="'+full.store.id+'" data-shopId="'+full.area.id+'" data-xSpot="'+full.xSpot+'" data-ySpot="'+full.ySpot+'" data-floorno="'+full.maps.floorNo+'" value="'+i18n_edit+'" id="'+full.id+' ">' +
        	                    		'<input type="button" data-type="del" style="width: 54px;height:30px;font-size: 13px;font-family:inherit;" data-floorNo="'+full.maps.floorNo+'" data-shopId="'+full.area.id+'" id="'+full.id+'" data-id="'+full.id+'" data-xSpot="'+full.xSpot+'" data-ySpot="'+full.ySpot+'" value="'+i18n_delete+'">'+
        	                    		'<a data-type="preview" role="button" class="btn"  style="font-size:13px;" data-floorNo="'+full.maps.floorNo+'" id="'+full.id+'"  data-xSpot="'+full.xSpot+'" data-ySpot="'+full.ySpot+'" data-pictrue="'+full.pictruePath+'" data-movie="'+full.moviePath+'">'+i18n_Preview+'</a>'+
        	                    		'<a data-type="ticket" role="button" class="btn"  style="font-size:13px;" data-floorNo="'+full.maps.floorNo+'" data-id="'+full.id+'"  data-xSpot="'+full.xSpot+'" data-ySpot="'+full.ySpot+'" data-pictrue="'+full.pictruePath+'" data-movie="'+full.moviePath+'">'+i18n_ticket+'</a>';
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
        	});
        }
    };

}();

function HtmlDecode2(str) {
	var str1 = str.replace(/&lt;/g,"<");
	var str2 = str1.replace(/&gt;/g,">");
	var str3 = str2.replace(/&amp;/g,"&");
	var str4 = str3.replace(/&quot;/g,"\"");
	var str5 = str4.replace(/&apos;/g,"\'");
    return str5;
}
function HtmlDecode3(str) {
	var str1 = str.replace(/</g,"&lt;");
	var str2 = str1.replace(/>/g,"&gt;");
	return str2;
}

function estimateOnkeyup(str)
{
	if (isNaN(str.value)&&!isNaN(str.value.substring(0,str.value.length-1))) {
		str.value = str.value.substring(0,str.value.length-1);
		str.focus();
		return false;
	}
	if(isNaN(str.value)&&isNaN(str.value.substring(0,str.value.length-1)))
	{
	str.value = "";
	str.focus();
	return false;
	}
	if (str.value.split(".").length<2) {
		var a = parseInt(str.value.substring(str.value.length-1,str.value.length));
		if (a<0)
		{
			str.value = str.value.substring(0,str.value.length-1);
			str.focus();
			return false;
		}
	}else
		{
		var c = str.value.split(".")[1];
		var b = str.value.split(".")[0];
		if (c.length>2) 
		{
			str.value = str.value.substring(0,b.length+3);
			str.focus();
			return false;	
		}else
			{
			if (isNaN(str.value))
			{
				str.value = str.value.substring(0,str.value.length-1);
				str.focus();
				return false;
			}else
				{
				if(str.value.split(".")[1]!=""&&str.value.split(".")[1]!="0"&&str.value.split(".")[1]!="00")
					{
					str.value = parseFloat(str.value);
					str.focus();
					return false;
					}
				return false;
				}
			
			
			}
		}
	}
function addMap()
{
	$(".demoform").Validform().resetForm();
	clearinfo();
	$("#editBox").show();	
}
function hideAdd()
{
	$(".demoform").Validform().resetForm();
	clearinfo();
	$("#editBox").hide();
	$('a[href="#myModal1"]').attr("disabled","disabled");
}
function clearinfo()
{
	$("#placeSel").val("");
	$("#shopNameId").val("");
	$("#messageId").val("");
	$("#timeInterval").val("");
	$("#xSpotId").val("");
	$("#ySpotId").val("");
	$("#rangeSpotId").val("");
	$("#isEnableId").val("");
	$("#idid").val("");
	$("#textfield").val("");
	$("#fileField").val("");
	$("#fileField1").val("");
	$("#textfield1").val("");
	$("#zSel").find("option").remove(); 
	$("#zSel").val("");
	$("#textfield3").val("");
	$("#ticketPathId").val("");
	$("#chancesId").val("");
	$("#msgIdId").val("");
	$("#ticketId").val("");
}
/*
function editMap(str)
{
    $("input[data-type='edit']").live("click",function(e){
    	var row = $(str).parent().parent();
    	var data = oTable.fnGetData(row[0]);
		$("#editBox").show();
		$("#placeSel").val(data.place);
		var place = $("#placeSel").val();
		$.post("/sva/heatmap/api/getFloorsByMarket",{place:place}, function(data1){
			if(!data1.error){
				var floors = _.pluck(data1.data,"floor");
				var flooNos = _.pluck(data1.data,"floorNo");
				
			    var sortData = floors.sort();
			    var sortData1 =flooNos.sort();
			    var len = sortData.length;
			    var options = '';
			    for(var i=0;i<len;i++){
			    	if(data.floor == sortData[i]){
			    		options += '<option class="addoption" selected=true value="'+sortData1[i]+'">' + sortData[i] +'</option>';
			    	}else{
			    		options += '<option class="addoption" value="'+sortData1[i]+'">' + sortData[i] +'</option>';
			    	}
			    }
			    var renderId = "zSel";
			    $('#'+renderId+' .addoption').remove().trigger("liszt:updated");
			    $("#zSel").append(options);
			    $("#zSel").val(data.floorNo);
			}
		});
//		$("#zSel").val(data.floor);
//		var option = '<option value='+data.floor+'selected=true>';
//		$("#zSel").append(option);
		$("#shopNameId").val(data.shopName);
		$("#messageId").val(data.message);
		$("#xSpotId").val(data.xSpot);
		$("#ySpotId").val(data.ySpot);
		$("#rangeSpotId").val(data.rangeSpot);
		$("#isEnableId").val(data.isEnable);
		$("#textfield").val(data.pictruePath);
		$("#textfield1").val(data.moviePath);
		$("#idid").val(data.id);
		

    });	
}*/
function clickFile()
{
	var file = document.getElementById("fileField");
	//document.getElementById('textfield').value=file.value;
	file.click();
}
function clickFile1()
{
	var file = document.getElementById("fileField1");
	//document.getElementById('textfield1').value=file.value;
	file.click();
}
function clickFile2()
{
	var file = document.getElementById("fileField2");
	//document.getElementById('textfield1').value=file.value;
	file.click();
}
function fileValue(file)
{

		document.getElementById('textfield').value=file.value;	
		$("#textfield").blur();
}
function fileValue1(file)
{
	document.getElementById('textfield1').value=file.value;	
	$("#textfield1").blur();
}

function fileValue2(file)
{
	document.getElementById('textfield2').value=file.value;	
	$("#textfield2").blur();
}
function pictrueMovie(event)
{
	var pictrue = $(event).data("pictrue");
	var movie = $(event).data("movie");
	var bgImgStr = "/sva/upload/" + pictrue;
	var bgMovie2 = "/sva/upload/" + movie;
	var bgMovie  = "http://www.w3school.com.cn/i/movie.mp4" ;
	var html = '<video width=798px height=426px controls="controls" autoplay="autoplay"> <source  type="video/mp4" src ='+bgMovie2+'> </video>';
	$("#pictureid").attr("src",bgImgStr);
	$("#movie").append(html);
	$('#myTab a:last').tab('show');
	$('#myTab a:first').tab('show');
		
	//	"width":300+"px",
	//	"height":300+"px"

}
function closeModel()
{
	$("#myModal").modal('hide');
	$("#myTicketModel").modal('hide');
	//$("#movie").empty();
}
function closeTicket()
{
	window.location.href=window.location.href;
	$("#myTicketModel").modal('hide');
	$("#textfield3").val("");
	$("#ticketPathId").val("");
	$("#chancesId").val("");
	$("#msgIdId").val("");
	$("#ticketId").val("");
}


