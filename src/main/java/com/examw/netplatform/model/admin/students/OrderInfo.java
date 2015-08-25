package com.examw.netplatform.model.admin.students;

import com.examw.model.IPaging;
import com.examw.netplatform.domain.admin.students.Order;

/**
 * 订单信息。
 * 
 * @author yangyong
 * @since 2014年12月1日
 */
public class OrderInfo extends Order implements IPaging {
	private static final long serialVersionUID = 1L;
	private String sourceName,statusName,order,sort;
	private Integer page,rows;
	private String[] studentIds,studentNames,classIds,classNames,packageIds,packageNames;
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
	 * 获取订单学员ID集合。
	 * @return 订单学员ID集合。
	 */
	public String[] getStudentIds() {
		return studentIds;
	}
	/**
	 * 设置订单学员ID集合。
	 * @param studentIds 
	 *	  订单学员ID集合。
	 */
	public void setStudentIds(String[] studentIds) {
		this.studentIds = studentIds;
	}
	/**
	 * 获取订单学员姓名集合。
	 * @return 订单学员姓名集合。
	 */
	public String[] getStudentNames() {
		return studentNames;
	}
	/**
	 * 设置订单学员姓名集合。
	 * @param studentNames 
	 *	  订单学员姓名集合。
	 */
	public void setStudentNames(String[] studentNames) {
		this.studentNames = studentNames;
	}
	/**
	 * 获取订单班级ID集合。
	 * @return 订单班级ID集合。
	 */
	public String[] getClassIds() {
		return classIds;
	}
	/**
	 * 设置订单班级ID集合。
	 * @param classIds 
	 *	  订单班级ID集合。
	 */
	public void setClassIds(String[] classIds) {
		this.classIds = classIds;
	}
	/**
	 * 获取订单班级名称集合。
	 * @return 订单班级名称集合。
	 */
	public String[] getClassNames() {
		return classNames;
	}
	/**
	 * 设置订单班级名称集合。
	 * @param classNames 
	 *	  订单班级名称集合。
	 */
	public void setClassNames(String[] classNames) {
		this.classNames = classNames;
	}
	/**
	 * 获取订单套餐ID集合。
	 * @return 订单套餐ID集合。
	 */
	public String[] getPackageIds() {
		return packageIds;
	}
	/**
	 * 设置订单套餐ID集合。
	 * @param packageIds 
	 *	  订单套餐ID集合。
	 */
	public void setPackageIds(String[] packageIds) {
		this.packageIds = packageIds;
	}
	/**
	 * 获取订单套餐名称集合。
	 * @return 订单套餐名称集合。
	 */
	public String[] getPackageNames() {
		return packageNames;
	}
	/**
	 * 设置订单套餐名称集合。
	 * @param packageNames 
	 *	  订单套餐名称集合。
	 */
	public void setPackageNames(String[] packageNames) {
		this.packageNames = packageNames;
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