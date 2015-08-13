package com.examw.netplatform.dao.admin.security;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examw.netplatform.domain.admin.security.User;

/**
 * 用户数据接口。
 * @author yangyong.
 * @since 2014-05-08.
 */
public interface UserMapper {
	/**
	 * 获取用户。
	 * @param id
	 * @return
	 */
	User getUser(String id);
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<User> findUsers(User info);
	/**
	 * 是否存在账号用户。
	 * @param account
	 * @return
	 */
	boolean hasUserByAccount(String account);
	/**
	 * 根据机构ID和用户账号查找用户。
	 * @param agencyId
	 * @param account
	 * @return
	 */
	User findByAccount(@Param("agencyId")String agencyId, @Param("account")String account);
	/**
	 * 机构账号是否存在。
	 * @param agencyId
	 * @param account
	 * @return
	 */
	boolean hasAgencyAccount(@Param("agencyId")String agencyId, @Param("account")String account);
	/**
	 * 是否存在用户机构。
	 * @param userId
	 * @param agencyId
	 * @return
	 */
	boolean hasUserAgency(@Param("userId")String userId, @Param("agencyId")String agencyId);
	/**
	 * 插入用户。
	 * @param data
	 */
	void insertUser(User data);
	/**
	 * 更新用户。
	 * @param data
	 */
	void updateUser(User data);
	/**
	 * 删除用户。
	 * @param data
	 */
	void deleteUser(String id);
	/**
	 * 插入用户机构。
	 * @param agencyId
	 * @param userId
	 * @param identity
	 */
	void insertUserAgency(@Param("userId")String userId, @Param("agencyId")String agencyId,  @Param("identity")int identity);
	/**
	 * 更新用户机构身份。
	 * @param userId
	 * @param agencyId
	 * @param identity
	 */
	void updateUserAgencyIdentity(@Param("userId")String userId, @Param("agencyId")String agencyId,  @Param("identity")int identity);
	/**
	 * 删除用户机构。
	 * @param userId
	 * @param agencyId
	 */
	void deleteUserAgency(@Param("userId")String userId, @Param("agencyId")String agencyId);
	/**
	 * 是否存在用户角色
	 * @param userId
	 * @param roleId
	 * @return
	 */
	boolean hasUserRole(@Param("userId")String userId,@Param("roleId")String roleId);
	/**
	 * 插入用户角色。
	 * @param userId
	 * @param roleId
	 */
	void insertUserRole(@Param("userId")String userId,@Param("roleId")String roleId);
	/**
	 * 删除用户角色。
	 * @param userId
	 * @param roleId
	 */
	void deleteUserRole(@Param("userId")String userId,@Param("roleId")String roleId);
}