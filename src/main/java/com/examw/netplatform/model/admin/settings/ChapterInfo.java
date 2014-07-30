package com.examw.netplatform.model.admin.settings;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;

/**
 * 科目章节信息
 * @author fengwei.
 * @since 2014年4月30日 下午3:08:42.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ChapterInfo extends Paging implements Serializable{
	private static final long serialVersionUID = 1L;
	private String pid, id,description,name,catalogId,examId,subjectId,subjectName;
	private Integer orderNo;
	private List<ChapterInfo> children;
	/**
	 * 获取 
	 * @return pid
	 * 
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置 章节父ID
	 * @param pid
	 * 章节父ID
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取 章节ID
	 * @return id
	 * 章节ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 章节ID
	 * @param id
	 * 章节ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 章节描述
	 * @return description
	 * 章节描述
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置 章节描述
	 * @param description
	 * 章节描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取 章节名称
	 * @return name
	 * 章节名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 章节名称
	 * @param name
	 * 章节名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 所属考试类别ID。
	 * @return
	 * 考试类别ID。
	 */
	public String getCatalogId() {
		return catalogId;
	}
	/**
	 * 设置考试类别ID。
	 * @param catalogId
	 * 考试类别ID。
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	/**
	 * 获取所属考试ID。
	 * @return
	 * 所属考试ID。
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置所属考试ID。
	 * @param examId
	 * 所属考试ID。
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取 科目ID
	 * @return subjectId
	 * 科目ID
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置 科目ID
	 * @param subjectId
	 * 科目ID
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取 科目名称
	 * @return subjectName
	 * 科目名称
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/**
	 * 设置 科目名称
	 * @param subjectName
	 * 科目名称
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	/**
	 * 获取 排序号
	 * @return orderNo
	 * 排序号
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置 排序号
	 * @param orderNo
	 * 排序号
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取 子章节集合
	 * @return children
	 * 子章节集合
	 */
	public List<ChapterInfo> getChildren() {
		return children;
	}
	/**
	 * 设置 子章节集合
	 * @param children
	 * 子章节集合
	 */
	public void setChildren(List<ChapterInfo> children) {
		this.children = children;
	}
}