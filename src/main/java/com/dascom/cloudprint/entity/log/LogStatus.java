package com.dascom.cloudprint.entity.log;


public class LogStatus {
	
	private String main;
	private String sub;
	
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
	@Override
	public String toString() {
		return "LogStatus [main=" + main + ", sub=" + sub + "]";
	}
	
}
