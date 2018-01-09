package com.dascom.cloudprint.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dascom.cloudprint.dao.CollectionLogsDao;
import com.dascom.cloudprint.entity.log.CollectionLogs;
import com.dascom.cloudprint.util.PageBean;

@Service
public class CollectionLogsService {
	@Autowired 
	private CollectionLogsDao collectionLogsDao;
	
	public PageBean<CollectionLogs> findCloudprintUserByKey(int page,
			String key) {
		PageBean<CollectionLogs> pageBean = new PageBean<CollectionLogs>();
		//设置总页数
		pageBean.setPage(page);
		//设置当前每页显示的记录数
		int limit = 8;
		pageBean.setLimit(limit);
		//设置总记录数
		int totalCount = 0;
		totalCount = collectionLogsDao.findCountLogByNumberSort(key);
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
		List<CollectionLogs> logList=collectionLogsDao.findAllLogByNumberSort("fire_date",false, key ,begin, limit);
		pageBean.setList(logList);
		return pageBean;
	}

	public CollectionLogs findLogById(String id) {
		
		return collectionLogsDao.findById(id);
		
	}
	
	public boolean delLogById(String id) {
		boolean result=false;
		try{
			collectionLogsDao.delete(id);
			result=true;
		}catch(Exception e){

			e.printStackTrace();
		}
		return result;

		
	}
}
