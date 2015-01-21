package com.examw.netplatform.model.front;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.BeanUtils;

import com.examw.model.Paging;
import com.examw.netplatform.model.admin.settings.ExamInfo;

/**
 * 前端考试信息。
 * 
 * @author yangyong
 * @since 2014年10月27日
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FrontExamInfo extends Paging implements Comparable<FrontExamInfo> {
	private static final long serialVersionUID = 1L;
	private String id,name,abbr,categoryId,categoryName;
	private String[] areaId,areaName;
	private Integer code;
	/**
	 * 构造函数。
	 */
	public FrontExamInfo(){
		
	}
	/**
	 * 构造函数。
	 * @param exam
	 */
	public FrontExamInfo(ExamInfo exam){
		this();
		if(exam != null){
			BeanUtils.copyProperties(exam, this);
		}
	}
	/**
	 * 获取考试ID。
	 * @return 考试ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置考试ID。
	 * @param id
	 * 考试ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取考试代码。
	 * @return 考试代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置考试代码。
	 * @param code
	 * 考试代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取考试名称。
	 * @return 考试名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置考试名称。
	 * @param name
	 * 考试名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取EN简称。
	 * @return EN简称。
	 */
	public String getAbbr() {
		return abbr;
	}
	/**
	 * 设置EN简称。
	 * @param abbr
	 * EN简称。
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	/**
	 * 获取考试类别ID。
	 * @return categoryId
	 * 考试类别ID。
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置考试类别ID。
	 * @param categoryId
	 * 考试类别ID。
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取考试类别名称。
	 * @return categoryName
	 * 考试类别名称。
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * 设置考试类别名称。
	 * @param categoryName
	 * 考试类别名称。
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * 获取所属地区ID集合。
	 * @return 所属地区ID集合。
	 */
	public String[] getAreaId() {
		return areaId;
	}
	/**
	 * 设置所属地区ID集合。
	 * @param areaId
	 * 所属地区ID集合。
	 */
	public void setAreaId(String[] areaId) {
		this.areaId = areaId;
	}
	/**
	 * 获取所属地区名称集合。
	 * @return 所属地区名称集合。
	 */
	public String[] getAreaName() {
		return areaName;
	}
	/**
	 * 设置所属地区名称集合。
	 * @param areaName
	 * 所属地区名称集合。
	 */
	public void setAreaName(String[] areaName) {
		this.areaName = areaName;
	}
	/**
	 * 排序比较。
	 */
	@Override
	public int compareTo(FrontExamInfo o) {
		int index = 0;
		if(this == o) return index; 
		index = this.getCode() - o.getCode();
		if(index == 0){
			index = this.getName().compareToIgnoreCase(o.getName());
			if(index == 0){
				index = this.getId().compareToIgnoreCase(o.getId());
			}
		}
		return index;
	}
}