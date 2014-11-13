package com.examw.netplatform.model.admin.courses;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 课时资源信息
 * @author fengwei.
 * @since 2014年5月22日 上午11:28:23.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class LessonInfo extends Paging{
	private static final long serialVersionUID = 1L;
	private String id,name,description,videoModeName,videoUrl,highVideoUrl,handoutModeName,handoutContent,handoutAttachUrl,classId,className;
	private String[] chapterId,chapterName;
	private Integer videoMode,handoutMode,orderNo;
	private Date createTime,lastTime;	
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
	 *	  课时资源ID。
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
	 *	  课时资源名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取描述信息。
	 * @return 描述信息。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置描述信息。
	 * @param description 
	 *	  描述信息。
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 *	  视频模式。
	 */
	public void setVideoMode(Integer videoMode) {
		this.videoMode = videoMode;
	}
	/**
	 * 获取视频模式名称。
	 * @return 视频模式名称。
	 */
	public String getVideoModeName() {
		return videoModeName;
	}
	/**
	 * 设置视频模式名称。
	 * @param videoModeName 
	 *	  视频模式名称。
	 */
	public void setVideoModeName(String videoModeName) {
		this.videoModeName = videoModeName;
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
	 *	  视频地址。
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
	 *	  高清视频地址。
	 */
	public void setHighVideoUrl(String highVideoUrl) {
		this.highVideoUrl = highVideoUrl;
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
	 * 获取讲义模式名称。
	 * @return 讲义模式名称。
	 */
	public String getHandoutModeName() {
		return handoutModeName;
	}
	/**
	 * 设置讲义模式名称。
	 * @param handoutModeName 
	 *	  讲义模式名称。
	 */
	public void setHandoutModeName(String handoutModeName) {
		this.handoutModeName = handoutModeName;
	}
	/**
	 * 获取讲义内容。
	 * @return handoutContent
	 */
	public String getHandoutContent() {
		return handoutContent;
	}
	/**
	 * 设置讲义内容。
	 * @param handoutContent 
	 *	  讲义内容。
	 */
	public void setHandoutContent(String handoutContent) {
		this.handoutContent = handoutContent;
	}
	/**
	 * 获取讲义下载地址。
	 * @return 讲义下载地址。
	 */
	public String getHandoutAttachUrl() {
		return handoutAttachUrl;
	}
	/**
	 * 设置讲义下载地址。
	 * @param handoutAttachUrl 
	 *	  讲义下载地址。
	 */
	public void setHandoutAttachUrl(String handoutAttachUrl) {
		this.handoutAttachUrl = handoutAttachUrl;
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
	 * 获取关联章节ID集合。
	 * @return 关联章节ID集合。
	 */
	public String[] getChapterId() {
		return chapterId;
	}
	/**
	 * 设置关联章节ID集合。
	 * @param chapterId 
	 *	  关联章节ID集合。
	 */
	public void setChapterId(String[] chapterId) {
		this.chapterId = chapterId;
	}
	/**
	 * 获取关联章节名称集合。
	 * @return 关联章节名称集合。
	 */
	public String[] getChapterName() {
		return chapterName;
	}
	/**
	 * 设置关联章节名称集合。
	 * @param chapterName 
	 *	  关联章节名称集合。
	 */
	public void setChapterName(String[] chapterName) {
		this.chapterName = chapterName;
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
	 *	  排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
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
	 * 获取最后修改时间。
	 * @return 最后修改时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
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