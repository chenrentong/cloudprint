package com.dascom.cloudprint.dto;

import java.util.List;

public class PrintNumberTakeDto {
	
	private String errorReason;
	private List<String> numbers;
	
	public String getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	public List<String> getNumbers() {
		return numbers;
	}
	public void setNumbers(List<String> numbers) {
		this.numbers = numbers;
	}
	
	
}
