package com.examw.netplatform.service.admin.security;

import java.util.List;

import com.examw.model.DataGrid;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.model.admin.security.UserInfo;
/**
 * 用户服务接口。
 * @author yangyong.
 * @since 2014-05-08.
 */
public interface IUserService {
	/**
	 * 加载性别名称。
	 * @param gender
	 * 性别值。
	 * @return
	 * 性别名称。
	 */
	String loadGenderName(Integer gender);
	/**
	 * 加载用户类型名称。
	 * @param type
	 * 用户类型。
	 * @return
	 * 用户类型名称。
	 */
	String loadTypeName(Integer type);
	/**
	 * 加载用户状态名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载身份名称。
	 * @param identity
	 * 身份值。
	 * @return
	 * 身份名称。
	 */
	String loadIdentityName(Integer identity);
	/**
	 * 加载用户ID的用户名。
	 * @param userId
	 * 用户ID。
	 * @return
	 */
	String loadUserName(String userId);
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	DataGrid<UserInfo> datagrid(UserInfo info);
	/**
	 * 加载订单下用户集合。
	 * @param orderId
	 * @return
	 */
	List<UserInfo> findUsersByOrder(String orderId);
	/**
	 * 更新用户。
	 * @param info
	 * @return
	 */
	UserInfo update(UserInfo info);
	/**
	 * 删除数据。
	 * @param ids
	 */
	void delete(String[] ids);
	/**
	 * 更新用户。
	 * @param info
	 * 用户信息。
	 * @return
	 * 用户数据。
	 */
	User updateUser(UserInfo info);
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
	/**
	 * 删除用户。
	 * @param userId
	 */
	void deleteUser(String userId);
}