package com.dascom.cloudprint.entity.log;

import java.util.List;

public class LogConsumeResult {
	private String result;
	private List email;
	private List url;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List getEmail() {
		return email;
	}
	public void setEmail(List email) {
		this.email = email;
	}
	public List getUrl() {
		return url;
	}
	public void setUrl(List url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "LogConsumeResult [result=" + result + ", email=" + email
				+ ", url=" + url + "]";
	}
	
	
}
