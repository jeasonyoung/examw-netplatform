package com.examw.netplatform.domain.admin.students;

import java.io.Serializable;
import java.util.Date;

import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.domain.admin.courses.Package;
import com.examw.netplatform.domain.admin.security.User;

/**
 * 学习进度
 * @author fengwei.
 * @since 2014年5月29日 上午11:28:55.
 */
public class Learning implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private User user;
	private Package package_;
	private Lesson lesson;
	private Date createTime;
	
	
	
	/**
	 * 获取 id
	 * @return id
	 * 
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 id
	 * @param id
	 * 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 学员
	 * @return user
	 * 
	 */
	public User getUser() {
		return user;
	}
	/**
	 * 设置 学员
	 * @param user
	 * 
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * 获取 套餐
	 * @return package_
	 * 
	 */
	public Package getPackage_() {
		return package_;
	}
	/**
	 * 设置 套餐
	 * @param package_
	 * 
	 */
	public void setPackage_(Package package_) {
		this.package_ = package_;
	}
	/**
	 * 获取 课时
	 * @return lesson
	 * 
	 */
	public Lesson getLesson() {
		return lesson;
	}
	/**
	 * 设置 课时
	 * @param lesson
	 * 
	 */
	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}
	/**
	 * 获取 创建时间
	 * @return createTime
	 * 
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置 创建时间
	 * @param createTime
	 * 
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
