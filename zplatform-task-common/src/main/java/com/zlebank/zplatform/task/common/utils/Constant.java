/* 
 * Constant.java  
 * 
 * version TODO
 *
 * 2016年10月13日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月13日 上午9:32:56
 * @since 
 */
public class Constant {

	private static final Logger log = LoggerFactory.getLogger(Constant.class);
	private String cmbc_single_insteadpay_ftp_ip;
	private int cmbc_single_insteadpay_ftp_port;
	private String cmbc_single_insteadpay_ftp_user;
	private String cmbc_single_insteadpay_ftp_pwd;
	private String cmbc_download_file_path;
	
    private boolean canRun;
    private String refresh_interval;
    private static Constant constant;
    public static synchronized Constant getInstance(){
		if(constant==null){
			constant = new Constant();
		}
		return constant;
	}
    
	private Constant(){
		refresh();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (canRun) {
					try {
						refresh();
						int interval = NumberUtils.toInt(refresh_interval, 60) * 1000;// 刷新间隔，单位：秒
						log.info("refresh Constant datetime:"+DateUtil.getCurrentDateTime());
						Thread.sleep(interval);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	
	public void refresh(){
		try {
			String path = "/home/web/trade/";
			File file = new File(path+ "zlrt.properties");
			if(!file.exists()){
			    path = getClass().getResource("/").getPath();
			    file = null;
			}
			Properties prop = new Properties();
			InputStream stream = null;
			stream = new BufferedInputStream(new FileInputStream(new File(path+ "zlrt.properties")));
			prop.load(stream);
			
			cmbc_single_insteadpay_ftp_ip = prop.getProperty("cmbc_single_insteadpay_ftp_ip");
			cmbc_single_insteadpay_ftp_port = Integer.valueOf(prop.getProperty("cmbc_single_insteadpay_ftp_port"));
			cmbc_single_insteadpay_ftp_user = prop.getProperty("cmbc_single_insteadpay_ftp_user");
			cmbc_single_insteadpay_ftp_pwd = prop.getProperty("cmbc_single_insteadpay_ftp_pwd");
			cmbc_download_file_path = prop.getProperty("cmbc_download_file_path");
			canRun = true;
			refresh_interval = prop.getProperty("refresh_interval");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the cmbc_single_insteadpay_ftp_ip
	 */
	public String getCmbc_single_insteadpay_ftp_ip() {
		return cmbc_single_insteadpay_ftp_ip;
	}

	/**
	 * @param cmbc_single_insteadpay_ftp_ip the cmbc_single_insteadpay_ftp_ip to set
	 */
	public void setCmbc_single_insteadpay_ftp_ip(
			String cmbc_single_insteadpay_ftp_ip) {
		this.cmbc_single_insteadpay_ftp_ip = cmbc_single_insteadpay_ftp_ip;
	}

	/**
	 * @return the cmbc_single_insteadpay_ftp_port
	 */
	public int getCmbc_single_insteadpay_ftp_port() {
		return cmbc_single_insteadpay_ftp_port;
	}

	/**
	 * @param cmbc_single_insteadpay_ftp_port the cmbc_single_insteadpay_ftp_port to set
	 */
	public void setCmbc_single_insteadpay_ftp_port(
			int cmbc_single_insteadpay_ftp_port) {
		this.cmbc_single_insteadpay_ftp_port = cmbc_single_insteadpay_ftp_port;
	}

	/**
	 * @return the cmbc_single_insteadpay_ftp_user
	 */
	public String getCmbc_single_insteadpay_ftp_user() {
		return cmbc_single_insteadpay_ftp_user;
	}

	/**
	 * @param cmbc_single_insteadpay_ftp_user the cmbc_single_insteadpay_ftp_user to set
	 */
	public void setCmbc_single_insteadpay_ftp_user(
			String cmbc_single_insteadpay_ftp_user) {
		this.cmbc_single_insteadpay_ftp_user = cmbc_single_insteadpay_ftp_user;
	}

	/**
	 * @return the cmbc_single_insteadpay_ftp_pwd
	 */
	public String getCmbc_single_insteadpay_ftp_pwd() {
		return cmbc_single_insteadpay_ftp_pwd;
	}

	/**
	 * @param cmbc_single_insteadpay_ftp_pwd the cmbc_single_insteadpay_ftp_pwd to set
	 */
	public void setCmbc_single_insteadpay_ftp_pwd(
			String cmbc_single_insteadpay_ftp_pwd) {
		this.cmbc_single_insteadpay_ftp_pwd = cmbc_single_insteadpay_ftp_pwd;
	}

	/**
	 * @return the canRun
	 */
	public boolean isCanRun() {
		return canRun;
	}

	/**
	 * @param canRun the canRun to set
	 */
	public void setCanRun(boolean canRun) {
		this.canRun = canRun;
	}

	/**
	 * @return the refresh_interval
	 */
	public String getRefresh_interval() {
		return refresh_interval;
	}

	/**
	 * @param refresh_interval the refresh_interval to set
	 */
	public void setRefresh_interval(String refresh_interval) {
		this.refresh_interval = refresh_interval;
	}

	/**
	 * @return the cmbc_download_file_path
	 */
	public String getCmbc_download_file_path() {
		return cmbc_download_file_path;
	}

	/**
	 * @param cmbc_download_file_path the cmbc_download_file_path to set
	 */
	public void setCmbc_download_file_path(String cmbc_download_file_path) {
		this.cmbc_download_file_path = cmbc_download_file_path;
	}
	
	
	
	
}
