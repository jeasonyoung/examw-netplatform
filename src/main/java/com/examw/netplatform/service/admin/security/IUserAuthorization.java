package com.examw.netplatform.service.admin.security;

import java.util.Set;

import com.examw.netplatform.domain.admin.security.User;

/**
 * 用户授权接口。
 * 
 * @author yangyong
 * @since 2014年11月3日
 */
public interface IUserAuthorization {
	/**
	 * 加载用户。
	 * @param userId
	 * @return
	 */
	User getUser(String userId);
	/**
	 * 加载机构账号下的用户。
	 * @param agencyId
	 * @param account
	 * @return
	 */
	User loadUserByAccount(String agencyId, String account);
	/**
	 * 加载用户ID下角色ID集合。
	 * @param userId
	 * @return
	 */
	Set<String> findRolesByUser(String userId);
	/**
	 * 加载用户ID下权限集合。
	 * @param userId
	 * @return
	 */
	Set<String> findPermissionsByUser(String userId);
}