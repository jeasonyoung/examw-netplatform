package com.examw.netplatform.domain.admin.students;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单日志
 * @author fengwei.
 * @since 2014年5月26日 上午9:38:50.
 */
public class OrderLog implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,content,createUsername;
	private Integer status;
	private Date createTime;
	private StudentOrder order_;
	/**
	 * 获取 订单日志ID
	 * @return id
	 * 
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 订单日志ID
	 * @param id
	 * 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 订单日志内容
	 * @return content
	 * 
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置 订单日志内容
	 * @param content
	 * 
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取 创建人
	 * @return createUsername
	 * 
	 */
	public String getCreateUsername() {
		return createUsername;
	}
	/**
	 * 设置 创建人
	 * @param createUsername
	 * 
	 */
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}
	/**
	 * 获取 订单状态
	 * @return status
	 * 
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置  订单状态
	 * @param status
	 * 
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	 * 设置  创建时间
	 * @param createTime
	 * 
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取 所属订单
	 * @return order
	 * 
	 */
	public StudentOrder getOrder_() {
		return order_;
	}
	/**
	 * 设置 所属订单
	 * @param order
	 * 
	 */
	public void setOrder_(StudentOrder order) {
		this.order_ = order;
	}
	
}
