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

}
