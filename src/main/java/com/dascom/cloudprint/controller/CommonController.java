package com.dascom.cloudprint.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dascom.cloudprint.common.CommitConstant;
import com.dascom.cloudprint.dto.PoolBatchJudgeDto;
import com.dascom.cloudprint.dto.PoolBatchJudgeNumber;
import com.dascom.cloudprint.dto.PoolBatchJudgeParmDto;
import com.dascom.cloudprint.dto.PrintNumberTakeDto;
import com.dascom.cloudprint.entity.device.CollectionIdPool;
import com.dascom.cloudprint.service.CollectionPrintersPoolService;
import com.dascom.cloudprint.service.CollectionPrintersService;
import com.dascom.cloudprint.util.JsonTransform;
import com.dascom.cloudprint.util.Logg;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("common")
public class CommonController {
	@Autowired
	private CollectionPrintersService collectionPrintersService;
	@Autowired
	private CollectionPrintersPoolService collectionPrintersPoolService;
	
	//批处理插入编号池
	@ResponseBody
	@RequestMapping(value="printPoolJudge",method=RequestMethod.POST)
	 public String printPoolJudge(HttpServletRequest request,@RequestBody PoolBatchJudgeParmDto pools) throws UnsupportedEncodingException{
		Logg.writeDebugLog("进入公共接口设备编号判断,printPoolJudge");
		Logg.writeDebugLog("Json数据："+pools);
		//List<PoolBatchJudgeParmDto> pools=new Gson().fromJson(poolsJson, new TypeToken<List<PoolBatchJudgeParmDto>>(){}.getType());
		Vector<PoolBatchJudgeNumber> failureNumbers=new Vector<PoolBatchJudgeNumber>();

		PoolBatchJudgeDto dto=new PoolBatchJudgeDto();
		boolean result=true;
		Gson gson=new Gson();
		//分支1修改
		/*if(poolsJson==null || poolsJson.equals("")){
			dto.setErrorReason("Parameter poolsJson is null!");
			dto.setFailureNumbers(failureNumbers);
			return gson.toJson(dto);
		}*/
		try{
			//PoolBatchJudgeParmDto pools = gson.fromJson(poolsJson, PoolBatchJudgeParmDto.class); 
			if(pools.getNumbers() == null){
				dto.setErrorReason("Parameter numbers is null!");
				dto.setFailureNumbers(failureNumbers);
				return gson.toJson(dto);
			}
			Logg.writeDebugLog("批处理条数："+pools.getNumbers().size());
			for(String number:pools.getNumbers() ){
				Logg.writeDebugLog("设备编号："+number);
				PoolBatchJudgeNumber failureNumber=new PoolBatchJudgeNumber();
				CollectionIdPool pool=new CollectionIdPool();
				pool.setRemark(pools.getRemark());
				pool.setNumber(number);
				boolean printerResult=collectionPrintersService.findPrintersByNumberIsHave(pool.getNumber());
				Logg.writeDebugLog("printerResult："+printerResult);
				if(!printerResult){
					//先放在一个list
					boolean poolResult=collectionPrintersPoolService.findPrintersByNumberIsHave(pool.getNumber());
					Logg.writeDebugLog("poolResult："+poolResult);
					if(!poolResult){
						pool.setUpload_date(new Date());
						boolean insertResult=collectionPrintersPoolService.insertPrintersPool(pool);
						if(insertResult){
							Logg.writeDebugLog( JsonTransform.loginJsonTransform(CommitConstant.OPERATIONINSERTSUCCESSFUL,CommitConstant.OPERATIONINSERTSUCCESSFULMSG,pool));
							
						}else{
							result=false;
							Logg.writeDebugLog(JsonTransform.loginJsonTransform(CommitConstant.OPERATIONINSERTFAILURE,CommitConstant.OPERATIONINSERTFAILUREMSG,pool));
							failureNumber.setNumber(number);
							failureNumber.setNumberReason("Failed insert!");
							failureNumbers.add(failureNumber);
						}
						
					}else{
						result=false;
						Logg.writeDebugLog(JsonTransform.loginJsonTransform(CommitConstant.BATCHFAILURE4014,CommitConstant.BATCHFAILURE4014MSG,pool));
						failureNumber.setNumber(number);
						failureNumber.setNumberReason("IdPool record already exists!");
						failureNumbers.add(failureNumber);
						continue;
					}
				}else{
					result=false;
					Logg.writeDebugLog(JsonTransform.loginJsonTransform(CommitConstant.BATCHFAILURE4013,CommitConstant.BATCHFAILURE4013MSG,pool));
					failureNumber.setNumber(number);
					failureNumber.setNumberReason("Printers record already exists!");
					failureNumbers.add(failureNumber);
					continue;
				}
			}
		}catch(JsonSyntaxException e){
			e.printStackTrace();
			dto.setErrorReason("Json convert errors!");
			dto.setFailureNumbers(failureNumbers);
			return gson.toJson(dto);
		}
		catch(Exception e){
			e.printStackTrace();
			dto.setErrorReason("System error!");
			dto.setFailureNumbers(failureNumbers);
			return gson.toJson(dto);
		}
		
		dto.setFailureNumbers(failureNumbers);
		
		if(result){
			dto.setErrorReason(null);
			dto.setFailureNumbers(null);
		}else{
			dto.setErrorReason("Part of the failure!");
		}
		Logg.writeDebugLog("返回："+gson.toJson(dto));
		return gson.toJson(dto);

	}
	
	//批处理拿编号
	@ResponseBody
	@RequestMapping(value="printNumberTake/{count}",method=RequestMethod.POST)
	 public String printNumberTake(HttpServletRequest request,@PathVariable int count){
		Logg.writeDebugLog("进入公共接口拿设备编号,printNumberTake");
		Logg.writeDebugLog("拿的数据条数："+count);
		PrintNumberTakeDto dto=new PrintNumberTakeDto();
		Gson gson=new Gson();
		List<String> list=null;
		if(count <= 0){
			dto.setErrorReason("Parameter count must greater than zero!");
			return gson.toJson(dto);
		}
		try{
			 list=collectionPrintersPoolService.takePrinterPoolByCount(count);
			 dto.setNumbers(list);
			
		}catch(Exception e){
			e.printStackTrace();
			dto.setErrorReason("System error!");
			return gson.toJson(dto);
		}
		
		if(list==null){
			Logg.writeDebugLog("返回数据：为null");
			dto.setErrorReason("Operation failure!");
			return gson.toJson(dto);
		}
		
		Logg.writeDebugLog("返回数据："+gson.toJson(list));
		return  gson.toJson(dto);
	}

}
