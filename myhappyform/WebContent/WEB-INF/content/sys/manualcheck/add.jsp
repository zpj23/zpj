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

<script type="text/javascript">
function changeDep(){
	$("#departmentname").val($("#departmentcode").find("option:selected").text());
}

var demon;
$(function(){
	
	demon=$("#form1").Validform(
			{
		tiptype:2,		
		ajaxPost:true,
		callback:function(data){
			if(data.map.status=="y"){
				$.Hidemsg();
				layer.msg('保存成功!',{icon: 1,time:1000});
// 	  			closethisWin();
			setTimeout('closethisWin()', 1000); 
			}
		}
	}
	);
// 	$("#tijiao").click(function(){
// 		demo.ajaxPost();
// 		return false;
// 	});
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
			  $("#departmentcode").html(str);
			  $('#departmentcode').val("${cinfo.departmentcode}");
			  $('#address').val("${cinfo.address}");
			  $('#workcontent').html("${cinfo.workcontent}");
			  $('#remark').html("${cinfo.remark}");
			  $('#workdate').val("${cinfo.workdate}".substring(0,10));
		   }
	});
	
});


//关闭该窗口
function closethisWin(){
	var index = parent.layer.getFrameIndex(window.name);
//	parent.$('.btn-refresh').click();
	console.log(parent);
	parent.tolist();
	parent.layer.close(index);
}
</script>
</head>
<body>
<div class="pd-20">
  <form action="jlManualCheckInfoAction_doAdd" name="form1" method="post" class="form form-horizontal" id="form1">
    <input type="hidden" id="id" name="cinfo.id" value="${cinfo.id}" />
    
    <div class="row cl">
      <label class="form-label col-3"><span class="c-red">*</span>施工人员：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="${cinfo.staffname}" placeholder="施工人员" id="staffname" name="cinfo.staffname" datatype="*1-20" nullmsg="施工人员不能为空">
      </div>
      <div class="col-4"> </div>
    </div>
   
    <div class="row cl">
      <label class="form-label col-3"><span class="c-red">*</span>施工日期：</label>
      <div class="formControls col-5">
        <input type="text"  id="workdate" datatype="*" onfocus="WdatePicker()" name="cinfo.workdate" class="input-text Wdate" style="width:120px;" value="" nullmsg="不能为空">
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3"><span class="c-red">*</span>出勤时间（小时）：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" datatype="*" value="${cinfo.workduringtime}" placeholder="数字" id="workduringtime" name="cinfo.workduringtime"   nullmsg="工作时长不能为空">
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3"><span class="c-red"></span>加班时间（小时）：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text"  value="${cinfo.overtime}" placeholder="数字" id="overtime" name="cinfo.overtime"   nullmsg="">
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3"><span class="c-red">*</span>所属地区：</label>
      <div class="formControls col-5"> <span class="select-box">
      	<input type="hidden" id="departmentname" name="cinfo.departmentname" value="${cinfo.departmentname}"/>
        <select class="select" size="1" name="cinfo.departmentcode" id="departmentcode" value="${cinfo.departmentcode}" onchange="changeDep()" datatype="*" nullmsg="请选择所属部门！">
          <option value="" selected>请选择部门</option>
          <option value="1">技术部</option>
          <option value="2">实施部</option>
          <option value="3">企划部</option>
<!--           <option value="4">admin</option> -->
        </select>
        </span> </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3">施工项目及区域：</label>
      <div class="formControls col-5">
      <input type="text" class="input-text" value="${cinfo.address}" placeholder="施工项目及区域" id="address" name="cinfo.address"  nullmsg="">
<%--         <textarea name="cinfo.address" id="address" value="${cinfo.address}" cols="" rows=""  class="textarea"  placeholder="说点什么..." datatype="*1-500" dragonfly="true" nullmsg="内容不能为空！" onKeyUp="textarealength(this,500)"></textarea> --%>
<!--         <p class="textarea-numberbar"><em class="textarea-length">0</em>/500</p> -->
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3">具体内容：</label>
      <div class="formControls col-5">
        <textarea name="cinfo.workcontent" id="workcontent" value="${cinfo.workcontent}" cols="" rows=""  class="textarea"  placeholder="说点什么..." datatype="*1-500" dragonfly="true" nullmsg="内容不能为空！" onKeyUp="textarealength(this,500)"></textarea>
        <p class="textarea-numberbar"><em class="textarea-length">0</em>/500</p>
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3">备注：</label>
      <div class="formControls col-5">
        <textarea name="cinfo.remark" id="remark" value="${cinfo.remark}" cols="" rows=""  class="textarea"  placeholder="说点什么..."  dragonfly="true" nullmsg="内容不能为空！" onKeyUp="textarealength(this,500)"></textarea>
        <p class="textarea-numberbar"><em class="textarea-length">0</em>/500</p>
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <div class="col-9 col-offset-3">
<!--       	<input class="btn btn-primary radius"  type="button" value="提 交" id="tijiao"  /> -->
        <input class="btn btn-primary radius" type="submit" id="tijiao"  value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
      </div>
    </div>
  </form>
</div>
</div>

</body>
</html>