package com.itmayiedu.service.syslog;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;

import com.itmayiedu.entity.BaseMessage;
import com.itmayiedu.entity.EventMessage;

/**
 * 日志泛化队列处理
 * @author Administrator
 *
 */
public class ScanQueue {

	private static Logger log = Logger.getLogger(ScanQueue.class);

	private BlockingQueue<BaseMessage>	queue;
	private static final SenderPool senderPool = SenderPool.getInstance();
	
	//泛化器
	private Generalization glz;
	private static long num=0l;
	public ScanQueue(BlockingQueue<BaseMessage> queue,String generalizationClassName){
		this.queue = queue;
		
		try {
			glz = (Generalization) Class.forName(generalizationClassName).newInstance();
			if(glz == null){
				log.error("syslog接收泛化器为空，请检查配置文件。系统退出......");
				System.exit(0);
			}
		} catch (Exception e) {
			log.error("系统内存在syslog事件采集，但syslog泛化器为空或者实例错误，请检查配置文件。系统退出......");
			System.exit(0);
		} 
	}
	
	@Async
	public void consume(){
		try {
			BaseMessage baseMessage = this.queue.take();
			EventMessage em=new EventMessage();
			//泛化
			log.info("原始日志为："+baseMessage.getMessage());
			String[] result = glz.consume(baseMessage,em);
			//发送
			if(result!=null){
				log.info("syslog event "+ ++num+":"+em.toString());
			}
			this.sendMessage(result, em);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 使用中间件发送消息
	 * @param result
	 * @param o
	 */
	private void sendMessage(String[] result,EventMessage o){
		senderPool.getSender(result[0]).sendEvent(result[1], o);
	}	
	 
}
