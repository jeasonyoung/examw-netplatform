package com.examw.netplatform.model.admin.teachers;

import com.examw.model.IPaging;
import com.examw.netplatform.domain.admin.teachers.Teacher;
/**
 * 主讲教师信息。
 * 
 * @author jeasonyoung
 * @since 2015年8月23日
 */
public class TeacherInfo extends Teacher implements IPaging {
	private static final long serialVersionUID = 1L;
	private String order,sort;
	private Integer page,rows;
	private String[] classIds,classNames;
	/**
	 * 获取主讲教师班级ID集合。
	 * @return 主讲教师班级ID集合。
	 */
	public String[] getClassIds() {
		return classIds;
	}
	/**
	 * 设置主讲教师班级ID集合。
	 * @param classIds 
	 *	  主讲教师班级ID集合。
	 */
	public void setClassIds(String[] classIds) {
		this.classIds = classIds;
	}
	/**
	 * 获取主讲教师班级名称集合。
	 * @return 主讲教师班级名称集合。
	 */
	public String[] getClassNames() {
		return classNames;
	}
	/**
	 * 设置主讲教师班级名称集合。
	 * @param classNames 
	 *	  主讲教师班级名称集合。
	 */
	public void setClassNames(String[] classNames) {
		this.classNames = classNames;
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