package com.examw.netplatform.domain.admin.settings;

import java.io.Serializable;
import java.util.Set;
/**
 * 行政地区。
 * @author yangyong.
 * @since 2014-08-01.
 */
public class Area implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,abbr;
	private Integer code;
	private Set<Exam> exams;
	private Set<Subject> subjects;
	private Set<Chapter> chapters;
	/**
	 * 获取地区ID。
	 * @return 地区ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置地区ID。
	 * @param id
	 * 地区ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取地区代码。
	 * @return 地区代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置地区代码。
	 * @param code
	 * 地区代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取地区名称。
	 * @return 地区名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置地区名称。
	 * @param name
	 * 地区名称。
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
	 * 获取关联的考试集合。
	 * @return 关联的考试集合。
	 */
	public Set<Exam> getExams() {
		return exams;
	}
	/**
	 * 设置关联的考试集合。
	 * @param exams 
	 *	  关联的考试集合。
	 */
	public void setExams(Set<Exam> exams) {
		this.exams = exams;
	}
	/**
	 * 获取关联的考试科目集合。
	 * @return 关联的考试科目集合。
	 */
	public Set<Subject> getSubjects() {
		return subjects;
	}
	/**
	 * 设置关联的考试科目集合。
	 * @param subjects 
	 *	  关联的考试科目集合。
	 */
	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
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
	 * 对象字符串表示。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("id=%1$s,code=%2$s,name=%3$s,abbr=%4$s", this.getId(), this.getCode(), this.getName(), this.getAbbr());
	}
}