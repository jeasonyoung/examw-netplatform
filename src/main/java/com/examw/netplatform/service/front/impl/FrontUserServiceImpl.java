package com.examw.netplatform.service.front.impl;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.security.IUserDao;
import com.examw.netplatform.dao.admin.settings.IAgencyUserDao;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.model.admin.security.UserInfo;
import com.examw.netplatform.model.front.FrontUserInfo;
import com.examw.netplatform.service.front.IFrontUserService;
import com.examw.netplatform.support.PasswordHelper;
import com.examw.service.Status;

/**
 * 前台用户服务接口实现类
 * @author fengwei.
 * @since 2015年1月20日 下午4:35:35.
 */
public class FrontUserServiceImpl implements IFrontUserService{
	private static final Logger logger = Logger.getLogger(FrontUserServiceImpl.class);
	private IAgencyUserDao agencyUserDao;
	private PasswordHelper passwordHelper;
	private IUserDao userDao;
	/**
	 * 设置 机构用户数据接口
	 * @param agencyUserDao
	 * 
	 */
	public void setAgencyUserDao(IAgencyUserDao agencyUserDao) {
		this.agencyUserDao = agencyUserDao;
	}
	
	/**
	 * 设置密码工具。
	 * @param passwordHelper
	 */
	public void setPasswordHelper(PasswordHelper passwordHelper) {
		if(logger.isDebugEnabled()) logger.debug("注入密码工具...");
		this.passwordHelper = passwordHelper;
	}
	
	/**
	 * 设置 用户数据接口
	 * @param userDao
	 * 
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	/*
	 * 登录
	 * @see com.examw.netplatform.service.front.user.IFrontUserService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public AgencyUser login(String username, String password) throws Exception {
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
		{
			throw new RuntimeException("用户名或密码为空");
		}
		AgencyUser user = this.agencyUserDao.loadStudent(username);
		if(user == null)
		{
			throw new RuntimeException("此用户不存在");
		}
		if(user.getUser().getStatus().equals(Status.DISABLE))
		{
			throw new RuntimeException("用户账号被禁用");
		}
		if(!password.equals(this.passwordHelper.decryptAESPassword(user.getUser())))
		{
			throw new RuntimeException("用户名或密码错误");
		}
		return user;
	}
	
	/*
	 * 注册
	 * @see com.examw.netplatform.service.front.user.IFrontUserService#register(com.examw.netplatform.model.front.FrontUser)
	 */
	@Override
	public boolean register(FrontUserInfo user) throws Exception {
		
		return false;
	}
	
	/*
	 * 更新用户信息
	 * @see com.examw.netplatform.service.front.user.IFrontUserService#updateInfo(com.examw.netplatform.model.front.FrontUserInfo)
	 */
	@Override
	public User updateInfo(FrontUserInfo info) throws Exception{
		User user = this.userDao.load(User.class, info.getId());
		if(user == null) throw new RuntimeException("用户不存在");
		user.setPhone(info.getPhone());
		user.setName(info.getName());
		user.setEmail(info.getEmail());
		return user;
	}
	/*
	 * 更新用户密码
	 * @see com.examw.netplatform.service.front.user.IFrontUserService#updatePwd(java.lang.String, java.lang.String)
	 */
	@Override
	public User updatePwd(String id,String oldPwd,final String newPwd) throws Exception{
		final User user = this.userDao.load(User.class, id);
		if(user == null) throw new RuntimeException("用户不存在");
		if(!this.passwordHelper.decryptAESPassword(user).equals(oldPwd))
		{
			throw new RuntimeException("原密码不正确");
		}
		if(StringUtils.isEmpty(newPwd)||!newPwd.matches("[A-Z0-9a-z]{6,15}"))
		{
			throw new RuntimeException("新密码不合法");
		}
		user.setPassword(this.passwordHelper.encryptAESPassword(new UserInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getAccount() { return user.getAccount();}
			@Override
			public String getPassword() { return newPwd;}
		}));
		return user;
	}
}
