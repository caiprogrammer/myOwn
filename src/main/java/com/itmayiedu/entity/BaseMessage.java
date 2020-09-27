package com.itmayiedu.entity;

import java.io.Serializable;
import java.util.Date;

public class BaseMessage implements Serializable{
	private String	srcAddress;     //发送方IP
	private int		srcPort	= -1;   //发送方端口
	private String	message;		//原始日志
	private String	parsedMessage;	//范化后日志
	private Date	time;			//接受日志时间
	
	
	
	/**
	 * @return the parsedMessage
	 */
	public String getParsedMessage() {
		return parsedMessage;
	}
	/**
	 * @param parsedMessage the parsedMessage to set
	 */
	public void setParsedMessage(String parsedMessage) {
		this.parsedMessage = parsedMessage;
	}
	/**
	 * @return the srcAddress
	 */
	public String getSrcAddress() {
		return srcAddress;
	}
	/**
	 * @param srcAddress the srcAddress to set
	 */
	public void setSrcAddress(String srcAddress) {
		this.srcAddress = srcAddress;
	}
	/**
	 * @return the srcPort
	 */
	public int getSrcPort() {
		return srcPort;
	}
	/**
	 * @param srcPort the srcPort to set
	 */
	public void setSrcPort(int srcPort) {
		this.srcPort = srcPort;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	
	public String toString(){
		String str = "srcAddress="+this.srcAddress+","
	    		   + "srcPort="+this.srcPort+"," 
	    		   + "mstime="+this.time+","
	    		   + "message="+this.message+","
	    		   + "parsedMessage="+this.parsedMessage;
	    return str;
	}
}
