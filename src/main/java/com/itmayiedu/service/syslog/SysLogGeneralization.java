package com.itmayiedu.service.syslog;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.itmayiedu.common.utils.DateUtil;
import com.itmayiedu.common.utils.IpUtil;
import com.itmayiedu.common.utils.JackJson;
import com.itmayiedu.entity.BaseMessage;
import com.itmayiedu.entity.EventMessage;

/**
 * UDP服务日志处理泛化类
 * @author pengdan
 *
 */
public class SysLogGeneralization implements Generalization{

	private static Logger log = Logger.getLogger(Generalization.class);
	
	public static String tag = "“vensu4Atag";//用于替换日志信息中的英文双引号
	private static VenusAudit2Adapter venusAudit2Adapter = VenusAudit2Adapter.getInstance();

	@Override
	public String[] consume(BaseMessage baseMessage, EventMessage eventMessage) {
		log.info("原始日志为"+baseMessage.getMessage());
		
		String message = baseMessage.getMessage();
		String tempMessage = null;
		String firstSegment = null;
		String lastSegment = null;
		StringBuffer strBuffMessage = null;
		int index;
		
		Map<String,String> map_month = new HashMap<String, String>();
    	map_month.put("Jan", "01");
    	map_month.put("Feb", "02");
    	map_month.put("Mar", "03");
    	map_month.put("Apr", "04");
    	map_month.put("May", "05");
    	map_month.put("Jun", "06");
    	map_month.put("Jul", "07");
    	map_month.put("Aug", "08");
    	map_month.put("Sep", "09");
    	map_month.put("Sept", "09");
    	map_month.put("Oct", "10");
    	map_month.put("Nov", "11");
    	map_month.put("Dec", "12");
		
    	if(message != null && !"".equals(message)){
    		message = message.trim();
    		message = StringUtils.replace(message, "\r\n", "");
    		message = StringUtils.replace(message, "\r", "");//回车
			message = StringUtils.replace(message, "\n", "");//回车换行
    		//设置事件原始信息
			eventMessage.setRaw_data(message);
			
			//日志格式如下：
			//value:category=ADLG,type=3,time=2020-09-11 15:53:46,srcip=2220932002,
			//srcipstr=132.96.187.162,destip=2220923021,destipstr=132.96.152.141,srcport=60517,
			//destport=22,protocol=SSH,starttime=2020-09-11 15:53:23,endtime=,mastername=cj_pengdan,
			//slavename=venus,operation=[venus@venusca009 ~]$ ls,content=[venus@venusca009 ~]$ ls,devip=0,
    		//devipstr=132.96.152.241,devport=47030,businessname=null,businessid=0,authenstatus=1,auditlevel=1,
    		//param_len=null,param=null,id=202009111510030000000354,resname=venus009,resid=0,restype=0,identifierid=null,
			//spsensitiveObj=null,eventTableName=VENUS_AUDIT_AUDITEVENT_RT,recordtime=,isSession=false,fromprog=false,reason=,
			//url=null,module=null,meun=null,function=null,raw_data=null
			
			
			
			//<86>VENUS_AUDIT: type=sign time=2007-09-23 10:46:51 engine_ip=192.168.100.121
			// src_ip=192.168.100.40 dst_ip=222.77.187.172 src_port=2755 dst_port=80 src_mac=00:1A:6B:38:2B:C3
			// dst_mac=00:1B:2B:7E:0C:C6 trans_proto_name=TCP rule_name=URL包含 proto_name=HTTP
			// start_time=0 end_time=0 user_name=全网 user_type=1 policy_name=HTTP例子 role_name=http
			// evt_set_name=HTTP例子 action_mode=1 alarm_mode=1 log_level=1
			// param_len=70 param=访问网页，URL=http://www.greendown.cn/ggao/index_top.htm;访问模式=get;
			if(message.startsWith("<86>VENUS_AUDIT:")){
				tempMessage = message.replace("<86>VENUS_AUDIT:", "class=VENUS_AUDIT");
				tempMessage = tempMessage.replace("engine_ip", "dev_ip");
				
				tempMessage = tempMessage.replace("user_name", "secondary_user");
				tempMessage = tempMessage.replace("role_name", "role");
				strBuffMessage = new StringBuffer(tempMessage);
				//处理时间段部分
				strBuffMessage.deleteCharAt(strBuffMessage.indexOf("time=") + 15);// 暂时删除类似"2007-09-23 10:46:51"时间的中间空格
				// 处理参数param之前的整体部分
				index = strBuffMessage.indexOf("param=");
				firstSegment = strBuffMessage.substring(0, index);// 参数之前的部分
				firstSegment = firstSegment.replaceAll("=", "=\""); // 在等号右边加一个引号"
				firstSegment = firstSegment.replaceAll(" ", "\" "); // 在相关属性值右边加一个引号",
																	// 形成值对关系
				strBuffMessage.insert(index + 6, "\"");// 6表示字符串"param="的长度,在等号之后插入引号"
				strBuffMessage.append("\"");// 在最后增加一个引号
				strBuffMessage.delete(0, index);
				strBuffMessage.insert(0, firstSegment);
				strBuffMessage.insert(strBuffMessage.indexOf("time=") + 16, " ");// 补上类似"2007-09-23 10:46:51"时间的中间空格
			
				Map map = new ConcurrentHashMap();
				splitMessage(map, strBuffMessage.toString().trim());
				// 形成审计事件对象
				getEventMessage(map,eventMessage);
				//从帐号不为空，则将其字母转换为大写。解决天玥II采集的从帐号大小写不一致引起无法匹配程序帐号事件绕行事件问题。
				if(eventMessage.getSlavename()!=null){
					eventMessage.setSlavename(eventMessage.getSlavename().toUpperCase());
				}
				
				eventMessage.setType(venusAudit2Adapter.getEventType(DateUtil.toString(map.get("protocol"))));
				//log.info("type is:"+eventMessage.getType());
				eventMessage.setAuditlevel(venusAudit2Adapter.getLevel(DateUtil.toString(map.get("log_level"))));
				//处理操作名称|审计操作    和   操作对象，具体敏感对象名称|操作对象
				Map operation = venusAudit2Adapter.getOperation(eventMessage);
				eventMessage.setOperation(String.valueOf(operation.get("Operation")));
				eventMessage.setSpsensitiveObj(String.valueOf(operation.get("SpsensitiveObj")));
				
				//设置事件存储表属性
				setEventTableName(eventMessage);
				
				baseMessage.setParsedMessage(strBuffMessage.toString());
				//log.info(eventMessage.getId());
				
				return new String[]{Sender.MQ,"AUDIT_II"};
			}else if (message.indexOf("adlg_type") >= 0) {
				log.info("进入vbh/osm日志");
				
				Map map = new ConcurrentHashMap();
				//splitMessage(map, message.trim());
				map=JackJson.getMapByJsonString(message);
				getVbhEventMessage(map, eventMessage);
				eventMessage.setDevipstr(baseMessage.getSrcAddress());
				eventMessage.setDevport(baseMessage.getSrcPort());
				setEventTableName(eventMessage);
				baseMessage.setParsedMessage(message);
				return new String[]{Sender.MQ,"AUDIT_VBH"};
			}else{
				//2008-11-09 add 如果是业务系统（在资源列表内的）和4A系统发过来的事件
				Map map = new ConcurrentHashMap();
				splitMessage(map, message.trim());
				// 形成审计事件对象
				getEventMessage(map,eventMessage);
				//设置类型和级别
				setTypeAndLevel(eventMessage,map);
				
				if(eventMessage.getCategory()!=null && !"".equals(eventMessage.getCategory())
						&& eventMessage.getTime()!=null){
					//设置事件存储表属性
					setEventTableName(eventMessage);

					baseMessage.setParsedMessage(message);
					return new String[]{Sender.MQ,Producer.SPECIAL.toString()};
				}else{
					return new String[]{Sender.FILE,null};
				}
			}
			
    	}
		return null;
	}
	
