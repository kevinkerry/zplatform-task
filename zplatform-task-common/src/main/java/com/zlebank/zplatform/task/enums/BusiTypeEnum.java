package com.zlebank.zplatform.task.enums;

/**
 * 业务类型枚举
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年12月9日 下午4:16:03
 * @since
 */
public enum BusiTypeEnum {
    /**
     *  (消费类：1000；充值类：2000；提现类：3000；退款类：4000；转账类：5000；保障金：6000;代付类:7000)
     */
    CONSUMPTION("1000"),
    CHARGE("2000"),
    WITHDRAWAL("3000"),
    REFUND("4000"),
    TRANSFER ("5000"),
    SAFEGUARD("6000"),
    INSTEADPAY("7000"),
    UNKNOW("99");
    private String code;
    
    private BusiTypeEnum(String code){
        this.code = code;
    }
    
    public static BusiTypeEnum fromValue(String value) {
        for(BusiTypeEnum status:values()){
            if(status.code.equals(value)){
                return status;
            }
        }
        return UNKNOW;
    }
    
    public String getCode(){
        return code;
    }
}
