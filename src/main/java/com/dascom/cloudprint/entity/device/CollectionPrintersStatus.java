package com.dascom.cloudprint.entity.device;


import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Field;

public class CollectionPrintersStatus {
	
	private String main;
	private String sub;
	private Date newest;
	
	public String getMain() {
		return main;
	}
	public void setMain(String main) {
		this.main = main;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	
	public Date getNewest() {
		return newest;
	}
	public void setNewest(Date newest) {
		this.newest = newest;
	}
	@Override
	public String toString() {
		return "CollectionPrintersStatus [main=" + main + ", sub=" + sub
				+ ", newest=" + newest + "]";
	}
	
	
}
