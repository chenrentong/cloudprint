package com.dascom.cloudprint.util;

public class PageBeanSlidingUtil {
	
	private int begin;
	private int end;
	
	//当前页、总页、滑动的条数
	public PageBeanSlidingUtil(int page, int totalPage,int size) {
		
		if(totalPage < size - 1){
			
			end = totalPage;
			
		}else if(page + size <= totalPage){
			
			end = page +size;
			
		}else{
			
			end = totalPage;
		}
		if(totalPage - size >= page){
			
			begin = page;
			
		}else if(totalPage - size < 1 ){
			
			begin = 1;
			
		}else{
			
			begin = totalPage - size;
		}
	}

	public int getBegin() {
		
		return begin;
	}

	public int getEnd() {
		
		return end;
	}
	
}
