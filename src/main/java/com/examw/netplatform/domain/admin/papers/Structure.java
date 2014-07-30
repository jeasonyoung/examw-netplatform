package com.examw.netplatform.domain.admin.papers;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * 试卷结构【大题】
 * @author fengwei.
 * @since 2014年5月3日 下午3:32:52.
 * 
 * 将其变为树形结构。
 * @author yangyong.
 * @since 2014-05-27.
 */
public class Structure implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id,title,description;
	private Integer type,orderNo;
	private BigDecimal totalScore;
	private Paper paper;
	private Structure parent;
	private Set<Structure> children;
	private Set<StructureItem> structureItems;
	/**
	 * 获取父结构。
	 * @return 父结构。
	 */
	public Structure getParent() {
		return parent;
	}
	/**
	 * 设置父结构。
	 * @param parent
	 * 父结构。
	 */
	public void setParent(Structure parent) {
		this.parent = parent;
	}
	/**
	 * 获取子结构集合。
	 * @return 子结构集合。
	 */
	public Set<Structure> getChildren() {
		return children;
	}
	/**
	 * 设置子结构集合。
	 * @param children
	 * 子结构集合。
	 */
	public void setChildren(Set<Structure> children) {
		this.children = children;
	}
	/**
	 * 获取 结构ID
	 * @return id
	 * 结构ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 结构ID
	 * @param id
	 * 结构ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 大题标题
	 * @return title
	 * 大题标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置 大题标题
	 * @param title
	 * 大题标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取 大题描述
	 * @return description
	 * 大题描述
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置 大题描述
	 * @param description
	 * 大题描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取 大题题型
	 * @return type
	 *  大题题型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置  大题题型
	 * @param type
	 *  大题题型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取 大题总分
	 * @return totalScore
	 * 大题总分
	 */
	public BigDecimal getTotalScore() {
		return totalScore;
	}
	/**
	 * 设置 大题总分
	 * @param totalScore
	 * 大题总分
	 */
	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}
	/**
	 * 获取 排序号
	 * @return orderNo
	 *  排序号
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置  排序号
	 * @param orderNo
	 *  排序号
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取 所属试卷
	 * @return paper
	 * 所属试卷
	 */
	public Paper getPaper() {
		return paper;
	}
	/**
	 * 设置 所属试卷
	 * @param paper
	 * 所属试卷
	 */
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	/**
	 * 获取结构题目集合。
	 * @return 结构题目集合。
	 */
	public Set<StructureItem> getStructureItems() {
		return structureItems;
	}
	/**
	 * 设置结构题目集合。
	 * @param structureItems
	 * 结构题目集合。
	 */
	public void setStructureItems(Set<StructureItem> structureItems) {
		this.structureItems = structureItems;
	}
}