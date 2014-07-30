package com.examw.netplatform.model.admin.courses;

import java.util.Date;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
import com.examw.netplatform.model.admin.IUser;
import com.examw.netplatform.support.CustomDateSerializer;

/**
 * 课时资源信息
 * @author fengwei.
 * @since 2014年5月22日 上午11:28:23.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class LessonInfo extends Paging implements IUser {
	private static final long serialVersionUID = 1L;
	private String id,name,description,videoUrl,highVideoUrl,handoutContent,handoutAttachUrl,classId,className,testPaperId,testPaperName,videoModeName,currentUserId;
	private Integer videoMode,orderNo;
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
	 * 获取所属班级ID。
	 * @return 所属班级ID。
	 */
	public String getClassId() {
		return classId;
	}
	/**
	 * 设置所属班级ID。
	 * @param classPlan
	 * 所属班级。
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
	 * 所属班级名称。
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * 获取所属随堂练习ID。
	 * @return 所属随堂练习ID。
	 */
	public String getTestPaperId() {
		return testPaperId;
	}
	/**
	 * 设置所属随堂练习ID。
	 * @param testPaperId
	 * 所属随堂练习ID。
	 */
	public void setTestPaperId(String testPaperId) {
		this.testPaperId = testPaperId;
	}
	/**
	 * 获取所属随堂练习名称。
	 * @return 所属随堂练习名称。
	 */
	public String getTestPaperName() {
		return testPaperName;
	}
	/**
	 * 设置所属随堂练习名称。
	 * @param testPaperName
	 * 所属随堂练习名称。
	 */
	public void setTestPaperName(String testPaperName) {
		this.testPaperName = testPaperName;
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
	 * 获取视频模式名称。
	 * @return 视频模式名称。
	 */
	public String getVideoModeName() {
		return videoModeName;
	}
	/**
	 * 设置视频模式名称。
	 * @param videoModeName
	 * 视频模式名称。
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
	@JsonSerialize(using = CustomDateSerializer.class)
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
	@JsonSerialize(using = CustomDateSerializer.class)
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
	 * 获取当前用户ID。
	 * @return 当前用户ID。
	 */
	public String getCurrentUserId() {
		return currentUserId;
	}
	/**
	 * 设置当前用户ID。
	 * @param currentUserId
	 * 当前用户ID。
	 */
	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}
}