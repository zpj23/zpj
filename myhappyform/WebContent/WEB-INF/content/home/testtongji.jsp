<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="js/EasyUI/EasyUI1.3.6/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/EasyUI/EasyUI1.3.6/css/pannel.css" />
<script type="text/javascript" src="js/jquery/jquery-1.6.min.js"></script>
<script type="text/javascript" src="js/EasyUI/EasyUI1.3.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/EasyUI/EasyUI1.3.6/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/sys/home/home.js"></script>
<script src="js/Highcharts/highcharts.js"></script>

<script language="Javascript" src="js/FusionCharts3.2/FusionCharts.js"></script>
<script type="text/javascript" src="js/comm/makefusioncharts.js" ></script>
</head>
<body style="margin: 0px;"> 
      <div id="maintt" class="easyui-tabs" style="height:270px;" border="false">   
	    <div title="风险评估项目统计"  style="padding:2px;">
	          <div id="tj" style="height:230px;padding:0px; overflow:hidden;" ></div>
	    </div>   
	    <div title="单位稳评工作评比"  style="overflow:auto;padding:0px;">   
	           <div id="tt" class="easyui-tabs" style="height:230px;">   
				    <div title="重大工程"  style="padding:2px;">
				       <div id="tj1" style="height:190px;padding:2px; overflow:hidden;" ></div> 
				    </div>   
				    <div title="重大政策"  style="overflow:auto;padding:2px;">   
				        <div id="tj2" style="height:190px;padding:2px; overflow:hidden;" ></div>    
				    </div>   
				    <div title="其他事项"  style="padding:2px;">   
				       <div id="tj3" style="height:190px;padding:2px; overflow:hidden;" ></div>
				    </div>   
				</div>
	    </div>   
	</div>
<input  type="hidden"  name="naturetype" id="naturetype" />
<script>
	fchar.init("line","hnStatisticsAction_queryHomeTJFc","tj","100%","100%","","","","","","");
	fchar.init("bing","hnStatisticsAction_queryHomeTJ2Fc?naturetype=ZDXM","tj1","100%","100%","","","","","","");
	fchar.init("bing","hnStatisticsAction_queryHomeTJ2Fc?naturetype=ZDZC","tj2","100%","100%","","","","","","");
	fchar.init("bing","hnStatisticsAction_queryHomeTJ2Fc?naturetype=ZDJC","tj3","100%","100%","","","","","","");
	  
    $('#tt').tabs({    
	    border:false,    
	    onSelect:function(title,index){    
	        $("#naturetype").val(index);    
	    }    
	 });
</script>
</body>
</html>