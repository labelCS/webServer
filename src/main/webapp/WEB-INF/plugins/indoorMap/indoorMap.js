;(function($,win){
	function Map(opt)
	{
		// 默认选项
		this._opt = {
			mapUrl : "",			// 获取地图的url
			scale : 0,				// 地图缩放比
			mapWidthOrign: 0,		// 图片原始宽度
			mapHeightOrign: 0,		// 图片原始高度
			containerId : "",		// 容器id
			LangSet : "cn",			// 语言设置
		};
		$.extend(this._opt, opt);
		
		// 语言选项
		this._lang = {
			en:{},
			cn:{}
		};
		
		// 主体div的id
		this.paintDivName = "mapBox";
		this.hammertime = null;
		
		
	};
	
	Map.prototype = {
		// 加载地图，进行初始化显示
		_init : function(){
			
			// 地图初始化
			var domHtml = '<div id="'+this.paintDivName+'" onselectstart="return false;" style="width:100%;height:100%;overflow: hidden;"><div id="mapId"></div></div>';
			if($("#"+this.paintDivName)) $("#"+this.paintDivName).remove();
			$("#"+this._opt.containerId).append(domHtml);
			$("#mapId").css({
				"position":"relative",
				"width" : this._opt.mapWidthOrign + "px",
				"height" : this._opt.mapHeightOrign + "px",
				"background-image" : "url('"+this._opt.mapUrl+"')",
				"background-size" : this._opt.mapWidthOrign + "px " + this._opt.mapHeightOrign + "px",
				"left":10,
				"top":10
			});
			
			//创建一个新的hammer对象并且在初始化时指定要处理的dom元素
			this.hammertime = new Hammer(document.getElementById("mapId"));
			//为该dom元素指定触屏移动事件
			this.hammertime.add(new Hammer.Pinch());
			this.hammertime.get('pan').set({ direction: Hammer.DIRECTION_ALL });
			
			
			// 绑定事件
			this._bindClickEvent(this);
			
			//点的初始化
			this.svg = d3.select("#mapId")     		//选择文档中的body元素
				.append("svg")          			//添加一个svg元素
				.attr("width", this._opt.mapWidthOrign)       //设定宽度
				.attr("height", this._opt.mapHeightOrign);    //设定高度
		},
		
		// 绑定单击事件
		_bindClickEvent : function(_this){
			// 移动开始事件
			_this.hammertime.on("panstart", function(ev){
				console.log("start");
				_this.temp1 = $("#mapId").css("left");
				_this.temp2 = $("#mapId").css("top");
			});
			// 移动运行事件
			_this.hammertime.on("panmove", function(ev){
				console.log("move");
				var w1 = parseInt(_this.temp1.substring(0,_this.temp1.length-2))+ev.deltaX;
				var w2 = parseInt(_this.temp2.substring(0,_this.temp2.length-2))+ev.deltaY;
				$("#mapId").css("left",w1);
				$("#mapId").css("top",w2);
			});
			// 缩放开始事件
			_this.hammertime.on("pinchstart", function(e) {
				var currentWidth = $("#mapId").width();
				_this.tempWidth = $("#mapId").width();
				_this.tempHeight = $("#mapId").height();
			});
			
			// 缩放运行事件
			_this.hammertime.on("pinchmove", function(e) {
				var currentWidth = $("#mapId").width();
				$("#mapId").width(_this.tempWidth*e.scale);
				$("#mapId").height(_this.tempHeight*e.scale);
				$("#mapId").css("background-size",_this.tempWidth*e.scale + "px " + _this.tempHeight*e.scale + "px");
			});
			
			_this.hammertime.on("tap", function (e) {
				
			});
		},
		
		// 图像上的所有元素跟随图像一起移动
		_resizeElement : function(){
			
		},
		
		_addPoint : function(){
			this.svg
			.append("circle")
			.attr("cx", 10)
			.attr("cy", 10)
			.attr("r", 5);
		}
	};
	
	var IndoorMap = {
		// 初始化
		init : function(opt){
			var hm = new Map(opt);
			hm._init();
			return hm;
		},
		// 改变设置
		changeOption : function(obj, opt){
			$.extend(obj._opt, opt);
			obj._init();
		},
		
		addPoint : function(obj){
			obj._addPoint();
		}
	};
	win["IndoorMap"] = IndoorMap;
})(jQuery,window);