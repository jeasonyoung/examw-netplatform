package com.examw.netplatform.domain.admin.students;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.settings.Agency;

/**
 * 订单数据。
 * 
 * @author yangyong
 * @since 2014年12月1日
 */
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,number,name,userId,userName;
	private Agency agency;
	private User student;
	private Integer type,source,status;
	private BigDecimal price;
	private Set<com.examw.netplatform.domain.admin.courses.Package> packages;
	private Set<ClassPlan> classes;
	private Date createTime,lastTime;
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
	 * 获取订单用户。
	 * @return 订单用户。
	 */
	public User getStudent() {
		return student;
	}
	/**
	 * 设置订单用户。
	 * @param student 
	 *	  订单用户。
	 */
	public void setStudent(User student) {
		this.student = student;
	}
	/**
	 * 获取类型。
	 * @return 类型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置类型。
	 * @param type 
	 *	  类型。
	 */
	public void setType(Integer type) {
		this.type = type;
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
	 * 获取套餐集合。
	 * @return 套餐集合。
	 */
	public Set<com.examw.netplatform.domain.admin.courses.Package> getPackages() {
		return packages;
	}
	/**
	 * 设置套餐集合。
	 * @param packages 
	 *	  套餐集合。
	 */
	public void setPackages(Set<com.examw.netplatform.domain.admin.courses.Package> packages) {
		this.packages = packages;
	}
	/**
	 * 获取班级集合。
	 * @return 班级集合。
	 */
	public Set<ClassPlan> getClasses() {
		return classes;
	}
	/**
	 * 设置班级集合。
	 * @param classes 
	 *	  班级集合。
	 */
	public void setClasses(Set<ClassPlan> classes) {
		this.classes = classes;
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