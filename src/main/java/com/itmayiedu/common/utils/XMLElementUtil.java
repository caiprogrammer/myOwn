package com.itmayiedu.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Element;


/**
 *@author 杨志泉<br>
 *描述：<br>
 *创建时间:2008 十一月 21 16:55:52<br>
 *修改者：<br>
 *修改日期：<br>
 *修改描述：<br>
 */
public class XMLElementUtil {

	/**
	 * 读取数据库日志配置文件*.mb.xml中的mainIndex标签值
	 * @param mainIndex
	 * @return
	 */
	public static Map parseMainIndex(List mainIndex){
		Map m=new HashMap();
		Iterator it=mainIndex.iterator();
		while(it.hasNext()){
			Element ee=(Element)it.next();
			List lt=ee.getChildren("key");
			if(lt!=null && lt.size()>0){
				Element et=(Element)lt.get(0);
				String key=et.getValue();
				m.put("key", key);
			}
			lt=ee.getChildren("type");
			if(lt!=null && lt.size()>0){
				Element et=(Element)lt.get(0);
				String type=et.getValue();
				m.put("type", type);
			}
			lt=ee.getChildren("value");
			if(lt!=null && lt.size()>0){
				Element et=(Element)lt.get(0);
				String value=et.getValue();
				m.put("value", value);
			}
			lt=ee.getChildren("function");
			if(lt!=null && lt.size()>0){
				Element et=(Element)lt.get(0);
				String value=et.getValue();
				m.put("function", value);
			}
		}
		
		return m;
	}
	
	/**
	 * 读取数据库日志配置文件*.mb.xml中的单个union标签子值
	 * @param union
	 * @return
	 */
	public static Map parseUnionIndex(List condition){
		Map m=new HashMap();
		Iterator it=condition.iterator();
		while(it.hasNext()){
			Element ee=(Element)it.next();
			if("key".equalsIgnoreCase(ee.getName())){
				String key=ee.getValue();
				m.put("key", key);
			}else if("type".equalsIgnoreCase(ee.getName())){
				String type=ee.getValue();
				m.put("type", type);
			}else if("operator".equalsIgnoreCase(ee.getName())){
				String operator=ee.getValue();
				m.put("operator", operator);
			}else if("value".equalsIgnoreCase(ee.getName())){
				String value=ee.getValue();
				m.put("value", value);
			}
		}
		
		return m;
	}
	/**
	 * 读取数据库日志配置文件*.mb.xml中的condition标签值
	 * @param condition
	 * @return
	 */
	public static List<Map> parseUnionCondition(List conditionList){
		if(conditionList!=null && conditionList.size()>0){
			Element e=(Element)conditionList.get(0);
			return parseUnion(e.getChildren("union"));
		}else{
			return new ArrayList();
		}
	}
	
	public static List<Map> parseUnion(List union){
		List list=new ArrayList();
		Iterator it=union.iterator();
		while(it.hasNext()){
			Element ee=(Element)it.next();
			Map m=parseUnionIndex(ee.getChildren());
			list.add(m);
		}
		
		return list;
	}
}
