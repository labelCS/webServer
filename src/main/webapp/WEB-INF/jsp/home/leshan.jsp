<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SVA APP demo</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">

<%@ include file="../shared/importCss.jsp"%>
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="<c:url value='/css/pages/heatmap.css'/>" rel="stylesheet"
	type="text/css" />
<link href="<c:url value='/plugins/jquery-chosen/chosen.css'/>"
	rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->
<style type="text/css">
<<<<<<< HEAD
body{
	background-color:transparent !important;
}
.countInfo {
	width: 120px;
	height: 30px;
	background: rgba(255,255,255,0.6); 
	z-index: 10;
	filter: alpha(opacity = 30);
	opacity: 0.5;
	padding: 0 5px;
	padding-top: 5px;
	border-radius: 10px;
	color:#15A4FA;
	border:1px solid #D6D2D2;
	margin-bottom:10px;
}
.legend-area {
	background: #DCFAFF; padding: 10px; outline: black solid 2px; right: 0px; bottom: 0px; line-height: 1em; position: absolute;
}
#min {
	float: left;
}
#max {
	float: right;
}
.tip {
	background: rgba(0, 0, 0, 0.8); padding: 5px; left: 0px; top: 0px; color: white; line-height: 18px; font-size: 14px; display: none; position: absolute;
}
.demo-wrapper {
	position: relative;
}
#time{
	vertical-align: top; 
	padding: 0px 3px 0px 4px; 
	height: 26px; 
	width:30px;
}
#confirm{
	vertical-align: top; 
	padding: 0px 3px 0px 4px; 
	height: 26px; 
	margin-top: 1px;
}
/*add*/
body{
	background-color:transparent !important;
}
.countInfo {
	width: 200px;
}
.chartArea > .infoArea {
	width:100%;
	height:102px;
	float: left;
}
.chartArea > .infoArea > div {
	width:30%;
	height:100%;
	float:left;
	border:1px solid #ddd;
}
.chartArea > .infoArea > div+div {
	margin-left: 20px;
}
.chartArea > .infoArea > .infoBox > div {
	text-align:center;
	height: 35px;
	line-height: 35px;
	overflow: hidden;
	font-size: 35px;
	color: #108ec8;
}
.chartArea > .infoArea > .infoBox > .infoTop {
	text-align:center;
	color: #717e9a;
	line-height: 30px;
	margin-top: 10px;
	font-size: 15px;
	word-wrap: break-word;
}
.chartBox {
	margin-top: 20px;
	clear:left;
}
.titleStyle {
	font-size: 16px;
	margin: 5px 0;
	padding: 5px 10px;
}
.chartBox > .btn-group {
	top: 25px;
}
</style>

<link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<form class="demoform" onkeydown= "if(event.keyCode==13)return false;">
					<input style="display:none;" id="confirm" type="button" value="<spring:message code="common_confirm" />">
				</form>
				<div class="countInfo">
					<spring:message code="heatmap_count">
						<label id="count"
							style="float: right; margin-right: 10px; margin-top: 2px;"></label>
					</spring:message>
				</div>
				<div id="divCon" style="text-align: center; height: 600px;">
					<div id="mapContainer" class="demo-wrapper">
						<div id="heatmap" class="heatmap"></div>
						<div id="legend" class="legend-area hide">
							<div  style="width: 100%;">
				                <div id="min" style="background-color: rgba(0,0,255,1);width: 90px;float:left;text-align: center;padding: 4px 0"></div>
				                <div id ="minup" style="background-color: rgba(73,255,0,1);width: 90px;float:left;text-align: center;padding: 4px 0"></div>
				                <div id ="max" style="background-color: rgba(251,255,0,1);width: 90px;float:left;text-align: center;padding: 4px 0"></div>
				                <div id="maxup" style="background-color: rgba(255,40,0,1);width: 100px;float:left;text-align: center;padding: 4px 0"></div>
			            	</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid" style="margin-top:50px;">
			<div class="span12">
				<form class="demoform2">
					<div class="span1" style="position:absolute;">
						<input style="display:none;" type="button" value="<spring:message code="common_confirm" />" id="confirm2" style="vertical-align: top; margin-top: 0;">
					</div>
					<span  id="msgdemo2"></span>
				</form>	
				<div class="chartArea hide">
					<div class="infoArea">
						<div class="infoBox fleft">
							<div class="infoTop"><spring:message code="linemap_info_total" /></div>
							<div style="font-size: 25px"  class="infoNumber" id="total"></div>
						</div>
						<div class="infoBox">
							<div class="infoTop"><spring:message code="linemap_info_maxDay" /></div>
							<div style="font-size: 25px"  class="infoNumber" id="maxDay"></div>
						</div>
						<div class="infoBox fright">
							<div class="infoTop"><spring:message code="linemap_info_maxTime" /></div>
							<div  style="font-size: 25px"  class="infoNumber" id="maxTime"></div>
						</div>
					</div>
					<div class="chartBox" id="chartBox" style="margin-bottom: 10px; width:100%;">
						<div class="titleStyle"><spring:message code="linemap_chart_title" /></div>
						<div class="btn-group"  data-toggle="buttons-radio">
							<button id="hour" class="btn active" value="1"><spring:message code="linemap_info_hour" /></button>
							<button id="day" class="btn" value="2"><spring:message code="linemap_info_day" /></button>
						</div>
						<div>
							<div id="chart" style="height:400px"></div>
						</div>
					</div>
				</div>	
			</div>
		</div>
	</div>
