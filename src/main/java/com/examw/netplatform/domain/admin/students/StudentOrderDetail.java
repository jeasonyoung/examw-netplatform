package com.examw.netplatform.domain.admin.students;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.courses.Package;
/**
 * 机构学员订单明细。
 * @author yangyong.
 * @since 2014-07-17.
 */
public class StudentOrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,title;
	private StudentOrder order;
	private Integer type;
	private ClassPlan plan;
	private Package pack;
	private BigDecimal price;
	private Date createTime;
	/**
	 * 类型－班级。
	 */
	public static final Integer TYPE_CLASS = 1;
	/**
	 * 类型－套餐。
	 */
	public static final Integer TYPE_PACKAGE = 2;
	/**
	 * 获取订单明细ID。
	 * @return 订单明细ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置订单明细ID。
	 * @param id
	 * 订单明细ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取标题。
	 * @return 标题。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置标题。
	 * @param title
	 * 标题。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取所属订单。
	 * @return 所属订单。
	 */
	public StudentOrder getOrder() {
		return order;
	}
	/**
	 * 设置所属订单。
	 * @param order
	 * 所属订单。
	 */
	public void setOrder(StudentOrder order) {
		this.order = order;
	}
	/**
	 * 获取类型。
	 * 1－班级，2-套餐。
	 * @return 类型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置类型。
	 * @param type
	 * 类型（1－班级，2-套餐）。
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取所属班级。
	 * @return 所属班级。
	 */
	public ClassPlan getPlan() {
		return plan;
	}
	/**
	 * 设置所属班级。
	 * @param plan
	 * 所属班级。
	 */
	public void setPlan(ClassPlan plan) {
		this.plan = plan;
	}
	/**
	 * 获取所属套餐。
	 * @return 所属套餐。
	 */
	public Package getPack() {
		return pack;
	}
	/**
	 * 设置所属套餐。
	 * @param pack
	 * 所属套餐。
	 */
	public void setPack(Package pack) {
		this.pack = pack;
	}
	/**
	 *  设置购买价格。
	 * @return 购买价格。
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置购买价格。
	 * @param price
	 * 购买价格。
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取购买时间。
	 * @return 购买时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置购买时间。
	 * @param createTime
	 * 购买时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}