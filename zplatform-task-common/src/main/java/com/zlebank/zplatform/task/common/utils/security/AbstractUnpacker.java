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
 * 解包器
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年4月20日 下午3:54:58
 * @since 
 */
public interface  AbstractUnpacker {
    public  String unpack(String data);
    public  String pack(String data);
}
