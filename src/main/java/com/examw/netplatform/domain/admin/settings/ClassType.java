package com.examw.netplatform.domain.admin.settings;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * 班级类型。
 * @author yangyong.
 * @since 2014-05-20.
 */
public class ClassType implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name;
	private Integer orderNo;
	/**
	 * 获取班级类型。
	 * @return
	 * 	班级类型。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置班级类型。
	 * @param id
	 * 班级类型。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取类型名称。
	 * @return
	 * 类型名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置类型名称。
	 * @param name
	 * 类型名称。
	 */
	@NotEmpty(message = "班级类型名称不能为空！")
	@Length(max=50, message="班级类型名称太长！")
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取排序号。
	 * @return
	 * 排序号。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo
	 * 排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
}