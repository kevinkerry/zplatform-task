package com.zlebank.zplatform.task.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
  * @ClassName: NotiyBean
  * @Description: TODO
  * @author guojia
  * @date 2016年10月22日 下午8:52:19
  *
  */
public class NotifyBean implements Serializable{
	
	
	private static final long serialVersionUID = 1602151415138555034L;
	private String data;
    private String sign;
    private String addit;
    
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
