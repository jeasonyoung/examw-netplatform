package com.examw.netplatform.domain.admin.teachers;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.settings.Agency;

/**
 * 教师答疑。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public class AnswerQuestion implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,title,content;
	private Integer status;
	private Agency agency;
	private User user;
	private Lesson lesson;
	private Date createTime;
	private AnswerQuestion parent;
	private Set<AnswerQuestion> children;
	/**
	 * 构造函数。
	 */
	public AnswerQuestion(){
		this.setCreateTime(new Date());
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
	 * 获取答疑标题。
	 * @return 答疑标题。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置答疑标题。
	 * @param title 
	 *	  答疑标题。
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * 获取agency
	 * @return agency
	 */
	public Agency getAgency() {
		return agency;
	}
	/**
	 * 设置 agency
	 * @param agency 
	 *	  agency
	 */
	public void setAgency(Agency agency) {
		this.agency = agency;
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
	 * 获取所属课程资源。
	 * @return 所属课程资源。
	 */
	public Lesson getLesson() {
		return lesson;
	}
	/**
	 * 设置所属课程资源。
	 * @param lesson 
	 *	  所属课程资源。
	 */
	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
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
	 * 获取父级。
	 * @return 父级。
	 */
	public AnswerQuestion getParent() {
		return parent;
	}
	/**
	 * 设置父级。
	 * @param parent 
	 *	  父级。
	 */
	public void setParent(AnswerQuestion parent) {
		this.parent = parent;
	}
	/**
	 * 获取答疑子集合。
	 * @return 答疑子集合。
	 */
	public Set<AnswerQuestion> getChildren() {
		return children;
	}
	/**
	 * 设置答疑子集合。
	 * @param children 
	 *	  答疑子集合。
	 */
	public void setChildren(Set<AnswerQuestion> children) {
		this.children = children;
	}
}