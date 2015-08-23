package com.examw.netplatform.domain.admin.teachers;

import java.io.Serializable;
/**
 * 主讲教师。
 * 
 * @author jeasonyoung
 * @since 2015年8月23日
 */
public class Teacher implements Serializable{
	private static final long serialVersionUID = 1L;
	private String agencyId,agencyName,id,name,title,imgUrl,description;
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
	/**
	 * 获取教师ID。
	 * @return 教师ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置教师ID。
	 * @param id 
	 *	  教师ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取教师姓名。
	 * @return 教师姓名。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置教师姓名。
	 * @param name 
	 *	  教师姓名。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取职务职称。
	 * @return 职务职称。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置职务职称。
	 * @param title 
	 *	  职务职称。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取照片URL。
	 * @return 照片URL。
	 */
	public String getImgUrl() {
		return imgUrl;
	}
	/**
	 * 设置照片URL。
	 * @param imgUrl 
	 *	  照片URL。
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 * 获取描述。
	 * @return 描述。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置描述。
	 * @param description 
	 *	  描述。
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}