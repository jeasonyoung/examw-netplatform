package com.examw.netplatform.domain.admin.security;

import java.io.Serializable;
import java.util.Set;

import com.examw.netplatform.domain.admin.settings.Agency;
/**
 * 角色。
 * @author yangyong.
 * @since 2014-05-05.
 */
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,description;
	private Integer status;
	private Set<User> users;
	private Set<MenuRight> rights;
	private Set<Agency> agencies;
	/**
	 * 获取角色ID。
	 * @return 角色ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置角色ID。
	 * @param id
	 * 角色ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取角色名称。
	 * @return 角色名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置角色名称。
	 * @param name
	 * 角色名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取状态。
	 * @return 状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置状态。
	 * @param status
	 * 状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取描述信息。
	 * @return  描述信息。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置描述信息。
	 * @param description
	 * 描述信息。
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取关联用户集合。
	 * @return 关联用户集合。
	 */
	public Set<User> getUsers() {
		return users;
	}
	/**
	 * 设置关联用户集合。
	 * @param users
	 * 关联用户集合。
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	/**
	 * 获取关联菜单权限集合。
	 * @return 关联菜单权限集合。
	 */
	public Set<MenuRight> getRights() {
		return rights;
	}
	/**
	 * 设置关联菜单权限集合。
	 * @param rights
	 * 关联菜单权限集合。
	 */
	public void setRights(Set<MenuRight> rights) {
		this.rights = rights;
	}
	/**
	 * 获取关联机构集合。
	 * @return 关联机构集合。
	 */
	public Set<Agency> getAgencies() {
		return agencies;
	}
	/**
	 * 设置关联机构集合。
	 * @param agencies 
	 *	  关联机构集合。
	 */
	public void setAgencies(Set<Agency> agencies) {
		this.agencies = agencies;
	}
}