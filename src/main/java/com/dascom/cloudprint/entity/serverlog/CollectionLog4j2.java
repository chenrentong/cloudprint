package com.dascom.cloudprint.entity.serverlog;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 主要用于存储 tcp服务和接口服务的 日志文件
 * @author DS-033
 *
 */
@Document(collection="log4j2")
public class CollectionLog4j2 {
	@Id
	private String _id;
	private String level; //warn error info debug 等
	private String loggerName; //错误的类名
	private String message;  //消息
	private Log4j2Source source; //错误来源
	private String marker; // 标记
	private String threadId;//进程id
	private String threadName;//进程名字
	private String threadPriority; // 进程优先级
	private String millis; // 估计是时间搓
	private Date date;//时间
	private String thrown ; //不知道是什么
 	private Log4j2ContextMap contextMap; //内容map 
 	private Log4j2ContextStack contextStack;//内容堆
 	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getLoggerName() {
		return loggerName;
	}
	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Log4j2Source getSource() {
		return source;
	}
	public void setSource(Log4j2Source source) {
		this.source = source;
	}
	public String getMarker() {
		return marker;
	}
	public void setMarker(String marker) {
		this.marker = marker;
	}
	public String getThreadId() {
		return threadId;
	}
	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}
	public String getThreadName() {
		return threadName;
	}
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	public String getThreadPriority() {
		return threadPriority;
	}
	public void setThreadPriority(String threadPriority) {
		this.threadPriority = threadPriority;
	}
	public String getMillis() {
		return millis;
	}
	public void setMillis(String millis) {
		this.millis = millis;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getThrown() {
		return thrown;
	}
	public void setThrown(String thrown) {
		this.thrown = thrown;
	}
	public Log4j2ContextMap getContextMap() {
		return contextMap;
	}
	public void setContextMap(Log4j2ContextMap contextMap) {
		this.contextMap = contextMap;
	}
	public Log4j2ContextStack getContextStack() {
		return contextStack;
	}
	public void setContextStack(Log4j2ContextStack contextStack) {
		this.contextStack = contextStack;
	}
 	
 	
}
/*{
    "_id" : ObjectId("5a547f69096c421d64ae2d5c"),
    "level" : "INFO",
    "loggerName" : "com.dascom.cloudPrintServer.modules.netty.ControlChannel",
    "message" : "心跳，number为00171211000000发送0x03请求给设备",
    "source" : {
        "className" : "com.dascom.cloudPrintServer.modules.netty.ControlChannel",
        "methodName" : "userEventTriggered",
        "fileName" : "ControlChannel.java",
        "lineNumber" : 252
    },
    "marker" : null,
    "threadId" : NumberLong(28),
    "threadName" : "nioEventLoopGroup-3-7",
    "threadPriority" : 10,
    "millis" : NumberLong(1515487081671),
    "date" : ISODate("2018-01-09T08:38:01.671Z"),
    "thrown" : null,
    "contextMap" : {},
    "contextStack" : []
}*/