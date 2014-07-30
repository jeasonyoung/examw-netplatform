package com.examw.netplatform.domain.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.domain.admin.security.User;

/**
 * 课堂答疑
 * @author fengwei.
 * @since 2014年5月31日 上午8:57:43.
 */
public class AnswersQuestion implements Serializable {
	private static final long serialVersionUID = 1L;
	private AnswersQuestion parent;
	private String id,content;
	private Lesson lesson;
	private User user;
	private Integer type,status;
	private Date createTime;
	private Set<AnswersQuestion> children;
	
	public static final int TYPE_STUDENT =1,	//学员
			TYPE_TEACHER = 2,	//老师
			STATUS_ASK = 1,	//提问状态
			STATUS_ANSWER = 2,	//回答
			STATUS_END=3;	//完结
	/**
	 * 获取 父问答
	 * @return parent
	 * 父问答
	 */
	public AnswersQuestion getParent() {
		return parent;
	}
	/**
	 * 设置 父问答
	 * @param parent
	 * 父问答
	 */
	public void setParent(AnswersQuestion parent) {
		this.parent = parent;
	}
	/**
	 * 获取 ID
	 * @return id
	 * ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 ID
	 * @param id
	 * ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 内容
	 * @return content
	 * 内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置 内容
	 * @param content
	 * 内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取 课时
	 * @return lesson
	 * 课时
	 */
	public Lesson getLesson() {
		return lesson;
	}
	/**
	 * 设置 课时
	 * @param lesson
	 * 课时
	 */
	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}
	/**
	 * 获取 用户
	 * @return user
	 * 用户
	 */
	public User getUser() {
		return user;
	}
	/**
	 * 设置 用户
	 * @param user
	 * 用户
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * 获取 类型
	 * @return type
	 * 类型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置 类型
	 * @param type
	 * 类型
	 */
	public void setType(Integer type) {
		this.type = type;
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
	 * 获取 创建时间
	 * @return create_time
	 * 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置 创建时间
	 * @param create_time
	 * 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取 子问答
	 * @return children
	 * 子问答
	 */
	public Set<AnswersQuestion> getChildren() {
		return children;
	}
	/**
	 * 设置 子问答
	 * @param children
	 * 子问答
	 */
	public void setChildren(Set<AnswersQuestion> children) {
		this.children = children;
	}
	
}
