package com.examw.netplatform.model.admin.settings;

import com.examw.model.IPaging;
import com.examw.netplatform.domain.admin.settings.MsgBody;

/**
 * 消息内容信息。
 * 
 * @author jeasonyoung
 * @since 2015年8月30日
 */
public class MsgBodyInfo extends MsgBody implements IPaging {
	private static final long serialVersionUID = 1L;
	private String typeName,statusName,order,sort;
	private Integer page,rows;
	private String[] studentIds;
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
	 * 获取学生ID集合。
	 * @return 学生ID集合。
	 */
	public String[] getStudentIds() {
		return studentIds;
	}
	/**
	 * 设置学生ID集合。
	 * @param studentIds 
	 *	  学生ID集合。
	 */
	public void setStudentIds(String[] studentIds) {
		this.studentIds = studentIds;
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