<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="newUI/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="newUI/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="newUI/css/style.css" rel="stylesheet" type="text/css" />
<link href="newUI/lib/Hui-iconfont/1.0.6/iconfont.css" rel="stylesheet" type="text/css" />
<!-- <script type="text/javascript" src="newUI/lib/jquery/1.9.1/jquery.min.js"></script>   -->
<script type="text/javascript" src="newUI/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="newUI/lib/laypage/1.2/laypage.js"></script> 
<script type="text/javascript" src="newUI/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="newUI/js/H-ui.js"></script> 
<script type="text/javascript" src="newUI/js/H-ui.admin.js"></script> 
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=sNyur13vORywDFGWIkwSmuDi"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>

<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>地图管理</title>
<script type="text/javascript">
var bigMap;
var datagrid;
var currentMapLevel=17;
var zuobiao="";
	$(function() {		
		initMap();
		zuobiao=$("#zuobiao").val();
		if(zuobiao!=null&&zuobiao!=""){
			addMarker(zuobiao.split(",")[0],zuobiao.split(",")[1]);
			bigMap.centerAndZoom(new BMap.Point(zuobiao.split(",")[0],zuobiao.split(",")[1]),currentMapLevel);
		}
	});
	//初始化地图
	function initMap(){
		bigMap = new BMap.Map("BaiduContainer",{mapType:BMAP_HYBRID_MAP}); 
		bigMap.enableScrollWheelZoom();
		bigMap.disableDoubleClickZoom();
		bigMap.centerAndZoom(new BMap.Point(120.798883,32.084829),17);
		var mapType1 = new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]});
		bigMap.addControl(mapType1);  
// 		bigMap.setCurrentCity("江苏省");
		currentMapLevel=bigMap.getZoom();
		bigMap.addEventListener('dblclick', function (e) {
			addMarker(e.point.lng,e.point.lat);
		});
	}
	function addMarker(lng,lat){
		bigMap.clearOverlays();
		
		var marker =new BMap.Marker(new BMap.Point(lng,lat));
			var label=new BMap.Label("定位点");
			var size=new BMap.Size(20,20);
			label.setOffset(size);
			marker.setLabel(label);
			bigMap.addOverlay(marker);
			zuobiao=lng+","+lat;
			//经纬度信息 定位第一个坐标位置
// 			bigMap.centerAndZoom(new BMap.Point(obj.lng,obj.lat),currentMapLevel);
	}
	function tijiao(){
		var index = parent.layer.getFrameIndex(window.name);
		parent.location_chooseBack(zuobiao);
		parent.layer.close(index);
	}
</script>
</head>
<body>
<nav class="breadcrumb"></nav>
<div class="pd-20">
<form action="" name="form1" method="post"  id="form1"  >
<input type="hidden" value="${zuobiao}" id="zuobiao" name="zuobiao" />
</form>
		
<div id="BaiduContainer" style="height: 80%; width:100%; " title="地图"></div>
</div>
<div class="row cl">
      <div class="col-6 col-offset-5">
        <input class="btn btn-primary radius" type="button" onclick="tijiao()" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
      </div>
    </div>
</body>
</html>