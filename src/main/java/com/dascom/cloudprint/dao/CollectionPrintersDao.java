package com.dascom.cloudprint.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;








import com.dascom.cloudprint.entity.auth.CollectionUserOperation;
import com.dascom.cloudprint.entity.device.CollectionPrinters;
import com.dascom.cloudprint.entity.log.CollectionLogs;

public class CollectionPrintersDao {
	     
	private MongoTemplate mongoTemplate;  
	

	public  List<CollectionPrinters> findAllByAliveAndNumber(boolean alive,String number,int begin,int limit) {
		Query query = new Query();
		query.addCriteria(Criteria.where("number").regex(".*?" +number+ ".*?")); 
		query.addCriteria(Criteria.where("alive").is(alive));
		
	    query.skip(begin);// 从那条记录开始
	    query.limit(limit);// 取多少条记录
		List<CollectionPrinters> list= getMongoTemplate().find(query, CollectionPrinters.class); 	
		return list;
	}
	
	public int findCountAllByAliveAndNumber(boolean alive,String number){
		Query query = new Query();
		query.addCriteria(Criteria.where("number").regex(".*?" +number+ ".*?")); 
		query.addCriteria(Criteria.where("alive").is(alive));
	    return (int) mongoTemplate.count(query, CollectionPrinters.class); 
	}
	
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public CollectionPrinters findById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		return mongoTemplate.findOne(query, CollectionPrinters.class);
	}  
	
	public int findCountAllByNumber(String number){
		Query query = new Query();
		query.addCriteria(Criteria.where("number").is(number)); 
	    return (int) mongoTemplate.count(query, CollectionPrinters.class); 
	}

	public void delete(String id) {
		
		getMongoTemplate().remove(new Query(Criteria.where("_id").is(id)), CollectionPrinters.class);
	}

	public void insert(CollectionPrinters printer) {
		
		getMongoTemplate().insert(printer);     
	}
}
