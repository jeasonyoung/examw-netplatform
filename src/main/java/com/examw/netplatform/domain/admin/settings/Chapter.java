package com.examw.netplatform.domain.admin.settings;

import java.io.Serializable;
import java.util.Set;

import com.examw.netplatform.domain.admin.courses.Lesson;
/**
 * 科目章节
 * @author fengwei.
 * @since 2014年4月30日 下午2:56:46.
 */
public class Chapter implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id,name,description;
	private Integer status,orderNo;
	private Subject subject;
	private Area area;
	private Chapter parent;
	private Set<Chapter> children;
	private Set<Lesson> lessons;
	/**
	 * 获取章节ID。
	 * @return 章节ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置章节ID。
	 * @param id
	 * 章节ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取上级章节。
	 * @return 上级章节。
	 */
	public Chapter getParent() {
		return parent;
	}
	/**
	 * 设置上级章节。
	 * @param parent
	 *  上级章节。
	 */
	public void setParent(Chapter parent) {
		this.parent = parent;
	}
	/**
	 * 获取章节名称。
	 * @return  章节名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置章节名称。
	 * @param name
	 *  章节名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取章节描述。
	 * @return  章节描述。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置章节描述。
	 * @param description
	 *  章节描述。
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * 获取排序号。
	 * @return 排序号。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo
	 *  排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取所属科目。
	 * @return 所属科目。
	 */
	public Subject getSubject() {
		return subject;
	}
	/**
	 * 设置所属科目。
	 * @param subject
	 * 所属科目。
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	/**
	 * 获取所属地区。
	 * @return 所属地区。
	 */
	public Area getArea() {
		return area;
	}
	/**
	 * 设置所属地区。
	 * @param area 
	 *	  所属地区。
	 */
	public void setArea(Area area) {
		this.area = area;
	}
	/**
	 * 获取子章节集合。
	 * @return 子章节集合。
	 */
	public Set<Chapter> getChildren() {
		return children;
	}
	/**
	 * 设置子章节集合。
	 * @param 子章节集合。
	 */
	public void setChildren(Set<Chapter> children) {
		this.children = children;
	}
	/**
	 * 获取关联的课程资源集合。
	 * @return 关联的课程资源集合。
	 */
	public Set<Lesson> getLessons() {
		return lessons;
	}
	/**
	 * 设置关联的课程资源集合。
	 * @param lessons 
	 *	  关联的课程资源集合。
	 */
	public void setLessons(Set<Lesson> lessons) {
		this.lessons = lessons;
	}	
}