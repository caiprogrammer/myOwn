package com.itmayiedu.service.syslog;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.itmayiedu.entity.BaseMessage;
import com.itmayiedu.entity.SysConfig;
import com.itmayiedu.service.ConfigDataServiceImpl;
import com.itmayiedu.service.syslog.config.SystemName;

/**
 * 日志接收中转服务器类，它在指定的端口侦听，并将接收到的数据放到queue中
 * @author pengdan
 *
 */
@Service
public class SyslogGetService {

	private static Logger log = Logger.getLogger(SyslogGetService.class);
	private static BlockingQueue<BaseMessage> queue =  new LinkedBlockingQueue<>(10000);; // 定义多个数据队列，存储数据;
	/**
	 * TCP和UDP的监听端口
	 */
	public static int ECHO_PORT = 514;
	/**
	 * 数据报接收缓冲区大小
	 */
	public static int ECHO_BUFFER = 5;
	
	@Autowired
	ConfigDataServiceImpl configDataServiceImpl;
	
	//@Async
	//@Scheduled(fixedRate = 5000)//springboot定时任务
	public void run() throws IOException{
		try {
			//获取xml中配置的泛化类名称
			List<SystemName> systemList = configDataServiceImpl.listSystem();
			for (SystemName systemName : systemList){
				receiveUdp(systemName.getClassname());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 接收udp消息
	 * @param config
	 * @throws IOException
	 */
	public static void receiveUdp(String config) throws IOException{
		try{
			DatagramEchoService udpThread = new DatagramEchoService(ECHO_PORT,ECHO_BUFFER,queue);
			udpThread.getUDP();
		}catch(IOException ie){
			log.warn("Syslog服务器无法正常UDP响应服务: " + ie);
			System.exit(0);
		}
		ScanQueue scanQueue = new ScanQueue(queue,config);
		scanQueue.consume();
		log.warn("Syslog服务器开始监听,端口为:"+ ECHO_PORT);
	}
}
