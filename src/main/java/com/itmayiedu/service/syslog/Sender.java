package com.itmayiedu.service.syslog;

import com.itmayiedu.entity.EventMessage;
import com.itmayiedu.entity.SenderBean;

/**
 *@author pengdan<br>
 *描述：事件发送器接口<br>
 *创建时间:2020 九月 22 11:29:47<br>
 *修改者：<br>
 *修改日期：<br>
 *修改描述：<br>
 */
public interface Sender {

	/**
	 * ActiveMQ发送器定义
	 */
	String MQ="MQSender";
	/**
	 * 文件发送器定义
	 */
	String FILE="FileSender";
	/**
	 * 数据库发送器定义
	 */
	String DAO="DaoSender";
	/**
	 * 其它应用的发送器定义
	 */
	String APP="Application";

	/**
	 * 发送事件对象
	 * @param param 用于设置需要参数的发送器的参数
	 * @param event 用于发送的事件对象
	 */
	public void sendEvent(String param,EventMessage event);
}
