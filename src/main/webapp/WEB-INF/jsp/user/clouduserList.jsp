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
    <title>用户管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 云用户管理 <span class="c-gray en">&gt;</span> 用户列表<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="text-c">
        <form class="Huiform" method="get" action="cloudprintUserListByKey" target="_self">
       	 <input type="hidden" value="1"  name="page">	
            <input type="text" class="input-text" style="width:250px" placeholder="邮箱名称" name="key">
            <button type="submit" class="btn btn-success" name=""><i class="Hui-iconfont">&#xe665;</i> 搜用户</button>
        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20"> 
   <span class="l">
            <a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
           <!--  <a href="javascript:;" onclick="javascript:;" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加用户</a> -->
        	</span>
    <span class="r">共有数据：<strong>${pageBean.totalCount}</strong> 条</span> 
    </div>
    <table class="table table-border table-bordered table-bg">
        <thead>
        <tr>
            <th scope="col" colspan="9">用户列表</th>
        </tr>
        <tr class="text-c">
            <th width="5%"><input type="checkbox" name="" value=""></th>
            <th width="12%">序号</th>
            <th width="12%">用户头像</th>
            <th width="12%">用户名</th>
            <th width="12%">邮箱</th>
            <th width="12%">手机</th>
            <th width="12%">是否禁用</th>
            <th width="12%">注册时间</th>
            <th width="12%">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="vo" items="${pageBean.list}" varStatus="status">
        <tr class="text-c">
            <td><input type="checkbox" value="${vo._id}" name="batch"></td>
            <td>${((pageBean.page - 1) * pageBean.limit+status.index+1)}</td>
         <%--    <td><img height="60" src="${vo.portrait}" alt=""></td> --%>
         	<td><img height="40" src="<%=basePath %>/backstage/img/1.jpg" alt=""></td>
            <td>${vo.user_name }</td>
            <td>${vo.email }</td>
            <td>${vo.mobile }</td>
            <c:if test="${vo.disabled }">
            	<td>禁用</td>
            </c:if>
            <c:if test="${!vo.disabled }">
            	<td>可用</td>
            </c:if>
            <td><fmt:formatDate type="both"  pattern="yyyy-MM-dd HH:mm:ss" value="${vo.register_date }" /></td>
            <td>
               <a title="详情" href="javascript:;" onclick="userInfo('用户详情','clouduserInfo','${vo._id}','1300','600')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe665;</i></a>
               <a title="绑定打印机" href="javascript:;" onclick="userInfo('绑定打印机','clouduserBinding','${vo._id}','1350','700')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
               <a title="删除" href="javascript:;" onclick="classify_del(this,'${vo._id}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
            </td>
         </tr>
         </c:forEach>
        </tbody>
    </table>
</div>

	<!--页数-->
		<c:if test="${!empty pageBean.list}">
			<div class="row page">
			 <div class="col-6 col-offset-5 printers-page">
		        <div class="page">
		       	 	
					<c:if test="${pageBean.page!=1}">
						<span ><a href="cloudprintUserListByKey?page=1&key=${key}">首页</a></span> 
						<span ><a href="cloudprintUserListByKey?page=${pageBean.page-1}&key=${key}">上一页</a></span>
					</c:if>
					<c:if test="${pageBean.page==1}">
						<span >首页</span>
						<span >上一页</span>
					</c:if> 
					
					 
		            <div class="pageNum">
			           <c:forEach begin="${curPage}" end="${totalPage}" var="i">					 	
						 	<c:if test="${i==pageBean.page}">
						 		<a href="javascript:;" class="onepage">${i }</a>
						 	</c:if >
						 	
						 	<c:if test="${i!=pageBean.page}">
						 		<a href="cloudprintUserListByKey?page=${i }&key=${key}">${i }</a>
						 	</c:if>	
						</c:forEach> 
		            </div>
		             
					 <c:if test="${pageBean.page!=pageBean.totalPage}">
						<span ><a href="cloudprintUserListByKey?page=${pageBean.page+1}&key=${key}">下一页</a></span>
						<span ><a href="cloudprintUserListByKey?page=${pageBean.totalPage }&key=${key}">尾页</a></span> 
					</c:if>
					<c:if test="${pageBean.page == pageBean.totalPage}">
						<span >下一页</span>
						<span >尾页</span>
					</c:if> 
					
		        </div>
    		</div>
		</div>
		</c:if>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=basePath %>/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=basePath %>/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=basePath %>/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript">
    /*
     参数解释：
     title	标题
     url		请求的url
     id		需要操作的数据id
     w		弹出层宽度（缺省调默认值）
     h		弹出层高度（缺省调默认值）
     */
    /*分类-添加*/
     function userInfo(title,url,id,w,h){
        var url=url+'?id='+id;
        layer_show(title,url,w,h);
    }
 
    function classify_add(title,url,w,h){
        layer_show(title,url,w,h);
    }
    /*分类-编辑*/
    function classify_edit(title,url,id,w,h){
        var url=url+'?id='+id;
        layer_show(title,url,w,h);
    }

    /*分类-删除*/
     function classify_del(obj,id){
        $count=$('.r strong').html();
        layer.confirm('确认要删除吗？',function(index){
            $.ajax({
                type: 'POST',
                url: 'cloudprintUserDel',
                data:{id:id},
                dataType: 'json',
              success: function(res){
               	if(res.code=="4005"){
					layer.msg(res.msg,{icon:1,time:1500});
					var time=setInterval (showTime, 1000);
					function showTime(){ 
						window.location.reload();
						clearInterval(time);
					} 
					
				}
				if(res.code!="4005"){
					layer.msg(res.msg,{icon:5,time:1500});
					var time=setInterval (showTime, 1000);
					function showTime(){ 
						window.location.reload();
						clearInterval(time);
					} 
				}
                },
                error:function(res) {
					layer.msg("系统错误",{icon:2,time:1500});
             	    var time=setInterval (showTime, 1000);

					function showTime(){ 
						window.location.reload();
						clearInterval(time);
					} 
                },
            });
        });
    }
    /*分类批量删除*/
    function datadel() {
        var id='';
        var arr=[];
        $("input[name='batch']").each(function () {
            var obj=$(this);
            if (obj.get(0).checked) {
                id+=','+obj.val();
                arr.push(obj);
            }
        });
        if (id=='') {
            layer.msg('请选择删除项');
            return false;
        }
        id=id.substring(1);
        classify_del(arr,id)
    }
</script>
</body>
</html>
