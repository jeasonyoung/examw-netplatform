package com.examw.netplatform.domain.admin.students;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单数据。
 * 
 * @author yangyong
 * @since 2014年12月1日
 */
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,number,name,agencyId,agencyName,userId,userName;
	private Integer source,status;
	private BigDecimal price;
	private Date createTime,lastTime;
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
	 * 获取订单ID。
	 * @return 订单ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置订单ID。
	 * @param id 
	 *	  订单ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取订单号码。
	 * @return 订单号码。
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * 设置订单号码。
	 * @param number 
	 *	  订单号码。
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * 获取订单名称。
	 * @return 订单名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置订单名称。
	 * @param name 
	 *	  订单名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取价格。
	 * @return 价格。
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置价格。
	 * @param price 
	 *	  价格。
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取来源。
	 * @return 来源。
	 */
	public Integer getSource() {
		return source;
	}
	/**
	 * 设置来源。
	 * @param source 
	 *	  来源。
	 */
	public void setSource(Integer source) {
		this.source = source;
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
	 * 获取操作用户ID。
	 * @return 操作用户ID。
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置操作用户ID。
	 * @param userId 
	 *	  操作用户ID。
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取操作用户名称。
	 * @return 操作用户名称。
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置操作用户名称。
	 * @param userName 
	 *	  操作用户名称。
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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