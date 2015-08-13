package com.examw.netplatform.domain.admin.settings;

import java.io.Serializable;
/**
 * 科目章节
 * @author fengwei.
 * @since 2014年4月30日 下午2:56:46.
 */
public class Chapter implements Serializable,Comparable<Chapter>{
	private static final long serialVersionUID = 1L;
	private String pid,id,name,description,subjectId,subjectName;
	private Integer status,orderNo;
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
	 * 获取上级章节ID。
	 * @return 上级章节ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置上级章节ID。
	 * @param pid 
	 *	  上级章节ID。
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
	 * 章节ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取章节名称。
	 * @return  章节名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置章节名称。
	 * @param name
	 *  章节名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取章节描述。
	 * @return  章节描述。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置章节描述。
	 * @param description
	 *  章节描述。
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
	 * 获取排序号。
	 * @return 排序号。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo
	 *  排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Chapter o) {
		return this.orderNo - o.orderNo;
	}
}