	/**
	 * 分解Syslog数据，存入Map中，Map的Key为属性名，Value为属性值
	 * 
	 * @param map
	 *            Map 存储解析完的数据
	 * @param message
	 *            String 需要解析的字符串
	 */
	private void splitMessage(Map map, String message) {
		// 标准字符串
		/*class="VENUS_AUDIT_IV" type="3" time="2007-08-28 00:51:10" src_ip="192.168.100.40" dst_ip="192.168.100.41" 
		 *src_port="2203" dst_port="1521" src_mac="00:15:C5:79:7E:F7" dst_mac="00:E0:FC:57:E2:13" protocol="TELNET" 
		 *start_time="2007-08-28 00:51:10" end_time="2007-08-28 00:58:10" primary_user="zhangsan" secondary_user="user1" 
		 *operation="insert" content="插入数据" dev_ip="192.168.100.152" dev_port="2222" dev_mac="0F:00:FC:57:E2:13" 
		 *authen_status="" log_level="1" session_id="10000" param_len="29" param="insert into table1 value(1);"		 
		 */
        message = message.trim()+" ";
		String regex = "(\\w+)=\"(.*?)\"\\s+";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(message);
		while (m.find()) {
			String key = m.group(1);
			String value = m.group(2);
			if(value == null) {
				value = "";
			}
			map.put(key, value.replaceAll(tag, "\""));
		}
	}

