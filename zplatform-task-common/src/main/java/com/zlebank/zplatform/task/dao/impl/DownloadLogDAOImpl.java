/* 
 * DownloadLogDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年11月10日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.task.common.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.task.dao.DownloadLogDAO;
import com.zlebank.zplatform.task.pojo.PojoDownloadLog;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月10日 上午11:22:09
 * @since 
 */
@Repository("downloadLogDAO")
public class DownloadLogDAOImpl extends HibernateBaseDAOImpl<PojoDownloadLog> implements DownloadLogDAO{

    @Override
    @Transactional(readOnly=true)
    public PojoDownloadLog getLogByFileName(String filename) {
        try {
            String hql = "from PojoDownloadLog where fileurl = ?";
            Session session = getSession();
            Query query = session.createQuery(hql);
            query.setString(0, filename);
            @SuppressWarnings("unchecked")
			List<PojoDownloadLog> logList = query.list();
            if(logList.size()>0){
                return logList.get(0);
            }
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

	/**
	 *
	 * @param downloadLog
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void saveDownloadLog(PojoDownloadLog downloadLog) {
		// TODO Auto-generated method stub
		saveEntity(downloadLog);
	}

	/**
	 *
	 * @param downloadLog
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateDownloadLog(PojoDownloadLog downloadLog) {
		// TODO Auto-generated method stub
		merge(downloadLog);
	}

}
