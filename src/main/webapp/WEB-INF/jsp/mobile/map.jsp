<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<%@ include file="../shared/taglib.jsp"%>

<html>
<head>
<title>SVA APP demo</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />

<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link rel="stylesheet" href="<c:url value='/plugins/jquery-mobile/jquery.mobile-1.4.5.css'/>">
<!-- END PAGE LEVEL PLUGIN STYLES -->

<link rel="shortcut icon" href="favicon.ico" />
<style type="text/css">
    .floorDiv{
        width:40px;
        height:40px;
        border:1px solid #eee;
        border-radius:20px;
        line-height:40px;
        text-align:center;
        background-color:#dedede;
        position:fixed;
    }
    
    .floorDiv.active{
        background-color:#3388cc;
        color:#fff;
    }
    
    #findPosition{
        width:50px;
        height:50px;
        background-image:url(../images/mobile/locate.png);
        background-size:50px 50px;
        position: fixed;
        left: 15px;
        bottom: 15px;
    }
</style>
</head>
<body>
    <!-- 首页 -->
    <div id="mainPage" data-role="page" data-theme="a">
        <div data-role="header">
            <div data-role="navbar">
                <ul>
                    <li><a id="storeLink" href="#chooseStore" data-icon="location" data-transition="slidedown">地点</a></li>
                    <li><a id="mapLink" href="#mapPage" data-icon="navigation" data-transition="slide">地图</a></li>
                    <li><a href="#" data-icon="info">登录</a></li>
                </ul>
            </div>
        </div>
        <div data-role="content"  data-theme="a">
            <p>广告</p>
        </div>
    </div>
    
    <!-- 地图定位导航页面-->
    <div id="mapPage" data-role="page"  style="overflow: hidden;">
        <div data-role="header">
            <a href="#mainPage" data-role="button" data-icon="home"  data-transition="slide" data-direction="reverse">首页</a>
            <h1 id="storeName"></h1>
            <a href="#" data-role="button" data-icon="info">我的</a>
        </div>
        <div data-role="content" style="padding-left:0 !important">
            <div id="mapContainer" style = "position:absolute; width:100%;height:100%;"></div>
            <div id="findPosition"></div>
        </div>
    </div>
    
    <!-- 商场选择弹出页面 -->
    <div id="chooseStore" data-role="page" data-dialog="true">
        <div data-role="header">
            <h1>请选择商场</h1>
        </div>
        <div data-role="content">
            <fieldset id="storeList" data-role="controlgroup" data-iconpos="right">
            </fieldset>
            <div data-role="controlgroup" data-type="horizontal" style="text-align: center;">
                <a id="radioOk" href="#" data-role="button" data-rel="back">确定</a>
                <a href="#" data-role="button" data-rel="back">取消</a>
            </div>
        </div>
    </div>

    <script src="<c:url value='/plugins/jquery.js'/>"></script>
    <script src="<c:url value='/plugins/jquery-mobile/jquery.mobile-1.4.5.js'/>"></script>
    <script src="<c:url value='/plugins/hammer.min.js'/>"></script>
    <script src="<c:url value='/plugins/d3/d3.js'/>"></script> 
    <script src="<c:url value='/plugins/indoorMap/indoorMap.js'/>"></script>
    <script src="<c:url value='/js/util.js'/>"></script>
    <script src="<c:url value='/js/mobile/map.js'/>"></script>
    <script>
    var storeId,floorNo;
    var mapInfo;
    var mapObj;
    var localIp = null;
    var timer;
    // 获取本地局域网ip
    getIPs(function(ip){
    	if(!localIp){
    	    localIp = ip;
    	}
    });
    $(document).on("pageinit","#mainPage",function(){
        // 此处是 jQuery 事件
    	Map.init();
    	Map.bindEvent();
    });
    </script>
</body>
</html>