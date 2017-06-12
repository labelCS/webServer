var swiper;
var timerFunctionTimer;
var BarJsonData;
function timerFunction()
{	
	clearTimeout(timerFunctionTimer);
	
	$.post("/sva/showParams/getStatisticData",{showId:"ba"}, function(item){
		if(!item.error){
			$("#numbern-data-item2").html(item.allUser1);
			$("#numbern-data-item3").text(item.allTime1);
			BarJsonData = item.item1;

			currentArr = _.pluck(BarJsonData, 'current');
			currentMax = _.max(currentArr,function(d){return parseInt(d);});
			var intcurrentMax = parseInt(currentMax)*1.5;
			
			maxcumulativeArr = _.pluck(BarJsonData, 'cumulative');
			cumulativeMax = _.max(maxcumulativeArr,function(d){return parseInt(d);});
			var maxcumulativeMax = parseInt(cumulativeMax)*1.5;
			
			maxcumulTime = _.pluck(BarJsonData, 'average');
			cumulTimeMax = _.max(maxcumulTime,function(d){return parseFloat(d);});
			var maxcumulTimeMax = (cumulTimeMax)*1.5;
			if(BarJsonData){
				$("#bar-slide1-list").html("");
				$.each(BarJsonData, function(i, item) {
					var _current = 0;
					var _times = 0;
					if(intcurrentMax > 0){
						_current = item.current/intcurrentMax * 100;
					}
					var _cumulative = 0;
					if(maxcumulativeMax > 0){
						_cumulative = item.cumulative/maxcumulativeMax * 100;
					}
					if(maxcumulTimeMax > 0){
						_times = item.average/maxcumulTimeMax * 100;
					}
		            $("#bar-slide1-list").append(
					       '<div class="bar-slide1-listItem"><ul><li>' + item.name + '</li>' + 
						   '<li><span class="slide1-bar"style="background-color:#d38a23;width:' + _current + '%"><span>'+ item.current +'</span></span></li>'+
		                 '<li><span class="slide1-bar"style="background-color:#e9643b;width:' + _cumulative + '%"><span>'+ item.cumulative +'</span></span></li>'+
		                 '<li><span class="slide1-bar"style="background-color:#e03d28;width:' + _times + '%"><span>'+ Math.abs(item.average) +'</span></span></li>'						  
//		                 '<li>'+item.average +'</li></ul></div>'
						  );
		        });
			}
			
		}
		timerFunctionTimer = setTimeout("timerFunction();", 60000);
	});
	
}
$(function(){
	
	timerFunction();
});