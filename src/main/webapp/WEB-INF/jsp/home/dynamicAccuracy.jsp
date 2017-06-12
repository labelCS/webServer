<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
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
<link
	href="<c:url value='/plugins/data-tables/media/css/demo_table.css'/>"
	rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/pages/statistic.css'/>" rel="stylesheet"
	type="text/css" />		
<style type="text/css">
.popuptext{
	margin-top:50px;
}
.editData{
	-webkit-user-select:text !important;
}
@media(max-width: 768px){
	.span1{
		margin: 0;
	}
	.span6{
		width: auto;
		margin-left: 0;
	}
	.demoform .col-md-6 button{
		float: left;
		margin: 7px 0 0 -60px;
	}
}
</style>
<!-- END PAGE LEVEL PLUGIN STYLES -->

<link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body class="page-header-fixed">

	<%@ include file="../shared/pageHeader.jsp"%>

	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">

		<%@ include file="../shared/sidebarMenu.jsp"%>

		<!-- BEGIN PAGE -->
		<div class="page-content">

			<!-- BEGIN PAGE HEADER-->
			<div>
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB
					<h3 class="page-title"><spring:message code="test_title" /></h3>-->
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="icon-home" style="background-image:none"></i>
							<spring:message code="ceshi_tool" /> <i
							class="icon-angle-right"></i></li>
						<li><spring:message code="dynamic_menu" /></li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<form class="demoform">
				<div class="span3" style="margin-left: 0px">
					<select id="marketSel" name = "marketSelName"  datatype="*"  nullmsg='<spring:message code="map_store_name" />'
						data-placeholder="<spring:message code="heatmap_place" />"
						style="width: 100%;height:26px;padding:2px 0 2px 0;">
						<option value=""></option>
					</select>
				</div>
				<div class="span6">
					<div class="input-append">
						<input id="select_time_begin_tab1" datatype="*"  nullmsg='<spring:message code="all_choose_starttime" />' style="width: 180px" readonly/> 
						<span class="add-on" onclick="dynamicAccuracy.showDate('select_time_begin_tab1');">
							<i class="icon-calendar"></i>
						</span>
					</div>
					-
					<div class="input-append">
						<input id="select_time_end_tab1" datatype="*"  nullmsg='<spring:message code="all_choose_endtime" />' style="width: 180px" readonly/> 
						<span class="add-on" onclick="dynamicAccuracy.showDate('select_time_end_tab1');"> 
							<i class="icon-calendar"></i>
						</span>
					</div>
				</div>
				<div class="span1">
					<input type="button" class="btn btn-primary" value="<spring:message code="common_confirm" />" onclick="dynamicAccuracy.clickComfirm();" id="confirm" style="vertical-align: top;">
				</div>
				<div class="span1" id="msgdemo2"></div>				
				<div class = "col-md-6" style="text-align: right;">
					<button type = "button" class="btn btn-primary" id="exportButton"><spring:message code="sva_daochu" /></button>
				</div>				
			</form>
	       	<ul id = "myTab" class="nav nav-tabs">
	       		<li class="active">
	       			<a href="#pictrue" data-toggle="tab"><spring:message code="cdfli" />
	       			</a>
	       		</li>
	       		<li class>
	       			<a href="#movie" data-toggle="tab"><spring:message code="cdftu" /></a>
	       		</li>
	       	</ul>	
	       	<div class="tab-content show" id = "myTabContent">
	       		<div class="tab-pane fade action in" id = "pictrue"  style="">
					<div class="tableBox">
					<div style="max-width:100%;overflow-x:auto;">
						<table id="table" class="table table-bordered">
							<thead>
								<tr>
									<th>ID</th>
									<th><spring:message code="test_table_title_location" /></th>
									<th><spring:message code="test_table_title_floor" /></th>
									<th style="min-width:81px"><spring:message code="test_table_title_x" /></th>
									<th style="min-width:81px"><spring:message code="test_table_title_y" /></th>
									<th style="min-width:109px"><spring:message code="test_table_title_starttime" /></th>
									<th style="min-width:109px"><spring:message code="test_table_title_endtime" /></th>
									<th><spring:message code="test_table_title_trigger" /></th>
									<th><spring:message code="test_table_title_averDevi" /></th>
									<th><spring:message code="static_maxoffset_title" /></th>
									<th><spring:message code="dynamic_offset_title" /></th>
									<th><spring:message code="test_table_title_detail" /></th>
								</tr>
							</thead>
						</table>
						</div>
					</div>
	       		</div>
       			<div class="tab-pane fade" style="text-align: center;" id = "movie">
		            <div class="chartBox" style="margin-bottom: 10px">
		                <div class="titleStyle"><spring:message code="cdftu" /></div>
		                    <div>
		                        <div id="chart3" style="height:400px;width: 1100px"></div>
		                    </div>
		            </div> 
	       		</div>
	       	</div>				
			<!-- END PAGE HEADER-->



		</div>
		<!-- END PAGE -->
	</div>
	<div class="modal hide fade" id="modal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<h3><spring:message code="test_popup_title" /></h3>
		</div>
		<div class="modal-body">
			<div class="row">
				<div class="span8">
					<div id="chart" class="editData" style="height:400px;"></div>
				</div>
				<div class="span4 offset1 popuptext">
					<table class="table table-bordered table-striped">
			            <tr>
			                <td>
			                  	<spring:message code="test_table_title_location" />
			                </td>
			                <td><label id="place"></label></td>
			            </tr>
			            <tr>
			                <td>
			                  	<spring:message code="test_table_title_floor" />
			                </td>
			                <td><label id="floor"></label></td>
			            </tr>
			            <tr>
			                <td>
			                  	<spring:message code="test_table_title_x" />
			                </td>
			                <td><label id="x"></label></td>
			            </tr>
			            <tr>
			                <td>
			                  	<spring:message code="test_table_title_y" />
			                </td>
			                <td><label id="y"></label></td>
			            </tr>
			            <tr>
			                <td>
			                  	<spring:message code="test_table_title_starttime" />
			                </td>
			                <td><label id="startTime"></label></td>
			            </tr>
			            <tr>
			                <td>
			                  	<spring:message code="test_table_title_endtime" />
			                </td>
			                <td><label id="endTime"></label></td>
			            </tr>
			            <tr>
                            <td>
                                <spring:message code="test_table_title_trigger" />
                            </td>
                            <td><label id="trigger"></label></td>
                        </tr>
			            <tr>
			                <td>
			                  	<spring:message code="test_table_title_averDevi" />
			                </td>
			                <td><label id="avgeOffset"></label></td>
			            </tr>
						<tr>
			                <td>
			                  	<spring:message code="static_maxoffset_title" />
			                </td>
			                <td><label id="maxOffset"></label></td>
			            </tr>
						<tr>
			                <td>
			                  	<spring:message code="dynamic_offset_title" />
			                </td>
			                <td><label id="offset"></label></td>
			            </tr>
			            
		            </table>
				</div>
			</div>
		</div>
		<div class="modal-footer" >
			<a href="#" class="btn"  data-dismiss="modal" aria-hidden="true"><spring:message code="common_close" /></a>
		</div>
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!-- END FOOTER -->

	<%@ include file="../shared/importJs.jsp"%>
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script
		src="<c:url value='/plugins/data-tables/media/js/jquery.dataTables.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/plugins/echarts-2.2.5/build/source/echarts-all.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/plugins/wDatePicker/WdatePicker.js'/>"
		type="text/javascript"></script>		
	<script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/dynamicAccuracy.js'/>" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->

	<script type="text/javascript">
		var oTable;
		var basePath = "<%=basePath%>";
		var i18n_detail ='<spring:message code="test_table_title_detail" />',
			i18n_chart_title = '<spring:message code="test_popup_chart_title" />',
			i18n_chart_tip = '<spring:message code="test_popup_chart_tip" />',
			i18n_minus = '<spring:message code="test_popup_chart_minus" />',
			i18n_plus = '<spring:message code="test_popup_chart_plus" />',
			i18n_offset = '<spring:message code="test_table_title_offset" />',
			i18n_var ='<spring:message code="test_table_title_variance" />',
			i18n_dataview ='<spring:message code="common_echart_dataview" />',
			i18n_saveimg ='<spring:message code="common_echart_saveimg" />',
			i18n_close ='<spring:message code="common_close" />',
			i18n_Invalid ='<spring:message code="test_table_title_failed" />',
			i18n_Valid ='<spring:message code="test_table_title_success" />',
			i18n_common_info ='<spring:message code="common_echart_info" />',
			i18n_accuracy_static ='<spring:message code="test_table_static" />',
	         i18n_daochu ='<spring:message code="sva_daochu" />',
	        i18n_dataview = '<spring:message code="common_echart_dataview" />',
	        i18n_close = '<spring:message code="common_close" />',
	        i18n_refresh = '<spring:message code="common_refresh" />',
	        i18n_saveimg = '<spring:message code="common_echart_saveimg" />',
	        i18n_visitTime = '<spring:message code="linemap_visitTime" />',
	        i18n_visitTitle = '<spring:message code="cdftu_mi" />',
	        i18n_cishu = '<spring:message code="cdftu_cishu" />',
	        i18n_time = '<spring:message code="linemap_time" />',
			i18n_accuracy_dynamic ='<spring:message code="test_table_dynamic" />',
	        i18n_language = '<spring:message code="time_language" />';  
		$(document).ready(function() {
			App.init();
			dynamicAccuracy.initTable();
			dynamicAccuracy.initDropdown();
			$("a[data-type='detail']").live('click', function(e) {
				var data = oTable.fnGetData( this.parentNode.parentNode );
				dynamicAccuracy.initPopupText(data);
				setTimeout(function(){dynamicAccuracy.initPopupChart(data);},500);
				//Accuracy.initPopupChart(data);
			});
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
</html>