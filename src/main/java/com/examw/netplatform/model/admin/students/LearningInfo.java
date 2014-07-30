package com.examw.netplatform.model.admin.students;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.netplatform.support.CustomDateSerializer;

/**
 * 学习进度数据
 * @author fengwei.
 * @since 2014年5月29日 上午11:39:48.
 */
public class LearningInfo extends Paging implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,userId,username,useraccount,packageId,packageName,lessonId,lessonName;
	private Date createTime;
	/**
	 * 获取 ID
	 * @return id
	 * 
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 ID
	 * @param id
	 * 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 学员ID
	 * @return userId
	 * 
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置 学员ID
	 * @param userId
	 * 
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取 学员名字
	 * @return username
	 * 
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置 学员名字
	 * @param username
	 * 
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取 套餐ID
	 * @return packageId
	 * 
	 */
	public String getPackageId() {
		return packageId;
	}
	/**
	 * 设置 套餐ID
	 * @param packageId
	 * 
	 */
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	/**
	 * 获取 套餐名字
	 * @return packageName
	 * 
	 */
	public String getPackageName() {
		return packageName;
	}
	/**
	 * 设置 套餐名字
	 * @param packageName
	 * 
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	/**
	 * 获取 课时ID
	 * @return lessonId
	 * 
	 */
	public String getLessonId() {
		return lessonId;
	}
	/**
	 * 设置 课时ID
	 * @param lessonId
	 * 
	 */
	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
	}
	/**
	 * 获取 课时名称
	 * @return lessonName
	 * 
	 */
	public String getLessonName() {
		return lessonName;
	}
	/**
	 * 设置 课时名称
	 * @param lessonName
	 * 
	 */
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}
	/**
	 * 获取 创建时间
	 * @return createTime
	 * 
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
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
	/**
	 * 获取 用户帐号
	 * @return useraccount
	 * 
	 */
	public String getUseraccount() {
		return useraccount;
	}
	/**
	 * 设置 用户帐号
	 * @param useraccount
	 * 
	 */
	public void setUseraccount(String useraccount) {
		this.useraccount = useraccount;
	}
	
}
