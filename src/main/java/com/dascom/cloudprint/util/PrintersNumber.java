package com.dascom.cloudprint.util;


public class PrintersNumber {
	
	public static final String PROJECT_WEIXIN="WEIXINPROJECT";
	public static final String PRODUCT_DASCOMPRODUCT="DASCOMPRODUCT";
	public static final String PRODUCT_HANGXINPRODUCT="HANGXINPRODUCT";
	//微信项目
	private static final String WEIXINPROJECT="PW"; 
	//得实产品
	private static final String DASCOMPRODUCT="D0"; 	
	//航信产品
	private static final String HANGXINPRODUCT="A0";
	//保留
	private static final String KEEP="00";
	//序号
	private static final int sequenceNumberArray[]=new int[2];//0为得实序号，1为航信序号
	//最大值
	private static final Integer SEQUENCENUMBERMAX=0XFFFFFF;
	//最小值
	private static final Integer SEQUENCENUMBERMIN=0X000000;

	/** 
	* 生成得实云打印编号 
	* <p>通过项目、产品生成云打印编号<br> 
	* @param  project 项目
	* @param  product 产品
	* @return String 云打印编号
	* @throws PrintersNumberException 
	*/ 
	public static String getPrintersNumber(String project,String product) throws PrintersNumberException{		
		String number="";
		if(project.equals(PROJECT_WEIXIN)){
			
			number+=WEIXINPROJECT;
			
			if(product.equals(PRODUCT_DASCOMPRODUCT)){	
				
				number+=DASCOMPRODUCT;
				number+=KEEP;
				if(SEQUENCENUMBERMIN <= sequenceNumberArray[0] && sequenceNumberArray[0] <= SEQUENCENUMBERMAX){

					synchronized ("number") {
						
						String sequenceNumberString=Integer.toString(sequenceNumberArray[0], 16).toUpperCase();
						StringBuilder  sb = new StringBuilder (sequenceNumberString);  
						
						while(sb.length()!=6){
							
							sb.insert(0, "0");
						}
						
						number+=sb.toString();
						sequenceNumberArray[0]++;
					}
					
				}else{
					
					throw new PrintersNumberException("生成序号过程错误，序号越界:"+sequenceNumberArray[0]);
				}
				
			}else if(product.equals(PRODUCT_HANGXINPRODUCT)){
				
				number+=HANGXINPRODUCT;
				number+=KEEP;
				if(SEQUENCENUMBERMIN <= sequenceNumberArray[1] && sequenceNumberArray[1] <= SEQUENCENUMBERMAX){
					
					synchronized ("number") {
						
						String sequenceNumberString=Integer.toString(sequenceNumberArray[1], 16).toUpperCase();
						StringBuilder  sb = new StringBuilder (sequenceNumberString);  
						
						while(sb.length()!=6){
							
							sb.insert(0, "0");
						}
						
						number+=sb.toString();
						sequenceNumberArray[1]++;
					}
					
				}else{
					
					throw new PrintersNumberException("生成序号过程错误，序号越界:"+sequenceNumberArray[1]);
				}
				
			}else{
				
				throw new PrintersNumberException("getPrintersNumber()方法,参数错误:product:"+product);
			}
		}else{
			
			throw new PrintersNumberException("getPrintersNumber()方法,参数错误:project:"+project);
		}
			
		return number;
	}

}