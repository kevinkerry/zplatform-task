package com.zlebank.zplatform.task.reconcile.service;

import java.util.List;

public interface TxnsLogReconService {

	public List<?> getAllMemberByDate(String date);

	public List<?> getAllMemberByDateByCharge(String date);

	public List<?> getSumExpense(String memberId, String date);

	public List<?> getSumRefund(String memberId, String date);

	public List<?> getAllMemberDetailedByDate(String memberId, String date);

	public List<?> getFtpUploadAddress(String memberId);

	public List<?> getFtpConfigByNameAndModule(String serverName, String module);

	public List<?> getBondMemberByDate(String date);

	public List<?> getSumBond(String memberId, String date);

	public List<?> getBondByDate(String memberId, String date);

	public List<?> geTransferAccountMemberByDate(String date);

	public List<?> getSumTransferAccount(String memberId, String date);

	public List<?> getTransferAccountByDate(String memberId, String date);

	public List<?> getInsteadMemberByDate(String date);

	public List<?> getSumInstead(String memberId, String date);

	public List<?> getInsteadMerchantDetailedByDate(String memberId, String date);

	public List<?> getSinglepaymentMemberByDate(String dateTime);

	public List<?> getSumSinglepayment(String memberId, String dateTime);

	public List<?> getSinglepaymentDetailByDate(String memberId, String dateTime);

	public List<?> getSingleInsteadMemberByDate(String dateTime);

	public List<?> getSumSingleInstead(String memberId, String dateTime);

	public List<?> getSingleInsteadDetailedByDate(String memberId, String dateTime);

	public List<?> getBatchpaymentMemberByDate(String dateTime);

	public List<?> getSumBatchpayment(String memberId, String dateTime);

	public List<?> getBatchpaymentDetailedByDate(String memberId, String dateTime);

}
