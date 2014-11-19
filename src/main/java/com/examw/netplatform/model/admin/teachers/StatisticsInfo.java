package com.examw.netplatform.model.admin.teachers;
/**
 * 答疑统计信息
 * @author fengwei.
 * @since 2014年6月17日 下午3:06:45.
 */
public class StatisticsInfo {
	private String userId,userName,userAccount,agencyId,agencyName;
	private Long totalCount,answeredCount;
	/**
	 * 获取 老师ID
	 * @return userId
	 * 老师ID
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置 老师ID
	 * @param userId
	 * 老师ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取 老师姓名
	 * @return userName
	 * 老师姓名
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置 老师姓名
	 * @param userName
	 * 老师姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取 老师帐号
	 * @return userAccount
	 * 老师帐号
	 */
	public String getUserAccount() {
		return userAccount;
	}
	/**
	 * 设置 老师帐号
	 * @param userAccount
	 * 老师帐号
	 */
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	/**
	 * 获取 问题总数
	 * @return totalCount
	 * 问题总数
	 */
	public Long getTotalCount() {
		return totalCount;
	}
	/**
	 * 设置 问题总数
	 * @param totalCount
	 * 问题总数
	 */
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * 获取 总回答数
	 * @return answeredCount
	 * 总回答数
	 */
	public Long getAnsweredCount() {
		return answeredCount;
	}
	/**
	 * 设置 总回答数
	 * @param answeredCount
	 * 总回答数
	 */
	public void setAnsweredCount(Long answeredCount) {
		this.answeredCount = answeredCount;
	}
	/**
	 * 获取 所在机构ID
	 * @return agencyId
	 * 所在机构ID
	 */
	public String getAgencyId() {
		return agencyId;
	}
	/**
	 * 设置 所在机构ID
	 * @param agencyId
	 * 所在机构ID
	 */
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	/**
	 * 获取 所在机构名称
	 * @return agencyName
	 * 所在机构名称
	 */
	public String getAgencyName() {
		return agencyName;
	}
	/**
	 * 设置 所在机构名称
	 * @param agencyName
	 * 所在机构名称
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	
}
