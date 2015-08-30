package com.examw.netplatform.domain.admin.settings;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 消息内容。
 * 
 * @author jeasonyoung
 * @since 2015年8月30日
 */
public class MsgBody implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id,title,content,agencyId,agencyName,userId,userName;
	private Integer type, status;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime,lastTime;
	/**
	 * 获取消息ID。
	 * @return 消息ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置消息ID。
	 * @param id 
	 *	  消息ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取消息标题。
	 * @return 消息标题。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置消息标题。
	 * @param title 
	 *	  消息标题。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取消息类型。
	 * @return 消息类型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置消息类型。
	 * @param type 
	 *	  消息类型。
	 */
	public void setType(Integer type) {
		this.type = type;
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
	 * 获取消息内容。
	 * @return 消息内容。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置消息内容。
	 * @param content 
	 *	  消息内容。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取所属机构ID。
	 * @return 所属机构ID。
	 */
	public String getAgencyId() {
		return agencyId;
	}
	/**
	 * 设置所属机构ID。
	 * @param agencyId 
	 *	  所属机构ID。
	 */
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	/**
	 * 获取所属机构名称。
	 * @return 所属机构名称。
	 */
	public String getAgencyName() {
		return agencyName;
	}
	/**
	 * 设置所属机构名称。
	 * @param agencyName 
	 *	  所属机构名称。
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	/**
	 * 获取所属用户ID。
	 * @return 所属用户ID。
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置所属用户ID。
	 * @param userId 
	 *	  所属用户ID。
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取所属用户名称。
	 * @return 所属用户名称。
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置所属用户名称。
	 * @param userName 
	 *	  所属用户名称。
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取createTime
	 * @return createTime
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
	 * 获取修改时间。
	 * @return 修改时间。
	 */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置修改时间。
	 * @param lastTime 
	 *	  修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
}