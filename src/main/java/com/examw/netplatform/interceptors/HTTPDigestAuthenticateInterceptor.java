package com.examw.netplatform.interceptors;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.security.UserStatus;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.service.admin.security.IUserAuthorization;
import com.examw.netplatform.service.admin.settings.IAgencyService;
import com.examw.netplatform.support.PasswordHelper;
import com.examw.utils.MD5Util;

/**
 * HTTP摘要认证服务器端拦截器。
 * 
 * @author yangyong
 * @since 2014年12月20日
 */
public class HTTPDigestAuthenticateInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = Logger.getLogger(HTTPDigestAuthenticateInterceptor.class);
	private String realm;
	private IUserAuthorization userAuthorization;
	private IAgencyService agencyService;
	private PasswordHelper passwordHelper;
	/**
	 * HTTP Authorization header, equal to <code>Authorization</code>
	 */
	private static final String AUTHORIZATION_HEADER = "Authorization";
	/**
     * HTTP Authentication header, equal to <code>WWW-Authenticate</code>
     */
    private static final String AUTHENTICATE_HEADER = "WWW-Authenticate";
    /**
     * 构造函数。
     */
    public HTTPDigestAuthenticateInterceptor(){
    	this.realm = "realm@examw.com";
    }
	/**
	 * 设置认证域。
	 * @param realm 
	 *	  认证域
	 */
	public void setRealm(String realm) {
		logger.debug(String.format("注入认证域：%s", realm));
		this.realm = realm;
	}
	/**
	 * 设置用户认证服务接口。
	 * @param userAuthorization 
	 *	  用户认证服务接口。
	 */
	public void setUserAuthorization(IUserAuthorization userAuthorization) {
		logger.debug("注入用户认证服务接口...");
		this.userAuthorization = userAuthorization;
	}
	/**
	 * 设置机构服务接口。
	 * @param agencyService 
	 *	  机构服务接口。
	 */
	public void setAgencyService(IAgencyService agencyService) {
		logger.debug("注入机构服务接口...");
		this.agencyService = agencyService;
	}
	/**
	 * 设置密码工具类。
	 * @param passwordHelper 
	 *	  密码工具类。
	 */
	public void setPasswordHelper(PasswordHelper passwordHelper) {
		logger.debug("注入密码工具类...");
		this.passwordHelper = passwordHelper;
	}
	/*
	 * 重载处理。
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		logger.debug("开始摘要认证处理...");
		//验证摘要。
		if(!this.authentication(request.getMethod(), request.getHeader(AUTHORIZATION_HEADER))){
			//认证失败，发送401错误。
			this.sendAuthenticate(response);
			return false;
		}
		logger.debug("摘要认证处理完毕!");
		return super.preHandle(request, response, handler);
	}
	//发送401错误认证信息。
	private void sendAuthenticate(HttpServletResponse response){
		logger.debug("Authentication required: sending 401 Authentication challenge response.");
		final String randomCode = createRandomCode(),qop = "auth";
		final StringBuilder authcHeadBuilder = new StringBuilder();
		authcHeadBuilder.append(HttpServletRequest.DIGEST_AUTH).append(" ")
									.append("realm").append("=").append("\"").append(this.realm).append("\",")
									.append("qop").append("=").append("\"").append(qop).append("\",")
									.append("nonce").append("=").append("\"").append(randomCode).append("\",")
									.append("opaque").append("=").append("\"").append(MD5Util.MD5(this.realm + ":" + qop + ":" + randomCode)).append("\"");
		final String authc_head = authcHeadBuilder.toString();
		logger.debug(String.format("http-head:%s", authc_head));
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setHeader(AUTHENTICATE_HEADER, authc_head);
	}
	//生成随机码。
	private static String createRandomCode(){
		logger.debug("生成随机码...");
		return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
	}
	//验证摘要。
	private synchronized boolean authentication(String method, String authz){
		logger.debug(String.format("验证摘要：%s", authz));
		//摘要信息为空
		if(StringUtils.isBlank(authz)){
			logger.debug("摘要信息为空！");
			return false;
		}
		final String username_value = this.getParameter(authz, "username");
		logger.debug("username => " + username_value);
		if(StringUtils.isBlank(username_value)){
			logger.debug("没有获取到username值！");
			return false;
		}
		final String realm_value = this.getParameter(authz, "realm");
		logger.debug("realm => " + realm_value);
		if(StringUtils.isBlank(realm_value)){
			logger.debug("没有获取到realm值！");
			return false;
		}
		final String nonce_value = this.getParameter(authz, "nonce");
		logger.debug("nonce => " + nonce_value);
		if(StringUtils.isBlank(nonce_value)){
			logger.debug("没有获取到nonce值！");
			return false;
		}
		final String uri_value = this.getParameter(authz, "uri");
		logger.debug("uri => " + uri_value);
		if(StringUtils.isBlank(uri_value)){
			logger.debug("没有获取到uri值！");
			return false;
		}
		final String qop_value = this.getParameter(authz, "qop");
		logger.debug("qop => " + qop_value);
		if(StringUtils.isBlank(qop_value)){
			logger.debug("没有获取到qop值！");
			return false;
		}
		final String nc_value = this.getParameter(authz, "nc");
		logger.debug("nc => " + nc_value);
		if(StringUtils.isBlank(nc_value)){
			logger.debug("没有获取到nc值！");
			return false;
		}
		final String cnonce_value = this.getParameter(authz, "cnonce");
		logger.debug("cnonce => " + cnonce_value);
		if(StringUtils.isBlank(cnonce_value)){
			logger.debug("没有获取到cnonce值！");
			return false;
		}
		final String response_value = this.getParameter(authz, "response");
		logger.debug("response => " + response_value);
		if(StringUtils.isBlank(response_value)){
			logger.debug("没有获取到response值！");
			return false;
		}
		final String opaque_value = this.getParameter(authz, "opaque");
		logger.debug("opaque => " + opaque_value);
		if(StringUtils.isBlank(opaque_value)){
			logger.debug("没有获取到opaque值！");
			return false;
		}
		//验证opaque
		final String opaque =  MD5Util.MD5(this.realm + ":" + qop_value + ":" + nonce_value);
		logger.debug("new opaque => " + opaque);
		if(!opaque.equalsIgnoreCase(opaque_value)){
			logger.debug("验证opaque失败！");
			return false;
		}
		//获取用户
		final String[] arrays = username_value.split("@");
		logger.debug("username split @ => " + StringUtils.join(arrays,","));
		if(arrays == null || arrays.length < 2){
			logger.debug("用户名中未包含机构EN!");
			return false;
		}
		//获取机构
		final Agency agency = this.agencyService.loadAgencyByAbbr(arrays[0]);
		if(agency == null){
			logger.debug("机构["+arrays[0]+"]不存在!");
			return false;
		}
		//加载用户
		final User user = this.userAuthorization.loadUserByAccount(agency.getId(), arrays[1]);
		if(user == null){
			logger.debug("机构[" + agency.getId() + ","+agency.getAbbrEN()+","+agency.getName()+"]下用户名["+arrays[1]+"]不存在!");
			return false;
		}
		//用户是否有效
		if(user.getStatus() != UserStatus.ENABLED.getValue()){
			logger.debug("用户["+user.getAccount()+"]状态:" + UserStatus.parse(user.getStatus()));
			return false;
		}
		//解密用户密码
		final String pwd = this.passwordHelper.decryptAESPassword(user);
	    final	 String ha1 = MD5Util.MD5(username_value + ":" + realm_value + ":" + pwd), ha2 = MD5Util.MD5(method + ":" + uri_value);
		logger.debug(String.format("HA1:%s", ha1));
		final String response = MD5Util.MD5(ha1 + ":" + nonce_value + ":" + nc_value + ":" + cnonce_value + ":" + qop_value + ":" + ha2);
		logger.debug(String.format("response:%s", response));
		return response_value.equalsIgnoreCase(response);
	}
	//获取参数。
	private String getParameter(String authz,String name){
		if(StringUtils.isEmpty(authz) || StringUtils.isEmpty(name)) return null;
		String regex = name + "=((.+?,)|((.+?)$))";
		Matcher m = Pattern.compile(regex).matcher(authz);
		if(m.find()){
			String p = m.group(1);
			if(!StringUtils.isEmpty(p)){
				if(p.endsWith(",")){
					p = p.substring(0, p.length() - 1);
				}
				if(p.startsWith("\"")){
					p = p.substring(1);
				}
				if(p.endsWith("\"")){
					p = p.substring(0, p.length() - 1);
				}
				return p;
			}
		}
		return null;
	}
}