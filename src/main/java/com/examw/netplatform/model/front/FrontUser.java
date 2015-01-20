package com.examw.netplatform.model.front;

import java.io.Serializable;

import org.springframework.util.StringUtils;

/**
 * 前台用户模型
 * @author fengwei.
 * @since 2015年1月20日 下午4:14:19.
 */
public class FrontUser implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String username,password,repeat,name,phone,email;

	/**
	 * 获取 用户名
	 * @return username
	 * 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置 用户名
	 * @param username
	 * 用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取 密码
	 * @return password
	 * 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置 密码
	 * @param password
	 * 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取 重复密码
	 * @return repeat
	 * 重复密码
	 */
	public String getRepeat() {
		return repeat;
	}

	/**
	 * 设置 重复密码
	 * @param repeat
	 * 重复密码
	 */
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	/**
	 * 获取 姓名
	 * @return name
	 * 姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置 姓名
	 * @param name
	 * 姓名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取 手机
	 * @return phone
	 * 手机
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置 手机
	 * @param phone
	 * 手机
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取 邮箱
	 * @return email
	 * 邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置 邮箱
	 * @param email
	 * 邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 检测数据合法性
	 * @return
	 * @throws Exception
	 */
	public boolean check()throws Exception
	{
		if(StringUtils.isEmpty(username)||!username.matches("[A-Z0-9a-z_]{4,20}"))
		{
			throw new RuntimeException("用户名不合法");
		}
		if(StringUtils.isEmpty(password)||!password.matches("[A-Z0-9a-z]{6,15}"))
		{
			throw new RuntimeException("密码不合法");
		}
		if(!password.equals(repeat))
		{
			throw new RuntimeException("两次密码输入不一致");
		}
		if(StringUtils.isEmpty(name)||!name.matches("[\u4e00-\u9fa5]{2,6}"))
		{
			throw new RuntimeException("姓名不合法");
		}
		if(StringUtils.isEmpty(phone)||!phone.matches("1[3,4,5,6,7,8][0-9]{9}"))
		{
			throw new RuntimeException("手机号不合法");
		}
		if(StringUtils.isEmpty(email)||!email.matches("[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+"))
		{
			throw new RuntimeException("邮箱不合法");
		}
		return true;
	}
	@Override
	public String toString() {
		return String.format("username=[%1$s];password=[%2$s];name=[%3$s];phone=[%4$s];email=[%5$s];", username,password,name,phone,email);
	}
}
