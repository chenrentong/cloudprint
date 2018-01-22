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
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/backstage/css/H-ui.min.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/backstage/css/H-ui.admin.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/backstage/iconfont/1.0.8/iconfont.css" />
		<link rel="stylesheet" href="<%=basePath %>/backstage/css/modelList.css" />
		
		<title>云打印管理</title>
	</head>

	<body>
		<nav class="breadcrumb">
			<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 云设备管理 <span class="c-gray en">&gt;</span> 云打印列表
			<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
		</nav>
		<div class="page-container">
			<div class="text-c clearfix">
				<p  class="Huiform f-l" style="width: 135px;height: 25px;border: 1px solid gainsboro;">刷新时间剩余：<span id="wait">10</span>s</p>
				<!--<form class="Huiform f-l" method="post" action="" target="_self">
					<!--<input type="text" class="input-text" style="width:120px;" placeholder="" name="key">
				</form>-->
				<div style=" margin-right:40%">
				<form class="Huiform f-r" method="post" action="cloudprintListByKeyAndLine?page=1" target="_self">
				<select name="line" class="input-text" style="width:150px;">
                       	<option  value="on" >在线</option>	
                       	<c:if test="${line=='out'}">
                       		<option selected="selected" value="out">离线</option>
                       	</c:if>
                       	<c:if test="${line=='on'}">
                       		<option  value="out">离线</option>
                       	</c:if>
                 		
            	</select>
					<input type="text" class="input-text" style="width:250px;" placeholder="设备编号" name="key">
					<button type="submit" class="btn btn-success" name=""><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
				</form>
				<input type="hidden" id="keyInfo" value="${key }" />
				<input type="hidden" id="pageInfo" value="${pageBean.page }" />
				<input type="hidden" id="lineInfo" value="${line}" />
				<input type="hidden" id="errorCode" value="${errorCode}" />
				</div>
			</div>
		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l">
          <!--   <a href="javascript:;" onclick="javascript:;" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加打印机</a> -->
        </span>
			<span class="r">打印机数量：<strong>${pageBean.totalCount} </strong> 台</span> 
		</div>
		
		<!--打印机型号-->
		<div class="row model clearfix">
			<div class="col-12 model-title">
			  <h3>打印机列表</h3>
			</div>
		 <c:forEach var="vo" items="${pageBean.list}" varStatus="status">
			<div class="col-3 col-md-3 col-sm-4 col-xs-12 modal-list">
				<ul class="modal-ul">
					<li><span style="color: #00ffff;margin-left: 150px ">云打印机</span> <span class="alive"></span></li>
					<li><span>别名：${vo.alias}</span> <span ></span></li>
					<li><span>是否在线：${vo.alive}</span> <span ></span></li>
					<li><span>设备编号：${vo.number}</span><span></span><p onclick="cloudprintInfo('打印机详情','cloudprintInfo','${vo._id}','1310','600')" class="details ">详情</p></li>
				 	<li><span>是否云打印机：${vo.cloud_prt }</span> <span></span><p onclick="cloudprintInfo('打印机编辑','cloudprintEdit','${vo._id}','1310','600')" class="details ">修改</p></li>
				 	<li><span>使用账户：${ vo.using}</span><span class="alive"></span><p onclick="classify_del(this,'${vo._id}')" class="details ">删除</p></li>  
                    <li><span>设备注册时间：</span> <span class="alive"></span></li>
                    <li><span><fmt:formatDate type="both"  pattern="yyyy-MM-dd HH:mm:ss" value="${vo.reg_date}" /></span> <span class="alive"></span></li>
                </ul>
			</div>
		</c:forEach>
		</div>
		<!--页数-->
		<c:if test="${!empty pageBean.list}">
			<div class="row page">
			 <div class="col-6 col-offset-5 printers-page">
		        <div class="page">
		       		
		        	<c:if test="${pageBean.page!=1}">
		        		<span ><a href="cloudprintListByKeyAndLine?page=1&key=${key}&line=${line}">首页</a></span> 
						<span ><a href="cloudprintListByKeyAndLine?page=${pageBean.page-1}&key=${key}&line=${line}">上一页</a></span>
					</c:if>
					
					<c:if test="${pageBean.page==1}">
						<span >首页 </span>
						<span >上一页 </span>

					</c:if> 
					
		            <div class="pageNum">
			            <c:forEach begin="${curPage}" end="${totalPage}" var="i">
						 	<c:if test="${i==pageBean.page}">
						 		<a href="javascript:;" class="onepage">${i }</a>
						 	</c:if >
						 	<c:if test="${pageBean.page!=i}">
						 		<a href="cloudprintListByKeyAndLine?page=${i }&key=${key}&line=${line}" >${i }</a>
						 	</c:if> 
						</c:forEach> 
		            </div>
		             
					 <c:if test="${pageBean.page!=pageBean.totalPage}">
						<span ><a href="cloudprintListByKeyAndLine?page=${pageBean.page+1}&key=${key}&line=${line}">下一页</a></span>
						<span ><a href="cloudprintListByKeyAndLine?page=${pageBean.totalPage }&key=${key}&line=${line}">尾页</a></span> 
					</c:if>
					<c:if test="${pageBean.page == pageBean.totalPage}">
						<span >下一页</span>
						<span >尾页</span>
					</c:if> 
					
		        </div>
    		</div>
		</div>
		</c:if>
	</div>
	<script type="text/javascript" src="<%=basePath%>/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/lib/layer/2.4/layer.js"></script>
	<script type="text/javascript" src="<%=basePath %>/static/h-ui/js/H-ui.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->
	<!--请在下方写此页面业务相关的脚本-->
	 <script type="text/javascript">
	 	var time=null;
	    $(function(){
	    	var page=$("#pageInfo").val();
			var key=$("#keyInfo").val();
			var line=$("#lineInfo").val();
			var errorCode=$("#errorCode").val();
				
			
			var second=9;
			
			time=setInterval (showTime, 1000);
			if(errorCode!=3000){
			
				error('错误详情','deviceError','','480');
			}
			
			function showTime(){ 
			
				if(second>=0){
					$("#wait").html(second);
				}

				if(second==0){
					//小于0服务器关闭了
					location.replace("cloudprintListByKeyAndLine?page="+page+"&key="+key+"&line="+line);   
					
				}else if(second<0){
					location.replace("errorService"); 
				}
				second--;
				
			}

	    });
    	/*-详情*/
   		function cloudprintInfo(title,url,id,w,h){
   		   	clearInterval(time);
   			var url=url+'?id='+id;
       		layer_show(title,url,w,h);
    	}
    	 /*报警*/
    	function error(title,url,w,h){
    	clearInterval(time);
        layer_show(title,url,w,h);
    }
     /*分类-删除*/
    function classify_del(obj,id){
        $count=$('.r strong').html();
        layer.confirm('确认要删除吗？',function(index){
            $.ajax({
                type: 'POST',
                url: 'cloudprintDel',
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
