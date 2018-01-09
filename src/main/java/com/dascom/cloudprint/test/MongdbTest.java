package com.dascom.cloudprint.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.dascom.cloudprint.dto.PoolBatchJudgeParmDto;
import com.dascom.cloudprint.util.JsonTransform;
import com.dascom.cloudprint.util.Logg;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class MongdbTest {

	@Test
	 public void testMongodb()  
    {  
      try{     
            // 连接到 mongodb 服务  
             Mongo mongo = new Mongo("192.168.11.211", 27017);    
            //根据mongodb数据库的名称获取mongodb对象 ,  
             DB db = mongo.getDB( "db_cloud_device" );  
            // DB db = mongo.getDB( "db_auth" );  
             Set<String> collectionNames = db.getCollectionNames();   
             System.out.println(collectionNames.size());
               // 打印出test中的集合    
              for (String name : collectionNames) {    
                    System.out.println("collectionName==="+name);    
              }    
               
          }catch(Exception e){  
             e.printStackTrace();  
          }  

    }
	
}
