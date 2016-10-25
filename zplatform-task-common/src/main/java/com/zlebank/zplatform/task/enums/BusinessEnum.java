/* 
 * BusinessEnum.java  
 * 
 * version TODO
 *
 * 2015年11月27日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.enums;

/**
 * 交易类型代码【分录流水记账专用】
 *
 * @author guojia
 * @version
 * @date 2015年11月27日 上午11:45:57
 * @since 
 */
public enum BusinessEnum {
    CONSUMEQUICK("10000001"),//消费-快捷
    CONSUMEACCOUNT("10000002"),//消费-账户
    CONSUMESPLIT("10000004"),//消费-分账
    RECHARGE("20000001"),//充值
    /** 提现申请 **/
    WITHDRAWALS("30000001"),
    
    REFUND("40000001"),//退款
    /** 代付申请 **/
    INSTEADPAY_BATCH("70000001"),
    /** 代付成功 **/
    INSTEADPAY_SINGLE("70000002"),
    /** 代付失败 **/
    
    /** 实名认证【有成本】 **/
    REALNAME_AUTH_COST("80000001"),
    /** 实名认证【无成本】 **/
    REALNAME_AUTH_NO_COST("80000002"),
    /** 转账交易流水 **/
    TRAN_DATA_NO("90000001"),
    UNKNOW("");//未知
    private String busiCode;
    
    private BusinessEnum(String busiCode){
        this.busiCode = busiCode;
    }
    
    public static BusinessEnum fromValue(String value) {
        for(BusinessEnum busi:values()){
            if(busi.busiCode.equals(value)){
                return busi;
            }
        }
        return UNKNOW;
    }

    public String getBusiCode(){
        return busiCode;
    }
}
