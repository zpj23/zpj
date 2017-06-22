<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'init.jsp' starting page</title>
    <script type="text/javascript" src="js/jquery/jquery-1.6.min.js"></script>
	<script type="text/javascript">
	$().ready(function (){
	var url = '${url}';
	window.open(url,"_blank","");
	  
	});
	</script>

  </head>
  
  <body>
  
  </body>
</html>
