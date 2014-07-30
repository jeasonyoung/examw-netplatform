package com.examw.netplatform.model.admin.agency;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
import com.examw.netplatform.model.admin.IUser;
import com.examw.netplatform.support.CustomDateSerializer;

/**
 * 机构用户信息。
 * @author yangyong.
 * @since 2014-07-08.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AgencyUserInfo extends Paging implements IUser {
	private static final long serialVersionUID = 1L;
	private String id,agencyId,agencyName,userId,userName,identityName,account,password,nickName,imgUrl,phone,qq,email,genderName,statusName,currentUserId;
	private Integer identity,gender,status;
	private String[] roleId;
	private Date createTime,lastTime;
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
	 * 获取所属机构ID。
	 * @return 所属机构ID。
	 */
	public String getAgencyId() {
		return agencyId;
	}
	/**
	 * 设置所属机构ID。
	 * @param agencyId
	 * 所属机构ID。
	 */
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	/**
	 * 获取所属机构名称。
	 * @return 所属机构名称。
	 */
	public String getAgencyName() {
		return agencyName;
	}
	/**
	 * 设置所属机构名称。
	 * @param agencyName
	 * 所属机构名称。
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	/**
	 * 获取所属用户ID。
	 * @return 所属用户ID。
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置所属用户ID。
	 * @param userId
	 * 所属用户ID。
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取所属用户名称。
	 * @return 所属用户名称。
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置所属用户名称。
	 * @param userName
	 * 所属用户名称。
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取性别。
	 * @return 性别。
	 */
	public Integer getGender() {
		return gender;
	}
	/**
	 * 设置性别。
	 * @param gender
	 * 性别。
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	/**
	 * 获取性别名称。
	 * @return 性别名称。
	 */
	public String getGenderName() {
		return genderName;
	}
	/**
	 * 设置性别名称。
	 * @param genderName
	 * 性别名称。
	 */
	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}
	/**
	 * 获取用户账号。
	 * @return 用户账号。
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置用户账号。
	 * @param account
	 * 用户账号。
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取用户密码。
	 * @return 用户密码。
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置用户密码。
	 * @param password
	 * 用户密码。
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取所属身份。
	 * @return 所属身份。
	 */
	public Integer getIdentity() {
		return identity;
	}
	/**
	 * 设置所属身份。
	 * @param identity
	 * 所属身份。
	 */
	public void setIdentity(Integer identity) {
		this.identity = identity;
	}
	/**
	 * 获取所属身份名称。
	 * @return  所属身份名称。
	 */
	public String getIdentityName() {
		return identityName;
	}
	/**
	 * 设置所属身份名称。
	 * @param identityName
	 * 所属身份名称。
	 */
	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}
	/**
	 * 获取用户昵称。
	 * @return 用户昵称。
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * 设置用户昵称。
	 * @param nickName
	 * 用户昵称。
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 *  获取用户图片URL。
	 * @return 用户图片URL。
	 */
	public String getImgUrl() {
		return imgUrl;
	}
	/**
	 * 设置用户图片URL。
	 * @param imgUrl
	 * 用户图片URL。
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 * 获取用户电话号码。
	 * @return 用户电话号码。
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置用户电话号码。
	 * @param phone
	 * 用户电话号码。
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取用户QQ。
	 * @return 用户QQ。
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * 设置用户QQ。
	 * @param qq
	 * 用户QQ。
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * 获取用户邮箱地址。
	 * @return 用户邮箱地址。
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置用户邮箱地址。
	 * @param email
	 * 用户邮箱地址。
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取用户状态。
	 * @return 用户状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置用户状态。
	 * @param status
	 * 用户状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取用户状态名称。
	 * @return
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置用户状态名称。
	 * @param statusName
	 * 用户状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取所属角色集合。
	 * @return 所属角色集合。
	 */
	public String[] getRoleId() {
		return roleId;
	}
	/**
	 * 设置所属角色集合。
	 * @param roleId
	 * 所属角色集合。
	 */
	public void setRoleId(String[] roleId) {
		this.roleId = roleId;
	}
	/**
	 *  获取创建时间。
	 * @return 创建时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
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
	@JsonSerialize(using = CustomDateSerializer.class)
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
	/**
	 * 获取当前用户ID。
	 * @return 当前用户ID。
	 */
	public String getCurrentUserId() {
		return currentUserId;
	}
	/**
	 * 设置当前用户ID。
	 * @param currentUserId
	 * 当前用户ID。
	 */
	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}
}