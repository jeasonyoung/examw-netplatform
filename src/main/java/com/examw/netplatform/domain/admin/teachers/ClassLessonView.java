package com.examw.netplatform.domain.admin.teachers;

import java.io.Serializable;

/**
 * 班级/课程资源。
 * @author jeasonyoung
 * @since 2015年8月24日
 */
public class ClassLessonView implements Serializable {
	private static final long serialVersionUID = 1L;
	private String pid,id,name,agencyId,classId,lessonId;
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
	 * 获取ID。
	 * @return ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置ID。
	 * @param id 
	 *	  ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
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
	 * 获取所属机构ID。
	 * @return 所属机构ID。
	 */
	public String getAgencyId() {
		return agencyId;
	}
	/**
	 * 设置所属机构ID。
	 * @param agencyId 
	 *	  所属机构ID。
	 */
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	/**
	 * 获取所属班级ID。
	 * @return 所属班级ID。
	 */
	public String getClassId() {
		return classId;
	}
	/**
	 * 设置所属班级ID。
	 * @param classId 
	 *	  所属班级ID。
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}
	/**
	 * 获取课程资源ID。
	 * @return 课程资源ID。
	 */
	public String getLessonId() {
		return lessonId;
	}
	/**
	 * 设置课程资源ID。
	 * @param lessonId 
	 *	  课程资源ID。
	 */
	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
	}
}