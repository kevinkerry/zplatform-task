/* 
 * AbstractCryptor.java  
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
 * 加密解密
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年4月22日 上午11:27:20
 * @since 
 */
public interface AbstractCryptor {
    public  String encrypt(String data);
    public  String decrypt(String data);
}
