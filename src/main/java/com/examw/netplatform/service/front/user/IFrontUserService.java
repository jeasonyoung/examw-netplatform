package com.examw.netplatform.service.front.user;

import com.examw.netplatform.model.front.FrontUser;

/**
 * 前台用户服务接口
 * @author fengwei.
 * @since 2015年1月20日 下午4:11:47.
 */
public interface IFrontUserService {
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	boolean login(String username,String password) throws Exception;
	/**
	 * 用户注册
	 * @param user
	 * @return
	 * @throws Exception
	 */
	boolean register(FrontUser user)throws Exception;
}
