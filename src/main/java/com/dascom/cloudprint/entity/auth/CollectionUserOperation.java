package com.dascom.cloudprint.entity.auth;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 主要用于存储每个账户的操作信息。
 * user_name唯一
 * operation_time是TTL索引
 */
@Document(collection="collection_user_operation")
public class CollectionUserOperation {
	@Id
	private String _id;
	private String user_name;
	private String operation;
	private Date operation_time;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Date getOperation_time() {
		return operation_time;
	}
	public void setOperation_time(Date operation_time) {
		this.operation_time = operation_time;
	}
	@Override
	public String toString() {
		return "CollectionUserOperation [_id=" + _id + ", user_name="
				+ user_name + ", operation=" + operation + ", operation_time="
				+ operation_time + "]";
	}
	
	
}
