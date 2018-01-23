package com.dascom.cloudprint.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;















import com.dascom.cloudprint.entity.auth.CollectionUserOperation;
import com.dascom.cloudprint.entity.device.CollectionIdPool;
import com.dascom.cloudprint.entity.device.CollectionPrinters;
import com.dascom.cloudprint.entity.log.CollectionLogs;

public class CollectionPrintersDao {
	     
	private MongoTemplate mongoTemplate;  
	

	public  List<CollectionPrinters> findAllByAliveAndNumber(boolean alive,String number,int begin,int limit) {
		Query query = new Query();
		query.addCriteria(Criteria.where("number").regex(".*?" +number+ ".*?")); 
		query.addCriteria(Criteria.where("alive").is(alive));
		query.addCriteria(Criteria.where("info").ne(null)) ; 
	    query.skip(begin);// 从那条记录开始
	    query.limit(limit);// 取多少条记录
		List<CollectionPrinters> list= getMongoTemplate().find(query, CollectionPrinters.class); 	
		return list;
	}
	
	public int findCountAllByAliveAndNumber(boolean alive,String number){
		Query query = new Query();
		query.addCriteria(Criteria.where("number").regex(".*?" +number+ ".*?")); 
		query.addCriteria(Criteria.where("alive").is(alive));
		query.addCriteria(Criteria.where("info").ne(null)) ; 

	    return (int) mongoTemplate.count(query, CollectionPrinters.class); 
	}
	
	public int findCountAllByAliveAndNumber(Date startTime, Date endTime,
			String like) {
		Query query = new Query();
		//模糊查询
		if(like!=null){
			query.addCriteria(Criteria.where("number").regex(".*?" +like+ ".*?")); 
		}
		if(startTime!=null&&endTime!=null){
			query.addCriteria(Criteria.where("reg_date").gte(startTime)//大于等于
					.lt(endTime));//小于等于
		}else if(startTime!=null&&endTime==null){
			query.addCriteria(Criteria.where("reg_date").gte(startTime)); 	//大于等于
		}else if(startTime==null&&endTime!=null){
			query.addCriteria(Criteria.where("reg_date").lt(endTime));//小于等于
		}
		 return (int) mongoTemplate.count(query, CollectionPrinters.class); 
	}
	
	public List<CollectionPrinters> findAllByAliveAndNumber(Date startTime,
			Date endTime, String like, int begin, int limit) {
		Query query = new Query();
		if(like!=null){
			query.addCriteria(Criteria.where("number").regex(".*?" +like+ ".*?")); 
		}
		if(startTime!=null&&endTime!=null){
			query.addCriteria(Criteria.where("reg_date").gte(startTime)//大于等于
					.lt(endTime));//小于等于
		}else if(startTime!=null&&endTime==null){
			query.addCriteria(Criteria.where("reg_date").gte(startTime)); 	//大于等于
		}else if(startTime==null&&endTime!=null){
			query.addCriteria(Criteria.where("reg_date").lt(endTime));//小于等于
		}
		
		query.skip(begin);// 从那条记录开始
	    query.limit(limit);// 取多少条记录
		List<CollectionPrinters> list= getMongoTemplate().find(query, CollectionPrinters.class); 	
		return list;
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

	public int findRegisterAllCount() {
		Query query = new Query();	
		query.addCriteria(Criteria.where("info").is(null));  
		return (int) mongoTemplate.count(query, CollectionPrinters.class); 
	}

	public List<CollectionPrinters> findRegisterAllNumber(String sortKey,
			boolean sort, int begin, int limit) {
		Query query = new Query();
		
		if(sort){
			query.with(new Sort(Direction.ASC, sortKey));
		}else{
			query.with(new Sort(Direction.DESC, sortKey));
		}
		query.addCriteria(Criteria.where("info").is(null));  
	    query.skip(begin);// 从那条记录开始
	    query.limit(limit);// 取多少条记录
		List<CollectionPrinters> list= getMongoTemplate().find(query, CollectionPrinters.class); 	
		return list;
	}

	public int findRegisterAllCountByDate(Date ...times) {
		Query query = new Query();
		query.addCriteria(Criteria.where("info").is(null));  
		if(times.length ==4){
			
			query.addCriteria(Criteria.where("reg_date").lte(times[3])); 	//小于等于
		}
		else if(times.length ==3){
			
			query.addCriteria(Criteria.where("reg_date").gte(times[2])); 	//大于等于
		}	
		else {
			query.addCriteria(Criteria.where("reg_date").gte(times[0])	//大于等于
					.lte(times[1])); 	//小于等于
		}

	    return (int) mongoTemplate.count(query, CollectionPrinters.class);	
	}

	public List<CollectionPrinters> findRegisterAllPoolByDate(String sortKey,boolean sort,int begin,int limit,Date ...times) {
		Query query = new Query();
		query.addCriteria(Criteria.where("info").is(null));  
		if(sort){
			query.with(new Sort(Direction.ASC, sortKey));
		}else{
			query.with(new Sort(Direction.DESC, sortKey));
		}

		if(times.length ==4){
			
			query.addCriteria(Criteria.where("reg_date").lte(times[3])); 	//小于等于
		}
		else if(times.length ==3){
			
			query.addCriteria(Criteria.where("reg_date").gte(times[2])); 	//大于等于
		}	
		else {
			query.addCriteria(Criteria.where("reg_date").gte(times[0])	//大于等于
					.lte(times[1])); 	//小于等于
		}

		query.skip(begin);// 从那条记录开始
	    query.limit(limit);// 取多少条记录
		List<CollectionPrinters> list= getMongoTemplate().find(query, CollectionPrinters.class); 
		return list;
	}
	
}
