package com.examw.netplatform.dao.admin.security;

import java.util.List;

import com.examw.netplatform.domain.admin.security.MenuEntity;
/**
 * 菜单数据操作接口。
 * @author yangyong.
 *	@since 2014-04-28.
 */
public interface MenuMapper {
	/**
	 * 加载菜单。
	 * @param id
	 * @return
	 */
	MenuEntity getMenu(String id);
	/**
	 * 查询菜单集合。
	 * @param menu
	 * @return
	 */
	List<MenuEntity> findMenus(MenuEntity menu);
	/**
	 * 查询子菜单集合。
	 * @param id
	 * @return
	 */
	List<MenuEntity> findChildMenus(String id);
	/**
	 * 是否存在子菜单。
	 * @param id
	 * @return
	 */
	boolean hasChildMenus(String id);
	/**
	 * 插入菜单。
	 * @param entity
	 */
	void insertMenu(MenuEntity entity);
	/**
	 * 更新菜单。
	 * @param entity
	 */
	void updateMenu(MenuEntity entity);
	/**
	 * 删除菜单。
	 * @param menuId
	 * 菜单ID。
	 */
	void deleteMenu(String menuId);
}