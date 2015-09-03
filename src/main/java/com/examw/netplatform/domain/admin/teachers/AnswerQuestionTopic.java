package com.examw.netplatform.domain.admin.teachers;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 教师答疑主题。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public class AnswerQuestionTopic implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,title,content,agencyId,agencyName,classId,lessonId,lessonName,studentId,studentName;
	private Integer status; 
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime,lastTime; 
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
	 * 获取所属学员ID。
	 * @return 所属学员ID。
	 */
	public String getStudentId() {
		return studentId;
	}
	/**
	 * 设置所属学员ID。
	 * @param studentId 
	 *	  所属学员ID。
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	/**
	 * 获取所属学员姓名。
	 * @return 所属学员姓名。
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * 设置所属学员姓名。
	 * @param studentName 
	 *	  所属学员姓名。
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	/**
	 * 获取答疑ID。
	 * @return 答疑ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置答疑ID。
	 * @param id 
	 *	  答疑ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取答疑标题。
	 * @return 答疑标题。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置答疑标题。
	 * @param title 
	 *	  答疑标题。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取答疑内容。
	 * @return content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置答疑内容。
	 * @param content 
	 *	  content
	 */
	public void setContent(String content) {
		this.content = content;
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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
}