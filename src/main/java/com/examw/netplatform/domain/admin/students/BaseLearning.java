package com.examw.netplatform.domain.admin.students;

import java.io.Serializable;
/**
 * 学习进度基础类。
 * 
 * @author jeasonyoung
 * @since 2015年8月28日
 */
public class BaseLearning implements Serializable {
	private static final long serialVersionUID = 1L;
	private String agencyId,lessonId,studentId;
	private Integer status;
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
	 * 获取所属课时资源ID。
	 * @return 所属课时资源ID。
	 */
	public String getLessonId() {
		return lessonId;
	}
	/**
	 * 设置所属课时资源ID。
	 * @param lessonId 
	 *	  所属课时资源ID。
	 */
	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
	}
	/**
	 * 获取学员ID。
	 * @return 学员ID。
	 */
	public String getStudentId() {
		return studentId;
	}
	/**
	 * 设置学员ID。
	 * @param studentId 
	 *	  学员ID。
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	/**
	 * 获取进度状态。
	 * @return 进度状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置进度状态。
	 * @param status 
	 *	  进度状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
}