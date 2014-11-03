package com.examw.netplatform.service.admin.security;

import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.model.admin.security.UserInfo;
import com.examw.netplatform.service.IBaseDataService;
/**
 * 用户服务接口。
 * @author yangyong.
 * @since 2014-05-08.
 */
public interface IUserService extends IBaseDataService<UserInfo> {
	/**
	 * 加载用户状态名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载性别名称。
	 * @param gender
	 * 性别值。
	 * @return
	 * 性别名称。
	 */
	String loadGenderName(Integer gender);
	/**
	 * 数据模型转换。
	 * @param data
	 * 用户数据。
	 * @param isViewPwd
	 * 是否显示明文密码。
	 * @return
	 */
	UserInfo conversion(User data,boolean isViewPwd);
	/**
	 * 修改用户密码。
	 * @param userId
	 * 用户ID。
	 * @param oldPassword
	 * 旧密码。
	 * @param newPassword
	 * 新密码。
	 * @throws Exception
	 */
	void modifyPassword(String userId,String oldPassword,String newPassword) throws Exception;
	/**
	 * 初始化用户。
	 * @param roleId
	 * 角色ID。
	 * @param account
	 * 账号。
	 * @param password
	 * 密码。
	 * @throws Exception
	 */
	void init(String roleId,String account, String password) throws Exception;
}