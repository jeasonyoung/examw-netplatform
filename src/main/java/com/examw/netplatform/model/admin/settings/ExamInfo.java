package com.examw.netplatform.model.admin.settings;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;

/**
 * 考试设置信息
 * @author fengwei.
 * @since 2014年8月6日 下午3:05:16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ExamInfo extends Paging implements Comparable<ExamInfo> {
	private static final long serialVersionUID = 1L;
	private String id,name,abbr,categoryId,categoryName,statusName;
	private String[] areaId,areaName;
	private Integer code,status;
	/**
	 * 获取考试ID。
	 * @return 考试ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置考试ID。
	 * @param id
	 * 考试ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取考试代码。
	 * @return 考试代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置考试代码。
	 * @param code
	 * 考试代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取考试名称。
	 * @return 考试名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置考试名称。
	 * @param name
	 * 考试名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取EN简称。
	 * @return EN简称。
	 */
	public String getAbbr() {
		return abbr;
	}
	/**
	 * 设置EN简称。
	 * @param abbr
	 * EN简称。
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	/**
	 * 获取状态。
	 * @return 状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置状态。
	 * @param status 
	 *	  状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	 * 获取所属类别ID。
	 * @return 所属类别ID。
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置所属类别ID。
	 * @param categoryId
	 * 所属类别ID。
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取所属类别名称。
	 * @return 所属类别名称。
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * 设置所属类别名称。
	 * @param categoryName
	 *所属类别名称。
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * 获取所属地区ID集合。
	 * @return 所属地区ID集合。
	 */
	public String[] getAreaId() {
		return areaId;
	}
	/**
	 * 设置所属地区ID集合。
	 * @param areaId
	 * 所属地区ID集合。
	 */
	public void setAreaId(String[] areaId) {
		this.areaId = areaId;
	}
	/**
	 * 获取所属地区名称集合。
	 * @return 所属地区名称集合。
	 */
	public String[] getAreaName() {
		return areaName;
	}
	/**
	 * 设置所属地区名称集合。
	 * @param areaName
	 * 所属地区名称集合。
	 */
	public void setAreaName(String[] areaName) {
		this.areaName = areaName;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ExamInfo o) {
		int index = 0;
		if(this == o) return index; 
		index = this.getCode() - o.getCode();
		if(index == 0){
			index = this.getName().compareToIgnoreCase(o.getName());
			if(index == 0){
				index = this.getId().compareToIgnoreCase(o.getId());
			}
		}
		return index;
	}
}