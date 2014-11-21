package com.examw.netplatform.model.admin.teachers;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 教师答疑明细信息。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public class AnswerQuestionDetailInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,content,topicId,userId,userName;
	private Date createTime,lastTime;
	/**
	 * 获取明细ID。
	 * @return 明细ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置明细ID。
	 * @param id 
	 *	  明细ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取明细内容。
	 * @return 明细内容。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置明细内容。
	 * @param content 
	 *	  明细内容。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取主题ID。
	 * @return 主题ID。
	 */
	public String getTopicId() {
		return topicId;
	}
	/**
	 * 设置主题ID。
	 * @param topicId 
	 *	  主题ID。
	 */
	public void setTopicId(String topicId) {
		this.topicId = topicId;
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