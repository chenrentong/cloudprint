package com.dascom.cloudprint.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.dascom.cloudprint.entity.auth.CollectionUserOperation;
public class CollectionUsersOperationDao {
	
	private MongoTemplate mongoTemplate;

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public List<CollectionUserOperation> findAllPoolByUserName(String key,String sortKey,boolean sort,int begin,int limit){
		Query query = new Query();
		if(sort){
			query.with(new Sort(Direction.ASC, sortKey));
		}else{
			query.with(new Sort(Direction.DESC, sortKey));
		}
		query.addCriteria(Criteria.where("user_name").regex(".*?" +key+ ".*?")); 
		query.skip(begin);// 从那条记录开始
	    query.limit(limit);// 取多少条记录
		List<CollectionUserOperation> list= getMongoTemplate().find(query, CollectionUserOperation.class); 
		return list;
		
	}
	public int findAllCountByKey(String key){
		Query query = new Query();
		query.addCriteria(Criteria.where("user_name").regex(".*?" +key+ ".*?")); 
	    return (int) mongoTemplate.count(query, CollectionUserOperation.class);
	}

	public void delete(String id) {
		
		getMongoTemplate().remove(new Query(Criteria.where("_id").is(id)), CollectionUserOperation.class);
		
	}

	public void insert(CollectionUserOperation operation) {
		
		getMongoTemplate().insert(operation);     
	}

	public void update(CollectionUserOperation operation) {
		 Update update=new Update();
		 update.set("operation", operation.getOperation());
		 update.set("operation_time", operation.getOperation_time());
		 getMongoTemplate().updateFirst(new Query(Criteria.where("_id").is(operation.get_id())), update, CollectionUserOperation.class);
	}
	
	 //upsert更新，不存在就新增
	public void upsert(CollectionUserOperation operation) {
		 Query query = new Query();  
	     query.addCriteria(Criteria.where("_id").is(operation.get_id()));  
	     Update update = new Update();  
	     update.set("user_name", operation.getUser_name());  
	     update.set("operation", operation.getOperation());  
	     update.set("operation_time", operation.getOperation_time());  
	     mongoTemplate.upsert(query, update, CollectionUserOperation.class);  
	}

	public CollectionUserOperation findByUserName(String user_name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("user_name").is(user_name)); 
		return mongoTemplate.findOne(query, CollectionUserOperation.class);
	}
}
