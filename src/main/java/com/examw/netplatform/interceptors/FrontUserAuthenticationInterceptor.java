package com.examw.netplatform.interceptors;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.examw.netplatform.domain.admin.settings.AgencyUser;

/**
 * 前台用户未登录拦截
 * @author fengwei.
 * @since 2015年1月21日 下午2:41:35.
 */
public class FrontUserAuthenticationInterceptor  extends HandlerInterceptorAdapter {
	private static Logger logger = Logger.getLogger(FrontUserAuthenticationInterceptor.class);
	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<>("StopWatch-StartTime");
	private String loginUrl;
	private List<String> safeUrl;
	/**
	 * 设置 登录地址
	 * @param loginUrl
	 * 
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	
	/**
	 * 设置 需要跳过检查的地址
	 * @param safeUrl
	 * 
	 */
	public void setSafeUrl(List<String> safeUrl) {
		this.safeUrl = safeUrl;
	}
	
	/*
	 * 在业务处理之前被调用。
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("开始前台业务处理..."+request.getServletPath());
			this.startTimeThreadLocal.set(System.currentTimeMillis());//线程绑定开始时间(该数据只有当前请求的线程可见)。
		}
		 //1、请求到登录页面 放行  
	    if(request.getServletPath().startsWith(loginUrl)) {  
	        return true;  
	    }  
	          
	    //2、TODO 比如退出、首页等页面无需登录，即此处要放行 允许游客的请求  静态资源
	    if(safeUrl!=null && safeUrl.size()>0){
	    	for(String url:safeUrl)
	    	{
	    		if(!StringUtils.isEmpty(url)){
	    			if(url.contains("*") && request.getServletPath().startsWith(url.replaceAll("[*]", "")))
	    		    {
	    		    	return true;
	    		    }else if(!url.contains("*") && request.getServletPath().equalsIgnoreCase(url)){
	    		    	return true;
	    		    }
	    		}
	    	}
	    }
	    //3、如果用户已经登录 放行
	    //从seesion取得用户
	    AgencyUser user = (AgencyUser) request.getSession().getAttribute("frontUser");
	    if(user != null){	//用户为空
	    	return true;
	    }
	    //4、非法请求 即这些请求需要登录后才能访问  
	    //重定向到登录页面  
	    //记录上一次的地址
	    Cookie cookie = new Cookie("LastPage",request.getRequestURI().substring(request.getContextPath().length()));
	    cookie.setPath("/");
	    response.addCookie(cookie);
	    //TODO 增加了项目名称
	    logger.debug("redirect : " + request.getContextPath()+loginUrl);
	    response.sendRedirect(request.getContextPath()+loginUrl);
	    return false; 
	}
	/*
	 * 业务处理完全处理完后被调用。
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
		super.afterCompletion(request, response, handler, ex);
		if(logger.isDebugEnabled()){
			long consumeTime = System.currentTimeMillis() - this.startTimeThreadLocal.get();
			logger.debug("前台业务"+request.getServletPath()+"处理完成，耗时：" + consumeTime + "  " + ((consumeTime > 500) ? "[较慢]" : "[正常]"));
		}
	}
}