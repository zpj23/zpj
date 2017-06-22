<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery/jquery-1.6.min.js"></script>
<script type="text/javascript">
function uploadFile(modulename,tableuuid){
  //$("#moduleName").val(modulename);
  //$("#tableUuid").val(tableuuid);  
  if($("#file").val()!=""){
  form1.action ="uploadfileAction_uploadFileMenu";
  form1.submit();
  }
}

function delFile(fileid,url,tableuuid){
  if(confirm('是否删除?')){
  $("#id").val(fileid);
   $("#fileUrl").val(url);
   $("#tableUuid").val(tableuuid);
  form1.action ="uploadfileAction_delFile";
  form1.submit();
 }
}

function downFile(filename,url){
   $("#originalName").val(filename);
   $("#fileUrl").val(url);
  form1.action ="uploadfileAction_downLoadFile";
  form1.submit();
}

function Ok(){
return true;
}


</script>
</head>
<body>

<form action="" method="post" id="form1" enctype="multipart/form-data" >
  <input type="hidden" name="uploadfile.moduleName" id="moduleName" value="${modulename }" />
  <input type="hidden" name="uploadfile.tableUuid" id="tableUuid"  value="${tableuuid}"/>
  <input type="hidden" name="uploadfile.id" id="id" />
  <input type="hidden" name="uploadfile.fileUrl" id="fileUrl" />
  <input type="hidden" name="uploadfile.originalName" id="originalName" />
  <input type="text" name="pictureurl" id="pictureurl" value="${pictureurl}" />
  <c:if test="${isView ne 'true' }">
  <input type="file" name="file" id="file" size="10" value="${pictureurl}"> <input type="button" value="上传" onclick="uploadFile()" />
  <div id="filediv">
  <c:forEach items="${fileList}" var="a">
     <div style="position: relative; float: left; width: 100px; margin-left:30px; margin-top:20px;">
        <a href="javascript:downFile('${a[1]}','${a[2]}');" style=" display:block; width:100%; float:left; text-align: center;">
         <img style="width:100px;height:100px;" src="${a[5]}" title="${a[1]}" /></a>
         <span style=" display:block; width:100%; float:left; text-align: center; line-height:1.5; white-space:nowrap; overflow:hidden; font-size:12px; margin-top:10px; text-overflow:ellipsis;">${a[1]}</span>
         <span onclick="delFile(${a[0]},'${a[2]}','${a[3]}')" style="display:block; width:18px; height:18px;  position:absolute; top:-10px; right:-10px; text-align:center; line-height:18px; background:#c00; border-radius:10px; border:1px solid #fff; font-size:10px; color:#fff; font-weight: bold; cursor:pointer;">×</span>
    </div>
   </c:forEach>
  </div>
  </c:if>
  
  <c:if test="${isView eq 'true' }">
   <c:forEach items="${fileList}" var="a">
     <div style="position: relative; float: left; width: 100px; margin-left:30px; margin-top:20px;">
        <a href="javascript:downFile('${a[1]}','${a[2]}');" style=" display:block; width:100%; float:left; text-align: center;">
         <img style="width:100px;height:100px;" src="${a[5]}" title="${a[1]}" /></a>
         <span style=" display:block; width:100%; float:left; text-align: center; line-height:1.5; white-space:nowrap; overflow:hidden; font-size:12px; margin-top:10px; text-overflow:ellipsis;">${a[1]}</span>
    </div>
   </c:forEach>
  </c:if>
  
</form>

</body>
</html>