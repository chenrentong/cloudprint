package com.dascom.cloudprint.entity.auth;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 主要用于存储整个平台的账户。
 * user_name唯一
 * 邮箱唯一
 * password是散列
 */
@Document(collection="collection_users")
public class CollectionUsers {
	@Id
	private String _id;
	@Indexed(unique = true)
	private String user_name;
	//是一个散列值
	private String password;
	@Indexed(unique = true)
	private String email;
	private String mobile;
	private String devices;
	private Date register_date;
	private boolean disabled;
	private String portrait;	//头像
	private CollectionUsersRole role;
	private String user_data;	//暂定
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	public String getDevices() {
		return devices;
	}
	public void setDevices(String devices) {
		this.devices = devices;
	}
	public Date getRegister_date() {
		return register_date;
	}
	public void setRegister_date(Date register_date) {
		this.register_date = register_date;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public CollectionUsersRole getRole() {
		return role;
	}
	public void setRole(CollectionUsersRole role) {
		this.role = role;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public String getUser_data() {
		return user_data;
	}
	public void setUser_data(String user_data) {
		this.user_data = user_data;
	}
	@Override
	public String toString() {
		return "CollectionUsers [_id=" + _id + ", user_name=" + user_name
				+ ", password=" + password + ", email=" + email + ", mobile="
				+ mobile + ", devices=" + devices + ", register_date="
				+ register_date + ", disabled=" + disabled + ", portrait="
				+ portrait + ", role=" + role + ", user_data=" + user_data
				+ "]";
	}
	
}
