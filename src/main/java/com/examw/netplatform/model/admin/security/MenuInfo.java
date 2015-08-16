package com.examw.netplatform.model.admin.security;

import com.examw.netplatform.domain.admin.security.MenuEntity;
/**
 * 菜单信息。
 * @author yangyong.
 * @since 2014-04-28.
 */
//@JsonSerialize(include = Inclusion.NON_NULL)
public class MenuInfo extends MenuEntity{
	private static final long serialVersionUID = 1L;
	/**
	 * 构造函数。
	 */
	public MenuInfo(){}
	/**
	 * 构造函数。
	 * @param id
	 * @param icon
	 * @param name
	 * @param uri
	 * @param orderNo
	 */
	public MenuInfo(String id,String icon,String name, String uri,Integer orderNo){
		this.setId(id);
		this.setIcon(icon);
		this.setName(name);
		this.setUri(uri);
		this.setOrderNo(orderNo);
	}
}