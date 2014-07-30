package com.examw.netplatform.model.admin.papers;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 试卷中的子题目分数信息。
 * @author yangyong.
 * @since 2014-05-29.
 */
public class StructureItemScoreInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,itemId,serial;
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
	 * 获取子题目ID。
	 * @return
	 * 题目ID。
	 */
	public String getItemId() {
		return itemId;
	}
	/**
	 * 设置子题目ID。
	 * @param itemId
	 * 子题目ID。
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	/**
	 * 获取试卷中子题目分数。
	 * @return
	 * 试卷中子题目分数。
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置试卷中子题目分数。
	 * @param score
	 * 试卷中子题目分数。
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