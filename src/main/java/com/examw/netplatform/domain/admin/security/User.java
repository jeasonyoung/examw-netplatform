package com.examw.netplatform.domain.admin.security;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户。
 * @author yangyong.
 * @since 2014-05-08.
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String agencyId,agencyName,id,name,account,password,nickName,imgUrl,phone,qq,email;
	private Integer identity,gender,type,status;
	private Date createTime,lastTime;
	/**
	 * 构造函数。
	 */
	public User(){
		this.setCreateTime(new Date());
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
	 *	  所属机构ID。
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
	 *	  所属机构名称。
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	/**
	 * 获取用户ID。
	 * @return 用户ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置用户ID。
	 * @param id
	 * 用户ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取用户姓名。
	 * @return
	 * 用户姓名。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置用户姓名。
	 * @param name
	 * 用户姓名。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取用户昵称。
	 * @return
	 * 用户昵称。
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * 设置用户昵称。
	 * @param nickName
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * 获取用户账号。
	 * @return
	 * 用户账号。
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
	 * 设置 password
	 * @param password 
	 *	  password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取用户机构身份。
	 * @return 用户机构身份。
	 */
	public Integer getIdentity() {
		return identity;
	}
	/**
	 * 设置用户机构身份。
	 * @param identity 
	 *	  用户机构身份。
	 */
	public void setIdentity(Integer identity) {
		this.identity = identity;
	}
	/**
	 * 获取性别。
	 * @return
	 * 性别(1-男，2-女)。
	 */
	public Integer getGender() {
		return gender;
	}
	/**
	 * 设置性别(1-男，2-女)。
	 * @param gender
	 * 性别(1-男，2-女)。
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	/**
	 * 获取手机号码。
	 * @return
	 * 手机号码。
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置手机号码。
	 * @param phone
	 * 手机号码。
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取QQ。
	 * @return
	 * QQ。
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * 设置QQ。
	 * @param qq
	 * QQ。
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * 获取Email。
	 * @return
	 * Email。
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置Email。
	 * @param email
	 * Email。
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 设置用户头像图片URL。
	 * @return
	 * 用户图片URL。
	 */
	public String getImgUrl() {
		return imgUrl;
	}
	/**
	 * 设置用户头像图片URL。
	 * @param imgUrl
	 * 用户图片URL。
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 * 获取用户类型(1-后台用户，2-前台用户)。
	 * @return 用户类型(1-后台用户，2-前台用户)。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置用户类型(1-后台用户，2-前台用户)。
	 * @param type 
	 *	  用户类型(1-后台用户，2-前台用户)。
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取用户状态。
	 * @return
	 * 用户状态。
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
	 * 获取用户创建时间。
	 * @return
	 * 用户创建时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置用户创建时间。
	 * @param createTime
	 * 用户创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取修改时间。
	 * @return 修改时间。
	 */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置修改时间。
	 * @param lastTime 
	 *	  修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
}