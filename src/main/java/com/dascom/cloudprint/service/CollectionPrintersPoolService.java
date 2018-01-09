package com.dascom.cloudprint.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.dascom.cloudprint.dao.CollectionPrintersPoolDao;
import com.dascom.cloudprint.entity.device.CollectionIdPool;
import com.dascom.cloudprint.entity.device.CollectionPrinters;
import com.dascom.cloudprint.util.PageBean;

@Service
public class CollectionPrintersPoolService {
	@Autowired 
	private CollectionPrintersPoolDao collectionPrintersPoolDao;
	@Autowired
	private CollectionPrintersService collectionPrintersService;
	
	public PageBean<CollectionIdPool> findPrinterPoolByKey(int page,String timeMin, String timeMax) throws ParseException{	
		PageBean<CollectionIdPool> pageBean = new PageBean<CollectionIdPool>();
		//设置总页数
		pageBean.setPage(page);
		//设置当前每页显示的记录数
		int limit = 8;
		pageBean.setLimit(limit);
		//设置总记录数
		int totalCount = 0;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟   

		
		if(timeMin.equals("") && timeMax.equals("")){
			totalCount = collectionPrintersPoolDao.findAllCount();
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
			List<CollectionIdPool> sharingList=collectionPrintersPoolDao.findAllPool( "upload_date", true, begin, limit);
			pageBean.setList(sharingList);
		}else if(timeMin.equals("") && !timeMax.equals("")){
			Date date2=sdf.parse(timeMax); 
			Calendar c = Calendar.getInstance(); 
	        c.setTime(date2);  
	        c.add(Calendar.DAY_OF_MONTH, 1);     
	        Date  beforedate2 = c.getTime();  
			totalCount = collectionPrintersPoolDao.findAllCountByDate(null,null,null,beforedate2);
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
			List<CollectionIdPool> sharingList=collectionPrintersPoolDao.findAllPoolByDate( "upload_date", true, begin, limit,null,null,null,beforedate2);
			pageBean.setList(sharingList);
			
		}else if(!timeMin.equals("") && timeMax.equals("")){
			Date date1=sdf.parse(timeMin);  

			totalCount = collectionPrintersPoolDao.findAllCountByDate(null,null,date1);
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
			List<CollectionIdPool> sharingList=collectionPrintersPoolDao.findAllPoolByDate( "upload_date", true, begin, limit,null,null,date1);
			pageBean.setList(sharingList);
			
		}else{
			Date date1=sdf.parse(timeMin);  
			Date date2=sdf.parse(timeMax); 
			Calendar c = Calendar.getInstance();  
	        c.setTime(date2);  
	        c.add(Calendar.DAY_OF_MONTH, 1);     
	        Date  beforedate2 = c.getTime();  
			totalCount = collectionPrintersPoolDao.findAllCountByDate(date1,beforedate2);
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
			List<CollectionIdPool> sharingList=collectionPrintersPoolDao.findAllPoolByDate( "upload_date", true, begin, limit,date1,beforedate2);
			pageBean.setList(sharingList);
		}
		
		return pageBean;
	
	}

	public boolean findPrintersByNumberIsHave(String number) {
		boolean result=false;
		int i=collectionPrintersPoolDao.findCountAllByNumber(number);
		if(i>0){
			result=true;
		}
		return result;
	}

	public boolean insertPrintersPool(CollectionIdPool pool) {
		boolean result=false;
		try{
			collectionPrintersPoolDao.insert(pool);
			result=true;
		}catch(Exception e){

			e.printStackTrace();
		}
		return result;
		
	}
	
	public boolean insertPrintersPoolAll(List<CollectionIdPool> list){
		boolean result=false;
		try{
			collectionPrintersPoolDao.insertAll(list);
			result=true;
		}catch(Exception e){

			e.printStackTrace();
		}
		return result;	
	}

	public boolean deletePrintersPool(String id) {
		boolean result=false;
		try{
			collectionPrintersPoolDao.delete(id);
			result=true;
		}catch(Exception e){

			e.printStackTrace();
		}
		return result;
	}
	
	public List<String> takePrinterPoolByCount(int count){
		//多拿十条
		List<CollectionIdPool> list=collectionPrintersPoolDao.findAllPool("upload_date", true, 0, count+10);
		Vector<String> verter=new Vector<String>();	
		for(CollectionIdPool item:list){
			CollectionPrinters printer =new CollectionPrinters();
			printer.setNumber(item.getNumber());
			printer.setReg_date(new Date());
			if(verter.size()<count){
				boolean delResult=deletePrintersPool(item.get_id());
				if(!delResult) continue;
				else{
					boolean insResult=collectionPrintersService.insertPrinters(printer);
					if(!insResult) continue;
					else verter.add(item.getNumber());
				}
			}else{
				return verter;
			}
		}
		if(verter.size()<count) return null;
		return verter;

	}
}
