package com.zlebank.zplatform.task.reconcile.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.task.reconcile.dao.SqlQueryDao;
import com.zlebank.zplatform.task.reconcile.service.TxnsLogReconService;

@Service
public class TxnsLogReconServiceImpl implements TxnsLogReconService {

	@Autowired
	private SqlQueryDao dao;

	@Override
	public List<?> getAllMemberByDate(String date) {
		String sql = "select distinct t.ACCSECMERNO, t.ACCSETTLEDATE from t_txns_log t where t.ACCSETTLEDATE=? and t.ACCSECMERNO is not null and SUBSTR (trim(t.retcode),-2) = '00'";
		return dao.query(sql, new Object[] { date });
	}

	@Override
	public List<?> getAllMemberByDateByCharge(String date) {
		String sql = "select distinct m.member_id ,t.INTIME from t_txns_charge t left join t_member m on t.memberid=m.mem_id  where trunc(t.intime)=TO_DATE(?,'YYYYMMDD')";
		return dao.query(sql, new Object[] { date });
	}

	@Override
	public List<?> getSumExpense(String memberId, String date) {
		String sql = "select count(*) total,"
                + " sum (t.amount) totalAmount,"
                + " sum(t.txnfee) totalfee"
                + " from t_txns_log t"
                + " where"
                + " t.ACCSECMERNO = ?"
                + " and t.ACCSETTLEDATE = ?"
                + " and t.busicode in (10000001,10000002,10000005,10000006,11000001)"
                + " and SUBSTR(trim(t.retcode), -2) = '00'";
		return dao.query(sql, new Object[] { memberId, date });
	}

	@Override
	public List<?> getSumRefund(String memberId, String date) {
		String sql = "select count(*) total,"
                + " sum(t.amount) totalAmount," 
				+ " sum(t.txnfee) totalfee"
                + " from t_txns_log t" 
				+ " where t.ACCSECMERNO = ?"
                + " and t.ACCSETTLEDATE = ?"
                + " and t.busicode in (40000001,40000002,40000003,40000004)"
                + " and SUBSTR(trim(t.retcode), -2) = '00'";
		return dao.query(sql, new Object[] { memberId, date });
	}

	@Override
	public List<?> getAllMemberDetailedByDate(String memberId, String date) {
		String sql = "select t.ACCORDNO,t.TXNSEQNO,t.ACCORDCOMMITIME,t.ACCSETTLEDATE,t.amount,t.busicode,t.TXNFEE, t.TXNSEQNO_OG "
                + " from t_txns_log t left join t_bnk_txn b on t.payordno=b.payordno "
                + " where (b.status=9 or b.status is null) and t.accsecmerno=? and t.ACCSETTLEDATE=? and t.payordno is not null and SUBSTR (trim(t.retcode), -2) = '00'  "
                + " and t.busicode in ('10000001','10000002','10000005','10000006','11000001','40000001','40000002','40000003','40000004')";
		return dao.query(sql, new Object[] { memberId, date });
	}

	@Override
	public List<?> getFtpUploadAddress(String memberId) {
		String sql = "select IP,PORT,PATH,USER_NAME,PASSWORD from T_MERCH_CONFING t where t.STATUS='00' and t.MEMBER_ID=? ";
		return dao.query(sql, new Object[] { memberId });
	}

	@Override
	public List<?> getFtpConfigByNameAndModule(String serverName, String module) {
		String sql = "select t.USERS,t.PWD,t.IP,t.PORT from T_FTP t where t.SERVERNAME=? and t.MODULE=?";
		return dao.query(sql, new Object[] { serverName, module });
	}

}
