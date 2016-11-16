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

	@Override
	public List<?> getBondMemberByDate(String date) {
		// 保证金线上充值：20000002,保证金提取：50000002
		String sql = "select distinct t.ACCSECMERNO, t.ACCSETTLEDATE from t_txns_log t where t.ACCSETTLEDATE=? and t.ACCSECMERNO is not null and SUBSTR (trim(t.retcode),-2) = '00' and t.busicode in ('20000002','20000003','50000002')";
		return dao.query(sql, new Object[] { date });
	}

	@Override
	public List<?> getSumBond(String memberId, String date) {
		String sql = "select count(*) total,sum (t.amount) totalAmount,sum(t.txnfee) totalfee "
                + "from t_txns_log t  "
                + "where  t.ACCSECMERNO = ? and t.ACCSETTLEDATE=? and t.busicode in ('20000002','20000003','50000002')"
                + " and SUBSTR(trim(t.retcode), -2) = '00'";
		return dao.query(sql, new Object[] { memberId, date });
	}

	@Override
	public List<?> getBondByDate(String memberId, String date) {
		String sql = "select t.ACCORDNO,t.TXNSEQNO,t.ACCORDCOMMITIME,t.ACCSETTLEDATE,t.AMOUNT,t.BUSICODE,t.TXNFEE,t.PAYORDCOMTIME "
				+ "from t_txns_log t left join t_bnk_txn b on t.payordno=b.payordno where (b.status=9 or b.status is null) and "
				+ "t.accsecmerno=? and t.ACCSETTLEDATE=? and t.payordno is not null and SUBSTR (trim(t.retcode), -2) = '00'  and t.busicode in ('20000002','20000003','50000002')";
		return dao.query(sql, new Object[] { memberId, date });
	}

	@Override
	public List<?> geTransferAccountMemberByDate(String date) {
		String sql = "select distinct t.ACCSECMERNO, t.ACCSETTLEDATE from t_txns_log t where t.ACCSETTLEDATE=? and t.ACCSECMERNO is not null and SUBSTR (trim(t.retcode),-2) = '00' and t.busicode='50000001'";
		return dao.query(sql, new Object[] { date });
	}

	@Override
	public List<?> getSumTransferAccount(String memberId, String date) {
		String sql = "select count(*) total,sum (t.amount) totalAmount,sum(t.txnfee) totalfee "
                + "from t_txns_log t  "
                + "where  t.ACCSECMERNO = ? and t.ACCSETTLEDATE=? and t.busicode = '50000001'"
                + " and SUBSTR(trim(t.retcode), -2) = '00'";
		return dao.query(sql, new Object[] { memberId, date });
	}

	@Override
	public List<?> getTransferAccountByDate(String memberId, String date) {
		String sql = "select t.ACCORDNO,t.TXNSEQNO,t.ACCORDCOMMITIME,t.ACCSETTLEDATE,t.AMOUNT,t.BUSICODE,t.TXNFEE,t.PAYORDCOMTIME "
				+ "from t_txns_log t left join t_bnk_txn b on t.payordno=b.payordno where (b.status=9 or b.status is null) and "
				+ "t.accsecmerno=? and t.ACCSETTLEDATE=? and t.payordno is not null and SUBSTR (trim(t.retcode), -2) = '00'  and t.busicode ='50000001'";
		return dao.query(sql, new Object[] { memberId, date });
	}

	@Override
	public List<?> getInsteadMemberByDate(String date) {
		String sql = "select distinct t.ACCSECMERNO, t.ACCSETTLEDATE from t_txns_log t where t.ACCSETTLEDATE=? and t.ACCSECMERNO is not null and SUBSTR (trim(t.retcode),-2) = '00' and t.busicode='70000001'";
		return dao.query(sql, new Object[] { date });
	}

	@Override
	public List<?> getSumInstead(String memberId, String date) {
		String sql = "select count(*) total,"
                + " sum (t.amount) totalAmount," + " sum(t.txnfee) totalfee"
                + " from t_txns_log t" + " where" + " t.ACCSECMERNO = ?"
                + " and t.ACCSETTLEDATE = ?" + " and t.busicode in (70000001)"
                + " and SUBSTR(trim(t.retcode), -2) = '00'";
		return dao.query(sql, new Object[] { memberId, date });
	}

	@Override
	public List<?> getInsteadMerchantDetailedByDate(String memberId, String date) {
		String sql = "select t.ACCORDNO,t.TXNSEQNO,t.ACCORDCOMMITIME,t.ACCSETTLEDATE,t.amount,t.busicode,t.TXNFEE,t.PAYORDCOMTIME from t_txns_log t left join t_bnk_txn b on t.payordno=b.payordno where (b.status=9 or b.status is null) and t.accsecmerno=? and t.ACCSETTLEDATE=? and t.payordno is not null and SUBSTR (trim(t.retcode), -2) = '00'  and t.busicode in ('70000001')";
		return dao.query(sql, new Object[] { memberId, date });
	}

	@Override
	public List<?> getSinglepaymentMemberByDate(String dateTime) {
		String sql = "select distinct t.ACCSECMERNO, t.ACCSETTLEDATE from t_txns_log t where t.ACCSETTLEDATE=? and t.ACCSECMERNO is not null and SUBSTR (trim(t.retcode),-2) = '00' and t.busicode='70000002'";// 单笔代付
		return dao.query(sql, new Object[] { dateTime });
	}

	@Override
	public List<?> getSumSinglepayment(String memberId, String dateTime) {
		String sql = "select count(*) total,"
                + " sum (t.amount) totalAmount," + " sum(t.txnfee) totalfee"
                + " from t_txns_log t" + " where" + " t.ACCSECMERNO = ?"
                + " and t.ACCSETTLEDATE = ?" + " and t.busicode = '70000002' "
                + " and SUBSTR(trim(t.retcode), -2) = '00'";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getSinglepaymentDetailByDate(String memberId, String dateTime) {
		String sql = "select t.ACCORDNO,t.TXNSEQNO,t.ACCORDCOMMITIME,t.ACCSETTLEDATE,t.amount,t.busicode,t.TXNFEE,t.PAYORDCOMTIME "
				+ " from t_txns_log t left join t_bnk_txn b on t.payordno=b.payordno where (b.status=9 or b.status is null) and "
				+ " t.accsecmerno=? and t.ACCSETTLEDATE=? and t.payordno is not null and SUBSTR (trim(t.retcode), -2) = '00'  and t.busicode in ('70000002')";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getSingleInsteadMemberByDate(String dateTime) {
		String sql = "select distinct t.TRAN_DATE,tr.MER_ID from t_txns_cmbc_inst_pay_log t,T_INSTEAD_PAY_REALTIME tr "
				+ " where tr.TXNSEQNO = t.TXNSEQNO  and tr.STATUS='05' and t.TRAN_DATE=?  ";
		return dao.query(sql, new Object[] { dateTime });
	}

	@Override
	public List<?> getSumSingleInstead(String memberId, String dateTime) {
		String sql = "select count(*) total,sum(t.TRANS_AMT) totalAmount from T_TXNS_CMBC_INST_PAY_LOG  t,T_INSTEAD_PAY_REALTIME tr"
				+ " where t.TXNSEQNO = tr.TXNSEQNO and tr.STATUS='05' and tr.MER_ID=? and t.TRAN_DATE=? ";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getSingleInsteadDetailedByDate(String memberId, String dateTime) {
		String sql = "select  tr.ORDERNO,tr.TXNSEQNO,tr.ORDER_COMMI_TIME,tr.TRANS_AMT,t.RESP_CODE ,t.BANK_TRAN_ID ,t.TRAN_DATE,t.RESP_CODE,t.RESP_MSGt,t.return_date "
				+ " from T_TXNS_CMBC_INST_PAY_LOG t,T_INSTEAD_PAY_REALTIME tr"
				+ " where t.TXNSEQNO = tr.TXNSEQNO and tr.STATUS='05' and tr.MER_ID=? and t.TRAN_DATE=? ";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getBatchpaymentMemberByDate(String dateTime) {
		String sql = "select distinct t.ACCSECMERNO, t.ACCSETTLEDATE from t_txns_log t where t.ACCSETTLEDATE=? and t.ACCSECMERNO is not null and SUBSTR (trim(t.retcode),-2) <> '00' and t.busicode='70000001'";
		return dao.query(sql, new Object[] { dateTime });
	}

	@Override
	public List<?> getSumBatchpayment(String memberId, String dateTime) {
		String sql = "select count(*) total,"
                + " sum (t.amount) totalAmount," + " sum(t.txnfee) totalfee"
                + " from t_txns_log t" + " where" + " t.ACCSECMERNO = ?"
                + " and t.ACCSETTLEDATE = ?" + " and t.busicode = '70000001' "
                + " and SUBSTR(trim(t.retcode), -2) <> '00' ";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getBatchpaymentDetailedByDate(String memberId, String dateTime) {
		String sql = "select t1.mer_id, t1.order_id,t.TXNSEQNO,t.txn_time, t1.amt,t1.resp_code, t1.resp_msg , t2.payordno, t2.payordfintime,t1.return_date from t_instead_pay_detail t1 "
                + " join t_instead_pay_batch t  on t.id=t1.batch_id "
                + " left join t_txns_log t2 on t2.txnseqno=t1.txnseqno "
                + " where t1.resp_code='04' and t1.mer_id =? and t.txn_time =?";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getIndustryRechargeByDate(String dateTime) {
		String sql = "select distinct t.ACCMEMBERID, t.ACCSETTLEDATE from t_txns_log t where t.ACCSETTLEDATE=? and t.ACCMEMBERID is not null and SUBSTR (trim(t.retcode),-2) = '00' and t.busicode='20000005'";
		return dao.query(sql, new Object[] { dateTime });
	}

	@Override
	public List<?> getSumIndustryRecharge(String memberId, String dateTime) {
		String sql = "select count(*) total,sum (t.amount) totalAmount,sum(t.txnfee) totalFee "
                + "from t_txns_log t  "
                + "where  t.ACCMEMBERID = ? and t.ACCSETTLEDATE=? and t.busicode = '20000005'"
                + " and SUBSTR(trim(t.retcode), -2) = '00'";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getIndustryRechargeDetailByDate(String memberId, String dateTime) {
		String sql = "select t.ACCORDNO,t.TXNSEQNO,t.ACCORDCOMMITIME,t.ACCSETTLEDATE,t.amount,t.busicode,t.TXNFEE,"
                + "t.PAYORDCOMTIME from t_txns_log t left join t_bnk_txn b on t.payordno=b.payordno  "
                + "where (b.status=9 or b.status is null) and t.ACCMEMBERID=? and t.ACCSETTLEDATE=? and t.payordno is not null "
                + "and SUBSTR (trim(t.retcode), -2) = '00'  and t.busicode in ('20000005')";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getIndustryConsumeByDate(String dateTime) {
		String sql = "select distinct t.ACCMEMBERID, t.ACCSETTLEDATE from t_txns_log t where t.ACCSETTLEDATE=? and t.ACCMEMBERID is not null and SUBSTR (trim(t.retcode),-2) = '00' and t.busicode='10000005'";
		return dao.query(sql, new Object[] { dateTime });
	}

	@Override
	public List<?> getSumIndustryConsume(String memberId, String dateTime) {
		String sql = "select count(*) total,sum (t.amount) totalAmount,sum(t.txnfee) totalFee "
                + "from t_txns_log t  "
                + "where  t.ACCMEMBERID = ? and t.ACCSETTLEDATE=? and t.busicode = '10000005'"
                + " and SUBSTR(trim(t.retcode), -2) = '00'";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getIndustryConsumeDetailByDate(String memberId, String dateTime) {
		String sql = "select t.ACCORDNO,t.TXNSEQNO,t.ACCORDCOMMITIME,t.ACCSETTLEDATE,t.amount,t.busicode,"
                + "t.TXNFEE,t.PAYORDCOMTIME from t_txns_log t left join t_bnk_txn b on t.payordno=b.payordno "
                + "where (b.status=9 or b.status is null) and t.ACCMEMBERID=? and t.ACCSETTLEDATE=? and "
                + " SUBSTR (trim(t.retcode), -2) = '00'  and t.busicode ='10000005' ";
		return null;
	}

	@Override
	public List<?> getIndustryDrawByDate(String dateTime) {
		String sql = "select distinct t.ACCMEMBERID, t.ACCSETTLEDATE from t_txns_log t where t.ACCSETTLEDATE=? and t.ACCMEMBERID is not null and SUBSTR (trim(t.retcode),-2) = '00' and t.busicode='50000004'";
		return dao.query(sql, new Object[] { dateTime });
	}

	@Override
	public List<?> getSumIndustryDraw(String memberId, String dateTime) {
		String sql = "select count(*) total,sum (t.amount) totalAmount,sum(t.txnfee) totalFee "
                + "from t_txns_log t  "
                + "where  t.ACCMEMBERID = ? and t.ACCSETTLEDATE=? and t.busicode = '50000004'"
                + " and SUBSTR(trim(t.retcode), -2) = '00'";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getIndustryDrawDetailByDate(String memberId, String dateTime) {
		String sql = "select t.ACCORDNO,t.TXNSEQNO,t.ACCORDCOMMITIME,t.ACCSETTLEDATE,t.amount,t.busicode,"
				+ "t.TXNFEE,t.PAYORDCOMTIME from t_txns_log t left join t_bnk_txn b on t.payordno=b.payordno "
				+ "where (b.status=9 or b.status is null) and t.ACCMEMBERID=? and t.ACCSETTLEDATE=? and "
				+ " SUBSTR (trim(t.retcode), -2) = '00'  and t.busicode ='50000004' ";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getIndustryTransferByDate(String dateTime) {
		String sql = "select distinct t.ACCMEMBERID, t.ACCSETTLEDATE from t_txns_log t where t.ACCSETTLEDATE=? and t.ACCMEMBERID is not null and SUBSTR (trim(t.retcode),-2) = '00' and t.busicode='50000003'";
		return dao.query(sql, new Object[] { dateTime });
	}

	@Override
	public List<?> getSumIndustryTransfer(String memberId, String dateTime) {
		String sql = "select count(*) total,sum (t.amount) totalAmount,sum(t.txnfee) totalFee "
                + "from t_txns_log t  "
                + "where  t.ACCMEMBERID = ? and t.ACCSETTLEDATE=? and t.busicode = '50000003'"
                + " and SUBSTR(trim(t.retcode), -2) = '00'";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getIndustryTransferDetailByDate(String memberId, String dateTime) {
		String sql = "select t.ACCORDNO,t.TXNSEQNO,t.ACCORDCOMMITIME,t.ACCSETTLEDATE,t.amount,t.busicode,"
				+ "t.TXNFEE,t.PAYORDCOMTIME from t_txns_log t left join t_bnk_txn b on t.payordno=b.payordno "
				+ "where (b.status=9 or b.status is null) and t.ACCMEMBERID=? and t.ACCSETTLEDATE=? and "
				+ " SUBSTR (trim(t.retcode), -2) = '00'  and t.busicode ='50000003' ";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getIndustryRefundByDate(String dateTime) {
		String sql = "select distinct t.ACCMEMBERID, t.ACCSETTLEDATE from t_txns_log t "
				+ "where t.ACCSETTLEDATE=? and t.ACCMEMBERID is not null and SUBSTR (trim(t.retcode),-2) = '00' and t.busicode='40000003'";
		return dao.query(sql, new Object[] { dateTime });
	}

	@Override
	public List<?> getSumIndustryRefund(String memberId, String dateTime) {
		String sql = "select count(*) total,sum (t.amount) totalAmount,sum(t.txnfee) totalFee "
                + "from t_txns_log t  "
                + "where  t.ACCMEMBERID = ? and t.ACCSETTLEDATE=? and t.busicode = '40000003'"
                + " and SUBSTR(trim(t.retcode), -2) = '00'";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getIndustryRefundDetailByDate(String memberId, String dateTime) {
		String sql = "select t.ACCORDNO,t.TXNSEQNO,t.ACCORDCOMMITIME,t.ACCSETTLEDATE,t.amount,t.busicode,"
				+ "t.TXNFEE,t.PAYORDCOMTIME,t.TXNSEQNO_OG from t_txns_log t left join t_bnk_txn b on t.payordno=b.payordno "
				+ "where (b.status=9 or b.status is null) and t.ACCMEMBERID=? and t.ACCSETTLEDATE=? and "
				+ " SUBSTR (trim(t.retcode), -2) = '00'  and t.busicode ='40000003' ";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getCreditRechargeByDate(String dateTime) {
		String sql = "select distinct t.ACCMEMBERID, t.ACCSETTLEDATE from t_txns_log t "
				+ "where t.ACCSETTLEDATE=? and t.ACCMEMBERID is not null and SUBSTR (trim(t.retcode),-2) = '00' and t.busicode='20000004'";
		return dao.query(sql, new Object[] { dateTime });
	}

	@Override
	public List<?> getSumCreditRecharge(String memberId, String dateTime) {
		String sql = "select count(*) total,sum (t.amount) totalAmount,sum(t.txnfee) totalFee "
                + "from t_txns_log t  "
                + "where  t.ACCMEMBERID = ? and t.ACCSETTLEDATE=? and t.busicode = '20000004'"
                + " and SUBSTR(trim(t.retcode), -2) = '00'";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getCreditRechargeDetailByDate(String memberId, String dateTime) {
		String sql = "select t.ACCORDNO,t.TXNSEQNO,t.ACCORDCOMMITIME,t.ACCSETTLEDATE,t.amount,t.busicode,"
                + "t.TXNFEE,t.PAYORDCOMTIME from t_txns_log t left join t_bnk_txn b on t.payordno=b.payordno "
                + "where (b.status=9 or b.status is null) and t.ACCMEMBERID=? and t.ACCSETTLEDATE=? and "
                + " SUBSTR (trim(t.retcode), -2) = '00'  and t.busicode ='20000004' ";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getCreditConsumeByDate(String dateTime) {
		String sql = "select distinct t.ACCMEMBERID, t.ACCSETTLEDATE from t_txns_log t "
				+ "where t.ACCSETTLEDATE=? and t.ACCMEMBERID is not null and SUBSTR (trim(t.retcode),-2) = '00' and t.busicode='10000006'";
		return dao.query(sql, new Object[] { dateTime });
	}

	@Override
	public List<?> getSumCreditConsume(String memberId, String dateTime) {
		String sql = "select count(*) total,sum (t.amount) totalAmount,sum(t.txnfee) totalFee "
                + "from t_txns_log t  "
                + "where  t.ACCMEMBERID = ? and t.ACCSETTLEDATE=? and t.busicode = '10000006'"
                + " and SUBSTR(trim(t.retcode), -2) = '00'";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getCreditConsumeDetailByDate(String memberId, String dateTime) {
		String sql = "select t.ACCORDNO,t.TXNSEQNO,t.ACCORDCOMMITIME,t.ACCSETTLEDATE,t.amount,t.busicode,"
				+ "t.TXNFEE,t.PAYORDCOMTIME from t_txns_log t left join t_bnk_txn b on t.payordno=b.payordno "
				+ "where (b.status=9 or b.status is null) and t.ACCMEMBERID=? and t.ACCSETTLEDATE=? and "
				+ " SUBSTR (trim(t.retcode), -2) = '00'  and t.busicode ='10000006' ";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getCreditRefundByDate(String dateTime) {
		String sql = "select distinct t.ACCMEMBERID, t.ACCSETTLEDATE from t_txns_log t "
				+ "where t.ACCSETTLEDATE=? and t.ACCMEMBERID is not null and SUBSTR (trim(t.retcode),-2) = '00' and t.busicode='40000004'";
		return dao.query(sql, new Object[] { dateTime });
	}

	@Override
	public List<?> getSumCreditRefund(String memberId, String dateTime) {
		String sql = "select count(*) total,sum (t.amount) totalAmount,sum(t.txnfee) totalFee "
                + "from t_txns_log t  "
                + "where  t.ACCMEMBERID = ? and t.ACCSETTLEDATE=? and t.busicode = '40000004'"
                + " and SUBSTR(trim(t.retcode), -2) = '00'";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

	@Override
	public List<?> getCreditRefundDetailByDate(String memberId, String dateTime) {
		String sql = "select t.ACCORDNO,t.TXNSEQNO,t.ACCORDCOMMITIME,t.ACCSETTLEDATE,t.amount,t.busicode,"
				+ "t.TXNFEE,t.PAYORDCOMTIME,t.TXNSEQNO_OG from t_txns_log t left join t_bnk_txn b on t.payordno=b.payordno "
				+ "where (b.status=9 or b.status is null) and t.ACCMEMBERID=? and t.ACCSETTLEDATE=? and "
				+ " SUBSTR (trim(t.retcode), -2) = '00'  and t.busicode ='40000004' ";
		return dao.query(sql, new Object[] { memberId, dateTime });
	}

}
