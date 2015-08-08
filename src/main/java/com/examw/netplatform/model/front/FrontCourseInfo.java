package com.examw.netplatform.model.front;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.examw.model.Paging;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.admin.courses.PackageInfo;

/**
 * 前台课程信息[套餐,班级通用]
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
	 * 获取 考试分类ID
	 * 
	 * @return categroyId
	 * 
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * 设置 考试分类ID
	 * 
	 * @param categroyId
	 * 
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * 获取 考试ID
	 * 
	 * @return examId
	 * 
	 */
	public String getExamId() {
		return examId;
	}

	/**
	 * 设置 考试ID
	 * 
	 * @param examId
	 * 
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 转换为前台的套餐数据信息
	 * @return
	 */
	public PackageInfo toPackageInfo() {
		PackageInfo info = new FrontPackageInfo();
		BeanUtils.copyProperties(this, info);
		return info;
	}
	/**
	 * 转换为前台的班级数据信息
	 * @return
	 */
	public ClassPlanInfo toClassPlanInfo() {
		ClassPlanInfo info = new FrontClassPlanInfo();
		BeanUtils.copyProperties(this, info);
		return info;
	}

	/**
	 * 获取 机构ID
	 * @return agencyId
	 * 
	 */
	public String getAgencyId() {
		return agencyId;
	}

	/**
	 * 设置 机构ID
	 * @param agencyId
	 * 
	 */
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	/**
	 * 获取 课程状态
	 * @return status
	 * 
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置 课程状态
	 * @param status
	 * 
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取 结束时间
	 * @return endTime
	 * 
	 */
	public Date getEndTime() {
		return new Date();
	}

	/**
	 * 设置 结束时间
	 * @param endTime
	 * 
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 获取 期限
	 * @return expireTime
	 * 
	 */
	public Date getExpireTime() {
		return new Date();
	}

	/**
	 * 设置 期限
	 * @param expireTime
	 * 
	 */
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
}
