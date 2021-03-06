package com.examw.netplatform.model.admin.courses;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.examw.model.IPaging;
import com.examw.netplatform.domain.admin.courses.Package;
/**
 * 套餐信息
 * @author fengwei.
 * @since 2014年5月21日 下午2:38:05.
 */
public class PackageInfo extends Package implements IPaging {
	private static final long serialVersionUID = 1L;
	private String statusName,order,sort;
	private String[] classIds;
	private Integer page,rows;
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
	 * 设置报名开始时间。
	 * @param startTime 
	 *	  报名开始时间。
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setStartTime(Date startTime) {
		super.setStartTime(startTime);
	}
	/**
	 * 设置报名结束时间。
	 * @param endTime 
	 *	  报名结束时间。
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setEndTime(Date endTime) {
		super.setEndTime(endTime);
	}
	/**
	 * 设置套餐过期时间。
	 * @param expireTime 
	 *	  套餐过期时间。
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setExpireTime(Date expireTime) {
		super.setExpireTime(expireTime);
	}
	/**
	 * 获取套餐班级ID集合。
	 * @return 套餐班级ID集合。
	 */
	public String[] getClassIds() {
		return classIds;
	}
	/**
	 * 设置套餐班级ID集合。
	 * @param classIds 
	 *	  套餐班级ID集合。
	 */
	public void setClassIds(String[] classIds) {
		this.classIds = classIds;
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