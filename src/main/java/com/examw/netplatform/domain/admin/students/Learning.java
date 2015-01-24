package com.examw.netplatform.domain.admin.students;

import java.io.Serializable;
import java.util.Date;

import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.settings.Agency;
/**
 * 学习进度数据。
 * @author fengwei.
 * @since 2014年5月29日 上午11:28:55.
 */
public class Learning implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private User user;
	private Agency agency;
	private Lesson lesson;
	private Date createTime;
	//增加属性
	private Integer status,learnedTime;
	/**
	 * 获取进度ID。
	 * @return 进度ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置进度ID。
	 * @param id 
	 *	  进度ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取用户。
	 * @return 用户。
	 */
	public User getUser() {
		return user;
	}
	/**
	 * 设置用户。
	 * @param user 
	 *	  用户。
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * 获取所属机构。
	 * @return 所属机构。
	 */
	public Agency getAgency() {
		return agency;
	}
	/**
	 * 设置所属机构。
	 * @param agency 
	 *	  所属机构。
	 */
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	/**
	 * 获取课时资源。
	 * @return 课时资源。
	 */
	public Lesson getLesson() {
		return lesson;
	}
	/**
	 * 设置课时资源。
	 * @param lesson 
	 *	  课时资源。
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