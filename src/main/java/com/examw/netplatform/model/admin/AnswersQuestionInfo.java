package com.examw.netplatform.model.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 课堂问答信息
 * @author fengwei.
 * @since 2014年5月31日 上午9:09:05.
 */
public class AnswersQuestionInfo extends Paging implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id,content,typeName,statusName,userId,username,userAccount,lessonId,lessonName;
	private Integer type,status;
	private Date createTime;
	private String pid;
	private List<AnswersQuestionInfo> children;
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
	 * 获取 类型名字
	 * @return typeName
	 *  类型名字
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置  类型名字
	 * @param typeName
	 * 类型名字
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取 
	 * @return stautsName
	 * 
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置 
	 * @param stautsName
	 * 
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取 用户ID
	 * @return userId
	 * 用户ID
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置 用户ID
	 * @param userId
	 * 用户ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取 用户名
	 * @return username
	 * 用户名
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置 用户名
	 * @param username
	 * 用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取 课时ID
	 * @return lessonId
	 * 课时ID
	 */
	public String getLessonId() {
		return lessonId;
	}
	/**
	 * 设置 课时ID
	 * @param lessonId
	 * 课时ID
	 */
	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
	}
	/**
	 * 获取 课时名字
	 * @return lessonName
	 * 课时名字
	 */
	public String getLessonName() {
		return lessonName;
	}
	/**
	 * 设置 课时名字
	 * @param lessonName
	 * 课时名字
	 */
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
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
	 * @return createTime
	 * 创建时间
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置 创建时间
	 * @param createTime
	 * 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取 用户帐户
	 * @return userAccount
	 * 用户帐户
	 */
	public String getUserAccount() {
		return userAccount;
	}
	/**
	 * 设置 用户帐户
	 * @param userAccount
	 * 用户帐户
	 */
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	/**
	 * 获取 父ID
	 * @return pid
	 * 父ID
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置 父ID
	 * @param pid
	 * 父ID
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取 子问题或答案
	 * @return children
	 * 
	 */
	public List<AnswersQuestionInfo> getChildren() {
		return children;
	}
	/**
	 * 设置 子问题或答案
	 * @param children
	 * 
	 */
	public void setChildren(List<AnswersQuestionInfo> children) {
		this.children = children;
	}
	
}
