<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>SVA APP demo</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<meta name="MobileOptimized" content="320">
	
	<%@ include file="../shared/importCss.jsp"%>
	
	<link rel="shortcut icon" href="favicon.ico" />
	<link href="<c:url value='/plugins/data-tables/media/css/demo_table.css'/>" rel="stylesheet" type="text/css" />
	<style type="text/css">
		.demo-wrapper {
			position: relative;
		}
		.icon-question-sign{
			background-position: 16px 16px;
		}
		div.point {
			position: absolute;
			background-color: #0CD4F7;
			border-radius: 3px;
			width: 6px;
			height: 6px;
		}
		div.svgCotainer {
			position: absolute;
		}	
		.preview_size_fake { /* 该对象只用来在IE下获得图片的原始尺寸，无其它用途 */
			filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);
			height: 1px;
			visibility: hidden;
			overflow: hidden;
			display: none;
		}
		#mapPloy {
			position: absolute;
	}
	</style>
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body id="body" class="page-header-fixed">
	<%@ include file="../shared/pageHeader.jsp"%>
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<%@ include file="../shared/sidebarMenu.jsp"%>
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN PAGE HEADER-->
			<div>
				<div>
					<!-- BEGIN PAGE TITLE & BREADCRUMB
					<h3 class="page-title">
					<spring:message code="message_title" />
					</h3>-->
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="icon-home" style="background-image:none"></i>
							<spring:message code="menu_info_manage" />
							<i class="icon-angle-right"></i>
						</li>
						<li><spring:message code="pet_infomation" /></li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<div class="clearfix"></div>
			<div class="tableBox">
				<div id="alertBox" class="hide">
					<div class="alert">
						<button type="button" class="close" data-dismiss="alert" >&times;</button>
						<div id="info"></div>
					</div>
				</div>
				<form class="form-horizontal demoform"   onkeydown= "if(event.keyCode==13)return false;" enctype="multipart/form-data" method="post" >
					<div id="editBox" class="portlet light-grey hide">
						<div class="portlet-title">
							<div class="caption">
								<i class="icon-edit icon-large" style="background: none"></i><spring:message code="bluemix_config" />
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6" style="padding:10px;">
								<div class="row-fluid">
									<div class="span12" style="outline:0px solid #E6E4E4;padding:20px 0;">

										<div class="control-group">
											<label class="control-label" for="category"><spring:message code="pet_petName" /></label>
											<div class="controls">
												<select datatype="*"  nullmsg='<spring:message code="pet_xuanzeName" />'  style="width: 61%;height: 15%;"id="petName" >
													<option value=""></option>
																																							
												</select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="areaName"><spring:message code="pet_viewRange" /></label>
											<div class="controls">
												<input type="text" nullmsg="<spring:message code="pet_xzviewrange" />" maxlength="100" datatype="password" errormsg="<spring:message code="pet_xzviewrange" />"  style="width: 59%;height: 15%;" name="" id="viewRange"  >
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="areaName"><spring:message code="pet_captureRange" /></label>
											<div class="controls">
												<input type="text" nullmsg="<spring:message code="pet_xzcaputruerange" />" maxlength="100" datatype="password" errormsg="<spring:message code="pet_xzcaputruerange" />"  style="width: 59%;height: 15%;" id="captureRange"  >
											</div>
										</div>

									</div>
								</div>	
							</div>
							
							<div class="span6" style="padding:10px;">
                  				<div class="row-fluid">
                      				<div class="span12" style="outline:0px solid #E6E4E4;padding:20px 0;">	
                      				
										<div class="control-group">
											<label class="control-label" for="areaName"><spring:message code="pet_probability" /></label>
											<div class="controls">
												<input type="text" nullmsg="<spring:message code="pet_xzgailv" />" maxlength="100" datatype="password" errormsg="<spring:message code="pet_xzgailv" />"  style="width: 59%;height: 15%;" name="" id="probability"  >
											</div>
										</div>		
										<div class="control-group">
											<label class="control-label" for="areaName"><spring:message code="pet_count" /></label>
											<div class="controls">
												<input type="text" nullmsg="<spring:message code="pet_xzcount" />" maxlength="100" datatype="password" errormsg="<spring:message code="pet_xzcount" />"  style="width: 59%;height: 15%;" name="" id="count"  >
											</div>
										</div>																												       
										<div class="control-group">
											<div class="controls">
												<input type="hidden" name = "id" id="idid" style="width: 56%; height: 15%;">
											</div>
										</div>
									<div class="control-group">
											<div class="controls">
												<input type="hidden" id="rangeId" style="width: 56%; height: 15%;">
											</div>
										</div>
									<div class="control-group">
											<div class="controls">
												<input type="hidden" id="countId" style="width: 56%; height: 15%;">
											</div>
										</div>		
									<div class="control-group">
											<div class="controls">
												<input type="hidden" id="petNameId" style="width: 56%; height: 15%;">
											</div>
										</div>																														

									</div>
								</div>
							</div>
							
							
						</div>
						<div class="form-actions" style="padding-left: 0px;">
							<div class="row-fluid">
								<div class="span12" style="text-align:center;">
									<input id="confirm" style="height: 30px" type="button" value="<spring:message code="common_confirm" />" class="btn btn-success"> 
									<button id="cancel" style="height: 30px" onclick="hideAdd()" type="button" class="btn"><spring:message code="common_cancel" /></button>
								<span class="sameInfo" ></span>
								</div>
							</div>
						</div>
					</div>
				</form>
				<div>
					<button id="add" style="height: 30px;" type="button" class="btn btn-primary" onclick="addMap()"><spring:message code="common_add" /></button>
					
					</div>  
					
					<table id="table" class="table table-bordered" style="text-align:center">
						<thead>
							<tr>
								<th style="width:100px"><spring:message code="pet_petName" /></th>
								<th style="width:50px"><spring:message code="pet_viewRange" /></th>
								<th style="width:50px"><spring:message code="pet_captureRange" /></th>
								<th style="width:50px"><spring:message code="pet_probability" /></th>
								<th style="width:93px"><spring:message code="pet_count" /></th>
								<th style="width:188px"><spring:message code="message_table_title_operation" /></th>
							</tr>
						</thead>			
					</table>	
				</div>
			</div>
			<!-- END PAGE -->
		</div>
		<!-- END CONTAINER -->
		<!-- BEGIN FOOTER -->
   		<%@ include file="../shared/pageFooter.jsp"%>
   		

       	<div class="modal hide fade" data-backdrop="static" id="myModal1">
       		
      		
			<div class="modal-header">
				<button type="button"  class="close"  data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<div style="padding-top: 20px;">
					<div class="control-group">
						<label class="control-label" style="float: left" for=placeSel><spring:message code="message_table_title_location" /></label>
						<div class="controls">
							<select datatype="*"  nullmsg='<spring:message code="map_store_name" />' style="width: 10%;height: 15%;float: left" name="placeId" id="placeSel" >
								<option value=""></option>
							</select>
						</div>
					</div>
					<div class="control-group" style="margin-top: -9px;">
						<label class="control-label" style="float: left" for="zSel"><spring:message code="message_table_title_floor" /></label>
						<div class="controls">
							<select datatype="*"  nullmsg='<spring:message code="all_floor_choose" />'  style="width: 10%;height: 15%;" name="floorNo" id="zSel" >
								<option value=" "></option>
							</select>
						</div>
					</div> 
					<button class="btn btn-small" id="Ok"  aria-hidden="true" disabled="disabled"
						style="margin-left: 28%;margin-top: -47px"><spring:message code="pet_save" /></button>					
					<button class="btn btn-small clearPaper" 
						style="float: right; margin-right: 40px;margin-top: -47px"><spring:message code="common_clear" /></button>
				</div>
			</div>
			<div class="modal-body">
				<div id="alertBoxScale" class="hide">
					<div class="alert fade in">
						<div id="infoScale"></div>
					</div>
				</div>
                <div id="divCon" style="text-align: center; height: 500px;">
                    <div id="preview" class="demo-wrapper"
                        style="outline: 1px solid #B3B1B1;">
                    </div>
                </div>
			</div>
			<div class="modal-footer">
				<div class="hide">
					<input id="x0" type="text">
					<input id="y0" type="text">
					<input id="x1" type="text">
					<input id="y1" type="text">
				</div>
				<button id="close" data-dismiss="modal" class="btn"><spring:message code="common_close" /></button>
			</div>
		</div>
       	<div class="modal hide fade" data-backdrop="static" id="areaModel">
			<div class="modal-header">
				<button type="button" style="margin-top: -9px;" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>				
			</div>
			<div class="modal-body">
				<div id="alertBoxScale" class="hide">
					<div class="alert fade in">
						<div id="areainfoScale"></div>
					</div>
				</div>
				<div id="areadivCon" style="text-align: center; height: 500px;">
					<div id="areapreview" class="demo-wrapper"
						style="outline: 1px solid #B3B1B1;">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="hide">
					
				</div>
				
			</div>
		</div>
   		<!-- END FOOTER -->
   
		<%@ include file="../shared/importJs.jsp"%>
		<!-- BEGIN PAGE LEVEL SCRIPTS -->
		<script src="<c:url value='/plugins/data-tables/media/js/jquery.dataTables.js'/>"type="text/javascript"></script> 
		<script src="<c:url value='/plugins/raphael-min.js'/>"
		type="text/javascript"></script>
		<script src="<c:url value='/js/mapEdit.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/plugins/heatmap.min.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/js/pet.js'/>" type="text/javascript"></script>
		<!-- END PAGE LEVEL SCRIPTS -->  

		<script type="text/javascript">
   var oTable;
   var rect = {};
   var petDataX =[];
   var petDataY =[];
   var petData = [];
   var validForm;
   var i18n_format = '<spring:message code="map_format" />';
   var i18n_movie = '<spring:message code="message_movie_format" />';
   var i18n_delete = '<spring:message code="common_delete" />',
   		i18n_edit = '<spring:message code="common_edit" />',
   	    i18n_info = '<spring:message code="map_info" />',
   	    i18n_deleteInfo = '<spring:message code="map_delete" />',
   	   	i18n_open = '<spring:message code="message_swith_open" />',
        i18n_fuzhi  = '<spring:message code="map_fuzhi" />',
   	   	i18n_close = '<spring:message code="message_swith_close" />',
   	 	i18n_choose_title = '<spring:message code="map_choose_posistion_alert" />',
       	i18n_Preview = '<spring:message code="seller_Preview" />',
       	i18n_dingyue = '<spring:message code="area_dingyue" />',
       	i18n_undingyue = '<spring:message code="area_no_dingyue" />',
       	i18n_petPosition = '<spring:message code="pet_weizhi" />',
       	i18n_sameName = '<spring:message code="input_select_name" />';
      $(document).ready(function() {
    	  
    	  $("#search").hide();
    	  validForm =   $(".demoform").Validform({tiptype:3,
              showAllError:true,
              datatype:{
                  "password":function(gets,obj,curform,regxp){
                  if(gets=="")
                  {
                  return false;
                  }
                  if(isNaN(gets))
                  {
                  return false;
                  }
              
          }}});

        
    	 App.init();
    	 MsgMng.initSelect();
    	 MsgMng.initMsgTable();
    	 MsgMng.bindClickEvent();
    	 $('.tip').tooltip();
      });
		</script>
   <!-- END JAVASCRIPTS -->
	</body>
</html>