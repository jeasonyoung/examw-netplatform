package com.examw.netplatform.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.service.admin.security.IUserAuthorization;
import com.examw.netplatform.support.UserAware;
/**
 * 用户认证拦截器。
 * @author yangyong.
 * @since 2014-05-15.
 */
public class UserAuthenticationInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = Logger.getLogger(UserAuthenticationInterceptor.class);
	private final NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<>("StopWatch-StartTime");
	private IUserAuthorization userAuthorization;
	/**
	 * 设置用户授权服务接口。
	 * @param userAuthorization
	 * 用户授权服务接口。
	 */
	public void setUserAuthorization(IUserAuthorization userAuthorization) {
		logger.debug("注入用户授权服务接口...");
		this.userAuthorization = userAuthorization;
	}
	/*
	 * 在业务处理之前被调用。
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		logger.debug("开始业务处理..."+request.getServletPath());
		this.startTimeThreadLocal.set(System.currentTimeMillis());//线程绑定开始时间(该数据只有当前请求的线程可见)。
		//
		if(handler instanceof HandlerMethod){
			final HandlerMethod hm = (HandlerMethod)handler;
			if(hm != null && (hm.getBean() instanceof UserAware)){
				final UserAware userAware = (UserAware)hm.getBean();
				logger.debug("准备注入用户信息...");
				final Subject subject = SecurityUtils.getSubject();
				if(subject != null && subject.isAuthenticated()){
					final String userId = (String)subject.getPrincipal();
					logger.debug("加载用户ID:" + userId);
					if(!StringUtils.isEmpty(userId)){
						final User user = this.userAuthorization.getUser(userId);
						if(user != null){
							//注入机构ID
							userAware.setAgencyId(user.getAgencyId());
							//注入用户ID
							userAware.setUserId(user.getId());
						}
					}
				}
			}
		}
		//
		return super.preHandle(request, response, handler);
	}
	/*
	 * 业务处理完全处理完后被调用。
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
		super.afterCompletion(request, response, handler, ex); 
		//
		long consumeTime = System.currentTimeMillis() - this.startTimeThreadLocal.get();
		logger.debug("业务"+request.getServletPath()+"处理完成，耗时：" + consumeTime + "  " + ((consumeTime > 500) ? "[较慢]" : "[正常]")); 
	}
}