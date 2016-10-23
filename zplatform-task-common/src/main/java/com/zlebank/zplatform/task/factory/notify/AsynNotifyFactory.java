package com.zlebank.zplatform.task.factory.notify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.task.bean.BatchInsteadPayOrderAsynRespBean;
import com.zlebank.zplatform.task.bean.NotifyBean;
import com.zlebank.zplatform.task.dao.InsteadPayRealtimeDAO;
import com.zlebank.zplatform.task.dao.TxnsOrderinfoDAO;
import com.zlebank.zplatform.task.enums.BiztypeEnum;
import com.zlebank.zplatform.task.enums.BusinessEnum;
import com.zlebank.zplatform.task.factory.BeanFactory;
import com.zlebank.zplatform.task.pojo.PojoInsteadPayRealtime;

/**
  * @ClassName: AsynNotifyFactory
  * @Description: TODO
  * @author guojia
  * @date 2016年10月22日 下午8:40:31
  *
  */
@Service
public class AsynNotifyFactory implements BeanFactory{

	@Autowired
	private TxnsOrderinfoDAO txnsOrderinfoDAO;
	@Autowired
	private InsteadPayRealtimeDAO insteadPayRealtimeDAO;
	
	@Override
	public NotifyBean getBean(String txnseqno,BiztypeEnum biztypeEnum) {
		NotifyBean bean = null;
		if(biztypeEnum == BiztypeEnum.NM000210){
			
		}else if(biztypeEnum == BiztypeEnum.NM000205){
			
			bean = new NotifyBean();
		}
		return bean;
	}

	@Override
	public NotifyBean getInsteadPayBean(String txnseqno,
			BusinessEnum businessEnum) {
		NotifyBean bean = null;
		if(businessEnum == BusinessEnum.INSTEADPAY_SINGLE){
			PojoInsteadPayRealtime insteadPayRealtime = insteadPayRealtimeDAO.getInsteadPayByTxnseqno(txnseqno);
			BiztypeEnum biztypeEnum = BiztypeEnum.fromValue(insteadPayRealtime.getBizType());
			if(biztypeEnum == BiztypeEnum.NM000210){
				
			}
		}else if(businessEnum == BusinessEnum.INSTEADPAY_BATCH){
			
		}
		
		return bean;
	}

}
