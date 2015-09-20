package com.examw.netplatform.domain.admin.teachers;

import java.io.Serializable;
import java.util.Date;
/**
 * 学员建议。
 * 
 * @author jeasonyoung
 * @since 2015年9月19日
 */
public class Suggest implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id, agencyId,studentId,content;
	private Date createTime;
	/**
	 * 获取建议ID。
	 * @return 建议ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置建议ID。
	 * @param id 
	 *	  建议ID。
	 */
	public void setId(String id) {
		this.id = id;
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
	 * 获取所属学员ID。
	 * @return 所属学员ID。
	 */
	public String getStudentId() {
		return studentId;
	}
	/**
	 * 设置所属学员ID。
	 * @param studentId 
	 *	  所属学员ID。
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	/**
	 * 获取建议内容。
	 * @return 建议内容。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置建议内容。
	 * @param content 
	 *	  建议内容。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime 
	 *	  创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}