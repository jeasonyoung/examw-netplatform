package com.examw.netplatform.domain.admin.teacher;

import java.io.Serializable;

import com.examw.netplatform.domain.admin.agency.Agency;
import com.examw.netplatform.domain.admin.agency.AgencyUser;
import com.examw.netplatform.domain.admin.security.User;

/**
 * 教师数据
 * @author fengwei.
 * @since 2014年5月29日 下午3:11:26.
 */
public class Teacher implements Serializable{
	private static final long serialVersionUID = 1L;
	private User user;
	private Agency agency;
	private String createUsername,description;
	private Integer orderNo;
	private Integer identity = AgencyUser.IDENTITY_TEACHER;	//固定为老师
	/**
	 * 获取 用户
	 * @return user
	 * 
	 */
	public User getUser() {
		return user;
	}
	/**
	 * 设置 用户
	 * @param user
	 * 
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * 获取 机构
	 * @return agency
	 * 
	 */
	public Agency getAgency() {
		return agency;
	}
	/**
	 * 设置 机构
	 * @param agency
	 * 
	 */
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	/**
	 * 获取 创建人
	 * @return createUsername
	 * 
	 */
	public String getCreateUsername() {
		return createUsername;
	}
	/**
	 * 设置 创建人
	 * @param createUsername
	 * 
	 */
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}
	/**
	 * 获取 教师简介
	 * @return description
	 * 
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置 教师简介
	 * @param description
	 * 
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取 排序号
	 * @return orderNo
	 * 
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置 排序号
	 * @param orderNo
	 * 
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取 身份
	 * @return identity
	 * 
	 */
	public Integer getIdentity() {
		return identity;
	}
	/**
	 * 设置 身份
	 * @param identity
	 * 
	 */
	public void setIdentity(Integer identity) {
		this.identity = identity;
	}
	
}
