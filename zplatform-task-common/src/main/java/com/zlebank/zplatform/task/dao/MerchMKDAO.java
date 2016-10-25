/* 
 * MerchMKDAO.java  
 * 
 * version 1.0
 *
 * 2015年9月17日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.dao;

import com.zlebank.zplatform.task.common.dao.BaseDAO;
import com.zlebank.zplatform.task.pojo.PojoMerchMK;

/**
 * Class Description
 *
 * @author yangying
 * @version
 * @date 2015年9月17日 上午9:35:41
 * @since 
 */
public interface MerchMKDAO extends BaseDAO<PojoMerchMK>{
    /**
     *  查询RSA 密钥信息（safeType：01）
     *  注意：此方法默认为RSA算法密钥（如果不是RSA密钥请另建方法）
     * @param memberId
     * @return
     */
    PojoMerchMK get(String memberId);
}
