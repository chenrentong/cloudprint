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
					<li class="user_information ">
						<label class="lead_name">基本信息</label>					
					</li>
					<li class="user_information second"></li>
					<li  class="user_information  ">
						<label>别名</label>
						<input type="text" value="${printer.alias }" />
					</li>
					<li class="user_information second">
						<label>是否在线</label>
						<input type="text" value="${printer.alive }" />
					</li>
					<li  class="user_information  ">
						<label>设备编号</label>
						<input type="text" value="${printer.number }" />
					</li>
					<li class="user_information second">
						<label>是否云打印机</label>
						<input type="text" value="${printer.cloud_prt }"/>
					</li>
					<li  class="user_information  ">
						<label>使用账户</label>
						<input type="text" value="${printer.using }" />
					</li>
					<li class="user_information second">
						<label>最近登录时间</label>
						<input type="text" value="<fmt:formatDate type="both"  pattern="yyyy-MM-dd HH:mm:ss" value="${printer.login_date }" />" />
						
					</li>
					<li  class="user_information  ">
						<label>注册时间</label>
						<input type="text" value="<fmt:formatDate type="both"  pattern="yyyy-MM-dd HH:mm:ss" value="${printer.reg_date }" />"/>
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
							<input type="text" value="${printer.status.main }"/>
					</li>
					<li  class="user_information  second">
							<label>子状态</label>
							<input type="text" value="${printer.status.sub  }"/>
					</li>
					<li class="user_information ">
							<label>时间</label>
							<input type="text" value="<fmt:formatDate type="both"  pattern="yyyy-MM-dd HH:mm:ss" value="${printer.status.newest }" />"/>
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
						<input type="text" value="${printer.info.sn }" />
					</li>
					<li class="user_information second">
							<label>型号</label>
							<input type="text" value="${printer.info.model }"/>
					</li>
					<li  class="user_information  ">
						<label>XDPI</label>
						<input type="text" value="${printer.info.XDPI }" />
					</li>
					<li class="user_information second">
							<label>YDPI</label>
							<input type="text" value="${printer.info.YDPI }"/>
					</li>
					<li  class="user_information  ">
						<label>页宽</label>
						<input type="text" value="${printer.info.pageWidth}" />
					</li>
					<li class="user_information second">
							<label>语言</label>
							<input type="text"  value="${printer.info.languag }"/>
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
						<input type="text" value="${printer.statistics.success+printer.statistics.failure }" />
					</li>
					<li class="user_information second">
						<label>打印成功页数</label>
						<c:if test="${empty printer.statistics.success}">
							<input type="text" value="0" />
						</c:if>
						<c:if test="${!empty printer.statistics.success}">
							<input type="text" value="${printer.statistics.success }" />
						</c:if>
					</li>
					<li  class="user_information  ">
						<label>失败页数</label>
						<c:if test="${empty printer.statistics.failure}">
							<input type="text" value="0"/>
						</c:if>
						<c:if test="${!empty printer.statistics.failure}">
							<input type="text" value="${printer.statistics.failure }"/>
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
						<select name="rank" style="margin-top: 0px; width: 320px;height: 35px;">
							<c:if test="${printer.alert.need }">
								<option selected="selected">是</option>
								<option>否</option>	
							</c:if>
							<c:if test="${!printer.alert.need }">
								<option>是</option>
								<option selected="selected">否</option>	
							</c:if>
						</select>

				</li>	
				<li  class="user_information  second"></li>
			</ul>
			
			<div>
			<!-- 邮箱列表 -->
				<label class="al">管理员邮箱列表<br><br><br></label>
				<textarea id="emails" readonly="readonly" spellcheck="false" class="a">${printer.alert.email}</textarea>
				<label class="al">添加邮箱</label>
				<input id="email" type="text" />
				<input type="button" onclick="addEmail()" class="btn btn-default radius" value="添加"/>
				<input type="button" onclick="delEmail()" class="btn btn-default radius" value="删除"/>
			</div>
			<div>
			<!-- 地址列表 -->
				<label class="al">HTTP地址列表 <br><br><br></label>
				<textarea id="https" readonly="readonly" spellcheck="false" class="a" >${printer.alert.url }</textarea>
				<label class="al">添加HTTP地址</label>
				<input id="http" type="text" />
				<input type="button" onclick="addHttp()" class="btn btn-default radius" value="添加"/>
				<input type="button" onclick="delHttp()" class="btn btn-default radius" value="删除"/>
			</div>			
		</div>
		
		 <div style="margin-bottom: 10px;margin-left: 900px" class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                <input name="button1" onclick="submit();" class="btn btn-primary radius" type="button"   value="&nbsp;&nbsp;修改&nbsp;&nbsp;">
            </div>
       </div>
       <!--_footer 作为公共模版分离出去-->
		<script type="text/javascript" src="<%=basePath %>/lib/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>/lib/layer/2.4/layer.js"></script>
		<script type="text/javascript" src="<%=basePath %>/static/h-ui/js/H-ui.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->
       <script type="text/javascript">
       		function addEmail(){
       			var before=$("#emails").val();
       			//要进行重复比较
       			var email=$("#email").val();
       			if(email!='' && before.indexOf(email) >= 0 ){
 					layer.msg("重复的邮箱地址！",{icon:5,time:1500});
				}else{
					var now;
       			var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
       			if(!myreg.test(email)){
       			 	layer.msg("请输入正确的邮箱地址！",{icon:5,time:1500});
       			}else{
       				if(before==""){
       					 now=email;
       				}else{
       			 		 now=before+"，"+email;
       				}
       				$("#email").val("");
       				$("#emails").val(now);
       			}
				}
       			
       			
       		}
       		function addHttp(){
       			var before=$("#https").val();
       			//要进行重复比较
       			var http=$("#http").val();
       			//对http进行校验
       			if(http!='' && before.indexOf(email) >= 0 ){
 					layer.msg("重复的HTTP地址！",{icon:5,time:1500});
				}else{
					var urlreg=/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/; 
	       			var now;
	       			if (!urlreg.test(http)){
	       				layer.msg("请输入正确的HTTP地址！",{icon:5,time:1500});
	       			}else{
	       				if(before==""){
	       					 now=http;
	       				}else{
	       			 		 now=before+"，"+http;
	       				}
	       				$("#http").val("");
	       				$("#https").val(now);
	       			}
				}		
       		}
       		function delHttp(){
 				var before=$("#https").val();
 				var strs= new Array(); //定义一数组 
				strs=before.split("，"); //字符分割 	
				var now="";
				for (var i=0;i<strs.length-1 ;i++ ) {
					if(i==0){
						now=now+strs[i];
					}else{
						now=now+"，"+strs[i];
					}
				}
 				$("#https").val(now);
       		}
       		function delEmail(){
				var before=$("#emails").val();
				strs=before.split("，"); //字符分割 	
 				var now="";
				for (var i=0;i<strs.length-1 ;i++ ) {
					if(i==0){
						now=now+strs[i];
					}else{
						now=now+"，"+strs[i];
					}
				}
				$("#emails").val(now);
       		}
       </script>
	</body>

</html>
