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
						<label>错误等级 </label>
						<input type="text" value="${log.level }" />
					</li>
					<li class="user_information second">
						<label>错误时间</label>
						<input type="text" value="<fmt:formatDate type="both"  pattern="yyyy-MM-dd HH:mm:ss" value="${log.date }" />" />
					</li>
				</ul>
			</div>
			<div>
			<!-- 邮箱列表 -->
				<label class="al">管理员邮箱列表  <br><br><br></label>
				<textarea spellcheck="false" class="a">${log.message}</textarea>
			</div>
		</div>
		
		
		<!-----状态------>
		<div class="leading_official">
			<div class="representative">
				<ul class="information_content">
					<li class="user_information "></li>
					<li  class="user_information  second"></li>
					<li class="user_information ">
						<label class="lead_name">错误源</label>
					</li>
					<li class="user_information second"></li>
					<li class="user_information ">
							<label>错误详情类</label>
							<input type="text" value="${log.source.className }"/>
					</li>
					<li  class="user_information  second">
							<label>错误类</label>
							<input type="text" value="${log.source.methodName }"/>
					</li>
					<li  class="user_information ">
							<label>错误方法</label>
							<input type="text" value="${log.source.fileName }"/>
					</li>
					<li  class="user_information  second">
							<label>错误行数</label>
							<input type="text" value="${log.source.lineNumber }"/>
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
					<label class="lead_name">错误线程</label>
				</li>
				<li  class="user_information  second"></li>
			
				<!-- 是否报警 -->
				<li class="user_information ">
						<label>线程id</label>
						<input type="text" readonly="readonly" value="${log.threadId }" />
				</li>	
				<li  class="user_information  second">
					<label>marker</label>
					<input type="text" readonly="readonly" value="${log.marker }" />
				</li>
				<li class="user_information ">
					<label>进程名</label>
					<input type="text" readonly="readonly" value="${log. threadName}" />
				</li>
				<li class="user_information second">
					<label>进程优先级</label>
					<input type="text" readonly="readonly" value="${log.threadPriority }" />
				</li>
				<li class="user_information ">
					<label>millis</label>
					<input type="text" readonly="readonly" value="${log.millis }" />
				</li>
				<li class="user_information second">
					<label>thrown</label>
					<input type="text" readonly="readonly" value="${log.thrown }" />
				</li>
			</ul>
			
			<%-- <div>
			<!-- 邮箱列表 -->
				<label class="al">管理员邮箱列表  <br><br><br></label>
				<textarea spellcheck="false" class="a">${log.consume_result.email}</textarea>
			</div>
			<div>
			<!-- 地址列表 -->
				<label class="al">HTTP地址列表  <br><br><br></label>
				<textarea spellcheck="false" class="a" >${log.consume_result.url }</textarea>
			</div>		 --%>	
		</div>
	</body>

</html>
