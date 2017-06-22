<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>

<html>
	<head>
		<%@ include file="/common/meta.jsp"%>
		<title>首页</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/EasyUI/EasyUI1.3.6/themes/portal.css" />
		<link rel="stylesheet" type="text/css" href="js/EasyUI/EasyUI1.3.6/css/pannel.css" />
		<style type="text/css">
		.title{
			font-size:16px;
			font-weight:bold;
			padding:20px 10px;
			background:#eee;
			overflow:hidden;
			border-bottom:1px solid #ccc;
		}
		.t-list{
			padding:5px;
		}
	</style>
	    <script src="${ctx}/js/Highcharts/highcharts.js"></script>
	    <script src="${ctx}/js/Highcharts/highcharts-3d.js"></script>
        <script src="${ctx}/js/Highcharts/modules/exporting.js"></script>
        <script src="${ctx}/js/sys/home/statistical.js"></script>
        <script src="${ctx}/js/sys/home/zhendata.js"></script>
		<script src="<%=request.getContextPath() %>/js/EasyUI/EasyUI1.3.6/extension/jquery.portal.js" type="text/javascript"></script>
		<script>
		var panels;
	    var portal;
	    var myHeight = 200;
	    //在一起的用，分割，不在一起的用：分割
        var local = '${panelsCode}::';//'p_notice,p_todo,p_tj:p_grid,p_map';
        // 面板ID数组
        var panelIdArray=[],  columnNum = '${portalConfig.columnNum}'; 
		$(function(){
			
			
			panels = eval('${panelsJson}');

       	//监听portal状态改变事件、每次改变位置、获取当前改变后的位置状态、保存在cookie
  	         portal = $('#pp').portal({
  	             border: false,
  	             fit: true,
  	             onStateChange: function () {
  	            	
  	            	 //alert(getPanelsLoaction());
  	            	 
  	            	saveConfigInfo(getPanelsLoaction());
  				/*       $.cookie('portal_paenl_states', getPanelsLoaction(), {
  	                     expires: 7
  	                 }); */
  	             }
  	         });
  	      
	        if ('${portalConfig.objectConfigInfo}') {
	             local = '${portalConfig.objectConfigInfo}';
	         } 
	       
	         function getPanelById(id) {
	             for (var i = 0; i < panels.length; i++) {
	                 if (panels[i].id == id) {
	                     return panels[i];
	                 }
	             }
	             return undefined;
	         }
	        
	         //渲染panel
	         function renderPanel() {
	        	 
	             var cols = local.split(":");
	             for (var i = 0; i < cols.length; i++) {
	                 var rows = cols[i].split(",");
	                
	                   for (var j = 0; j < rows.length; j++) {
	                	 if(rows==""){ return; }
	                     var op = getPanelById(rows[j]);
	                     panelIdArray.push(op.id);
	                     var p = $('<div/>').attr('id', op.id).appendTo('body');
	                     p.panel(op);
	                     // 显示更多
	                     if(op.toolsStatus==1){
	                    	 p.panel({
	                    		 tools:[{
	                    			id:op.id,
	                    			iconCls:'icon-application-view-columns',
	             					handler:function(){ mores(this.id) }
	                    		 }]
	                    	 });
	                     }
	                     portal.portal('add', {
	                         panel: p,
	                         columnIndex: i
	                     });
	                 }   
	             }
	         }
	        
	         renderPanel();
	        
	         

            
	         //获取当前位置状态
	         function getPanelsLoaction() {
	             var locals = [];
	             var cols = local.split(":").length;
	             for (var i = 0; i < cols; i++) {
	                 var temp = [];
	                 var pl = portal.portal("getPanels", i);
	                 for (var j = 0; j < pl.length; j++) {
	                     temp.push(pl[j].attr("id"));
	                 }
	                 locals.push(temp.join(","));
	             }
	             return locals.join(':');
	         }
	         setHeight();
		});
		
		function oper(type,url){
			 url=url+"&flag=1"; //首页标识 
			 parent.Home.addTab("999", type, "", url, "href",true);
		}
		
		function saveConfigInfo(configInfo){
			$.ajax({
	            type: "post",
	            data:"configInfo="+configInfo,
	            url: "HomeAction_savePortalConfig",
	            success: function () {
	            }
			});
			
			portal.portal('resize');
		}
		
		function showWin(param){
			var titleStr = $('#'+param.id).parent().parent().text();
			var title_ = titleStr.substring(0, titleStr.indexOf('更多'));
			if(param.id =='a_p_todo'){
				window.parent.Home.addTab(param.id, title_, "", "HomeAction_todoMore?titleName="+title_, "href", true);
			}if(param.id == 'a_p_yj'){
				window.parent.Home.addTab(param.id, title_, "", "HomeAction_preMore?titleName="+title_, "href", true);
			}if(param.id == 'a_p_notice'){
				window.parent.Home.addTab(param.id, title_, "", "noticeAction_toNoticeList", "href", true);
			}
		}
		
		function setHeight(){
			
			var c = $('body');
            var p = c.layout('panel','center');
            var oldHeight = p.panel('panel').outerHeight();
            var cols = local.split(":");
            $.each(cols, function(k,v){
            	 $.each($('#pp').portal('getPanels',k), function(kk,vv){
                  	 $(vv).panel('resize',{height:(oldHeight-10)/2});
                  	 c.layout('resize',{
                  		height: oldHeight
                     });
                  });
            });
		}
		
		function mores(param){
			if(param=='p_todo'){
				window.parent.Home.addTab(1503, "稳评项目报备", "", "HnAction_showListIndex", "href", true);
			}else if(param=='p_tj'){
				window.parent.Home.addTab(1503, "项目统计", "", "HomeAction_indextongjidetail", "href", true);
			}else if(param=='p_sjtj'){
				window.parent.Home.addTab(1503, "统计图", "", "hnStatisticsAction_statisProjectkind", "href", true);
			}
		}
	</script>
	<style type="text/css">
	  ul,li {margin:0;padding:0;list-style:none;}
	</style>
