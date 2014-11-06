package com.examw.netplatform.model.admin.settings;

import java.io.Serializable;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;

/**
 * 科目章节信息
 * @author fengwei.
 * @since 2014年4月30日 下午3:08:42.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ChapterInfo extends Paging implements Serializable,Comparable<ChapterInfo>{
	private static final long serialVersionUID = 1L;
	private String pid, id,name,description,statusName,categoryId,examId,subjectId,subjectName,areaId,areaName;
	private Integer status,orderNo;
	private Set<ChapterInfo> children;
	/**
	 * 获取上级章节ID。
	 * @return 上级章节ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置上级章节ID。
	 * @param pid
	 * 上级章节ID。
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取章节ID。
	 * @return 章节ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置章节ID。
	 * @param id
	 *  章节ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取章节名称。
	 * @return 章节名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置章节名称。
	 * @param name
	 * 章节名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取章节描述。
	 * @return 章节描述。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置章节描述。
	 * @param description
	 * 章节描述。
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * 获取排序号。
	 * @return 排序号。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo 
	 *	  排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
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
	 *	  所属类别ID。
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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
	 *	  所属考试ID。
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取所属科目ID。
	 * @return 所属科目ID。
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置所属科目ID。
	 * @param subjectId 
	 *	  所属科目ID。
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取所属科目名称。
	 * @return 所属科目名称。
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/**
	 * 设置所属科目名称。
	 * @param subjectName 
	 *	  所属科目名称。
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	/**
	 * 获取所属地区ID。
	 * @return 所属地区ID。
	 */
	public String getAreaId() {
		return areaId;
	}
	/**
	 * 设置所属地区ID。
	 * @param areaId 
	 *	  所属地区ID。
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	/**
	 * 获取所属地区名称。
	 * @return 所属地区名称。
	 */
	public String getAreaName() {
		return areaName;
	}
	/**
	 * 设置所属地区名称。
	 * @param areaName 
	 *	  所属地区名称。
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	/**
	 * 获取子章节集合。
	 * @return 子章节集合。
	 */
	public Set<ChapterInfo> getChildren() {
		return children;
	}
	/**
	 * 设置子章节集合。
	 * @param children 
	 *	  子章节集合。
	 */
	public void setChildren(Set<ChapterInfo> children) {
		this.children = children;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ChapterInfo o) {
		int index = 0;
		if(this == o) return index;
		index = this.getOrderNo() - o.getOrderNo();
		if(index == 0){
			index = this.getName().compareToIgnoreCase(o.getName());
			if(index == 0){
				index = this.getId().compareToIgnoreCase(o.getId());
			}
		}
		return index;
	}
}