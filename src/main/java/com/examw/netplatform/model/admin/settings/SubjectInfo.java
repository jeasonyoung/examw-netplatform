package com.examw.netplatform.model.admin.settings;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;

/**
 * 科目信息
 * @author fengwei.
 * @since 2014年8月6日 下午3:05:25.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SubjectInfo extends Paging implements Comparable<SubjectInfo>{
	private static final long serialVersionUID = 1L;
	private String id,name,statusName,examId,examName,categoryId,categoryName;
	private Integer code,status;
	private String[] areaId,areaName;
	/**
	 * 获取科目ID。
	 * @return 科目ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置科目ID。
	 * @param id
	 * 科目ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取科目代码。
	 * @return 科目代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置科目代码。
	 * @param code
	 * 科目代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取科目名称。
	 * @return 科目名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置科目名称。
	 * @param name
	 * 科目名称。
	 */
	public void setName(String name) {
		this.name = name;
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
	 * 获取所属考试ID。
	 * @return 所属考试ID。
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置所属考试ID。
	 * @param examId
	 *  所属考试ID。
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取所属考试名称。
	 * @return 所属考试名称。
	 */
	public String getExamName() {
		return examName;
	}
	/**
	 * 设置所属考试名称。
	 * @param examName
	 *  所属考试名称。
	 */
	public void setExamName(String examName) {
		this.examName = examName;
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
	 * @return areaName
	 * 所属地区名称集合。
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
	/**
	 * 获取所属类别ID。
	 * @return categoryId
	 *  所属类别ID。
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置所属类别ID。
	 * @param categoryId
	 *  所属类别ID。
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取所属类别名称。
	 * @return categoryName
	 *  所属类别名称。
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * 设置所属类别名称。
	 * @param categoryName
	 *  所属类别名称。
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(SubjectInfo o) {
		if(this == o) return 0;
		int index = this.getCode() - o.getCode();
		if(index == 0){
			index = this.getExamId().compareToIgnoreCase(o.getExamId());
			if(index == 0){
				index = this.getName().compareToIgnoreCase(o.getName());
				if(index == 0){
					index = this.getId().compareToIgnoreCase(o.getId());
				}
			}
		}
		return index;
	}	
}