package com.examw.netplatform.service.front.user.impl;

import com.examw.netplatform.dao.admin.settings.IAgencyUserDao;
import com.examw.netplatform.model.front.FrontUser;
import com.examw.netplatform.service.front.user.IFrontUserService;

/**
 * 
 * @author fengwei.
 * @since 2015年1月20日 下午4:35:35.
 */
public class FrontUserServiceImpl implements IFrontUserService{
	private IAgencyUserDao agencyUserDao;
	/*
	 * 登录
	 * @see com.examw.netplatform.service.front.user.IFrontUserService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean login(String username, String password) throws Exception {
		
		return false;
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
