package com.examw.netplatform.model;

import java.io.Serializable;

/**
 * 枚举值名数据。
 * 
 * @author jeasonyoung
 * @since 2015年8月18日
 */
public class EnumValueName implements Serializable, Comparable<EnumValueName> {
	private static final long serialVersionUID = 1L;
	private Integer value;
	private String name;
	/**
	 * 构造函数。
	 * @param value
	 * 枚举值。
	 * @param name
	 * 枚举名称。
	 */
	public EnumValueName(Integer value, String name){
		this.value = value;
		this.name = name;
	}
	/**
	 * 获取枚举值。
	 * @return 枚举值。
	 */
	public Integer getValue() {
		return value;
	}
	/**
	 * 获取枚举名称。
	 * @return 枚举名称。
	 */
	public String getName() {
		return name;
	}
	/*
	 * 比较排序。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(EnumValueName o) {
		return this.value - o.value;
	}
}