package com.examw.netplatform.model.admin.courses;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;
/**
 * 套餐信息
 * @author fengwei.
 * @since 2014年5月21日 下午2:38:05.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class PackageInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,name,description,imgUrl,videoUrl,agencyId,agencyName,categoryId,examId,examName,statusName;
	private String[] subjectId,subjectName,classId;
	private Integer status,orderNo;
	private BigDecimal price,discountPrice,wholesalePrice;
	private Date startTime,endTime,expireTime,createTime,lastTime;
	/**
	 * 获取套餐ID。
	 * @return 套餐ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置套餐ID。
	 * @param id 
	 *	  套餐ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取套餐名称。
	 * @return 套餐名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置套餐名称。
	 * @param name 
	 *	  套餐名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取描述信息。
	 * @return 描述信息。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置描述信息。
	 * @param description 
	 *	  描述信息。
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 *	  宣传图片地址。
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 * 获取试听视频地址。
	 * @return 试听视频地址。
	 */
	public String getVideoUrl() {
		return videoUrl;
	}
	/**
	 * 设置试听视频地址。
	 * @param videoUrl 
	 *	  试听视频地址。
	 */
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
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
	 * 获取所属考试类别ID。
	 * @return 所属考试类别ID。
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置所属考试类别ID。
	 * @param categoryId 
	 *	  所属考试类别ID。
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
	 * 获取所属科目ID集合。
	 * @return 所属科目ID集合。
	 */
	public String[] getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置所属科目ID集合。
	 * @param subjectId 
	 *	  所属科目ID集合。
	 */
	public void setSubjectId(String[] subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取所属科目名称集合。
	 * @return 所属科目名称集合。
	 */
	public String[] getSubjectName() {
		return subjectName;
	}
	/**
	 * 设置所属科目名称集合。
	 * @param subjectName 
	 *	  所属科目名称集合。
	 */
	public void setSubjectName(String[] subjectName) {
		this.subjectName = subjectName;
	}
	/**
	 * 获取关联班级集合。
	 * @return 关联班级集合。
	 */
	public String[] getClassId() {
		return classId;
	}
	/**
	 * 设置关联班级集合。
	 * @param classId 
	 *	  关联班级集合。
	 */
	public void setClassId(String[] classId) {
		this.classId = classId;
	}
	/**
	 * 获取状态。
	 * @return 状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置状态。
	 * @param status 
	 *	  状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取状态名称。
	 * @return 状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置状态名称。
	 * @param statusName 
	 *	  状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
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
	 * 获取原价。
	 * @return 原价。
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置原价。
	 * @param price 
	 *	  原价。
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取优惠价。
	 * @return 优惠价。
	 */
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}
	/**
	 * 设置优惠价。
	 * @param discountPrice 
	 *	  优惠价。
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
	 *	  批发价。
	 */
	public void setWholesalePrice(BigDecimal wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}
	/**
	 * 获取报名开始时间。
	 * @return 报名开始时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置报名开始时间。
	 * @param startTime 
	 *	  报名开始时间。
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取报名结束时间。
	 * @return 报名结束时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置报名结束时间。
	 * @param endTime 
	 *	  报名结束时间。
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取套餐过期时间。
	 * @return 套餐过期时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getExpireTime() {
		return expireTime;
	}
	/**
	 * 设置套餐过期时间。
	 * @param expireTime 
	 *	  套餐过期时间。
	 */
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
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
	/**
	 * 获取最后修改时间。
	 * @return 最后修改时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置最后修改时间。
	 * @param lastTime 
	 *	  最后修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
}