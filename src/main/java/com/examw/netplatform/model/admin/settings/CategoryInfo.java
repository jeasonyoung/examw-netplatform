package com.examw.netplatform.model.admin.settings;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.examw.model.Paging;

/**
 * 考试类别信息
 * @author fengwei.
 * @since 2014年8月6日 上午11:37:04.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CategoryInfo extends Paging implements Comparable<CategoryInfo> {
	private static final long serialVersionUID = 1L;
	private String pid,id,name,abbr;
	private String fullName;
	private Integer code;
	/**
	 * 获取上级类别ID。
	 * @return pid
	 * 上级类别ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置上级类别ID。
	 * @param pid
	 * 上级类别ID。
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取类别ID。
	 * @return 类别ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置类别ID。
	 * @param id
	 * 类别ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取类别代码。
	 * @return 类别代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置类别代码。
	 * @param code
	 * 类别代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取类别名称。
	 * @return 类别名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置类别名称。
	 * @param name
	 * 类别名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取类别EN简称。
	 * @return 类别EN简称。
	 */
	public String getAbbr() {
		return abbr;
	}
	/**
	 * 设置类别EN简称。
	 * @param abbr
	 * 类别EN简称
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	/**
	 * 获取类别全称
	 * @return 类别全称
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * 设置类别全称
	 * @param fullName
	 * 类别全称
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(CategoryInfo o) {
		if(this == o) return 0;
		int index = this.getCode() - o.getCode();
		if(index == 0){
			index = this.getName().compareTo(o.getName());
			if(index == 0){
				index = this.getId().compareTo(o.getId());
			}
		}
		return index;
	}	
}