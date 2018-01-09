package com.dascom.cloudprint.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.dascom.cloudprint.entity.device.CollectionIdPool;
import com.dascom.cloudprint.entity.device.CollectionPrinters;

public class CollectionPrintersPoolDao {
	
	private MongoTemplate mongoTemplate;

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public List<CollectionIdPool> findAllPoolByDate(String sortKey,boolean sort,int begin,int limit,Date ...times){
		Query query = new Query();
		if(sort){
			query.with(new Sort(Direction.ASC, sortKey));
		}else{
			query.with(new Sort(Direction.DESC, sortKey));
		}

		if(times.length ==4){
			
			query.addCriteria(Criteria.where("upload_date").lte(times[3])); 	//小于等于
		}
		else if(times.length ==3){
			
			query.addCriteria(Criteria.where("upload_date").gte(times[2])); 	//大于等于
		}	
		else {
			query.addCriteria(Criteria.where("upload_date").gte(times[0])	//大于等于
					.lte(times[1])); 	//小于等于
		}

		query.skip(begin);// 从那条记录开始
	    query.limit(limit);// 取多少条记录
		List<CollectionIdPool> list= getMongoTemplate().find(query, CollectionIdPool.class); 
		return list;	
	}
	
	public int findCountAllByNumber(String number){
		Query query = new Query();
		query.addCriteria(Criteria.where("number").is(number)); 
	    return (int) mongoTemplate.count(query, CollectionIdPool.class); 
	}

	public void insert(CollectionIdPool pool) {
		
		getMongoTemplate().insert(pool);     
	}

	public void insertAll(List<CollectionIdPool> pool){
		
		getMongoTemplate().insertAll(pool);
	}
	
	public void delete(String id) {
		
		getMongoTemplate().remove(new Query(Criteria.where("_id").is(id)), CollectionIdPool.class);
	}

	public int findAllCountByDate(Date ...times) {
		Query query = new Query();
		if(times.length ==4){
			
			query.addCriteria(Criteria.where("upload_date").lte(times[3])); 	//小于等于
		}
		else if(times.length ==3){
			
			query.addCriteria(Criteria.where("upload_date").gte(times[2])); 	//大于等于
		}	
		else {
			query.addCriteria(Criteria.where("upload_date").gte(times[0])	//大于等于
					.lte(times[1])); 	//小于等于
		}

	    return (int) mongoTemplate.count(query, CollectionIdPool.class);	
	}
	
	public int findAllCount() {
		Query query = new Query();
		return (int) mongoTemplate.count(query, CollectionIdPool.class);	
	}
	
	public List<CollectionIdPool> findAllPool(String sortKey,boolean sort,int begin,int limit){
		Query query = new Query();
		if(sort){
			query.with(new Sort(Direction.ASC, sortKey));
		}else{
			query.with(new Sort(Direction.DESC, sortKey));
		}
		query.skip(begin);// 从那条记录开始
	    query.limit(limit);// 取多少条记录
		List<CollectionIdPool> list= getMongoTemplate().find(query, CollectionIdPool.class); 
		return list;	
	}
}
