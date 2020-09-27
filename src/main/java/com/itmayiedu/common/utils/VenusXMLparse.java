package com.itmayiedu.common.utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.itmayiedu.entity.SysConfig;


/**
 * @author yangzhiquan
 * xml解析器
 */
public class VenusXMLparse {
	private static final Log log = LogFactory.getLog(VenusXMLparse.class);
	private SAXBuilder builder = new SAXBuilder(false);;
	private Document doc = null;
	private Element root = null;

	public SAXBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(SAXBuilder builder) {
		this.builder = builder;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public Element getRoot() {
		return root;
	}

	public void setRoot(Element root) {
		this.root = root;
	}
	/**
	 * 将xml文件内容读到JDOM中
	 * @param filename
	 */
	private void init(String filename) {
		ClassLoader cl = this.getClass().getClassLoader();
		URL url = cl.getResource("");
		String file = url.getPath() + filename;
		try {
//			this.builder = new SAXBuilder(false);
			this.doc = this.getBuilder().build(file);
			// 得到xml文件的根节点
			this.root = this.getDoc().getRootElement();
		} catch (Exception je) {
			je.printStackTrace();
		}
	}
	/**
	 * 获得二级节点
	 * @return
	 */
	private List getSystems() {
		List list = this.getRoot().getChildren("system");
		return list;
	}
	/**
	 * 解析都是什么应用系统和他们的相关配置信息
	 * @param 节点集合
	 * @return 封装了应用系统配置bean的集合
	 */
	private List getSysConfig(List list){
		List o=new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			o.add(SysBeanFactory(e));
		}
		return o;
	}
	/**
	 * 根据节点信息封装成SysConfig类
	 * @param e
	 * @return
	 */
	private SysConfig SysBeanFactory(Element e){
		SysConfig c=new SysConfig();
		String name=e.getChildText("name");
		String path=e.getChildText("path");
		String format=e.getChildText("format-class");
		String period=e.getChildText("period");
		String protocol=e.getChildText("protocol");
		String generalization=e.getChildText("generalization");
		String port=e.getChildText("port");
		String encoding=e.getChildText("encoding");
		String backup=e.getChildText("backup");//是否备份ftp日志文件
		
		if(name==null||format==null){
			log.error("配置文件有误，业务系统名称不能为空，请检查配置文件！");
			System.exit(0);
		}
		//默认编码为GBK
		if(encoding==null) encoding = "GBK";
		//默认不备份日志
		if(backup==null) backup = "false";
		
		c.setSysName(name);
		c.setLogPath(path);
		c.setFormat(format);
		c.setPeriod(period);
		c.setProtocol(protocol);
		c.setEncoding(encoding);
		c.setBackup(backup);
		if(generalization == null || "".equals(generalization)){
			generalization = "cn.com.venustech.eventserver.reader.syslog.SysLogGeneralization";
		}
		c.setGeneralization(generalization);
		c.setPort(port);
		
		return c;
	}
	/**
	 * 解析都有哪些数据源和他们的相关配置信息
	 * @param 节点集合
	 * @return 封装了数据源配置bean的集合
	 */
	private List getDbConfig(List list){
		List o=new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			List l=e.getChildren("property");
			Iterator ita=l.iterator();
			while(ita.hasNext()){
				Element el=(Element)ita.next();
				o.add(DBBeanFactory(el));
			}
		}
		return o;
	}
	/**
	 * 根据节点信息封装成DBConfig类
	 * @param e
	 * @return
	 */
	private DBConfig DBBeanFactory(Element e){
		DBConfig dbconfig=new DBConfig();
		String sysName=e.getAttributeValue("name");
		String key=e.getChildText("key");
		if(sysName==null||key==null){
			throw new RuntimeException("数据源配置错误，请检查配置文件!");
		}
		dbconfig.setSysName(sysName);
		dbconfig.setKey(key);
		getHBMinfo(dbconfig,key);
		return dbconfig;
	}
	public List parseSysXML(String filename){
//		VenusXMLparse xp = new VenusXMLparse();
		init(filename);
		return getSysConfig(getSystems());
	}
	public List parseDbXML(String filename){
//		VenusXMLparse xp = new VenusXMLparse();
		init(filename);
		return getDbConfig(getSystems());
	}
	
	/**
	 * 解析某个应用系统的数据库表配置信息
	 * @param 
	 * @return 封装了应用系统配置bean的集合
	 */
	public void getHBMinfo(DBConfig config,String filename){
		VenusXMLparse xp=new VenusXMLparse();
		xp.init(filename);
		Element e=xp.getRoot().getChild("session-factory");
		List list=e.getChildren("property");
		Iterator it=list.iterator();
		while(it.hasNext()){
			Element ee=(Element)it.next();
			this.parseDataSource(ee,config);
			
		}
		Element e1=xp.getRoot().getChild("table");
		String tableName=e1.getAttributeValue("name");
		config.setTableName(tableName);
		String convertencoding=e1.getAttributeValue("convertencoding","false");//是否转换字符集，默认为false
		config.setConvertEncoding(convertencoding);
		if(!validateMB(config)){
			throw new RuntimeException("mb文件配置错误，请检查配置文件--"+filename);
		}
		list=e1.getChildren("property");
		if(list!=null && list.size()>0){
			Map map=new HashMap();
			it=list.iterator();
			while(it.hasNext()){
				Element ee=(Element)it.next();
				map.put(ee.getAttributeValue("name"), ee.getAttributeValue("column"));
			}
			config.setColoums(map);
		}
		
		config.setMainIndex(XMLElementUtil.parseMainIndex(e1.getChildren("mainIndex")));
//		log.info(config.getMainIndex().get("key"));
//		log.info(config.getMainIndex().get("type"));
		
		config.setUnion_condition(XMLElementUtil.parseUnionCondition(e1.getChildren("condition")));
//		List<Map> lt=config.getUnion_condition();
//		for(Map m:lt){
//			log.info(m.get("key"));
//			log.info(m.get("type"));
//			log.info(m.get("operator"));
//			log.info(m.get("value"));
//		}
		
		//数据库表事件等级字段值与4A事件等级标准值的对应
		Element e2=xp.getRoot().getChild("Level");
		if(e2 != null){
			list=e2.getChildren("Mapping");
			if(list!=null && list.size()>0){
				Map map=new HashMap();
				it=list.iterator();
				while(it.hasNext()){
					Element ee=(Element)it.next();
					map.put(ee.getAttributeValue("SourceValue"), ee.getAttributeValue("DestValue"));
				}
				config.setAuditlevel(map);
			}
		}
	}
	/**
	 * 校验mb配置文件
	 */
	private boolean validateMB(DBConfig config){
		if(config.getDriverClassName()==null||config.getUrl()==null||config.getUsername()==null){
			return false;
		}
		return true;
	}
	/**
	 * 设置数据源
	 * @param ee
	 * @param config
	 */
	private void parseDataSource(Element ee,DBConfig config){
		if("connection.driver_class".equals(ee.getAttributeValue("name"))){
			config.setDriverClassName(ee.getValue());
		}
		if("connection.url".equals(ee.getAttributeValue("name"))){
			config.setUrl(ee.getValue());
		}
		if("connection.username".equals(ee.getAttributeValue("name"))){
			config.setUsername(ee.getValue());
		}
		if("connection.password".equals(ee.getAttributeValue("name"))){
			config.setPassword(ee.getValue());
		}
		if("maxActive".equals(ee.getAttributeValue("name"))){
			config.setMaxActive(ee.getValue());
		}
		if("maxIdle".equals(ee.getAttributeValue("name"))){
			config.setMaxIdle(ee.getValue());
		}
	}
	public static void main(String[] args) {
		VenusXMLparse xp=new VenusXMLparse();
//		parseSysXML("cn/com/venustech/server/config/eventConfig.xml");
		xp.parseDbXML("cn/com/venustech/server/config/DataSource.xml");
	}
}
