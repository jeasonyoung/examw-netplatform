package com.examw.netplatform.domain.admin.teachers;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
/**
 * 试卷结构。
 * 
 * @author yangyong
 * @since 2014年11月22日
 */
public class Structure implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,title,description;
	private Integer type,total,orderNo;
	private BigDecimal score,min;
	private Practice practice;
	private Set<Item> items;
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
	 * 获取描述。
	 * @return 描述。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置描述。
	 * @param description 
	 *	  描述。
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * 获取试题总数。
	 * @return 试题总数。
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * 设置试题总数。
	 * @param total 
	 *	  试题总数。
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
	 * 获取最小分数。
	 * @return 最小分数。
	 */
	public BigDecimal getMin() {
		return min;
	}
	/**
	 * 设置最小分数。
	 * @param min 
	 *	  最小分数。
	 */
	public void setMin(BigDecimal min) {
		this.min = min;
	}
	/**
	 * 获取所属随堂练习。
	 * @return 所属随堂练习。
	 */
	public Practice getPractice() {
		return practice;
	}
	/**
	 * 设置所属随堂练习。
	 * @param practice 
	 *	  所属随堂练习。
	 */
	public void setPractice(Practice practice) {
		this.practice = practice;
	}
	/**
	 * 获取所属试题集合。
	 * @return 所属试题集合。
	 */
	public Set<Item> getItems() {
		return items;
	}
	/**
	 * 设置所属试题集合。
	 * @param items 
	 *	  所属试题集合。
	 */
	public void setItems(Set<Item> items) {
		this.items = items;
	}
}