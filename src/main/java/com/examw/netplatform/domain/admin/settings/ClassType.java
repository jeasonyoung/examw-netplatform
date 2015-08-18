package com.examw.netplatform.domain.admin.settings;

import java.io.Serializable;
/**
 * 班级类型。
 * @author yangyong.
 * @since 2014-05-20.
 */
public class ClassType implements Serializable,Comparable<ClassType> {
	private static final long serialVersionUID = 1L;
	private String id,name,agencyId,agencyName;
	private Integer code;
	/**
	 * 获取班级类型ID。
	 * @return 班级类型ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置班级类型ID。
	 * @param id
	 * 班级类型ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取班级类型代码。
	 * @return 班级类型代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置班级类型代码。
	 * @param code 
	 *	  班级类型代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取班级类型名称。
	 * @return 班级类型名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置班级类型名称。
	 * @param name
	 *  班级类型名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取所属机构ID。
	 * @return 所属机构ID。
	 */
	public String getAgencyId() {
		return agencyId;
	}
	/**
	 * 设置所属机构ID。
	 * @param agencyId 
	 *	  所属机构ID。
	 */
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	/**
	 * 获取所属机构名称。
	 * @return 所属机构名称。
	 */
	public String getAgencyName() {
		return agencyName;
	}
	/**
	 * 设置所属机构名称。
	 * @param agencyName 
	 *	  所属机构名称。
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ClassType o) {
		return this.code - o.code;
	}
}