package com.examw.netplatform.domain.admin.teachers;

import java.io.Serializable;
import java.util.Set;
/**
 * 试题。
 * 
 * @author yangyong
 * @since 2014年11月22日
 */
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,content,answer,analysis;
	private Integer type,count,year,orderNo;
	private Item parent;
	private Set<Item> children;
	private Structure structure;
	/**
	 * 获取试题ID。
	 * @return 试题ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置试题ID。
	 * @param id 
	 *	  试题ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取试题内容。
	 * @return 试题内容。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置试题内容。
	 * @param content 
	 *	  试题内容。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取试题答案。
	 * @return 试题答案。
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * 设置试题答案。
	 * @param answer 
	 *	  试题答案。
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/**
	 * 获取试题分析。
	 * @return 试题分析。
	 */
	public String getAnalysis() {
		return analysis;
	}
	/**
	 * 设置试题分析。
	 * @param analysis 
	 *	  试题分析。
	 */
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	/**
	 * 获取试题类型。
	 * @return 试题类型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置试题类型。
	 * @param type 
	 *	  试题类型。
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取试题数目。
	 * @return 试题数目。
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * 设置试题数目。
	 * @param count 
	 *	  试题数目。
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * 获取使用年份。
	 * @return 使用年份。
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * 设置使用年份。r
	 * @param year 
	 *	  使用年份。
	 */
	public void setYear(Integer year) {
		this.year = year;
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
	 * 获取上级试题。
	 * @return 上级试题。
	 */
	public Item getParent() {
		return parent;
	}
	/**
	 * 设置上级试题。
	 * @param parent 
	 *	  上级试题。
	 */
	public void setParent(Item parent) {
		this.parent = parent;
	}
	/**
	 * 获取子试题集合。
	 * @return 子试题集合。
	 */
	public Set<Item> getChildren() {
		return children;
	}
	/**
	 * 设置子试题集合。
	 * @param children 
	 *	  子试题集合。
	 */
	public void setChildren(Set<Item> children) {
		this.children = children;
	}
	/**
	 * 获取试题所属结构。
	 * @return 试题所属结构。
	 */
	public Structure getStructure() {
		return structure;
	}
	/**
	 * 设置试题所属结构。
	 * @param structure 
	 *	  试题所属结构。
	 */
	public void setStructure(Structure structure) {
		this.structure = structure;
	}
}