package com.examw.netplatform.domain.admin.settings;

import java.io.Serializable;
import java.util.Set;
/**
 * 科目章节
 * @author fengwei.
 * @since 2014年4月30日 下午2:56:46.
 */
public class Chapter implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id,name,description;
	private Integer orderNo;
	private Subject subject;
	private Chapter parent;
	private Set<Chapter> children;
	/**
	 * 获取 章节ID
	 * @return id
	 * 章节ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 章节ID
	 * @param id
	 * 章节ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 父章节
	 * @return parent
	 * 父章节
	 */
	public Chapter getParent() {
		return parent;
	}
	/**
	 * 设置 父章节
	 * @param parent
	 * 父章节
	 */
	public void setParent(Chapter parent) {
		this.parent = parent;
	}
	/**
	 * 获取 章节名称
	 * @return name
	 * 章节名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 章节名称
	 * @param name
	 * 章节名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取 章节描述
	 * @return description
	 *  章节描述
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置  章节描述
	 * @param description
	 *  章节描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取 排序号
	 * @return orderNo
	 *  排序号
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置  排序号
	 * @param orderNo
	 *  排序号
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取 章节所属科目
	 * @return subject
	 * 章节所属科目
	 */
	public Subject getSubject() {
		return subject;
	}
	/**
	 * 设置 章节所属科目
	 * @param subject
	 * 章节所属科目
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	/**
	 * 获取 子章节集合
	 * @return children
	 * 子章节集合
	 */
	public Set<Chapter> getChildren() {
		return children;
	}
	/**
	 * 设置 子章节集合
	 * @param children
	 * 子章节集合
	 */
	public void setChildren(Set<Chapter> children) {
		this.children = children;
	}	
}