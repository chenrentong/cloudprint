package com.dascom.cloudprint.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.dascom.cloudprint.util.Logg;

@Controller
@RequestMapping("test")
public class TestController {
	
	@RequestMapping("batchTest")
	public String batchTest(){
		Logg.writeDebugLog("进入批处理测试,batchTest");
		return "/test/batchTest";
		
	}
}
