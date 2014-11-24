package com.examw.netplatform.model.admin.teachers;

import java.util.Set;

import com.examw.model.Paging;
/**
 * 试题信息。
 * 
 * @author yangyong
 * @since 2014年11月22日
 */
public class ItemInfo extends Paging implements Comparable<ItemInfo> {
	private static final long serialVersionUID = 1L;
	private String pid,id,content,answer,analysis,structureId,practiceId,typeName;
	private Integer type,count,year,orderNo;
	private Set<ItemInfo> children;
	/**
	 * 获取父试题ID。
	 * @return 父试题ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置父试题ID。
	 * @param pid 
	 *	  父试题ID。
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
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
	 * 获取试题解析。
	 * @return 试题解析。
	 */
	public String getAnalysis() {
		return analysis;
	}
	/**
	 * 设置试题解析。
	 * @param analysis 
	 *	  试题解析。
	 */
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	/**
	 * 获取所属结构ID。
	 * @return 所属结构ID。
	 */
	public String getStructureId() {
		return structureId;
	}
	/**
	 * 设置所属结构ID。
	 * @param structureId 
	 *	  所属结构ID。
	 */
	public void setStructureId(String structureId) {
		this.structureId = structureId;
	}
	/**
	 * 获取所属随堂练习ID。
	 * @return 所属随堂练习ID。
	 */
	public String getPracticeId() {
		return practiceId;
	}
	/**
	 * 设置所属随堂练习ID。
	 * @param practiceId 
	 *	  所属随堂练习ID。
	 */
	public void setPracticeId(String practiceId) {
		this.practiceId = practiceId;
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
	 * 获取typeName
	 * @return typeName
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置 typeName
	 * @param typeName 
	 *	  typeName
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	 * 设置使用年份。
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
	 * 获取子试题集合。
	 * @return 子试题集合。
	 */
	public Set<ItemInfo> getChildren() {
		return children;
	}
	/**
	 * 设置子试题集合。
	 * @param children 
	 *	  子试题集合。
	 */
	public void setChildren(Set<ItemInfo> children) {
		this.children = children;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ItemInfo o) {
		int index = 0;
		if(this == o) return index;
		index = this.getOrderNo() - o.getOrderNo();
		if(index == 0){
			index = this.getContent().compareToIgnoreCase(o.getContent());
			if(index == 0){
				index = this.getId().compareToIgnoreCase(o.getId());
			}
		}
		return index;
	}
}