<table id="datagrid_onlineuser" class="easyui-datagrid" style="width:650px;height:auto" fit="true" border="false" singleSelect="true" idField="id" >
</table>



<script>
		bindOnlineUser();
		function bindOnlineUser(){
			 //alert("datagrid_onlineuser");
			 $('#datagrid_onlineuser').datagrid({
					url: '/Core/HomeAction_ListJson4OnlineUsers',
					idField : 'id',
					columns : [ [ {
						field : 'name',
						title : '用户姓名',
						width : 100
					},{
						field : 'loginTime',
						title : '登陆时间',
						width : 110
					},{
						field : 'loginIp',
						title : '登陆Ip',
						width : 110
					}] ]
			});
		 }
</script>