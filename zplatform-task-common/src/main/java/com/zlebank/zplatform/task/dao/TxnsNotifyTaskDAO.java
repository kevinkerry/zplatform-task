/* 
 * ITxnsNotifyTaskDAO.java  
 * 
 * version TODO
 *
 * 2015年11月9日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.dao;

import java.util.List;

import com.zlebank.zplatform.task.common.dao.BaseDAO;
import com.zlebank.zplatform.task.pojo.PojoTxnsNotifyTask;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月9日 上午9:49:46
 * @since 
 */
public interface TxnsNotifyTaskDAO extends BaseDAO<PojoTxnsNotifyTask>{
	/**
	 * 保存异步通知信息
	 * @param task
	 */
    public void saveTask(PojoTxnsNotifyTask task);
    /**
     * 更新异步通知信息
     * @param task
     * @return
     */
    public void updateTask(PojoTxnsNotifyTask task);
    /**
     * 通过交易序列号查询获取异步通知结果集
     * @param txnseqno
     * @param memberId
     * @return
     */
    public List<PojoTxnsNotifyTask> findTaskByTxnseqno(String txnseqno,String memberId);
    
    /**
     * 取得异步 通知类型为异步的异步通知交易
     * @param txnseqno
     * @return
     */
    public PojoTxnsNotifyTask getAsyncNotifyTask(String txnseqno);
}