	/**
	 * 根据Map的值生成AuditEvent对象
	 * 
	 * @param map
	 *            Map 存储解析完的数据
	 * @return AuditEvent 审计事件对象
	 */
	private synchronized EventMessage getEventMessage(Map map,EventMessage eventMessage) {
		eventMessage.setCategory(DateUtil.toString(map.get("class")));
		eventMessage.setTime(DateUtil.strToDate(DateUtil.toString(map.get("time"))));
		eventMessage.setSrcipstr(DateUtil.toString(map.get("src_ip")));
		String srcIpStr = eventMessage.getSrcipstr();
		try {
			//添加ipv6支持
			String srcipFlag = IpUtil.isIpv4OrIpv6(srcIpStr);
			BigInteger srcipNum = IpUtil.ipStr2IpBigInteger(srcipFlag);
			if("ipv6".equalsIgnoreCase(srcipFlag)) {
				eventMessage.setSrcip(srcipNum);
			}else if("ipv4".equalsIgnoreCase(srcipFlag)) {
				eventMessage.setSrcipv6(srcipNum);
			}
		} catch (Exception e) {
			log.error("泛化处理器中,ip转换异常", e);
		}
		eventMessage.setDestipstr(DateUtil.toString(map.get("dst_ip")));
		String destipStr = eventMessage.getDestipstr();
		try {
			//添加ipv6支持
			String destFlag = IpUtil.isIpv4OrIpv6(destipStr);
			BigInteger destipNum = IpUtil.ipStr2IpBigInteger(destipStr);
			if("ipv6".equalsIgnoreCase(destFlag)) {
				eventMessage.setDestip(destipNum);
			}else if("ipv4".equalsIgnoreCase(destFlag)) {
				eventMessage.setDestipv6(destipNum);
			}
		} catch (Exception e) {
			log.error("泛化处理器中,目标ip转换异常", e);
		}
		eventMessage.setSrcport(DateUtil.toInt(map.get("src_port")));
		eventMessage.setDestport(DateUtil.toInt(map.get("dst_port")));
		if(DateUtil.strToDate(DateUtil.toString(map.get("start_time"))) == null){
			eventMessage.setStarttime(eventMessage.getTime());
		}
		else{
			eventMessage.setStarttime(DateUtil.strToDate(DateUtil.toString(map.get("start_time"))));
		}
		eventMessage.setEndtime(DateUtil.strToDate(DateUtil.toString(map.get("end_time"))));
		eventMessage.setMastername(DateUtil.toString(map.get("primary_user")));
		eventMessage.setSlavename(DateUtil.toString(map.get("secondary_user")));
		eventMessage.setOperation(DateUtil.toString(map.get("operation")));
		eventMessage.setContent(DateUtil.toString(map.get("content")));
		eventMessage.setProtocol(DateUtil.toString(map.get("protocol")));
		eventMessage.setDevipstr(DateUtil.toString(map.get("dev_ip")));
		String devipStr = eventMessage.getDevipstr();
		try {
			String devFlag = IpUtil.isIpv4OrIpv6(devipStr);
			BigInteger devipNum = IpUtil.ipStr2IpBigInteger(devipStr);
			if("ipv4".equalsIgnoreCase(devFlag)) {
				eventMessage.setDevip(devipNum);
			}else if("ipv6".equals(devFlag)) {
				eventMessage.setDevipv6(devipNum);
			}
		} catch (Exception e) {
			log.error("泛化处理器中,设备ip转换异常", e);
		}
		eventMessage.setDevport(DateUtil.toInt(map.get("dev_port")));
		eventMessage.setBusinessname(DateUtil.toString(map.get("bu_name")));
		eventMessage.setBusinessid(DateUtil.toInt(map.get("bu_type")));
		String authenstatus = "Success".equals(DateUtil.toString(map.get("authen_status")))?"1":"2";
		eventMessage.setAuthenstatus(DateUtil.toInt(authenstatus));
		eventMessage.setParam_len(DateUtil.toString(map.get("param_len")));
		eventMessage.setParam(DateUtil.toString(map.get("param")));
		eventMessage.setIdentifierid(DateUtil.toString(map.get("session_id")));
		eventMessage.setSpsensitiveObj(DateUtil.toString(map.get("spsensitiveObj")));
		eventMessage.setIsSession(false);//初始化为false，用于处理告警之后判断是链接审计事件表还是认证事件表
		String fromprog = DateUtil.toString(map.get("fromprog"));
		if(fromprog!=null && fromprog.equalsIgnoreCase("true")){
			eventMessage.setFromprog(true);
		}else{
			eventMessage.setFromprog(false);
		}
		
		if ("".equals(eventMessage.getContent())) {
			eventMessage.setContent(eventMessage.getParam());
		}
		if ("".equals(eventMessage.getParam_len())) {
			eventMessage.setParam_len(eventMessage.getParam().length()+"");
		}
		if((eventMessage.getProtocol().equalsIgnoreCase("IP-MAC")) && eventMessage.getContent().equals("")){
			eventMessage.setContent("IP-MAC");
		}
		if(map.get("daemon")!=null){//天玥II 6082版本事件
			eventMessage.setProtocol(DateUtil.toString(map.get("daemon")));
		}
			
		eventMessage.setId(GlobalIdBuild.getId());
		return eventMessage;
	}
	
