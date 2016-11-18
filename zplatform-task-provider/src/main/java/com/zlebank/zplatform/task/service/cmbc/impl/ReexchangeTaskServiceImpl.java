/* 
 * ReexchangeTaskServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年10月25日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.service.cmbc.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.google.common.base.Charsets;
import com.zlebank.zplatform.cmbc.producer.InsteadPayProducer;
import com.zlebank.zplatform.cmbc.producer.enums.InsteadPayTagsEnum;
import com.zlebank.zplatform.cmbc.producer.interfaces.Producer;
import com.zlebank.zplatform.task.bean.SingleReexchangeBean;
import com.zlebank.zplatform.task.common.utils.Constant;
import com.zlebank.zplatform.task.common.utils.DateUtil;
import com.zlebank.zplatform.task.dao.CmbcResfileLogDAO;
import com.zlebank.zplatform.task.dao.DownloadLogDAO;
import com.zlebank.zplatform.task.pojo.PojoCmbcResfileLog;
import com.zlebank.zplatform.task.pojo.PojoDownloadLog;
import com.zlebank.zplatform.task.service.cmb.ReexchangeTaskService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月25日 下午2:44:47
 * @since 
 */
@Service("reexchangeTaskService")
public class ReexchangeTaskServiceImpl implements ReexchangeTaskService{
	 private static final Log log = LogFactory.getLog(ReexchangeTaskServiceImpl.class);
	    private final static String FTPIP = Constant.getInstance().getCmbc_single_insteadpay_ftp_ip();
	    private final static String USER = Constant.getInstance().getCmbc_single_insteadpay_ftp_user();
	    private final static String PWD = Constant.getInstance().getCmbc_single_insteadpay_ftp_pwd();
	    private final static int port = Constant.getInstance().getCmbc_single_insteadpay_ftp_port();
	    private static final String TARGETPATH = Constant.getInstance().getCmbc_download_file_path();
	    @Autowired
	    private DownloadLogDAO downloadLogDAO;
	    @Autowired
	    private CmbcResfileLogDAO cmbcResfileLogDAO;
	/**
	 *
	 */
	@Override
	public void scanCMBCReexchange() {
		
		String path = "TH_ZLJR";
		List<String> fileNameList = scanningCMBCRESFile(path);
		for (String fileName : fileNameList) {
            PojoDownloadLog downloadLog = downloadLogDAO.getLogByFileName(fileName);
            if(downloadLog!=null){
                if("00".equals(downloadLog.getRecode())){
                    continue;
                }
                downloadLog.setDownloadcount(downloadLog.getDownloadcount()+1);
                downloadLogDAO.update(downloadLog);
            }else{
                downloadLog = new PojoDownloadLog();
                downloadLog.setFilename(fileName);
                downloadLog.setDownloadcount(1);
                downloadLog.setDownloadtime(DateUtil.getCurrentDateTime());
                downloadLog.setCaid("93000001");
                downloadLog.setCaname("民生银行");
                downloadLog.setDownloaderid(0L);
                downloadLog.setDownloadername("system");
                downloadLog.setFileurl(fileName);
                downloadLogDAO.saveDownloadLog(downloadLog);
            }

            try {
               downloadCMBCFile(downloadLog.getFileurl(),TARGETPATH);
            } catch (Exception e) {
                // TODO: handle exception
                downloadLog.setRecode("02");
                downloadLogDAO.update(downloadLog);
                continue;
            }
            File file = null;
            try {
                file = new File(TARGETPATH);
                downloadLog.setRecode("00");
                String[] files =fileName.split("/");
                analyzeReexchangeFile(file);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                downloadLog.setRecode("01");
            } finally {
                downloadLogDAO.updateDownloadLog(downloadLog);
                file.delete();
            }
        }
		
	}

	private void analyzeReexchangeFile(File file) throws NumberFormatException, IOException{
        if (file.isFile() && file.exists()) { // 判断文件是否存在
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), Charsets.UTF_8);// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            int i=0;
            List<SingleReexchangeBean> resultList = new ArrayList<SingleReexchangeBean>();
            while ((lineTxt = bufferedReader.readLine()) != null) {
                
                if("########".equals(lineTxt.trim())){
                    break;
                }
                log.info("原始退汇文件内容("+i+"行):"+lineTxt);
                //0        1        2         3           4      5          6       7   8       9     10
                //交易服务码|合作方流水号|银行处理流水号|收款人账户号|收款人账户名|交易金额(单位为分)|应答码类型|应答码|应答描述|银行对账日期|退汇日期
                String[] body = lineTxt.split("\\|");
                SingleReexchangeBean resultBean = new SingleReexchangeBean(body);
                log.info("接收到退汇文件内容("+i+"行):"+JSON.toJSONString(resultBean));
                resultList.add(resultBean);
                i++;
            }
            read.close();
            try {
				Producer producer = new InsteadPayProducer(ResourceBundle.getBundle("producer_cmbc").getString("single.namesrv.addr"), InsteadPayTagsEnum.INSTEADPAY_REALTIME_REEXCHANGE);
				for(SingleReexchangeBean bean : resultList){
					PojoCmbcResfileLog cmbcResfileLog = new PojoCmbcResfileLog(bean);
					cmbcResfileLogDAO.saveResfileLog(cmbcResfileLog);
					producer.sendJsonMessage(JSON.toJSONString(bean),InsteadPayTagsEnum.QUERY_INSTEADPAY_REALTIME);
				}
				producer.closeProducer();
			} catch (MQClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemotingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MQBrokerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           //剩下的处理由民生渠道模块完成，这里只进行数据的读取和保存
            
            
        } else {
            
        }
    }

	public List<String> scanningCMBCRESFile(String path){
        FTPClient ftpClient = new FTPClient();
        FileOutputStream fos = null;
        try {
            ftpClient.connect(FTPIP, port);
            ftpClient.login(USER,PWD);
            ftpClient.setBufferSize(1024);
            // 设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(path);
            //设置为被动模式，不然非21端口会无法上传文件
            ftpClient.enterLocalPassiveMode();
            FTPFile[] ftpFiles =  ftpClient.listFiles();
            List<String> fileNameList = new ArrayList<String>();
            for(int i=0;i<ftpFiles.length;i++){
                log.info("file name:"+path+"/"+ftpFiles[i].getName());
                if(ftpFiles[i].getName().indexOf("DF_ZLJR_THWJ")>=0){
                    fileNameList.add(path+"/"+ftpFiles[i].getName());
                }
            }
            return fileNameList;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(fos);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
       
    }
	
	public void downloadCMBCFile(String filePath,String targetPath) {
        FTPClient ftpClient = new FTPClient();
        FileOutputStream fos = null;
        try {
            log.info("filePath:"+filePath);
            log.info("targetPath:"+targetPath);
            ftpClient.connect(FTPIP, port);
            ftpClient.login(USER, PWD);
            fos = new FileOutputStream(targetPath);
            ftpClient.setBufferSize(1024);
            //设置为被动模式，不然非21端口会无法上传文件
            ftpClient.enterLocalPassiveMode();
            // 设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.retrieveFile(filePath, fos);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(fos);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
    }
}
