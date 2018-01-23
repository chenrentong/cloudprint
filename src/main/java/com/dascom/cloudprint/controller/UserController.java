package com.dascom.cloudprint.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dascom.cloudprint.common.CommitConstant;
import com.dascom.cloudprint.common.DataBaseConstant;
import com.dascom.cloudprint.common.EquipmentConstant;
import com.dascom.cloudprint.common.LoginConstant;
import com.dascom.cloudprint.entity.auth.CollectionUserOperation;
import com.dascom.cloudprint.entity.auth.CollectionUsers;
import com.dascom.cloudprint.entity.log.CollectionLogs;
import com.dascom.cloudprint.service.CollectionUsersOperationService;
import com.dascom.cloudprint.service.CollectionUsersService;
import com.dascom.cloudprint.util.ErrorBean;
import com.dascom.cloudprint.util.JsonTransform;
import com.dascom.cloudprint.util.Logg;
import com.dascom.cloudprint.util.PageBean;
import com.dascom.cloudprint.util.PageBeanSlidingUtil;

@Controller
public class UserController {
	@Autowired
	private CollectionUsersService collectionUsersService;
	@Autowired
	private CollectionUsersOperationService collectionUsersOperationService;
	
	@RequestMapping("cloudprintUserListByKey")
	public String cloudprintUserListByKey(HttpServletRequest request){
		Logg.writeDebugLog("进入云用户监控,cloudprintUserListByKey");
		boolean result=collectionUsersOperationService.operationRecord("进入用户列表！");
		if(!result){
			return "/logException";
		}
		String page=request.getParameter("page");
		String key=request.getParameter("key");
		if(page ==null){
			page="1";
		}
		if(key==null){
			key="";
		}
		try{
			PageBean<CollectionUsers> pageBean=collectionUsersService.findCloudprintUserByKey(Integer.parseInt(page), key);
			request.setAttribute("pageBean", pageBean);
			PageBeanSlidingUtil sliding=new PageBeanSlidingUtil(pageBean.getPage(), pageBean.getTotalPage(), 5);
			request.setAttribute("curPage", String.valueOf(sliding.getBegin()));
			request.setAttribute("totalPage", String.valueOf(sliding.getEnd()));
			request.setAttribute("key", key);
		}catch(ConversionException e){
			//转换异常
			Logg.writeWarnLog("字段转换异常");
			//设备警报的一种方式；或者自己抛出异常（推荐第二种）
			/*request.setAttribute("errorCode", DataBaseConstant.DATABASE_ERROR_CODE_2003);*/
			ErrorBean errorBean=new ErrorBean();
			errorBean.setCode(DataBaseConstant.DATABASE_ERROR_CODE_2003);
			errorBean.setMessage(DataBaseConstant.DATABASE_ERROR_MESSAGE_DATABASEFIELDERROR);
			request.getSession().setAttribute("errorBean", errorBean);
			e.printStackTrace();
		}catch(DataAccessResourceFailureException e){
			//无法连接数据库
			Logg.writeWarnLog("数据库连接超时");
			ErrorBean errorBean=new ErrorBean();
			errorBean.setCode(DataBaseConstant.DATABASE_ERROR_CODE_2002);
			errorBean.setMessage(DataBaseConstant.DATABASE_ERROR_MESSAGE_DATABASEOUTTIME);
			request.getSession().setAttribute("errorBean", errorBean);
			e.printStackTrace();
		}catch(org.springframework.data.mongodb.UncategorizedMongoDbException e){
			Logg.writeWarnLog("数据库授权出现异常");
			ErrorBean errorBean=new ErrorBean();
			errorBean.setCode("2007");
			errorBean.setMessage("数据库授权出现异常");
			request.getSession().setAttribute("errorBean", errorBean);
			e.printStackTrace();
		}catch(DataAccessException e){
			//运行时出现异常
			Logg.writeWarnLog("数据库运行时出现异常");
			ErrorBean errorBean=new ErrorBean();
			errorBean.setCode(DataBaseConstant.DATABASE_ERROR_CODE_2005);
			errorBean.setMessage(DataBaseConstant.DATABASE_ERROR_MESSAGE_DATABASERUNERROR);
			request.getSession().setAttribute("errorBean", errorBean);
			e.printStackTrace();
		}catch(Exception e){
			//出现了其他异常
			Logg.writeWarnLog("出现了其他异常");
			ErrorBean errorBean=new ErrorBean();
			errorBean.setCode(EquipmentConstant.EQUIPMENT_ERROR_CODE_3002);
			errorBean.setMessage(EquipmentConstant.EQUIPMENT_ERROR_MESSAGE_OTHERERROR);
			request.getSession().setAttribute("errorBean", errorBean);
			e.printStackTrace();
		}
		
		return "/user/clouduserList";
	}
	
