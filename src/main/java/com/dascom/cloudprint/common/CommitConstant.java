package com.dascom.cloudprint.common;

public interface CommitConstant {
	
	//系统内部
	String OPERATIONINSERTSUCCESSFUL="4001";
	String OPERATIONINSERTSUCCESSFULMSG="插入成功";
	
	String OPERATIONINSERTFAILURE="4002";
	String OPERATIONINSERTFAILUREMSG="插入失败";
	
	String OPERATIONUPDATESUCCESSFUL="4003";
	String OPERATIONUPDATESUCCESSFULMSG="修改成功";
	
	String OPERATIONUPDATEFAILURE="4004";
	String OPERATIONUPDATEFAILUREMSG="修改失败";
	
	String OPERATIONDELETESUCCESSFUL="4005";
	String OPERATIONDELETESUCCESSFULMSG="删除成功";
	
	String OPERATIONDELETEFAILURE="4006";
	String OPERATIONDELETEFAILUREMSG="删除失败";
		
	String FIELDSHAVENULL="4007";
	String FIELDSHAVENULLMSG="你还有信息没填写";
	
	String FIELDILLEGAL="4008";
	String FIELDILLEGALMSG="填写信息不合法";
		
	String UPLOADFAILED="4010"; 
	String UPLOADFAILEDMSG="资源上传失败";
	
	String NUMBERFAILED="4011"; 
	String NUMBERFAILEDMSG="生成设备编号失败";
	
	String SYSTEMERROR="4009";
	String SYSTEMERRORMSG="系统错误";
	
	String LOGRECORDFAILED="4015";
	String LOGRECORDFAILEDMSG="日志记录失败";
	
	//批处理接口
	String BATCHSUCCESSFUL="4012"; 
	String BATCHSUCCESSFULMSG="批处理完成";
	
	String BATCHFAILURE4013="4013"; 
	String BATCHFAILURE4013MSG="插入失败,云设备表已存在";
	
	String BATCHFAILURE4014="4014"; 
	String BATCHFAILURE4014MSG="插入失败,设备池已存在";
}
