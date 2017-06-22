<table style="width:650px;height:auto" fit="true" border="false" singleSelect="true" idField="WEIWXX_GUID" id="datagrid_yujing">
<thead><tr>
<th field="WEIWXX_GUID" align="left" width="200" checkbox="true">id</th>
<th field="SHIDMC" align="left" width="270" formatter="formatter_shidmc">名称</th>
<th field="FENLXX" align="left" width="230" >信息分类</th>
<th field="GUANKJB" align="left" width="100" >管控级别</th>
</tr> </thead>
</table>

<script>
		datagrid_yujing();
		// 信息预警
		 function datagrid_yujing(){
			$('#datagrid_yujing').datagrid({
				url: '${ctx}/HomeAction_queryHomeYuJing',
				queryParams : {tableName :'weiwxx', rowCount:4, formatterMap: "{\"FENLXX\":\"SW01\" , \"GUANKJB\":\"SW02\"}"}
			})
		 }
		 
		 function formatter_shidmc(rowIndex, rowData){
			 try{
				 if(rowData.GUANKJB.indexOf('红色')==0){
					 return rowData.SHIDMC+"<img src='${ctx}/images/shan/001.gif' width='20' height='22' align='absmiddle'>";
				 }if(rowData.GUANKJB.indexOf('橙色')==0){
					 return rowData.SHIDMC+"<img src='${ctx}/images/shan/002.gif' width='20' height='22' align='absmiddle'>";
				 }if(rowData.GUANKJB.indexOf('黄色')==0){
					 return rowData.SHIDMC+"<img src='${ctx}/images/shan/003.gif' width='20' height='22' align='absmiddle'>";
				 }if(rowData.GUANKJB.indexOf('蓝色')==0){
					 return rowData.SHIDMC+"<img src='${ctx}/images/shan/004.gif' width='20' height='22' align='absmiddle'>";
				 } 
			 }catch(e){
				 return rowData.SHIDMC;
			 }
		 }
</script>