package com.dascom.cloudprint.dto;

import java.util.List;


public class PoolBatchJudgeDto {
	
	private String errorReason;
	private List<PoolBatchJudgeNumber> failureNumbers;
	
	public List<PoolBatchJudgeNumber> getFailureNumbers() {
		return failureNumbers;
	}

	public void setFailureNumbers(List<PoolBatchJudgeNumber> failureNumbers) {
		this.failureNumbers = failureNumbers;
	}

	public String getErrorReason() {
		return errorReason;
	}

	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

	
}

