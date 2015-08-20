package com.examw.netplatform.domain.admin.settings;

import java.io.Serializable;
/**
 * 考试科目树。
 * 
 * @author jeasonyoung
 * @since 2015年8月20日
 */
public class ExamSubjectView implements Serializable {
	private static final long serialVersionUID = 1L;
	private String pid,id,name,type,categoryId,examId,subjectId;
	private Integer orderNo,status;
	/**
	 * 获取所属考试分类ID。
	 * @return 所属考试分类ID。
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置所属考试分类ID。
	 * @param categoryId 
	 *	  所属考试分类ID。
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
	 * 获取父节点ID。
	 * @return 父节点ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置父节点ID。
	 * @param pid 
	 *	  父节点ID。
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取节点ID。
	 * @return 节点ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置节点ID。
	 * @param id 
	 *	  节点ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取节点名称。
	 * @return 节点名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置节点名称。
	 * @param name 
	 *	  节点名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取节点类型。
	 * @return 节点类型。
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置节点类型。
	 * @param type 
	 *	  节点类型。
	 */
	public void setType(String type) {
		this.type = type;
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
	 * 获取节点状态。
	 * @return 节点状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置节点状态。
	 * @param status 
	 *	  节点状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
}