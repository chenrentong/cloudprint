package com.dascom.cloudprint.util;

public class PageBeanSlidingUtil {
	
	private int begin;
	private int end;
	
	//当前页、总页、滑动的条数
	public PageBeanSlidingUtil(int page, int totalPage,int size) {
		
		
		//当前页在前三页的时候   总页数>=5 显示1~5页  总页数<5的时候      1~总页数  
		
		//当前页在后三页时候 当前位置 在倒数3页的时候    总页数-5~总页数
		
		//当前页在中间的时候   当前页-2~当前页-3
		//如果是单数 
		/*size=size%2==0?size+1:size;
		int sizeto=size/2;
		
		if(page<=sizeto&&totalPage>size){
			begin=1;
			end=size;
		}else if(page<=sizeto&&totalPage<=size){
			begin=1;
			end=totalPage;
		}else if(page>=  totalPage-sizeto){
			begin=totalPage-size;
			end=totalPage;
		}else if(page>=sizeto&&page<=totalPage-sizeto){
			begin=page-sizeto;
			end=page+sizeto;
		}else{
			begin=1;
			end=totalPage;
		}*/
		
		
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
