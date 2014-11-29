package com.examw.netplatform.model.admin.students;

import java.io.Serializable;

/**
 * 批量学生用户信息。
 * 
 * @author yangyong
 * @since 2014年11月29日
 */
public class BatchStudentInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String agencyId, prefix;
	private Integer count,status,identity,passwordLength;
	/**
	 * 获取机构ID。
	 * @return 机构ID。
	 */
	public String getAgencyId() {
		return agencyId;
	}
	/**
	 * 设置机构ID。
	 * @param agencyId 
	 *	  机构ID。
	 */
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	/**
	 * 获取账号前缀。
	 * @return 账号前缀。
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * 设置账号前缀。
	 * @param prefix 
	 *	  账号前缀。
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	/**
	 * 获取账号总数。
	 * @return 账号总数。
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * 设置账号总数。
	 * @param count 
	 *	  账号总数。
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * 获取身份值。
	 * @return 身份值。
	 */
	public Integer getIdentity() {
		return identity;
	}
	/**
	 * 设置身份值。
	 * @param identity 
	 *	  身份值。
	 */
	public void setIdentity(Integer identity) {
		this.identity = identity;
	}
	/**
	 * 获取密码长度。
	 * @return 密码长度。
	 */
	public Integer getPasswordLength() {
		return passwordLength;
	}
	/**
	 * 设置密码长度。
	 * @param passwordLength 
	 *	  密码长度。
	 */
	public void setPasswordLength(Integer passwordLength) {
		this.passwordLength = passwordLength;
	}
	/**
	 * 获取状态值。
	 * @return 状态值。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置状态值。
	 * @param status 
	 *	  状态值。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
}