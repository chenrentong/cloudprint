package com.dascom.cloudprint.entity.device;

public class CollectionPrintersAlert {
	private String email;	//管理员邮箱列表，以\r\n分割
	private String url;		//HTTP地址列表，以\r\n分割
	private boolean need;	//是否报警
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isNeed() {
		return need;
	}
	public void setNeed(boolean need) {
		this.need = need;
	}
	@Override
	public String toString() {
		return "CollectionPrintersAlert [email=" + email + ", url=" + url
				+ ", need=" + need + "]";
	}
	
}
