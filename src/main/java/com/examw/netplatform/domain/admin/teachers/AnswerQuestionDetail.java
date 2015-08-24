package com.examw.netplatform.domain.admin.teachers;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 教师答疑明细。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public class AnswerQuestionDetail implements Serializable {
	private static final long serialVersionUID = 1L; 
	private String topicId,id,content,userId,userName;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 获取所属主题ID。
	 * @return 所属主题ID。
	 */
	public String getTopicId() {
		return topicId;
	}
	/**
	 * 设置所属主题ID。
	 * @param topicId 
	 *	  所属主题ID。
	 */
	public void setTopicId(String topicId) {
		this.topicId = topicId;
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
}