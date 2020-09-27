package com.itmayiedu.common.utils;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;


/**   
 * IP地址操作工具类
 */
public class IpUtil {

	private static boolean enableDNS = true;
    private static Integer syncDNSName = new Integer(0);
    private static Integer syncIPAdrs = new Integer(0);
    
    public static boolean isDNSEnabled() {
    	return enableDNS;
    }
    
    public static void setDNSEnabled(boolean flag) {
    	enableDNS = flag;
    }
    /**   
     * 得到指定字符型IP默认的掩码，此函数适用范围受限
     * @param s String IP地址字符串
     * @return String 返回IP地址掩码
     */
    public static String getDefaultNetMask(String s) {
		if (s == null || s.indexOf(".") == -1)
		    return null;
		int[] ai = getAddrArray(s);
		if (ai == null && ai[0] < 128)
		    return "255.0.0.0";
		if (ai[0] < 192)
		    return "255.255.0.0";
		return "255.255.255.0";
    }
    /**   
     * 得到子网long值IP地址
     * @param s String IP地址
     * @param s1 String IP地址掩码
     * @return long 返回子网long值IP地址
     */   
    public static long getNumIPs(String s, String s1) {
		long l = getAddrLong("255.255.255.255");
		long l1 = getAddrLong(s1);
		if (l1 == l)
		    return 0L;
		long l2 = getAddrLong(s);
		long l3 = l ^ l1;
		if (l1 == 0L || l2 == 0L || l1 > l || l3 > l)
		    return 0L;
		return l3;
    }
    
    /**   
     * 得到子网String[]形式IP地址
     * @param s String IP地址
     * @param s1 String IP地址掩码
     * @return String[] 返回子网String[]形式IP地址
     */   
    public static String[] getIPList(String s, String s1) {
		String[] as = null;
		long l = getAddrLong("255.255.255.255");
		long l1 = getAddrLong(s1);
		if (l1 == l) {
		    as = new String[1];
		    as[0] = s;
		    return as;
		}
		long l2 = getAddrLong(s);
		long l3 = l ^ l1;
		if (l1 == 0L || l2 == 0L || l1 > l || l3 > l)
		    return null;
		if (l3 > 65536L)
		    l3 = 65025L;
		if (l3 == 2L) {
		    as = new String[1];
		    as[0] = convertAddr(l2 + 1L);
		    return as;
		}
		if (l2 + l3 - 1L > l)
		    return null;
		as = new String[(int) l3 - 2];
		for (int i = 0; (long) i < l3 - 2L; i++)
		    as[i] = convertAddr(l2 + 1L + (long) i);
		return as;
    }
    
    /**   
     * 得到子网String形式IP地址
     * @param s String IP地址
     * @param s1 String IP地址掩码
     * @return String 返回子网String形式IP地址
     */   
    public static String getNetAddr(String s, String s1) {
		if (s != null && s1 != null) {
		    if (s.equals(s1))
			s1 = "255.255.255.0";
		    if (s1.equals("255.255.255.255"))
			s1 = "255.255.255.0";
		    if (s1.equals("0.0.0.0"))
			s1 = "255.255.255.0";
		}
		long l = getAddrLong(s1);
		long l1 = getAddrLong(s);
		long l2 = l1 & l;
		return convertAddr(l2);
    }
    /**   
     * 转换long型IP地址值为String形式IP地址值，比如：3232241665 －》192.168.24.1
     * @param l long 整型IP地址
     * @return String 返回String形式IP地址值
     */      
    public static String convertAddr(long l) {
		int[] ai = new int[4];
		for (int i = 0; i < 4; i++)
		    ai[i] = (int) (l >> 8 * (3 - i) & 0xffL);
		return new String(ai[0] + "." + ai[1] + "." + ai[2] + "." + ai[3]);
    }
    /**   
     * 转换long型IP地址值为String形式IP地址值，需要补齐0，比如：3232241665 －》192.168.024.001
     * @param l long 整型IP地址
     * @return String 返回String形式IP地址值
     */       
    public static String convertFullAddr(long l) {
		int[] ai = new int[4];
		for (int i = 0; i < 4; i++)
		    ai[i] = (int) (l >> 8 * (3 - i) & 0xffL);
		return new String(FormatIPNum(ai[0],3) + "." + FormatIPNum(ai[1],3) + "." + FormatIPNum(ai[2],3) + "." + FormatIPNum(ai[3],3));
    }
    /**   
     * 将String型IP地址值补齐，比如：3232241665 －》192.168.024.001
     * @param strip String IP地址
     * @return String 返回String形式IP地址值
     */  
    public static String convertFullAddr(String strip) {
		if(isIPAddress(strip)){
			long ip = getAddrLong(strip);
			return convertFullAddr(ip);
		}
		return strip;
    }
    /**   
     * 格式化数字为字符串,格式化成统一长度的字符串，不足长度的前面补0，比如：FormatIPNum（1,3）表示 1－》001
     * @param ipnum int 数字
     * @param n int 字符串长度
     * @return String 返回String形式值
     */  
   public static String FormatIPNum(int ipnum,int n){
    	String zero = "";
    	for(int i=0; i<n; i++){
    		zero += "0";
    	}
    	String ip = zero + String.valueOf(ipnum);
    	ip = ip.substring(ip.length()-n,ip.length());
    	return ip;
    }
   /**   
    * 反向转换long型IP地址为字符串
    * @param l long 数字
    * @return String 返回String形式值
    */     
    public static String ReConvertAddr(long l) {
    	int[] ai = new int[4];
    	for (int i = 0; i < 4; i++)
    	    ai[i] = (int) (l >> 8 * (3 - i) & 0xffL);
    	return new String(ai[3] + "." + ai[2] + "." + ai[1] + "." + ai[0]);
    }   
    
