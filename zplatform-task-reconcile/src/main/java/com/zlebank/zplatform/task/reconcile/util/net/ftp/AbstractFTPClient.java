package com.zlebank.zplatform.task.reconcile.util.net.ftp;

import java.io.File;
import java.io.IOException;

public abstract class AbstractFTPClient {
	private String userName;
	private String password;
	private String severIp;
	private int port;

	/**
	 * upload the file to ftp server
	 * 
	 * @param uploadPath
	 *            the path where the file upload to.It's a directory,if it not
	 *            exist,will create it.
	 * @param fileName
	 *            the file name save in the server.
	 * @param file
	 *            the file to upload.
	 */
	abstract public void upload(String uploadPath, String fileName, File file) throws IOException;

	/**
	 * download a file to local path
	 * 
	 * @param downloadPath
	 *            the source file path in ftp server
	 * @param fileName
	 *            the source file name in ftp server
	 * @param targetDir
	 *            the directory put the file in local path
	 * @param targetName
	 *            the file name in local path
	 */
	abstract public void download(String downloadPath, String fileName, String targetDir, String targetName)
			throws IOException;

	protected AbstractFTPClient(String userName, String password, String serverIp, int port) {
		this.userName = userName;
		this.password = password;
		this.severIp = serverIp;
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getSeverIp() {
		return severIp;
	}

	public int getPort() {
		return port;
	}
}
