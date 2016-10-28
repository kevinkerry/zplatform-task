/* 
 * TradeQueueJob.java  
 * 
 * version TODO
 *
 * 2016年10月26日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.task.service.TradeQueueScanService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月26日 上午11:28:02
 * @since 
 */
@Service
public class TradeQueueJob {

	
	@Autowired
	private TradeQueueScanService tradeQueueScanService;
	
	public void scanTradeQueue(){
		tradeQueueScanService.scanTradeQueue();
	}
}
