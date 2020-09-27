package com.itmayiedu.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author yangzhiquan
 * 数据库配置类
 */
public class DBConfig {
	//应用系统名称
	private String sysName;
	//应用系统对应的hbm文件
	private String key;
	//数据库驱动
	private String driverClassName;
	//数据库URL
	private String url;
	//数据库用户名
	private String username;
	//数据库密码
	private String password;
	//数据库最大连接数
	private String maxActive;
	//数据库最大空闲连接数
	private String maxIdle;
	//数据库的表名称
	private String tableName;
	//数据库的查询条件字段
	private String condition;
	//数据库的主查询条件字段
	private Map mainIndex;
	//其它查询条件
	private List<Map> union_condition;
	//数据库表字段与标准格式的对应
	private Map coloums=new HashMap(); 
	//数据库表事件等级字段值与4A事件等级标准值的对应
	private Map auditlevel=new HashMap();
	
	//是否需要进行字符集转换，true-转换，false-不转换
	private String convertEncoding;
	
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(String maxActive) {
		this.maxActive = maxActive;
	}
	public String getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(String maxIdle) {
		this.maxIdle = maxIdle;
	}
	public Map getColoums() {
		return coloums;
	}
	public void setColoums(Map coloums) {
		this.coloums = coloums;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public List<Map> getUnion_condition() {
		return union_condition;
	}
	public void setUnion_condition(List<Map> union_condition) {
		this.union_condition = union_condition;
	}
	public Map getMainIndex() {
		return mainIndex;
	}
	public void setMainIndex(Map mainIndex) {
		this.mainIndex = mainIndex;
	}
	public Map getAuditlevel() {
		return auditlevel;
	}
	public void setAuditlevel(Map auditlevel) {
		this.auditlevel = auditlevel;
	}
	public String getConvertEncoding() {
		return convertEncoding;
	}
	public void setConvertEncoding(String convertEncoding) {
		this.convertEncoding = convertEncoding;
	}
	
	
}
