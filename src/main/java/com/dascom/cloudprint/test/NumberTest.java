package com.dascom.cloudprint.test;

import com.dascom.cloudprint.util.PrintersNumber;
import com.dascom.cloudprint.util.PrintersNumberException;

public class NumberTest {

	public static void main(String[] args) throws PrintersNumberException {

		for(int i=0;i<100;i++){
			String s=PrintersNumber.getPrintersNumber(PrintersNumber.PROJECT_WEIXIN, PrintersNumber.PRODUCT_DASCOMPRODUCT);
			System.out.println(s);
		}
		for(int i=0;i<100;i++){
			String s=PrintersNumber.getPrintersNumber(PrintersNumber.PROJECT_WEIXIN, PrintersNumber.PRODUCT_HANGXINPRODUCT);
			System.out.println(s);
		}
	}
}