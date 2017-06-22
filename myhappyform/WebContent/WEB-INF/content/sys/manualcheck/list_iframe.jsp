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
		url : 'jlManualCheckInfoAction_getListJson',
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
			field : 'departmentname',
			title : '所属地区',
			width : 20
		},{
			field : 'address',
			title : '施工项目及区域',
			width : 50
		},{
			field : 'workcontent',
			title : '工作内容',
			width : 100
		},{
			field : 'staffname',
			title : '施工人员',
			width : 20
		},{
			field : 'workdate',
			title : '施工日期',
			width : 30,
			formatter: function(value, rowData, rowIndex){
				var str=rowData.workdate;
				return str.substring(0,10);
			}
			
		},{
			field : 'workduringtime',
			title : '出勤时间（小时）',
			width : 50
		},{
			field : 'overtime',
			title : '加班时间（小时）',
			width : 50
		},{
			field : 'remark',
			title : '备注',
			width : 50
		}
		,{
			field : 'caoz',
			title : '操作',
			width : 50,
			formatter: function(value, rowData, rowIndex){
				var tempStr="";
				var col="black";
				var str= '<a title="编辑" href="javascript:;" onclick="edit(\''+rowData.id+'\')"  style="text-decoration:none">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a title="删除" href="javascript:;" onclick="del(\''+rowData.id+'\')"  style="text-decoration:none">删除</a>';
				return str;
			}
		}
		
		] ]
	});
});
/****编辑****/
function edit(id){
	parent.admin_add('编辑','jlManualCheckInfoAction_toAdd?id='+id,'800','650');
}
/*****删除*****/
function del(id){
	parent.admin_del(id);
}

function selectOneData(){
	var rowData=$("#datagrid").datagrid("getSelected");
	if(rowData!=null){
		var id=rowData.id;
		return id;
	}else{
		parent.layer.msg('请勾选一条数据!',{icon: 5,time:3000});
	}
}



function load(datemin,datemax,username,departmentid,address){
	datagrid.datagrid("load", { 
		datemin:datemin,
		datemax:datemax,
		username:username,
		departmentid:departmentid,
		address:address
	});
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