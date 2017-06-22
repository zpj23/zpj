<html>
<head> 
<title></title>
<style>
.f_title {
	text-align: center;
	font-size: 32px;
	color: blcak;
	font-family: SongTi;
	font-weight: bold;
	padding-top: 0px;
}

.f_time {
	text-align: right;
	padding-top: 10px;
	font-size: 18px;
	font-family: SongTi;
	margin-right: 12.2%;
	margin-bottom: 20px;
}
.TableBlock{
	border-collapse: collapse;
}
.TableBlock td{
	border:1px solid black;
	font-family: SongTi;
	font-size:20px;
}
.pl20{
	text-align: left;
	padding-left:20px;
}
p{text-align: center;font-family: SongTi;
	font-size:20px;}

</style> 
</head> 
<body>
<div class="b_">
	<h1 class="f_title">中共山东省委办公厅维稳协调工作办理单</h1>
	<p class="f_time">${mail.shouwenshijian} 编号: ${mail.bianhao }</p>	
	<div>
	<table class ="TableBlock" width= "100%" align ="center">
	<tr>
          <td style=" text-align: center;width:8%;">
          <br/>领<br/><br/>导<br/><br/>批<br/><br/>示<br/>&nbsp;</td>
		  <td colspan="5" class="pl20" style="text-align: left;">
          ${mail.pishi}
          </td>
          
           </tr>
    <tr>
          <td style=" text-align: center;width:8%;">
          <br/>批<br/><br/><br/><br/>示<br/>&nbsp;</td>
          <td colspan="5" class="pl20" style="text-align: left;">
          ${mail.shenqian}
          </td>
    </tr>
    <tr>
          <td style="text-align: center;width:8%;">
          <br/>拟<br/><br/>办<br/><br/>意<br/><br/>见<br/>&nbsp;</td>
          <td colspan="5" class="pl20" style="text-align: left;">
          ${mail.niban}
          </td>
    </tr>
    <tr>
          <td style="text-align: center;width:8%;">
          <br/>内<br/><br/>容<br/><br/>摘<br/><br/>要<br/>&nbsp;</td>
          <td colspan="5" class="pl20" style="text-align: left;">
          ${mail.neirong}
          </td>
    </tr>
    <tr>
          <td style="text-align: center;width:8%;">
         文件<br/>标题</td>
           <td colspan="5" class="pl20" style="text-align: left;border:1px solid black;">
          ${mail.biaoti}
          </td>
    </tr>
    <tr>
          <td style="text-align: center;width:8%;border:1px solid white;">
         </td>
           <td colspan="5" class="pl20" style="height:1px;text-align: left;border:1px solid white;">
         
          </td>
    </tr>
     <tr><td colspan="6" style="border:1px solid white;">
    <table width="100%;" style="border-collapse: collapse;">
     <tr>
     	 <td style="width:12%;border:1px solid white;padding:10px; text-align: left;">负责人:</td>
         <td style="width:20%;border:1px solid white;padding:10px; text-align: left;">${mail.fzr}</td>
         <td style="width:12%;border:1px solid white;padding:10px; text-align: left;">经办人:</td>
         <td style="width:20%;border:1px solid white;padding:10px; text-align: left;">${mail.jbr}</td>
         <td style="width:12%;border:1px solid white;padding:10px; text-align: right;">电话:</td>
         <td style="width:20%;border:1px solid white;padding:10px; text-align: left;">${mail.tel}</td>
    </tr>
    </table>
    </td></tr>
     <tr>
          <td colspan="6" style="border:1px solid white; text-align: right;">
         	中共山东省委办公厅维稳协调处
	</td>
    </tr>
    <tr>
          <td colspan="6" style="border:1px solid white; text-align: right;">
         	&nbsp;
	</td>
    </tr>
	</table> 
</html> 