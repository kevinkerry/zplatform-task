package com.zlebank.zplatform.task.dao;

import com.zlebank.zplatform.task.common.dao.BaseDAO;
import com.zlebank.zplatform.task.pojo.PojoTxnsLog;

/**
  * @ClassName: TxnsLogDAO
  * @Description: TODO
  * @author guojia
  * @date 2016年10月22日 下午7:43:40
  *
  */
public interface TxnsLogDAO extends BaseDAO<PojoTxnsLog>{

	/**
	 * 
	  * getTxnsLogByTxnseqno
	  * @Title: getTxnsLogByTxnseqno
	  * @Description: TODO
	  * @param txnseqno 交易序列号
	  * @return PojoTxnsLog    返回类型
	  * @throws
	 */
	public PojoTxnsLog getTxnsLogByTxnseqno(String txnseqno);
}
