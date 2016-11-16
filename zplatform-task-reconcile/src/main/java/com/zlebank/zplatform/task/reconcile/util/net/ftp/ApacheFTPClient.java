package com.zlebank.zplatform.task.reconcile.util.net.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class ApacheFTPClient extends AbstractFTPClient {

	private final static int DEFAULT_PROT = 21;
	private FTPClient ftp;
	private final static String FILE_DESCRIPTOR = "/";
	private final static String FTP_SUCCESS_CODE_PRE = "2";

	public ApacheFTPClient(String userName, String password, String serverIp) {
		super(userName, password, serverIp, DEFAULT_PROT);
	}

	public ApacheFTPClient(String userName, String password, String serverIp, int port) {
		super(userName, password, serverIp, port);
	}

	@Override
	public void upload(String uploadPath, String fileName, File file) throws IOException {
		ftp = new FTPClient();
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			ftp.connect(getSeverIp(), getPort());
			ftp.login(getUserName(), getPassword());

			changeWorkingDirectory(uploadPath);

			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.storeFile(fileName, in);
		} catch (IOException e) {
			throw e;
		} finally {
			if (in != null)
				in.close();
			ftp.logout();
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
	}

	@Override
	public void download(String downloadPath, String fileName, String targetDir, String targetName) throws IOException {
		ftp = new FTPClient();
		try {
			ftp.connect(getSeverIp(), getPort());
			ftp.login(getUserName(), getPassword());// 登录

			changeWorkingDirectory(downloadPath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File _targetDir = new File(targetDir);
					if (!_targetDir.exists()) {
						_targetDir.mkdirs();
					}

					File localFile = new File(targetDir + "/" + targetName);
					if (!localFile.exists()) {
						localFile.createNewFile();
					}
					ftp.setFileType(FTP.BINARY_FILE_TYPE);
					OutputStream os = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), os);
					os.close();
				}
			}
			ftp.logout();
		} catch (IOException e) {
			throw e;
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
	}

	/**
	 * change work directory from ftp root path to given path by parameter
	 * 
	 * @param path
	 * @throws IOException
	 */
	private void changeWorkingDirectory(String path) throws IOException {
		if (!path.startsWith(FILE_DESCRIPTOR)) {
			throw new RuntimeException("uploadPath must start with character '/'");
		}

		String accuratePath = path;
		if (!path.equals(FILE_DESCRIPTOR) && path.endsWith(FILE_DESCRIPTOR)) {
			accuratePath = path.substring(0, path.length() - 2);
		}

		String[] pathHierarchy = accuratePath.split(FILE_DESCRIPTOR);
		if (pathHierarchy.length != 0) {
			for (int i = 1; i < pathHierarchy.length; i++) {
				String directyory = pathHierarchy[i];
				if (!ftp.changeWorkingDirectory(directyory)) {
					ftp.makeDirectory(directyory);
					int reply = ftp.getReplyCode();
					if (!String.valueOf(reply).startsWith(FTP_SUCCESS_CODE_PRE)) {
						throw new IOException(
								"make directory error.upload path:" + path + " failed.ftp replay code:" + reply);
					}
					ftp.changeWorkingDirectory(directyory);
				}
			}
		}
	}
}
