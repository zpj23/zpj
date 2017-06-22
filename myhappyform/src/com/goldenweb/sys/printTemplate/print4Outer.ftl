<!DOCTYPE html>
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css"
	href="js/EasyUI/EasyUI1.3.6/css/style_fxpg.css" />
<style>
* {
	padding: 0px;
	margin: 0px;
	list-style: none;
}

.f_title {
	text-align: center;
	font-size: 40px;
	color: black;
	font-family: SongTi;
	font-weight: bold;
}

.f_time {
	text-align: right;
	font-size: 18px;
	font-family: SongTi;
	margin-right: 12.2%;
	margin-bottom: 20px;
}

.b_ {
	margin-left: 5%;
	margin-right: 5%;
}

.div_s {
	border-bottom: 2px solid black;
	height: 30px;
	font-family: SongTi;
	font-size: 18px;
	overflow: auto;
}

.div_s_t {
	border-bottom: 2px solid black;
	height: 30px;
	font-family: SongTi;
	font-size: 18px;
}

.div_h210 {
	height: 150px;
}

.div_h50 {
	height: 50px;
}

.div_b_b_n {
	border-bottom: none;
}

span{
	font-family: SongTi;
	font-size: 18px;
	padding-top: 20px;
}

.div_c {
	float: none;
	padding-left: 36px;
}

.td_t{
	font-family: SongTi;
	font-size: 18px;
	padding: 20 0 20 0;
}
table td{
	text-align: left;
	font-size: 18px;
	font-family: SongTi;
}
</style>
</head>
<body>
	<div class="b_">
		<h1 class="f_title">
			中共山东省委办公厅<br />维稳协调工作文电签批单
		</h1>
		<table style="width:100%;">
		<tr>
		<td style="width:10%;">
			密级：
		</td>
		<td style="width:30%;">
		<#if out??>
          	<#if out.miji == "FX3001">
          		绝密
          	</#if>
          	<#if out.miji == "FX3002">
          		机密
          	</#if>
          	<#if out.miji == "FX3003">
          		秘密
          	</#if>
          	<#if out.miji == "FX3004">
          		普通
          	</#if>
         </#if>
          </td>
          	<td style="width:15%;">
          	紧急程度：
          	</td>
          	<td style="width:30%;">
          <#if out??>
          	<#if out.jinji == "FX2901">
          		特急
          	</#if>
          	<#if out.jinji == "FX2902">
          		紧急
          	</#if>
          	<#if out.jinji == "FX2903">
          		平急
          	</#if>
          </#if>
          </td>
			<td style="width:15%;">
			编号：${out.wenhao }
			</td>
			</tr>
		</table>
		<div class="div_s div_h210">
			<span style="padding-top:20px;"><p style="height:10px;"></p>&nbsp;&nbsp;签发：<p style="height:10px;"></p> <div class="div_c">${out.qianfa }</div></span>
		</div>
		<div class="div_s div_h210">
			<span><p style="height:10px;"></p>&nbsp;&nbsp;审签：<p style="height:10px;"></p><div class="div_c">${out.shenqian }</div></span>
		</div>
		<div class="div_s div_h210">
			<span><p style="height:10px;"></p>&nbsp;&nbsp;审签：
          <p style="height:10px;"></p><div class="div_c">${out.pishi}</div>
          </span>
		</div>
		<table cellspacing="0" width="100%">
			<tr>
				<td style="border-bottom: 2px solid black;height:40px;" colspan="2"><span
					class="td_t">&nbsp;&nbsp;拟稿单位：${out.createOrg }</span></td>
				<td
					style="border-bottom: 2px solid black; border-left: 2px solid black;height:40px;"><span
					class="td_t">&nbsp;&nbsp;拟稿人：${out.createUsername }</span></td>
			</tr>
			<tr>
				<td
					style="border-bottom: 2px solid black; border-right: 2px solid black;height:40px;"><span
					class="td_t">&nbsp;&nbsp;负责人：${out.fzr }</span></td>
				<td style="border-bottom: 2px solid black;height:40px;"><span
					class="td_t">&nbsp;&nbsp;核稿人：${out.hgr }</span></td>
				<td
					style="border-bottom: 2px solid black; border-left: 2px solid black;height:40px;"><span
					class="td_t">&nbsp;&nbsp;联系电话：${out.tel }</span></td>
			</tr>
		</table>
		<div class="div_s div_h210">
			<span><p style="height:10px;"></p>&nbsp;&nbsp;标题：<p style="height:10px;"></p><div class="div_c">${out.title }</div></span>
		</div>
		<div class="div_s div_h50">
			<span><p style="height:10px;"></p>&nbsp;&nbsp;发往单位：${out.sendusers }</span>
		</div>
		<div class="div_s div_h50 div_b_b_n" style="float:right;width:150px;">
			<span style="width:150px;"><p style="height:10px;"></p>${out.status }</span>
		</div>
		</p>
	</div>
	</div>
</body>
</html>