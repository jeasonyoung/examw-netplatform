package com.examw.netplatform.model.admin.students;

import com.examw.model.IPaging;
import com.examw.netplatform.domain.admin.students.Learning;

/**
 * 学习进度信息。
 * @author fengwei.
 * @since 2014年5月29日 上午11:39:48.
 */
public class LearningInfo extends Learning implements IPaging {
	private static final long serialVersionUID = 1L;
	private String statusName,order,sort;
	private Integer page,rows;
	/**
	 * 获取进度状态名称。
	 * @return 进度状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置进度状态名称。
	 * @param statusName 
	 *	  进度状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
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