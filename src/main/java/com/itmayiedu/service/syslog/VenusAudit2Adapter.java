package com.itmayiedu.service.syslog;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jms.JMSException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.itmayiedu.common.utils.FileUtil;
import com.itmayiedu.entity.EventMessage;



public class VenusAudit2Adapter {
	private String va2To4AMapping = FileUtil.readAll(URLDecoder.decode(VenusAudit2Adapter.class.getResource("/").getPath()+ "/conf/eventserver/VA2TO4AMapping.xml"));
	private Document xmldocument;
	private Element root; 
	private static Map levelMap; //事件级别对应关系
	private static Map eventTypeMap;//事件类型对应关系
	private static Map radiusTypeMap;//针对Radius事件处理内容
	private static Map radiusMap;  //AuditRadius对象集
	private static Map radiusEventMap;//Radius类事件集
	private static Map ruleMap;//审计操作对应关系
	private static Map protocolTypeMap;//协议类型
	private static Object initLock = new Object();
	private static VenusAudit2Adapter venusAudit2Adapter = null;
	
	
	public static VenusAudit2Adapter getInstance()
	{
		if (venusAudit2Adapter == null) {
			synchronized (initLock) {
				if (venusAudit2Adapter == null) {
					try {
						venusAudit2Adapter = new VenusAudit2Adapter();
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}
			}
		}
		return venusAudit2Adapter;
	}
	public VenusAudit2Adapter(){
		try{
			levelMap = new HashMap();
			eventTypeMap = new HashMap();
			radiusTypeMap = new HashMap();
			radiusMap = new HashMap();
			radiusEventMap = new HashMap();
			ruleMap = new HashMap();
			protocolTypeMap = new HashMap();
			this.initialize();
		}
		catch(JMSException e){}
		catch(DocumentException e){}
	}
	public void initialize() throws JMSException, DocumentException {
		xmldocument = DocumentHelper.parseText(va2To4AMapping);
		root = xmldocument.getRootElement();
		String xml = va2To4AMapping;
		if(!"".equals(xml) && root!=null){
			//加载VA2TO4A映射内容到内存
			//加载级别
			for (Iterator i = root.elementIterator("Level"); i.hasNext();) {
				Element level  = (Element) i.next();
				for (Iterator k = level.elementIterator(); k.hasNext();) {
					Element attrib = (Element) k.next();
					String sourceValue = attrib.attributeValue("SourceValue");
					String destValue = attrib.attributeValue("DestValue");
					levelMap.put(sourceValue, destValue);
				}
			}
			//加载事件类型
			for (Iterator i = root.elementIterator("EventType"); i.hasNext();) {
				Element level  = (Element) i.next();
				for (Iterator k = level.elementIterator(); k.hasNext();) {
					Element attrib = (Element) k.next();
					String sourceValue = attrib.attributeValue("SourceValue");
					String destValue = attrib.attributeValue("DestValue");
					String Name = attrib.attributeValue("Name");
					eventTypeMap.put(sourceValue, destValue);
				}
			}
			//加载天玥II的Radius类型相关信息
			for (Iterator i = root.elementIterator("RadiusType"); i.hasNext();) {
				Element level  = (Element) i.next();
				for (Iterator k = level.elementIterator(); k.hasNext();) {
					Element attrib = (Element) k.next();
					String sourceValue = attrib.attributeValue("SourceValue");
					String destValue = attrib.attributeValue("DestValue");
					radiusTypeMap.put(sourceValue, destValue);
				}
			}
			//加载操作名称规则集
			for (Iterator i = root.elementIterator("Rules"); i.hasNext();) {
				Element level  = (Element) i.next();
				for (Iterator k = level.elementIterator(); k.hasNext();) {
					Element attrib = (Element) k.next();
					String name = attrib.attributeValue("Name");
					String operation = attrib.attributeValue("Operation");
					String pattern = attrib.attributeValue("Pattern");
					Map map = new HashMap();
					map.put("Name", name);
					map.put("Operation", operation);
					map.put("Pattern", pattern);
					ruleMap.put(name, map);
				}
			}
			
			//加载协议类别规则集
			for (Iterator i = root.elementIterator("ProtocolType"); i.hasNext();) {
				Element level  = (Element) i.next();
				for (Iterator k = level.elementIterator(); k.hasNext();) {
					Element attrib = (Element) k.next();
					String Type = attrib.attributeValue("Type");
					String Name = attrib.attributeValue("Name");
					String Protocol = attrib.attributeValue("Protocol");
					String Pattern = attrib.attributeValue("Pattern");
					Map map = new HashMap();
					map.put("Type", Type);
					map.put("Name", Name);
					map.put("Protocol", Protocol);
					map.put("Pattern", Pattern);
					protocolTypeMap.put(Type, map);
				}
			}
		}
		
	}
	/**
	 * 通过天玥II传来的Level值，取得与4A对应的Level值
	 * @param level String 天玥II里的Level值
	 * @return int 4A中的Level值
	 */
	public int getLevel(String level){
		int iLevel = 1;
		try{
			if(levelMap != null) {iLevel = Integer.parseInt((String)levelMap.get(level));}
		}
		catch(Exception e){
			
		}
		return iLevel;
	} 
	/**
	 * 通过天玥II传来的协议类型值，取得与4A对应的事件类型值
	 * @param eventType String 天玥II里的协议类型值
	 * @return String 4A中的事件类型值
	 */
	public int getEventType(String eventType){
		int iEventType = 10;
		try{
			if(eventTypeMap != null) {iEventType = Integer.parseInt((String)eventTypeMap.get(eventType));}
		}
		catch(Exception e){
			
		}
		return iEventType;
	} 
	public String getOptName(String optName)
	{
		String opt = optName;
		if(null!=opt && !"".equals(opt)){
			int i = opt.indexOf("，");
			if(i==-1) i = opt.indexOf(",");
			if(i==-1) i = opt.indexOf("；");
			if(i==-1) i = opt.indexOf(";");
			if(i==-1) i = opt.indexOf("=");
			if(i==-1) 
				opt = "";
			else
				opt = opt.substring(0, i);
		}
		return opt;
	}
	/**
	 * 通过天玥II传来的协议值，取得其对应的类别，以便对协议的专有字段进行分析
	 * @param protocol String 天玥II里的协议值
	 * @return 类别代码
	 */
	public String getProtocolType(String protocol){
		if(protocol==null || protocol.trim().equals("")){
			return null;
		}
		protocol = protocol.toUpperCase();
		String type = null;
		try{
			if(protocolTypeMap != null) {
				for (Iterator iter = protocolTypeMap.entrySet().iterator(); iter.hasNext();) {
				    Map.Entry entry = (Map.Entry) iter.next();
				    String type_k = (String)entry.getKey();//返回与此项对应的键
				    Map map = (Map)entry.getValue();//返回与此项对应的值
				    if(map!=null){
					    String protocols = (String)map.get("Protocol");//取出协议值
					    if(protocols!=null){
					    	protocols = protocols.toUpperCase();
					    	if(protocols.indexOf(protocol)!=-1){
					    		type = type_k;
					    		break;
					    	}
					    }
				    }
				}
			}
		}
		catch(Exception e){
			
		}
		return type;
	} 
	//处理天玥II 6069版本的审计事件的操作名称
	public Map getOperation(EventMessage auditEvent)
	{
		Map map = new HashMap();
		map.put("SpsensitiveObj", "");
		map.put("Operation", "");
		String optName = auditEvent.getParam();
		optName = getOptName(optName);
		if(ruleMap != null) {
			Map rule = (Map)ruleMap.get(optName);
			if(rule != null){
				map.put("Operation", rule.get("Operation"));
				String pattern = (String)rule.get("Pattern");
				String spsensitiveObj = "";
				if(pattern != null){
					//进行正则表达式匹配
					Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
					Matcher m = p.matcher(auditEvent.getParam());
					if (m.find()) {
				       for(int i=1;i<=m.groupCount();i++){ 
				    	   spsensitiveObj = m.group(i); 
				    	   break;
				       } 
					}
				}
				map.put("SpsensitiveObj", spsensitiveObj);
			}
		}
		return map;		
	}
	
	/**
	 * 处理天玥II 6082版本的审计事件中，获取各协议专有字段值
	 * @param auditEvent
	 * @return Map map
	 */
	public Map getProprietaryValue(EventMessage auditEvent){
		Map map = new HashMap();
		map.put("SpsensitiveObj", "");
		map.put("Operation", "");
		
		String protocol = auditEvent.getProtocol();//取出当前协议类型
		String protocoltype = venusAudit2Adapter.getProtocolType(protocol);//取出当前协议类别代码
		String param = auditEvent.getParam();//取出param字段值
		if(protocoltype != null && param!=null && !param.trim().equals("")) {
			//目前暂时只对数据库类型事件专有字段进行解析，获取表名和操作描述
			if(protocoltype.equals("1")){//数据库类型
				Map pt_map = (Map)protocolTypeMap.get(protocoltype);
				if(pt_map != null){
					String pattern= (String)pt_map.get("Pattern");//正则表达式
					if(pattern != null && !pattern.trim().equals("")){
						//进行正则表达式匹配
						Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
						Matcher m = p.matcher(param);
						if (m.find()) {
						   String keyvalue= "";
						   String key = "";
						   String value = "";
					       for(int i=1;i<=m.groupCount();i++){ 
					    	   keyvalue = m.group(i);
					    	   key = keyvalue.substring(0,keyvalue.indexOf("="));
					    	   value = keyvalue.substring(keyvalue.indexOf("=")+1);					    	   
					    	   map.put(key, value==null?"":value.trim());					    	   
					       } 
						}
						
					}
					map.put("Operation", getSqlOperation((String)map.get("sql")));
					map.put("SpsensitiveObj", map.get("table_name")==null?"":map.get("table_name"));//表名
				}
			}
		}
				
		return map;
	}
	/**
	 * 根据sql语句，分析其操作类型
	 * @param sql
	 * @return
	 */
	public static String getSqlOperation(String sql){
		if(sql==null || sql.trim().equals("")){
			return "";
		}
		String operation = "";
		if(ruleMap != null) {
			for (Iterator iter = ruleMap.entrySet().iterator(); iter.hasNext();) {
			    Map.Entry entry = (Map.Entry) iter.next();
			    String name = (String)entry.getKey();//返回与此项对应的键
			    Map rule = (Map)entry.getValue();//返回与此项对应的值
			    if(rule!=null){
			    	String pattern = (String)rule.get("Pattern");
					if(pattern != null && !pattern.trim().equals("")){
						//进行正则表达式匹配
						Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
						Matcher m = p.matcher(sql);
						if (m.find()) {
							operation = (String)rule.get("Operation");
							break;					        
						}
					}
			    }
			}
		}
		return operation;
	}
	
	public static Map getRadiusTypeMap() {
		return radiusTypeMap;
	}

	public static Map getRadiusMap() {
		return radiusMap;
	}
	public static Map getRadiusEventMap() {
		return radiusEventMap;
	}
	public static Map getRuleMap() {
		return ruleMap;
	}
	public static void setRadiusEvent(String identifier,EventMessage auditEvent){
		radiusEventMap.put(identifier,auditEvent);
	}
	public static EventMessage getRadiusEvent(String identifier){
		return (EventMessage)radiusEventMap.get(identifier);
	}
	public static EventMessage removeRadiusEvent(String identifier){
		return (EventMessage)radiusEventMap.remove(identifier);
	}
	public static void main(String[] args) {
		VenusAudit2Adapter venusAudit2Adapter = VenusAudit2Adapter.getInstance();
		System.out.println(venusAudit2Adapter.getEventType("DB2"));
		System.out.println(venusAudit2Adapter.getProtocolType("oracle"));
		System.out.println(venusAudit2Adapter.getSqlOperation("select * from dual;"));
	}
}
