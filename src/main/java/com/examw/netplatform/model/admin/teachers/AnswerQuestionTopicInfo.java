package com.examw.netplatform.model.admin.teachers;

import java.util.Date;

import com.examw.model.Paging;

/**
 * 教师答疑主题信息。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public class AnswerQuestionTopicInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,title,content,statusName,agencyId,agencyName,userId,userName,lessonId,lessonName;
	private Integer status;
	private Date createTime,lastTime;
	/**
	 * 获取主题ID。
	 * @return 主题ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置主题ID。
	 * @param id 
	 *	  主题ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取主题标题。
	 * @return 主题标题。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置主题标题。
	 * @param title 
	 *	  主题标题。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取主题内容。
	 * @return 主题内容。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置主题内容。
	 * @param content 
	 *	  主题内容。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取机构ID。
	 * @return 机构ID。
	 */
	public String getAgencyId() {
		return agencyId;
	}
	/**
	 * 设置机构ID。
	 * @param agencyId 
	 *	  机构ID。
	 */
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	/**
	 * 获取机构名称。
	 * @return 机构名称。
	 */
	public String getAgencyName() {
		return agencyName;
	}
	/**
	 * 设置机构名称。
	 * @param agencyName 
	 *	  机构名称。
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	/**
	 * 获取用户ID。
	 * @return 用户ID。
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置用户ID。
	 * @param userId 
	 *	  用户ID。
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取用户名称。
	 * @return 用户名称。
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置用户名称。
	 * @param userName 
	 *	  用户名称。
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取课时资源ID。
	 * @return 课时资源ID。
	 */
	public String getLessonId() {
		return lessonId;
	}
	/**
	 * 设置课时资源ID。
	 * @param lessonId 
	 *	  课时资源ID。
	 */
	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
	}
	/**
	 * 获取课时资源名称。
	 * @return 课时资源名称。
	 */
	public String getLessonName() {
		return lessonName;
	}
	/**
	 * 设置课时资源名称。
	 * @param lessonName 
	 *	  课时资源名称。
	 */
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
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
	 * 获取状态名称。
	 * @return 状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置状态名称。
	 * @param statusName 
	 *	  状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
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