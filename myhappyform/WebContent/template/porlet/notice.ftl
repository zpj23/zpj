<ul class="notice">
<#list noticeList as notice>
 	 <li>
    	<span style="float: right;margin-right:10px;">${notice.createTime?string("yyyy-MM-dd HH:mm:ss ")}</span>
		<a href="void(0);" onclick="toNoticeDetail('${notice.id}');return false;" style="width: 300px">${notice.title}</a>
	</li>
</#list>
</ul>


<script>
		function toNoticeDetail(id){
			 common.openWindow('查看通知公告', 'noticeAction_toViewNoticeIndex?id='+id, 780, 500);
		 }
</script>