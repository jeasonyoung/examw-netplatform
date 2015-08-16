package com.examw.netplatform.service.admin.security;

import java.util.List;

import com.examw.netplatform.model.admin.security.MenuInfo;

/**
 * 菜单服务接口。
 * @author yangyong.
 * @since 2014-04-26.
 */
public interface IMenuService { 
	/**
	 * 加载系统名称。
	 * @return
	 * 系统名称。
	 */
	String loadSystemName();
	/**
	 * 加载全部菜单数据。
	 * @return
	 */
	List<MenuInfo> loadAllMenus();
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	List<MenuInfo> datagrid(MenuInfo info);
	/**
	 * 更新菜单数据。
	 * @param info
	 * 源菜单。
	 * @return
	 * 更新后菜单数据。
	 */
	MenuInfo update(MenuInfo info);
	/**
	 * 删除数据。
	 * @param ids
	 * 菜单ID数组。
	 */
	void delete(String[] ids);
	/**
	 * 初始化。
	 * @throws Exception
	 */
	void init() throws Exception;
}