package com.examw.netplatform.domain.admin.courses;

import java.io.Serializable;
/**
 * 考试分类视图(含有考试的考试分类)
 * 
 * @author jeasonyoung
 * @since 2015年8月22日
 */
public class CategoryHasExamView implements Serializable{
	private static final long serialVersionUID = 1L;
	private String pid,id,name,categoryId,examId;
	/**
	 * 获取上级节点ID。
	 * @return 上级节点ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置上级节点ID。
	 * @param pid 
	 *	  上级节点ID。
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
	 * 获取名称。
	 * @return 名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置名称。
	 * @param name 
	 *	  名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取考试分类ID。
	 * @return 考试分类ID。
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置考试分类ID。
	 * @param categoryId 
	 *	  考试分类ID。
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取考试ID。
	 * @return 考试ID。
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置考试ID。
	 * @param examId 
	 *	  考试ID。
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
}