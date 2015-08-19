package com.examw.netplatform.domain.admin.settings;

import java.io.Serializable;

/**
 * 考试。
 * @author yangyong.
 * @since 2014-08-01
 */
public class Exam implements Serializable,Comparable<Exam> {
	private static final long serialVersionUID = 1L;
	private String id,name,abbr,categoryId,categoryName;
	private Integer code,status;
	private String[] areaIds,areaNames;
	/**
	 * 获取所属考试类别ID。
	 * @return 所属考试类别ID。
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置所属考试类别ID。
	 * @param categoryId 
	 *	  所属考试类别ID。
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取所属考试类别名称。
	 * @return 所属考试类别名称。
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * 设置 categoryName
	 * @param categoryName 
	 *	  categoryName
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * 获取考试ID。
	 * @return 考试ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置考试ID。
	 * @param id
	 * 考试ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取考试代码。
	 * @return 考试代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置考试代码。
	 * @param code
	 * 考试代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取考试名称。
	 * @return 考试名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置考试名称。
	 * @param name
	 * 考试名称。
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
	 * EN简称。
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	/**
	 * 获取考试状态。
	 * @return 考试状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置考试状态。
	 * @param status 
	 *	  考试状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取考试地区ID集合。
	 * @return 考试地区ID集合。
	 */
	public String[] getAreaIds() {
		return areaIds;
	}
	/**
	 * 设置考试地区ID集合。
	 * @param areaIds 
	 *	  考试地区ID集合。
	 */
	public void setAreaIds(String[] areaIds) {
		this.areaIds = areaIds;
	}
	/**
	 * 获取考试地区名称集合。
	 * @return 考试地区名称集合。
	 */
	public String[] getAreaNames() {
		return areaNames;
	}
	/**
	 * 设置考试地区名称集合。
	 * @param areaNames 
	 *	  考试地区名称集合。
	 */
	public void setAreaNames(String[] areaNames) {
		this.areaNames = areaNames;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Exam o) {
		return this.code - o.code;
	}
}