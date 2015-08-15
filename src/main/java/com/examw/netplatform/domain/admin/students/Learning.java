package com.examw.netplatform.domain.admin.students;

import java.io.Serializable;
import java.util.Date;
/**
 * 学习进度数据。
 * @author fengwei.
 * @since 2014年5月29日 上午11:28:55.
 */
public class Learning implements Serializable {
	private static final long serialVersionUID = 1L;
	private String studentId,studentName,lessonId,lessonName,agencyId,agencyName;
	private Date createTime,lastTime;
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
	 * 获取学员名称。
	 * @return 学员名称。
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * 设置学员名称。
	 * @param studentName 
	 *	  学员名称。
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
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
	 * 获取所属课时资源名称。
	 * @return 所属课时资源名称。
	 */
	public String getLessonName() {
		return lessonName;
	}
	/**
	 * 设置所属课时资源名称。
	 * @param lessonName 
	 *	  所属课时资源名称。
	 */
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
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
	 * 获取所属机构名称。
	 * @return 所属机构名称。
	 */
	public String getAgencyName() {
		return agencyName;
	}
	/**
	 * 设置所属机构名称。
	 * @param agencyName 
	 *	  所属机构名称。
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime 
	 *	  创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取最后修改时间。
	 * @return 最后修改时间。
	 */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置最后修改时间。
	 * @param lastTime 
	 *	  最后修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
}