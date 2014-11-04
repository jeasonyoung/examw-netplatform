package com.examw.netplatform.model.admin.security;

import java.io.Serializable;
/**
 * 菜单权限提交信息。
 * 
 * @author yangyong
 * @since 2014年11月4日
 */
public class MenuRightPost implements Serializable {
	private static final long serialVersionUID = 1L;
	private String[] menuId,rightId;
	/**
	 * 获取菜单ID数组。
	 * @return 菜单ID数组。
	 */
	public String[] getMenuId() {
		return menuId;
	}
	/**
	 * 设置菜单ID数组。
	 * @param menuId 
	 *	  菜单ID数组。
	 */
	public void setMenuId(String[] menuId) {
		this.menuId = menuId;
	}
	/**
	 * 获取权限ID数组。
	 * @return 权限ID数组。
	 */
	public String[] getRightId() {
		return rightId;
	}
	/**
	 * 设置权限ID数组。
	 * @param rightId 
	 *	  权限ID数组。
	 */
	public void setRightId(String[] rightId) {
		this.rightId = rightId;
	}
}