	/**
	 * 设置事件存储表属性
	 * 比较事件发生时间是否小于当前时间（精确到天
	 */
	private void setEventTableName(EventMessage eventMessage){
		boolean compareFlag = DateUtil.compareDate(eventMessage.getTime(),new Date());
		if (eventMessage.getType() == 2) {//认证事件
			if(eventMessage.getFromprog()){//第三方程序访问堡垒机事件
				eventMessage.setEventTableName(IPersistenceService.SESSION_PROG_TABLE_NAME);
			}else{
				if(compareFlag){
					eventMessage.setEventTableName(IPersistenceService.SESSION_HIST_TABLE_NAME);
				}else{
					eventMessage.setEventTableName(IPersistenceService.SESSION_TABLE_NAME);
				}
			}
			
			eventMessage.setIsSession(true);
		}else {//非认证事件
			if(eventMessage.getFromprog()){//第三方程序访问堡垒机事件
				eventMessage.setEventTableName(IPersistenceService.AUDIT_PROG_TABLE_NAME);
			}else{
				if(compareFlag){
					eventMessage.setEventTableName(IPersistenceService.AUDIT_HIST_TABLE_NAME);
				}else{
					eventMessage.setEventTableName(IPersistenceService.AUDIT_TABLE_NAME);
				}
				
			}
			eventMessage.setIsSession(false);
		}
	}
	
