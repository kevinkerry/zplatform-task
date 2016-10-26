/* 
 * CmbcResfileLogDAO.java  
 * 
 * version TODO
 *
 * 2016年10月25日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.dao;

import com.zlebank.zplatform.task.common.dao.BaseDAO;
import com.zlebank.zplatform.task.pojo.PojoCmbcResfileLog;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月25日 下午4:23:58
 * @since 
 */
public interface CmbcResfileLogDAO extends BaseDAO<PojoCmbcResfileLog>{

	/**
	 * 保存民生回盘文件
	 * @param cmbcResfileLog
	 */
	public void saveResfileLog(PojoCmbcResfileLog cmbcResfileLog);
}
