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
<script src="https://map.qq.com/api/gljs?v=1.exp&key=GKJBZ-NYWLU-YQ4VO-BIUGM-JVGP3-N2BMU"></script>
<title></title>
<script type="text/javascript">
var marker=null;
var markerArr=new Array();
var labelArr=new Array();
var map;
var basePath="${ctx}";
$(function () {
	initMap();
// 	initDep();
});

//地图初始化函数，本例取名为init，开发者可根据实际情况定义
function initMap() {
    //定义地图中心点坐标
    var center = new TMap.LatLng(32.084829,120.798883);
    //定义map变量，调用 TMap.Map() 构造函数创建地图
    map = new TMap.Map(document.getElementById('container'), {
        center: center,//设置地图中心点坐标
        zoom: 17.2,   //设置地图缩放级别
        pitch: 43.5,  //设置俯仰角
        rotation: 45    //设置地图旋转角度
    });
    var today=new Date();
    $("#datemin").val(today.getFullYear());
}

//创建marker事件
function createMarker(obj) {
	console.log(obj);
        var marker = new TMap.MultiMarker({
            id: obj.id+"marker",
            map: map,
            styles: {
                "marker": new TMap.MarkerStyle({
                    "width": 35,
                    "height": 35,
                    "anchor": { x: 16, y: 32 },
                    "src": basePath+'images/yonghu.png'
                })
            },
            geometries: [{
//                 "id": 'demo',
                "styleId": 'marker',
                "position": new TMap.LatLng(obj.zuobiao.split(",")[1],obj.zuobiao.split(",")[0]),
                "properties": {
                    "title": "marker"
                }
            }]
        });
        markerArr.push(marker);
        //label
         var label = new TMap.MultiLabel({
            id: obj.id+"label",
            map: map,
            styles: {
                'label': new TMap.LabelStyle({
                    'color': '#000000', //颜色属性
                    'size': 18, //文字大小属性
                    'offset': { x: 20, y: 20 }, //文字偏移属性单位为像素
                    'angle': 0, //文字旋转属性
                    'alignment': 'center', //文字水平对齐属性
                    'verticalAlignment': 'middle' //文字垂直对齐属性
                })
            },
            geometries: [{
                'id': 'label', //点图形数据的标志信息
                'styleId': 'label', //样式id
                'position': new TMap.LatLng(obj.zuobiao.split(",")[1],obj.zuobiao.split(",")[0]), //标注点位置
                'content': obj.username+':'+obj.updatetime, //标注文本
                'properties': { //标注点的属性数据
                    'title': 'label'
                }
            }]
        })
         labelArr.push(label);
}

function deleteOverlays() {
    if (markerArr) {
        for (i in markerArr) {
        	markerArr[i].setMap(null);
        }
        markerArr.length = 0;
    }
    if (labelArr) {
        for (i in labelArr) {
        	labelArr[i].setMap(null);
        }
        labelArr.length = 0;
    }
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
			  layer.msg('总计查询到打卡数据'+datas.count+'条!',{icon: 1,time:5000});
			  //{"totalpage":1.0,"count":1,"list":[{"zuobiao":"120.79982,32.087973","id":1,"address":"江苏省南通市港闸区陈桥纬一路","updatetime":"2020-09-13 16:42:10","username":"super"}]}
			  for(var i=0;i<arr.length;i++){
				  createMarker(arr[i]);
			  }
			//经纬度信息
		    map.panTo(new TMap.LatLng(arr[0].zuobiao.split(",")[1],arr[0].zuobiao.split(",")[0]));
			  
		   }
 	});
	
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
<div class="pd-20">
	<div id="container" style="min-width:700px;height:85%"></div>
</div>

</body>
</html>