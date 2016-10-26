/* 
 * DownloadLogDAO.java  
 * 
 * version TODO
 *
 * 2015年11月10日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.dao;

import com.zlebank.zplatform.task.common.dao.BaseDAO;
import com.zlebank.zplatform.task.pojo.PojoDownloadLog;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月10日 上午11:21:18
 * @since 
 */
public interface DownloadLogDAO extends BaseDAO<PojoDownloadLog>{
	/**
	 * 通过文件名称获取下载日志
	 * @param filename
	 * @return
	 */
    public PojoDownloadLog getLogByFileName(String filename);
    
    /**
     * 保存下载日志
     */
    public void saveDownloadLog(PojoDownloadLog downloadLog);
    
    /**
     * 更新下载日志
     * @param downloadLog
     */
    public void updateDownloadLog(PojoDownloadLog downloadLog);
}
