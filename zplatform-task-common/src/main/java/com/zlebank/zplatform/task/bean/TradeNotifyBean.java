/* 
 * PayNotifyBean.java  
 * 
 * version TODO
 *
 * 2016年10月24日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.bean;

import com.zlebank.zplatform.task.pojo.PojoInsteadPayRealtime;
import com.zlebank.zplatform.task.pojo.PojoTxnsOrderinfo;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月24日 下午5:22:24
 * @since
 */
public class TradeNotifyBean {
	private String version;// 版本
	private String encoding;// 编码方式
	private String txnType;// 交易类型
	private String txnSubType;// 交易子类
	private String bizType;// 产品类型
	private String channelType;// 渠道类型
	private String merName;// 商户名称
	private String orderId;// 商户订单号
	private String txnTime;// 订单发送时间
	private String payTimeout;// 支付超时时间
	private String txnAmt;// 交易金额
	private String currencyCode;// 交易币种
	private String orderDesc;// 订单描述
	private String reserved;// 保留域
	private String orderStatus;// 订单状态
	private String tn;// 受理订单号
	private String respCode;// 响应码
	private String respMsg;// 应答信息
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}
	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	/**
	 * @return the txnType
	 */
	public String getTxnType() {
		return txnType;
	}
	/**
	 * @param txnType the txnType to set
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	/**
	 * @return the txnSubType
	 */
	public String getTxnSubType() {
		return txnSubType;
	}
	/**
	 * @param txnSubType the txnSubType to set
	 */
	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}
	/**
	 * @return the bizType
	 */
	public String getBizType() {
		return bizType;
	}
	/**
	 * @param bizType the bizType to set
	 */
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	/**
	 * @return the channelType
	 */
	public String getChannelType() {
		return channelType;
	}
	/**
	 * @param channelType the channelType to set
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	/**
	 * @return the merName
	 */
	public String getMerName() {
		return merName;
	}
	/**
	 * @param merName the merName to set
	 */
	public void setMerName(String merName) {
		this.merName = merName;
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the txnTime
	 */
	public String getTxnTime() {
		return txnTime;
	}
	/**
	 * @param txnTime the txnTime to set
	 */
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	/**
	 * @return the payTimeout
	 */
	public String getPayTimeout() {
		return payTimeout;
	}
	/**
	 * @param payTimeout the payTimeout to set
	 */
	public void setPayTimeout(String payTimeout) {
		this.payTimeout = payTimeout;
	}
	/**
	 * @return the txnAmt
	 */
	public String getTxnAmt() {
		return txnAmt;
	}
	/**
	 * @param txnAmt the txnAmt to set
	 */
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	/**
	 * @return the orderDesc
	 */
	public String getOrderDesc() {
		return orderDesc;
	}
	/**
	 * @param orderDesc the orderDesc to set
	 */
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	/**
	 * @return the reserved
	 */
	public String getReserved() {
		return reserved;
	}
	/**
	 * @param reserved the reserved to set
	 */
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}
	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * @return the tn
	 */
	public String getTn() {
		return tn;
	}
	/**
	 * @param tn the tn to set
	 */
	public void setTn(String tn) {
		this.tn = tn;
	}
	/**
	 * @return the respCode
	 */
	public String getRespCode() {
		return respCode;
	}
	/**
	 * @param respCode the respCode to set
	 */
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	/**
	 * @return the respMsg
	 */
	public String getRespMsg() {
		return respMsg;
	}
	/**
	 * @param respMsg the respMsg to set
	 */
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	/**
	 * @param version
	 * @param encoding
	 * @param txnType
	 * @param txnSubType
	 * @param bizType
	 * @param channelType
	 * @param merName
	 * @param orderId
	 * @param txnTime
	 * @param payTimeout
	 * @param txnAmt
	 * @param currencyCode
	 * @param orderDesc
	 * @param reserved
	 * @param orderStatus
	 * @param tn
	 * @param respCode
	 * @param respMsg
	 */
	public TradeNotifyBean(PojoTxnsOrderinfo orderinfo) {
		super();
		this.version = "1.0";
		this.encoding = "1";
		this.txnType = orderinfo.getTxntype();
		this.txnSubType = orderinfo.getTxnsubtype();
		this.bizType = orderinfo.getBiztype();
		this.channelType = "00";
		this.merName = orderinfo.getSecmembername();
		this.orderId = orderinfo.getOrderno();
		this.txnTime = orderinfo.getOrdercommitime();
		this.payTimeout = orderinfo.getPaytimeout();
		this.txnAmt = orderinfo.getOrderamt()+"";
		this.currencyCode = orderinfo.getCurrencycode();
		this.orderDesc = orderinfo.getOrderdesc();
		this.reserved = orderinfo.getReserved();
		this.orderStatus = orderinfo.getStatus();
		this.tn = orderinfo.getTn();
		this.respCode = "00";
		this.respMsg = "交易成功";
	}
	/**
	 * 
	 */
	public TradeNotifyBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TradeNotifyBean(PojoInsteadPayRealtime insteadPayRealtime) {
		super();
		this.version = "1.0";
		this.encoding = "1";
		this.txnType = insteadPayRealtime.getTxnType();
		this.txnSubType = insteadPayRealtime.getTxnSubType();
		this.bizType = insteadPayRealtime.getBizType();
		this.channelType = "00";
		this.merName = insteadPayRealtime.getMerName();
		this.orderId = insteadPayRealtime.getOrderno();
		this.txnTime = insteadPayRealtime.getOrderCommiTime();
		//this.payTimeout = insteadPayRealtime.get;
		this.txnAmt = insteadPayRealtime.getTransAmt()+"";
		this.currencyCode = insteadPayRealtime.getCurrencyCode();
		this.orderDesc = insteadPayRealtime.getOrderDesc();
		this.reserved = insteadPayRealtime.getNotes();
		this.orderStatus = insteadPayRealtime.getStatus();
		this.tn = insteadPayRealtime.getTn();
		this.respCode = insteadPayRealtime.getRetCode();
		this.respMsg = insteadPayRealtime.getRetInfo();
	}
	
}
