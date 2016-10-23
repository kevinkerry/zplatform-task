/* 
 * MD5Verifier.java  
 * 
 * version TODO
 *
 * 2016年4月22日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.common.utils.security;


/**
 * Class Description
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年4月22日 下午4:10:47
 * @since 
 */
public class MD5Helper implements AbstractVerifier{

    /**
     *
     * @param data
     */
    @Override
    public boolean verify(String data, String sign) {
        String localMd5 = Md5Utils.md5(data, "UTF-8").toUpperCase();
        if (!localMd5.equals(sign.toUpperCase())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * @param data
     */
    @Override
    public String sign(String data) {
        String localMd5 = Md5Utils.md5(data, "UTF-8").toUpperCase();
        return localMd5;
    }
    
}
