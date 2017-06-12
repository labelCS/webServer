/**
 * 移动端地图定位导航
 */
var Map = function(){
	/**
	 * 将对应信息填充到对应的select @ param renderId [string] 标签id @ param data [array]
	 *                   列表数据
	 */
	var updateList = function(renderId, data, selectTxt, callback) {
		var sortData = data.sort(function(a, b) {
			return a.name - b.name;
		});
		var len = sortData.length;
		var options = '';
		for ( var i = 0; i < len; i++) {
			if(sortData[i].id == selectTxt){
				options += '<input type="radio" name="radio-store" id="radio-store-'+i+'" data-name="'+sortData[i].name+'" value="'+sortData[i].id+'" checked="checked">'
				options += '<label for="radio-store-'+i+'">'+sortData[i].name+'</label>';
			}else{
				options += '<input type="radio" name="radio-store" id="radio-store-'+i+'" data-name="'+sortData[i].name+'" value="'+sortData[i].id+'">'
				options += '<label for="radio-store-'+i+'">'+sortData[i].name+'</label>';
			}
		}
		$('#' + renderId).empty();
		$('#' + renderId).append(options);
		if (callback) {
			callback();
		}
		return;
	};
	
	var getStores = function(){
		$.get("/sva/mobile/store/api/getData?t="+Math.random(), function(data) {
			if (!data.error) {
				var storeList = data.data;
				updateList("storeList", storeList);
			}
		});
	};
	
	var bindClickEvent = function(){
		// 地点按钮点击事件
		$("#radioOk").on("click",function(){
			var radio = document.getElementsByName("radio-store");
			var selectId,storeName;
		    for (i=0; i<radio.length; i++) {  
		        if (radio[i].checked) {  
		        	storeId = radio[i].value;
		        	selectId = radio[i].id;
		        	storeName = $(radio[i]).data("name");
		        	break;
		        }  
		    }
		    $("#storeLink").text(storeName);
		    $("#storeName").text(storeName);
		});
		
		// 地图按钮点击事件
		$("#mapLink").on("click",function(){
			clearInterval(timer);
			// 获取地图数据
			$.post("/sva/heatmap/api/getFloorsByMarket", {
				placeId : storeId
			}, function(data) {
				if (!data.error) {
					mapInfo = data.data;
					var sortData = mapInfo.sort(function(a, b) {
						return a.floor - b.floor;
					});
					var len = sortData.length;
					var divHeight = $("#mapContainer").position().top;
					var options = '';
					for ( var i = 0; i < len; i++) {
						var top = divHeight + 5 + i*45;
						options += '<div class="floorDiv" data-floorNo="'
							+ sortData[i].floorNo + '" style="top:'+top+'px;left:15px;z-index:5;">' + sortData[i].floor + '</div>';
					}
					$(".floorDiv").remove();
					$("#mapContainer").append(options);
					mapObj = IndoorMap.init({
						mapUrl:"../upload/" + sortData[0].svg, 
						containerId:"mapContainer",
						mapWidthOrign:sortData[0].imgWidth,
						mapHeightOrign:sortData[0].imgHeight
					});
					$($(".floorDiv")[0]).addClass("active");
					IndoorMap.addPoint(mapObj);
				}
			});
		});
		
		// 楼层选择事件
		$(".floorDiv").live("click",function(){
			$(".floorDiv").removeClass("active");
			$(this).addClass("active");
			clearInterval(timer);
			var selectedFloor = $(this).data("floorno");
			var svgImg,mapWidth,mapHeight;
			for ( var i = 0; i < mapInfo.length; i++) {
				if(mapInfo[i].floorNo == selectedFloor){
					svgImg = mapInfo[i].svg;
					mapWidth = mapInfo[i].imgWidth;
					mapHeight = mapInfo[i].imgHeight;
					break;
				}
			}
			IndoorMap.changeOption(mapObj,{mapUrl:"../upload/" + svgImg,mapWidthOrign:mapWidth,mapHeightOrign:mapHeight});
			IndoorMap.addPoint(mapObj);
		});
		
		// 定位按钮点击事件
		$("#findPosition").on("click",function(){
			timer = setInterval(function(){
				$.ajax({
	        		url:"/sva/api/getData",
	        		type:"post",
	        		data:JSON.stringify({ip:localIp}),
	        		contentType:'application/json',
	        		dataType:"json",
	        		success:function(data){
	        			var originX = data.x,
	        				originY = data.y;
	        			IndoorMap.addPoint(mapObj,data.x,data.y);
	        		}
	        	});
			},1000);
			
		});
	};
	
	return {
		init : function(){
			getStores();
		},
		
		bindEvent : function(){
			bindClickEvent();
		}
	}
}();