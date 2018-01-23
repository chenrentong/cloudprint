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
    <title>系统管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 系统进程<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="text-c clearfix">
				<p  class="Huiform f-l" style="width: 135px;height: 25px;border: 1px solid gainsboro;">刷新时间剩余：<span id="wait">10</span>s</p>
		</div>		
    <div class="cl pd-5 bg-1 bk-gray mt-20"> 
    
    <span class="r">共有数据：<strong>${pageBean.totalCount}</strong> 条</span> 
    </div>
    <table class="table table-border table-bordered table-bg">
        <thead>
        <tr>
            <th scope="col" colspan="13">进程列表</th>
        </tr>
        <tr class="text-c">
            <th width="5%"><input type="checkbox" name="" value=""></th>
            <th width="5%">序号</th>
            <th width="8%">进程名称</th>
            <th width="8%">状态</th>
            <th width="8%">级别</th>
            <th width="8%">用户</th>
            <th width="8%">用户组</th>
            <th width="8%">虚拟内存(M)</th>
            <th width="8%">驻留内存(M)</th>
            <th width="8%">CPU时间</th>
            <th width="8%">启动时间</th>

            <th width="">所在路径</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="vo" items="${pageBean.list}" varStatus="status">
        <tr class="text-c">
            <td><input type="checkbox" value="${vo._id}" name="batch"></td>
            <td>${((pageBean.page - 1) * pageBean.limit+status.index+1)}</td>
            <td>${vo.number}</td>
            <td><fmt:formatDate type="both"  pattern="yyyy-MM-dd HH:mm:ss" value="${vo.fire_date }" /></td>
            <td><fmt:formatDate type="both"  pattern="yyyy-MM-dd HH:mm:ss" value="${vo.consume_date }" /></td>
            <td>
               <a title="详情" href="javascript:;" onclick="cloudlogInfo('日志详情','cloudlogInfo','${vo._id}','1300','600')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe665;</i></a>
               <a title="删除" href="javascript:;" onclick="classify_del(this,'${vo._id}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
            </td>
         </tr>
         </c:forEach>
        </tbody>
    </table>
</div>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=basePath %>/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=basePath %>/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=basePath %>/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript">
  
    /*分类-删除*/
    function classify_del(obj,id){
        $count=$('.r strong').html();
        layer.confirm('确认要删除吗？',function(index){
            $.ajax({
                type: 'POST',
                url: 'cloudlogDel',
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
    
</script>
</body>
</html>
