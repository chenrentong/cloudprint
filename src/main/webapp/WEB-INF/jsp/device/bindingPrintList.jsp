<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <!--[if lt IE 9]>
    <script type="text/javascript" src="../../../lib/html5shiv.js"></script>
    <script type="text/javascript" src="../../../lib/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/static/h-ui/css/H-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/static/h-ui.admin/css/H-ui.admin.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/lib/Hui-iconfont/1.0.8/iconfont.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/static/h-ui.admin/skin/default/skin.css" id="skin" />
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/static/h-ui.admin/css/style.css" />
    <link rel="stylesheet" href="<%=basePath %>/backstage/css/modelList.css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="../../../lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <title>编号池管理</title>
</head>
<body>
<br>
<div class="page-container">
<!--  <div class="text-c">
        <form class="Huiform" method="get" action="cloudprintPool" target="_self">
       	 <input type="hidden" value="1"  name="page">	
            <input type="text" class="input-text" style="width:250px" placeholder="设备编号" name="key">
            <button type="submit" class="btn btn-success" name=""><i class="Hui-iconfont">&#xe665;</i> 搜编号</button>
        </form>
    </div> -->
      <form class="Huiform" method="get" action="printList" target="_self">
    <div class="text-c">
	 	 日期范围：
		<input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}' })" id="startTime" name="startTime" value="${startTime }" class="input-text Wdate" style="width:120px;">
		-
		<input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d' })" id="endTime"  name="endTime" value="${endTime }" class="input-text Wdate" style="width:120px;">
		<input type="text" class="input-text" style="width:250px" placeholder="设备编号" name="like" value="${like }">
		<button type="submit" class="btn btn-success" name=""><i class="Hui-iconfont">&#xe665;</i> 搜编号</button>
	</div>
	
	</form>
   <div class="cl pd-5 bg-1 bk-gray mt-20"> 
   <span class="l">
           <!--  <a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> -->
         <!--    <a href="javascript:;" onclick="javascript:;" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加编号</a> -->
        	</span>
    <span class="r">共有数据：<strong>${pageBean.totalCount}</strong> 条</span> 
    </div>
    <table class="table table-border table-bordered table-bg">
        <thead>
        <tr>
            <th scope="col" colspan="9">剩余编号列表</th>
        </tr>
        <tr class="text-c">
            <th width="5%"><input type="checkbox" name="" value=""></th>
            <th width="20%">序号</th>
            <th width="20%">设备编号</th>
            <th width="20%">注册时间</th>
          <!--   <th width="20%">操作</th> -->
        </tr>
        </thead>
        <tbody>
        <c:forEach var="vo" items="${pageBean.list}" varStatus="status">
        <tr class="text-c">
            <td><input type="checkbox" value="${vo.number}" name="batch"></td> 
            <td>${((pageBean.page - 1) * pageBean.limit+status.index+1)}</td>
            <td>${vo.number }</td>
            <td>
            <c:if test="${vo.reg_date==null}">
            	暂未登录过
            </c:if>
            <c:if test="${vo.reg_date!=null}">
            	<fmt:formatDate type="both"  pattern="yyyy-MM-dd HH:mm:ss" value="${vo.reg_date}" />
            </c:if>
            
            </td>
           <%--  <td> 
               <a title="删除" href="javascript:;" onclick="classify_del(this,'${vo._id}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
            </td> --%>
         </tr>
         </c:forEach>
        </tbody>
    </table>
    <!--页数-->
		<c:if test="${!empty pageBean.list}">
			<div class="row page">
			 <div class="col-6 col-offset-5 printers-page">
		        <div class="page">        	
					<c:if test="${pageBean.page!=1}">
						<span ><a href="cloudprintPool?page=${pageBean.page-1}&timeMin=${timeMin}&timeMax=${timeMax}">上一页</a></span>
					</c:if>
					<c:if test="${pageBean.page==1}">
						<span >上一页</span>
					</c:if> 
					
					 
		            <div class="pageNum">
			           <c:forEach begin="${curPage}" end="${totalPage}" var="i">
			           
						 	<c:if test="${i==pageBean.page}">
						 		<a href="javascript:;" class="onepage">${i }</a>
						 	</c:if >
						 	
						 	<c:if test="${i!=pageBean.page}">
						 		<a href="cloudprintPool?page=${i }&timeMin=${timeMin}&timeMax=${timeMax}">${i }</a>
						 	</c:if>	
						</c:forEach> 
		            </div>
		             
					 <c:if test="${pageBean.page!=pageBean.totalPage}">
						<span ><a href="cloudprintPool?page=${pageBean.page+1}&timeMin=${timeMin}&timeMax=${timeMax}">下一页</a></span>
					</c:if>
					<c:if test="${pageBean.page==pageBean.totalPage}">
						<span >下一页</span>
					</c:if> 
		        </div>
    		</div>
		</div>
		</c:if>
    <input onclick="binding()" class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;绑定设备&nbsp;&nbsp;">
</div>

	
		

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=basePath %>/lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="<%=basePath %>/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=basePath %>/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=basePath %>/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript">

	//绑定设备
	function binding(){
		var chk_value =[]; 
		$('input[name="batch"]:checked').each(function(){ 
		chk_value.push($(this).val()); 
		
		}); 
		if(chk_value.length==0){
			alert('你还没有选择任何内容');
		}else{
			console.log(chk_value);
			$.ajax({
		        type: "get",
		        url: "bindingPrint",
		    	data:{'numberList':chk_value+""},
		        dataType: "json",
		        success: function (date )
		        {
					console.log(date);
					var index = parent.layer.getFrameIndex(window.name);
					//关闭弹出层
					parent.layer.close(index);
		        },
		        error:function (date) {      
		        	console.log("请求失败！");
		        	var index = parent.layer.getFrameIndex(window.name);
			        //关闭弹出层
			        parent.layer.close(index);
		        }
		     }); 
		}
	}

</script>
</body>
</html>
