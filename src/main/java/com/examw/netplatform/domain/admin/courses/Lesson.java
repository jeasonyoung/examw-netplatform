package com.examw.netplatform.domain.admin.courses;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.examw.netplatform.domain.admin.settings.Chapter;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionTopic;
/**
 * 课时资源
 * @author fengwei.
 * @since 2014年5月22日 上午11:22:13.
 * 
 * 修改重构课时资源。
 * @author yangyong.
 * @since 2014-07-13.
 */
public class Lesson implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,description,videoUrl,highVideoUrl,handoutContent,handoutAttachUrl;
	private ClassPlan classPlan;
	private Integer videoMode,handoutMode,orderNo;
	private Date createTime,lastTime;
	private Set<Chapter> chapters;
	private Set<AnswerQuestionTopic> topics;
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
	 * 获取所属班级。
	 * @return 所属班级。
	 */
	public ClassPlan getClassPlan() {
		return classPlan;
	}
	/**
	 * 设置所属班级。
	 * @param classPlan
	 * 所属班级。
	 */
	public void setClassPlan(ClassPlan classPlan) {
		this.classPlan = classPlan;
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
	 * 获取handoutMode
	 * @return handoutMode
	 */
	public Integer getHandoutMode() {
		return handoutMode;
	}
	/**
	 * 设置 handoutMode
	 * @param handoutMode 
	 *	  handoutMode
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
	/**
	 * 获取所属章节集合。
	 * @return 所属章节集合。
	 */
	public Set<Chapter> getChapters() {
		return chapters;
	}
	/**
	 * 设置所属章节集合。
	 * @param chapters 
	 *	  所属章节集合。
	 */
	public void setChapters(Set<Chapter> chapters) {
		this.chapters = chapters;
	}
	/**
	 * 获取关联教师答疑主题集合。
	 * @return 关联教师答疑主题集合。
	 */
	public Set<AnswerQuestionTopic> getTopics() {
		return topics;
	}
	/**
	 * 设置关联教师答疑主题集合。
	 * @param topics 
	 *	  关联教师答疑主题集合。
	 */
	public void setTopics(Set<AnswerQuestionTopic> topics) {
		this.topics = topics;
	}
}