	@ResponseBody
	@RequestMapping(value="cloudprintUserDel",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String cloudprintUserDel(HttpServletRequest request,String id){
		Logg.writeDebugLog("进入用户删除,cloudprintUserDel");
		boolean result=false;
		String[] ids=id.split(",");	//批量删除
		if(ids.length>1){
			boolean userlog=collectionUsersOperationService.operationRecord("批量删除了用户记录！");
			if(!userlog){
				return JsonTransform.loginJsonTransform(CommitConstant.LOGRECORDFAILED,CommitConstant.LOGRECORDFAILEDMSG,null);
			}
		}else{
			boolean userlog=collectionUsersOperationService.operationRecord("删除了用户记录！");
			if(!userlog){
				return JsonTransform.loginJsonTransform(CommitConstant.LOGRECORDFAILED,CommitConstant.LOGRECORDFAILEDMSG,null);
			}
		}
		for(String id1:ids){
			try{
				result=collectionUsersService.delUserById(id1);
				if(!result){				
					return JsonTransform.loginJsonTransform(CommitConstant.OPERATIONDELETEFAILURE,CommitConstant.OPERATIONDELETEFAILUREMSG,null);
				}
			}catch(Exception e){			
				e.printStackTrace();
				return JsonTransform.loginJsonTransform(CommitConstant.SYSTEMERROR,CommitConstant.SYSTEMERRORMSG,null);
			}
		}
			
		return JsonTransform.loginJsonTransform(CommitConstant.OPERATIONDELETESUCCESSFUL,CommitConstant.OPERATIONDELETESUCCESSFULMSG,null);
	}
	
	@RequestMapping("clouduserOperation")
	public String clouduserOperation(HttpServletRequest request){
		Logg.writeDebugLog("进入云用户操作监控,clouduserOperation");
		boolean result=collectionUsersOperationService.operationRecord("进入用户操作列表！");
		if(!result){
			return "/logException";
		}
		String page=request.getParameter("page");
		String key=request.getParameter("key");
		if(page ==null){
			page="1";
		}
		if(key==null){
			key="";
		}
		try{
			PageBean<CollectionUserOperation> pageBean= collectionUsersOperationService.findUserOperationByKey(Integer.parseInt(page), key);
			request.setAttribute("pageBean", pageBean);
			PageBeanSlidingUtil sliding=new PageBeanSlidingUtil(pageBean.getPage(), pageBean.getTotalPage(), 5);
			request.setAttribute("curPage", String.valueOf(sliding.getBegin()));
			request.setAttribute("totalPage", String.valueOf(sliding.getEnd()));
			request.setAttribute("key", key);
		}catch(ConversionException e){
			//转换异常
			Logg.writeWarnLog("字段转换异常");
			//设备警报的一种方式；或者自己抛出异常（推荐第二种）
			/*request.setAttribute("errorCode", DataBaseConstant.DATABASE_ERROR_CODE_2003);*/
			ErrorBean errorBean=new ErrorBean();
			errorBean.setCode(DataBaseConstant.DATABASE_ERROR_CODE_2003);
			errorBean.setMessage(DataBaseConstant.DATABASE_ERROR_MESSAGE_DATABASEFIELDERROR);
			request.getSession().setAttribute("errorBean", errorBean);
			e.printStackTrace();
		}catch(DataAccessResourceFailureException e){
			//无法连接数据库
			Logg.writeWarnLog("数据库连接超时");
			ErrorBean errorBean=new ErrorBean();
			errorBean.setCode(DataBaseConstant.DATABASE_ERROR_CODE_2002);
			errorBean.setMessage(DataBaseConstant.DATABASE_ERROR_MESSAGE_DATABASEOUTTIME);
			request.getSession().setAttribute("errorBean", errorBean);
			e.printStackTrace();
		}catch(org.springframework.data.mongodb.UncategorizedMongoDbException e){
			Logg.writeWarnLog("数据库授权出现异常");
			ErrorBean errorBean=new ErrorBean();
			errorBean.setCode("2007");
			errorBean.setMessage("数据库授权出现异常");
			request.getSession().setAttribute("errorBean", errorBean);
			e.printStackTrace();
		}catch(DataAccessException e){
			//运行时出现异常
			Logg.writeWarnLog("数据库运行时出现异常");
			ErrorBean errorBean=new ErrorBean();
			errorBean.setCode(DataBaseConstant.DATABASE_ERROR_CODE_2005);
			errorBean.setMessage(DataBaseConstant.DATABASE_ERROR_MESSAGE_DATABASERUNERROR);
			request.getSession().setAttribute("errorBean", errorBean);
			e.printStackTrace();
		}catch(Exception e){
			//出现了其他异常
			Logg.writeWarnLog("出现了其他异常");
			ErrorBean errorBean=new ErrorBean();
			errorBean.setCode(EquipmentConstant.EQUIPMENT_ERROR_CODE_3002);
			errorBean.setMessage(EquipmentConstant.EQUIPMENT_ERROR_MESSAGE_OTHERERROR);
			request.getSession().setAttribute("errorBean", errorBean);
			e.printStackTrace();
		}
		
		return "/user/clouduserOperation";
	}
	
	@ResponseBody
	@RequestMapping(value="clouduserOperationDel",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String clouduserOperationDel(HttpServletRequest request,String id){
		Logg.writeDebugLog("进入用户操作删除,clouduserOperationDel");
		boolean result=false;
		String[] ids=id.split(",");	//批量删除
		if(ids.length>1){
			boolean userlog=collectionUsersOperationService.operationRecord("批量删除了用户操作记录！");
			if(!userlog){
				return JsonTransform.loginJsonTransform(CommitConstant.LOGRECORDFAILED,CommitConstant.LOGRECORDFAILEDMSG,null);
			}
		}else{
			boolean userlog=collectionUsersOperationService.operationRecord("删除了用户操作记录！");
			if(!userlog){
				return JsonTransform.loginJsonTransform(CommitConstant.LOGRECORDFAILED,CommitConstant.LOGRECORDFAILEDMSG,null);
			}
		}	
		for(String id1:ids){
			try{	
				result=collectionUsersOperationService.deluserOperationById(id1);
				if(!result){				
					return JsonTransform.loginJsonTransform(CommitConstant.OPERATIONDELETEFAILURE,CommitConstant.OPERATIONDELETEFAILUREMSG,null);
				}
			}catch(Exception e){			
				e.printStackTrace();
				return JsonTransform.loginJsonTransform(CommitConstant.SYSTEMERROR,CommitConstant.SYSTEMERRORMSG,null);
			}
		}
			
		return JsonTransform.loginJsonTransform(CommitConstant.OPERATIONDELETESUCCESSFUL,CommitConstant.OPERATIONDELETESUCCESSFULMSG,null);
	}
	
    @ResponseBody
  	@RequestMapping(value="loginSubmit" ,method=RequestMethod.POST,produces="application/json;charset=utf-8")
  	public String loginSubmit(HttpServletRequest request, String username,String password){
    	Logg.writeDebugLog("云用户登录,loginSubmit");
  		//保存用户名
  		Subject sub =SecurityUtils.getSubject(); 
  		Session session =sub.getSession();
  		//系统的验证码
        String code = (String) session.getAttribute("validateCode");
        //用户输入的验证码
        String submitCode = WebUtils.getCleanParam(request, "validateCode");
       // System.out.println("散射值:"+DigestUtils.md5Hex(password));
        if (StringUtils.isEmpty(submitCode)  || !StringUtils.equals(code,submitCode.toLowerCase())) {
            
        	return JsonTransform.loginJsonTransform( LoginConstant.LOGIN_ERROR_CODE_100000, LoginConstant.LOGIN_ERROR_MESSAGE_VALIDATECODE, null);
        
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            // 想要得到 SecurityUtils.getSubject() 的对象．．访问地址必须跟shiro的拦截地址内．不然后会报空指针 
            // 用户输入的账号和密码,,存到UsernamePasswordToken对象中..然后由shiro内部认证对比,
            // 认证执行者交由ShiroDbRealm中doGetAuthenticationInfo处理
            // 当以上认证成功后会向下执行,认证失败会抛出异常
            sub.login(token);
            //从subject中获取保存在安全管理员中的user对象
            CollectionUsers user = (CollectionUsers) sub.getPrincipal();
            //将用户保存在session中,因为自定义拦截器的原因(如果没有自定义拦截器,将用户存放在session域中的这个步骤可以省略)
            boolean result=collectionUsersOperationService.operationRecord("用户登录！");
    		if(!result){
    			return "/logException";
    		}
            request.getSession().setAttribute("user", user);
            
            return JsonTransform.loginJsonTransform( LoginConstant.LOGIN_ERROR_CODE_1000, LoginConstant.LOGIN_SUCCEED_MESSAGE_VALIDATECODE, null);
       
        } catch (LockedAccountException lae) {
        	
            token.clear();
            return JsonTransform.loginJsonTransform( LoginConstant.LOGIN_ERROR_CODE_100002, LoginConstant.LOGIN_ERROR_MESSAGE_SYSTEMERROR, null);
       
        } catch (ExcessiveAttemptsException e) {	
        	
            token.clear();
            return JsonTransform.loginJsonTransform( LoginConstant.LOGIN_ERROR_CODE_100003, "账号：" + username + LoginConstant.LOGIN_ERROR_MESSAGE_MAXERROR, null);
       
        }catch (IncorrectCredentialsException e) {	
        	
            token.clear();
            return JsonTransform.loginJsonTransform( LoginConstant.LOGIN_ERROR_CODE_100001, LoginConstant.LOGIN_ERROR_MESSAGE_USERERROR, null);
        
        }catch(UnknownAccountException e){
        	
        	 token.clear();
             return JsonTransform.loginJsonTransform( LoginConstant.LOGIN_ERROR_CODE_100005, LoginConstant.LOGIN_ERROR_MESSAGE_USERERUNKOWN, null);
       
        }catch (Exception e) {	
        	
            token.clear();
            e.printStackTrace();
            return JsonTransform.loginJsonTransform( LoginConstant.LOGIN_ERROR_CODE, LoginConstant.LOGIN_ERROR_MESSAGE, null);
       
        }
  	}
    
    @RequestMapping("clouduserInfo")
	public String clouduserInfo(HttpServletRequest request){
    	Logg.writeDebugLog("进入用户详细信息,clouduserInfo");
    	boolean result=collectionUsersOperationService.operationRecord("进入用户详情！");
		if(!result){
			return "/logException";
		}
    	String id=request.getParameter("id");
    	CollectionUsers user=collectionUsersService.findUserById(id);
    	request.setAttribute("user", user);
		return "/user/clouduserInfo";
    	
    }
    
    @RequestMapping("clouduserEdit")
	public String clouduserEdit(HttpServletRequest request){
    	Logg.writeDebugLog("进入用户编辑界面,clouduserInfo");
    	boolean result=collectionUsersOperationService.operationRecord("进入用户编辑！");
		if(!result){
			return "/logException";
		}
    	String id=request.getParameter("id");
    	CollectionUsers user=collectionUsersService.findUserById(id);
    	request.setAttribute("user", user);
		return "/user/clouduserEdit";
    	
    }
}
