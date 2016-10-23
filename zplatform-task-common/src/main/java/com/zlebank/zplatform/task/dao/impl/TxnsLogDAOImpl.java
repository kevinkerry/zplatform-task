package com.zlebank.zplatform.task.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.task.common.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.task.dao.TxnsLogDAO;
import com.zlebank.zplatform.task.pojo.PojoTxnsLog;
/**
 * 
  * @ClassName: TxnsLogDAOImpl
  * @Description: TODO
  * @author guojia
  * @date 2016年10月22日 下午7:51:49
  *
 */
@Repository("txnsLogDAO")
public class TxnsLogDAOImpl extends HibernateBaseDAOImpl<PojoTxnsLog> implements TxnsLogDAO{

	@Override
	@Transactional(readOnly=true)
	public PojoTxnsLog getTxnsLogByTxnseqno(String txnseqno) {
		Criteria criteria = getSession().createCriteria(PojoTxnsLog.class);
		criteria.add(Restrictions.eq("txnseqno", txnseqno));
		return (PojoTxnsLog) criteria.uniqueResult();
	}

}
