/* 
 * RSACryptor.java  
 * 
 * version TODO
 *
 * 2016年4月22日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.common.utils.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zlebank.zplatform.task.common.utils.Base64Utils;
import com.zlebank.zplatform.task.common.utils.RSAUtils;

/**
 * RSA加密解密
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年4月22日 下午3:58:45
 * @since 
 */
public class RSAHelper implements AbstractCryptor, AbstractVerifier{
    
    private static final Log log = LogFactory.getLog(RSAHelper.class);

    private String privateKey;
    
    private String publicKey;

    /**
     * 
     */
    public RSAHelper(String publicKey, String privateKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }
    /**
     * 公钥加密
     * @param data
     * @return String Base64编码后的密文
     */
    @Override
    public String encrypt(String data) {
        try {
            byte[] encryptData = RSAUtils.encryptByPublicKey(data.getBytes(), publicKey);
            return Base64Utils.encode(encryptData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 私钥解密
     * @param data Base64编码后的密文
     * @return String 明文
     */
    @Override
    public String decrypt(String data) {
        try {
            byte[] decodedData = RSAUtils.decryptByPrivateKey(Base64Utils.decode(data), privateKey);
            return new String(decodedData, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * RSA公钥验签
     * @param data
     * @param sign
     * @return
     */
    @Override
    public boolean verify(String data, String sign) {
        boolean isOK = false;
        try {
            isOK = RSAUtils.verify(data.getBytes(), publicKey.trim(), sign);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return isOK;
    }
    /**
     * RSA私钥加签
     * @param data
     */
    @Override
    public String sign(String data) {
        try {
            return RSAUtils.sign(data.getBytes(), privateKey.trim());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
