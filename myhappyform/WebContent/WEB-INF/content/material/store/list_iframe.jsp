<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript">
var datagrid;
$(document).ready(function(){
	datagrid = $('#datagrid');
	datagrid.datagrid({
		url : 'jlStoreAction_getStoreListJson',
		queryParams : {
			
		},
		rowStyler:function(rowIndex,rowData){  
            return 'height:55px;';  
        },
		idField : 'id',
		frozenColumns : [ [ {
			title : 'id',
			field : 'id',
			width : 50,
			checkbox : true
		} ] ],
		columns : [ [ {
			field : 'goodsname',
			title : '物资名称',
			width : 200
		},{
			field : 'suppliername',
			title : '供应商名称',
			width : 200
		},{
			field : 'num',
			title : '数量',
			width : 50
		},{
			field : 'unit',
			title : '单位',
			width : 50
		},{
			field : 'price',
			title : '库存金额',
			width : 50,
			formatter: function(value, rowData, rowIndex){
				var str=(parseFloat(rowData.num)*parseFloat(rowData.price)).toFixed(2)+"元";
				return str;
			}	
		},{
			field : 'updatetime',
			title : '更新时间',
			width : 100
		}
		
		] ]
	});
});




function load(datemin,datemax,username){
	datagrid.datagrid("load", { 
		datemin:datemin,
		datemax:datemax,
		username:username
	});
}

function openSelect(){
	var rowData=$("#datagrid").datagrid("getSelected");
	if(rowData!=null){
		var goodsid=rowData.goodsid;
		parent.inoutDetail(goodsid);
	}
}



</script>
</head>
<body>
<table id="datagrid" fit="true" fitColumns="true"
				style="height: auto; width: auto;" toolbar="" title=""
				pageSize="${ipagesize}" pageList="${ipagelist}"
				queryParams="" idField="id" border="false"
				rownumbers="true" singleSelect="true" pagination="true">
			</table>
</body>
</html>