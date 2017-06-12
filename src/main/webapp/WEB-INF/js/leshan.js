var placeId = 72;
var floorNo = 725001;
var time = 1000;


var refreshHeatmapData = function() {
	//var times = $("#time").val($.cookie("times"));	
	var times = $.cookie("times");
	$.post("/sva/heatmap/api/getData?t="+Math.random(), {floorNo : floorNo,times:times}, function(data) {
		if (!data.error) {
			if (data.data && data.data.length > 0) {
				// var points = {max:1,data:dataFilter(data)};
				var points = dataFilter(data.data, origX, origY, scale,
						imgWidth, imgHeight, coordinate, imgScale);
				var dataObj = {
					max : pointVal,
					min : 1,
					data : points
				};
				heatmap.setData(dataObj);
				$("#legend").show();
			}else{
				var canvas=document.getElementsByTagName('canvas')[0];
				var ctx=canvas.getContext('2d');
				ctx.clearRect(0,0,imgWidth,imgHeight);
				$("#legend").hide();
			}
			$("#count").text(data.data.length);
		}
		timer = setTimeout("refreshHeatmapData();", 4000);
	});
};



var dataFilter = function(data, xo, yo, scale, width, height, coordinate,
		imgScale) {
	var list = [];
	xo = parseFloat(xo);
	yo = parseFloat(yo);
	scale = parseFloat(scale);
	switch (coordinate){
	case "ul":
		for ( var i in data) {
			var point = {
				x : (data[i].x / 10 * scale + xo * scale) / imgScale+Math.random()/10,
				y : (data[i].y / 10 * scale + yo * scale) / imgScale+Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "ll":
		for ( var i in data) {
			var point = {
				x : (data[i].x / 10 * scale + xo * scale) / imgScale+Math.random()/10,
				y : height - (data[i].y / 10 * scale + yo * scale) / imgScale+Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "ur":
		for ( var i in data) {
			var point = {
				x : width - (data[i].x / 10 * scale + xo * scale) / imgScale+Math.random()/10,
				y : (data[i].y / 10 * scale + yo * scale) / imgScale+Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "lr":
		for ( var i in data) {
			var point = {
				x : width - (data[i].x / 10 * scale + xo * scale) / imgScale+Math.random()/10,
				y : height - (data[i].y / 10 * scale + yo * scale) / imgScale+Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	}

	return list;
};

var calImgSize = function(width, height) {
	var newWidth, newHeight, imgScale;
	var divWidth = parseInt($("#divCon").css("width").slice(0, -2));

	if (divWidth / 600 > width / height) {
		newHeight = 600;
		imgScale = height / newHeight;
		newWidth = width / imgScale;
	} else {
		newWidth = divWidth;
		imgScale = width / newWidth;
		newHeight = height / imgScale;
	}

	return [ imgScale, newWidth, newHeight ];
};

var Leshan = function() {

	var initHeatmap = function(floorNo) {
		$("#mapContainer").css("background-image", "");
		$("#heatmap").empty();
		$.post("/sva/heatmap/api/getMapInfoByPosition", {
			floorNo : floorNo
		}, function(data) {
			if (!data.error) {
				if (data.bg) {
					// 全局变量赋值
					origX = data.xo;
					origY = data.yo;
					bgImg = data.bg;
					bgImgWidth = data.bgWidth;
					bgImgHeight = data.bgHeight;
					scale = data.scale;
					coordinate = data.coordinate;
					// 设置背景图片
					var bgImgStr = "url(../upload/" + bgImg + ")";
					var imgInfo = calImgSize(bgImgWidth, bgImgHeight);
					imgScale = imgInfo[0];
					imgWidth = imgInfo[1];
					imgHeight = imgInfo[2];
					console.log(imgInfo);
					$("#mapContainer").css({
						"width" : imgWidth + "px",
						"height" : imgHeight + "px",
						"background-image" : bgImgStr,
						"background-size" : imgWidth + "px " + imgHeight + "px",
						"margin" : "0 auto"
					});

					configObj.onExtremaChange = function(data) {
						updateLegend(data);
					};
					var times = $.cookie("times");
				//	var times = $("#time").val($.cookie("times"));
					heatmap = h337.create(configObj);
					$.post("/sva/heatmap/api/getData?t="+Math.random(), {
						floorNo : floorNo,times:times
					}, function(data) {
						if (!data.error) {
							if (data.data && data.data.length > 0) {
								// var points = {max:1,data:dataFilter(data)};
								var points = dataFilter(data.data, origX,
										origY, scale, imgWidth, imgHeight,
										coordinate, imgInfo[0]);
								var dataObj = {
									max : pointVal,
									min : 1,
									data : points
								};
								heatmap.setData(dataObj);
								$("#legend").show();
							}
							$(".countInfo").show();
							$("#count").text(data.data.length);
						}
					});
					clearTimeout(timer);
					timer = setTimeout("refreshHeatmapData();", 4000);
					// refreshHeatmapData();
				}
			}
		});
	};

	var removeOption = function(renderId) {
		$('#' + renderId + ' .addoption').remove().trigger("liszt:updated");
	};

	/* legend code */
	var updateLegend = function(data) {

		if (data.max==1)
		{
			$("#minup").popover("destroy") ;
			$("#max").popover('destroy') ;
			$("#min").popover('destroy') ;
			$("#maxup").popover('destroy') ;
			$("#max").html(i18n_heatmap_max);
			$("#max").css("color","purple");
			$("#maxup").html(i18n_heatmap_maxup);
			$("#maxup").css("color","purple");
			$("#minup").html(i18n_heatmap_minup);
			$("#minup").css("color","purple");
			$("#min").html(i18n_heatmap_min);
			$("#min").css("color","purple");
			var htmlstrmin3 = '<i style="color:rgba(0,0,255,1);" class="icon-male"></i>'+i18n_heatmap_min1+data.max;
			var option3 = {html:true,trigger:"hover",content:htmlstrmin3,placement:"top"};
			var htmlstrmin2 = '<i style="color:rgba(73,255,0,1);" class="icon-male"></i><i style="color:rgba(73,255,0,1);" class="icon-male"></i>'+i18n_heatmap_minup2+Math.round(((data.max-data.min)/3)+1);
			var option2 = {html:true,trigger:"hover",content:htmlstrmin2,placement:"top"};
			var htmlstrmin = '<i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i>'+i18n_heatmap_maxup4+data.max;
			var htmlstrmin1 = '<i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i>'+i18n_heatmap_maxup3+Math.round(((data.max-data.min)/3)+1);
			var option = {html:true,trigger:"hover",content:htmlstrmin,placement:"top"};
			var option1 = {html:true,trigger:"hover",content:htmlstrmin1,placement:"top"};
			$("#max").popover(option1);
			$("#min").popover(option3);
			$("#maxup").popover(option);
			$("#minup").popover(option2);
		}
		if (data.max==2)
		{
			$("#minup").popover("destroy") ;
			$("#max").popover('destroy') ;
			$("#min").popover('destroy') ;
			$("#maxup").popover('destroy') ;
			$("#max").html(i18n_heatmap_max);
			$("#max").css("color","purple");
			$("#maxup").html(i18n_heatmap_maxup);
			$("#maxup").css("color","purple");
			$("#minup").html(i18n_heatmap_minup);
			$("#minup").css("color","purple");
			$("#min").html(i18n_heatmap_min);
			$("#min").css("color","purple");
			var htmlstrmin3 = '<i style="color:rgba(0,0,255,1);" class="icon-male"></i>'+i18n_heatmap_min1+data.min;
			var option3 = {html:true,trigger:"hover",content:htmlstrmin3,placement:"top"};
			var htmlstrmin2 = '<i style="color:rgba(73,255,0,1);" class="icon-male"></i><i style="color:rgba(73,255,0,1);" class="icon-male"></i>'+i18n_heatmap_minup2+Math.round(((data.max-data.min)/3)+1);
			var option2 = {html:true,trigger:"hover",content:htmlstrmin2,placement:"top"};
			var htmlstrmin = '<i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i>'+i18n_heatmap_maxup4+data.max;
			var htmlstrmin1 = '<i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i>'+i18n_heatmap_max3+Math.round(((data.max-data.min)/3)+1);
			var option = {html:true,trigger:"hover",content:htmlstrmin,placement:"top"};
			var option1 = {html:true,trigger:"hover",content:htmlstrmin1,placement:"top"};
			$("#max").popover(option1);
			$("#min").popover(option3);
			$("#maxup").popover(option);
			$("#minup").popover(option2);
		}
		if (data.max==3)
		{
			$("#minup").popover("destroy") ;
			$("#max").popover('destroy') ;
			$("#min").popover('destroy') ;
			$("#maxup").popover('destroy') ;
			$("#max").html(i18n_heatmap_max);
			$("#max").css("color","purple");
			$("#maxup").html(i18n_heatmap_maxup);
			$("#maxup").css("color","purple");
			$("#minup").html(i18n_heatmap_minup);
			$("#minup").css("color","purple");
			$("#min").html(i18n_heatmap_min);
			$("#min").css("color","purple");
			var htmlstrmin3 = '<i style="color:rgba(0,0,255,1);" class="icon-male"></i>'+i18n_heatmap_min1+data.min;
			var option3 = {html:true,trigger:"hover",content:htmlstrmin3,placement:"top"};
			var htmlstrmin2 = '<i style="color:rgba(73,255,0,1);" class="icon-male"></i><i style="color:rgba(73,255,0,1);" class="icon-male"></i>'+i18n_heatmap_minup2+Math.round(((data.max-data.min)/3)+1);
			var option2 = {html:true,trigger:"hover",content:htmlstrmin2,placement:"top"};
			var htmlstrmin = '<i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i>'+i18n_heatmap_maxup4+data.max;
			var htmlstrmin1 = '<i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i>'+i18n_heatmap_max3+Math.round(((data.max-data.min)/3)*2+1);
			var option = {html:true,trigger:"hover",content:htmlstrmin,placement:"top"};
			var option1 = {html:true,trigger:"hover",content:htmlstrmin1,placement:"top"};
			$("#max").popover(option1);
			$("#min").popover(option3);
			$("#maxup").popover(option);
			$("#minup").popover(option2);
		}
		if (data.max>3) {
			$("#minup").popover("destroy") ;
			$("#max").popover('destroy') ;
			$("#min").popover('destroy') ;
			$("#maxup").popover('destroy') ;
			$("#max").html(i18n_heatmap_max);
			$("#max").css("color","purple");
			$("#maxup").html(i18n_heatmap_maxup);
			$("#maxup").css("color","purple");
			$("#minup").html(i18n_heatmap_minup);
			$("#minup").css("color","purple");
			$("#min").html(i18n_heatmap_min);
			$("#min").css("color","purple");
			var htmlstrmin3 = '<i style="color:rgba(0,0,255,1);" class="icon-male"></i>'+i18n_heatmap_min1+data.min;
			var option3 = {html:true,trigger:"hover",content:htmlstrmin3,placement:"top"};
			var htmlstrmin2 = '<i style="color:rgba(73,255,0,1);" class="icon-male"></i><i style="color:rgba(73,255,0,1);" class="icon-male"></i>'+i18n_heatmap_minup2+Math.round(((data.max-data.min)/3)+1);
			var option2 = {html:true,trigger:"hover",content:htmlstrmin2,placement:"top"};
			var htmlstrmin = '<i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i>'+i18n_heatmap_maxup4+data.max;
			var htmlstrmin1 = '<i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i>'+i18n_heatmap_max3+Math.round(((data.max-data.min)/3)*2+1);
			var option = {html:true,trigger:"hover",content:htmlstrmin,placement:"top"};
			var option1 = {html:true,trigger:"hover",content:htmlstrmin1,placement:"top"};
			$("#max").popover(option1);
			$("#min").popover(option3);
			$("#maxup").popover(option);
			$("#minup").popover(option2);
		}

	};
	var showInfo = function(placeId, startTime, endTime){
		$.post("/sva/linemap/api/getInfoData",{placeId:placeId,startTime:startTime,endTime:endTime},function(data){
			if(!data.error){
				if (data.total==0){
					$("#total").text(0);
					$("#maxDay").text("");
					$("#maxTime").text("");		
				}else
					{
					var md = _.pluck(data.maxDay,"time");
					var mt = _.pluck(data.maxHour,"time");
					if(md!=""){
					var	md1 = md[0].split(" ")[0];
					$("#maxDay").text(md1);
					}else
						{
						$("#maxDay").text("");
						}
					if(mt!=""){
					var	mt1 = mt[0].split(".")[0];
					$("#maxTime").text(mt1);		
					}else{
						$("#maxTime").text("");
					}
					$("#total").text(data.total);
//					$("#maxDay").text(md);
//					$("#maxTime").text(mt);				
					}
					
			}
		});
	};
	
	var showChart = function(placeId, startTime, endTime, dim){
		$.post("/sva/linemap/api/getChartData",{placeId:placeId,startTime:startTime,endTime:endTime,dim:dim},function(data){
			if(!data.error){
				var startTime = startTime;
				var endTime = endTime;
				var xTitle = _.pluck(data.data,"time");
				var yVal = _.pluck(data.data,"number");
				//$("#chart").empty();
				
				var myChart = echarts.init(document.getElementById("chart"));
				var option = {
//				    title : {
//				        text: i18n_title
//				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            dataView : {
				            	show: true, 
				            	title : i18n_dataview,
				            	readOnly: true,
				            	lang: [i18n_dataview, i18n_close, i18n_refresh]
				            },
				            saveAsImage : {
				            	show: true,
				            	title : i18n_saveimg
				            }
				        }
				    },
				    calculable : false,
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : false,
				            data : xTitle
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            axisLabel : {
				                formatter: '{value} '+i18n_person
				            }
				        }
				    ],
				    series : [
				        {
				            name:i18n_tag,
				            type:'line',
				            data: yVal,
				            markPoint : {
				                data : [
				                    {type : 'max', name: i18n_max}
				                   // ,{type : 'min', name: i18n_min}
				                ]
				            },
				            markLine : {
				                data : [
				                    {type : 'average', name: i18n_avg}
				                ]
				            }
				        }
				    ]
				};
				
				myChart.setOption(option);
			}
		});
	};

	
	
	function GetDateStr(AddDayCount) {
		var dd = new Date();
		//获取AddDayCount天后的日期
		dd.setDate(dd.getDate()+AddDayCount);
		var y = dd.getFullYear();
		//获取当前月份的日期
		var m = dd.getMonth()+1;
		var d = dd.getDate();
		return y+"-"+m+"-"+d + " 00:00:00";
	} 
	return {

		bindClickEvent : function() {
			
			// 确认按钮点击 触发热力图刷新
			$('#confirm').click(function(e) {
				pointVal = 3;
				configObj.radius = 50;
				$("#floorSel").blur();
				$("#mainContent").show();
				initHeatmap(floorNo);
				$.cookie("place", placeId, { expires: 30});
				$.cookie("floor", floorNo, { expires: 30});
				$.cookie("times", time, { expires: 30});

			});
			
    		$('#confirm2').click(function(e){
    			startTime = GetDateStr(-7);
    			endTime = GetDateStr(0);
    			if(placeId && startTime && endTime){
    				$(".chartArea").show();
    				showInfo(placeId, startTime, endTime);
    				showChart(placeId, startTime, endTime,1);
    			}
    			
    			$("#hour").click();
    		});
    		
    		$("#hour").click(function(e){
    			startTime = GetDateStr(-7);
    			endTime = GetDateStr(0);
    			
				showChart(placeId, startTime, endTime, 1);
    		});
    		
    		$("#day").click(function(e){
    			startTime = GetDateStr(-7);
    			endTime = GetDateStr(0);
    			
				showChart(placeId, startTime, endTime, 2);
    		});
		}

	};

}();
