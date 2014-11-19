package com.examw.netplatform.domain.admin.teachers;

import java.io.Serializable;
import java.util.Date;

import com.examw.netplatform.domain.admin.security.User;

/**
 * 教师答疑明细。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public class AnswerQuestionDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private AnswerQuestionTopic topic;
	private String id,content;
	private User user;
	private Date createTime,lastTime;
	/**
	 * 构造函数。
	 */
	public AnswerQuestionDetail(){
		this.setCreateTime(new Date());
	}
	/**
	 * 获取所属主题。
	 * @return 所属主题。
	 */
	public AnswerQuestionTopic getTopic() {
		return topic;
	}
	/**
	 * 设置所属主题。
	 * @param topic 
	 *	  所属主题。
	 */
	public void setTopic(AnswerQuestionTopic topic) {
		this.topic = topic;
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
	 * 获取所属用户。
	 * @return 所属用户。
	 */
	public User getUser() {
		return user;
	}
	/**
	 * 设置所属用户。
	 * @param user 
	 *	  所属用户。
	 */
	public void setUser(User user) {
		this.user = user;
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