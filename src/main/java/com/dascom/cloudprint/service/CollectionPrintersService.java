package com.dascom.cloudprint.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dascom.cloudprint.dao.CollectionPrintersDao;
import com.dascom.cloudprint.entity.device.CollectionIdPool;
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

	/**
	 * 查询全部已经记录过的设备
	 * @param startTime2
	 * @param endTime2
	 * @param like
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageBean<CollectionPrinters> findPrinterBylineAndKey(
			Date startTime2, Date endTime2, String like, Integer pageNumber,
			Integer pageSize) {
		PageBean<CollectionPrinters> pageBean = new PageBean<CollectionPrinters>();
		//设置当前页数
		pageBean.setPage(pageNumber);
		//设置当前每页显示的记录数
		int limit = pageSize;
		pageBean.setLimit(limit);
		//设置总记录数
		int totalCount = 0;
		totalCount = collectionPrintersDao.findCountAllByAliveAndNumber( startTime2,  endTime2,  like);
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
		int begin = (pageNumber - 1) * limit;
		//每页显示的数据集合
		List<CollectionPrinters> sharingList=collectionPrintersDao.findAllByAliveAndNumber( startTime2,  endTime2,  like,begin, limit);
		pageBean.setList(sharingList);
		return pageBean;
	}
	

	public PageBean<CollectionPrinters> findRegisterNumberByKey(int page,
			String timeMin, String timeMax) throws ParseException {
		PageBean<CollectionPrinters> pageBean = new PageBean<CollectionPrinters>();
		//设置总页数
		pageBean.setPage(page);
		//设置当前每页显示的记录数
		int limit = 8;
		pageBean.setLimit(limit);
		//设置总记录数
		int totalCount = 0;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟   

		
		if(timeMin.equals("") && timeMax.equals("")){
			totalCount = collectionPrintersDao.findRegisterAllCount();
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
			List<CollectionPrinters> sharingList=collectionPrintersDao.findRegisterAllNumber( "upload_date", true, begin, limit);
			pageBean.setList(sharingList);
		}else if(timeMin.equals("") && !timeMax.equals("")){
			Date date2=sdf.parse(timeMax); 
			Calendar c = Calendar.getInstance(); 
	        c.setTime(date2);  
	        c.add(Calendar.DAY_OF_MONTH, 1);     
	        Date  beforedate2 = c.getTime();  
			totalCount = collectionPrintersDao.findRegisterAllCountByDate(null,null,null,beforedate2);
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
			List<CollectionPrinters> sharingList=collectionPrintersDao.findRegisterAllPoolByDate( "upload_date", true, begin, limit,null,null,null,beforedate2);
			pageBean.setList(sharingList);
			
		}else if(!timeMin.equals("") && timeMax.equals("")){
			Date date1=sdf.parse(timeMin);  

			totalCount = collectionPrintersDao.findRegisterAllCountByDate(null,null,date1);
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
			List<CollectionPrinters> sharingList=collectionPrintersDao.findRegisterAllPoolByDate( "upload_date", true, begin, limit,null,null,date1);
			pageBean.setList(sharingList);
			
		}else{
			Date date1=sdf.parse(timeMin);  
			Date date2=sdf.parse(timeMax); 
			Calendar c = Calendar.getInstance();  
	        c.setTime(date2);  
	        c.add(Calendar.DAY_OF_MONTH, 1);     
	        Date  beforedate2 = c.getTime();  
			totalCount = collectionPrintersDao.findRegisterAllCountByDate(date1,beforedate2);
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
			List<CollectionPrinters> sharingList=collectionPrintersDao.findRegisterAllPoolByDate( "upload_date", true, begin, limit,date1,beforedate2);
			pageBean.setList(sharingList);
		}
		
		return pageBean;
	}
}
