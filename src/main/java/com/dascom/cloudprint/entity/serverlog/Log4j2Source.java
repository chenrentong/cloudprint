package com.dascom.cloudprint.entity.serverlog;

import org.springframework.data.mongodb.core.mapping.Field;

@Field("class")
public class Log4j2Source {
	private String className;
	private String methodName;
	private String fileName;
	private String lineNumber;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	
}
/*"source" : {
    "className" : "com.dascom.cloudPrintServer.modules.netty.ControlChannel",
    "methodName" : "userEventTriggered",
    "fileName" : "ControlChannel.java",
    "lineNumber" : 252
},*/