package com.dascom.cloudprint.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.dascom.cloudprint.entity.log.CollectionLogs;
import com.mongodb.WriteResult;

public class CollectionLogsDao {
	
	private MongoTemplate mongoTemplate;
	
	public  List<CollectionLogs> findAllLogByNumberSort(String sortKey,boolean sort,String key,int begin,int limit) {
		Query query = new Query();
		if(sort){
			query.with(new Sort(Direction.ASC, sortKey));
		}else{
			query.with(new Sort(Direction.DESC, sortKey));
		}
		query.addCriteria(Criteria.where("number").regex(".*?" +key+ ".*?")); 
		query.skip(begin);// 从那条记录开始
	    query.limit(limit);// 取多少条记录
		List<CollectionLogs> list= getMongoTemplate().find(query, CollectionLogs.class); 
		return list;
	}
	
	public int findCountLogByNumberSort(String key){
		Query query = new Query();
		query.addCriteria(Criteria.where("number").regex(".*?" +key+ ".*?")); 
	    return (int) mongoTemplate.count(query, CollectionLogs.class); 
	
	}
	
	public void addTest(CollectionLogs s){
		
		getMongoTemplate().insert(s);     
		
	}
	
	public void updateTest(String id){
		
		 getMongoTemplate().updateFirst(new Query(Criteria.where("_id").is(id)), new Update().set("number", "333"), CollectionLogs.class);
		
	}
	
	public void delete(String id){
		
		getMongoTemplate().remove(new Query(Criteria.where("_id").is(id)), CollectionLogs.class);
		
	}
	
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public CollectionLogs findById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id)); 
		return mongoTemplate.findOne(query, CollectionLogs.class);
	} 
	
	
}
