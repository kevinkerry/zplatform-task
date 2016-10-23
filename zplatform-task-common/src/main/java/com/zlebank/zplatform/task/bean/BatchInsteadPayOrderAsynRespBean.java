/* 
 * AnonOrderAsynRespBean.java  
 * 
 * version TODO
 *
 * 2016年5月16日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * 批量代付异步通知bean
 *
 * @author guojia
 * @version
 * @date 2016年5月16日 下午1:30:11
 * @since
 */
public class BatchInsteadPayOrderAsynRespBean implements Serializable {
	
	
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	private static final long serialVersionUID = 9083709488325194482L;
	private String version;// 版本
	private String encoding;// 编码方式
	private String txnType;// 交易类型
	private String txnSubType;// 交易子类
	public String bizType;// 产品类型
	private String respCode;// 响应码
	private String respMsg;// 应答信息
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
	

	

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public String getPayTimeout() {
		return payTimeout;
	}

	public void setPayTimeout(String payTimeout) {
		this.payTimeout = payTimeout;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getTxnSubType() {
		return txnSubType;
	}
	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
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
	public BatchInsteadPayOrderAsynRespBean(String version, String encoding,
			String txnType, String txnSubType, String bizType,
			String channelType, String merName, String orderId, String txnTime,
			String payTimeout, String txnAmt, String currencyCode,
			String orderDesc, String reserved, String orderStatus, String tn,
			String respCode, String respMsg) {
		
		this.version = version;
		this.encoding = encoding;
		this.txnType = txnType;
		this.txnSubType = txnSubType;
		this.bizType = bizType;
		this.channelType = channelType;
		this.merName = merName;
		this.orderId = orderId;
		this.txnTime = txnTime;
		this.payTimeout = payTimeout;
		this.txnAmt = txnAmt;
		this.currencyCode = currencyCode;
		this.orderDesc = orderDesc;
		this.reserved = reserved;
		this.orderStatus = orderStatus;
		this.tn = tn;
		this.respCode = respCode;
		this.respMsg = respMsg;
	}

	/**
		 * 
		 */
	public BatchInsteadPayOrderAsynRespBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<NameValuePair> getNotifyParam() {
		BasicNameValuePair[] pairs = new BasicNameValuePair[] {
				new BasicNameValuePair("version", version),
				new BasicNameValuePair("encoding", encoding),
				new BasicNameValuePair("txnType", txnType),
				new BasicNameValuePair("txnSubType", txnSubType),
				new BasicNameValuePair("bizType", bizType),
				new BasicNameValuePair("channelType", channelType),
				new BasicNameValuePair("merName", merName),
				new BasicNameValuePair("orderId", orderId),
				new BasicNameValuePair("txnTime", txnTime),
				new BasicNameValuePair("payTimeout", payTimeout),
				new BasicNameValuePair("txnAmt", txnAmt),
				new BasicNameValuePair("currencyCode", currencyCode),
				new BasicNameValuePair("orderDesc", orderDesc),
				new BasicNameValuePair("reserved", reserved),
				new BasicNameValuePair("orderStatus", orderStatus),
				new BasicNameValuePair("tn", tn),
				new BasicNameValuePair("respCode", respCode),
				new BasicNameValuePair("respMsg", respMsg) };
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		for (int i = 0; i < pairs.length; i++) {
			qparams.add(pairs[i]);
		}

		return qparams;
	}
}