    /**   
     * 转换String型IP地址为long型，比如：192.168.24.1 －》 3232241665
     * @param s String IP地址
     * @return long 返回long形式值
     */     
   public static long getAddrLong(String s) {
		int[] ai = getAddrArray(s);
		if (ai == null)
		    return 0L;
		long l = 0L;
		for (int i = 0; i < 4; i++)
		    l |= (long) ai[i] << 8 * (3 - i);
		return l;
    }
    
   /**   
    * 转换String型IP地址为byte[]型,按“.”分割字符串，然后转换为byte
    * @param s String IP地址
    * @return byte[] 返回byte[]形式值
    */     
    public static byte[] getAddrBytes(String s) {
		if (s == null)
		    return null;
		StringTokenizer stringtokenizer = new StringTokenizer(s, ".");
		if (stringtokenizer.countTokens() != 4)
		    return null;
		byte[] ai = new byte[4];
		try {
		    for (int i = 0; i < 4; i++)
			ai[i] = Byte.parseByte(stringtokenizer.nextToken());
		} catch (NumberFormatException numberformatexception) {
		    return null;
		}
		for (int j = 0; j < 4; j++) {
		    if (ai[j] < 0 || ai[j] > 255)
			return null;
		}
		return ai;
    }
    
    /**   
     * 转换String型IP地址为int[]型,按“.”分割字符串，然后转换为int
     * @param s String IP地址
     * @return byte[] 返回int[]形式值
     */     
   public static int[] getAddrArray(String s) {
		if (s == null)
		    return null;
		StringTokenizer stringtokenizer = new StringTokenizer(s, ".");
		if (stringtokenizer.countTokens() != 4)
		    return null;
		int[] ai = new int[4];
		try {
		    for (int i = 0; i < 4; i++)
			ai[i] = Integer.parseInt(stringtokenizer.nextToken());
		} catch (NumberFormatException numberformatexception) {
		    return null;
		}
		for (int j = 0; j < 4; j++) {
		    if (ai[j] < 0 || ai[j] > 255)
			return null;
		}
		return ai;
    }
    
   /**   
    * 根据名称获得域名
    * @param s String 名称
    * @return String 返回域名
    */     
    public static String getDNSName(String s) {
		if (!enableDNS)
		    return s;
		String s1;
		try {
		    if (System.getProperty("os.name").startsWith("Linux")) {
			synchronized (syncDNSName) {
			    InetAddress inetaddress1 = InetAddress.getByName(s);
			    s1 = inetaddress1.getHostName().trim();
			}
		    } else {
			InetAddress inetaddress = InetAddress.getByName(s);
			s1 = inetaddress.getHostName().trim();
		    }
		} catch (UnknownHostException unknownhostexception) {
		    s1 = s;
		}
		return s1.toLowerCase();
    }
    
    /**   
     * 根据名称获得IP地址
     * @param s String 名称
     * @return String 返回IP地址
     */     
    public static String getIP(String s) {
		String s1;
		try {
		    if (System.getProperty("os.name").startsWith("Linux")) {
			synchronized (syncIPAdrs) {
			    InetAddress inetaddress1 = InetAddress.getByName(s);
			    s1 = inetaddress1.getHostAddress().trim();
			}
		    } else {
			InetAddress inetaddress = InetAddress.getByName(s);
			s1 = inetaddress.getHostAddress().trim();
		    }
		} catch (UnknownHostException unknownhostexception) {
		    s1 = s;
		}
		return s1;
    }
    
