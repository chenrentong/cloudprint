package com.dascom.cloudprint.entity.device;

public class CollectionPrintersInfo {
	private String sn;
	private String model;
	private Integer XDPI;
	private Integer YDPI;
	private String pageWidth;
	private String languag;
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Integer getXDPI() {
		return XDPI;
	}
	public void setXDPI(Integer xDPI) {
		XDPI = xDPI;
	}
	public Integer getYDPI() {
		return YDPI;
	}
	public void setYDPI(Integer yDPI) {
		YDPI = yDPI;
	}
	public String getPageWidth() {
		return pageWidth;
	}
	public void setPageWidth(String pageWidth) {
		this.pageWidth = pageWidth;
	}
	public String getLanguag() {
		return languag;
	}
	public void setLanguag(String languag) {
		this.languag = languag;
	}
	@Override
	public String toString() {
		return "CollectionPrintersInfo [sn=" + sn + ", model=" + model
				+ ", XDPI=" + XDPI + ", YDPI=" + YDPI + ", pageWidth="
				+ pageWidth + ", languag=" + languag + "]";
	}
	
	
}
