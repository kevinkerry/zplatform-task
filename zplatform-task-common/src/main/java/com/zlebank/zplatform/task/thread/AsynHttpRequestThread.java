/* 
 * SynHttpRequest.java  
 * 
 * version TODO
 *
 * 2015年8月29日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.thread;

import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zlebank.zplatform.task.bean.NotifyBean;
import com.zlebank.zplatform.task.bean.NotifyTaskBean;
import com.zlebank.zplatform.task.bean.ResultBean;
import com.zlebank.zplatform.task.common.net.HttpUtil;
import com.zlebank.zplatform.task.common.utils.SpringContext;
import com.zlebank.zplatform.task.service.TradeQueueService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月29日 上午9:33:04
 * @since
 */
public class AsynHttpRequestThread implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(AsynHttpRequestThread.class);
	
	
	private final String memberId;
	private final String txnseqno;
	private final String sendUrl;
	private final List<NameValuePair> params;
	//private TxnsNotifyTaskDAO txnsNotifyTaskDAO = (TxnsNotifyTaskDAO) SpringContext.getContext().getBean("txnsNotifyTaskDAO");;
	private TradeQueueService tradeQueueService = (TradeQueueService) SpringContext.getContext().getBean("tradeQueueService");
	private HttpUtil http;
	private static final ThreadLocal<HttpUtil> httpThread = new ThreadLocal<HttpUtil>();

	/**
	 * @param sendUrl
	 * @param list
	 */
	public AsynHttpRequestThread(String memberId, String txnseqno,
			String sendUrl, List<NameValuePair> list) {
		super();
		this.memberId = memberId;
		this.txnseqno = txnseqno;
		this.sendUrl = sendUrl;
		this.params = list;
	}

	public AsynHttpRequestThread(String memberId, String txnseqno,
			String sendUrl, NotifyBean bean ) {
		super();
		this.memberId = memberId;
		this.txnseqno = txnseqno;
		this.sendUrl = sendUrl;
		this.params = bean.getNotifyParam();
		
	}
	public AsynHttpRequestThread(String memberId, String txnseqno,NotifyBean bean ) {
		super();
		this.memberId = memberId;
		this.txnseqno = txnseqno;
		this.sendUrl = bean.getNotifyURL();
		this.params = bean.getNotifyParam();
		
	}
	@Override
	public void run() {
		try {
			sendHttpRequest(sendUrl, params);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendHttpRequest(String sendUrl, List<NameValuePair> params)
			throws HttpException {
		//TxnsNotifyTaskModel asyncNotifyTask = txnsNotifyTaskDAO.getAsyncNotifyTask(txnseqno);
		
		log.info("send notify url:" + sendUrl);
		ResultBean resultBean = null;
		String sendStatus = null;
		try {
			http = httpThread.get();
			if (http == null) {
				http = new HttpUtil();
			}
			http.openConnection();
			resultBean = http.exeHttpPost(sendUrl, params,"UTF-8");
			sendStatus = "01";
			if (resultBean != null) {
				log.info("response message:" + resultBean.getErrMsg());
				if (resultBean.getErrCode().equals("200")) {
					if ("success".equalsIgnoreCase(resultBean.getErrMsg())) {
						log.info("sync notify success");
						sendStatus = "00";
					} else {
						log.info("sync notify fail");
						sendStatus = "01";
					}
				}
			}
			http.closeConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if ("success".equalsIgnoreCase(resultBean.getErrMsg())) {
			log.info("sync notify success complete :" + txnseqno);
		}
		NotifyTaskBean notifyTaskBean = new NotifyTaskBean(memberId, txnseqno, 1, 5,"", sendStatus, resultBean.getErrCode(), sendUrl, "1");
		tradeQueueService.addNotifyQueue(notifyTaskBean);
	}
}
