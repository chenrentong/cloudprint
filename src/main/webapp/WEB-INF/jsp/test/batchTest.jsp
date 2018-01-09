<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'batchTest.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    This is my test page. <br>
	<div style="margin-top: 10px"> 参 数：<textarea id="canshu" style="resize:none"  rows="10" cols="200">{"numbers":["111","111","111"],"remark":"备注"}</textarea></div>  
	<div style="margin-top: 10px"> 返回值：<textarea id="fanhui" style="resize:none"  rows="10" cols="200"></textarea></div> 
	<input style="margin-top: 10px" onclick="loginSubmit()"onclick="loginSubmit()" type="button" value="提交">
  </body>
  <script type="text/javascript" src="<%=basePath%>/lib/jquery/1.9.1/jquery.min.js"></script>
  <script type="text/javascript">
	//$("#hasnext2").text('YES');
  	function loginSubmit(){
		var a=$("#canshu").val();
		console.log(a);
		var b=$("#fanhui");
    	$.ajax({
			type: "post",
			url: "common/printPoolJudge",
			data:a,
			dataType: "json",
			contentType:"application/json", 
			success: function(res) {
				var date=JSON.stringify(res);
				//console.log(res);
				b.val(date);
			},
			error: function() {
				b.val("发生错误");			
				//console.log("发生错误！");
			}
			
		});
    }
  </script>
</html>
