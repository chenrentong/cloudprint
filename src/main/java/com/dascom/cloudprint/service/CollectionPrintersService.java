package com.dascom.cloudprint.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dascom.cloudprint.dao.CollectionPrintersDao;
import com.dascom.cloudprint.entity.device.CollectionPrinters;
import com.dascom.cloudprint.util.PageBean;

@Service
public class CollectionPrintersService {
	@Autowired 
	private CollectionPrintersDao collectionPrintersDao;
	
	public PageBean<CollectionPrinters> findPrinterBylineAndKey(int page,boolean line,String number){	
		PageBean<CollectionPrinters> pageBean = new PageBean<CollectionPrinters>();
		//设置总页数
		pageBean.setPage(page);
		//设置当前每页显示的记录数
		int limit = 8;
		pageBean.setLimit(limit);
		//设置总记录数
		int totalCount = 0;
		totalCount = collectionPrintersDao.findCountAllByAliveAndNumber(line, number);
		pageBean.setTotalCount(totalCount);
		//设置总页数
		int totalPage = 0;
		if(totalCount % limit == 0)
		{
			totalPage = totalCount / limit;
		}else{
			totalPage = totalCount / limit + 1;
		}
		if(totalPage==0){
			totalPage=1;
		}
		pageBean.setTotalPage(totalPage);
		//从哪页开始
		int begin = (page - 1) * limit;
		//每页显示的数据集合
		List<CollectionPrinters> sharingList=collectionPrintersDao.findAllByAliveAndNumber(line, number,begin, limit);
		pageBean.setList(sharingList);
		return pageBean;
	
	}

	public CollectionPrinters findPrintersById(String id) {
		
		return collectionPrintersDao.findById(id);
		
	}

	public boolean findPrintersByNumberIsHave(String number) {
		boolean result=false;
		int i=collectionPrintersDao.findCountAllByNumber(number);
		if(i>0){
			result=true;
		}
		return result;
	}

	public boolean deletePrinters(String id) {
		boolean result=false;
		try{
			collectionPrintersDao.delete(id);
			result=true;
		}catch(Exception e){

			e.printStackTrace();
		}
		return result;
	
	}
	
	public boolean insertPrinters(CollectionPrinters printer){
		boolean result=false;
		try{
			collectionPrintersDao.insert(printer);
			result=true;
		}catch(Exception e){

			e.printStackTrace();
		}
		return result;
	}
}
