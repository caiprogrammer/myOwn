package com.itmayiedu.entity;
/**
 * @author Administrator
 * 应用系统配置类
 */
public class SysConfig {
	//应用系统名称
	String sysName;
	//应用系统日志存储路径
	String logPath;
	//应用系统读取器类型（实现）
	String format;
	//事件读取周期
	String period;
	//事件所用协议
	String protocol;
	//所用泛化器（实现）
	String generalization;
	//使用端口号
	String port;
	//默认编码
	String encoding;
	//是否备份FTP日志文件
	String backup;
	
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public String getLogPath() {
		return logPath;
	}
	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getGeneralization() {
		return generalization;
	}
	public void setGeneralization(String generalization) {
		this.generalization = generalization;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getBackup() {
		return backup;
	}
	public void setBackup(String backup) {
		this.backup = backup;
	}
}
