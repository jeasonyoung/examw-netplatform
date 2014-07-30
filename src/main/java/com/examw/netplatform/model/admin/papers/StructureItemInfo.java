package com.examw.netplatform.model.admin.papers;

import java.math.BigDecimal;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;

/**
 * 试卷题目。
 * @author yangyong.
 * @since 2014-05-29.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StructureItemInfo extends Paging{
	private static final long serialVersionUID = 1L;
	private String id,structureId,serial,typeName;
	private BigDecimal score;
	private Integer type,orderNo;
	private ItemInfo item;
	private Set<StructureItemScoreInfo> itemScores;
	/**
	 * 获取试卷题目ID。
	 * @return
	 * 试卷题目ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置试卷题目ID。
	 * @param id
	 * 试卷题目ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取试卷结构ID。
	 * @return
	 * 试卷结构ID。
	 */
	public String getStructureId() {
		return structureId;
	}
	/**
	 * 设置试卷结构ID。
	 * @param structureId
	 * 试卷结构ID。
	 */
	public void setStructureId(String structureId) {
		this.structureId = structureId;
	}
	/**
	 * 获取结构题型。
	 * @return
	 * 结构题型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置结构题型。
	 * @param type
	 * 结构题型。
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 设置结构题型名称。
	 * @return
	 * 结构题型名称。
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置结构题型名称。
	 * @param typeName
	 * 结构题型名称。
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取题目序号。
	 * @return
	 * 题目序号。
	 */
	public String getSerial() {
		return serial;
	}
	/**
	 * 设置题目序号。
	 * @param serial
	 * 题目序号。
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}
	/**
	 * 获取题目分数。
	 * @return 
	 * 题目分数。
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置题目分数。
	 * @param score
	 * 题目分数。
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/**
	 * 获取题目数据。
	 * @return
	 * 题目数据。
	 */
	public ItemInfo getItem() {
		return item;
	}
	/**
	 * 设置题目数据。
	 * @param item
	 * 题目数据。
	 */
	public void setItem(ItemInfo item) {
		this.item = item;
	}
	/**
	 * 获取子题目分数集合。
	 * @return
	 * 子题目分数集合。
	 */
	public Set<StructureItemScoreInfo> getItemScores() {
		return itemScores;
	}
	/**
	 * 设置子题目分数集合。
	 * @param itemScores
	 * 子题目分数集合。
	 */
	public void setItemScores(Set<StructureItemScoreInfo> itemScores) {
		this.itemScores = itemScores;
	}
	/**
	 * 获取题目排序。
	 * @return
	 * 题目排序。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置题目排序。
	 * @param orderNo
	 * 题目排序。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
}