	/**
	 * vbh eventmessage
	 * 
	 * @param map
	 *            Map 存储解析完的数据
	 * @return AuditEvent 审计事件对象
	 */
	private synchronized void getVbhEventMessage(Map map,EventMessage eventMessage) {
		eventMessage.setCategory("ADLG");
		eventMessage.setSession_guid(DateUtil.toString(map.get("session_guid")));
		eventMessage.setAccess_guid(DateUtil.toString(map.get("access_guid")));
		eventMessage.setOperation_guid(DateUtil.toString(map.get("operation_guid")));
		eventMessage.setSlavename(DateUtil.toString(map.get("account")));
		// adlg_type
		eventMessage.setType(DateUtil.toInt(map.get("adlg_type")));
		
		eventMessage.setEndtime(DateUtil.strToDate(DateUtil.toString(map.get("end_time"))));
		eventMessage.setTime(DateUtil.strToDate(DateUtil.toString(map.get("message_time"))));
		if(DateUtil.strToDate(DateUtil.toString(map.get("begin_time"))) == null){
			eventMessage.setStarttime(DateUtil.strToDate(DateUtil.toString(map.get("message_time"))));
		}
		else{
			eventMessage.setStarttime(DateUtil.strToDate(DateUtil.toString(map.get("begin_time"))));
		}
		eventMessage.setProtocol(DateUtil.toString(map.get("protocol_name")));
		eventMessage.setResname(DateUtil.toString(map.get("resource_name")));
		eventMessage.setRestype_str(DateUtil.toString(map.get("resource_system")));
		eventMessage.setDestipstr(DateUtil.toString(map.get("service_ip")));
		String destipStr = eventMessage.getDestipstr();
		try {
			//添加ipv6支持
			String destFlag = IpUtil.isIpv4OrIpv6(destipStr);
			BigInteger destipNum = IpUtil.ipStr2IpBigInteger(destipStr);
			if("ipv6".equalsIgnoreCase(destFlag)) {
				eventMessage.setDestip(destipNum);
			}else if("ipv4".equalsIgnoreCase(destFlag)) {
				eventMessage.setDestipv6(destipNum);
			}
		} catch (Exception e) {
			log.error("泛化处理器中,目标ip转换异常", e);
		}
		eventMessage.setDestport(DateUtil.toInt(map.get("service_port")));
		eventMessage.setSrcipstr(DateUtil.toString(map.get("user_ip")));
		String srcIpStr = eventMessage.getSrcipstr();
		try {
			//添加ipv6支持
			String srcipFlag = IpUtil.isIpv4OrIpv6(srcIpStr);
			BigInteger srcipNum = IpUtil.ipStr2IpBigInteger(srcipFlag);
			if("ipv6".equalsIgnoreCase(srcipFlag)) {
				eventMessage.setSrcip(srcipNum);
			}else if("ipv4".equalsIgnoreCase(srcipFlag)) {
				eventMessage.setSrcipv6(srcipNum);
			}
		} catch (Exception e) {
			log.error("泛化处理器中,ip转换异常", e);
		}
		eventMessage.setSrcport(DateUtil.toInt(map.get("user_port")));
		eventMessage.setMastername(DateUtil.toString(map.get("user_name")));
		eventMessage.setDirection(DateUtil.toString(map.get("direction")));
		eventMessage.setOperation(DateUtil.toString(map.get("operation")));
		eventMessage.setReviewer(DateUtil.toString(map.get("reviewer")));
		eventMessage.setReason(DateUtil.toString(map.get("reason")));
		eventMessage.setAction(DateUtil.toString(map.get("action")));
		eventMessage.setOperation_time(DateUtil.strToDate(DateUtil.toString(map.get("operation_time"))));
		eventMessage.setAuditlevel(DateUtil.toInt(map.get("log_level"), 1));
		log.info("eventMessage的log_level值===="+map.get("log_level"));
		eventMessage.setId(GlobalIdBuild.getId());
		String fromprog = DateUtil.toString(map.get("fromprog"));
		if(fromprog!=null && fromprog.equalsIgnoreCase("true")){
			eventMessage.setFromprog(true);
		}else{
			eventMessage.setFromprog(false);
		}
	}
	
	/**
	 * 设置类型和级别 
	 */
	private void setTypeAndLevel(EventMessage eventMessage,Map map){
		eventMessage.setType(DateUtil.toInt(map.get("type"), 10));
		eventMessage.setAuditlevel(DateUtil.toInt(map.get("log_level"), 1));
	}
}
