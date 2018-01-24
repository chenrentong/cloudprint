package com.dascom.cloudprint.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
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
import com.dascom.cloudprint.entity.device.CollectionPrinters;
import com.dascom.cloudprint.service.CollectionPrintersPoolService;
import com.dascom.cloudprint.service.CollectionPrintersService;
import com.dascom.cloudprint.service.CollectionUsersOperationService;
import com.dascom.cloudprint.util.ErrorBean;
import com.dascom.cloudprint.util.JsonTransform;
import com.dascom.cloudprint.util.Logg;
import com.dascom.cloudprint.util.PageBean;
import com.dascom.cloudprint.util.PageBeanSlidingUtil;
import com.dascom.cloudprint.util.PrintersNumber;
import com.dascom.cloudprint.util.PrintersNumberException;

@Controller
public class PrinterController {
	@Autowired
	private CollectionPrintersService collectionPrintersService;
	@Autowired
	private CollectionPrintersPoolService collectionPrintersPoolService;
	@Autowired
	private CollectionUsersOperationService collectionUsersOperationService;

	
	@RequestMapping("cloudprintListByKeyAndLine")
	public String cloudprintListByOnline(HttpServletRequest request ){
		Logg.writeDebugLog("进入云设备监控,cloudprintListByKeyAndLine");
		boolean result=collectionUsersOperationService.operationRecord("进入设备列表！");
		if(!result){
			return "/logException";
		}
		String key=request.getParameter("key");
		String page=request.getParameter("page");
		String line=request.getParameter("line");
		if(page ==null){
			page="1";
		}
		if(key ==null){
			key="";
		}
		if(line == null){
			line="on";
		}
		PageBean<CollectionPrinters> pageBean=null;
		try{
			if(line.equals("on")){
				
				pageBean=collectionPrintersService.findPrinterBylineAndKey(Integer.parseInt(page), true, key);
			
			}else if(line.equals("out")){
				
				pageBean=collectionPrintersService.findPrinterBylineAndKey(Integer.parseInt(page), false, key);
				
			}
			//监控每个设备的状态
			request.setAttribute("errorCode", EquipmentConstant.EQUIPMENT_ERROR_CODE_3000);
			request.setAttribute("pageBean", pageBean);
			PageBeanSlidingUtil sliding=new PageBeanSlidingUtil(pageBean.getPage(), pageBean.getTotalPage(), 5);
			request.setAttribute("curPage", String.valueOf(sliding.getBegin()));
			request.setAttribute("totalPage", String.valueOf(sliding.getEnd()));
			request.setAttribute("key", key);
			request.setAttribute("line", line);
		
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
		
		return "/device/cloudprintList";
	}
	
	@RequestMapping("cloudprintPool")
	public String cloudprintPool(HttpServletRequest request ){
		Logg.writeDebugLog("进入设备编号监控,cloudprintPool");
		boolean result=collectionUsersOperationService.operationRecord("进入设备编号列表！");
		if(!result){
			return "/logException";
		}
		String timeMin=request.getParameter("timeMin");
		String timeMax=request.getParameter("timeMax");
		String page=request.getParameter("page");
		if(page ==null){
			page="1";
		}
		if(timeMin ==null || timeMax==null){
			timeMin="";
			timeMax="";
		}
		try{
			PageBean<CollectionIdPool> pageBean=collectionPrintersPoolService.findPrinterPoolByKey(Integer.parseInt(page), timeMin,timeMax);
			request.setAttribute("pageBean", pageBean);
			PageBeanSlidingUtil sliding=new PageBeanSlidingUtil(pageBean.getPage(), pageBean.getTotalPage(), 5);
			request.setAttribute("curPage", String.valueOf(sliding.getBegin()));
			request.setAttribute("totalPage", String.valueOf(sliding.getEnd()));
			request.setAttribute("timeMin", timeMin);
			request.setAttribute("timeMax", timeMax);
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
		return "/device/cloudprintPool";
	}
	
	@RequestMapping(value="deviceError")
	 public String deviceError(HttpServletRequest request){
		Logg.writeDebugLog("进入错误页面,deviceError");
		boolean result=collectionUsersOperationService.operationRecord("监控系统报警！");
		if(!result){
			return "/logException";
		}
		return "/device/deviceError";  
	}
	
	@RequestMapping(value="errorService")
	 public String errorServer(HttpServletRequest request){
		Logg.writeDebugLog("进入服务器关闭错误,errorService");
		boolean result=collectionUsersOperationService.operationRecord("遭遇服务器重启！");
		if(!result){
			return "/logException";
		}
		return "/device/errorServer";  
	}
	
	@RequestMapping(value="cloudprintInfo")
	 public String cloudprintInfo(HttpServletRequest request){
		Logg.writeDebugLog("进入打印机详细信息,cloudprintInfo");
		boolean result=collectionUsersOperationService.operationRecord("设备详情！");
		if(!result){
			return "/logException";
		}
		String id=request.getParameter("id");
		CollectionPrinters printer=collectionPrintersService.findPrintersById(id);
		request.setAttribute("printer", printer);
		return "/device/cloudprintInfo";  
	}
	
	@RequestMapping(value="cloudprintEdit")
	 public String cloudprintEdit(HttpServletRequest request){
		Logg.writeDebugLog("进入打印机修改,cloudprintEdit");
		boolean result=collectionUsersOperationService.operationRecord("进入设备修改页面！");
		if(!result){
			return "/logException";
		}
		String id=request.getParameter("id");
		CollectionPrinters printer=collectionPrintersService.findPrintersById(id);
		request.setAttribute("printer", printer);
		return "/device/cloudprintEdit";  
	}
	
	@ResponseBody
	@RequestMapping(value="cloudprintPoolDel",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	 public String cloudprintPoolDel(HttpServletRequest request,String id){
		Logg.writeDebugLog("进入设备编号删除,cloudprintPoolDel");
		boolean result=false;
		String[] ids=id.split(",");	//批量删除
		if(ids.length>1){
			boolean userlog=collectionUsersOperationService.operationRecord("批量删除了设备编号记录！");	
			if(!userlog){
				return JsonTransform.loginJsonTransform(CommitConstant.LOGRECORDFAILED,CommitConstant.LOGRECORDFAILEDMSG,null);
			}
		}else{
			boolean userlog=collectionUsersOperationService.operationRecord("删除了设备编号记录！");
			if(!userlog){
				return JsonTransform.loginJsonTransform(CommitConstant.LOGRECORDFAILED,CommitConstant.LOGRECORDFAILEDMSG,null);
			}
		}
		for(String id1:ids){
			try{
				result=collectionPrintersPoolService.deletePrintersPool(id1);
				if(!result){
					CollectionIdPool pool=new CollectionIdPool();
					pool.set_id(id1);
					return JsonTransform.loginJsonTransform(CommitConstant.OPERATIONDELETEFAILURE,CommitConstant.OPERATIONDELETEFAILUREMSG,pool);
				}
			}catch(Exception e){
				CollectionIdPool pool=new CollectionIdPool();
				pool.set_id(id1);
				e.printStackTrace();
				return JsonTransform.loginJsonTransform(CommitConstant.SYSTEMERROR,CommitConstant.SYSTEMERRORMSG,pool);
			}
		}
			
		return JsonTransform.loginJsonTransform(CommitConstant.OPERATIONDELETESUCCESSFUL,CommitConstant.OPERATIONDELETESUCCESSFULMSG,null);
			
	}
	
	@ResponseBody
	@RequestMapping(value="cloudprintDel",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	 public String cloudprintDel(HttpServletRequest request,String id){
		Logg.writeDebugLog("进入设备删除,cloudprintDel");
		boolean result=false;
		String[] ids=id.split(",");	//批量删除
		if(ids.length>1){
			boolean userlog=collectionUsersOperationService.operationRecord("批量删除了设备记录！");	
			if(!userlog){
				return JsonTransform.loginJsonTransform(CommitConstant.LOGRECORDFAILED,CommitConstant.LOGRECORDFAILEDMSG,null);
			}
		}else{
			boolean userlog=collectionUsersOperationService.operationRecord("删除了设备记录！");
			if(!userlog){
				return JsonTransform.loginJsonTransform(CommitConstant.LOGRECORDFAILED,CommitConstant.LOGRECORDFAILEDMSG,null);
			}
		}
		
		for(String id1:ids){
			try{
				result=collectionPrintersService.deletePrinters(id1);
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
	
	@RequestMapping(value="machinePrint")
	 public String machinePrint(HttpServletRequest request){
		Logg.writeDebugLog("进入设备打印,machinePrint");
		boolean result=collectionUsersOperationService.operationRecord("进入设备打印！");
		if(!result){
			return "/logException";
		}
		return "/device/machinePrint";  
	}
	
	
	/**
	 * 查询全部打印机设备  
	 * @param request
	 * @param startTime
	 * @param endTime
	 * @param like
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="printList")
	public String  printList(HttpServletRequest request,String startTime,String endTime,String like, @RequestParam(value="", defaultValue="1") Integer pageNumber,@RequestParam(value="",defaultValue="8")Integer pageSize){
		Logg.writeDebugLog("进入云设备监控,cloudprintListByKeyAndLine");
		boolean result=collectionUsersOperationService.operationRecord("进入设备列表！");
		if(!result){
			return "/logException";
		}
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("like", like);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟   
		PageBean<CollectionPrinters> pageBean=null;
		try{
			Date startTime2 =startTime!=null&&!"".equals(startTime)?sdf.parse(startTime):null;
			Date endTime2 =endTime!=null&&!"".equals(endTime)?sdf.parse(endTime):null;
			
			pageBean=collectionPrintersService.findPrinterBylineAndKey(startTime2, endTime2, like,pageNumber,pageSize);
			//监控每个设备的状态
			request.setAttribute("errorCode", EquipmentConstant.EQUIPMENT_ERROR_CODE_3000);
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
		
		return "/device/bindingPrintList";
	}
	
	
	
	@RequestMapping(value="cloudprintNumber")
	 public String cloudprintNumber(HttpServletRequest request){
		Logg.writeDebugLog("进入注册设备编号,cloudprintNumber");
		boolean result=collectionUsersOperationService.operationRecord("进入设备注册编号！");
		if(!result){
			return "/logException";
		}
		String timeMin=request.getParameter("timeMin");
		String timeMax=request.getParameter("timeMax");
		String page=request.getParameter("page");
		if(page ==null){
			page="1";
		}
		if(timeMin ==null || timeMax==null){
			timeMin="";
			timeMax="";
		}
		try{
			PageBean<CollectionPrinters> pageBean=collectionPrintersService.findRegisterNumberByKey(Integer.parseInt(page), timeMin,timeMax);
			request.setAttribute("pageBean", pageBean);
			PageBeanSlidingUtil sliding=new PageBeanSlidingUtil(pageBean.getPage(), pageBean.getTotalPage(), 5);
			request.setAttribute("curPage", String.valueOf(sliding.getBegin()));
			request.setAttribute("totalPage", String.valueOf(sliding.getEnd()));
			request.setAttribute("timeMin", timeMin);
			request.setAttribute("timeMax", timeMax);
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
		return "/device/cloudprintNumber";  
	}
	
	@ResponseBody
	@RequestMapping(value="cloudprintNumberDel",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	 public String cloudprintNumberDel(HttpServletRequest request,String id){
		Logg.writeDebugLog("进入注册编号删除,cloudprintDel");
		boolean result=false;
		String[] ids=id.split(",");	//批量删除
		if(ids.length>1){
			boolean userlog=collectionUsersOperationService.operationRecord("批量删除了注册编号！");	
			if(!userlog){
				return JsonTransform.loginJsonTransform(CommitConstant.LOGRECORDFAILED,CommitConstant.LOGRECORDFAILEDMSG,null);
			}
		}else{
			boolean userlog=collectionUsersOperationService.operationRecord("删除了注册编号！");
			if(!userlog){
				return JsonTransform.loginJsonTransform(CommitConstant.LOGRECORDFAILED,CommitConstant.LOGRECORDFAILEDMSG,null);
			}
		}	
		for(String id1:ids){
			try{
				result=collectionPrintersService.deletePrinters(id1);
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
	
	@RequestMapping(value="projectManage")
	 public String projectManage(HttpServletRequest request){
		Logg.writeDebugLog("进入项目管理,projectManage");
		boolean result=collectionUsersOperationService.operationRecord("进入项目管理！");
		if(!result){
			return "/logException";
		}
		return "/device/projectManage";  
	}
	
}