</head>
<body class="easyui-layout">
<form action="HomeAction_savePorlets">
	<div id="div_portal_edit" region="north" class="title" border="false" style="height:80px;display: ${display};">
			<s:radio theme="simple" name="columnNum" list="#{'2':'两栏','3':'三栏' }" value="portalConfig.columnNum"></s:radio>
			<br>
			<s:checkboxlist theme="simple" name="porlets" list="allPorlets" listKey="code" listValue="name" value="portalConfig.objectInfo.split(',')" ></s:checkboxlist>

			<s:submit theme="simple" value="保存"></s:submit>
			<s:submit theme="simple" action="HomeAction_toHome" value="取消"></s:submit>
	</div>

<div region="center" border="false">
	<div id="pp" style="position:relative; overflow: hidden;" class="MyPortal">

			<s:bean name="org.apache.struts2.util.Counter" id="counter">
			   <s:param name="first" value="1" />
			   <s:param name="last" value="portalConfig.columnNum" />
			    <s:iterator>
			 	<div style="width:33%;">
				 
			    </div>
			    </s:iterator>
			</s:bean>

			
		   <!--  <div style="width:33%;">
		     
			</div>
			<div style="width:33%;">
		     
			</div> -->
		</div>
</div>

	 
</form>
</body>
</html>
<script>


// var tabData = {tableName_: 'stable_swry', tableGuid_: v.STABLE_SWRY_GUID, blobObjArray_: ''};
 $(window.parent.frames["jd_iframe_window"]).load(function(){
        // this.generic.BindingDB(tabData);
         //this.generic.disabled_();
  // this.generic.leaderRead();
        // this.generic.fileUpload('stable_swry',v.STABLE_SWRY_GUID,'${ctx}');
     });
</script>