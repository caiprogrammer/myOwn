package com.itmayiedu.service.syslog;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.itmayiedu.common.kafka.Consumer;
import com.itmayiedu.entity.BaseMessage;

/**
 * UDP接收服务类
 * @author pengdan
 *
 */

@Repository
public class DatagramEchoService {
	
	private static Logger log = Logger.getLogger(DatagramEchoService.class);

	private static int count = 0;
	private DatagramSocket ds;
	private static BlockingQueue<BaseMessage> queue = null; // 定义多个数据队列，存储数据;
	
	public DatagramEchoService(int port,int buff,BlockingQueue<BaseMessage> queue) throws IOException{
		this.ds = new DatagramSocket(port);
		this.ds.setReceiveBufferSize(buff * 1024 * 1024);// buff M,系统默认的缓存大小是8192Byte（8K）
		this.queue = queue;
	}
	
	public DatagramEchoService(){
	}
	
	private static final int LEN = 65530;
	 
	
	
	/**
	 * 接收UDP服务
	 */
	@Async
	public void getUDP(){
		DatagramPacket dp;
		dp = new DatagramPacket(new byte[LEN],LEN);
		while(true){
			
			try {
				dp.setLength(LEN);
				ds.receive(dp);
				//形成消息对象
				BaseMessage message = new BaseMessage();
				message.setSrcAddress(dp.getAddress().getHostAddress());
				message.setSrcPort(dp.getPort());
				String msg = new String(dp.getData(),0,dp.getLength());
				message.setMessage(msg);
				message.setTime(new Date());
				queue.put(message);
			} catch (IOException e) {
				log.warn("UDP服务错误：" +e);
			} catch (InterruptedException e) {
				log.warn("UDP服务错误：" +e);
			}
			
		}
		
	}
	
	
	
}
