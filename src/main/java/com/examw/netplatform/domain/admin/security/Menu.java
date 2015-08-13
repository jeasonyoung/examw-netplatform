package com.examw.netplatform.domain.admin.security;

import java.io.Serializable;

/**
 * 菜单数据
 * @author yangyong.
 * @since 2014-04-28.
 */
public class Menu implements Serializable,Comparable<Menu> {
	private static final long serialVersionUID = 1L;
	private String id,icon,name,uri;
	private int orderNo;
	/**
	 * 获取菜单ID。
	 * @return 菜单ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置菜单ID。
	 * @param id 
	 *	 菜单ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取菜单图标。
	 * @return 菜单图标。
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * 设置菜单图标。
	 * @param icon 
	 *	  菜单图标。
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * 获取菜单名称。
	 * @return 菜单名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置菜单名称。
	 * @param name 
	 *	  菜单名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取菜单Uri。
	 * @return 菜单Uri。
	 */
	public String getUri() {
		return uri;
	}
	/**
	 * 设置菜单Uri。
	 * @param uri 
	 *	  菜单Uri。
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	/**
	 * 获取菜单序号。
	 * @return 菜单序号。
	 */
	public int getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置菜单序号。
	 * @param orderNo 
	 *	  菜单序号。
	 */
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	/*
	 * 重载比较排序。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Menu o) {
		return this.orderNo - o.orderNo;
	}
}