    /**   
     * 判断IP地址是否在某个子网内
     * @param s String IP地址
     * @param s1 String IP掩码
     * @param s2 String 需要确定是否在子网内的IP
     * @return boolean 返回true或false
     */     
   public static boolean inNet(String s, String s1, String s2) {
		long l = getAddrLong("255.255.255.255");
		long l1 = getAddrLong(s2);
		long l2 = getAddrLong(s1);
		l2 &= l1;
		long l3 = l ^ l1;
		long l4 = getAddrLong(s);
		if (l4 == 0L)
		    return false;
		return l4 < l2 + l3 && l4 >= l2;
    }
    
    public static String getMinMaxAddr(Vector vector, boolean flag) {
		String s = (String) vector.firstElement();
		long l = getAddrLong(s);
		for (int i = 1; i < vector.size(); i++) {
		    String s1 = (String) vector.elementAt(i);
		    long l1 = getAddrLong(s1);
		    if (!flag) {
			if (l1 < l) {
			    s = s1;
			    l = l1;
			}
		    } else if (l1 > l) {
			s = s1;
			l = l1;
		    }
		}
		return s;
    }
    
    public static boolean isAddressInRange(String s, Vector vector, Vector vector1) {
		if (vector == null && vector1 == null)
		    return true;
		long l = getAddrLong(s);
		if (l == 0L)
		    return false;
		for (int i = 0; i < vector.size(); i++) {
		    long l1 = getAddrLong((String) vector.elementAt(i));
		    long l2 = getAddrLong((String) vector1.elementAt(i));
		    if (l <= l2 && l >= l1)
			return true;
		}
		return false;
    }   
    
    /**   
     * 判断字符串是否是IP地址格式
     * @param s String IP地址
     * @return boolean 返回true或false
     */     
	public static boolean isIPAddress(String s) {
		StringTokenizer stringtokenizer = new StringTokenizer(s, ".");
		if (stringtokenizer.countTokens() != 4)
		    return false;
		int[] ai = new int[4];
		try {
		    for (int i = 0; i < 4; i++)
			ai[i] = Integer.parseInt(stringtokenizer.nextToken());
		} catch (NumberFormatException numberformatexception) {
		    return false;
		}
		for (int j = 0; j < 4; j++) {
		    if (ai[j] < 0 || ai[j] > 255)
			return false;
		}
		return true;
   }
    /**   
     * 根据具体IP获得子网IP
     * @param s String IP地址
     * @return String 返回子网IP
     */     
	public static String getNetWorkByIP(String s) {
		String network = "";
		StringTokenizer stringtokenizer = new StringTokenizer(s, ".");
		if (stringtokenizer.countTokens() != 4)
		    return network;
		int[] ai = new int[4];
		try {
		    for (int i = 0; i < 4; i++)
			ai[i] = Integer.parseInt(stringtokenizer.nextToken());
		} catch (NumberFormatException numberformatexception) {
		    return network;
		}
		for (int j = 0; j < 4; j++) {
		    if (ai[j] < 0 || ai[j] > 255)
			return network;
		}
		network = ai[0]+"."+ ai[1]+"."+ ai[2]+".0"; 
		return network;
   }
	
	/**
	 * 从给定的字符串中取出IP地址
	 * @param str 包含IP地址的字符串
	 * @return IP地址
	 */
	public static String getIpAddress(String str){
		String regex ="(\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if (m.find()) {
        	return m.group(1);
        }
        return null;
	}
	
	/**
	 * 根据IP地址判断Ipv4或Ipv6
	 * 
	 * @param ipAdress ipv4或ipv6字符串
	 * @return ipv4：ipv4, ipv6:ipv6, null:地址不对
	 * @throws Exception
	 */
	public static String isIpv4OrIpv6(String ipAdress) throws Exception {
		InetAddress address = InetAddress.getByName(ipAdress);
		if (StringUtils.isEmpty(ipAdress)) {
			if (address instanceof Inet4Address) {
				return "ipv4";
			} else if (address instanceof Inet6Address) {
				return "ipv6";
			}
		}
		return "";
	}
	
	/**
	 * ipv4或ipv6地址String 转 BigInteger类型
	 * 
	 * @param ip ip地址（ipv4、ipv6）
	 * @return 返回long类型
	 * @throws UnknownHostException 
	 */
	public static BigInteger ipStr2IpBigInteger(String ip) throws UnknownHostException {
		//如果ip为null或者为""时，直接返回ZERO
		if(StringUtils.isEmpty(ip)) {
			return BigInteger.ZERO;
		}
		ip = ip.trim().replace(" ", "");
		InetAddress address = InetAddress.getByName(ip);
		
		byte[] bytes = address.getAddress();
		BigInteger bigInt = new BigInteger(1, bytes);
		return bigInt;
	}

	public static void main(String[] args) {
		String ip = " ";
		if(StringUtils.isEmpty(ip)) {
			System.out.println("error");
			System.exit(0);
		}
		String trim = ip.replace(" ", "");
		System.out.println("--->trim:"+trim);
	}
	
}
