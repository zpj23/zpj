<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/common/h-ui_header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=sNyur13vORywDFGWIkwSmuDi"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>
<title></title>
<script type="text/javascript">
var marker=null;
var markerArr=new Array();
var labelArr=new Array();
var bigMap;
var basePath="${ctx}";
var currentMapLevel=17;
$(function () {
	initMap();
// 	initDep();
});

//地图初始化函数，本例取名为init，开发者可根据实际情况定义
function initMap() {
	bigMap = new BMap.Map("container",{mapType:BMAP_HYBRID_MAP}); 
	bigMap.enableScrollWheelZoom();
	bigMap.centerAndZoom(new BMap.Point(120.798883,32.084829),17);
	var mapType1 = new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]});
	bigMap.addControl(mapType1);  
	bigMap.setCurrentCity("江苏省");
	currentMapLevel=bigMap.getZoom();
    var today=new Date();
    $("#datemin").val(today.getFullYear());
}

function createHtml(obj){
	var content = "<table>";  
     content = content + "<tr><td>姓名：</td><td>"+obj.username+"</td></tr>";  
	 content = content + "<tr><td>打卡地址：</td><td>"+obj.address+"</td></tr>";  
	 content = content + "<tr><td>经度：</td><td>"+obj.zuobiao.split(",")[0]+"</td></tr>";  
	 content = content + "<tr><td>纬度：</td><td>"+obj.zuobiao.split(",")[1]+"</td></tr>";  
	 content = content + "<tr><td>打卡时间：</td><td>"+obj.updatetime+"</td></tr>";
     content += "</table>";
     return content;
}

//创建marker事件
function createMarker(obj) {
	currentMapLevel=bigMap.getZoom();
	var zb=obj.zuobiao;
	if(zb==null||zb==undefined||zb==''||zb==","){
		return ;
	}else{
		var taa=qqMapToBMap(zb.split(",")[0],zb.split(",")[1]);
		var marker =new BMap.Marker(new BMap.Point(taa[0],taa[1]));
		var html=createHtml(obj);
		
		(function () { 
			var infoWindow = new BMap.InfoWindow(html);
			marker.addEventListener("click", function(){this.openInfoWindow(infoWindow);});
		})();
		
		marker.setTitle(obj.username);
			var label=new BMap.Label(obj.username);
			var size=new BMap.Size(20,20);
			label.setOffset(size);
			marker.setLabel(label);
			marker.id=obj.id;
			markerArr.push(marker);
			bigMap.addOverlay(marker);
			//经纬度信息 定位第一个坐标位置
			bigMap.centerAndZoom(new BMap.Point(taa[0],taa[1]),currentMapLevel);
	}
}

function deleteOverlays() {
	bigMap.clearOverlays();
}
function initData(){
	deleteOverlays();
	if($("#datemin").val()==""&&($("#yuefen").val()!=""||$("#tianshu").val()!="")){
		layer.msg('请先选择年份!',{icon: 5,time:3000});
		return;
	}
	$.ajax({
 		type: "POST",
		   url: "jlLocationAction_findListInfoByPhone",
		   async:false,
		   data: "datemin="+$("#datemin").val()+"&yuefen="+$("#yuefen").val()+"&tianshu="+$("#tianshu").val()+"&username="+$("#username").val()+"&sgxm="+$("#sgxm").val()+"&sgqy="+$("#sgqy").val()+"&workcontent="+$("#workcontent").val()+"&departmentid="+$("#departmentid").val()+"&cpage=1&pagerow=2000",
		   success: function(arr){
			  var datas=$.parseJSON(arr);
// 			  console.log(datas);
			  var arr=datas.list;
			  layer.msg('总计查询到打卡数据'+datas.count+'条!', {
				  offset: 't',
				  anim: 6,
				  icon: 1,
				  time:5000
				});
			  //{"totalpage":1.0,"count":1,"list":[{"zuobiao":"120.79982,32.087973","id":1,"address":"江苏省南通市港闸区陈桥纬一路","updatetime":"2020-09-13 16:42:10","username":"super"}]}
			  markerArr=new Array();
			  for(var i=0;i<arr.length;i++){
				  createMarker(arr[i]);
			  }
			  initTableList(arr);
		   }
 	});
	
}
function locationInfo(zb,id){
	if(zb==null||zb==undefined||zb==''||zb==","){
		layer.msg('无定位数据', {
			  offset: 't',
			  anim: 6,
			  icon: 3,
			  time:5000
			});
		return;
	}
	var taa=qqMapToBMap(zb.split(",")[0],zb.split(",")[1]);
	bigMap.panTo(new BMap.Point(taa[0],taa[1]));
	var index=0;
	for(var m=0;m<markerArr.length;m++){
		if(markerArr[m].id==id){
			markerArr[m].setAnimation(BMAP_ANIMATION_BOUNCE);
			index=m;
			break;
		}
	}
	setTimeout(function(){
		markerArr[index].setAnimation(null);
	},3000);
	
}
function initTableList(arr){
	$("#listTable").html('');
	var html="";
	for(var i=0;i<arr.length;i++){
		
		html+="<tr onclick=\"locationInfo('"+arr[i].zuobiao+"','"+arr[i].id+"')\"><th>"+arr[i].username+"</th><td>"+arr[i].updatetime+"</td></tr>";	
	}
	$("#listTable").html(html);
}

