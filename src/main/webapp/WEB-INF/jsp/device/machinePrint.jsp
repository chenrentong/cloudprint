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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 云设备管理 <span class="c-gray en">&gt;</span> 设备打印<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<article class="page-container">
    <form action="/devctrl2/v1.6/dev/print/number" method="post" class="form form-horizontal" id="form-member-add" enctype="multipart/form-data">
    	  <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>是否网络打印：</label>
            <div class="formControls col-xs-8 col-sm-5">
                <select  onchange="budgetTypeSelect2(this.value)" class="input-text" style="width:150px;">  	 
                	 	<option value="0">是</option>
                	 	<option selected="selected" value="1">否</option>
                </select>
            </div>
        </div>
        <div id="bendi">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>编号：</label>
            <div class="form-label col-xs-4 col-sm-3">
                <input type="text" class="input-text"  placeholder="请输入设备编号" id="number"  value="00171211000027" >
            </div>
        </div>
        
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>打印纸宽：</label>
            <div class="form-label col-xs-4 col-sm-3">
                <input type="text" class="input-text"  placeholder="（210打印机设置为4.3英寸最为合适）" id="paperwidth"  name="paperwidth" value="4.3">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>打印纸长：</label>
            <div class="form-label col-xs-4 col-sm-3">
                <input type="text" class="input-text"  placeholder="请输入打印纸长" id="paperlength"  name="paperlength" value="7">
            </div>
        </div>
         <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>dpi值：</label>
            <div class="form-label col-xs-4 col-sm-3">
                <input type="text" class="input-text"  placeholder="请输入dpi值" id="dpi" name="dpi" value="203">
            </div>
        </div>
         <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>打印浓度：</label>
            <div class="formControls col-xs-8 col-sm-5">
                <!-- <input type="text" class="input-text"  placeholder="（范围0-30）" id="name" name="name"> -->
                <select id="concentration" name="concentration" class="input-text" style="width:150px;">
                	 <c:forEach begin="0" end="30" varStatus="status">
                	 	<option value="${status.index }">${status.index }</option>
                	 </c:forEach>
                </select>
            </div>
        </div>
         <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>图片阀值：</label>
            <div class="form-label col-xs-4 col-sm-3">
                <input type="text"  class="input-text"  placeholder="（178较为合适）" id="imagehreshold" name="imagehreshold" value="178">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">文件：</label>
            <!--<input type="file" name="file">-->
            <div class="formControls col-xs-8 col-sm-9">
                <span class="btn-upload form-group">
				<input  class="input-text upload-url" type="text"  id="uploadfile" readonly placeholder="请添加文件！" style="width:200px">
				<a href="javascript:void();" class="btn btn-primary radius upload-btn" ><i class="Hui-iconfont">&#xe642;</i> 浏览文件</a>
				<input type="file" onclick="$('#isPicture').hide();" onchange="_s()" multiple name="file" id="file-2" class="input-file">
				</span>
            </div>
        </div>
        </div>
        <div id="isPicture">
         <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">转换图片hdpi值：</label>
            <div class="form-label col-xs-4 col-sm-3">
                <input type="text" class="input-text"  placeholder="请输入转换图片hdpi值" id="hdpi" value="203">
            </div>
        </div>
         <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">转换图片vdpi值：</label>
            <div class="form-label col-xs-4 col-sm-3">
                <input type="text" class="input-text"  placeholder="请输入转换图片vdpi值" id="vdpi" value="203">
            </div>
        </div>
         <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">转换图片像素宽：</label>
            <div class="form-label col-xs-4 col-sm-3">
                <input type="text" class="input-text"  placeholder="请输入转换图片像素宽" id="wpx" value="1363">
            </div>
        </div>
         <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">转换图片像素宽高：</label>
            <div class="form-label col-xs-4 col-sm-3">
                <input type="text" class="input-text"  placeholder="请输入转换图片像素宽高" id="hpx" value="888">
            </div>
        </div>
        </div>
         <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">是否共享打印机：</label>
            <div class="formControls col-xs-8 col-sm-5">
                <select  onchange="budgetTypeSelect(this.value)" class="input-text" style="width:150px;">  	 
                	 	<option value="0">是</option>
                	 	<option value="1">否</option>
                </select>
            </div>
        </div>
        <div id="isShare">
         <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">用户id：</label>
            <div class="form-label col-xs-4 col-sm-3">
                <input type="text" class="input-text"  placeholder="请输入用户id" id="id" >
            </div>
        </div>
         <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">用户token：</label>
            <div class="form-label col-xs-4 col-sm-3">
                <input type="text" class="input-text"  placeholder="请输入用户token" id="token" >
            </div>
        </div>
        </div>
         <div id="isInternet">
         <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">网络图片地址：</label>
            <div class="form-label col-xs-4 col-sm-3">
                <input type="text" class="input-text"  placeholder="请输入网络图片地址" id="url" >
            </div>
        </div>
        </div>
        <div class="row cl" style="margin-top: 15px">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
            	<!-- <input onclick="preview()" class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;预览&nbsp;&nbsp;"> -->
                <input onclick="print()" class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;打印&nbsp;&nbsp;">
            </div>
        </div>
        </form>
       
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=basePath %>/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=basePath %>/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=basePath %>/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript">
	$("#isShare").hide();
	$("#isPicture").hide();
	$("#isInternet").hide();
	var type="";
  	function budgetTypeSelect(val){
  		if(val==1){
  			$("#isShare").hide();
  		}else{
  			$("#isShare").show();
  		}
  	}
  	
  	function budgetTypeSelect2(val){
  		if(val==0){
  			$("#isInternet").show();
  			/* $("#bendi").hide(); */
 
  		}else{
  			$("#url").val("");
  		  	/* $("#bendi").show(); */
  			$("#isInternet").hide();
  			type="";
  		}
  	}

	 function _s() {  
        var f = document.getElementById("file-2").files;  
        //上次修改时间  
        //alert(f[0].lastModifiedDate);  
         //名称  
        //alert(f[0].name);  
        //大小 字节  
       //alert(f[0].size);  
       //类型  
         if(f[0].type == undefined){

         }else if(f[0].type=="image/jpeg" || f[0].type=="image/png" ){
         	type="application/jpg";
        	$("#isPicture").hide();
        	
        }else if(f[0].type=="application/pdf" ){
        	type="application/pdf";
        	$("#isPicture").show();
        	
        }else if(f[0].type=="application/msword" || f[0].type== "application/vnd.ms-powerpoint" || f[0].type=="application/vnd.ms-excel"){
        	//微软word,powerpoint,execel文档,97~03
        	type="application/msoffice";
        	$("#isPicture").show();
        	
        }else if(f[0].type=="application/vnd.openxmlformats-officedocument.wordprocessingml.document" || f[0].type== "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" || f[0].type=="application/vnd.openxmlformats-officedocument.presentationml.presentation"){
      		//微软word,powerpoint,execel文档,07
      		type="application/msoffice";
      		$("#isPicture").show();
       
        }else{ 
        	//alert(f[0].type);
       		$("#isPicture").hide();
        	layer.msg("不支持该格式："+f[0].type,{icon:2,time:2000});
        }
    }  

    /*打印*/
    function print(){
    	var oMyForm = new FormData();
    	var number=$("#number").val();
    	var paperwidth=$("#paperwidth").val();
		oMyForm.append("paperwidth",paperwidth);
		var paperlength=$("#paperlength").val();
		oMyForm.append("paperlength",paperlength); 
		var dpi=$("#dpi").val();
		oMyForm.append("dpi", dpi); 
		oMyForm.append("concentration", $("#concentration option:selected").val());
		var imagehreshold= $("#imagehreshold").val(); 
		oMyForm.append("imagehreshold",imagehreshold); 
		if(number==''|| paperwidth==''|| paperlength==''||dpi==''||imagehreshold==''){
			layer.msg("必须参数不能为空",{icon:2,time:1500});
			return;
		}
		
		oMyForm.append("hdpi", $("#hdpi").val()); 
		oMyForm.append("vdpi", $("#vdpi").val()); 
		oMyForm.append("wpx", $("#wpx").val()); 
		oMyForm.append("hpx", $("#hpx").val()); 
		oMyForm.append("id", $("#id").val()); 
		oMyForm.append("token", $("#token").val()); 	
		/*网络地址打印*/
		var url=$("#url").val();
		oMyForm.append("url", url); 
		if(url==''){
			oMyForm.append("file", document.getElementById("file-2").files[0]);
		}else{
			type="application/url";
		}
	
        layer.confirm('确认要打印吗？',function(index){
        	
            $.ajax({
                type: 'POST',
                url: 'http://119.23.64.104:8080/devctrl2/v1.6/dev/print/'+number,
                cache: false,
   				data: oMyForm,
    			processData: false,
   				contentType: false,
                success: function(res){
	               	if(res =="success"){
						layer.msg("打印成功",{icon:1,time:2000});
						/* var time=setInterval (showTime, 1000);
						function showTime(){ 
							window.location.reload();
							clearInterval(time);
						}  */
						
					}
                },
                error:function(res) {
                	var obj=JSON.parse(res.responseText);
					layer.msg(obj.error,{icon:2,time:2000});
             	    /* var time=setInterval (showTime, 1000);

					function showTime(){ 
						window.location.reload();
						clearInterval(time);
					}  */
                },
                beforeSend: function(xhr) {

       				 xhr.setRequestHeader("File-Type", type);
   				 }
            });
        });
    }
  

</script>
</body>
</html>
