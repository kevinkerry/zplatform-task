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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class CreateMemberFileJob {

	private static final Log log = LogFactory.getLog(CreateMemberFileJob.class);

	private static final String FILE_PREX = "ZLMSD";
	private static final String DELETIMER = "|";
	private static final String RECON_FILE_ROOT_DIR = "/memberrecon/";
	private static final String RECON_FILE_LOCAL_ROOT_DIR = "/home/web/recon_temp";

	@Autowired
	private TxnsLogReconService txnsLogReconService;
	@Autowired
	private FTPClientFactory ftpClientFactory;

	public void execute() throws Exception {
		// 取出网络时间
		Date date = new Date(System.currentTimeMillis());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateTime = sdf.format(date);
		// String dateTime ="20161012";
		// 根据日期取出t_txns_log取出当天清算的所有商户号
		List<?> memberList = txnsLogReconService.getAllMemberByDate(dateTime);
		List<?> memberListA = txnsLogReconService.getAllMemberByDateByCharge(dateTime);
		JSONArray jsonArray = JSONArray.fromObject(memberList);
		JSONArray jsonArrayA = JSONArray.fromObject(memberListA);
		JSONObject job = null;
		Map<String, String> memIDmap = new HashMap<String, String>();
		for (int i = 0; i < jsonArray.size(); i++) {
			job = jsonArray.getJSONObject(i);
			memIDmap.put(job.get("ACCSECMERNO").toString(), job.get("ACCSECMERNO").toString());
		}
		for (int i = 0; i < jsonArrayA.size(); i++) {
			job = jsonArrayA.getJSONObject(i);
			memIDmap.put(job.get("MEMBER_ID").toString(), job.get("MEMBER_ID").toString());
		}
		StringBuffer fileBuffer = new StringBuffer();
		// 遍历当天所有的商户
		for (Map.Entry<String, String> entry : memIDmap.entrySet()) {
			String memberId = entry.getKey();
			fileBuffer.setLength(0);
			fileBuffer.append("memberId:" + memberId + "|date:" + dateTime);
			// 消费(账户，快捷)汇总信息;商户账户资金增加
			List<?> sumCoumList = txnsLogReconService.getSumExpense(memberId, dateTime);
			// 退款，商户资金减少
			List<?> sumReList = txnsLogReconService.getSumRefund(memberId, dateTime);

			// 总交易笔数
			int count = 0;
			// 总清算金额
			Long totalAmount = 0L;
			// 总手续费
			Long sumFee = 0L;
			if (sumCoumList != null && sumCoumList.size() > 0) {
				JSONArray json = JSONArray.fromObject(sumCoumList);
				count += Integer.parseInt(json.getJSONObject(0).get("TOTAL").equals("null") ? "0"
						: json.getJSONObject(0).get("TOTAL").toString());
				totalAmount += Long.parseLong(json.getJSONObject(0).get("TOTALAMOUNT").equals("null") ? "0"
						: json.getJSONObject(0).get("TOTALAMOUNT").toString());
				sumFee += Long.parseLong(json.getJSONObject(0).get("TOTALFEE").equals("null") ? "0"
						: json.getJSONObject(0).get("TOTALFEE").toString());
			}
			if (sumReList != null && sumReList.size() > 0) {
				JSONArray json = JSONArray.fromObject(sumReList);
				count += Integer.parseInt(json.getJSONObject(0).get("TOTAL").equals("null") ? "0"
						: json.getJSONObject(0).get("TOTAL").toString());
				totalAmount -= Long.parseLong(json.getJSONObject(0).get("TOTALAMOUNT").equals("null") ? "0"
						: json.getJSONObject(0).get("TOTALAMOUNT").toString());
				sumFee += Long.parseLong(json.getJSONObject(0).get("TOTALFEE").equals("null") ? "0"
						: json.getJSONObject(0).get("TOTALFEE").toString());
			}
			fileBuffer.append("\n");
			fileBuffer.append("totalCount:" + count + "|totalAmount:" + totalAmount + "|totalFee:" + sumFee);
			List<?> memberDetailedList = txnsLogReconService.getAllMemberDetailedByDate(memberId, dateTime);
			JSONArray detailJsonArray = JSONArray.fromObject(memberDetailedList);
			for (int i = 0; i < detailJsonArray.size(); i++) {
				long amount = 0;
				long fee = 0;
				long settAmount = 0;
				job = detailJsonArray.getJSONObject(i);
				amount = Long.valueOf(job.get("AMOUNT").toString());
				fee = Long.valueOf(job.get("TXNFEE").toString());

				fileBuffer.append("\n");
				fileBuffer.append(job.get("ACCORDNO").toString()).append(DELETIMER)
						.append(job.get("TXNSEQNO").toString()).append(DELETIMER).append(job.get("ACCORDCOMMITIME"))
						.append(DELETIMER).append(job.get("BUSICODE").toString()).append(DELETIMER);
				// 注：日明细对账文件，包括商户所有资金交易。不需要商户收付款标志C或者D，需要更新的是文档
				if ("'30000001', '40000001','40000002','40000003','40000004'"
						.contains(job.get("BUSICODE").toString())) {
					settAmount = amount + fee;
					fileBuffer.append(amount).append(DELETIMER).append(fee).append(DELETIMER).append(settAmount)
							.append(DELETIMER).append(job.get("TXNSEQNO_OG").toString());
				}
				if ("'10000001', '10000002','10000005','10000006','11000001'"
						.contains(job.get("BUSICODE").toString())) {
					settAmount = amount - fee;
					fileBuffer.append(amount).append(DELETIMER).append(fee).append(DELETIMER).append(settAmount);
				}
			}
			fileBuffer.append("\n");
			fileBuffer.append("######");
			System.out.println(fileBuffer.toString());
			doPrint(fileBuffer.toString(), RECON_FILE_LOCAL_ROOT_DIR + "/" + memberId + "/",
					FILE_PREX + "_" + memberId + "_" + dateTime + ".txt", memberId);
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

}
