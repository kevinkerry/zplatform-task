/* 
 * PojoMerchMK.java  
 * 
 * version 1.0
 *
 * 2015年9月17日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class Description
 *
 * @author yangying
 * @version
 * @date 2015年9月17日 上午9:06:43
 * @since
 */
@Entity
@Table(name="T_MERCH_MK")
public class PojoMerchMK implements Serializable{
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 452863400030365384L;
	private String memberId;
    private String safeSeq;
    private String safeType;
    private String memberPubKey;
    private String memberPriKey;
    private String localPubKey;
    private String localPriKey;
    private String status;
    
    @Id
    @Column(name="MEMBERID",length=15)
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    
    @Column(name="SAFESEQ",length=32)
    public String getSafeSeq() {
        return safeSeq;
    }
    public void setSafeSeq(String safeSeq) {
        this.safeSeq = safeSeq;
    }
    

    @Column(name="SAFETYPE",length=32)
    public String getSafeType() {
        return safeType;
    }
    
    public void setSafeType(String safeType) {
        this.safeType = safeType;
    }
    
    @Column(name="MEMBERPUBKEY",length=2048)
    public String getMemberPubKey() {
        return memberPubKey;
    }
    public void setMemberPubKey(String memberPubKey) {
        this.memberPubKey = memberPubKey;
    }
    
    @Column(name="MEMBERPRIKEY",length=2048)
    public String getMemberPriKey() {
        return memberPriKey;
    }
    public void setMemberPriKey(String memberPriKey) {
        this.memberPriKey = memberPriKey;
    }
    
    @Column(name="LOCALPUBKEY",length=2048)
    public String getLocalPubKey() {
        return localPubKey;
    }
    public void setLocalPubKey(String localPubKey) {
        this.localPubKey = localPubKey;
    }
    
    @Column(name="LOCALPRIKEY",length=2048)
    public String getLocalPriKey() {
        return localPriKey;
    }
    public void setLocalPriKey(String localPriKey) {
        this.localPriKey = localPriKey;
    }
    
    @Column(name="STATUS",length=2)
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
