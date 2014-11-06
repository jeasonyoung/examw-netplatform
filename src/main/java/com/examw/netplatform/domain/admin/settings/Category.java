package com.examw.netplatform.domain.admin.settings;

import java.io.Serializable;
import java.util.Set;

/**
 * 考试类别。
 * @author yangyong.
 * @since 2014-08-01.
 */
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,abbr;
	private Category parent;
	private Set<Category> children;
	private Set<Exam> exams;
	private Integer code;
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
	/**
	 * 获取上级类别。
	 * @return 上级类别。
	 */
	public Category getParent() {
		return parent;
	}
	/**
	 * 设置上级类别。
	 * @param parent
	 * 上级类别。
	 */
	public void setParent(Category parent) {
		this.parent = parent;
	}
	/**
	 * 获取子类别集合。
	 * @return 子类别集合。
	 */
	public Set<Category> getChildren() {
		return children;
	}
	/**
	 * 设置子类别集合。
	 * @param children
	 * 子类别集合。
	 */
	public void setChildren(Set<Category> children) {
		this.children = children;
	}
	/**
	 * 获取考试集合。
	 * @return 考试集合。
	 */
	public Set<Exam> getExams() {
		return exams;
	}
	/**
	 * 设置考试集合。
	 * @param exams
	 * 考试集合。
	 */
	public void setExams(Set<Exam> exams) {
		this.exams = exams;
	}
	/*
	 * 对象字符串。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("id=%1$s,code=%2$s,name=%3$s,abbr=%4$s", this.getId(), this.getCode(), this.getName(), this.getAbbr());
	}
}