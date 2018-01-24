<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>打印机详情</title>
		<link rel="stylesheet" href="<%=basePath %>/ds/css/base.css" />
		<link rel="stylesheet" href="<%=basePath %>/ds/css/public-style.css" />
		<link rel="stylesheet" href="<%=basePath %>/ds/css/corporate_search.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/static/h-ui/css/H-ui.min.css" />
	    <link rel="stylesheet" type="text/css" href="<%=basePath %>/static/h-ui.admin/css/H-ui.admin.css" />
	    <link rel="stylesheet" type="text/css" href="<%=basePath %>/lib/Hui-iconfont/1.0.8/iconfont.css" />
	    <link rel="stylesheet" type="text/css" href="<%=basePath %>/static/h-ui.admin/skin/default/skin.css" id="skin" />
	    <link rel="stylesheet" type="text/css" href="<%=basePath %>/static/h-ui.admin/css/style.css" />
	</head>

	<body>
		<!-------------基本信息--------------->
		<div class="leading_official">
			
			<div class="representative">
				<ul class="information_content">
					<li class="user_information "></li>
					<li  class="user_information  second"></li>
					<!-- <li class="user_information ">
						<label class="lead_name">基本信息</label>					
					</li>
					<li class="user_information second"></li> -->
					
					<%-- <li class="user_information ">
						<label>拥有的设备</label>
						<input type="text" value="${user.devices }" name="devices" />
						
					</li>
					<li class="user_information second"></li> --%>
					<li class="user_information ">
						<label></label>
						<input onclick="userInfo('','printList','${vo._id}','1300','600')" class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;选择设备&nbsp;&nbsp;">
					</li>
					<li class="user_information second"></li>
				</ul>
				
				<div>
			<!-- 邮箱列表 -->
				<label class="al">拥有的设备<br><br><br></label>
				<textarea   name="devices" class="a">${user.devices }</textarea>
			</div>
			</div>
					
		</div>
		
		
		
		
		
		<div class="leading_official">
			<div class="representative">
				<ul class="information_content">
					<li class="user_information ">
						<input onclick="edit()" class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;确定&nbsp;&nbsp;">
					</li>
				</ul>
			</div>
		</div>		
		
	</body>
	<script type="text/javascript" src="<%=basePath%>/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/lib/layer/2.4/layer.js"></script>
	<script type="text/javascript" src="<%=basePath %>/static/h-ui/js/H-ui.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->
<script type="text/javascript">
function userInfo(title,url,id,w,h){
    var url=url;
    layer_show(title,url,w,h);

}

//绑定设备
function edit(){
	$.ajax({
        type: "post",
        url: "clouduserBindingSumbit",
        data:{"devices":$("[name=devices]").val()},
        dataType: "json",
        success: function (date )
        {
        	console.log(date);
           if(date.code='1000'){
        	   alert(date.msg);
        	   var index = parent.layer.getFrameIndex(window.name);
   	        //关闭弹出层
   	        parent.layer.close(index);
           }
           if(date.code!='1000'){
				alert(date.msg);
           }
           
        },
        error:function (date) {      
        	console.log("请求失败！");
        	
        }
     });
        	
	
}


</script>
</html>
