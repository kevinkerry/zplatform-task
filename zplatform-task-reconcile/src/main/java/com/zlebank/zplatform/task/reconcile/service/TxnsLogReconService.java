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

	public List<?> getIndustryRechargeByDate(String dateTime);

	public List<?> getSumIndustryRecharge(String memberId, String dateTime);

	public List<?> getIndustryRechargeDetailByDate(String memberId, String dateTime);

	public List<?> getIndustryConsumeByDate(String dateTime);

	public List<?> getSumIndustryConsume(String memberId, String dateTime);

	public List<?> getIndustryConsumeDetailByDate(String memberId, String dateTime);

	public List<?> getIndustryDrawByDate(String dateTime);

	public List<?> getSumIndustryDraw(String memberId, String dateTime);

	public List<?> getIndustryDrawDetailByDate(String memberId, String dateTime);

	public List<?> getIndustryTransferByDate(String dateTime);

	public List<?> getSumIndustryTransfer(String memberId, String dateTime);

	public List<?> getIndustryTransferDetailByDate(String memberId, String dateTime);

	public List<?> getIndustryRefundByDate(String dateTime);

	public List<?> getSumIndustryRefund(String memberId, String dateTime);

	public List<?> getIndustryRefundDetailByDate(String memberId, String dateTime);

	public List<?> getCreditRechargeByDate(String dateTime);

	public List<?> getSumCreditRecharge(String memberId, String dateTime);

	public List<?> getCreditRechargeDetailByDate(String memberId, String dateTime);

	public List<?> getCreditConsumeByDate(String dateTime);

	public List<?> getSumCreditConsume(String memberId, String dateTime);

	public List<?> getCreditConsumeDetailByDate(String memberId, String dateTime);

	public List<?> getCreditRefundByDate(String dateTime);

	public List<?> getSumCreditRefund(String memberId, String dateTime);

	public List<?> getCreditRefundDetailByDate(String memberId, String dateTime);

}
