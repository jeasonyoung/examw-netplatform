package com.examw.netplatform.domain.admin.settings;

import java.io.Serializable;
import java.util.Set;

/**
 * 考试。
 * @author yangyong.
 * @since 2014-08-01
 */
public class Exam implements Serializable,Comparable<Exam> {
	private static final long serialVersionUID = 1L;
	private String id,name,abbr;
	private Integer code,status;
	private Category category;
	private Set<Area> areas;
	private Set<Subject> subjects;
	private Set<Package> packages;
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
	 * 获取所属类别。
	 * @return 所属类别。
	 */
	public Category getCategory() {
		return category;
	}
	/**
	 * 设置所属类别。
	 * @param category
	 * 所属类别。
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
	/**
	 * 获取所属地区集合。
	 * @return 所属地区集合。
	 */
	public Set<Area> getAreas() {
		return areas;
	}
	/**
	 * 设置所属地区集合。
	 * @param areas 
	 *	  所属地区集合。
	 */
	public void setAreas(Set<Area> areas) {
		this.areas = areas;
	}
	/**
	 * 获取科目集合。
	 * @return 科目集合。
	 */
	public Set<Subject> getSubjects() {
		return subjects;
	}
	/**
	 * 设置科目集合。
	 * @param subjects
	 * 科目集合。
	 */
	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}
	/**
	 * 获取关联的套餐集合。
	 * @return 关联的套餐集合。
	 */
	public Set<Package> getPackages() {
		return packages;
	}
	/**
	 * 设置关联的套餐集合。
	 * @param packages 
	 *	 关联的套餐集合。
	 */
	public void setPackages(Set<Package> packages) {
		this.packages = packages;
	}
	/*
	 * 对象字符串。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("id=%1$s,code=%2$s,name=%3$s,abbr=%2$s", this.getId(), this.getCode(), this.getName(), this.getAbbr());
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Exam o) {
		int index = 0;
		if(this == o) return index;
		index = this.getCode() - o.getCode();
		if(index == 0){
			index = this.getName().compareToIgnoreCase(o.getName());
			if(index == 0){
				index = this.getId().compareToIgnoreCase(o.getId());
			}
		}
		return index;
	}
}