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
    <link href="<c:url value='/plugins/jquery-chosen/chosen.css'/>"
    rel="stylesheet" type="text/css" />
    <link href="<c:url value='/plugins/mapEditor/mapEditor.css'/>"
    rel="stylesheet" type="text/css" />
    <link rel="shortcut icon" href="favicon.ico" />
</head>
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
                    <ul class="page-breadcrumb breadcrumb">
                        <li>
                            <i class="icon-home" style="background-image:none"></i>
                            <spring:message code="menu_customer_analyse" />
                            <i class="icon-angle-right"></i>
                        </li>
                        <li><spring:message code="menu_customer_analyse_heatmap5" /></li>
                    </ul>
                </div>
            </div>
            <!-- END PAGE HEADER-->
         
            <div class="clearfix"></div>
            <div class="row-fluid">
                <form class="demoform">
                    <div class="span9">
                        <select id="marketSel" datatype="*"  nullmsg='<spring:message code="map_store_name" />'  data-placeholder="<spring:message code="heatmap_place" />" class="chosen-select" >
                            <option value=""></option>
                        </select>           
                        <select id="floorSel" datatype="*"  nullmsg='<spring:message code="all_floor_choose" />' data-placeholder="<spring:message code="heatmap_floor" />"  class="chosen-select" >
                            <option value=""></option>
                        </select>
                        <input type="button" class="btn btn-primary" value="<spring:message code="common_confirm" />" id="confirm" style="vertical-align: top;">
                    </div>
                    <div class = "span3" >
                        <button type = "button" class="btn btn-primary" id="exportButton"><spring:message code="sva_daochu" /></button>
                        <a download="" href="" target="blank" id="downloadLink" style="visibility: hidden">test</a>
                    </div>
                </form>     
            </div>
            <div class="row-fluid">
                <input id="width3D" placeholder="<spring:message code="pathplan_width_3D" />">
                <input id="height3D" placeholder="<spring:message code="pathplan_height_3D" />">
                <input id="upperLeftX3D" placeholder="<spring:message code="pathplan_point_x" />">
                <input id="upperLeftY3D" placeholder="<spring:message code="pathplan_point_y" />">
            </div>
            <div class="row-fluid">
                <div id="mainContent" style="width:100%;height:600px;">
                </div>
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
    <script src="<c:url value='/plugins/jquery-chosen/chosen.jquery.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/plugins/raphael-min.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/plugins/mapEditor/mapEditor.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/js/pathPlan.js'/>" type="text/javascript"></script>
    <!-- END PAGE LEVEL SCRIPTS -->  

    <script type="text/javascript">
    var map;
    $(document).ready(function() {
    	var validForm = $(".demoform").Validform({tiptype:3});
        App.init();
        PathPlan.init();
    });
    </script>
    <!-- END JAVASCRIPTS -->
</body>
</html>