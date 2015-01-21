package com.examw.netplatform.service.front.user.impl;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.settings.IAgencyUserDao;
import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.model.front.FrontUser;
import com.examw.netplatform.service.front.user.IFrontUserService;
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
	public boolean register(FrontUser user) throws Exception {
		
		return false;
	}
	
}
