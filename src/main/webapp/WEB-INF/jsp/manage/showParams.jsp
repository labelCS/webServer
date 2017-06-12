<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if IE 11]> <html lang="en" class="ie11 no-js"> <![endif]-->
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
      <link
    href="<c:url value='/plugins/data-tables/media/css/demo_table.css'/>"
    rel="stylesheet" type="text/css" />
    <link href="<c:url value='/plugins/jquery-chosen/chosen.css'/>"
    rel="stylesheet" type="text/css" />
    <style type="text/css">
    .storeDiv{
        border:1px solid rgb(221, 221, 221);
        border-radius:5px;
        margin-bottom:20px;
        padding-top:10px;
    }
    
    .storeDiv .row-fluid{
        margin-bottom:0px !important;
    }
    
    .row-fluid.form-horizontal{
        padding:10px;
        width: auto;
    }
    </style>
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
            <div>
               <!-- BEGIN PAGE TITLE & BREADCRUMB -->
               <ul class="page-breadcrumb breadcrumb">
                  <li>
                     <i class="icon-home" style="background-image:none"></i>
                     <spring:message code="menu_info_manage" />
                     <i class="icon-angle-right"></i>
                  </li>
                  <li><spring:message code="menu_show_param" /></li>
               </ul>
               <!-- END PAGE TITLE & BREADCRUMB-->
            </div>
         </div>
         <!-- END PAGE HEADER-->
         
         <div class="clearfix"></div>
         <div class="tableBox">
            <div id="alertBox" class="hide">
                <div class="alert">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <div id="info"></div>
                </div>
            </div>
            <form class="demoform" onkeydown= "if(event.keyCode==13)return false;">
                <div id="editBox" class="portlet light-grey hide">
                    <div class="portlet-title">
                        <div class="caption">
                            <i class="icon-edit icon-large" style="background: none"></i><spring:message code="paramconfig" />
                            <input type="hidden" name = "id" id="idHidden">
                        </div>
                    </div>
                    <div class="row-fluid form-horizontal">
                        <div class="span6">
                            <div class="row-fluid">
                                <div class="span12">
                                    <div class="storeDiv">
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <label class="control-label" for=placeSel1><spring:message code="mwc_zhanguan1" /></label>
                                                    <div class="controls">
                                                        <select datatype="*" id="placeSel1" nullmsg='<spring:message code="map_store_name" />' style="width: 59%;height: 15%;"   >
                                                            <option value=" "></option>
                                                        </select>
                                                        
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <label class="control-label" for="zSel1"><spring:message code="message_table_title_floor" /></label>
                                                    <div class="controls">
                                                        <select datatype="*" id="zSel1" nullmsg='<spring:message code="all_floor_choose" />'  style="width: 59%;height: 15%;"  >
                                                            <option value=" "></option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <!-- 密度 -->
                                                <div class="control-group">
                                                    <label class="control-label" for="zSel1"><spring:message code="heatmap_density" /></label>
                                                    <div class="controls">
                                                        <select id="densitySel1" style="height: 32px;width: 82px" data-placeholder="<spring:message code="heatmap_density" />"
                                                        class="">
                                                            <option value="1">1</option>
                                                            <option value="2">2</option>
                                                            <option value="3">3</option>
                                                            <option value="4">4</option>
                                                            <option value="5">5</option>
                                                            <option value="10">10</option>
                                                            <option value="15">15</option>
                                                            <option value="20">20</option>
                                                        </select>
                                                    </div>
                                                 </div>
                                             </div>
                                         </div>
                                         <div class="row-fluid">
                                             <div class="span12">
                                                <!-- 扩散度 -->
                                                <div class="control-group">
                                                    <label class="control-label" for="zSel1"><spring:message code="heatmap_diffusance" /></label>
                                                    <div class="controls">
                                                        <select id="radiusSel1" style="height: 32px;width: 82px" data-placeholder="<spring:message code="heatmap_diffusance" />"
                                                            class="">
                                                            <option value="20">20</option>
                                                            <option value="30">30</option>
                                                            <option value="40">40</option>
                                                            <option value="50">50</option>
                                                            <option value="100">100</option>
                                                            <option value="150">150</option>
                                                            <option value="200">200</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="storeDiv hide">
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <label class="control-label" for=placeSel2><spring:message code="mwc_zhanguan2" /></label>
                                                    <div class="controls">
                                                        <select id="placeSel2" style="width: 59%;height: 15%;" >
                                                            <option value=" "></option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <label class="control-label" for="zSel2"><spring:message code="message_table_title_floor" /></label>
                                                    <div class="controls">
                                                        <select id="zSel2" style="width: 59%;height: 15%;"  >
                                                            <option value=" "></option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <!-- 密度 -->
                                                <div class="control-group">
                                                    <label class="control-label" for="zSel2"><spring:message code="heatmap_density" /></label>
                                                    <div class="controls">
                                                        <select id="densitySel2" style="height: 32px;width: 82px" data-placeholder="<spring:message code="heatmap_density" />"
                                                        class="">
                                                            <option value="1">1</option>
                                                            <option value="2">2</option>
                                                            <option value="3">3</option>
                                                            <option value="4">4</option>
                                                            <option value="5">5</option>
                                                            <option value="10">10</option>
                                                            <option value="15">15</option>
                                                            <option value="20">20</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <!-- 扩散度 -->
                                                <div class="control-group">
                                                    <label class="control-label" for="zSel2"><spring:message code="heatmap_diffusance" /></label>
                                                    <div class="controls">
                                                    <select id="radiusSel2" style="height: 32px;width: 82px" data-placeholder="<spring:message code="heatmap_diffusance" />"
                                                        class="">
                                                        <option value="20">20</option>
                                                        <option value="30">30</option>
                                                        <option value="40">40</option>
                                                        <option value="50">50</option>
                                                        <option value="100">100</option>
                                                        <option value="150">150</option>
                                                        <option value="200">200</option>
                                                    </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="storeDiv hide">
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <label class="control-label" for=placeSel><spring:message code="mwc_zhanguan3" /></label>
                                                    <div class="controls">
                                                        <select id="placeSel3" style="width: 59%;height: 15%;" >
                                                            <option value=" "></option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <label class="control-label" for="zSel3"><spring:message code="message_table_title_floor" /></label>
                                                    <div class="controls">
                                                        <select id="zSel3" style="width: 59%;height: 15%;"  >
                                                            <option value=" "></option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <!-- 密度 -->
                                                <div class="control-group">
                                                    <label class="control-label" for="zSel3"><spring:message code="heatmap_density" /></label>
                                                    <div class="controls">
                                                        <select id="densitySel3" style="height: 32px;width: 82px" data-placeholder="<spring:message code="heatmap_density" />"
                                                        class="">
                                                            <option value="1">1</option>
                                                            <option value="2">2</option>
                                                            <option value="3">3</option>
                                                            <option value="4">4</option>
                                                            <option value="5">5</option>
                                                            <option value="10">10</option>
                                                            <option value="15">15</option>
                                                            <option value="20">20</option>
                                                        </select>
                                                    </div>
                                                 </div>
                                             </div>
                                         </div>
                                         <div class="row-fluid">
                                            <div class="span12">
                                                <!-- 扩散度 -->
                                                <div class="control-group">
                                                    <label class="control-label" for="zSel3"><spring:message code="heatmap_diffusance" /></label>
                                                    <div class="controls">
                                                        <select id="radiusSel3" style="height: 32px;width: 82px" data-placeholder="<spring:message code="heatmap_diffusance" />"
                                                            class="">
                                                            <option value="20">20</option>
                                                            <option value="30">30</option>
                                                            <option value="40">40</option>
                                                            <option value="50">50</option>
                                                            <option value="100">100</option>
                                                            <option value="150">150</option>
                                                            <option value="200">200</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="storeDiv hide">
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <label class="control-label" for=placeSel4><spring:message code="mwc_zhanguan4" /></label>
                                                    <div class="controls">
                                                        <select id="placeSel4" style="width: 59%;height: 15%;" >
                                                            <option value=" "></option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <label class="control-label" for="zSel4"><spring:message code="message_table_title_floor" /></label>
                                                    <div class="controls">
                                                        <select id="zSel4" style="width: 59%;height: 15%;"  >
                                                            <option value=" "></option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <!-- 密度 -->
                                                <div class="control-group">
                                                    <label class="control-label" for="zSel4"><spring:message code="heatmap_density" /></label>
                                                    <div class="controls">
                                                        <select id="densitySel4" style="height: 32px;width: 82px" data-placeholder="<spring:message code="heatmap_density" />"
                                                        class="">
                                                            <option value="1">1</option>
                                                            <option value="2">2</option>
                                                            <option value="3">3</option>
                                                            <option value="4">4</option>
                                                            <option value="5">5</option>
                                                            <option value="10">10</option>
                                                            <option value="15">15</option>
                                                            <option value="20">20</option>
                                                        </select>
                                                    </div>
                                                 </div>
                                             </div>
                                         </div>
                                         <div class="row-fluid">
                                            <div class="span12">
                                                <!-- 扩散度 -->
                                                <div class="control-group">
                                                    <label class="control-label" for="zSel4"><spring:message code="heatmap_diffusance" /></label>
                                                    <div class="controls">
                                                        <select id="radiusSel4" style="height: 32px;width: 82px"  data-placeholder="<spring:message code="heatmap_diffusance" />"
                                                            class="">
                                                            <option value="20">20</option>
                                                            <option value="30">30</option>
                                                            <option value="40">40</option>
                                                            <option value="50">50</option>
                                                            <option value="100">100</option>
                                                            <option value="150">150</option>
                                                            <option value="200">200</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="storeDiv hide">
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <label class="control-label" for=placeSel5><spring:message code="mwc_zhanguan5" /></label>
                                                    <div class="controls">
                                                        <select id="placeSel5" style="width: 59%;height: 15%;" >
                                                            <option value=" "></option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <label class="control-label" for="zSel5"><spring:message code="message_table_title_floor" /></label>
                                                    <div class="controls">
                                                        <select id="zSel5" style="width: 59%;height: 15%;"  >
                                                            <option value=" "></option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <!-- 密度 -->
                                                <div class="control-group">
                                                    <label class="control-label" for="zSel5"><spring:message code="heatmap_density" /></label>
                                                    <div class="controls">
                                                        <select id="densitySel5" style="height: 32px;width: 82px" data-placeholder="<spring:message code="heatmap_density" />"
                                                        class="">
                                                            <option value="1">1</option>
                                                            <option value="2">2</option>
                                                            <option value="3">3</option>
                                                            <option value="4">4</option>
                                                            <option value="5">5</option>
                                                            <option value="10">10</option>
                                                            <option value="15">15</option>
                                                            <option value="20">20</option>
                                                        </select>
                                                    </div>
                                                 </div>
                                             </div>
                                         </div>
                                         <div class="row-fluid">
                                            <div class="span12">
                                                <!-- 扩散度 -->
                                                <div class="control-group">
                                                    <label class="control-label" for="zSel5"><spring:message code="heatmap_diffusance" /></label>
                                                    <div class="controls">
                                                        <select id="radiusSel5" style="height: 32px;width: 82px" data-placeholder="<spring:message code="heatmap_diffusance" />"
                                                            class="">
                                                            <option value="20">20</option>
                                                            <option value="30">30</option>
                                                            <option value="40">40</option>
                                                            <option value="50">50</option>
                                                            <option value="100">100</option>
                                                            <option value="150">150</option>
                                                            <option value="200">200</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="storeDiv hide">
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
			                                        <label class="control-label" for=placeSel6><spring:message code="mwc_zhanguan6" /></label>
			                                        <div class="controls">
			                                            <select id="placeSel6" style="width: 59%;height: 15%;" >
			                                                <option value=" "></option>
			                                            </select>
			                                        </div>
			                                    </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
			                                        <label class="control-label" for="zSel6"><spring:message code="message_table_title_floor" /></label>
			                                        <div class="controls">
			                                            <select id="zSel6" style="width: 59%;height: 15%;"  >
			                                                <option value=" "></option>
			                                            </select>
			                                        </div>
			                                    </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <!-- 密度 -->
			                                    <div class="control-group">
			                                        <label class="control-label" for="zSel6"><spring:message code="heatmap_density" /></label>
		                                            <div class="controls">
			                                            <select id="densitySel6" style="height: 32px;width: 82px" data-placeholder="<spring:message code="heatmap_density" />"
			                                            class="">
				                                            <option value="1">1</option>
				                                            <option value="2">2</option>
				                                            <option value="3">3</option>
				                                            <option value="4">4</option>
				                                            <option value="5">5</option>
				                                            <option value="10">10</option>
				                                            <option value="15">15</option>
				                                            <option value="20">20</option>
			                                            </select>
		                                            </div>
			                                     </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <!-- 扩散度 -->
			                                    <div class="control-group">
			                                        <label class="control-label" for="zSel6"><spring:message code="heatmap_diffusance" /></label>
		                                            <div class="controls">
			                                            <select id="radiusSel6" style="height: 32px;width: 82px" data-placeholder="<spring:message code="heatmap_diffusance" />"
			                                                class="">
			                                                <option value="20">20</option>
			                                                <option value="30">30</option>
			                                                <option value="40">40</option>
			                                                <option value="50">50</option>
			                                                <option value="100">100</option>
			                                                <option value="150">150</option>
			                                                <option value="200">200</option>
			                                            </select>
		                                            </div>
			                                    </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="storeDiv hide">
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
			                                        <label class="control-label" for=placeSel7><spring:message code="mwc_zhanguan7" /></label>
			                                        <div class="controls">
			                                            <select id="placeSel7" style="width: 59%;height: 15%;" >
			                                                <option value=" "></option>
			                                            </select>
			                                        </div>
			                                    </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
			                                        <label class="control-label" for="zSel7"><spring:message code="message_table_title_floor" /></label>
			                                        <div class="controls">
			                                            <select id="zSel7" style="width: 59%;height: 15%;"  >
			                                                <option value=" "></option>
			                                            </select>
			                                        </div>
			                                    </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <!-- 密度 -->
			                                    <div class="control-group">
			                                        <label class="control-label" for="zSel7"><spring:message code="heatmap_density" /></label>
		                                            <div class="controls">
			                                            <select id="densitySel7" style="height: 32px;width: 82px" data-placeholder="<spring:message code="heatmap_density" />"
			                                            class="">
				                                            <option value="1">1</option>
				                                            <option value="2">2</option>
				                                            <option value="3">3</option>
				                                            <option value="4">4</option>
				                                            <option value="5">5</option>
				                                            <option value="10">10</option>
				                                            <option value="15">15</option>
				                                            <option value="20">20</option>
			                                            </select>
		                                            </div>
			                                     </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <!-- 扩散度 -->
			                                    <div class="control-group">
			                                        <label class="control-label" for="zSel7"><spring:message code="heatmap_diffusance" /></label>
		                                            <div class="controls">
			                                            <select id="radiusSel7" style="height: 32px;width: 82px" data-placeholder="<spring:message code="heatmap_diffusance" />"
			                                                class="">
			                                                <option value="20">20</option>
			                                                <option value="30">30</option>
			                                                <option value="40">40</option>
			                                                <option value="50">50</option>
			                                                <option value="100">100</option>
			                                                <option value="150">150</option>
			                                                <option value="200">200</option>
			                                            </select>
		                                            </div>
			                                    </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="storeDiv hide">
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
		                                        <label class="control-label" for=placeSel8><spring:message code="mwc_zhanguan8" /></label>
		                                        <div class="controls">
		                                            <select id="placeSel8" style="width: 59%;height: 15%;" >
		                                                <option value=" "></option>
		                                            </select>
		                                        </div>
		                                    </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
			                                        <label class="control-label" for="zSel8"><spring:message code="message_table_title_floor" /></label>
			                                        <div class="controls">
			                                            <select id="zSel8" style="width: 59%;height: 15%;"  >
			                                                <option value=" "></option>
			                                            </select>
			                                        </div>
			                                    </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <!-- 密度 -->
			                                    <div class="control-group">
			                                        <label class="control-label" for="zSel8"><spring:message code="heatmap_density" /></label>
			                                            <div class="controls">
			                                            <select id="densitySel8" style="height: 32px;width: 82px" data-placeholder="<spring:message code="heatmap_density" />"
			                                            class="">
			                                            <option value="1">1</option>
			                                            <option value="2">2</option>
			                                            <option value="3">3</option>
			                                            <option value="4">4</option>
			                                            <option value="5">5</option>
			                                            <option value="10">10</option>
			                                            <option value="15">15</option>
			                                            <option value="20">20</option>
			                                            </select>
			                                            </div>
			                                     </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <!-- 扩散度 -->
			                                    <div class="control-group">
			                                        <label class="control-label" for="zSel8"><spring:message code="heatmap_diffusance" /></label>
		                                            <div class="controls">
			                                            <select id="radiusSel8" style="height: 32px;width: 82px"  data-placeholder="<spring:message code="heatmap_diffusance" />"
			                                                class="">
			                                                <option value="20">20</option>
			                                                <option value="30">30</option>
			                                                <option value="40">40</option>
			                                                <option value="50">50</option>
			                                                <option value="100">100</option>
			                                                <option value="150">150</option>
			                                                <option value="200">200</option>
			                                            </select>
		                                            </div>
			                                    </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="span12 text-right">
                                    <div class="btn-group">
                                        <a id="addStore" class="btn btn-primary"><spring:message code="param_add_store" /></a>
                                        <a id="removeStore" class="btn btn-danger"><spring:message code="param_del_store" /></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="span6">
                        <div class="row-fluid">
                                <div class="span12">
                                    <div class="control-group">
                                        <label class="control-label" for="showId"><spring:message code="param_show_id" /></label>
                                        <div class="controls">
                                            <input id="showId" datatype="*" style="width: 59%;height:15%;padding:2px 0 2px 0;" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="span12">
                                    <div class="control-group">
                                        <label class="control-label" for="periodSel"><spring:message code="bz_shijian" /></label>
                                        <div class="controls">
                                            <select id="periodSel" datatype="*"  data-placeholder="<spring:message code="heatmap_period" />" nullmsg='<spring:message code="bz_shijian_shuru" />'  style="width: 59%;height:15%;padding:2px 0 2px 0;"  class="chosen-select" >
                                                <option value=" "></option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="span12">
                                    <!-- nullmsg='<spring:message code="bzparam_input_coefficient" />'  -->
                                    <div class="control-group">
                                        <label class="control-label" for="coefficient"><spring:message code="bzparam_coefficient" /></label>
                                        <div class="controls">
                                            <input id="coefficient" datatype="paramYz"  value ="1" data-placeholder="<spring:message code="heatmap_period" />"  style="width: 59%;height:15%;padding:2px 0 2px 0;" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="span12">
                                    <div class="control-group">
                                        <label class="control-label" for="select_time_begin_tab1"><spring:message code="bz_kasihishijian" /></label>
                                        <div class="controls">
                                            <input id="select_time_begin_tab1" datatype="*" onclick = "show.showDate('select_time_begin_tab1');" datatype="*"  nullmsg='<spring:message code="all_choose_starttime" />' style="width: 180px" readonly/> 
                                            <span class="add-on" onclick = "show.showDate('select_time_begin_tab1');">
                                                <i class="icon-calendar"></i>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="span12" style="padding: 0 65px;">
                                    <table id="paramTable" class="table table-bordered table-hover">
                                        <tr id="optTr">
                                            <td><a id="addParam" class="btn btn-primary"><spring:message code="param_add_param" /></a></td>
                                            <td><input id="paramKey" type="text" placeholder="Enter the key"></td>
                                            <td><input id="paramValue" type="text" placeholder="Enter the value"></td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-actions" style="padding-left: 0px;">
                        <div class="row-fluid">
                            <div class="span12" style="text-align:center;">
                                <input id="confirm" style="height: 30px" type="button" value="<spring:message code="common_confirm" />" class="btn btn-success"> 
                                <button id="cancel" style="height: 30px" type="button" class="btn"><spring:message code="common_cancel" /></button>
                            <span class="sameInfo" ></span>
                            </div>
                        </div>
                    </div>
                </div>
            </form> 
            <div>
                <button id="add" type="button" class="btn btn-primary" ><spring:message code="common_add" /></button>
            </div>                              

            <table id="table" class="table table-bordered">
                <thead>
                    <tr>
                        <th><spring:message code="param_show_id" /></th>
                        <th><spring:message code="store_add_name" /></th>
                        <th><spring:message code="map_table_title_floor" /></th>
                        <th><spring:message code="store_add_name" /></th>
                        <th><spring:message code="map_table_title_floor" /></th>
                        <th><spring:message code="store_add_name" /></th>
                        <th><spring:message code="map_table_title_floor" /></th>
                        <th><spring:message code="store_add_name" /></th>
                        <th><spring:message code="map_table_title_floor" /></th>
                        <th><spring:message code="store_add_name" /></th>
                        <th><spring:message code="map_table_title_floor" /></th>
                        <th><spring:message code="store_add_name" /></th>
                        <th><spring:message code="map_table_title_floor" /></th>
                        <th><spring:message code="store_add_name" /></th>
                        <th><spring:message code="map_table_title_floor" /></th>
                        <th><spring:message code="store_add_name" /></th>
                        <th><spring:message code="map_table_title_floor" /></th>
                        <th><spring:message code="param_custom_param" /></th>
                        <th><spring:message code="param_updateTime" /></th>
                        <th><spring:message code="param_start_stat_time" /></th>
                        <th><spring:message code="param_fake_ratio" /></th>
                        <th><spring:message code="param_period" /></th>
                        <th><spring:message code="message_table_title_operation" /></th>
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
    <!-- END FOOTER -->
   
    <%@ include file="../shared/importJs.jsp"%>
    <!-- BEGIN PAGE LEVEL SCRIPTS -->
    <script src="<c:url value='/plugins/data-tables/media/js/jquery.dataTables.js'/>"type="text/javascript"></script>
    <script src="<c:url value='/plugins/heatmap.min.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/js/showParams.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/plugins/jquery-chosen/chosen.jquery.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/plugins/layer/layer.js'/>"type="text/javascript"></script> 
    <script src="<c:url value='/plugins/echarts-2.2.5/build/source/echarts-all.js'/>"type="text/javascript"></script>
    <script src="<c:url value='/plugins/wDatePicker/WdatePicker.js'/>"type="text/javascript"></script>
    <!-- END PAGE LEVEL SCRIPTS -->  

    <script type="text/javascript">
    var validForm;
    var oTable;
    var i18n_edit = '<spring:message code="common_edit" />',
        i18n_delete = '<spring:message code="common_delete" />',
        i18n_deleteInfo = '<spring:message code="map_delete" />',
        i18n_deleteCustomParam = '<spring:message code="param_del_param" />',
        i18n_language = '<spring:message code="time_language" />'; 
    $(document).ready(function() {  
        App.init();
        show.init();
        
        validForm =    $(".demoform").Validform({       
            tiptype:3});
    });
    </script>
    <!-- END JAVASCRIPTS -->
</body>
</html>