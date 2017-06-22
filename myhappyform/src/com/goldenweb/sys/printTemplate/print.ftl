<html><br> 
<head><br> 
<style>
.s_left{float: left; margin-left:5%; line-height: 5px; font-size:21px; font-family:楷体}
.s_right{float: right; margin-right:5%; line-height: 5px; font-size:21px; font-family:楷体}
table{
	border-collapse: collapse;
}
table td{
	border:1px solid white;
	font-family: 楷体;
	font-size:20px;
}
</style>
<title></title><br> 
</head><br> 
<body>
<br> 
<br>
<center>
<p class="f_time">
<table style="width:100%;">
<tr><td style="width:75%;text-align:left;">${org}</td><td style="width:23%;text-align:right;">${time}</td></tr>
</table>
<div style="font-size:20px; text-align: center;width:100%;font-family: 方正小标宋简体; font-weight: bold;padding-top:40px;padding-bottom:40px;">${title}</div>
</center>
<#list srcs as src>
	<center style="text-indent: 0em;"><img id="abc" alt="" src="${src}" width="680" height="480"></center>
	<div style="height:15px;"></div>
</#list>


<div style="font-family: 仿宋;font-size:22px;text-align:left;line-height:30px;">${content}</div>
<div style="height:0px;">&nbsp;</div>
 
</body> 
</html> 