package com.itmayiedu.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import com.itmayiedu.common.utils.DateUtil;





/**
 * 标准事件对象,包括所有属性
 * 需要根据应用系统名称、事件种类、事件类型的不同来入库
 */
public class EventMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7052066224036556545L;
	//编号(可临时用来存放读取数据库日志时主键字段转为String类型后的值)
	private String id;
	//事件产生时间
	private Date time;
	//事件产生原始时间 针对safeword的GMT时间 yangzhiquan add 20081119
	private String time_str;
	//事件类型，用于审计告警
	private int type;
	//审计级别
	private int auditlevel;
	//审计级别-String类型
	private String auditlevel_str;
	//主帐号名称|审计主账号
	private String mastername;
	//从帐号名称|审计从帐号
	private String slavename;
	//资源名称
	private String resname;
	//协议名称
	private String protocol;
	//源IP(字符型)|事件源ip地址
	private String srcipstr;
	//源IP(整型)|事件源ip地址
	private BigInteger srcip;
	//源IPV6(整型)|事件源ipv6地址
	private BigInteger srcipv6;
	//源端口
	private int srcport;
	//目的IP(字符型)|事件目的ip
	private String destipstr;
	//目的IP(整型)|事件目的ip
	private BigInteger destip;
	//目的IPV6(整型)|事件目的ipv6
	private BigInteger destipv6;
	//目的端口
	private int destport;
	//操作对象，具体敏感对象名称|操作对象
	private String spsensitiveObj; 
	//事件操作名称|审计操作
	private String operation;
	//时间操作路径
	private String operationPath;
	//事件内容描述|审计内容
	private String content;
	//事件服务器设备IP(字符型)
	private String devipstr;
	//事件服务器设备IP(整型)
	private BigInteger devip;
	//事件服务器设备IPV6(整型)
	private BigInteger devipv6;
	//事件服务器设备端口
	private int devport;
	//业务名称|业务系统名称（从配置文件中设定的参数读取）
	private String businessname;
	//业务系统编号
	private int businessid;
	//数据库写入时间
	private Date recordtime;
	//事件种类，天玥IV：VENUS_AUDIT_IV        天玥II: VENUS_AUDIT         SAFEWORD：SAFEWORD         SIMS：SOCEVENT
	private String category;
	//天玥II或天玥IV的会话ID
	private String identifierid;	
	//开始时间
	private Date starttime;
	//结束时间
	private Date endtime;
	//认证状态
	private int authenstatus;
	//认证状态 yangzhiquan add 20081119
	private String authenstatus_str;
	//参数长度
	private String param_len;
	//参数
	private String param;
	//审计原始信息
	private String raw_data;
	//资源编号
	private long resid;
	//资源类型
	private int restype;
	//资源类型 yangzhiquan add 20081119
	private String restype_str;
	//概化敏感对象名称  chenci add 20080908
	private String surveyObj;
	//事件入库的表名称
	private String eventTableName;
	//用于判断是审计事件还是认证事件
	private boolean isSession;
	//用于判断是否是第三方程序经堡垒访问目标机时，堡垒机发出的事件
	private boolean fromprog;
	/**vbh扩展部分***/
	//会话开始时间
	private Date begin_time;
	//会话编号
	private String session_guid;
	//唯一访问编号
	private String access_guid;
	//操作唯一编号
	private String operation_guid;
	//操作时间
	private Date operation_time;
	//数据传输方向1：用户->资源的命令（上行数据）2：资源->用户的回应（下行数据）。
	private String direction;
	//审计动作
	private String action;
	//审核人
	private String reviewer;
	
	//认证方法
	private String authmode;
	//请求url
	private String url;
	//请求模块
	private String module;
	//请求菜单
	private String menu;
	//请求功能
	private String function;
	//退出原因
	private String reason;
	//vhb/osm堡垒机日志类型,1-会话开始  2-操作日志  3-会话结束
	private int adlg_type;
	
	
	
	public boolean getFromprog() {
		return fromprog;
	}

	public void setFromprog(boolean fromprog) {
		this.fromprog = fromprog;
	}

	public boolean getIsSession() {
		return isSession;
	}

	public void setIsSession(boolean isSession) {
		this.isSession = isSession;
	}

	public String getSurveyObj() {
		return surveyObj;
	}

	public void setSurveyObj(String surveyObj) {
		this.surveyObj = surveyObj;
	}

	public String getAccess_guid() {
		return access_guid;
	}

	public String getOperation_guid() {
		return operation_guid;
	}

	public String getDirection() {
		return direction;
	}

	public String getAction() {
		return action;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setAccess_guid(String access_guid) {
		this.access_guid = access_guid;
	}

	public void setOperation_guid(String operation_guid) {
		this.operation_guid = operation_guid;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public EventMessage(){	}
	 

	public EventMessage(String category, int type, Date time, long src_ip, String srcipstr,
			BigInteger destip, BigInteger destipv6, String destipstr, int srcport, int destport,
			 String protocol, Date starttime,BigInteger srcip,String operationPath,
			Date endtime, String mastername, String slavename,
			String operation, String content, BigInteger devip, BigInteger devipv6, String devipstr,
			int devport,  String businessname, int businessid,
			int authenstatus, int auditlevel, String param_len,
			String param, String raw_data, String id, String resname, int resid,
			int restype, String identifierid, Date recordtime,String spsensitiveObj,String eventTableName,
			boolean isSession,boolean fromprog) {
		super();
		this.category = category;
		this.type = type;
		this.time = time;
		this.srcip = srcip;
		this.srcipstr = srcipstr;
		this.destip = destip;
		this.destipv6 = destipv6;
		this.destipstr = destipstr;
		this.srcport = srcport;
		this.destport = destport;
		this.protocol = protocol;
		this.starttime = starttime;
		this.endtime = endtime;
		this.mastername = mastername;
		this.slavename = slavename;
		this.operation = operation;
		this.operationPath = operationPath;
		this.content = content;
		this.devip = devip;
		this.devipv6 = devipv6;
		this.devipstr = devipstr;
		this.devport = devport;
		this.businessname = businessname;
		this.businessid = businessid;
		this.authenstatus = authenstatus;
		this.auditlevel = auditlevel;
		this.param_len = param_len;
		this.param = param;
		this.raw_data = raw_data;
		this.id = id;
		this.resname = resname;
		this.resid = resid;
		this.restype = restype;
		this.identifierid = identifierid;
		this.recordtime = recordtime;
		this.spsensitiveObj=spsensitiveObj;
		this.eventTableName=eventTableName;
		this.isSession = isSession;
		this.fromprog = fromprog;
	}
	
	public boolean equals(Object o){
		EventMessage auditEvent = (EventMessage)o;
		if (auditEvent.getId() == this.id) {
			return true;
		} 
		else {
			return false;
		}
	}

	public String toString() {
		String str = "";
		str += "category="+this.category+","
			 + 	"adlg_type="+this.adlg_type+","
			 + "type="+this.type + ","
			 + "time="+DateUtil.dateToAllCode(this.time) + ","
			 + "srcip="+this.srcip + ","
			 + "srcipstr="+this.srcipstr + ","
			 + "destip="+this.destip + ","
			 + "destipv6="+this.destipv6 + ","
			 + "destipstr="+this.destipstr + ","
			 + "srcport="+this.srcport + ","
			 + "destport="+this.destport + ","
			 + "protocol="+this.protocol + ","
			 + "starttime="+DateUtil.dateToAllCode(this.starttime) + ","
			 + "endtime="+DateUtil.dateToAllCode(this.endtime) + ","
			 + "mastername="+this.mastername + ","
			 + "slavename="+this.slavename + ","
			 + "operation="+this.operation + ","
			 + "operationPath="+this.operationPath + ","
			 + "content="+this.content + ","
			 + "devip="+this.devip + ","
			 + "devipv6="+this.devipv6 + ","
			 + "devipstr="+this.devipstr + ","
			 + "devport="+this.devport + ","
			 + "businessname="+this.businessname + ","
			 + "businessid="+this.businessid + ","
			 + "authenstatus="+this.authenstatus + ","
			 + "auditlevel="+this.auditlevel + ","
			 + "param_len="+this.param_len + ","
			 + "param="+this.param + ","
			 + "id="+this.id + ","
			 + "resname="+this.resname + ","
			 + "resid="+this.resid + ","
			 + "restype="+this.restype + ","
			 + "identifierid="+this.identifierid + ","
			 + "spsensitiveObj="+this.spsensitiveObj+","
			 + "eventTableName="+this.eventTableName+","
			 + "recordtime="+DateUtil.dateToAllCode(this.recordtime)+","
			 + "isSession="+this.isSession+","
			 + "fromprog="+this.fromprog+","
			 + "begin_time="+DateUtil.dateToAllCode(this.begin_time)+","
			 + "access_guid="+this.access_guid+","
			 + "direction="+this.direction+","
			 + "operation_guid="+this.operation_guid+","
			 + "operation_time="+DateUtil.dateToAllCode(this.operation_time)+","
			 + "action="+this.action+","
			 + "reviewer="+this.reviewer+","
			 + "reason="+this.reason+","
			 + "url="+this.url+","
			 + "module="+this.module+","
			 + "meun="+this.menu+","
			 + "function="+this.function+","
			 + "raw_data="+this.raw_data;
		return str;
	}

	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}


	public BigInteger getSrcip() {
		return srcip;
	}


	public void setSrcip(BigInteger srcip) {
		this.srcip = srcip;
	}
	
	public BigInteger getSrcipv6() {
		return srcipv6;
	}

	public void setSrcipv6(BigInteger srcipv6) {
		this.srcipv6 = srcipv6;
	}

	public String getSrcipstr() {
		return srcipstr;
	}


	public void setSrcipstr(String srcipstr) {
		this.srcipstr = srcipstr;
	}


	public BigInteger getDestip() {
		return destip;
	}

	public void setDestip(BigInteger destip) {
		this.destip = destip;
	}
	
	
	public BigInteger getDestipv6() {
		return destipv6;
	}

	public void setDestipv6(BigInteger destipv6) {
		this.destipv6 = destipv6;
	}

	
	public String getDestipstr() {
		return destipstr;
	}

	public void setDestipstr(String destipstr) {
		this.destipstr = destipstr;
	}


	public int getSrcport() {
		return srcport;
	}


	public void setSrcport(int srcport) {
		this.srcport = srcport;
	}


	public int getDestport() {
		return destport;
	}


	public void setDestport(int destport) {
		this.destport = destport;
	}


	public String getProtocol() {
		return protocol;
	}


	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}


	public Date getStarttime() {
		return starttime;
	}


	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}


	public Date getEndtime() {
		return endtime;
	}


	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}


	public String getMastername() {
		return mastername;
	}


	public void setMastername(String mastername) {
		this.mastername = mastername;
	}


	public String getSlavename() {
		return slavename;
	}


	public void setSlavename(String slavename) {
		this.slavename = slavename;
	}


	public String getOperation() {
		return operation;
	}


	public void setOperation(String operation) {
		this.operation = operation;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

	public BigInteger getDevip() {
		return devip;
	}


	public void setDevip(BigInteger devip) {
		this.devip = devip;
	}

	public BigInteger getDevipv6() {
		return devipv6;
	}
	
	public void setDevipv6(BigInteger devipv6) {
		this.devipv6 = devipv6;
	}

	public String getDevipstr() {
		return devipstr;
	}


	public void setDevipstr(String devipstr) {
		this.devipstr = devipstr;
	}


	public int getDevport() {
		return devport;
	}


	public void setDevport(int devport) {
		this.devport = devport;
	}


	public String getBusinessname() {
		return businessname;
	}


	public void setBusinessname(String businessname) {
		this.businessname = businessname;
	}


	public int getBusinessid() {
		return businessid;
	}


	public void setBusinessid(int businessid) {
		this.businessid = businessid;
	}


	public int getAuthenstatus() {
		return authenstatus;
	}


	public void setAuthenstatus(int authenstatus) {
		this.authenstatus = authenstatus;
	}


	public int getAuditlevel() {
		return auditlevel;
	}


	public void setAuditlevel(int auditlevel) {
		this.auditlevel = auditlevel;
	}


	public String getParam_len() {
		return param_len;
	}


	public void setParam_len(String param_len) {
		this.param_len = param_len;
	}


	public String getParam() {
		return param;
	}


	public void setParam(String param) {
		this.param = param;
	}


	public String getRaw_data() {
		return raw_data;
	}


	public void setRaw_data(String raw_data) {
		this.raw_data = raw_data;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getResname() {
		return resname;
	}


	public void setResname(String resname) {
		this.resname = resname;
	}


	public long getResid() {
		return resid;
	}


	public void setResid(long resid) {
		this.resid = resid;
	}


	public int getRestype() {
		return restype;
	}


	public void setRestype(int restype) {
		this.restype = restype;
	}


	public Date getRecordtime() {
		return recordtime;
	}


	public void setRecordtime(Date recordtime) {
		this.recordtime = recordtime;
	}


	public String getIdentifierid() {
		return identifierid;
	}


	public void setIdentifierid(String identifierid) {
		this.identifierid = identifierid;
	}


	public String getSpsensitiveObj() {
		return spsensitiveObj;
	}


	public void setSpsensitiveObj(String spsensitiveObj) {
		this.spsensitiveObj = spsensitiveObj;
	}

	public String getEventTableName() {
		return eventTableName;
	}
	/**
	 * 设置事件入库表名称
	 * @param eventTableName
	 */
	public void setEventTableName(String eventTableName) {
		this.eventTableName = eventTableName;
	}

	public String getAuthenstatus_str() {
		return authenstatus_str;
	}

	public void setAuthenstatus_str(String authenstatus_str) {
		this.authenstatus_str = authenstatus_str;
	}

	public String getRestype_str() {
		return restype_str;
	}

	public void setRestype_str(String restype_str) {
		this.restype_str = restype_str;
	}

	public String getTime_str() {
		return time_str;
	}

	public void setTime_str(String time_str) {
		this.time_str = time_str;
	}

	public String getAuditlevel_str() {
		return auditlevel_str;
	}

	public void setAuditlevel_str(String auditlevel_str) {
		this.auditlevel_str = auditlevel_str;
	}

	public String getSession_guid() {
		return session_guid;
	}

	public void setSession_guid(String session_guid) {
		this.session_guid = session_guid;
	}

	public Date getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(Date begin_time) {
		this.begin_time = begin_time;
	}

	public Date getOperation_time() {
		return operation_time;
	}

	public void setOperation_time(Date operation_time) {
		this.operation_time = operation_time;
	}

	public String getOperationPath() {
		return operationPath;
	}

	public void setOperationPath(String operationPath) {
		this.operationPath = operationPath;
	}

	public String getAuthmode() {
		return authmode;
	}

	public void setAuthmode(String authmode) {
		this.authmode = authmode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getAdlg_type() {
		return adlg_type;
	}

	public void setAdlg_type(int adlg_type) {
		this.adlg_type = adlg_type;
	}

}
