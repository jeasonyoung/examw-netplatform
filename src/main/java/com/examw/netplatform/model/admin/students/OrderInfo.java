package com.examw.netplatform.model.admin.students;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 订单信息。
 * 
 * @author yangyong
 * @since 2014年12月1日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class OrderInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,number,name,userId,userName,agencyId,agencyName,studentId,studentName,typeName,sourceName,statusName;
	private String[] packageId,packageName,classId,className;
	private Integer type,source,status;
	private BigDecimal price;
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
	 * 获取培训机构ID。
	 * @return 培训机构ID。
	 */
	public String getAgencyId() {
		return agencyId;
	}
	/**
	 * 设置培训机构ID。
	 * @param agencyId 
	 *	  培训机构ID。
	 */
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	/**
	 * 获取培训机构名称。
	 * @return 培训机构名称。
	 */
	public String getAgencyName() {
		return agencyName;
	}
	/**
	 * 设置培训机构名称。
	 * @param agencyName 
	 *	  培训机构名称。
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	/**
	 * 获取学员ID。
	 * @return 学员ID。
	 */
	public String getStudentId() {
		return studentId;
	}
	/**
	 * 设置学员ID。
	 * @param studentId 
	 *	  学员ID。
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	/**
	 * 获取学员名称。
	 * @return 学员名称。
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * 设置学员名称。
	 * @param studentName 
	 *	  学员名称。
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
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
	 * 获取类型名称。
	 * @return 类型名称。
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置类型名称。
	 * @param typeName 
	 *	  类型名称。
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	 * 获取来源名称。
	 * @return 来源名称。
	 */
	public String getSourceName() {
		return sourceName;
	}
	/**
	 * 设置来源名称。
	 * @param sourceName 
	 *	  来源名称。
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
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
	 * 获取状态名称。
	 * @return 状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置状态名称。
	 * @param statusName 
	 *	  状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
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
	 * 获取套餐ID集合。
	 * @return 套餐ID集合。
	 */
	public String[] getPackageId() {
		return packageId;
	}
	/**
	 * 设置套餐ID集合。
	 * @param packageId 
	 *	  套餐ID集合。
	 */
	public void setPackageId(String[] packageId) {
		this.packageId = packageId;
	}
	/**
	 * 获取套餐名称集合。
	 * @return 套餐名称集合。
	 */
	public String[] getPackageName() {
		return packageName;
	}
	/**
	 * 设置套餐名称集合。
	 * @param packageName 
	 *	  套餐名称集合。
	 */
	public void setPackageName(String[] packageName) {
		this.packageName = packageName;
	}
	/**
	 * 获取班级ID集合。
	 * @return 班级ID集合。
	 */
	public String[] getClassId() {
		return classId;
	}
	/**
	 * 设置班级ID集合。
	 * @param classId 
	 *	  班级ID集合。
	 */
	public void setClassId(String[] classId) {
		this.classId = classId;
	}
	/**
	 * 获取班级名称集合。
	 * @return 班级名称集合。
	 */
	public String[] getClassName() {
		return className;
	}
	/**
	 * 设置班级名称集合。
	 * @param className 
	 *	  班级名称集合。
	 */
	public void setClassName(String[] className) {
		this.className = className;
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
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
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
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
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