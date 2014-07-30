package com.examw.netplatform.model.admin.teacher;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.netplatform.model.admin.security.UserInfo;

/**
 * 教师信息
 * @author fengwei.
 * @since 2014年5月29日 下午3:06:32.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class TeacherInfo extends UserInfo {
	private static final long serialVersionUID = 1L;
	private String createUsername;
	private String description;
	private String agencyName;
	
	/**
	 * 获取 创建人
	 * @return createUsername
	 * 创建人
	 */
	public String getCreateUsername() {
		return createUsername;
	}
	/**
	 * 设置 创建人
	 * @param createUsername
	 * 创建人
	 */
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}
	/**
	 * 获取 教师介绍
	 * @return description
	 * 
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置 教师介绍
	 * @param description
	 * 
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取 所属机构名称
	 * @return agencyName
	 * 
	 */
	public String getAgencyName() {
		return agencyName;
	}
	/**
	 * 设置 所属机构名称
	 * @param agencyName
	 * 
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	
}
