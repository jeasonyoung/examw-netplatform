package com.examw.netplatform.domain.admin.security;
/**
 * 菜单及其权限数据。
 * 
 * @author jeasonyoung
 * @since 2015年8月17日
 */
public class MenuPermission extends MenuRight {
	private static final long serialVersionUID = 1L;
	private String pid,name,type;
	/**
	 * 获取名称。
	 * @return 名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置名称。
	 * @param name 
	 *	  名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取上级ID。
	 * @return 上级ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置上级ID。
	 * @param pid 
	 *	  上级ID。
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取类型。
	 * @return 类型。
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置类型。
	 * @param type 
	 *	  类型。
	 */
	public void setType(String type) {
		this.type = type;
	}
}