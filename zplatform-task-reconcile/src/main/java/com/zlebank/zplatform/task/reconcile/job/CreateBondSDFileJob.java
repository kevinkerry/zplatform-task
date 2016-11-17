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
public class CreateBondSDFileJob {

	private static final Log log = LogFactory.getLog(CreateBondSDFileJob.class);

	private static final String FILE_PREX = "BOND";
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

	public void execute() throws Exception {
		// 取出网络时间
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateTime = sdf.format(date);

		// 根据日期取出t_txns_log取出当天所有的保证金商户
		List<?> memberList = txnsLogReconService.getBondMemberByDate(dateTime);

		JSONArray jsonArray = JSONArray.fromObject(memberList);
		JSONObject job = null;
		Map<String, String> memIDMap = new HashMap<String, String>();
		for (int i = 0; i < jsonArray.size(); i++) {
			job = jsonArray.getJSONObject(i);
			memIDMap.put(job.get("ACCSECMERNO").toString(), job.get("ACCSECMERNO").toString());
		}
		StringBuilder fileBuffer = new StringBuilder();
		for (Map.Entry<String, String> entry : memIDMap.entrySet()) {
			String memberId = entry.getKey();
			fileBuffer.setLength(0);
			fileBuffer.append(MERCH_ID);
			fileBuffer.append(":");
			fileBuffer.append(memberId);
			fileBuffer.append(DELETIMER).append(DATE).append(":" + dateTime);

			// 保证金汇总信息
			List<?> sumBondList = txnsLogReconService.getSumBond(memberId, dateTime);

			// 总交易笔数
			int count = 0;
			// 总清算金额
			Long totalAmount = 0L;
			// 总手续费
			Long sumFee = 0L;
			if (sumBondList != null && sumBondList.size() > 0) {
				JSONArray json = JSONArray.fromObject(sumBondList);
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
			List<?> bondList = txnsLogReconService.getBondByDate(memberId, dateTime);
			JSONArray detailJsonArray = JSONArray.fromObject(bondList);
			for (int i = 0; i < detailJsonArray.size(); i++) {
				long amount = 0;
				long fee = 0;
				long settAmount = 0;
				job = detailJsonArray.getJSONObject(i);
				amount = Long.valueOf(job.get("AMOUNT").toString());
				fee = StringUtils.isEmpty(job.get("TXNFEE").toString()) ? 0
						: Long.valueOf(job.get("TXNFEE").toString());
				// 保证金线上充值、线下充值
				if ("'20000002', '20000003'".contains(job.get("BUSICODE").toString())) {
					settAmount = amount + fee;
				} else if (job.get("BUSICODE").toString().equals("50000002")) {
					// 保证金提取
					settAmount = amount - fee;
				}
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
