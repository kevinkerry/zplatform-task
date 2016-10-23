package com.zlebank.zplatform.task.dao.impl;

import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.task.common.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.task.dao.TxnsNotifyTaskDAO;
import com.zlebank.zplatform.task.pojo.PojoTxnsNotifyTask;
@Repository("txnsNotifyTaskDAO")
public class TxnsNotifyTaskDAOImpl extends HibernateBaseDAOImpl<PojoTxnsNotifyTask> implements
		TxnsNotifyTaskDAO {


	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void saveTask(PojoTxnsNotifyTask task) {
		// TODO Auto-generated method stub
		saveEntity(task);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateTask(PojoTxnsNotifyTask task) {
		// TODO Auto-generated method stub
		merge(task);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<PojoTxnsNotifyTask> findTaskByTxnseqno(String txnseqno,String memberId) {
		Criteria criteria = getSession().createCriteria(PojoTxnsNotifyTask.class);
		criteria.add(Restrictions.eq("txnseqno", txnseqno));
		criteria.add(Restrictions.eq("memberId", memberId));
		return criteria.list();
	}

	@Override
	@Transactional(readOnly=true)
	public PojoTxnsNotifyTask getAsyncNotifyTask(String txnseqno) {
		Criteria criteria = getSession().createCriteria(PojoTxnsNotifyTask.class);
		criteria.add(Restrictions.eq("txnseqno", txnseqno));
		return (PojoTxnsNotifyTask) criteria.uniqueResult();
	}

}
