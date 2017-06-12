var APLocation = function() {
	
	var formatData = function(data){
		var result = [];
		for(var i=0; i<data.length; i++){
			var tmp = {};
			tmp["lat"] = data[i].lat;
			tmp["lon"] = data[i].lon;
			tmp["title"] = data[i].userId;
			tmp["html"] = '<h3>MAC:'+data[i].mac+'</h3>';
			tmp["zoom"] = zoomLvl;
			result.push(tmp);
		}
		return result;
	}

	var refreshMap = function(userId){
		$.post("/sva/ap/api/getApLocation?t="+Math.random(),{userId:userId}, function(data){
			if(data.data){
				var locs = formatData(data.data);
				maplace.SetLocations(locs, true);
				
			}else{
				alert(data.error);
			}
		});
	};
	
	var initMap = function(){
        maplace = new Maplace({  
            locations: [{  
                lat: 36.628753857760,   
                lon: 117.037353515625,  
                zoom: zoomLvl  
            }],
            map_options:{
            	mapTypeId:google.maps.MapTypeId.HYBRID
            },
            listeners:{
            	zoom_changed:function(){
            		zoomLvl = maplace.oMap.zoom;
            		//console.log(zoomLvl);
            	}
            }
        }).Load();
        refreshMap();
	};
	
	var initDropdown = function(){
		$("#sel").chosen();
		$.post("/sva/ap/api/getUserMac",function(data){
			var list = data.data;
			var options = '';
			for ( var i = 0; i < list.length; i++) {
				options += '<option class="addoption" value="'
					+ list[i] + '">' + HtmlDecode3(list[i])
					+ '</option>';
			}
			$('#sel').append(options);
			$('#sel').trigger("liszt:updated");
		});
		
	};
	
	var bindEvent = function(){
		$("#confirm").on("click",function(e){
			var userId = $("#sel").val();
			refreshMap(userId);
		});
	};
	
	return {
		// 初始化
		init : function() {
			// 谷歌地图初始化
			initMap();
			// 下拉列表初始化
			initDropdown();
			// 绑定事件
			bindEvent();
		}
	};

}();