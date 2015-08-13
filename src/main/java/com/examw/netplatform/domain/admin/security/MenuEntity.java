package com.examw.netplatform.domain.admin.security;

import org.apache.commons.lang3.StringUtils;


/**
 * 菜单实体类。
 * 
 * @author jeasonyoung
 * @since 2015年8月10日
 */
public class MenuEntity extends Menu {
	private static final long serialVersionUID = 1L;
	private String pid;
	/**
	 * 获取上级菜单ID。
	 * @return 上级菜单ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置上级菜单ID。
	 * @param pid 
	 *	  上级菜单ID。
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/*
	 * 重载。
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if(StringUtils.isBlank(this.getId()))
			return super.hashCode();
		return super.hashCode() + this.getId().hashCode();
	}
}