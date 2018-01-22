package com.dascom.cloudprint.test;
import java.io.FileOutputStream;  

import org.dom4j.Document;  
import org.dom4j.DocumentHelper;  
import org.dom4j.Element;  
import org.dom4j.io.OutputFormat;  
import org.dom4j.io.XMLWriter;  
  
/** 
 * 用DOM4J创建xml文档 
 *  
 * 
 */  
  
public class DOM4JCreateXML {  
  
    public static void main(String[] args) throws Exception {  
          
        //1.第一种 创建文档及设置根元素节点的方式  
          
        //创建文档的根节点  
//      Document document = DocumentHelper.createDocument();  
//      //创建文档的 根元素节点  
//      Element root = DocumentHelper.createElement("Person");  
//        document.setRootElement(root);  
          
        //2.第二种 创建文档及设置根元素节点的方式  
        Element root = DocumentHelper.createElement("Person");  
        Document document = DocumentHelper.createDocument(root);  
        //给根节点添加属性  
        root.addAttribute("学校", "南大").addAttribute("位置", "江西");  
          
        //给根节点添加孩子节点  
        Element element1 = root.addElement("学生");  
        element1.addElement("姓名").addAttribute("婚姻", "单身").addText("小章");  
        element1.addElement("年龄").addText("21");  
          
        Element element2 = root.addElement("学生");  
        element2.addElement("姓名").addAttribute("婚姻", "单身").addText("小红").addElement("爱好").addText("唱歌");  
        element2.addElement("年龄").addText("22");  
                  
          
        //把生成的xml文档存放在硬盘上  true代表是否换行  
        OutputFormat format = new OutputFormat("    ",true);  
        format.setEncoding("GBK");//设置编码格式  
        XMLWriter xmlWriter = new XMLWriter(new FileOutputStream("src\\main\\webapp\\WEB-INF\\Person.xml"),format);  
      
        xmlWriter.write(document);  
        xmlWriter.close();  
    }  
  
}  
