package com.examw.netplatform.model.admin.security;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.IPaging;
import com.examw.netplatform.domain.admin.security.User;
/**
 * 用户信息。
 * @author yangyong.
 * @since 2014-05-08.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class UserInfo extends User implements IPaging {
	private static final long serialVersionUID = 1L;
	private String genderName,typeName,statusName,order,sort;
	private Integer page,rows;
	/**
	 * 构造函数。
	 */
	public UserInfo(){
		this.setCreateTime(new Date());
	} 
	/**
	 * 获取性别名称。
	 * @return 性别名称。
	 */
	public String getGenderName() {
		return genderName;
	}
	/**
	 * 设置性别名称。
	 * @param genderName
	 * 性别名称。
	 */
	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}  
	/**
	 * 获取用户类型名称。
	 * @return 用户类型名称。
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置用户类型名称。
	 * @param typeName 
	 *	  用户类型名称。
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
	 * 状态名称。
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