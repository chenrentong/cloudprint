package com.dascom.cloudprint.service;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dascom.cloudprint.dao.CollectionUsersOperationDao;
import com.dascom.cloudprint.entity.auth.CollectionUserOperation;
import com.dascom.cloudprint.entity.auth.CollectionUsers;
import com.dascom.cloudprint.util.Logg;
import com.dascom.cloudprint.util.PageBean;

@Service
public class CollectionUsersOperationService {
	@Autowired 
	private CollectionUsersOperationDao collectionUsersOperationDao;
	
	public PageBean<CollectionUserOperation> findUserOperationByKey(int page,String userName){	
		PageBean<CollectionUserOperation> pageBean = new PageBean<CollectionUserOperation>();
		//设置总页数
		pageBean.setPage(page);
		//设置当前每页显示的记录数
		int limit = 8;
		pageBean.setLimit(limit);
		//设置总记录数
		int totalCount = 0;
		totalCount = collectionUsersOperationDao.findAllCountByKey(userName);
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
		List<CollectionUserOperation> sharingList=collectionUsersOperationDao.findAllPoolByUserName(userName, "operation_time", true, begin, limit);
		pageBean.setList(sharingList);
		return pageBean;
	
	}

	public boolean deluserOperationById(String id) {
		boolean result=false;
		try{
			collectionUsersOperationDao.delete(id);
			result=true;
		}catch(Exception e){

			e.printStackTrace();
		}
		return result;

	}
	
	public boolean insertOperation(CollectionUserOperation operation){
		boolean result=false;
		try{
			collectionUsersOperationDao.insert(operation);
			result=true;
		}catch(Exception e){

			e.printStackTrace();
		}
		return result;

	}

	public CollectionUserOperation findUserOperationByUserName(String user_name) {
		
		return collectionUsersOperationDao.findByUserName(user_name);
		
	}

	public boolean updateOperation(CollectionUserOperation operation) {
		boolean result=false;
		try{
			collectionUsersOperationDao.update(operation);
			result=true;
		}catch(Exception e){

			e.printStackTrace();
		}
		return result;
	}

	public boolean operationRecord (String operationMsg){
		Logg.writeDebugLog("进入了日志记录！,operationRecord:"+operationMsg);
		Subject sub =SecurityUtils.getSubject(); 
		CollectionUsers user = (CollectionUsers) sub.getPrincipal();
		if(user==null){
			Logg.writeErrorLog("无法获取当前用户！");
			return false;
		}
		CollectionUserOperation operation =new CollectionUserOperation();
		operation.setOperation(operationMsg);
		operation.setUser_name(user.getUser_name());
		operation.setOperation_time(new Date());	
		return insertOperation(operation);
					
	}
}
