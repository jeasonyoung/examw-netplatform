package com.examw.netplatform.dao.admin.security;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.security.Menu;
import com.examw.netplatform.model.admin.security.MenuInfo;
/**
 * 菜单数据操作接口。
 * @author yangyong.
 *	@since 2014-04-28.
 */
public interface IMenuDao extends IBaseDao<Menu> {
	/**
	 * 加载一级菜单集合。
	 * @return
	 */
	List<Menu> loadTopMenus();
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 菜单集合。
	 */
	List<Menu> findMenus(MenuInfo info);
}