</body>

<!--  
<body class="page-header-fixed">

	<div class="page-container">

			<div class="row-fluid">
			<form class="demoform" onkeydown= "if(event.keyCode==13)return false;">
				<input style="display:none;" id="confirm" type="button" value="<spring:message code="common_confirm" />">
				</form>
			</div>
			<div id="mainContent" class="row-fluid hide">
				<div class="countInfo" style="width: 195px">
					<spring:message code="heatmap_count">
						<label id="count"
							style="float: right; margin-right: 10px; margin-top: 2px;"></label>
					</spring:message>
				</div>
				<div id="divCon" style="text-align: center; height: 600px;">
					<div id="mapContainer" class="demo-wrapper">
						<div id="heatmap" class="heatmap"></div>
						<div id="legend" class="legend-area hide">
							<div  style="width: 100%;">
				                <div id="min" style="background-color: rgba(0,0,255,1);width: 90px;float:left;text-align: center;padding: 4px 0"></div>
				                <div id ="minup" style="background-color: rgba(73,255,0,1);width: 90px;float:left;text-align: center;padding: 4px 0"></div>
				                <div id ="max" style="background-color: rgba(251,255,0,1);width: 90px;float:left;text-align: center;padding: 4px 0"></div>
				                <div id="maxup" style="background-color: rgba(255,40,0,1);width: 100px;float:left;text-align: center;padding: 4px 0"></div>
               			 	</div>
						</div>
					</div>
				</div>
			</div>
			<div class="r-top row-fluid">
			<form class="demoform2">
				<div class="span1">
					<input style="display:none;" type="button" value="<spring:message code="common_confirm" />" id="confirm2" style="vertical-align: top; margin-top: 0;">
				</div>
				<span  id="msgdemo2"></span>
			</form>	
			</div>
			<div class="chartArea hide">

				<div class="infoArea">

					<div class="infoBox fleft">

						<div class="infoTop"><spring:message code="linemap_info_total" /></div>

						<div style="font-size: 25px"  class="infoNumber" id="total"></div>

					</div>

					<div class="infoBox">

						<div class="infoTop"><spring:message code="linemap_info_maxDay" /></div>

						<div style="font-size: 25px"  class="infoNumber" id="maxDay"></div>

					</div>

					<div class="infoBox fright">

						<div class="infoTop"><spring:message code="linemap_info_maxTime" /></div>

						<div  style="font-size: 25px"  class="infoNumber" id="maxTime"></div>

					</div>

				</div>
			<div class="chartBox" style="margin-bottom: 10px">
				<div class="titleStyle"><spring:message code="linemap_chart_title" /></div>
					<div class="btn-group"  data-toggle="buttons-radio">
						<button id="hour" class="btn active" value="1"><spring:message code="linemap_info_hour" /></button>
						<button id="day" class="btn" value="2"><spring:message code="linemap_info_day" /></button>
					</div>
					<div>
						<div id="chart" style="height:400px"></div>
					</div>
			</div>
            </div>
	</div>
	-->
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!-- END FOOTER -->

	<%@ include file="../shared/importJs.jsp"%>
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="<c:url value='/plugins/jquery-chosen/chosen.jquery.js'/>"
		type="text/javascript"></script>
	<script
	src="<c:url value='/plugins/echarts-2.2.5/build/source/echarts-all.js'/>"
	type="text/javascript"></script>
	<script src="<c:url value='/plugins/heatmap.min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/leshan.js'/>" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->

	<script type="text/javascript">
		var i18n_info = '<spring:message code="map_info" />';
		var i18n_heatmap_min = '<spring:message code="heatmap_picture_min" />';
		var i18n_heatmap_minup = '<spring:message code="heatmap_picture_minup" />';
		var i18n_heatmap_max = '<spring:message code="heatmap_picture_max" />';
		var i18n_heatmap_maxup = '<spring:message code="heatmap_picture_maxup" />';
		var i18n_heatmap_min1 = '<spring:message code="heatmap_heat_pictrue1" />';
		var i18n_heatmap_minup2 = '<spring:message code="heatmap_heat_pictrue2" />';
		var i18n_heatmap_max3 = '<spring:message code="heatmap_heat_pictrue3" />';
		var i18n_heatmap_maxup4 = '<spring:message code="heatmap_heat_pictrue4" />';
		var i18n_heatmap_maxup3 = '<spring:message code="heatmap_heat_pictrue3up" />';
		var floorNo, origX, origY, bgImg, scale, coordinate, imgHeight, imgWidth, imgScale, heatmap, timer,floorLoop;
		var pointVal = 1;
		var configObj = {
			container : document.getElementById("heatmap"),
			maxOpacity : .6,
			radius : 20,
			blur : .90,
			backgroundColor : 'rgba(0, 0, 58, 0.1)'
		};
		// we want to display the gradient, so we have to draw it
		var legendCanvas = document.createElement('canvas');
		legendCanvas.width = 100;
		legendCanvas.height = 10;
		var min = document.querySelector('#min');
		var max = document.querySelector('#max');
		var gradientImg = document.querySelector('#gradient');

		var legendCtx = legendCanvas.getContext('2d');
		var gradientCfg = {};

		var demoWrapper = document.querySelector('.demo-wrapper');
		var tooltip = document.querySelector('.tip');

		var i18n_delete = '<spring:message code="common_delete" />',
        i18n_time = '<spring:message code="linemap_time" />',
        i18n_title = '<spring:message code="linemap_chart_title" />', 
        i18n_title1 = '<spring:message code="barmap_chart_title" />',
        i18n_tag = '<spring:message code="linemap_chart_tag" />',
        i18n_person = '<spring:message code="linemap_chart_person" />', 
        i18n_max = '<spring:message code="linemap_chart_max" />', 
        i18n_min = '<spring:message code="linemap_chart_min" />', 
        i18n_avg = '<spring:message code="linemap_chart_avg" />',
        i18n_dataview = '<spring:message code="common_echart_dataview" />',
        i18n_close = '<spring:message code="common_close" />',
        i18n_refresh = '<spring:message code="common_refresh" />',
        i18n_saveimg = '<spring:message code="common_echart_saveimg" />',
        i18n_visitTime = '<spring:message code="linemap_visitTime" />',
        i18n_visitTitle = '<spring:message code="linemap_visitTime_title" />',
        i18n_language = '<spring:message code="time_language" />';  
		jQuery(document).ready(function() {
			var validForm = $(".demoform").Validform({tiptype:3});
			 $(".demoform2").Validform({       
					tiptype:function(msg,o,cssctl){
		            var objtip=$("#msgdemo2");
		            cssctl(objtip,o.type);
		            objtip.text(msg);
		        },tipSweep:true});
			App.init();
			Leshan.bindClickEvent();
			$("#confirm").click();
			$("#confirm2").click();
			//Heatmap.initHeatmap();
		});

		window.onresize=function(){ location=location }
	</script>
	<!-- END JAVASCRIPTS -->
</body>
</html>