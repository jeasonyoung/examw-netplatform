package com.examw.netplatform.model.admin.security;

import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
/**
 * 菜单信息。
 * @author yangyong.
 * @since 2014-04-28.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class MenuInfo extends Paging implements Comparable<MenuInfo>{
	private static final long serialVersionUID = 1L;
	private String pid, id,icon,name,uri;
	private Integer orderNo;
	private Set<MenuInfo> children;
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
		this();
		this.setId(id);
		this.setIcon(icon);
		this.setName(name);
		this.setUri(uri);
		this.setOrderNo(orderNo);
	}
	/**
	 * 获取上级菜单ID。
	 * @return
	 * 上级菜单ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置上级菜单ID。
	 * @param pid
	 * 上级菜单ID。
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取菜单ID。
	 * @return
	 * 菜单ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置菜单ID。
	 * @param id
	 * 菜单ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取菜单图标。
	 * @return
	 * 菜单图标。
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * 设置菜单图标。
	 * @param icon
	 * 菜单图标。
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * 获取菜单名称。
	 * @return
	 * 菜单名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置菜单名称。
	 * @param name
	 * 菜单名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取菜单Uri。
	 * @return
	 * 菜单Uri。
	 */
	public String getUri() {
		return uri;
	}
	/**
	 * 设置菜单URL。
	 * @param uri
	 * 菜单URL。
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	/**
	 * 获取排序。
	 * @return
	 * 排序。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序。
	 * @param orderNo
	 * 排序。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取子菜单集合。
	 * @return
	 *  子菜单集合。
	 */
	public Set<MenuInfo> getChildren() {
		return children;
	}
	/**
	 * 设置子菜单集合。
	 * @param children
	 * 子菜单集合。
	 */
	public void setChildren(Set<MenuInfo> children) {
		this.children = children;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(MenuInfo o) {
		int index = 0;
		if(this == o) return index;
		index = this.getOrderNo() - o.getOrderNo();
		if(index == 0){
			index = this.getName().compareToIgnoreCase(o.getName());
			if(index == 0){
				index = this.getId().compareToIgnoreCase(o.getId());
			}
		}
		return index;
	}
}