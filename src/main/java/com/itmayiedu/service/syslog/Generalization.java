package com.itmayiedu.service.syslog;

import com.itmayiedu.entity.BaseMessage;
import com.itmayiedu.entity.EventMessage;

/**
 *@author pengdan<br>
 *描述：泛化器接口，所有泛化器实现此接口的consume方法<br>
 *创建时间:2020 九月 22 09:51:25<br>
 *修改者：<br>
 *修改日期：<br>
 *修改描述：<br>
 */
public interface Generalization {

	/**
	 * 根据BaseMessage，生成EventMessage，返回字符串数组【发送器类型，参数或者消息通道】
	 * 设置ID eventMessage.setId(GlobalIdBuild.getId());
	 * @param baseMessage
	 * @param eventMessage
	 * @return
	 */
	public String[] consume(BaseMessage baseMessage,EventMessage eventMessage);
}
