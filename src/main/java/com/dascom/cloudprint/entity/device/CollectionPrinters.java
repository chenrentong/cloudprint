package com.dascom.cloudprint.entity.device;


import java.util.Date;
import java.util.List;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * 主要用于存储已注册的云打印机。
 * number设备编号唯一
 * */
@Document(collection="collection_printers")
public class CollectionPrinters {
	@Id
	private String _id;
	//private String sn;
	private boolean alive;
	private boolean cloud_prt;
	//private List using;
	private String using;
	private List owner;	
	private CollectionPrintersStatus status;
	@Indexed(unique = true)
	private String number;
	private Date reg_date;
	private Date login_date;
	private CollectionPrintersAlert alert;
	private String alias;
	private CollectionPrintersInfo info;
	private CollectionPrintersStatistics statistics;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public boolean isCloud_prt() {
		return cloud_prt;
	}
	public void setCloud_prt(boolean cloud_prt) {
		this.cloud_prt = cloud_prt;
	}
	
	public CollectionPrintersStatistics getStatistics() {
		return statistics;
	}
	public void setStatistics(CollectionPrintersStatistics statistics) {
		this.statistics = statistics;
	}
	/*public List getUsing() {
		return using;
	}
	public void setUsing(List using) {
		this.using = using;
	}*/
	
	public List getOwner() {
		return owner;
	}
	public String getUsing() {
		return using;
	}
	public void setUsing(String using) {
		this.using = using;
	}
	public void setOwner(List owner) {
		this.owner = owner;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public Date getReg_date() {
		return reg_date;
	}
	
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	
	public Date getLogin_date() {
		return login_date;
	}
	
	public void setLogin_date(Date login_date) {
		this.login_date = login_date;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public CollectionPrintersStatus getStatus() {
		return status;
	}
	
	public void setStatus(CollectionPrintersStatus status) {
		this.status = status;
	}
	
	public CollectionPrintersInfo getInfo() {
		return info;
	}
	
	public void setInfo(CollectionPrintersInfo info) {
		this.info = info;
	}
	
	public CollectionPrintersAlert getAlert() {
		return alert;
	}
	public void setAlert(CollectionPrintersAlert alert) {
		this.alert = alert;
	}
	@Override
	public String toString() {
		return "CollectionPrinters [_id=" + _id + ", alive=" + alive
				+ ", cloud_prt=" + cloud_prt + ", using=" + using + ", owner="
				+ owner + ", status=" + status + ", number=" + number
				+ ", reg_date=" + reg_date + ", login_date=" + login_date
				+ ", alert=" + alert + ", alias=" + alias + ", info=" + info
				+ ", statistics=" + statistics + "]";
	}
	
}
