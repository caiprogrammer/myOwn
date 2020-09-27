package com.itmayiedu.service.syslog;

import java.io.Serializable;

import org.springframework.stereotype.Repository;




/**
 *@author 杨志泉<br>
 *描述：事件存储器接口，提供启动、存储、查询方法<br>
 *创建时间:2008 九月 24 15:58:59<br>
 *修改者：<br>
 *修改日期：<br>
 *修改描述：<br>
 */
@Repository
public interface IPersistenceService {
	String DB_AUDIT="DB_AUDIT";
	String DB_RAW="DB_RAW";
	String FILE="FILE";
	String AUDIT_TABLE_NAME="VENUS_AUDIT_AUDITEVENT_RT";
	String AUDIT_HIST_TABLE_NAME="VENUS_AUDIT_AUDITEVENT_HIST";
	String SESSION_TABLE_NAME="VENUS_AUDIT_SESSION_RT";
	String SESSION_HIST_TABLE_NAME="VENUS_AUDIT_SESSION_HIST";
	String RAW_DATA_TABLE_NAME="VENUS_AUDIT_AUDITRAWDATA_RT";
	String RAW_HIST_DATA_TABLE_NAME="VENUS_AUDIT_AUDITRAWDATA_HIST";
	
	String AUDIT_PROG_TABLE_NAME="VENUS_AUDIT_AUDITEVENT_PROG";//第三方程序访问堡垒机操作事件表
	String SESSION_PROG_TABLE_NAME="VENUS_AUDIT_SESSION_PROG";//第三方程序访问堡垒机认证事件表
	String AAA_AUTHENTICATION_TABLE_NAME="VENUS_AAA_AUDIT_AUTHENTICATION";
	String AAA_ACCOUNTING_TABLE_NAME="VENUS_AAA_AUDIT_ACCOUNTING";
	String AAA_AUTHORIZATION_TABLE_NAME="VENUS_AAA_AUDIT_AUTHORIZATION";
	
	/**
	 * 事件存储
	 */
	public void save(Serializable event);
	/**
	 * 定时执行
	 */
	public void executeTask();
}
