/* 
 * AESUnpacker.java  
 * 
 * version TODO
 *
 * 2016年4月22日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.common.utils.security;

import com.zlebank.zplatform.task.common.utils.Base64Utils;



/**
 * AES解包（报文解包）
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年4月22日 下午4:10:18
 * @since 
 */
public class AESHelper implements AbstractUnpacker{
    private String key;
    public AESHelper(String key) {
        this.key = key;
    }
    /**
     *
     * @param data
     * @return 
     */
    @Override
    public String unpack(String data) {
        try {
            return new String(AESUtil.decrypt(Base64Utils.decode(data), key), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param data
     */
    @Override
    public String pack(String data) {
        try {
            return Base64Utils.encode((AESUtil.encrypt(data, key)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
    }
    
}
