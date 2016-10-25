/* 
 * MerchMKDAOImpl.java  
 * 
 * version 1.0
 *
 * 2015年9月17日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.task.common.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.task.dao.MerchMKDAO;
import com.zlebank.zplatform.task.pojo.PojoMerchMK;

/**
 * Class Description
 *
 * @author yangying
 * @version
 * @date 2015年9月17日 上午9:37:40
 * @since 
 */
@Repository
public class MerchMKDAOImpl  extends HibernateBaseDAOImpl<PojoMerchMK> implements MerchMKDAO{

    /**
     * 查询RSA 密钥信息（safeType：01）
     * 注意：此方法默认为RSA算法密钥（如果不是RSA密钥请另建方法）
     * @param memberId
     * @return
     */
    @Override
    @Transactional(readOnly=true)
    public PojoMerchMK get(String memberId) {
        Criteria  criteria = getSession().createCriteria(PojoMerchMK.class);
        criteria.add(Restrictions.eq("memberId", memberId)).add(Restrictions.eq("status", "00"));
        criteria.add(Restrictions.eq("safeType", "01"));
        return (PojoMerchMK)criteria.uniqueResult();
    }

}
