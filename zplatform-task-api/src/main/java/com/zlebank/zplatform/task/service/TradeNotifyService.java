
package com.zlebank.zplatform.task.service;


/**
  * @ClassName: TradeNotifyService
  * @Description: 交易异步通知service
  * @author guojia
  * @date 2016年10月22日 下午7:25:41
  *
  */
public interface TradeNotifyService {

	
	/**
	  * notify
	  * @Title: notify
	  * @Description: 交易异步通知
	  * @param txnseqno    交易序列号
	  * @return void    返回类型
	  * @throws
	 */
	public void notify(String txnseqno);
	
	/**
	 * 
	  * queueNotfiy
	  * @Title: queueNotfiy
	  * @Description: TODO    参数
	  * @return void    返回类型
	  * @throws
	 */
	public void queueNotfiy();
}
