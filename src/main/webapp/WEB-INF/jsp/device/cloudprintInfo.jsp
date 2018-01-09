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
						<label>别名</label>
						<input type="text" readonly="readonly" value="${printer.alias }" />
					</li>
					<li class="user_information second">
						<label>是否在线</label>
						<c:if test="${printer.alive }">
							<input type="text" readonly="readonly" value="是" />
						</c:if>
						<c:if test="${!printer.alive }">
							<input type="text" readonly="readonly" value="否" />
						</c:if>
					</li>
					<li  class="user_information  ">
						<label>设备编号</label>
						<input type="text" readonly="readonly" value="${printer.number }" />
					</li>
					<li class="user_information second">
						<label>是否云打印机</label>
						<c:if test="${printer.cloud_prt }">
							<input type="text" readonly="readonly" value="是" />
						</c:if>
						<c:if test="${!printer.cloud_prt }">
							<input type="text" readonly="readonly" value="否" />
						</c:if>
					</li>
					<li  class="user_information  ">
						<label>使用账户</label>
						<input type="text" readonly="readonly" value="${printer.using }" />
					</li>
					<li class="user_information second">
						<label>最近登录时间</label>
						<input type="text" readonly="readonly" value="<fmt:formatDate type="both"  pattern="yyyy-MM-dd HH:mm:ss" value="${printer.login_date }" />" />
						
					</li>
					<li  class="user_information  ">
						<label>注册时间</label>
						<input type="text" readonly="readonly" value="<fmt:formatDate type="both"  pattern="yyyy-MM-dd HH:mm:ss" value="${printer.reg_date }" />"/>
					</li>
					<li class="user_information second">
						<label></label>
						
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
						<label class="lead_name">打印机状态</label>
							
					</li>
					<li class="user_information second"></li>
					<li class="user_information ">
							<label>主状态</label>
							<input type="text" readonly="readonly" value="${printer.status.main }"/>
					</li>
					<li  class="user_information  second">
							<label>子状态</label>
							<input type="text" readonly="readonly" value="${printer.status.sub  }"/>
					</li>
					<li class="user_information ">
							<label>时间</label>
							<input type="text" readonly="readonly" value="<fmt:formatDate type="both"  pattern="yyyy-MM-dd HH:mm:ss" value="${printer.status.newest }" />"/>
					</li>
					<li  class="user_information  second">
							<label></label>
							
					</li>
					
					
				</ul>
			</div>
		</div>
		
		<!-------------打印机设备信息--------------->
		<div class="leading_official">
			
			<div class="representative">
				<ul class="information_content">
					<li class="user_information "></li>
					<li  class="user_information  second"></li>
					<li class="user_information ">
						<label class="lead_name">设备信息</label>					
					</li>
					<li class="user_information second"></li>
					
					<li  class="user_information  ">
						<label>序列号</label>
						<input type="text" readonly="readonly" value="${printer.info.sn }" />
					</li>
					<li class="user_information second">
							<label>型号</label>
							<input type="text" readonly="readonly" value="${printer.info.model }"/>
					</li>
					<li  class="user_information  ">
						<label>XDPI</label>
						<input type="text" readonly="readonly" value="${printer.info.XDPI }" />
					</li>
					<li class="user_information second">
							<label>YDPI</label>
							<input type="text" readonly="readonly" value="${printer.info.YDPI }"/>
					</li>
					<li  class="user_information  ">
						<label>页宽</label>
						<input type="text" readonly="readonly" value="${printer.info.pageWidth}" />
					</li>
					<li class="user_information second">
							<label>语言</label>
							<input type="text" readonly="readonly" value="${printer.info.languag }"/>
					</li>
				</ul>
			</div>
		</div>
		
		<!-------------打印机各信息--------------->
		<div class="leading_official">
			
			<div class="representative">
				<ul class="information_content">
					<li class="user_information "></li>
					<li  class="user_information  second"></li>
					<li class="user_information ">
						<label class="lead_name">统计信息</label>					
					</li>
					<li class="user_information second"></li>
					<li  class="user_information  ">
						<label>总页数</label>
						<input type="text" readonly="readonly" value="${printer.statistics.success+printer.statistics.failure }" />
					</li>
					<li class="user_information second">
						<label>打印成功页数</label>
						<c:if test="${empty printer.statistics.success}">
							<input readonly="readonly" type="text" value="0" />
						</c:if>
						<c:if test="${!empty printer.statistics.success}">
							<input readonly="readonly" type="text" value="${printer.statistics.success }" />
						</c:if>
						
					</li>
					<li  class="user_information  ">
						<label>失败页数</label>
						<c:if test="${empty printer.statistics.failure}">
							<input readonly="readonly" type="text" value="0"/>
						</c:if>
						<c:if test="${!empty printer.statistics.failure}">
							<input readonly="readonly" type="text" value="${printer.statistics.failure }"/>
						</c:if>
					</li>
					<li class="user_information second">
							
					</li>
					
				</ul>
			</div>
		</div>
		
		<!-------------警报手段--------------->
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
						<label>是否报警</label>
						<c:if test="${printer.alert.need }">
							<input type="text" readonly="readonly" value="是" />
						</c:if>
						<c:if test="${!printer.alert.need }">
							<input type="text" readonly="readonly" value="否" />
						</c:if>
						
				</li>	
				<li  class="user_information  second"></li>
			</ul>
			
			<div>
			<!-- 邮箱列表 -->
				<label class="al">管理员邮箱列表<br><br><br></label>
				<textarea readonly="readonly" spellcheck="false" class="a">${printer.alert.email}</textarea>
			</div>
			<div>
			<!-- 地址列表 -->
				<label class="al">HTTP地址列表 <br><br><br></label>
				<textarea readonly="readonly" spellcheck="false" class="a" >${printer.alert.url }</textarea>
			</div>			
		</div>
	</body>

</html>
