package com.examw.netplatform.support;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
/**
 * 
 * 
 * @author jeasonyoung
 * @since 2015年8月27日
 */
public class WebHelpers {
	/**
	 * 获取客户端IP地址。
	 * @param request
	 * 客户端请求对象。
	 * @return 客户端IP地址。
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		if(request == null) throw new IllegalArgumentException("request");
		String client =  request.getHeader("x-forwarded-for");
		if(StringUtils.isEmpty(client) || "unknown".equalsIgnoreCase(client)){
			client =  request.getHeader("Proxy-Client-IP");
		}
		if(StringUtils.isEmpty(client) || "unknown".equalsIgnoreCase(client)){
			client  = request.getHeader("WL-Proxy-Client-IP");
		}
		if(StringUtils.isEmpty(client) || "unknown".equalsIgnoreCase(client)){
			client = request.getRemoteAddr();
		}
		return client;
	}
}
