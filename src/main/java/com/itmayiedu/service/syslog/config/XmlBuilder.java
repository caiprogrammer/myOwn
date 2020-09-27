package com.itmayiedu.service.syslog.config;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XmlBuilder {

	/**
     * 将XML转为指定的POJO对象
     *
     * @param clazz 需要转换的类
     * @param xmlStr xml数据
     * @return
     */
	public static Object xmlStrToObject(Class<?> clazz,String xmlStr){
		
		Object xmlObject = null;
		Reader reader = null;
		
		try {
			//利用JAXBContext将类转为一个实例
			JAXBContext context = JAXBContext .newInstance(clazz);
			//xml转为对象的接口
			Unmarshaller unmarshaller  = context.createUnmarshaller();
			reader = new StringReader(xmlStr);
			xmlObject = unmarshaller.unmarshal(reader);
			if(reader != null){
				reader.close();
			}
			
			return xmlObject;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return xmlStr;
	}
}
