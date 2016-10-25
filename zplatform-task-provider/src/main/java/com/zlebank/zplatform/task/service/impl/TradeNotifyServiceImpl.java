package com.zlebank.zplatform.task.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.task.bean.NotifyBean;
import com.zlebank.zplatform.task.bean.NotifyQueueBean;
import com.zlebank.zplatform.task.common.utils.DateUtil;
import com.zlebank.zplatform.task.dao.TxnsLogDAO;
import com.zlebank.zplatform.task.dao.TxnsNotifyTaskDAO;
import com.zlebank.zplatform.task.dao.TxnsOrderinfoDAO;
import com.zlebank.zplatform.task.enums.BiztypeEnum;
import com.zlebank.zplatform.task.enums.BusiTypeEnum;
import com.zlebank.zplatform.task.enums.BusinessEnum;
import com.zlebank.zplatform.task.enums.TradeQueueEnum;
import com.zlebank.zplatform.task.factory.BeanFactory;
import com.zlebank.zplatform.task.pojo.PojoTxnsLog;
import com.zlebank.zplatform.task.pojo.PojoTxnsNotifyTask;
import com.zlebank.zplatform.task.pojo.PojoTxnsOrderinfo;
import com.zlebank.zplatform.task.service.TradeNotifyService;
import com.zlebank.zplatform.task.service.TradeQueueService;
import com.zlebank.zplatform.task.thread.AsynHttpRequestThread;
import com.zlebank.zplatform.task.thread.AsyncNotifyThreadPool;
@Service("tradeNotifyService")
public class TradeNotifyServiceImpl implements TradeNotifyService {

	private final static Logger log = LoggerFactory.getLogger(TradeNotifyServiceImpl.class);
	@Autowired
	private TxnsOrderinfoDAO txnsOrderinfoDAO;
	@Autowired
	private TxnsLogDAO txnsLogDAO;
	@Autowired
	private BeanFactory beanFactory;
	@Autowired
	private TradeQueueService tradeQueueService;
	@Autowired
	private TxnsNotifyTaskDAO txnsNotifyTaskDAO;
	@Override
	public void notify(String txnseqno) {
		// TODO Auto-generated method stub
		NotifyBean bean = null;
		try {
			PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(txnseqno);
			BusiTypeEnum busiTypeEnum = BusiTypeEnum.fromValue(txnsLog.getBusitype());
			if(busiTypeEnum == BusiTypeEnum.CONSUMPTION){//消费
				PojoTxnsOrderinfo orderinfo = txnsOrderinfoDAO.getTxnsOrderinfoByTxnseqno(txnseqno);
				BiztypeEnum biztypeEnum = BiztypeEnum.fromValue(orderinfo.getBiztype());
				bean = beanFactory.getBean(txnseqno,biztypeEnum);
			}else if(busiTypeEnum == BusiTypeEnum.CHARGE){//充值
				PojoTxnsOrderinfo orderinfo = txnsOrderinfoDAO.getTxnsOrderinfoByTxnseqno(txnseqno);
				BiztypeEnum biztypeEnum = BiztypeEnum.fromValue(orderinfo.getBiztype());
				bean = beanFactory.getBean(txnseqno,biztypeEnum);
			}else if(busiTypeEnum == BusiTypeEnum.INSTEADPAY){//代付
				bean = beanFactory.getInsteadPayBean(txnseqno, BusinessEnum.fromValue(txnsLog.getBusicode()));
			}
			if(bean!=null){
				AsynHttpRequestThread notifyThread = new AsynHttpRequestThread(txnsLog.getAccsecmerno(), txnseqno,bean);
				AsyncNotifyThreadPool.getInstance().executeMission(notifyThread);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void queueNotfiy() {
		// TODO Auto-generated method stub
		log.info("【开始异步通知任务】");
		long queueSize = tradeQueueService.getQueueSize(TradeQueueEnum.NOTIFYQUEUE);
		log.info("【异步通知队列大小】"+queueSize);
		if(queueSize>0){
			for (int i = 0; i < queueSize; i++) {
				NotifyQueueBean notifyQueueBean = tradeQueueService.queuePop(TradeQueueEnum.NOTIFYQUEUE,NotifyQueueBean.class);
				log.info("【异步通知队列数据】"+JSON.toJSONString(notifyQueueBean));
				if(notifyQueueBean==null){
					continue;
				}
				if(Long.valueOf(DateUtil.getCurrentDateTime())<Long.valueOf(notifyQueueBean.getSendDateTime())){//没有到通知时间，不发送信息，重回队列
					log.info("【异步通知队列数据,txnseqno:"+notifyQueueBean.getTxnseqno()+"异步通知时间:"+notifyQueueBean.getSendDateTime()+" 未到发送时间】");
					tradeQueueService.addNotifyQueue(notifyQueueBean);
					continue;
				}
				//判断异步通知是否成功
				PojoTxnsNotifyTask asyncNotifyTask = txnsNotifyTaskDAO.getAsyncNotifyTask(notifyQueueBean.getTxnseqno());
				if("00".equals(asyncNotifyTask.getSendStatus())){
					log.info("【异步通知队列数据,txnseqno:"+notifyQueueBean.getTxnseqno()+"异步通知完成】");
					continue;
				}
				notify(notifyQueueBean.getTxnseqno());
			}
			
		}
		
	}

}
