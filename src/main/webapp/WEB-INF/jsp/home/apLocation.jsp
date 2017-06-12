<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

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
<link href="<c:url value='/plugins/jquery-chosen/chosen.css'/>"
    rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->
<style type="text/css">
</style>

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
                <div>
                    <!-- BEGIN PAGE TITLE & BREADCRUMB>-->
                    <ul class="page-breadcrumb breadcrumb">
                        <li><i class="icon-home" style="background-image: none"></i>
                            <spring:message code="menu_customer_analyse" /> <i
                            class="icon-angle-right"></i></li>
                        <li><spring:message code="menu_customer_analyse_heatmap" /></li>
                    </ul>
                    <!-- END PAGE TITLE & BREADCRUMB-->
                </div>
            </div>
            <!-- END PAGE HEADER-->

            <div class="clearfix"></div>
            <div class="row-fluid">
                    <select id="sel" datatype="*"  nullmsg='<spring:message code="map_store_name" />' style="width: 30%" >
                        <option value="" selected></option>
                    </select>
                    <input id="confirm" style="vertical-align: top;" type="button" value="<spring:message code="common_confirm" />">
            </div>
            <div class="row-fluid">
                <div id="gmap" style="text-align: center; height: 550px;padding: 3px;border: 5px solid #ddd;">
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
    <script src="<c:url value='/plugins/jquery-chosen/chosen.jquery.js'/>"
        type="text/javascript"></script>
    
    <script src="http://maps.googleapis.com/maps/api/js?sensor=false&libraries=geometry&v=3.21&key=AIzaSyARctKRZfzC6eJb3bGRtepeZhDiUEgwDd0">
    </script>
    <!--  
    <script src="http://ditu.google.cn/maps/api/js?v=3.21&key=AIzaSyARctKRZfzC6eJb3bGRtepeZhDiUEgwDd0">
    </script>-->
    <script src="<c:url value='/plugins/maplace.min.js'/>"></script>
    <script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/js/apLocation.js'/>" type="text/javascript"></script>
    <!-- END PAGE LEVEL SCRIPTS -->

    <script type="text/javascript">
        var maplace;
        var zoomLvl =8;
        jQuery(document).ready(function() {
            var validForm = $(".demoform").Validform({tiptype:3});
            App.init();
            /*
            map = new google.maps.Map(document.getElementById('gmap'), {
                zoom: 12,
                center: {lat: 11, lng: 11},
                mapTypeId: google.maps.MapTypeId.HYBRID
            });*/  
            APLocation.init();
        });
    </script>
    <!-- END JAVASCRIPTS -->
</body>
</html>