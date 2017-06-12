var show = function() {
	/**
	 * 将对应信息填充到对应的select @ param renderId [string] 标签id @ param data [array]
	 *                   列表数据
	 */
	var updateList = function(renderId, data, callback) {
		var sortData = data.sort(function(a,b){return a.name - b.name;});
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	    	var info = sortData[i];
    		options += '<option class="addoption" value="'+info.id+'">' + HtmlDecode3(info.name) +'</option>';
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    if(callback){
	        callback();
	    }
	    return;
	};
	var updateFloorList1 = function(renderId,data,selectTxt,callback){
		$("#floorSel").find("option").remove(); 
	    var sortData = data.sort(function(a,b){return a.floor - b.floor;});
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	    //	var info = sortData[i];
	        if(selectTxt == sortData[i].mapId){
	    		options += '<option class="addoption" selected=true value="'+sortData[i].mapId+'">' + sortData[i].floor +'</option>';
	    	}else{
	    		options += '<option class="addoption"  value="'+sortData[i].mapId+'">' + sortData[i].floor +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    if(callback){
	    	callback();
	    }
	    return;
	};
	
	var updateFloorByStoreId = function(storeId, floorDomId, selectedVal){
		
		$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:storeId}, (function(floorId,val){
			return function(data){
				if(!data.error){
					updateFloorList1(floorId,data.data,val,function(){$('#'+floorDomId).trigger("liszt:updated");});
				}
			}})(floorDomId,selectedVal));
	}

	var removeOption = function(renderId) {
		$('#' + renderId + ' .addoption').remove().trigger("liszt:updated");
	};
	
	var clearInput = function(){
		// 清空隐藏值
		$("#idHidden").val("");
		// 清空下拉列表
		$("#placeSel1").val("");
		$("#placeSel2").val("");
		$("#placeSel3").val("");
		$("#placeSel4").val("");
		$("#placeSel5").val("");
		$("#placeSel6").val("");
		$("#placeSel7").val("");
		$("#placeSel8").val("");
		$("#zSel1").val("");	
		$("#zSel2").val("");
		$("#zSel3").val("");	
		$("#zSel4").val("");	
		$("#zSel5").val("");	
		$('#zSel6').val("");
		$('#zSel7').val("");
		$('#zSel8').val("");
		$('#densitySel1').val("1");
		$('#densitySel2').val("1");
		$('#densitySel3').val("1");
		$('#densitySel4').val("1");
		$('#densitySel5').val("1");
		$('#densitySel6').val("1");
		$('#densitySel7').val("1");
		$('#densitySel8').val("1");
		$('#radiusSel1').val("20");
		$('#radiusSel2').val("20");
		$('#radiusSel3').val("20");
		$('#radiusSel4').val("20");
		$('#radiusSel5').val("20");
		$('#radiusSel6').val("20");
		$('#radiusSel7').val("20");
		$('#radiusSel8').val("20");		
		// 除第一个商场外，全部隐藏
		$(".storeDiv").addClass("hide");
		$(".storeDiv").hide();
		$(".storeDiv").first().show();
		$(".storeDiv").first().removeClass("hide");
		// 清空固定参数
		$('#periodSel').val("5");
		$("#coefficient").val("1");
		$("#select_time_begin_tab1").val("");
		// 清楚自定义参数
		$("#optTr").siblings().remove();
		
		$(".demoform").Validform().resetForm();
	   	$(".sameInfo").removeClass("Validform_wrong");
	   	$(".sameInfo").text("");
	};
	
	var initTable = function(){
		$.get("/sva/showParams/api/getTableData?t="+Math.random(),function(data){
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
							"mData": "showId"
						},
    					{ 
    						"aTargets": [1],
    						"mData": "storeName1"
    					},
    					{ 
    						"aTargets": [2],
    						"mData": "floorName1"
    					},
    					{ 
    						"aTargets": [3],
    						"mData": "storeName2"
    					},
    					{ 
    						"aTargets": [4],
    						"mData": "floorName2"
    					},
    					{ 
    						"aTargets": [5],
    						"mData": "storeName3"
    					},
    					{ 
    						"aTargets": [6],
    						"mData": "floorName3"
    					},
    					{ 
    						"aTargets": [7],
    						"bVisible": false,
    						"mData": "storeName4"
    					},
    					{ 
    						"aTargets": [8],
    						"bVisible": false,
    						"mData": "floorName4"
    					},
    					{ 
    						"aTargets": [9],
    						"bVisible": false,
    						"mData": "storeName5"
    					},
    					{ 
    						"aTargets": [10],
    						"bVisible": false,
    						"mData": "floorName5"
    					},
    					{ 
    						"aTargets": [11],
    						"bVisible": false,
    						"mData": "storeName6"
    					},
    					{ 
    						"aTargets": [12],
    						"bVisible": false,
    						"mData": "floorName6"
    					},
    					{ 
    						"aTargets": [13],
    						"bVisible": false,
    						"mData": "storeName7"
    					},
    					{ 
    						"aTargets": [14],
    						"bVisible": false,
    						"mData": "floorName7"
    					},
    					{ 
    						"aTargets": [15],
    						"bVisible": false,
    						"mData": "storeName8"
    					},
    					{ 
    						"aTargets": [16],
    						"bVisible": false,
    						"mData": "floorName8"
    					},
    					{ 
    						"aTargets": [17],
    						"mData": "customParam"
    					},
    					{ 
    						"aTargets": [18],
    						"mData": "updateTime"
    					},
    					{ 
    						"aTargets": [19],
    						"bVisible": true,
    						"mData": "startStatisticTime"
    					},
    					{ 
    						"aTargets": [20],
    						"bVisible": true,
    						"mData": "fakeRatio"
    					},
    					{ 
    						"aTargets": [21],
    						"bVisible": true,
    						"mData": "periodHeatmap"
    					},
    					{
    	                    "aTargets": [22],
    	                    "bSortable": false,
    	                    "bFilter": false,
    	                    "mData": function(source, type, val) {
    	                        return "";
    	                    },
    	                    "mRender": function ( data, type, full ) {
    	                    	var html = "" +
    	                    		'<input type="button" style="width: 53px;height:30px;font-size: 13px;font-family:inherit;" data-type="edit" data-mapType="'+full.mapType+'" data-id="'+full.floorid+'" value="'+i18n_edit+'">' +
    	                    		'<input type="button" style="width: 53px;height:30px;font-size: 13px;font-family:inherit;" data-type="del" data-mapType="'+full.mapType+'" id="'+full.id+'" data-id="'+full.id+'" data-floorno="'+full.floorNo+'" value="'+i18n_delete+'">';
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
	};
	
	var initDropdown = function() {
		// 下拉列表初始化
		$("#placeSel1").chosen();
		$("#placeSel2").chosen();
		$("#placeSel3").chosen();
		$("#placeSel4").chosen();
		$("#placeSel5").chosen();
		$("#placeSel6").chosen();
		$("#placeSel7").chosen();
		$("#placeSel8").chosen();
		$("#zSel1").chosen();
		$("#zSel2").chosen();
		$("#zSel3").chosen();
		$("#zSel4").chosen();
		$("#zSel5").chosen();
		$("#zSel6").chosen();
		$("#zSel7").chosen();
		$("#zSel8").chosen();
		$("#periodSel").chosen();
		// 商场列表更新
		$.get("/sva/store/api/getData?t="+Math.random(),function(data){
			if(!data.error){
				updateList("placeSel1",data.data,function(){$('#placeSel1').trigger("liszt:updated");});
				updateList("placeSel2",data.data,function(){$('#placeSel2').trigger("liszt:updated");});
				updateList("placeSel3",data.data,function(){$('#placeSel3').trigger("liszt:updated");});
				updateList("placeSel4",data.data,function(){$('#placeSel4').trigger("liszt:updated");});
				updateList("placeSel5",data.data,function(){$('#placeSel5').trigger("liszt:updated");});
				updateList("placeSel6",data.data,function(){$('#placeSel6').trigger("liszt:updated");});
				updateList("placeSel7",data.data,function(){$('#placeSel7').trigger("liszt:updated");});
				updateList("placeSel8",data.data,function(){$('#placeSel8').trigger("liszt:updated");});
			}
		});
		// 时间段列表更新
		var options = '';
		for ( var i = 1; i <= 60; i++) {
			if(i==5){
    			options += '<option class="addoption" selected value="' + i + '">' + i + '</option>';    				
			}else{
    			options += '<option class="addoption" value="' + i + '">' + i + '</option>';    				
			}
		}
		$('#periodSel').empty();
		$('#periodSel').append(options);
		$('#periodSel').trigger("liszt:updated");
	};		
	
	var showDate = function(id){
		WdatePicker({
			el : document.getElementById(id),
			lang : i18n_language,
			isShowClear : false,
			isShowToday:false,
			readOnly : true,
			dateFmt : 'HH:00:00',
			maxDate : '23:00:00',
			skin : "twoer"
		});
	};
	
	var bindClickEvent = function(){
		// 地点下拉列表修改 触发楼层下拉列表变化
		$("#placeSel1").on("change", function(){
			var placeId = $("#placeSel1").val();
			updateFloorByStoreId(placeId, "zSel1", null);
		});
		$("#placeSel2").on("change", function(){
			var placeId = $("#placeSel2").val();
			updateFloorByStoreId(placeId, "zSel2", null);
		});
		$("#placeSel3").on("change", function(){
			var placeId = $("#placeSel3").val();
			updateFloorByStoreId(placeId, "zSel3", null);
		});
		$("#placeSel4").on("change", function(){
			var placeId = $("#placeSel4").val();
			updateFloorByStoreId(placeId, "zSel4", null);
		});
		$("#placeSel5").on("change", function(){
			var placeId = $("#placeSel5").val();
			updateFloorByStoreId(placeId, "zSel5", null);
		});
		$("#placeSel6").on("change", function(){
			var placeId = $("#placeSel6").val();
			updateFloorByStoreId(placeId, "zSel6", null);
		});
		$("#placeSel7").on("change", function(){
			var placeId = $("#placeSel7").val();
			updateFloorByStoreId(placeId, "zSel7", null);
		});
		$("#placeSel8").on("change", function(){
			var placeId = $("#placeSel8").val();
			updateFloorByStoreId(placeId, "zSel8", null);
		});
		
		$("#add").on("click",function(e){
        	clearInput();
        	$("#editBox").show();
        });
		
		$("#cancel").on("click",function(e){
			$(".demoform").Validform().resetForm();
			clearInput();
			$("#editBox").hide();
		});
		
		//  确认按钮点击  触发热力图刷新    		
		$('#confirm').click(function(e){
			var check = validForm.check(false);
          	if (!check) {
				return false;
			}
			var placeId1 = $("#placeSel1").val();
			var placeId2 = $("#placeSel2").val();
			var placeId3 = $("#placeSel3").val();
			var placeId4 = $("#placeSel4").val();
			var placeId5 = $("#placeSel5").val();
			var placeId6 = $("#placeSel6").val();
			var placeId7 = $("#placeSel7").val();
			var placeId8 = $("#placeSel8").val();
			var floorSel1 = $("#zSel1").val();
			var floorSel2 = $("#zSel2").val();
			var floorSel3 = $("#zSel3").val();
			var floorSel4 = $("#zSel4").val();
			var floorSel5 = $("#zSel5").val();
			var floorSel6 = $("#zSel6").val();
			var floorSel7 = $("#zSel7").val();
			var floorSel8 = $("#zSel8").val();
			
			var densitySel1 = $("#densitySel1").val();
			var radiusSel1 = $("#radiusSel1").val();
			var densitySel2 = $("#densitySel2").val();
			var radiusSel2 = $("#radiusSel2").val();
			var densitySel3 = $("#densitySel3").val();
			var radiusSel3 = $("#radiusSel3").val();
			var densitySel4 = $("#densitySel4").val();
			var radiusSel4 = $("#radiusSel4").val();
			var densitySel5 = $("#densitySel5").val();
			var radiusSel5 = $("#radiusSel5").val();
			var densitySel6 = $("#densitySel6").val();
			var radiusSel6 = $("#radiusSel6").val();
			var densitySel7 = $("#densitySel7").val();
			var radiusSel7 = $("#radiusSel7").val();
			var densitySel8 = $("#densitySel8").val();
			var radiusSel8 = $("#radiusSel8").val();
			
			var id = $("#idHidden").val();
			// 局点id
			var showId = $("#showId").val();
			// 系数
		    var fakeRatio = $("#coefficient").val();
		    // 时间段
		    var periodHeatmap = $("#periodSel").val();
		    // 开始时间
			var startStatisticTime = $("#select_time_begin_tab1").val();
			// 自定义参数
		    var customParam = {};
		    var domParams = $("#paramTable tr.info");
		    for(var i = 0; i < domParams.length; i++){
		    	var trDom = domParams[i];
		    	var tdDom = $(trDom).children();
		    	customParam[tdDom[1].innerText] = tdDom[2].innerText;
		    }

			//修改参数更新数据库
			var param ={
					id:id,
					densitySel1:densitySel1,
					radiusSel1:radiusSel1,
					densitySel2:densitySel2,
					radiusSel2:radiusSel2,
					densitySel3:densitySel3,
					radiusSel3:radiusSel3,
					densitySel4:densitySel4,
					radiusSel4:radiusSel4,
					densitySel5:densitySel5,
					radiusSel5:radiusSel5,
					densitySel6:densitySel6,
					radiusSel6:radiusSel6,
					densitySel7:densitySel7,
					radiusSel7:radiusSel7,
					densitySel8:densitySel8,
					radiusSel8:radiusSel8,
					storeId1 : placeId1,
					mapId1 : floorSel1,
					showId:showId,
					fakeRatio:fakeRatio,
					periodHeatmap:periodHeatmap,
					startStatisticTime:startStatisticTime,
					customParam:JSON.stringify(customParam)
            	};
			if(placeId2 != " " && floorSel2 != " "){
				param.storeId2 = placeId2;
				param.mapId2 = floorSel2;
			}
			if(placeId3 != " " && floorSel3 != " "){
				param.storeId3 = placeId3;
				param.mapId3 = floorSel3;
			}
			if(placeId4 != " " && floorSel4 != " "){
				param.storeId4 = placeId4;
				param.mapId4 = floorSel4;
			}
			if(placeId5 != " " && floorSel5 != " "){
				param.storeId5 = placeId5;
				param.mapId5 = floorSel5;
			}
			if(placeId6 != " " && floorSel6 != " "){
				param.storeId6 = placeId6;
				param.mapId6 = floorSel6;
			}
			if(placeId7 != " " && floorSel7 != " "){
				param.storeId7 = placeId7;
				param.mapId7 = floorSel7;
			}
			if(placeId8 != " " && floorSel8 != " "){
				param.storeId8 = placeId8;
				param.mapId8 = floorSel8;
			}
			
			$.ajax({
	              "dataType": 'json', 
	              "type": "POST", 
	              "url": "/sva/showParams/api/saveData", 
	              "data": param, 
	              "success": function(data){
	            	  if(data.error){
	            		  $("#info").text(data.error);
	            		  $(".alert").removeClass("alert-info");
	            		  $(".alert").addClass("alert-error");
	            		  $("#alertBox").show();
	            	  }else{
	            		  $("#editBox").hide();
	            		  clearInput();
	            		  initTable();
	            	  }
	              }
	        });
		});

		$("input[data-type='edit']").live("click",function(e){
    		$("#editBox").show();
        	$(".demoform").Validform().resetForm();
        	var row = $(this).parent().parent();
        	var data = oTable.fnGetData(row[0]);
        	$("#placeSel1").val(data.storeId1);
        	$('#placeSel1').trigger("liszt:updated");
			updateFloorByStoreId(data.storeId1, "zSel1", parseInt(data.mapId1));
			
        	if(data.storeId2){
    			$("#placeSel2").val(data.storeId2);
    			$('#placeSel2').trigger("liszt:updated");
    			updateFloorByStoreId(data.storeId2, "zSel2", parseInt(data.mapId2));
            	$(".storeDiv").eq(1).show();
            	$(".storeDiv").eq(1).removeClass("hide");
        	}
        	if(data.storeId3){
        		$("#placeSel3").val(data.storeId3);
    			$('#placeSel3').trigger("liszt:updated");
    			updateFloorByStoreId(data.storeId3, "zSel3", parseInt(data.mapId3));
            	$(".storeDiv").eq(2).show();
            	$(".storeDiv").eq(2).removeClass("hide");
        	}
        	if(data.storeId4){
        		$("#placeSel4").val(data.storeId4);
    			$('#placeSel4').trigger("liszt:updated");
    			updateFloorByStoreId(data.storeId4, "zSel4", parseInt(data.mapId4));
            	$(".storeDiv").eq(3).show();
            	$(".storeDiv").eq(3).removeClass("hide");
        	}
        	if(data.storeId5){
        		$("#placeSel5").val(data.storeId5);
    			$('#placeSel5').trigger("liszt:updated");
    			updateFloorByStoreId(data.storeId5, "zSel5", parseInt(data.mapId5));
            	$(".storeDiv").eq(4).show();
            	$(".storeDiv").eq(4).removeClass("hide");
        	}
        	if(data.storeId6){
        		$("#placeSel6").val(data.storeId6);
    			$('#placeSel6').trigger("liszt:updated");
    			updateFloorByStoreId(data.storeId6, "zSel6", parseInt(data.mapId6));
            	$(".storeDiv").eq(5).show();
            	$(".storeDiv").eq(5).removeClass("hide");
        	}
        	if(data.storeId7){
        		$("#placeSel7").val(data.storeId7);
    			$('#placeSel7').trigger("liszt:updated");
    			updateFloorByStoreId(data.storeId7, "zSel7", parseInt(data.mapId7));
            	$(".storeDiv").eq(6).show();
            	$(".storeDiv").eq(6).removeClass("hide");
        	}
        	if(data.storeId8){
        		$("#placeSel8").val(data.storeId8);
    			$('#placeSel8').trigger("liszt:updated");
    			updateFloorByStoreId(data.storeId8, "zSel8", parseInt(data.mapId8));
            	$(".storeDiv").eq(7).show();
            	$(".storeDiv").eq(7).removeClass("hide");
        	}
			
			$("#densitySel1").val(data.densitySel1);
			$("#radiusSel1").val(data.radiusSel1);
			$("#densitySel2").val(data.densitySel2);
			$("#radiusSel2").val(data.radiusSel2);
			$("#densitySel3").val(data.densitySel3);
			$("#radiusSel3").val(data.radiusSel3);
			$("#densitySel4").val(data.densitySel4);
			$("#radiusSel4").val(data.radiusSel4);
			$("#densitySel5").val(data.densitySel5);
			$("#radiusSel5").val(data.radiusSel5);
			$("#densitySel6").val(data.densitySel6);
			$("#radiusSel6").val(data.radiusSel6);
			$("#densitySel7").val(data.densitySel7);
			$("#radiusSel7").val(data.radiusSel7);
			$("#densitySel8").val(data.densitySel8);
			$("#radiusSel8").val(data.radiusSel8);
			
			$("#idHidden").val(data.id);
			// 局点id
			$("#showId").val(data.showId);
			// 系数
		    $("#coefficient").val(data.fakeRatio);
		    // 时间段
		    $("#periodSel").val(data.periodHeatmap);
		    $('#periodSel').trigger("liszt:updated");
		    // 开始时间
		    $("#select_time_begin_tab1").val(data.startStatisticTime);
			// 自定义参数
		    var customParam = JSON.parse(data.customParam);
		    for(var k in customParam){
		    	var newTr = "<tr class='info'><td><a data-type='delParam' class='btn btn-danger'>"+i18n_deleteCustomParam+"</a></td><td>"+k+"</td><td>"+customParam[k]+"</td></tr>";
				$("#optTr").before(newTr);
		    }
			
    		$(".sameInfo").removeClass("Validform_wrong");
    		$(".sameInfo").text("");
    		
    		$("#editBox").show();
        });
		
		$("input[data-type='del']").live("click",function(e){
        	if(confirm(i18n_deleteInfo)){
            	var id = $(this).data("id");	           	 
        		$.post("/sva/showParams/api/deleteData",{id:id},function(data){
        			if(data.error){
        				$("#info").text(data.error);
            			$(".alert").removeClass("alert-info");
            			$(".alert").addClass("alert-error");
            			$("#alertBox").show();
	           		}else{
            			initTable();
	           		}
        		});
        	}
        });
		
		// 添加商场按钮点击，多显示一个商场供用户使用，最多显示8个
		$("#addStore").on("click",function(e){
			$(".storeDiv.hide").first().show();
			$(".storeDiv.hide").first().removeClass("hide");
		});
		
		// 删除商场按钮点击，删除最新一个商场，最多显示8个
		$("#removeStore").on("click",function(e){
			if($(".storeDiv:visible").length>1){
				$(".storeDiv:visible").last().addClass("hide");
				$(".storeDiv:visible").last().hide();
			}
		});
		
		// 添加自定义参数
		$("#addParam").on("click",function(e){
			var k = $("#paramKey").val();
			var v = $("#paramValue").val();
			if(k == "" || v ==""){
				return alert("key or value can not be empty!");
			}
			
			var newTr = "<tr class='info'><td><a data-type='delParam' class='btn btn-danger'>"+i18n_deleteCustomParam+"</a></td><td>"+k+"</td><td>"+v+"</td></tr>";
			$("#optTr").before(newTr);
			
			$("#paramKey").val("");
			$("#paramValue").val("");
		});
		
		// 删除自定义参数
		$("a[data-type='delParam']").live("click", function(e){
			if(confirm(i18n_deleteInfo)){
				$(this).parent().parent().remove();
			}
		});
	};
	
	return {
		init : function() {
			initTable();
			initDropdown();
			bindClickEvent();
		},
		showDate: function(id){
			WdatePicker({
				el : document.getElementById(id),
				lang : i18n_language,
				isShowClear : false,
				isShowToday:false,
				readOnly : true,
				dateFmt : 'HH:00:00',
				maxDate : '23:00:00',
				skin : "twoer"
			});
		},
	};

}();