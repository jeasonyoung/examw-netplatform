package com.examw.netplatform.service.admin.security;

import com.examw.model.DataGrid;
import com.examw.netplatform.model.admin.security.MenuRightInfo;

/**
 * 菜单权限服务。
 * @author yangyong.
 * @since 2014-05-04.
 */
public interface IMenuRightService {
	/**
	 * 菜单权限初始化。
	 * @throws Exception
	 */
	void init() throws Exception;
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	DataGrid<MenuRightInfo> datagrid(MenuRightInfo info);
	/**
	 * 更新数据。
	 * @param info
	 */
	void update(MenuRightInfo info);
	/**
	 * 删除菜单权限
	 * @param ids
	 */
	void delete(String [] ids);
}