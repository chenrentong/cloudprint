package com.dascom.cloudprint.entity.device;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 主要用于存储未被使用过的设备编号。
 * number设备编号唯一
 * */
@Document(collection="collection_id_pool")
public class CollectionIdPool {
	@Id
	private String _id;
	@Indexed(unique = true)
	private String number;
	private Date upload_date;
	private String remark;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Date getUpload_date() {
		return upload_date;
	}
	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "CollectionIdPool [_id=" + _id + ", number=" + number
				+ ", upload_date=" + upload_date + ", remark=" + remark + "]";
	}
	
}
