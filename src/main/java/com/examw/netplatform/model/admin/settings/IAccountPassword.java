package com.examw.netplatform.model.admin.settings;

import java.io.Serializable;
/**
 * 导出账号密码。
 * 
 * @author yangyong
 * @since 2014年11月29日
 */
public interface IAccountPassword extends Serializable {
	/**
	 * 获取账号。
	 * @return 账号。
	 */
	String getAccount();
	/**
	 * 设置账号。
	 * @param account
	 * 账号。
	 */
	void setAccount(String account);
	/**
	 * 获取密码。
	 * @return 密码。
	 */
	String getPassword();
	/**
	 * 设置密码。
	 * @param password
	 * 密码。
	 */
	void setPassword(String password);
}