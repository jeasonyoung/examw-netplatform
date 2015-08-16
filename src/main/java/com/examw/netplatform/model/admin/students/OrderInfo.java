package com.examw.netplatform.model.admin.students;

import java.util.Date;

import com.examw.model.IPaging;
import com.examw.netplatform.domain.admin.students.Order;

/**
 * 订单信息。
 * 
 * @author yangyong
 * @since 2014年12月1日
 */
//@JsonSerialize(include = Inclusion.NON_NULL)
public class OrderInfo extends Order implements IPaging {
	private static final long serialVersionUID = 1L;
	private String sourceName,statusName,order,sort;
	private Integer page,rows;
	/**
	 * 获取订单来源名称。
	 * @return 订单来源名称。
	 */
	public String getSourceName() {
		return sourceName;
	}
	/**
	 * 设置订单来源名称。
	 * @param sourceName 
	 *	  订单来源名称。
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
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
	 *	  订单状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	//@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	@Override
	public Date getCreateTime() {
		return super.getCreateTime();
	}
	/**
	 * 获取最后修改时间。
	 * @return 最后修改时间。
	 */
	//@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	@Override
	public Date getLastTime() {
		return super.getLastTime();
	}
	/*
	 * 获取页码。
	 * @see com.examw.model.IPaging#getPage()
	 */
	@Override
	public Integer getPage() {
		return this.page;
	}
	/*
	 * 设置页码。
	 * @see com.examw.model.IPaging#setPage(java.lang.Integer)
	 */
	@Override
	public void setPage(Integer page) {
		this.page = page;
	}
	/*
	 * 获取页数据。
	 * @see com.examw.model.IPaging#getRows()
	 */
	@Override
	public Integer getRows() {
		return this.rows;
	}
	/*
	 * 设置页数据。
	 * @see com.examw.model.IPaging#setRows(java.lang.Integer)
	 */
	@Override
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	/*
	 * 获取排序字段。
	 * @see com.examw.model.IPaging#getOrder()
	 */
	@Override
	public String getOrder() {
		return this.order;
	}
	/*
	 * 设置排序字段。
	 * @see com.examw.model.IPaging#setOrder(java.lang.String)
	 */
	@Override
	public void setOrder(String order) {
		 this.order = order;
	}
	/*
	 * 获取排序方式。
	 * @see com.examw.model.IPaging#getSort()
	 */
	@Override
	public String getSort() {
		return this.sort;
	}
	/*
	 * 设置排序方式。
	 * @see com.examw.model.IPaging#setSort(java.lang.String)
	 */
	@Override
	public void setSort(String sort) {
		this.sort = sort;
	}
}