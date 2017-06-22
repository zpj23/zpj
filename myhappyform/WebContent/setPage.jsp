<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table class="flip" width="100%" border="0" cellpadding="5"
	cellspacing="0">
	<c:if test="${infoCount ne 0}">
		<tr>
			<td align="right">
				<c:if test="${page eq 1}">
					<font color="gray"><u>上一页</u></font>
				</c:if>
				<c:if test="${page ne 1}">
					<font color="gray"><a href="#"
							onclick="javascript:document.all.page.value=${page-1};form1.submit();">上一页</a></font>
				</c:if>
				&nbsp;
				<c:if test="${page ne pageNum and pageNum ne 1}"><font color="gray"><a href="#"
							onclick="javascript:document.all.page.value=${page+1};form1.submit();">下一页</a></font>
				</c:if>
				<c:if test="${page eq pageNum or pageNum eq 1}"><font color="gray"><u>下一页</u></font>
				</c:if>
				<font color="#666666"><!--第${(page-1)*pageSize+1}-${infoNum}条/总共${infoCount}条&nbsp;-->第${page}页/总共${pageNum}页 </font>
				<font color="#717376">跳转<input type="text" name="topage" id="topage" style="width:35px;" onkeydown> 页 </font> 
				<font color="#717376">条数 </font>
				<select name="pageSize" onchange="document.all.page.value = 1;form1.submit();">
					<option value="10" ${pageSize eq 10?'selected':''}>
						10
					</option>
					<option value="14" ${pageSize eq 14?'selected':''}>
						14
					</option>
					<option value="20" ${pageSize eq 20?'selected':''}>
						20
					</option>
					<option value="30" ${pageSize eq 30?'selected':''}>
						30
					</option>
					<option value="40" ${pageSize eq 40?'selected':''}>
						40
					</option>
					<option value="50" ${pageSize eq 50?'selected':''}>
						50
					</option>
				</select>
				<!--<script language="javascript" for="document" event="onkeydown">
	if (event.keyCode == 13 && event.srcElement.type != 'button'
			&& event.srcElement.type != 'submit'
			&& event.srcElement.type != 'reset'
			&& event.srcElement.type != 'textarea'
			&& event.srcElement.type != '') {
		var cc = document.all.pageNum.value;
		if (document.all.topage.value == '') {
			return false;
		} else if (isNaN(document.all.topage.value)) {
			alert('<spring:message code="INPUT_NUMBER" />!');
			document.all.topage.value = "";
			document.all.topage.focus();
			return false;
		} else if (parseInt(document.all.topage.value) <= 0
				|| parseInt(document.all.topage.value) > parseInt(cc)) {
			alert('<spring:message code="PAGE_ERROR" />');
			document.all.topage.value = "";
			document.all.topage.focus();
			return false;
		} else {
			document.all.page.value = document.all.topage.value;
			form1.submit();
		}
	} else if (event.keyCode == 116 || (event.ctrlKey && event.keyCode == 82)) {
		event.keyCode = 0;
		event.cancelBubble = true;
		return false;
	}
</script>
				-->
				
			<script language="javascript" >
       document.onkeydown = function(event) {
    event = event ? event : window.event;
    var keyCode = event.which ? event.which : event.keyCode;
    
        
        
        if (keyCode == 13 && event.srcElement.type != 'button'
			&& event.srcElement.type != 'submit'
			&& event.srcElement.type != 'reset'
			&& event.srcElement.type != 'textarea'
			&& event.srcElement.type != '') {
		var cc = document.all.pageNum.value;
		if (document.all.topage.value == '') {
			return false;
		} else if (isNaN(document.all.topage.value)) {
			alert('请输入数值!');
			document.all.topage.value = "";
			document.all.topage.focus();
			return false;
		} else if (parseInt(document.all.topage.value) <= 0
				|| parseInt(document.all.topage.value) > parseInt(cc)) {
			alert("您输入的数值有误!");
			document.all.topage.value = "";
			document.all.topage.focus();
			return false;
		} else {
			document.all.page.value = document.all.topage.value;
			form1.submit();
		}
	} else if (keyCode == 116 || (event.ctrlKey && keyCode == 82)) {
		event.keyCode = 0;
		event.cancelBubble = true;
		return false;
	}
}
     
</script>	
				
				
				
				<input type="hidden" name="pageNum" value="${pageNum}">
				<input type="hidden" name="page" value="${page}" id="page">
			</td>
		</tr>
	</c:if>
	<c:if test="${infoCount eq 0}">
		<tr>
			<td>

				<font color="blue">&nbsp;&nbsp;
				<!--<spring:message code="NORECORD" />
				--></font>
				<input type="hidden" name="pageNum" value="${pageNum}">
				<input type="hidden" name="page" value="${page}" id="page">
			</td>
		</tr>
	</c:if>
</table>