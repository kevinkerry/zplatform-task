/* 
 * CmbcResfileLogDAOImpl.java  
 * 
 * version TODO
 *
 * 2016年10月25日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.task.common.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.task.dao.CmbcResfileLogDAO;
import com.zlebank.zplatform.task.pojo.PojoCmbcResfileLog;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月25日 下午4:26:08
 * @since 
 */
@Repository("cmbcResfileLogDAO")
public class CmbcResfileLogDAOImpl extends HibernateBaseDAOImpl<PojoCmbcResfileLog> implements CmbcResfileLogDAO {

	
	/**
	 *
	 * @param cmbcResfileLog
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void saveResfileLog(PojoCmbcResfileLog cmbcResfileLog) {
		// TODO Auto-generated method stub
		saveEntity(cmbcResfileLog);
	}

}
