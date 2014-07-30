package com.examw.netplatform.model.admin.students;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
import com.examw.netplatform.model.admin.IUser;
import com.examw.netplatform.support.CustomDateSerializer;
/**
 * 机构学员订单信息。
 * @author fengwei.
 * @since 2014年5月26日 上午8:09:15.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class StudentOrderInfo extends Paging implements IUser {
	private static final long serialVersionUID = 1L;
	private String id,orderNo,remarks,agencyId,agencyName,studentId,studentName,statusName,currentUserId;
	private Integer status;
	private BigDecimal total;
	private Set<StudentOrderDetailInfo> details;
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
	 * 订单ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取订单号。
	 * @return 订单号。
	 */
	public String getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置订单号。
	 * @param orderNo
	 * 订单号。
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	 * 所属机构ID。
	 */
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	/**
	 * 设置所属机构名称。
	 * @return 所属机构名称。
	 */
	public String getAgencyName() {
		return agencyName;
	}
	/**
	 * 设置所属机构名称。
	 * @param agencyName
	 * 所属机构名称。
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	/**
	 * 获取学生ID。
	 * @return 学生ID。
	 */
	public String getStudentId() {
		return studentId;
	}
	/**
	 * 设置学生ID。
	 * @param studentId
	 * 学生ID。
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	/**
	 * 设置学生名称。
	 * @return 学生名称。
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * 设置学生名称。
	 * @param studentName
	 * 学生名称。
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	/**
	 * 获取订单备注。
	 * @return 订单备注。
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 设置订单备注。
	 * @param remarks
	 * 订单备注。
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取总价格。
	 * @return 总价格。
	 */
	public BigDecimal getTotal() {
		return total;
	}
	/**
	 * 设置总价格。
	 * @param total
	 * 总价格。
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	/**
	 * 获取订单状态。
	 * @return 订单状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置订单状态。
	 * @param status
	 * 订单状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取订单状态名称。
	 * @return 订单状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置订单状态名称。
	 * @param statusName
	 * 订单状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取订单创建时间。
	 * @return 订单创建时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置订单创建时间。
	 * @param createTime
	 * 订单创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取最后修改时间。
	 * @return 最后修改时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置最后修改时间。
	 * @param lastTime
	 * 最后修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * 获取订单明细集合。
	 * @return 订单明细集合。
	 */
	public Set<StudentOrderDetailInfo> getDetails() {
		return details;
	}
	/**
	 * 设置订单明细集合。
	 * @param details
	 * 订单明细集合。
	 */
	public void setDetails(Set<StudentOrderDetailInfo> details) {
		this.details = details;
	}
	/*
	 * 获取当前用户ID。
	 * @see com.examw.netplatform.model.admin.IUser#getCurrentUserId()
	 */
	@Override
	public String getCurrentUserId() {
		return this.currentUserId;
	}
	/*
	 * 设置当前用户ID。
	 * @see com.examw.netplatform.model.admin.IUser#setCurrentUserId(java.lang.String)
	 */
	@Override
	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}
}