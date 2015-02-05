package com.examw.netplatform.model.front;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.examw.model.Paging;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.admin.courses.PackageInfo;

/**
 * 
 * @author fengwei.
 * @since 2015年2月3日 上午11:48:34.
 */
public class FrontCourseInfo extends Paging implements Serializable {
	private static final long serialVersionUID = 1L;
	private String categoryId, examId;
	private String agencyId;
	private Integer status;
	@SuppressWarnings("unused")
	private Date endTime,expireTime;
	/**
	 * 获取
	 * 
	 * @return categroyId
	 * 
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * 设置
	 * 
	 * @param categroyId
	 * 
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * 获取
	 * 
	 * @return examId
	 * 
	 */
	public String getExamId() {
		return examId;
	}

	/**
	 * 设置
	 * 
	 * @param examId
	 * 
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}

	public PackageInfo toPackageInfo() {
		PackageInfo info = new PackageInfo();
		BeanUtils.copyProperties(this, info);
		return info;
	}

	public ClassPlanInfo toClassPlanInfo() {
		ClassPlanInfo info = new ClassPlanInfo();
		BeanUtils.copyProperties(this, info);
		return info;
	}

	/**
	 * 获取 
	 * @return agencyId
	 * 
	 */
	public String getAgencyId() {
		return agencyId;
	}

	/**
	 * 设置 
	 * @param agencyId
	 * 
	 */
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	/**
	 * 获取 
	 * @return status
	 * 
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置 
	 * @param status
	 * 
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取 
	 * @return endTime
	 * 
	 */
	public Date getEndTime() {
		return new Date();
	}

	/**
	 * 设置 
	 * @param endTime
	 * 
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 获取 
	 * @return expireTime
	 * 
	 */
	public Date getExpireTime() {
		return new Date();
	}

	/**
	 * 设置 
	 * @param expireTime
	 * 
	 */
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
}
