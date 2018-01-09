<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="Bookmark" href="/favicon.ico" >
    <link rel="Shortcut Icon" href="/favicon.ico" />
    <!--[if lt IE 9]>
    <script type="text/javascript" src="../../../lib/html5shiv.js"></script>
    <script type="text/javascript" src="../../../lib/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/static/h-ui/css/H-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/static/h-ui.admin/css/H-ui.admin.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/lib/Hui-iconfont/1.0.8/iconfont.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/static/h-ui.admin/skin/default/skin.css" id="skin" />
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/static/h-ui.admin/css/style.css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="../../../lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <!--/meta 作为公共模版分离出去-->

    <title>设备编号添加</title>
</head>
<body>
<article class="page-container">
    <form class="form form-horizontal" >
       <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>项目：</label>
            <div class="form-label col-xs-4 col-sm-3"><span class="select-box">
				<select name="project" class="select">   
                 	<option value="weixin" >微信</option>
                </select>
				</span>
		 	</div>
       </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>产品：</label>
            <div class="form-label col-xs-4 col-sm-3"><span class="select-box">
				<select name="product" class="select">   
                 	<option  value="deshi" >得实</option>
                 	<option  value="hangxin" >航信</option>
                </select>
				</span>
		 	</div>
       </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>备注：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text"  placeholder="请输入备注" id="remark" name="remark">
            </div>
        </div>
	</form>
       <div style="margin-top: 10px" class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                <input name="button1" onclick="submit();" class="btn btn-primary radius" type="button"   value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
            </div>
       </div>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=basePath %>/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=basePath %>/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=basePath %>/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="<%=basePath %>/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="<%=basePath %>/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<!-- <script type="text/javascript">
    $(function(){
        $("#form-member-add").validate({
            rules:{
                remark:{
                    required:true,
                    minlength:2,
                    maxlength:16
                }
            }
        });
    });
</script> -->
<script>
	$(function(){
			document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    //enter键
			    if(ev.keyCode==13) {
			    	submit();

			     }
			}
	});  

    function submit(){
    	$.ajax({
			type: "post",
			async: true,
			url: "cloudprintPoolSummitAdd",
			data:  $("form").serialize(),
			dataType: "json",
			success: function(res) {
				if(res.code=="4001"){
					layer.msg(res.msg,{icon:1,time:1500});
					window.setTimeout(index,1500); 
				}
				if(res.code!="4001"){
				
					layer.msg(res.msg,{icon:5,time:1500});
				}
			},
			error: function() {
			
				layer.msg("发生错误！",{icon:2,time:1500});
			}
			
		});
    }
    
    function index(){  
    
		window.parent.location.reload();
	} 
    
   
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
