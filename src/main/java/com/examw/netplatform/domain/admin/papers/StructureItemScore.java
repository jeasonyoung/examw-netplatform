package com.examw.netplatform.domain.admin.papers;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 结构题目下的分数。
 * @author yangyong.
 * @since 2014-05-27.
 */
public class StructureItemScore implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,serial,item;
	private StructureItem structureItem;
	private BigDecimal score;
	private Integer orderNo;
	/**
	 * 获取结构下题目分数ID。
	 * @return 结构下题目分数ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置结构下题目分数ID。
	 * @param id
	 * 结构下题目分数ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取题号。
	 * @return
	 * 题号。
	 */
	public String getSerial() {
		return serial;
	}
	/**
	 * 设置题号。
	 * @param serial
	 * 题号。
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}
	/**
	 * 获取结构题目。
	 * @return
	 * 结构题目。
	 */
	public StructureItem getStructureItem() {
		return structureItem;
	}
	/**
	 * 设置结构题目。
	 * @param structureItem
	 * 结构题目。
	 */
	public void setStructureItem(StructureItem structureItem) {
		this.structureItem = structureItem;
	}
	/**
	 * 获取题目ID。
	 * @return
	 * 题目。
	 */
	public String getItem() {
		return item;
	}
	/**
	 * 设置题目ID。
	 * @param item
	 * 题目ID。
	 */
	public void setItem(String item) {
		this.item = item;
	}
	/**
	 * 获取得分。
	 * @return
	 * 得分。
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 *  设置得分。
	 * @param score
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
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
}