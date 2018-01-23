package com.dascom.cloudprint.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dascom.cloudprint.common.CommitConstant;
import com.dascom.cloudprint.common.DataBaseConstant;
import com.dascom.cloudprint.common.EquipmentConstant;
import com.dascom.cloudprint.entity.auth.CollectionUsers;
import com.dascom.cloudprint.entity.device.CollectionIdPool;
import com.dascom.cloudprint.entity.serverlog.CollectionLog4j2;
import com.dascom.cloudprint.service.CollectionServerLogsService;
import com.dascom.cloudprint.service.CollectionUsersOperationService;
import com.dascom.cloudprint.util.ErrorBean;
import com.dascom.cloudprint.util.JsonTransform;
import com.dascom.cloudprint.util.Logg;
import com.dascom.cloudprint.util.PageBean;
import com.dascom.cloudprint.util.PageBeanSlidingUtil;

@Controller
public class ServerLogController {
	@Autowired
	private CollectionServerLogsService collectionServerLogsService;
	@Autowired
	private CollectionUsersOperationService collectionUsersOperationService;
	/**
	 * 接口与tcp服务日志文件 列表
	 * @param request
	 * @return
	 */
	@RequestMapping("serverlogList")
	public String serverlogList(HttpServletRequest request,String type,String startTime ,String endTime,String level,@RequestParam(value="", defaultValue="1") Integer pageNumber,@RequestParam(value="",defaultValue="20")Integer pageSize){
		Logg.writeDebugLog("进入日志监控,cloudlogListByKey");
		boolean result=collectionUsersOperationService.operationRecord("进入日志列表！");
		if(!result){
			return "/logException";
		}
		if(!("tcp".equals(type)||"".equals(type)||"interface".equals(type))){ //没有传类型过来 .
			return "/log/serverlogList";
		}
		if(!"".equals(level))
			request.setAttribute("level", level);
		if(type!=null&&!"".equals(type)){
			request.setAttribute("type", type);
		}
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟   
		PageBean<CollectionLog4j2> pageBean=null;
		try{
			startTime="".equals(startTime)?null:startTime;
			Date endTime2=null;
			if(endTime!=null&&!"".equals(endTime)){
				 Calendar ca = Calendar.getInstance();
			        ca.setTime(sdf.parse(endTime));
			        ca.add(Calendar.DATE, 1);// num为增加的天数，可以改变的
			        endTime2=ca.getTime();
			}
			pageBean=collectionServerLogsService.findCloudprintUserByKey(type,startTime!=null?sdf.parse(startTime):null,endTime2,level,pageNumber,pageSize);
			request.setAttribute("pageBean", pageBean);
			PageBeanSlidingUtil sliding=new PageBeanSlidingUtil(pageBean.getPage(), pageBean.getTotalPage(), 5);
			request.setAttribute("curPage", String.valueOf(sliding.getBegin()));
			request.setAttribute("totalPage", String.valueOf(sliding.getEnd()));
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
		return "/log/serverlogList";
	}
	
	
	
	 
	
	
	
	
	
	
	
	
	@RequestMapping("serverlogInfo")
	public String serverlogInfo(HttpServletRequest request,String id ,String type){
		Logg.writeDebugLog("进入日志详细信息,cloudlogInfo");
		boolean result=collectionUsersOperationService.operationRecord("进入日志详情！");
		if(!result){
			return "/logException";
		}
		/*String id=request.getParameter("id");*/
		CollectionLog4j2 log=collectionServerLogsService.findLogById(type,id);
		request.setAttribute("log", log);
		return "/log/serverlogInfo";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*@ResponseBody
	@RequestMapping(value="serverlogDel",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String serverlogDel(HttpServletRequest request,String id){
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
	}*/
}
