package com.itmayiedu.service.syslog;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.itmayiedu.common.utils.PropertyManager;


/**
 *@author 杨志泉<br>
 *描述：生成24位长度的事件ID的类，ID的格式为14位当前时间"+"10位自动增长数字",满足每秒100亿-1个ID<br>
 *创建时间:2008 九月 27 10:29:02<br>
 *修改者：<br>
 *修改日期：<br>
 *修改描述：<br>
 */
public class GlobalIdBuild {
	private final static Object lock=new Object();
	
//	private final static long MAX=9999999999l;
	private final static PropertyManager systemManager = new PropertyManager("/conf/eventserver/system.properties");
	private final static long MAX = systemManager.getProperty("globalId_max", 9999999999l);
	private final static String length_10="0000000000";
	private final static String length_14="00000000000000";
	private static long scope=systemManager.getProperty("globalId_scope", 1l);
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		for(int i=0;i<10000000;i++){
			System.out.println(getId());
//			Thread.sleep(1000);
		}
	}
	/**
	 * 获得23位ID
	 */
	public static String getId(){
//		return "123456";
		return getDate()+getInt();
	}
	/**
	 * 获得14位日期
	 * @return
	 */
	private static String getDate(){
		Date date=new Date();
		SimpleDateFormat dd=new SimpleDateFormat("yyyyMMddHHmmss"); 
		return dd.format(date);
	}
	/**
	 * 获得14位时间,到5138年11月16日不再可用
	 */
	private static String getTime(){
		long l=System.currentTimeMillis();
		StringBuffer sf=new StringBuffer(l+"");
		if(sf.length()<14){
			sf.insert(0, length_14.substring(0,14-sf.length()));
		}
		return sf.toString();
	}
	/**
	 * 获得9位的连续增长的数字,满足每秒10亿-1条事件
	 * @return
	 */
	private static String getInt() {
		synchronized (lock) {
			if (++scope > MAX) {
				scope = systemManager.getProperty("globalId_scope", 1l);
			}

			StringBuffer sf = new StringBuffer(scope + "");
			if (sf.length() < 10) {
				sf.insert(0, length_10.substring(0, 10 - sf.length()));
			}
			return sf.toString();
		}
	}
}

