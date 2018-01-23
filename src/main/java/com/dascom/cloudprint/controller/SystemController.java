package com.dascom.cloudprint.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SystemController {
	
	@RequestMapping("processList")
	public String processList(){

		return "/system/processList";
	}
}
