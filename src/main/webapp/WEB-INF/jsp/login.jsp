<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link href="<%=basePath%>/lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>/static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>/static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
    <title>后台登录 - 云打印监控系统</title>
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<div class="header" style="background-image:none!important;letter-spacing: 4px;font-size: 35px;color: #eff0ef;" ><span style="margin-left: 40%">得实云打印监控系统</span></div>
<div class="loginWraper">
    <div id="loginform" class="loginBox">
        <form id="form-member-add" class="form form-horizontal" >
       
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
                <div class="formControls col-xs-8">
                    <input name="username" type="text" placeholder="账户" class="input-text size-L" value="${username }">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
                <div class="formControls col-xs-8">
                    <input name="password" type="password" placeholder="密码" class="input-text size-L">
                </div>
            </div>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <input class="input-text size-L" name="validateCode" type="text" maxlength="4" placeholder="验证码" style="width:150px;">
                    <span id="span1"><img id="validateCode" onclick="changeImg();" src="validateCode" height="41px">&nbsp;&nbsp;&nbsp;&nbsp;</span><a id="kanbuq"  onclick="changeImg();" href="javascript:;">换一张</a> </div>
            </div>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <!--<label for="online">
                        <input type="checkbox" name="online" id="online" value="">
                        使我保持登录状态</label>-->
                </div>
            </div>
	 </form>   
            <div class="row cl"> 
                <div class="formControls col-xs-8 col-xs-offset-3">
                	 <p id="errorMes" style="visibility;color: red;margin-left: 10px;margin-bottom: 30px"></p>
                    <input name="" type="button" onclick="loginSubmit()" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
                    <input name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
                </div>
            </div>
    </div>
</div>
<div class="footer">版权所有 广州得实</div>
<script type="text/javascript" src="<%=basePath%>/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/h-ui/js/H-ui.min.js"></script>
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=basePath %>/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="<%=basePath %>/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="<%=basePath %>/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script>
	$(function (){
		reflaceOne();
		$("#errorMes").hide();
		$(function(){
			document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    //enter键
			    if(ev.keyCode==13) {
			    	loginSubmit();

			     }
				}
			}); 
	});
    //更换验证码
    function changeImg(){
    	 var explorer = window.navigator.userAgent.toLowerCase() ;
		 //firefox 
		  if (explorer.indexOf("firefox") >= 0) {
		     $("#validateCode").attr("src","validateCode?data=" + new Date() + Math.floor(Math.random()*24));
		 }
		 //Chrome
		 else if(explorer.indexOf("chrome") >= 0){
		 	 $("#validateCode").attr("src","validateCode?data=" + new Date() + Math.floor(Math.random()*24));
		 }else{
		 	//alert("ie");
		 	$("#span1").html("<img id='validateCode' height='41px' onclick='changeImg();' src=validateCode?data="+new Date().getMilliseconds()+Math.floor(Math.random()*24)+">&nbsp;&nbsp;&nbsp;&nbsp;");
		 }	 
	}
    function loginSubmit(){
    	$.ajax({
			type: "post",
			async: true,
			url: "loginSubmit",
			data:  $("form").serialize(),
			dataType: "json",
			success: function(res) {
				//console.log(res);
				if(res.code=="1000"){
					layer.msg("登录成功",{icon:1,time:1500});
					window.setTimeout(index,1500); 
					
				}
				if(res.code!="1000"){
					changeImg();
					$("#errorMes").show();
					$("#errorMes").html(res.msg);
				}
			},
			error: function() {
				changeImg();
				$("#errorMes").show();
				$("#errorMes").html(res.msg);
				//console.log("发生错误！");
			}
			
		});
    }
    
    function index(){ 
      
		window.location.href = "index";
	} 
    
    function reflaceOne(){
   		 var  flag = window.localStorage.getItem("flag");
   		 //alert(flag);
   		 if(flag=="null"||flag==null){
   		 	//alert("杀星一次");
       		window.localStorage.setItem("flag","true");
       		window.parent.location.reload();
    	}
    }
 
</script>
</body>
</html>