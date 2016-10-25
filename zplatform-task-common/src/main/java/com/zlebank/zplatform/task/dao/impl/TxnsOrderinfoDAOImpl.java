package com.zlebank.zplatform.task.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.task.common.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.task.dao.TxnsOrderinfoDAO;
import com.zlebank.zplatform.task.pojo.PojoTxnsOrderinfo;

/**
  * @ClassName: TxnsOrderinfoDAOImpl
  * @Description: TODO
  * @author guojia
  * @date 2016年10月22日 下午7:50:35
  *
  */
@Repository("txnsOrderinfoDAO")
public class TxnsOrderinfoDAOImpl extends HibernateBaseDAOImpl<PojoTxnsOrderinfo> implements TxnsOrderinfoDAO {

	

	@Override
	@Transactional(readOnly=true)
	public PojoTxnsOrderinfo getTxnsOrderinfoByTxnseqno(String txnseqno) {
		Criteria criteria = getSession().createCriteria(PojoTxnsOrderinfo.class);
		criteria.add(Restrictions.eq("relatetradetxn", txnseqno));
		PojoTxnsOrderinfo orderinfo= (PojoTxnsOrderinfo) criteria.uniqueResult();
		if(orderinfo != null){
			getSession().evict(orderinfo);
		}
		return orderinfo;
	}

}
