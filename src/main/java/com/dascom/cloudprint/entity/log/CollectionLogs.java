package com.dascom.cloudprint.entity.log;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="collection_alert")
public class CollectionLogs {
	@Id
	private String _id;
	@Indexed(unique = true)
	private String number;
	private LogStatus status;
	private Date fire_date;
	private Date consume_date;
	private LogConsumeResult consume_result;

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

	public LogStatus getStatus() {
		return status;
	}

	public void setStatus(LogStatus status) {
		this.status = status;
	}

	public Date getFire_date() {
		return fire_date;
	}

	public void setFire_date(Date fire_date) {
		this.fire_date = fire_date;
	}

	public Date getConsume_date() {
		return consume_date;
	}

	public void setConsume_date(Date consume_date) {
		this.consume_date = consume_date;
	}

	public LogConsumeResult getConsume_result() {
		return consume_result;
	}

	public void setConsume_result(LogConsumeResult consume_result) {
		this.consume_result = consume_result;
	}

	@Override
	public String toString() {
		return "CollectionLogs [_id=" + _id + ", number=" + number
				+ ", status=" + status + ", fire_date=" + fire_date
				+ ", consume_date=" + consume_date + ", consume_result="
				+ consume_result + "]";
	}
	
	
}
