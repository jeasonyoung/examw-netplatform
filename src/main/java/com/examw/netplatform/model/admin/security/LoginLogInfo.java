package com.examw.netplatform.model.admin.security;

import java.util.Date;

import com.examw.model.IPaging;
import com.examw.netplatform.domain.admin.security.LoginLog;
/**
 * 登录日志信息。
 * @author yangyong.
 * @since 2014-05-17.
 */
//@JsonSerialize(include = Inclusion.NON_NULL)
public class LoginLogInfo extends LoginLog implements IPaging {
	private static final long serialVersionUID = 1L;
	private Integer page,rows;
	private String order,sort,userAccount,userName;
	/**
	 * 构造函数。
	 */
	public LoginLogInfo(){
		this.setCreateTime(new Date());
	}
	/**
	 * 构造函数。
	 * @param account
	 * 账号。
	 * @param ip
	 * IP地址。
	 * @param browser
	 * 浏览器信息。
	 */
	public LoginLogInfo(String userId,String ip,String browser){
		this();
		this.setUserId(userId);
		this.setIp(ip);
		this.setBrowser(browser);
	}
	/**
	 * 获取用户账号。
	 * @return 用户账号。
	 */
	public String getUserAccount() {
		return userAccount;
	}
	/**
	 * 设置用户账号。
	 * @param userAccount 
	 *	  用户账号。
	 */
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	/**
	 * 获取用户姓名。
	 * @return 用户姓名。
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置用户姓名。
	 * @param userName 
	 *	  用户姓名。
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/*
	 * 获取页码。
	 * @see com.examw.model.IPaging#getPage()
	 */
	@Override
	public Integer getPage() {
		return this.page;
	}
	/*
	 * 设置页码。
	 * @see com.examw.model.IPaging#setPage(java.lang.Integer)
	 */
	@Override
	public void setPage(Integer page) {
		this.page = page;
	}
	/*
	 * 获取页数据。
	 * @see com.examw.model.IPaging#getRows()
	 */
	@Override
	public Integer getRows() {
		return this.rows;
	}
	/*
	 * 设置页数据。
	 * @see com.examw.model.IPaging#setRows(java.lang.Integer)
	 */
	@Override
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	/*
	 * 获取排序字段。
	 * @see com.examw.model.IPaging#getOrder()
	 */
	@Override
	public String getOrder() {
		return this.order;
	}
	/*
	 * 设置排序字段。
	 * @see com.examw.model.IPaging#setOrder(java.lang.String)
	 */
	@Override
	public void setOrder(String order) {
		 this.order = order;
	}
	/*
	 * 获取排序方式。
	 * @see com.examw.model.IPaging#getSort()
	 */
	@Override
	public String getSort() {
		return this.sort;
	}
	/*
	 * 设置排序方式。
	 * @see com.examw.model.IPaging#setSort(java.lang.String)
	 */
	@Override
	public void setSort(String sort) {
		this.sort = sort;
	}
}