package com.examw.netplatform.domain.admin.settings;

import java.io.Serializable;

/**
 * 考试类别。
 * @author yangyong.
 * @since 2014-08-01.
 */
public class Category implements Serializable,Comparable<Category> {
	private static final long serialVersionUID = 1L;
	private String pid,id,name,abbr;
	private Integer code;
	/**
	 * 获取上级类别ID。
	 * @return 上级类别ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置上级类别ID。
	 * @param pid 
	 *	  上级类别ID。
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取类别ID。
	 * @return 类别ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置类别ID。
	 * @param id
	 * 类别ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取类别代码。
	 * @return 类别代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置类别代码。
	 * @param code
	 * 类别代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取类别名称。
	 * @return 类别名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置类别名称。
	 * @param name
	 * 类别名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取EN简称。
	 * @return EN简称。
	 */
	public String getAbbr() {
		return abbr;
	}
	/**
	 * 设置EN简称。
	 * @param abbr
	 * EN简称
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Category o) {
		 return this.code - o.code;
	}
}