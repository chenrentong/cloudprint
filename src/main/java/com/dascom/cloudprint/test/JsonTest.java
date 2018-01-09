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

public class JsonTest {

	@Test
	public void batchTest(){
		PoolBatchJudgeParmDto pool=new PoolBatchJudgeParmDto();
		List<String> s=new ArrayList<>();
		s.add("111");
		s.add("111");
		s.add("111");
		pool.setNumbers(s);
		pool.setRemark("备注");
		Gson gson = new Gson();  
		String i=gson.toJson(pool);		
		System.out.println(i);
		//List
		//List<PoolBatchJudgeParmDto> pools2= gson.fromJson(i, new TypeToken<List<PoolBatchJudgeParmDto>>(){}.getType());
		PoolBatchJudgeParmDto beanOne = gson.fromJson(i, PoolBatchJudgeParmDto.class); 
		System.out.println(beanOne);
	}	
	
}
