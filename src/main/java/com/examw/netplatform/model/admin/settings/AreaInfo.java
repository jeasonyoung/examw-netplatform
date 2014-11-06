package com.examw.netplatform.model.admin.settings;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
/**
 * 行政地区信息
 * @author fengwei.
 * @since 2014年8月6日 上午11:31:30.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AreaInfo extends Paging implements Comparable<AreaInfo>{
	private static final long serialVersionUID = 1L;
	private String id,name,abbr;
	private Integer code;
	/**
	 * 获取地区ID。
	 * @return 地区ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置地区ID。
	 * @param id
	 * 地区ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取地区代码。
	 * @return 地区代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置地区代码。
	 * @param code
	 * 地区代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取地区名称。
	 * @return 地区名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置地区名称。
	 * @param name
	 * 地区名称。
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
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AreaInfo o) {
		if(this == o) return 0;
		int index = this.getCode() - o.getCode();
		if(index == 0){
			index = this.getAbbr().compareToIgnoreCase(o.getAbbr());
			if(index == 0){
				index = this.getName().compareToIgnoreCase(o.getName());
				if(index == 0){
					index = this.getId().compareToIgnoreCase(o.getId());
				}
			}
		}
		return index;
	}
}