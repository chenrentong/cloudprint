package com.dascom.cloudprint.dao;

import java.util.List;






import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.dascom.cloudprint.entity.auth.CollectionUserOperation;
import com.dascom.cloudprint.entity.auth.CollectionUsers;

public class CollectionUsersDao {
	
	private MongoTemplate mongoTemplate;


	public  List<CollectionUsers> findAllByKey(String key,int begin,int limit) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").regex(".*?" +key+ ".*?")); 
		query.skip(begin);// 从那条记录开始
		query.limit(limit);// 取多少条记录
		List<CollectionUsers> list= getMongoTemplate().find(query, CollectionUsers.class); 
		return list;
	}
	
	public int findAllCountByKey(String key){
		Query query = new Query();
		query.addCriteria(Criteria.where("email").regex(".*?" +key+ ".*?")); 
	    return (int) mongoTemplate.count(query, CollectionUsers.class); 
	
	}
	
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}


	public CollectionUsers findAllByName(String userName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("user_name").is(userName));
		CollectionUsers user= (CollectionUsers) getMongoTemplate().findOne(query, CollectionUsers.class); 	
		return user;
	}

	public CollectionUsers findById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		CollectionUsers user= (CollectionUsers) getMongoTemplate().findOne(query, CollectionUsers.class); 	
		return user;
	}

	public void delete(String id) {
		getMongoTemplate().remove(new Query(Criteria.where("_id").is(id)), CollectionUsers.class);
	}

	public void updateBindingPrint(CollectionUsers user) {
		getMongoTemplate().updateMulti (new Query(Criteria.where("_id").is(user.get_id())), new Update().set("devices", user.getDevices()),CollectionUsers.class);  
	} 
	
	
}
