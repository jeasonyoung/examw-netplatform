package com.examw.netplatform.domain.admin.settings;

import java.io.Serializable;
import java.util.Set;

/**
 * 考试科目。
 * @author yangyong.
 * @since 2014-08-02.
 */
public class Subject implements Serializable,Comparable<Subject>{
	private static final long serialVersionUID = 1L;
	private String id,name;
	private Integer code,status;
	private Exam exam;
	private Set<Area> areas;
	private Set<Chapter> chapters;
	/**
	 * 获取科目ID。
	 * @return 科目ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置科目ID。
	 * @param id
	 * 科目ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取科目代码。
	 * @return 科目代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置科目代码。
	 * @param code
	 * 科目代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取科目名称。
	 * @return 科目名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置科目名称。
	 * @param name
	 * 科目名称。
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
	 *	  状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取所属考试。
	 * @return 所属考试。
	 */
	public Exam getExam() {
		return exam;
	}
	/**
	 * 设置所属考试。
	 * @param exam
	 * 所属考试。
	 */
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	/**
	 * 获取所属地区集合。
	 * @return 所属地区集合。
	 */
	public Set<Area> getAreas() {
		return areas;
	}
	/**
	 * 设置所属地区。
	 * @param area
	 * 所属地区。
	 */
	public void setAreas(Set<Area> areas) {
		this.areas = areas;
	}
	/**
	 * 获取关联章节集合。
	 * @return 关联章节集合。
	 */
	public Set<Chapter> getChapters() {
		return chapters;
	}
	/**
	 * 设置关联章节集合。
	 * @param chapters 
	 *	  关联章节集合。
	 */
	public void setChapters(Set<Chapter> chapters) {
		this.chapters = chapters;
	}
	/*
	 * 对象字符串。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("id=%1$s,code=%2$s,name=%3$s", this.getId(), this.getCode(), this.getName());
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Subject o) {
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