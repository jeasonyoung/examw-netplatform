package com.examw.netplatform.model.admin;
/**
 * 当前用户接口。
 * @author yangyong.
 * @since 2014-07-16.
 */
public interface IUser {
	/**
	 * 获取当前用户ID。
	 * @return 当前用户ID。
	 */
	String getCurrentUserId();
	/**
	 * 设置当前用户ID。
	 * @param currentUserId
	 * 当前用户ID。
	 */
	void setCurrentUserId(String currentUserId);
}