package com.zlebank.zplatform.task.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月24日 下午5:27:54
 * @since 
 */
public class NotifyBean implements Serializable{
	
	
	private static final long serialVersionUID = 1602151415138555034L;
	private String data;
    private String sign;
    private String addit;
    private String notifyURL;
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getAddit() {
		return addit;
	}
	public void setAddit(String addit) {
		this.addit = addit;
	}
	
	/**
	 * @return the notifyURL
	 */
	public String getNotifyURL() {
		return notifyURL;
	}
	/**
	 * @param notifyURL the notifyURL to set
	 */
	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}
	public List<NameValuePair> getNotifyParam() {
		BasicNameValuePair[] pairs = new BasicNameValuePair[] {
				new BasicNameValuePair("data", data),
				new BasicNameValuePair("addit", sign),
				new BasicNameValuePair("sign", addit) };
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		for (int i = 0; i < pairs.length; i++) {
			qparams.add(pairs[i]);
		}
		return qparams;
	}
	
}
