package com.zlebank.zplatform.task.factory.notify;

import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.task.bean.NotifyBean;
import com.zlebank.zplatform.task.bean.TradeNotifyBean;
import com.zlebank.zplatform.task.common.utils.security.AESHelper;
import com.zlebank.zplatform.task.common.utils.security.AESUtil;
import com.zlebank.zplatform.task.common.utils.security.RSAHelper;
import com.zlebank.zplatform.task.dao.InsteadPayRealtimeDAO;
import com.zlebank.zplatform.task.dao.MerchMKDAO;
import com.zlebank.zplatform.task.dao.TxnsOrderinfoDAO;
import com.zlebank.zplatform.task.enums.BiztypeEnum;
import com.zlebank.zplatform.task.enums.BusinessEnum;
import com.zlebank.zplatform.task.factory.BeanFactory;
import com.zlebank.zplatform.task.pojo.PojoInsteadPayRealtime;
import com.zlebank.zplatform.task.pojo.PojoMerchMK;
import com.zlebank.zplatform.task.pojo.PojoTxnsOrderinfo;

/**
  * @ClassName: AsynNotifyFactory
  * @Description: TODO
  * @author guojia
  * @date 2016年10月22日 下午8:40:31
  *
  */
@Service
public class AsynNotifyFactory implements BeanFactory{

	private static final Logger log = LoggerFactory.getLogger(AsynNotifyFactory.class);
	@Autowired
	private TxnsOrderinfoDAO txnsOrderinfoDAO;
	@Autowired
	private InsteadPayRealtimeDAO insteadPayRealtimeDAO;
	@Autowired
	private MerchMKDAO merchMKDAO;
	@Override
	public NotifyBean getBean(String txnseqno,BiztypeEnum biztypeEnum) {
		NotifyBean bean = null;
		if(biztypeEnum == BiztypeEnum.NM000210){
			bean = new NotifyBean();
			PojoTxnsOrderinfo orderinfo = txnsOrderinfoDAO.getTxnsOrderinfoByTxnseqno(txnseqno);
			TradeNotifyBean tradeNotifyBean = new TradeNotifyBean(orderinfo);
			responseData(tradeNotifyBean, orderinfo.getFirmemberno(), orderinfo.getSecmemberno(), bean);
			bean.setNotifyURL(orderinfo.getBackurl());
		}else if(biztypeEnum == BiztypeEnum.NM000205){
			
			
		}
		return bean;
	}

	@Override
	public NotifyBean getInsteadPayBean(String txnseqno,
			BusinessEnum businessEnum) {
		NotifyBean bean = null;
		if(businessEnum == BusinessEnum.INSTEADPAY_SINGLE){
			PojoInsteadPayRealtime insteadPayRealtime = insteadPayRealtimeDAO.getInsteadPayByTxnseqno(txnseqno);
			BiztypeEnum biztypeEnum = BiztypeEnum.fromValue(insteadPayRealtime.getBizType());
			if(biztypeEnum == BiztypeEnum.NM000210){
				bean = new NotifyBean();
				TradeNotifyBean tradeNotifyBean = new TradeNotifyBean(insteadPayRealtime);
				responseData(tradeNotifyBean, insteadPayRealtime.getCoopInstCode(), insteadPayRealtime.getMerId(), bean);
				bean.setNotifyURL(insteadPayRealtime.getBackUrl());
			}
		}else if(businessEnum == BusinessEnum.INSTEADPAY_BATCH){
			
		}
		
		return bean;
	}

	
	@SuppressWarnings("unchecked")
	private void responseData(TradeNotifyBean respBean, String coopInstCode,String merchNo,NotifyBean task) {
        JSONObject jsonData = JSONObject.fromObject(respBean);
        // 排序
        Map<String, Object> map = new TreeMap<String, Object>();
        map =(Map<String, Object>) JSONObject.toBean(jsonData, TreeMap.class);
        jsonData = JSONObject.fromObject(map);
        
        JSONObject addit = new JSONObject();
        addit.put("accessType", "1");
        addit.put("coopInstiId", coopInstCode);
        addit.put("merId", merchNo);
        //MerchMK merchMk = merchMKService.get(addit.getString("merId"));
        PojoMerchMK merchMk = merchMKDAO.get(addit.getString("merId"));
        RSAHelper rsa = new RSAHelper(merchMk.getMemberPubKey(), merchMk.getLocalPriKey());
        String aesKey = null;
        try {
            aesKey = AESUtil.getAESKey();
            log.debug("【AES KEY】" + aesKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addit.put("encryKey", rsa.encrypt(aesKey));
        addit.put("encryMethod", "01");

        // 加签名
        StringBuffer originData = new StringBuffer(addit.toString());//业务数据
        originData.append(jsonData.toString());// 附加数据
        log.debug("【应答报文】加签用字符串：" + originData.toString());
        // 加签
        String sign = rsa.sign(originData.toString());
        AESHelper packer = new AESHelper(aesKey);
        JSONObject rtnSign = new JSONObject();
        rtnSign.put("signature", sign);
        rtnSign.put("signMethod", "01");
        
        // 业务数据
        task.setData(packer.pack(jsonData.toString()));
        // 附加数据
        task.setAddit(addit.toString());
        // 签名数据
        task.setSign(rtnSign.toString());
        
        log.debug("【发送报文数据】【业务数据】："+task.getData());
        log.debug("【发送报文数据】【附加数据】："+task.getAddit());
        log.debug("【发送报文数据】【签名数据】："+ task.getSign());
    }
}
