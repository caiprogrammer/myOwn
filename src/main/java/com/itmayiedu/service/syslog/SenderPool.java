package com.itmayiedu.service.syslog;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送器池
 * @author pengdan
 *
 */
public class SenderPool {

	private static SenderPool senderPool = new SenderPool();
	private Map<String,Sender> pool = new HashMap<String,Sender>();
	
	private SenderPool(){
		
	}
	
	public static SenderPool getInstance(){
		return senderPool;
	}
	
	public void putSender(String name,Sender s){
		pool.put(name, s);
	}
	
	public Sender getSender(String name){
		if(pool.containsKey(name)){
			return pool.get(name);
		}else{
			return null;
		}
	}
}
