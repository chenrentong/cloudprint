package com.dascom.cloudprint.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.dascom.cloudprint.entity.serverlog.CollectionLog4j2;

public class CollectionTcpLogsDao {
	
	private MongoTemplate mongoTemplate;
	
	public  List<CollectionLog4j2> findAllLogByNumberSort(Date startTime,Date endTime,String level,int begin,int limit) {
		Query query = new Query();
		/*if(sort){
			query.with(new Sort(Direction.ASC, sortKey));
		}else{
			query.with(new Sort(Direction.DESC, sortKey));
		}*/
		/*query.addCriteria(Criteria.where("number").regex(".*?" +key+ ".*?")); */
		if(level!=null||"".equals(level))
			query.addCriteria(Criteria.where("level").is(level));
		
		if(startTime!=null&&endTime!=null){
			query.addCriteria(Criteria.where("date").gte(startTime)//大于等于
					.lt(endTime));//小于等于
		}else if(startTime!=null&&endTime==null){
			query.addCriteria(Criteria.where("date").gte(startTime)); 	//大于等于
		}else if(startTime==null&&endTime!=null){
			query.addCriteria(Criteria.where("date").lt(endTime));//小于等于
		}
		query.skip(begin);// 从那条记录开始
	    query.limit(limit);// 取多少条记录
	    query.with(new Sort(Direction.DESC, "date"));
		List<CollectionLog4j2> list= getMongoTemplate().find(query, CollectionLog4j2.class); 
		return list;
	}
	
	public int findCountLogByNumberSort(Date startTime, Date endTime, String level){
		Query query = new Query();
		if(startTime!=null&&endTime!=null){
			query.addCriteria(Criteria.where("date").gte(startTime)//大于等于
					.lt(endTime));//小于等于
		}else if(startTime!=null&&endTime==null){
			query.addCriteria(Criteria.where("date").gte(startTime)); 	//大于等于
		}else if(startTime==null&&endTime!=null){
			query.addCriteria(Criteria.where("date").lt(endTime));//小于等于
		}
		if(level!=null||"".equals(level))
			query.addCriteria(Criteria.where("level").is(level));
	    return (int) mongoTemplate.count(query, CollectionLog4j2.class); 
	
	}
	
	public void addTest(CollectionLog4j2 s){
		
		getMongoTemplate().insert(s);     
		
	}
	
	public void updateTest(String id){
		
		 getMongoTemplate().updateFirst(new Query(Criteria.where("_id").is(id)), new Update().set("number", "333"), CollectionLog4j2.class);
		
	}
	
	public void delete(String id){
		
		getMongoTemplate().remove(new Query(Criteria.where("_id").is(id)), CollectionLog4j2.class);
		
	}
	
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public CollectionLog4j2 findById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id)); 
		return mongoTemplate.findOne(query, CollectionLog4j2.class);
	} 
	
	
}
