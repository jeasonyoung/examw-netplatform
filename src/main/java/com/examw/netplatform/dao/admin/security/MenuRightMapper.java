package com.examw.netplatform.dao.admin.security;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examw.netplatform.domain.admin.security.MenuPermission;
import com.examw.netplatform.domain.admin.security.MenuRight;

/**
 * 菜单权限数据访问接口。
 * @author yangyong.
 * @since 2014-05-04.
 */
public interface MenuRightMapper {
	/**
	 * 获取菜单权限数据。
	 * @param menuRightId
	 * @return
	 */
	MenuRight getMenuRight(String menuRightId);
	/**
	 * 加载菜单权限数据。
	 * @param menuId
	 * 菜单ID。
	 * @param rightId
	 * 权限ID。
	 * @return
	 * 菜单权限数据。
	 */
	MenuRight loadMenuRight(@Param("menuId")String menuId, @Param("rightId")String rightId);
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	List<MenuRight> findMenuRights(MenuRight info);
	/**
	 * 查询全部菜单及其权限集合。
	 * @return
	 */
	List<MenuPermission> findMenuPermissions(MenuRight info);
	/**
	 * 查询角色下的菜单权限集合。
	 * @param roleId
	 * @return
	 */
	List<MenuRight> findMenuRightsByRole(String roleId);
	/**
	 * 插入菜单权限数据。
	 * @param data
	 */
	void insertMenuRight(MenuRight data);
	/**
	 * 更新菜单权限数据。
	 * @param data
	 */
	void updateMenuRight(MenuRight data);
	/**
	 * 删除菜单权限数据。
	 * @param id
	 */
	void deleteMenuRight(String id);
}