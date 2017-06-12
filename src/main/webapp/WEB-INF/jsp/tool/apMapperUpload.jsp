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
                    <ul class="page-breadcrumb breadcrumb">
                        <li>
                            <i class="icon-home" style="background-image:none"></i>
                            <spring:message code="menu_tools" />
                            <i class="icon-angle-right"></i>
                        </li>
                        <li><spring:message code="tools_pRRU" /></li>
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
                <form class="form-horizontal demoform" action="/sva/ap/api/saveData" enctype="multipart/form-data" method="post" >
                    <input type="file" name="file" class="file" id="fileField"/>
                    <button id="add" style="height: 30px;" type="submit" class="btn btn-primary""><spring:message code="common_add" /></button>
                </form>
            </div>
        </div>
        <!-- END PAGE -->
    </div>
    <!-- END CONTAINER -->
    <!-- BEGIN FOOTER -->
    <div class="footer">
        <div class="footer-inner">
        <%@ include file="../shared/pageFooter.jsp"%>
        </div>
        <div class="footer-tools">
            <span class="go-top">
                <i class="icon-angle-up"></i>
            </span>
        </div>
    </div>
    <!-- END FOOTER -->
    <%@ include file="../shared/importJs.jsp"%>
    <!-- BEGIN PAGE LEVEL SCRIPTS -->
    <script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
    <!-- END PAGE LEVEL SCRIPTS -->  

    <script type="text/javascript">
    $(document).ready(function() { 
        App.init();
    });
    </script>
    <!-- END JAVASCRIPTS -->
    </body>
</html>