package com.examw.netplatform.model.admin.teachers;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 结构信息。
 * 
 * @author yangyong
 * @since 2014年11月22日
 */
public class StructureInfo implements Serializable,Comparable<StructureInfo> {
	private static final long serialVersionUID = 1L;
	private String id,title,description,practiceId,practiceName;
	private Integer type,total,orderNo;
	private BigDecimal score,min;
	/**
	 * 获取结构ID。
	 * @return 结构ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置结构ID。
	 * @param id 
	 *	  结构ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取结构名称。
	 * @return 结构名称。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置结构名称。
	 * @param title 
	 *	  结构名称。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取结构描述。
	 * @return 结构描述。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置结构描述。
	 * @param description 
	 *	  结构描述。
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取所属练习题ID。
	 * @return 所属练习题ID。
	 */
	public String getPracticeId() {
		return practiceId;
	}
	/**
	 * 设置所属练习题ID。
	 * @param practiceId 
	 *	  所属练习题ID。
	 */
	public void setPracticeId(String practiceId) {
		this.practiceId = practiceId;
	}
	/**
	 * 获取所属练习题名称。
	 * @return 所属练习题名称。
	 */
	public String getPracticeName() {
		return practiceName;
	}
	/**
	 * 设置所属练习题名称。
	 * @param practiceName 
	 *	  所属练习题名称。
	 */
	public void setPracticeName(String practiceName) {
		this.practiceName = practiceName;
	}
	/**
	 * 获取类型。
	 * @return 类型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置类型。
	 * @param type 
	 *	  类型。
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取总分。
	 * @return 总分。
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * 设置总分。
	 * @param total 
	 *	  总分。
	 */
	public void setTotal(Integer total) {
		this.total = total;
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
	 * 获取分数。
	 * @return 分数。
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置分数。
	 * @param score 
	 *	  分数。
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/**
	 * 获取最少得分。
	 * @return 最少得分。
	 */
	public BigDecimal getMin() {
		return min;
	}
	/**
	 * 设置最少得分。
	 * @param min 
	 *	  最少得分。
	 */
	public void setMin(BigDecimal min) {
		this.min = min;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(StructureInfo o) {
		int index = 0;
		if(this == o) return index;
		index = this.getOrderNo() - o.getOrderNo();
		if(index == 0){
			index = this.getTitle().compareToIgnoreCase(o.getTitle());
			if(index == 0){
				index = this.getId().compareToIgnoreCase(o.getId());
			}
		}
		return index;
	}
}