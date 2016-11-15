package com.zlebank.zplatform.task.reconcile.util.net;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zlebank.zplatform.task.reconcile.service.TxnsLogReconService;
import com.zlebank.zplatform.task.reconcile.util.net.ftp.AbstractFTPClient;
import com.zlebank.zplatform.task.reconcile.util.net.ftp.ApacheFTPClient;

import net.sf.json.JSONObject;

/**
 * {@link AbstractFTPClient} client Factory. <br>
 * It create instance of {@link AbstractFTPClient}. It is not a singleton in
 * design pattern,but it should be a bean of spring and it's a "singleton" which
 * assured by spring.
 */
@Component
public class FTPClientFactory {

	private final String serverName = "FTP_SERVER_PRODUCT";
	private final String module = "MANAGER";

	@Autowired
	private TxnsLogReconService txnsLogReconService;

	public AbstractFTPClient getFtpClient() {
		Object object = txnsLogReconService.getFtpConfigByNameAndModule(serverName, module).get(0);
		JSONObject obj = JSONObject.fromObject(object);
		int port = Integer.parseInt(obj.get("PORT").toString());
		AbstractFTPClient ftpClient = new ApacheFTPClient(obj.get("USERS").toString(), obj.get("PWD").toString(),
				obj.get("IP").toString(), port);
		return ftpClient;
	}

	// 民生实时代付的退汇文件地址和路径
	@SuppressWarnings("unchecked")
	public AbstractFTPClient getFtpClient1(String memberId) {
		List<Map<String, Object>> allConfig = (List<Map<String, Object>>) txnsLogReconService
				.getFtpUploadAddress(memberId);
		String ip = allConfig.get(0).get("IP").toString();
		int port = Integer.parseInt(allConfig.get(0).get("PORT").toString());
		String username = allConfig.get(0).get("USER_NAME").toString();
		String password = allConfig.get(0).get("PASSWORD").toString();
		AbstractFTPClient ftpClient = new ApacheFTPClient(username, password, ip, port);
		return ftpClient;
	}

}