//初始化部门下拉框
function initDep(){
	   $.ajax({
	     type: "POST",
	     url: "jlDepartmentInfoAction_getDep",
	     async:false,
	     success: function(data1){
	      var str="";
	      var data = $.parseJSON(data1);
	      str="<option value='' >请选择</option>";
	      for(var i=0;i<data.length;i++){
	       str+="<option value='"+data[i].code+"' >"+data[i].name+"</option>";
	      }
	      $("#departmentid").html(str);

	     }
	});
}

/**
* 坐标转换，腾讯地图转换成百度地图坐标
* lng 腾讯经度（pointy）
* lat 腾讯纬度（pointx）
* 经度>纬度
*/

function qqMapToBMap(lng, lat) {

    if (lng == null || lng == '' || lat == null || lat == '')
        return [lng, lat];

    var x_pi = 3.14159265358979324;
    var x = parseFloat(lng);
    var y = parseFloat(lat);
    var z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
    var theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
    var lng = (z * Math.cos(theta) + 0.0065).toFixed(5);
    var lat = (z * Math.sin(theta) + 0.006).toFixed(5);
    return [lng, lat];

}
</script>
</head>
<body>
<!-- <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 统计管理 <span class="c-gray en">&gt;</span> 柱状图统计 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav> -->
<div class="pd-20">
<div class="text-c"> 
		<input type="text" placeholder="选择年份" onfocus="WdatePicker({dateFmt:'yyyy',minDate:'2020',maxDate:'2025'})" id="datemin" name="datemin" class="input-text Wdate" style="width:80px;">
		<input type="text" placeholder="选择月份" onfocus="WdatePicker({dateFmt:'MM'})" id="yuefen" name="yuefen" class="input-text Wdate" style="width:80px;">
		<input type="text" placeholder="选择日期" onfocus="WdatePicker({dateFmt:'dd'})" id="tianshu" name="tianshu" class="input-text Wdate" style="width:80px;">
<!-- 		<input type="text" class="input-text" style="width:170px" placeholder="输入施工项目" id="sgxm" name="sgxm" /> -->
<!-- 		<input type="text" class="input-text" style="width:170px" placeholder="输入施工区域" id="sgqy" name="sgqy" /> -->
		
		<input type="text" class="input-text" style="width:150px" placeholder="输入施工人员名称" id="username" name="username" />
<!-- 		<input type="text" class="input-text" style="width:150px" placeholder="输入工作内容" id="workcontent" name="workcontent" /> -->
<!-- 		<span class="select-box inline"> -->
<!-- 			<select class="select" size="1" name="departmentid" id="departmentid" value="" onchange="" datatype="*" nullmsg="请选择所属部门！"> -->
<!-- 	          <option value="" selected>所属区域</option> -->
<!-- 	        </select> -->
        </span>
		<button type="button"  class="btn btn-success" onclick="initData();" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 构造</button>
</div>
<div class="pd-20 col-xs-12" >
	<div id="container" class="col-xs-6" style="width:800px;height:85%"></div>
	<div class="col-xs-6 pd-20" style="vertical-align: ">
	<table class="table table-border table-bordered table-striped" style="with:200px;cursor:pointer">
  <thead>
    <tr><th width="20%">姓名</th><th>时间</th></tr>
  </thead>
  <tbody id="listTable">
    <tr ><th></th><td>暂无数据</td></tr>
<!--     <tr class="success"><th>.success</th><td>成功或积极</td></tr> -->
<!--     <tr class="warning"><th>.warning</th><td>警告或出错</td></tr> -->
<!--     <tr class="danger"><th>.danger</th><td>危险</td></tr> -->
  </tbody>
</table>
	</div>
</div>

</body>
</html>