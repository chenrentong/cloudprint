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
						<label>设备编号</label>
						<input type="text" value="${log.number }" />
					</li>
					<li class="user_information second">
						<label>报警时间</label>
						<input type="text" value="<fmt:formatDate type="both"  pattern="yyyy-MM-dd HH:mm:ss" value="${log.fire_date }" />" />
					</li>
					<li  class="user_information  ">
						<label>处理时间</label>
						<input type="text" value="<fmt:formatDate type="both"  pattern="yyyy-MM-dd HH:mm:ss" value="${log.consume_date }" />" />
					</li>
					<li class="user_information second">
						
					</li>
					
				</ul>
			</div>
		</div>
		
		<!-----状态------>
		<div class="leading_official">
			<div class="representative">
				<ul class="information_content">
					<li class="user_information "></li>
					<li  class="user_information  second"></li>
					<li class="user_information ">
						<label class="lead_name">日志状态</label>
							
					</li>
					<li class="user_information second"></li>
					<li class="user_information ">
							<label>主状态</label>
							<input type="text" value="${log.status.main }"/>
					</li>
					<li  class="user_information  second">
							<label>子状态</label>
							<input type="text" value="${log.status.sub  }"/>
					</li>
				</ul>
			</div>
		</div>
	
		<!-------------警报结果--------------->
		<div class="company_information">
			<ul class="information_content">
				<li class="user_information "></li>
				<li  class="user_information  second"></li>
				<li class="user_information ">
					<label class="lead_name">警报手段</label>
				</li>
				<li  class="user_information  second"></li>
			
				<!-- 是否报警 -->
				<li class="user_information ">
						<label>结果</label>
						<c:if test="${log.consume_result.result }">
							<input type="text" readonly="readonly" value="是" />
						</c:if>
						<c:if test="${!log.consume_result.result }">
							<input type="text" readonly="readonly" value="否" />
						</c:if>
				</li>	
				<li  class="user_information  second"></li>
			</ul>
			
			<div>
			<!-- 邮箱列表 -->
				<label class="al">管理员邮箱列表  <br><br><br></label>
				<textarea spellcheck="false" class="a">${log.consume_result.email}</textarea>
			</div>
			<div>
			<!-- 地址列表 -->
				<label class="al">HTTP地址列表  <br><br><br></label>
				<textarea spellcheck="false" class="a" >${log.consume_result.url }</textarea>
			</div>			
		</div>
	</body>

</html>
