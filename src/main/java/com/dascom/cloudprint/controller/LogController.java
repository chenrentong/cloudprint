package com.dascom.cloudprint.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dascom.cloudprint.common.CommitConstant;
import com.dascom.cloudprint.common.DataBaseConstant;
import com.dascom.cloudprint.common.EquipmentConstant;
import com.dascom.cloudprint.entity.auth.CollectionUsers;
import com.dascom.cloudprint.entity.device.CollectionIdPool;
import com.dascom.cloudprint.entity.log.CollectionLogs;
import com.dascom.cloudprint.service.CollectionLogsService;
import com.dascom.cloudprint.service.CollectionUsersOperationService;
import com.dascom.cloudprint.util.ErrorBean;
import com.dascom.cloudprint.util.JsonTransform;
import com.dascom.cloudprint.util.Logg;
import com.dascom.cloudprint.util.PageBean;
import com.dascom.cloudprint.util.PageBeanSlidingUtil;

@Controller
public class LogController {
	@Autowired
	private CollectionLogsService collectionLogsService;
	@Autowired
	private CollectionUsersOperationService collectionUsersOperationService;
	
	@RequestMapping("cloudlogListByKey")
	public String cloudlogListByKey(HttpServletRequest request){
		Logg.writeDebugLog("进入日志监控,cloudlogListByKey");
		boolean result=collectionUsersOperationService.operationRecord("进入日志列表！");
		if(!result){
			return "/logException";
		}
		String page =request.getParameter("page");
		String key=request.getParameter("key");
		if(page==null){
			page="1";
		}
		if(key == null){
			key="";
		}
		PageBean<CollectionLogs> pageBean=null;
		try{
			pageBean=collectionLogsService.findCloudprintUserByKey(Integer.parseInt(page), key);
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
		
		return "/log/cloudlogList";
	}
	
	@RequestMapping("cloudlogInfo")
	public String cloudlogInfo(HttpServletRequest request){
		Logg.writeDebugLog("进入日志详细信息,cloudlogInfo");
		boolean result=collectionUsersOperationService.operationRecord("进入日志详情！");
		if(!result){
			return "/logException";
		}
		String id=request.getParameter("id");
		CollectionLogs log=collectionLogsService.findLogById(id);
		request.setAttribute("log", log);
		return "/log/cloudlogInfo";
	}
	
	@ResponseBody
	@RequestMapping(value="cloudlogDel",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String cloudlogDel(HttpServletRequest request,String id){
		Logg.writeDebugLog("进入日志删除,cloudlogDel");
		boolean result=false;
		String[] ids=id.split(",");	//批量删除
		if(ids.length>1){
			boolean userlog=collectionUsersOperationService.operationRecord("批量删除了日志记录！");
			if(!userlog){
				return JsonTransform.loginJsonTransform(CommitConstant.LOGRECORDFAILED,CommitConstant.LOGRECORDFAILEDMSG,null);
			}
		}else{
			boolean userlog=collectionUsersOperationService.operationRecord("删除了日志记录！");
			if(!userlog){
				return JsonTransform.loginJsonTransform(CommitConstant.LOGRECORDFAILED,CommitConstant.LOGRECORDFAILEDMSG,null);
			}
		}
		for(String id1:ids){
			try{
				result=collectionLogsService.delLogById(id1);
				if(!result){	
					CollectionLogs log=new CollectionLogs();
					log.set_id(id1);
					return JsonTransform.loginJsonTransform(CommitConstant.OPERATIONDELETEFAILURE,CommitConstant.OPERATIONDELETEFAILUREMSG,log);
				}
			}catch(Exception e){
				CollectionLogs log=new CollectionLogs();
				log.set_id(id1);
				e.printStackTrace();
				return JsonTransform.loginJsonTransform(CommitConstant.SYSTEMERROR,CommitConstant.SYSTEMERRORMSG,log);
			}
		}
		return JsonTransform.loginJsonTransform(CommitConstant.OPERATIONDELETESUCCESSFUL,CommitConstant.OPERATIONDELETESUCCESSFULMSG,null);
	}
}
