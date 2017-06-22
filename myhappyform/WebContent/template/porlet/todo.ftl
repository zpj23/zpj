<table id='datagrid_todo' class='easyui-datagrid' style='width:650px;height:auto' fit='true' border='false' singleSelect='true' idField='id' >
</table>

<script>
		bindTodo();
		function bindTodo()
		 {
			 	$('#datagrid_todo').datagrid({
					url: '/Core/HomeAction_ListJson4MainTodo',
					rowStyler:function(rowIndex,rowData){  
			            //任务完成100%， 并且已审核通过，背景色置红
			            if(rowData && rowData.stts && (rowData.stts2=='1'))  
			            {  
			                return 'color:#FF0000;';  
			            }  
			        },
					idField : 'seriNo',
					columns : [ [ {
						field : 'stts',
						title : '状态',
						width : 100
					},{
						field : 'title',
						title : '标题',
						width : 180,
						formatter : function(value, rowData, rowIndex) {
							var str ="<a href=\"javascript:oper('"+rowData.type+"','"+rowData.dealUrl+"')\">"+value+"</a>";
							return str;
						}
					},{
						field : 'type',
						title : '类型',
						width : 110
					},{
						field : 'sendDate',
						title : '发送日期',
						width : 150
					},{
						field : 'sendOrgDept',
						title : '发送单位',
						width : 100,
						formatter : function(value, rowData, rowIndex) {
							var str = rowData.sendOrgDept;
							return str;
						}
					}] ]
				});
		 }
</script>