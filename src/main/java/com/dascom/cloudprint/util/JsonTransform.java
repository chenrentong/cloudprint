package com.dascom.cloudprint.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.dascom.cloudprint.dto.PoolBatchJudgeParmDto;

public class JsonTransform {
	/**
	 * 帮助转换json数据的方法
	 * @param code
	 * @param msg
	 * @param date
	 * @return
	 */
	public static String loginJsonTransform(String code, String msg ,Object date){
		Map<String, Object> map=new HashMap<String, Object>();
		//用户状态码 
		map.put("code", code);
		//登录消息
		map.put("msg", msg);
		//返回的数据.
		map.put("date", date);
		//转换成json数据
		JSONObject json=new JSONObject(map);
		return  json.toString();
	}
	
	
}
