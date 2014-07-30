package com.examw.netplatform.support;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

/**
 * session管理，解决uploadify session丢失的问题。
 * @author yangyong.
 * @since 2014-05-12.
 */
public class SessionContext {
	private static SessionContext instance;
	private Map<String, HttpSession> sessionMap;
	/**
	 * 构造函数。
	 */
	private SessionContext(){
		this.sessionMap = new HashMap<String, HttpSession>();
	}
	/**
	 * 获取实例对象。
	 * @return
	 */
	public static SessionContext getInstance(){
		if(instance == null) 
			instance = new SessionContext();
		return instance;
	}
	/**
	 * 添加Session。
	 * @param session
	 */
	public synchronized void addSession(HttpSession session) {  
        if (session != null) {  
            this.sessionMap.put(session.getId(), session);  
        }  
    }  
	/**
	 * 移除Session。
	 * @param session
	 */
    public synchronized void delSession(HttpSession session) {  
        if (session != null) {  
            this.sessionMap.remove(session.getId());  
        }  
    }  
    /**
     * 获取Session。
     * @param sessionId
     * @return
     */
    public synchronized HttpSession getSession(String sessionId) {  
        if (StringUtils.isEmpty(sessionId)) return null;  
        return (HttpSession) this.sessionMap.get(sessionId);  
    }  
}