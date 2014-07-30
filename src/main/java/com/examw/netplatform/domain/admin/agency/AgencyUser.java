package com.examw.netplatform.domain.admin.agency;

import java.io.Serializable;
import java.util.Date;

import com.examw.netplatform.domain.admin.security.User;
/**
 * 机构用户。
 * @author yangyong.
 * @since 2014-06-19.
 */
public class AgencyUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private Agency agency;
	private User user;
	private Integer identity;
	private Date createTime,lastTime;
	/**
	 * 机构管理员。
	 */
	public static final Integer IDENTITY_USER = 1;
	/**
	 * 机构教师。
	 */
	public static final Integer IDENTITY_TEACHER = 2;
	/**
	 * 机构学员。
	 */
	public static final Integer IDENTITY_STUDENT = 3;
	/**
	 * 学习卡用户
	 */
	public static final Integer IDENTITY_CARD = 4;
	/**
	 * 获取机构用户ID。
	 * @return 机构用户ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置机构用户ID。
	 * @param id
	 * 机构用户ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取所属机构。
	 * @return 所属机构。
	 */
	public Agency getAgency() {
		return agency;
	}
	/**
	 * 设置所属机构。
	 * @param agency
	 * 所属机构。
	 */
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	/**
	 * 获取所属用户。
	 * @return 所属用户。
	 */
	public User getUser() {
		return user;
	}
	/**
	 * 设置所属用户。
	 * @param user
	 * 所属用户。
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * 获取机构用户身份。
	 * @return 机构用户身份。
	 */
	public Integer getIdentity() {
		return identity;
	}
	/**
	 * 设置机构用户身份。
	 * @param identity
	 * 机构用户身份。
	 */
	public void setIdentity(Integer identity) {
		this.identity = identity;
	}
	/**
	 *  获取创建时间。
	 * @return 创建时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime
	 * 创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取最新修改时间。
	 * @return 最新修改时间。
	 */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置最新修改时间。
	 * @param lastTime
	 * 最新修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
}