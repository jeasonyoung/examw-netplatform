package com.examw.netplatform.domain.admin.papers;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * 试题结构与试题的关系
 * @author fengwei.
 * @since 2014年5月3日 下午4:24:27.
 */
public class StructureItem implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id,serial;
	private BigDecimal score;
	private Integer orderNo;
	
	private Item item;
	private Structure structure;
	
	private Set<StructureItemScore> itemScores;
	/**
	 * 获取结构题目ID。
	 * @return
	 * 结构题目ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置结构题目ID。
	 * @param id
	 * 结构题目ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取序号。
	 * @return
	 * 序号。
	 */
	public String getSerial() {
		return serial;
	}
	/**
	 * 设置序号。
	 * @param title
	 * 序号。
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}
	/**
	 * 获取 该题分数
	 * @return score
	 * 该题分数
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置 该题分数
	 * @param score
	 * 该题分数
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
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
	 * 获取 试题
	 * @return item
	 * 试题
	 */
	public Item getItem() {
		return item;
	}
	/**
	 * 设置 试题
	 * @param item
	 * 试题
	 */
	public void setItem(Item item) {
		this.item = item;
	}
	/**
	 * 获取 大题
	 * @return structure
	 * 大题
	 */
	public Structure getStructure() {
		return structure;
	}
	/**
	 * 设置 大题
	 * @param structure
	 * 大题
	 */
	public void setStructure(Structure structure) {
		this.structure = structure;
	}
	/**
	 * 获取子题目分数集合。
	 * @return
	 * 子题目分数集合。
	 */
	public Set<StructureItemScore> getItemScores() {
		return itemScores;
	}
	/**
	 * 设置子题目分数集合。
	 * @param itemScores
	 * 子题目分数集合。
	 */
	public void setItemScores(Set<StructureItemScore> itemScores) {
		this.itemScores = itemScores;
	}
}