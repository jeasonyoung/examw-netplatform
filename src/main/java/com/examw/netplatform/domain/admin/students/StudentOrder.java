package com.examw.netplatform.domain.admin.students;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.examw.netplatform.domain.admin.agency.AgencyUser;

/**
 * 订单数据
 * @author fengwei.
 * @since 2014年5月24日 下午4:41:11.
 */
public class StudentOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,orderNo,remarks;
	private Integer status;
	private AgencyUser student;
	private Set<StudentOrderDetail> details;
	private Date createTime,lastTime;
	/**
	 * 状态－机构指定。
	 */
	public static final Integer STATUS_APPOINT = 1;
	/**
	 * 状态－学员购买。
	 */
	public static final Integer STATUS_BUY = 2;
	/**
	 * 状态－申请取消。
	 */
	public static final Integer STATUS_CANCEL = 0;
	/**
	 * 状态－已删除。
	 */
	public static final Integer STATUS_DELETE = -1;
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
	 * 获取所属学员。
	 * @return 所属学员。
	 */
	public AgencyUser getStudent() {
		return student;
	}
	/**
	 * 设置所属学员。
	 * @param student
	 * 所属学员。
	 */
	public void setStudent(AgencyUser student) {
		this.student = student;
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
	 * 获取订单创建时间。
	 * @return 订单创建时间。
	 */
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
	public Set<StudentOrderDetail> getDetails() {
		return details;
	}
	/**
	 * 设置订单明细集合。
	 * @param details
	 * 订单明细集合。
	 */
	public void setDetails(Set<StudentOrderDetail> details) {
		this.details = details;
	}
}