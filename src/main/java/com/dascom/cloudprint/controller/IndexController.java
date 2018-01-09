package com.dascom.cloudprint.controller;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dascom.cloudprint.service.CollectionUsersOperationService;
import com.dascom.cloudprint.util.Logg;
import com.dascom.cloudprint.util.ValidateCode;

@Controller
public class IndexController {
	@Autowired
	private CollectionUsersOperationService collectionUsersOperationService;
	//跳转到首页
	@RequestMapping("index")
	public String index(HttpServletRequest request ){
		Logg.writeDebugLog("进入首页,index");
		boolean result=collectionUsersOperationService.operationRecord("进入首页！");
		if(!result){
			return "/logException";
		}
		return "/index";
	}
	@RequestMapping("welcome")
	public String welcome(HttpServletRequest request ){
		Logg.writeDebugLog("进入欢迎页,welcome");
		//aaa
		//撒啊啊

		//分
		//oafenzhi
		//主该

		return "/welcome";
	}
	
	@RequestMapping("logException")
	public String logException(HttpServletRequest request ){
		Logg.writeDebugLog("日志记录失败页,logException");
		return "/logException";
	}
	
	@RequestMapping("login")
	public String login(HttpServletRequest request ){
		Logg.writeDebugLog("进入登录页,login");
		return "/login";
	}
	
	@RequestMapping(value="logout")
	 public String logout(HttpServletRequest request){
		Logg.writeDebugLog("进入注销,logout");
		boolean result=collectionUsersOperationService.operationRecord("注销登录！");
		if(!result){
			return "/logException";
		}
	   	Subject subject = SecurityUtils.getSubject();  
	       if (subject != null) { 
	    	   
	           subject.logout();   
	        }
		return "/login";  
	}
	
	@RequestMapping(value="notPermission")
	 public String notPermission(HttpServletRequest request){
		Logg.writeDebugLog("进入权限错误,error");
		boolean result=collectionUsersOperationService.operationRecord("越权操作！");
		if(!result){
			return "/logException";
		}
		return "/notPermission";  
	}	
	
	/**
     * 生成验证码
     * @param request
     * @param response
     * @throws IOException
     * @ValidateCode.generateTextCode(验证码字符类型,验证码长度,需排除的特殊字符)
     * @ValidateCode.generateImageCode(文本验证码,图片宽度,图片高度,干扰线的条数,字符的高低位置是否随机,图片颜色,字体颜色,干扰线颜色)
     */
    @RequestMapping(value = "validateCode")
    public void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Logg.writeDebugLog("进入生成验证码,validateCode");
    	response.setHeader("Cache-Control", "no-cache");
        String verifyCode = ValidateCode.generateTextCode(ValidateCode.TYPE_NUM_LOWER, 4, null);
        request.getSession().setAttribute("validateCode", verifyCode);
		Logg.writeDebugLog("生成验证码:"+verifyCode);
        response.setContentType("image/jpeg");
        BufferedImage bim = ValidateCode.generateImageCode(verifyCode, 90, 30, 5, true, Color.WHITE, Color.BLUE, null);
        ImageIO.write(bim, "JPEG", response.getOutputStream());
    }
}
