package com.dascom.cloudprint.dto;

import java.util.List;


public class PoolBatchJudgeParmDto {
	
	private List<String> numbers;

	private String remark;

	public List<String> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<String> numbers) {
		this.numbers = numbers;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "PoolBatchJudgeParmDto [numbers=" + numbers + ", remark="
				+ remark + "]";
	}


	
}
