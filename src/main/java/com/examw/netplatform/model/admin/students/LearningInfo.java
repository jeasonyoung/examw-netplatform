package com.examw.netplatform.model.admin.students;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 学习进度信息。
 * @author fengwei.
 * @since 2014年5月29日 上午11:39:48.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class LearningInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,agencyId,agencyName,userId,userName,classId,className,lessonId,lessonName;
	private Date createTime;
	//增加属性
	private Integer status,learnedTime;
	/**
	 * 获取进行ID。
	 * @return 进行ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置进行ID。
	 * @param id 
	 *	  进行ID。
	 */
	public void setId(String id) {
		this.id = id;
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
	 * 获取班级ID。
	 * @return 班级ID。
	 */
	public String getClassId() {
		return classId;
	}
	/**
	 * 设置班级ID。
	 * @param classId 
	 *	  班级ID。
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}
	/**
	 * 获取班级名称。
	 * @return 班级名称。
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * 设置班级名称。
	 * @param className 
	 *	  班级名称。
	 */
	public void setClassName(String className) {
		this.className = className;
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
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
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
	 * 获取 状态
	 * @return status
	 * 状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置 状态
	 * @param status
	 * 状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取 已学习时间
	 * @return learnedTime
	 * 
	 */
	public Integer getLearnedTime() {
		return learnedTime;
	}
	/**
	 * 设置 已学习时间
	 * @param learnedTime
	 * 
	 */
	public void setLearnedTime(Integer learnedTime) {
		this.learnedTime = learnedTime;
	}
}