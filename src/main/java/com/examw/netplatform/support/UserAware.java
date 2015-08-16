package com.examw.netplatform.support;

/**
 * 注入用户信息。
 * 
 * @author jeasonyoung
 * @since 2015年8月16日
 */
public interface UserAware {
	/**
	 * 设置所属机构ID。
	 * @param agencyId
	 */
	void setAgencyId(String agencyId);
	/**
	 * 设置当前用户ID。
	 * @param userId
	 */
	void setUserId(String userId);
}