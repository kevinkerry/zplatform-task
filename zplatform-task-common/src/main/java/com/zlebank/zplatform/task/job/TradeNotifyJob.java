/* 
 * TradeNotifyJob.java  
 * 
 * version TODO
 *
 * 2016年10月25日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.task.service.TradeNotifyService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月25日 上午10:36:16
 * @since 
 */
@Service
public class TradeNotifyJob {

	@Autowired
	private TradeNotifyService tradeNotifyService;
	
	public void tradeNotify(){
		tradeNotifyService.queueNotfiy();
	}
}
