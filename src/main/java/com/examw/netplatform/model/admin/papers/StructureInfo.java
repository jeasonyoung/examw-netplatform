package com.examw.netplatform.model.admin.papers;

import java.io.Serializable;
import java.math.BigDecimal; 
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * 试卷结构信息
 * @author fengwei.
 * @since 2014年5月5日 下午5:02:35.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StructureInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,pid, title,description,typeName,paperId;
	private Integer type,orderNo;
	private BigDecimal totalScore;
	private Set<StructureInfo> children;
	/**
	 * 获取所属试卷ID。
	 * @return
	 * 所属试卷ID。
	 */
	public String getPaperId() {
		return paperId;
	}
	/**
	 * 设置所属试卷ID。
	 * @param paperId
	 * 所属试卷ID。
	 */
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}
	/**
	 * 获取上级试卷结构ID。
	 * @return
	 * 上级试卷结构ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置上级试卷结构ID。
	 * @param pid
	 * 上级试卷结构ID。
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取结构ID。
	 * @return
	 * 结构ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置结构ID。
	 * @param id
	 * 结构ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取结构标题。
	 * @return
	 * 结构标题。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置结构标题。
	 * @param title
	 * 
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取结构描述。
	 * @return
	 * 结构描述。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置结构描述。
	 * @param description
	 * 结构描述。
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取题型名称。
	 * @return
	 * 题型名称。
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置题型名称。
	 * @param typeName
	 * 题型名称。
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取题型。
	 * @return
	 * 题型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置题型。
	 * @param type
	 * 题型。
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取排序号。
	 * @return
	 * 排序号。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo
	 * 排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取结构总分。
	 * @return
	 * 结构总分。
	 */
	public BigDecimal getTotalScore() {
		return totalScore;
	}
	/**
	 * 设置结构总分。
	 * @param totalScore
	 * 结构总分。
	 */
	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}
	/**
	 * 获取子结构集合。
	 * @return 子结构集合。
	 */
	public Set<StructureInfo> getChildren() {
		return children;
	}
	/**
	 * 设置子结构集合。
	 * @param children
	 * 子结构集合。
	 */
	public void setChildren(Set<StructureInfo> children) {
		this.children = children;
	}
}