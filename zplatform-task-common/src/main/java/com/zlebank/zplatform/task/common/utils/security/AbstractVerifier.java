/* 
 * AbstractUnpacker.java  
 * 
 * version TODO
 *
 * 2016年4月20日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.common.utils.security;

/**
 * 加签验签器
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年4月20日 下午3:54:58
 * @since 
 */
public interface AbstractVerifier {
    public  boolean verify(String data, String sign);
    public  String sign(String data);
}
