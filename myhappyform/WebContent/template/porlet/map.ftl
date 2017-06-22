 <div id="p_map" > 
<div class="easyui-tabs" data-options="fit:true,plain:true" style="height:430px;overflow-x:hidden;overflow-y:hidden;padding: 5px;">
<div id="baidumap1" style="height: 380px; width:680px; " title="涉稳信息">
</div>
<div id="baidumap2" style="height: 380px; width:680px; " title="涉稳人员">
</div>
<div id="baidumap3" style="height: 380px; width:680px; " title="热力分布">
</div>
</div>
 </div>
<script>
		buildMap();
		function buildMap(){
			var x = 120.873801;
			var y = 32.014665;


			var map1 = new BMap.Map("baidumap1");          // 创建地图实例
		    var point1 = new BMap.Point(x, y);
		    map1.centerAndZoom(point1, 25);             // 初始化地图，设置中心点坐标和地图级别
		    map1.enableScrollWheelZoom(); // 允许滚轮缩放

			//map1.centerAndZoom(new BMap.Point(120.873801, 32.014665), 14);
			var marker1 = new BMap.Marker(new BMap.Point(120.873801, 32.014665));  // 创建标注
			map1.addOverlay(marker1);              // 将标注添加到地图中

			//创建信息窗口
			var infoWindow1 = new BMap.InfoWindow("普通标注");
			marker1.addEventListener("click", function(){this.openInfoWindow(infoWindow1);});

			//创建小狐狸
			//var pt = new BMap.Point(120.873801, 32.014665);
			//var myIcon = new BMap.Icon("", new BMap.Size(300,157));
			//var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
			//map1.addOverlay(marker2);              // 将标注添加到地图中

			//让小狐狸说话（创建信息窗口）
			//var infoWindow2 = new BMap.InfoWindow("<p style='font-size:14px;'>哈哈，你看见我啦！我可不常出现哦！</p><p style='font-size:14px;'>赶快查看源代码，看看我是如何添加上来的！</p>");
			//marker2.addEventListener("click", function(){this.openInfoWindow(infoWindow2);});

		 

			var map2 = new BMap.Map("baidumap2");          // 创建地图实例
		    var point2 = new BMap.Point(x, y);
		    map2.centerAndZoom(point2, 15);             // 初始化地图，设置中心点坐标和地图级别
		    map2.enableScrollWheelZoom(); // 允许滚轮缩放

			var marker22 = new BMap.Marker(new BMap.Point(120.873801, 32.014665));  // 创建标注
			map2.addOverlay(marker22);              // 将标注添加到地图中
			




			var map = new BMap.Map("baidumap3");          // 创建地图实例

		    var point = new BMap.Point(x, y);
		    map.centerAndZoom(point, 15);             // 初始化地图，设置中心点坐标和地图级别
		    map.enableScrollWheelZoom(); // 允许滚轮缩放

		    var points =[
		    {"lng":120.873801,"lat":32.014665,"count":50},
		    {"lng":120.863801,"lat":32.024665,"count":51},
		    {"lng":120.853801,"lat":32.013665,"count":15},
		    {"lng":120.861801,"lat":32.029665,"count":40},
		    {"lng":120.858801,"lat":32.034665,"count":200},
		    {"lng":120.876801,"lat":32.026665,"count":250},
		    {"lng":120.863801,"lat":32.016665,"count":18},
		    {"lng":120.853801,"lat":32.017665,"count":80},
		    {"lng":120.843801,"lat":32.011165,"count":11},
		    {"lng":120.883801,"lat":32.011695,"count":7},
		    {"lng":120.877801,"lat":32.034655,"count":42},
		    {"lng":120.866801,"lat":32.024115,"count":300},
		    {"lng":120.855801,"lat":32.014225,"count":27},
		    {"lng":120.866601,"lat":32.012265,"count":23},
		    {"lng":120.855501,"lat":32.013365,"count":60},
		    {"lng":120.844401,"lat":32.011125,"count":8}];
		    

		    if(!isSupportCanvas()){
		    	alert('热力图目前只支持有canvas支持的浏览器,您所使用的浏览器不能使用热力图功能~')
		    }

		    	
			//详细的参数,可以查看heatmap.js的文档 https://github.com/pa7/heatmap.js/blob/master/README.md

			//参数说明如下:
			/* visible 热力图是否显示,默认为true
		     * opacity 热力的透明度,1-100
		     * radius 势力图的每个点的半径大小   
		     * gradient  {JSON} 热力图的渐变区间 . gradient如下所示
		     *	{
					.2:'rgb(0, 255, 255)',
					.5:'rgb(0, 110, 255)',
					.8:'rgb(100, 0, 255)'
				}
				其中 key 表示插值的位置, 0~1. 
				    value 为颜色值. 
		     */
			heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":20});
			map.addOverlay(heatmapOverlay);
			heatmapOverlay.setDataSet({data:points,max:100});
		   
		}
		
		function setRadius(radius){
	    	document.getElementById("radius-result").innerHTML = radius;
	        heatmapOverlay.setOptions({"radius":radius})
	    }

	    function setOpacity(opacity){
	    	document.getElementById("opacity-result").innerHTML = opacity;
	        heatmapOverlay.setOptions({"opacity":opacity})
	    }

	    function toggle(){
	        heatmapOverlay.toggle()
	    }

	     function setGradient(){

	     	/*
			格式如下所示:
			{
		  		0:'rgb(102, 255, 0)',
		 	 	.5:'rgb(255, 170, 0)',
			  	1:'rgb(255, 0, 0)'
			}
	     	*/
	     	var gradient = {};
	     	var colors = document.querySelectorAll("input[type='color']");
	     	colors = [].slice.call(colors,0);
	     	colors.forEach(function(ele){
				gradient[ele.getAttribute("data-key")] = ele.value; 
	     	});
	        heatmapOverlay.setOptions({"gradient":gradient});
	    }


	    function isSupportCanvas(){
	        var elem = document.createElement('canvas');
	        return !!(elem.getContext && elem.getContext('2d'));
	    }
</script>