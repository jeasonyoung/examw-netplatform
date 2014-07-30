package com.examw.netplatform.support;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Session监听器。
 * @author yangyong.
 * @since 2014-05-13.
 */
public class SessionListener  implements HttpSessionListener {
	private SessionContext sc = SessionContext.getInstance();
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		 sc.addSession(se.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		if(session != null){
			sc.delSession(session);
		}
	}
}