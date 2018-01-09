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
	</head>

	<body>
		<!-------------基本信息--------------->
		<div class="leading_official">
			
			<div class="representative">
				<ul class="information_content">
					<li class="user_information "></li>
					<li  class="user_information  second"></li>
					<li class="user_information ">
						<label class="lead_name">基本信息</label>					
					</li>
					<li class="user_information second"></li>
					<li  class="user_information  ">
						<label>用户名</label>
						<input type="text" value="${user.user_name }" />
					</li>
					<li class="user_information second">
						<label>邮箱</label>
						<input type="text" value="${user.email }" />
					</li>
					<li  class="user_information  ">
						<label>手机</label>
						<input type="text" value="${user.mobile }" />
					</li>
					<li class="user_information second">
						<label>拥有的设备</label>
						<input type="text" value="${user.devices }" />
						
					</li>
					<li  class="user_information  ">
						<label>是否可用</label>
						<c:if test="${user.disabled }">
							<input type="text" readonly="readonly" value="是" />
						</c:if>
						<c:if test="${!user.disabled }">
							<input type="text" readonly="readonly" value="否" />
						</c:if>
					</li>
					<li class="user_information second">
						<label>注册时间</label>
						<input type="text" value="<fmt:formatDate type="both"  pattern="yyyy-MM-dd HH:mm:ss" value="${user.register_date }" />"/>
					</li>
				</ul>
			</div>
		</div>
		
		
		<!-----角色------>
		<div class="leading_official">
			<div class="representative">
				<ul class="information_content">
					<li class="user_information "></li>
					<li  class="user_information  second"></li>
					<li class="user_information ">
						<label class="lead_name">用户角色</label>
							
					</li>
					<li class="user_information second"></li>
					<li class="user_information ">
							<label>角色类型</label>
							<input type="text" value="${user.role.category }"/>
					</li>
					<li  class="user_information  second">
							<label>子类别</label>
							<input type="text" value="${user.role.role  }"/>
					</li>
				</ul>
			</div>
		</div>		
		
	</body>

</html>
