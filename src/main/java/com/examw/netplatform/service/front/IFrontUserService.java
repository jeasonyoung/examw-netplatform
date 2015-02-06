package com.examw.netplatform.service.front;

import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.model.front.FrontUserInfo;

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
	AgencyUser login(String username,String password) throws Exception;
	/**
	 * 用户注册
	 * @param user
	 * @return
	 * @throws Exception
	 */
	boolean register(FrontUserInfo user)throws Exception;
	/**
	 * 更新用户信息
	 * @param info
	 * @return
	 */
	User updateInfo(FrontUserInfo info) throws Exception;
	/**
	 * 更新用户密码
	 * @param id
	 * @param newPwd
	 * @return
	 */
	User updatePwd(String id,String oldPwd, String newPwd) throws Exception;
}
