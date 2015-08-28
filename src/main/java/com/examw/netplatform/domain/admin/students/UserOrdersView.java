package com.examw.netplatform.domain.admin.students;

import java.io.Serializable;

/**
 * 用户订单套餐/班级视图。
 * 
 * @author jeasonyoung
 * @since 2015年8月28日
 */
public class UserOrdersView implements Serializable{
	private static final long serialVersionUID = 1L;
	private String pid,id,name,type,orderNo;
	/**
	 * 获取上级ID。
	 * @return 上级ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置上级ID。
	 * @param pid 
	 *	  上级ID。
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取ID。
	 * @return ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置ID。
	 * @param id 
	 *	  ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取名称。
	 * @return 名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置名称。
	 * @param name 
	 *	  名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取类型。
	 * @return 类型。
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置类型。
	 * @param type 
	 *	  类型。
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取排序号。
	 * @return 排序号。
	 */
	public String getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo 
	 *	  排序号。
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}