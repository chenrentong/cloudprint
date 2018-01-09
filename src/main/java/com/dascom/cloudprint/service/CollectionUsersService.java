package com.dascom.cloudprint.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dascom.cloudprint.dao.CollectionUsersDao;
import com.dascom.cloudprint.entity.auth.CollectionUsers;
import com.dascom.cloudprint.entity.device.CollectionPrinters;
import com.dascom.cloudprint.util.PageBean;

@Service
public class CollectionUsersService {
	@Autowired 
	private CollectionUsersDao collectionUsersDao;
	
	public PageBean<CollectionUsers> findCloudprintUserByKey(int page,
			String key) {
		PageBean<CollectionUsers> pageBean = new PageBean<CollectionUsers>();
		//设置总页数
		pageBean.setPage(page);
		//设置当前每页显示的记录数
		int limit = 8;
		pageBean.setLimit(limit);
		//设置总记录数
		int totalCount = 0;
		totalCount = collectionUsersDao.findAllCountByKey(key);
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
		List<CollectionUsers> sharingList=collectionUsersDao.findAllByKey(key, begin, limit);
		pageBean.setList(sharingList);
		return pageBean;
	}

	public CollectionUsers findUserById(String id) {
		
		return collectionUsersDao.findById(id);
		
	}

	public boolean delUserById(String id) {
		boolean result=false;
		try{
			collectionUsersDao.delete(id);
			result=true;
		}catch(Exception e){

			e.printStackTrace();
		}
		return result;

	}
}
