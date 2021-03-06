package com.examw.netplatform.domain.admin.courses;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 班级。
 * @author fengwei.
 * @since 2014年5月20日 下午4:42:55.
 */
public class ClassPlan implements Serializable,Comparable<ClassPlan> {
	private static final long serialVersionUID = 1L;
	private String id,name,typeId,typeName,agencyId,agencyName,categoryId,examId,examName,subjectId,subjectName,description,imgUrl,videoUrl;
	private Integer useYear,totalHours,handoutMode,videoMode,status,orderNo;
	private BigDecimal price,discountPrice,wholesalePrice;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date startTime,endTime,createTime,lastTime;
	/**
	 * 获取班级ID。
	 * @return 班级ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置班级ID。
	 * @param id
	 * 班级ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取班级名称。
	 * @return 班级名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置班级名称。
	 * @param name
	 * 班级名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取班级类型ID。
	 * @return 班级类型ID。
	 */
	public String getTypeId() {
		return typeId;
	}
	/**
	 * 设置班级类型ID。
	 * @param typeId 
	 *	  班级类型ID。
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	/**
	 * 获取班级类型名称。
	 * @return 班级类型名称。
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置班级类型名称。
	 * @param typeName 
	 *	  班级类型名称。
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	/**
	 * 获取所属考试分类ID。
	 * @return 所属考试分类ID。
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置所属考试分类ID。
	 * @param categoryId 
	 *	  所属考试分类ID。
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取所属考试ID。
	 * @return 所属考试ID。
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置所属考试ID。
	 * @param examId 
	 *	  所属考试ID。
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取所属考试名称。
	 * @return 所属考试名称。
	 */
	public String getExamName() {
		return examName;
	}
	/**
	 * 设置所属考试名称。
	 * @param examName 
	 *	  所属考试名称。
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}
	/**
	 * 获取所属科目ID。
	 * @return 所属科目ID。
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置所属科目ID。
	 * @param subjectId 
	 *	  所属科目ID。
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取所属科目名称。
	 * @return 所属科目名称。
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/**
	 * 设置所属科目名称。
	 * @param subjectName 
	 *	  所属科目名称。
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	/**
	 * 获取班级描述。
	 * @return 班级描述。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置班级描述。
	 * @param remarks
	 * 班级描述。
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 获取原价。
	 * @return 原价。
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置原价。
	 * @param price
	 * 原价。
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取优惠价格。
	 * @return 优惠价格。
	 */
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}
	/**
	 * 设置优惠价格。
	 * @param discountPrice
	 *  优惠价格。
	 */
	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}
	/**
	 * 获取批发价。
	 * @return 批发价。
	 */
	public BigDecimal getWholesalePrice() {
		return wholesalePrice;
	}
	/**
	 * 设置批发价。
	 * @param wholesalePrice
	 *  批发价。
	 */
	public void setWholesalePrice(BigDecimal wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}
	/**
	 * 获取宣传图片地址。
	 * @return 宣传图片地址。
	 */
	public String getImgUrl() {
		return imgUrl;
	}
	/**
	 * 设置宣传图片地址。
	 * @param imgUrl
	 * 宣传图片地址。
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 * 获取试听地址。
	 * @return 试听地址。
	 */
	public String getVideoUrl() {
		return videoUrl;
	}
	/**
	 * 设置试听地址。
	 * @param videoUrl
	 * 试听地址。
	 */
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	/**
	 * 获取使用年份。
	 * @return  使用年份。
	 */
	public Integer getUseYear() {
		return useYear;
	}
	/**
	 * 设置使用年份。
	 * @param userYear
	 * 使用年份。
	 */
	public void setUseYear(Integer useYear) {
		this.useYear = useYear;
	}
	/**
	 * 获取班级课时。
	 * @return 班级课时。
	 */
	public Integer getTotalHours() {
		return totalHours;
	}
	/**
	 * 设置班级课时。
	 * @param totalHours
	 * 班级课时。
	 */
	public void setTotalHours(Integer totalHours) {
		this.totalHours = totalHours;
	}
	/**
	 * 获取讲义模式。
	 * @return 讲义模式。
	 */
	public Integer getHandoutMode() {
		return handoutMode;
	}
	/**
	 * 设置讲义模式。
	 * @param handoutMode
	 * 讲义模式。
	 */
	public void setHandoutMode(Integer handoutMode) {
		this.handoutMode = handoutMode;
	}
	/**
	 * 获取视频模式。
	 * @return  视频模式。
	 */
	public Integer getVideoMode() {
		return videoMode;
	}
	/**
	 * 设置视频模式。
	 * @param videoMode
	 * 视频模式。
	 */
	public void setVideoMode(Integer videoMode) {
		this.videoMode = videoMode;
	}
	/**
	 * 获取班级状态。
	 * @return 班级状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置班级状态。
	 * @param status
	 * 班级状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取开班时间。
	 * @return 开班时间。
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置开班时间。
	 * @param startTime
	 * 开班时间。
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取结班时间。
	 * @return 结班时间。
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置结班时间。
	 * @param endTime
	 * 结班时间。
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	 *  创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取最后修改时间。
	 * @return 最后修改时间。
	 */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置最后修改时间。
	 * @param lastTime
	 * 最后修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * 获取排序号。
	 * @return 排序号。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo 
	 *	  排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 班级是否过期。
	 * @return
	 */
	public boolean isOverdue()
	{
		if(this.endTime == null) return false;
		return !(this.endTime.compareTo(new Date()) > 0);
	}
	/**
	 * 班级是否开班。
	 * @return
	 */
	public boolean isOpen()
	{
		if(this.startTime == null || this.isOverdue()) return false;
		return new Date().compareTo(this.startTime) > 0;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ClassPlan o) {
		return this.orderNo - o.orderNo;
	}
}