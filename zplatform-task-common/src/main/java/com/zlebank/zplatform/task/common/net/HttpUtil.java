package com.zlebank.zplatform.task.common.net;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.zlebank.zplatform.task.bean.ResultBean;

/**
 * @ClassName: HttpUtil
 * @Description: TODO
 * @author guojia
 * @date 2016年10月23日 上午9:16:06
 *
 */
@ThreadSafe
public class HttpUtil {

	private CloseableHttpClient httpClient = null;

	public void openConnection() {
		PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager();
		// 增加最大连接到400
		clientConnectionManager.setMaxTotal(400);
		// 增加每个路由的默认最大连接到400
		clientConnectionManager.setDefaultMaxPerRoute(400);
		httpClient = HttpClients.createMinimal(clientConnectionManager);
		//org.apache.http.config.Lookup
	}

	public ResultBean exeHttpPost(String url,List<NameValuePair> params, String encoding) throws HttpException {
		ResultBean resultBean = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
					encoding);
			httpPost.setEntity(entity);

			HttpResponse response = this.httpClient.execute(httpPost);

			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				resultBean = new ResultBean(response.getStatusLine()
						.getStatusCode() + "", EntityUtils.toString(
						response.getEntity(), encoding));
				return resultBean;
			} else {
				resultBean = new ResultBean(response.getStatusLine()
						.getStatusCode() + "", "");
			}

			return resultBean;
		} catch (Exception e) {
			e.printStackTrace();
			resultBean = new ResultBean(HttpStatus.SC_GATEWAY_TIMEOUT + "",
					"超时");
		}
		return resultBean;
	}
	
	public void closeConnection(){
		if(null != httpClient){
            try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
}
