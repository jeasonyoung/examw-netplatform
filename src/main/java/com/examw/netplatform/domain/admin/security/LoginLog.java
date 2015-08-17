package com.examw.netplatform.domain.admin.security;

import java.io.Serializable;
/**
 * 登录日志。
 * @author yangyong.
 * @since 2014-05-17.
 */
public class LoginLog implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,userId,userAccount,userName,ip,browser,createTime;
	/**
	 * 获取日志ID。
	 * @return
	 * 日志ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置日志ID。
	 * @param id
	 * 日志ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取登录用户ID。
	 * @return
	 * 登录账号用户ID。
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置登录账号。
	 * @param userId
	 * 登录账号。
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取用户账号。
	 * @return 用户账号。
	 */
	public String getUserAccount() {
		return userAccount;
	}
	/**
	 * 设置用户账号。
	 * @param userAccount 
	 *	  用户账号。
	 */
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	/**
	 * 获取用户姓名。
	 * @return 用户姓名。
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置用户姓名。
	 * @param userName 
	 *	  用户姓名。
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取登录IP。
	 * @return
	 * 登录IP。
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * 设置登录IP。
	 * @param ip
	 * 登录IP。
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取浏览器版本。
	 * @return
	 * 浏览器版本。
	 */
	public String getBrowser() {
		return browser;
	}
	/**
	 * 设置浏览器版本。
	 * @param browser
	 * 浏览器版本。
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	/**
	 * 获取登录时间。
	 * @return
	 * 登录时间。
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * 设置登录时间。
	 * @param createTime
	 * 登录时间。
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}