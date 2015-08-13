package com.examw.netplatform.dao.admin.security;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examw.netplatform.domain.admin.security.Role;

/**
 * 角色数据接口。
 * @author yangyong.
 * @since 2014-05-05.
 */
public interface RoleMapper {
	/**
	 * 加载角色数据。
	 * @param id
	 * @return
	 */
	Role getRole(String id);
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Role> findRoles(Role info);
	/**
	 * 插入角色。
	 * @param role
	 */
	void insertRole(Role role);
	/**
	 * 更新角色。
	 * @param role
	 */
	void updateRole(Role role);
	/**
	 * 删除角色。
	 * @param id
	 */
	void deleteRole(String id);
	
	/**
	 * 检查角色是否有权限。
	 * @param roleId
	 * @param menuRightId
	 * @return
	 */
	boolean hasRoleRight(@Param("roleId")String roleId,@Param("menuRightId")String menuRightId);
	/**
	 * 插入角色权限。
	 * @param roleId
	 * @param menuRightId
	 */
	void insertRoleRight(@Param("roleId")String roleId,@Param("menuRightId")String menuRightId);
	/**
	 * 删除角色权限。
	 * @param roleId
	 */
	void deleteRightByRole(String roleId);
}