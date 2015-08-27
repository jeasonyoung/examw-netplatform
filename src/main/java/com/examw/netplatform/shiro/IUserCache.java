package com.examw.netplatform.shiro;
/**
 *  Shiro用户缓存。
 * 
 * @author yangyong
 * @since 2014年11月11日
 */
public interface IUserCache {
	/**
	 *  移除用户权限缓存。
	 * @param account
	 */
	void removeUserCache(String userId);
	/**
	 * 移除全部权限集合。
	 */
	void removeAllPermissions();
}