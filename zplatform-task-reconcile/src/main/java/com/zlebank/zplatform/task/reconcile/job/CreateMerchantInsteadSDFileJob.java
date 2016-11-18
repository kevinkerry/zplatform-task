package com.zlebank.zplatform.task.reconcile.job;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zlebank.zplatform.task.reconcile.service.TxnsLogReconService;
import com.zlebank.zplatform.task.reconcile.util.net.FTPClientFactory;
import com.zlebank.zplatform.task.reconcile.util.net.ftp.AbstractFTPClient;
import com.zlebank.zplatform.task.util.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class CreateMerchantInsteadSDFileJob {

	private static final Log log = LogFactory.getLog(CreateMerchantInsteadSDFileJob.class);

	private static final String FILE_PREX = "ZLMIP";
	private static final String FILE_PREX1 = "ZLMTH";
	private static final String FILE_PREX2 = "DF_ZLJR_THWJ_";
	private static final String MERCH_ID = "merchId";
	private static final String DATE = "date";
	private static final String TOTAL_COUNT = "totalCount";
	private static final String TOTAL_AMOUNT = "totalAmount";
	private static final String TOTAL_FEE = "totalFee";
	private static final String DELETIMER = "|";
	private static final String RECON_FILE_ROOT_DIR = "/memberrecon/";
	private static final String RECON_FILE_LOCAL_ROOT_DIR = "/home/web/recon_temp";

	@Autowired
	private TxnsLogReconService txnsLogReconService;
	@Autowired
	private FTPClientFactory ftpClientFactory;

	// 批量代付对账文件
	public void execute() throws Exception {
		// 取出网络时间
		Date date = new Date(System.currentTimeMillis());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		String dateTime = sdf.format(date);
		// String dateTime = "20160609";
		// 根据日期取出t_txns_log取出当天清算的所有商户号
		List<?> memberList = txnsLogReconService.getInsteadMemberByDate(dateTime);

		JSONArray jsonArray = JSONArray.fromObject(memberList);
		JSONObject job = null;
		Map<String, String> memIDMap = new HashMap<String, String>();
		for (int i = 0; i < jsonArray.size(); i++) {
			job = jsonArray.getJSONObject(i);
			memIDMap.put(job.get("ACCSECMERNO").toString(), job.get("ACCSECMERNO").toString());
		}
		StringBuilder fileBuffer = new StringBuilder();
		// 遍历当天所有的商户
		for (Map.Entry<String, String> entry : memIDMap.entrySet()) {
			String memberId = entry.getKey();
			fileBuffer.setLength(0);
			fileBuffer.append(MERCH_ID);
			fileBuffer.append(":");
			fileBuffer.append(memberId);
			fileBuffer.append(DELETIMER).append(DATE).append(":" + dateTime);
			// 代付汇总信息
			List<?> sumInsteadList = txnsLogReconService.getSumInstead(memberId, dateTime);

			// 总交易笔数
			int count = 0;
			// 总清算金额
			Long totalAmount = 0L;
			// 总手续费
			Long sumFee = 0L;
			if (sumInsteadList != null && sumInsteadList.size() > 0) {
				JSONArray json = JSONArray.fromObject(sumInsteadList);
				count += Integer.parseInt(json.getJSONObject(0).get("TOTAL").equals("null") ? "0"
						: json.getJSONObject(0).get("TOTAL").toString());
				totalAmount += Long.parseLong(json.getJSONObject(0).get("TOTALAMOUNT").equals("null") ? "0"
						: json.getJSONObject(0).get("TOTALAMOUNT").toString());
				sumFee += Long.parseLong(json.getJSONObject(0).get("TOTALFEE").equals("null") ? "0"
						: json.getJSONObject(0).get("TOTALFEE").toString());
			}

			fileBuffer.append("\n");
			fileBuffer.append(TOTAL_COUNT).append(":").append(count).append(DELETIMER).append(TOTAL_AMOUNT).append(":")
					.append(totalAmount).append(DELETIMER).append(TOTAL_FEE).append(":").append(sumFee);
			List<?> memberDetailedList = txnsLogReconService.getInsteadMerchantDetailedByDate(memberId, dateTime);
			JSONArray detailJsonArray = JSONArray.fromObject(memberDetailedList);
			for (int i = 0; i < detailJsonArray.size(); i++) {
				long amount = 0;
				long fee = 0;
				long settAmount = 0;
				job = detailJsonArray.getJSONObject(i);
				amount = Long.valueOf(job.get("AMOUNT").toString());
				fee = StringUtils.isEmpty(job.get("TXNFEE").toString()) ? 0
						: Long.valueOf(job.get("TXNFEE").toString());
				settAmount = amount + fee;
				fileBuffer.append("\n");
				fileBuffer.append(job.get("ACCORDNO").toString()).append(DELETIMER)
						.append(job.get("TXNSEQNO").toString()).append(DELETIMER).append(job.get("ACCORDCOMMITIME"))
						.append(DELETIMER).append(job.get("BUSICODE").toString())
						.append(DELETIMER).append(amount).append(DELETIMER).append(fee).append(DELETIMER)
						.append(settAmount);
			}
			fileBuffer.append("\n");
			fileBuffer.append("######");
			String filePath = RECON_FILE_LOCAL_ROOT_DIR + "/" + memberId + "/";
			String fileName = FILE_PREX + "_" + memberId + "_" + dateTime + ".txt";
			doPrint(fileBuffer.toString(), filePath, fileName, memberId);
		}
	}

	// 单笔代付对账文件
	public void execute1() throws Exception {
		// 取出网络时间
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateTime = sdf.format(date);
		// 根据日期取出t_txns_log取出当天清算的所有商户号
		List<?> memberList = txnsLogReconService.getSinglepaymentMemberByDate(dateTime);

		JSONArray jsonArray = JSONArray.fromObject(memberList);
		JSONObject job = null;
		Map<String, String> memIDMap = new HashMap<String, String>();
		for (int i = 0; i < jsonArray.size(); i++) {
			job = jsonArray.getJSONObject(i);
			memIDMap.put(job.get("ACCSECMERNO").toString(), job.get("ACCSECMERNO").toString());
		}
		StringBuilder fileBuffer = new StringBuilder();
		// 遍历当天所有的商户
		for (Map.Entry<String, String> entry : memIDMap.entrySet()) {
			String memberId = entry.getKey();
			fileBuffer.setLength(0);
			fileBuffer.append(MERCH_ID);
			fileBuffer.append(":");
			fileBuffer.append(memberId);
			fileBuffer.append(DELETIMER).append(DATE).append(":" + dateTime);
			// 代付汇总信息
			List<?> sumSinglePaymentList = txnsLogReconService.getSumSinglepayment(memberId, dateTime);

			// 总交易笔数
			int count = 0;
			// 总清算金额
			Long totalAmount = 0L;
			// 总手续费
			Long sumFee = 0L;
			if (sumSinglePaymentList != null && sumSinglePaymentList.size() > 0) {
				JSONArray json = JSONArray.fromObject(sumSinglePaymentList);
				count += Integer.parseInt(json.getJSONObject(0).get("TOTAL").equals("null") ? "0"
						: json.getJSONObject(0).get("TOTAL").toString());
				totalAmount += Long.parseLong(json.getJSONObject(0).get("TOTALAMOUNT").equals("null") ? "0"
						: json.getJSONObject(0).get("TOTALAMOUNT").toString());
				sumFee += Long.parseLong(json.getJSONObject(0).get("TOTALFEE").equals("null") ? "0"
						: json.getJSONObject(0).get("TOTALFEE").toString());
			}

			fileBuffer.append("\n");
			fileBuffer.append(TOTAL_COUNT).append(":").append(count).append(DELETIMER).append(TOTAL_AMOUNT).append(":")
					.append(totalAmount).append(DELETIMER).append(TOTAL_FEE).append(":").append(sumFee);

			List<?> memberDetailedList = txnsLogReconService.getSinglepaymentDetailByDate(memberId, dateTime);
			JSONArray detailJsonArray = JSONArray.fromObject(memberDetailedList);
			for (int i = 0; i < detailJsonArray.size(); i++) {
				long amount = 0;
				long fee = 0;
				long settAmount = 0;
				job = detailJsonArray.getJSONObject(i);
				amount = Long.valueOf(job.get("AMOUNT").toString());
				fee = StringUtils.isEmpty(job.get("TXNFEE").toString()) ? 0
						: Long.valueOf(job.get("TXNFEE").toString());
				settAmount = amount + fee;
				fileBuffer.append("\n");
				fileBuffer.append(job.get("ACCORDNO").toString()).append(DELETIMER)
						.append(job.get("TXNSEQNO").toString()).append(DELETIMER).append(job.get("ACCORDCOMMITIME"))
						.append(DELETIMER).append(job.get("BUSICODE").toString())
						.append(DELETIMER).append(amount).append(DELETIMER).append(fee).append(DELETIMER)
						.append(settAmount);
			}
			fileBuffer.append("\n");
			fileBuffer.append("######");
			String filePath = RECON_FILE_LOCAL_ROOT_DIR + "/" + memberId + "/";
			String fileName = FILE_PREX1 + "_" + memberId + "_" + dateTime + ".txt";
			doPrint(fileBuffer.toString(), filePath, fileName, memberId);
		}
	}

	public void doPrint(String fileString, String path, String fileName, String memberId) {
		string2File(fileString, path + fileName);
		File file = new File(path + fileName);
		AbstractFTPClient ftpClient = ftpClientFactory.getFtpClient();
		try {
			ftpClient.upload(RECON_FILE_ROOT_DIR + memberId, fileName, file);
		} catch (IOException e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
			}
			log.warn("upload to ftp get a exception.caused by:" + e.getMessage());
		}
	}

	public boolean string2File(String res, String filePath) {
		boolean flag = true;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			File distFile = new File(filePath);
			if (!distFile.getParentFile().exists())
				distFile.getParentFile().mkdirs();
			bufferedReader = new BufferedReader(new StringReader(res));
			bufferedWriter = new BufferedWriter(new FileWriter(distFile));
			char buf[] = new char[1024]; // 字符缓冲区
			int len;
			while ((len = bufferedReader.read(buf)) != -1) {
				bufferedWriter.write(buf, 0, len);
			}
			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
			return flag;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	// 单笔实时代付的退汇文件上传到对方的ftp上
	public void execute2() throws Exception {
		// 取出网络时间
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateTime = sdf.format(date);
		// String dateTime ="20161018";
		// 根据日期取出t_txns_log取出当天清算的所有商户号
		List<?> memberList = txnsLogReconService.getSingleInsteadMemberByDate(dateTime);

		JSONArray jsonArray = JSONArray.fromObject(memberList);
		JSONObject job = null;
		Map<String, String> memIDMap = new HashMap<String, String>();
		for (int i = 0; i < jsonArray.size(); i++) {
			job = jsonArray.getJSONObject(i);
			memIDMap.put(job.get("MER_ID").toString(), job.get("MER_ID").toString());
		}

		StringBuilder fileBuffer = new StringBuilder();
		// 遍历当天所有的商户
		for (Map.Entry<String, String> entry : memIDMap.entrySet()) {
			String memberId = entry.getKey();
			fileBuffer.setLength(0);
			fileBuffer.append(MERCH_ID);
			fileBuffer.append(":");
			fileBuffer.append(memberId);
			fileBuffer.append(DELETIMER).append(DATE).append(":" + dateTime);
			// 代付汇总信息
			List<?> sumInsteadList = txnsLogReconService.getSumSingleInstead(memberId, dateTime);

			// 总交易笔数
			int count = 0;
			// 总清算金额
			Long totalAmount = 0L;

			if (sumInsteadList != null && sumInsteadList.size() > 0) {
				JSONArray json = JSONArray.fromObject(sumInsteadList);
				count += Integer.parseInt(json.getJSONObject(0).get("TOTAL").equals("null") ? "0"
						: json.getJSONObject(0).get("TOTAL").toString());
				totalAmount += Long.parseLong(json.getJSONObject(0).get("TOTALAMOUNT").equals("null") ? "0"
						: json.getJSONObject(0).get("TOTALAMOUNT").toString());
			}
			fileBuffer.append("\n");
			fileBuffer.append(TOTAL_COUNT).append(":").append(count).append(DELETIMER).append(TOTAL_AMOUNT).append(":")
					.append(totalAmount);
			// 退汇文件内容：文件内容以|分割
			// merId:（商户号）|date:(退汇文件生成日期YYYYMMDD）
			// totalCount:(总笔数)|totalAmount:(总金额)|totalFee:(手续费)
			// 商户订单号(商户退款订单号)|支付流水号|订单支付时间(yyyyMMddHHmmss)|交易代码|订单金额(单位：分)|手续费(单位：分)|清算金额(单位：分)|失败返回码|失败原因|退汇日期
			// ######(文件结束标识)

			List<?> memberDetailedList = txnsLogReconService.getSingleInsteadDetailedByDate(memberId, dateTime);
			JSONArray detailJsonArray = JSONArray.fromObject(memberDetailedList);
			for (int i = 0; i < detailJsonArray.size(); i++) {
				long amount = 0;
				job = detailJsonArray.getJSONObject(i);
				amount = Long.valueOf(job.get("TRANS_AMT").toString());
				fileBuffer.append("\n");
				fileBuffer.append(job.get("ORDERNO").toString()).append(DELETIMER)
						.append(job.get("TXNSEQNO").toString()).append(DELETIMER).append(job.get("ORDER_COMMI_TIME"))
						.append(DELETIMER).append("70000002").append(DELETIMER)
						.append(amount).append(DELETIMER).append(job.get("RESP_CODE")).append(DELETIMER)
						.append(job.get("RESP_MSG")).append(DELETIMER).append(job.get("RETURN_DATE"));
			}
			fileBuffer.append("\n");
			fileBuffer.append("######");
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> allConfig = (List<Map<String, Object>>) txnsLogReconService
					.getFtpUploadAddress(memberId);
			String path = "/" + allConfig.get(0).get("PATH").toString();
			// ZLMTH_商户号_YYYYMMDD.txt，其中YYYYMMDD代表退汇文件生成日期，格式为年（4位）月（2位）日（2位）。
			// 如：ZLMTH_200000000000593_20160308.txt
			String fileName = FILE_PREX1 + "_" + memberId + "_" + dateTime + ".txt";
			doPrint2(fileBuffer.toString(), path, fileName, memberId);
		}
	}

	// 批量实时代付的退汇文件上传到对方的ftp上
	public void execute3() throws Exception {
		// 取出网络时间
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateTime = sdf.format(date);
		// String dateTime ="20161018";
		// 根据日期取出t_txns_log取出当天清算的所有商户号
		List<?> memberList = txnsLogReconService.getBatchpaymentMemberByDate(dateTime);

		JSONArray jsonArray = JSONArray.fromObject(memberList);
		JSONObject job = null;
		Map<String, String> memIDMap = new HashMap<String, String>();
		for (int i = 0; i < jsonArray.size(); i++) {
			job = jsonArray.getJSONObject(i);
			memIDMap.put(job.get("MER_ID").toString(), job.get("MER_ID").toString());
		}

		StringBuilder fileBuffer = new StringBuilder();
		// 遍历当天所有的商户
		for (Map.Entry<String, String> entry : memIDMap.entrySet()) {
			String memberId = entry.getKey();
			fileBuffer.setLength(0);
			fileBuffer.append(MERCH_ID);
			fileBuffer.append(":");
			fileBuffer.append(memberId);
			fileBuffer.append(DELETIMER).append(DATE).append(":" + dateTime);
			// 代付汇总信息
			List<?> sumInsteadList = txnsLogReconService.getSumBatchpayment(memberId, dateTime);

			// 总交易笔数
			int count = 0;
			// 总清算金额
			Long totalAmount = 0L;

			if (sumInsteadList != null && sumInsteadList.size() > 0) {
				JSONArray json = JSONArray.fromObject(sumInsteadList);
				count += Integer.parseInt(json.getJSONObject(0).get("TOTAL").equals("null") ? "0"
						: json.getJSONObject(0).get("TOTAL").toString());
				totalAmount += Long.parseLong(json.getJSONObject(0).get("TOTALAMOUNT").equals("null") ? "0"
						: json.getJSONObject(0).get("TOTALAMOUNT").toString());
			}
			fileBuffer.append("\n");
			fileBuffer.append(TOTAL_COUNT).append(":").append(count).append(DELETIMER).append(TOTAL_AMOUNT).append(":")
					.append(totalAmount);
			// 退汇文件内容：文件内容以|分割
			// merId:（商户号）|date:(退汇文件生成日期YYYYMMDD）
			// totalCount:(总笔数)|totalAmount:(总金额)|totalFee:(手续费)
			// 商户订单号(商户退款订单号)|支付流水号|订单支付时间(yyyyMMddHHmmss)|交易代码|订单金额(单位：分)|手续费(单位：分)|清算金额(单位：分)|失败返回码|失败原因|退汇日期
			// ######(文件结束标识)

			List<?> memberDetailedList = txnsLogReconService.getBatchpaymentDetailedByDate(memberId, dateTime);
			JSONArray detailJsonArray = JSONArray.fromObject(memberDetailedList);
			for (int i = 0; i < detailJsonArray.size(); i++) {
				long amount = 0;
				job = detailJsonArray.getJSONObject(i);
				amount = Long.valueOf(job.get("AMT").toString());
				fileBuffer.append("\n");
				fileBuffer.append(job.get("ORDER_ID").toString()).append(DELETIMER)
						.append(job.get("TXNSEQNO").toString()).append(DELETIMER).append(job.get("TXN_TIME"))
						.append(DELETIMER).append("70000001").append(DELETIMER)
						.append(amount).append(DELETIMER).append(job.get("RESP_CODE")).append(DELETIMER)
						.append(job.get("RESP_MSG")).append(DELETIMER).append(job.get("RETUR_NDATE"));
			}
			fileBuffer.append("\n");
			fileBuffer.append("######");
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> allConfig = (List<Map<String, Object>>) txnsLogReconService
					.getFtpUploadAddress(memberId);
			String path = "/" + allConfig.get(0).get("PATH").toString();
			// ZLMTH_商户号_YYYYMMDD.txt，其中YYYYMMDD代表退汇文件生成日期，格式为年（4位）月（2位）日（2位）。
			// 如：ZLMTH_200000000000593_20160308.txt
			String fileName = FILE_PREX1 + "_" + memberId + "_" + dateTime + ".txt";
			doPrint2(fileBuffer.toString(), path, fileName, memberId);
		}
	}

	public void doPrint2(String fileString, String path, String fileName, String memberId) {
		string2File(fileString, path);
		File file = new File(path);
		AbstractFTPClient ftpClient = ftpClientFactory.getFtpClient1(memberId);
		try {
			ftpClient.upload(path, fileName, file);
		} catch (IOException e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
			}
			log.warn("upload to ftp get a exception.caused by:" + e.getMessage());
		}
	}

}
