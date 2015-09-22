package com.examw.netplatform.controllers.api;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.examw.netplatform.domain.admin.students.BaseLearning;

/**
 * API测试。
 * 
 * @author jeasonyoung
 * @since 2015年9月22日
 */
public class TestApi {
	private static final Logger logger = Logger.getLogger(TestApi.class);

	@Test
	public void testUpdateLearning() throws Exception{
		logger.debug("testUpdateLearning....");
		//初始化GET
//		final String url = "http://demo.examw.com/examw-netplatform/api/m/categories.do";
//		final HttpGet getMethod = new HttpGet(url);
		//logger.debug(sendDigestRequest(getMethod));
		
		
		BaseLearning data = new BaseLearning();
		data.setAgencyId("");
		data.setStudentId("");
		data.setLessonId("");
		data.setStatus(0);
		
		final String json = JSON.toJSONString(data);
		
		//初始化POST
		final String url  = "http://demo.examw.com/examw-netplatform/api/m/learning.do";
		final HttpPost postMethod = new HttpPost(url);
		
		StringEntity s  = new StringEntity(json);
		s.setContentEncoding("UTF-8");
		s.setContentType("application/json");
		
		postMethod.setEntity(s);
		logger.debug(sendDigestRequest(postMethod));
	}
	//
	private static String sendDigestRequest(HttpUriRequest method) throws Exception{
		//创建密码证书
		final Credentials credentials = new UsernamePasswordCredentials("hk@test", "test");
		//创建认证提供者
		final BasicCredentialsProvider bcp = new BasicCredentialsProvider();
		bcp.setCredentials(AuthScope.ANY, credentials);
		//初始化HTTP客户端
		final CloseableHttpClient client = HttpClients.custom()
				.setDefaultCredentialsProvider(bcp)
				.build();
		//执行方法并返回
		final HttpResponse response = client.execute(method);
		//返回结果
		final int status = response.getStatusLine().getStatusCode();
		final String result = EntityUtils.toString(response.getEntity());
		//释放连接
		client.close();
		//
		if(status == HttpStatus.SC_OK){
			return result;
		}
		return status + "/" + result;
	}
}