package com.examw.netplatform.domain.admin.courses;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 课时资源
 * @author fengwei.
 * @since 2014年5月22日 上午11:22:13.
 * 
 * 修改重构课时资源。
 * @author yangyong.
 * @since 2014-07-13.
 */
public class Lesson implements Serializable,Comparable<Lesson> {
	private static final long serialVersionUID = 1L;
	private String id,name,description,agencyId,categoryId,examId,subjectId,classId,className,videoUrl,highVideoUrl,superVideoUrl,handoutContent,handoutAttachUrl;
	private Integer time,videoMode,handoutMode,orderNo;
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
	 * 获取所属考试类别ID。
	 * @return 所属考试类别ID。
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置所属考试类别ID。
	 * @param categoryId 
	 *	  所属考试类别ID。
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取所属考试ID。
	 * @return 所属考试ID。
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置所属考试ID。
	 * @param examId 
	 *	  所属考试ID。
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取所属科目ID。
	 * @return 所属科目ID。
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置所属科目ID。
	 * @param subjectId 
	 *	  所属科目ID。
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取课时资源ID。
	 * @return 课时资源ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置课时资源ID。
	 * @param id
	 * 课时资源ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取课时资源名称。
	 * @return 课时资源名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置课时资源名称。
	 * @param name
	 * 课时资源名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取视频时长。
	 * @return 视频时长。
	 */
	public Integer getTime() {
		return time;
	}
	/**
	 * 设置视频时长。
	 * @param time 
	 *	  视频时长。
	 */
	public void setTime(Integer time) {
		this.time = time;
	}
	/**
	 * 获取课时资源描述。
	 * @return 课时资源描述。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置课时资源描述。
	 * @param description
	 * 课时资源描述。
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * 获取所属班级名称。
	 * @return 所属班级名称。
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * 设置所属班级名称。
	 * @param className 
	 *	  所属班级名称。
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * 获取视频模式。
	 * @return 视频模式。
	 */
	public Integer getVideoMode() {
		return videoMode;
	}
	/**
	 * 设置视频模式。
	 * @param videoMode
	 * 视频模式。
	 */
	public void setVideoMode(Integer videoMode) {
		this.videoMode = videoMode;
	}
	/**
	 * 获取视频地址。
	 * @return 视频地址。
	 */
	public String getVideoUrl() {
		return videoUrl;
	}
	/**
	 * 设置视频地址。
	 * @param videoUrl
	 * 视频地址。
	 */
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	/**
	 * 获取高清视频地址。
	 * @return 高清视频地址。
	 */
	public String getHighVideoUrl() {
		return highVideoUrl;
	}
	/**
	 * 设置高清视频地址。
	 * @param highVideoUrl
	 * 高清视频地址。
	 */
	public void setHighVideoUrl(String highVideoUrl) {
		this.highVideoUrl = highVideoUrl;
	}
	/**
	 * 获取超清视频地址。
	 * @return 超清视频地址。
	 */
	public String getSuperVideoUrl() {
		return superVideoUrl;
	}
	/**
	 * 设置超清视频地址。
	 * @param superVideoUrl 
	 *	  超清视频地址。
	 */
	public void setSuperVideoUrl(String superVideoUrl) {
		this.superVideoUrl = superVideoUrl;
	}
	/**
	 * 获取讲义模式。
	 * @return 讲义模式。
	 */
	public Integer getHandoutMode() {
		return handoutMode;
	}
	/**
	 * 设置讲义模式。
	 * @param handoutMode 
	 *	  讲义模式。
	 */
	public void setHandoutMode(Integer handoutMode) {
		this.handoutMode = handoutMode;
	}
	/**
	 * 获取讲义内容。
	 * @return 讲义内容。
	 */
	public String getHandoutContent() {
		return handoutContent;
	}
	/**
	 * 设置讲义内容。
	 * @param handoutContent
	 * 讲义内容。
	 */
	public void setHandoutContent(String handoutContent) {
		this.handoutContent = handoutContent;
	}
	/**
	 * 获取讲义附件URL。
	 * @return 讲义附件URL。
	 */
	public String getHandoutAttachUrl() {
		return handoutAttachUrl;
	}
	/**
	 * 设置讲义附件URL。
	 * @param handoutAttachUrl
	 * 讲义附件URL。
	 */
	public void setHandoutAttachUrl(String handoutAttachUrl) {
		this.handoutAttachUrl = handoutAttachUrl;
	}
	/**
	 * 获取课时资源排序。
	 * @return
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置课时资源排序。
	 * @param orderNo
	 * 课时资源排序。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
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
	 * 创建时间。
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
	 * 最后修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Lesson o) {
		return this.orderNo - o.orderNo;
	}
}