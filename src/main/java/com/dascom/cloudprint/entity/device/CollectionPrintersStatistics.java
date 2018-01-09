package com.dascom.cloudprint.entity.device;

public class CollectionPrintersStatistics {
	private int success;
	private int failure;
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
	public int getFailure() {
		return failure;
	}
	public void setFailure(int failure) {
		this.failure = failure;
	}
	@Override
	public String toString() {
		return "CollectionPrintersStatistics [success=" + success
				+ ", failure=" + failure + "]";
	}
	
	
}
