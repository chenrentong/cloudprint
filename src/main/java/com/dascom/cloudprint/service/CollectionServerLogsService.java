package com.dascom.cloudprint.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dascom.cloudprint.dao.CollectionLogsDao;
import com.dascom.cloudprint.dao.CollectionTcpLogsDao;
import com.dascom.cloudprint.entity.serverlog.CollectionLog4j2;
import com.dascom.cloudprint.util.PageBean;

@Service
public class CollectionServerLogsService {
	@Autowired 
	private CollectionTcpLogsDao collectionTcpLogsDao;
	
	public PageBean<CollectionLog4j2> findCloudprintUserByKey(String type,
			Date startTime, Date endTime, String level, Integer pageNumber,
			Integer pageSize) {
		if("".equals(level))
			level=null;
		
		PageBean<CollectionLog4j2> pageBean = new PageBean<CollectionLog4j2>();
		//设置当前页
		pageBean.setPage(pageNumber);
		//设置当前每页显示的记录数
		int limit = pageSize;
		pageBean.setLimit(limit);
		//设置总记录数
		int totalCount = 0;
		if("tcp".equals(type)){
			totalCount = collectionTcpLogsDao.findCountLogByNumberSort(startTime,endTime,level);
		}else if("".equals(type)){
			totalCount =0;
		}else{
			totalCount =0;
		}
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
		List<CollectionLog4j2> logList=new ArrayList<>();
		if("tcp".equals(type)){
			logList=collectionTcpLogsDao.findAllLogByNumberSort(startTime,endTime,level,begin, limit);
		}else if("".equals(type)){
			
		}else{
			
		}
		pageBean.setList(logList);
		return pageBean;
	}

	public CollectionLog4j2 findLogById(String type, String id) {
		if("tcp".equals(type)){
			return collectionTcpLogsDao.findById(id);
		}else if("interface".equals(type)){
			return null;
		}else{
			return null;
		}
	}
	
	public boolean delLogById(String id) {
		boolean result=false;
		try{
			collectionTcpLogsDao.delete(id);
			result=true;
		}catch(Exception e){

			e.printStackTrace();
		}
		return result;

		
	}

}
