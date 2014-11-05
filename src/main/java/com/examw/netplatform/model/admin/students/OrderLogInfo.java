package com.examw.netplatform.model.admin.students;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 订单日志信息
 * @author fengwei.
 * @since 2014年5月26日 上午9:49:13.
 */
public class OrderLogInfo extends Paging implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,content,createUsername,statusName,orderId;
	private Integer status;
	private Date createTime;
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
	@JsonSerialize(using = CustomDateSerializer.class)
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
	 * 获取 状态名称 
	 * @return statusName
	 * 
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置 状态名称
	 * @param statusName
	 * 
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取 订单ID
	 * @return orderId
	 * 
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * 设置 订单ID
	 * @param orderId
	 * 
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}	
