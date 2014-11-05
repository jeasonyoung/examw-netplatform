package com.examw.netplatform.model.admin.courses;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.format.annotation.DateTimeFormat;

import com.examw.model.Paging;
import com.examw.netplatform.model.admin.IUser;
import com.examw.support.CustomDateSerializer;
/**
 * 开班计划信息
 * @author fengwei.
 * @since 2014年5月20日 下午5:22:02.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ClassPlanInfo extends Paging implements IUser {
	private static final long serialVersionUID = 1L;
	private String id,name,agencyId,agencyName,catalogId,catalogName,examId,examName,subjectId,subjectName,classTypeId,
				classTypeName,remarks,imgUrl,videoUrl,handoutModeName,videoModeName,statusName,currentUserId;
	private Integer useYear,totalHours,handoutMode,videoMode,status;
	private BigDecimal price,discountPrice,wholesalePrice;
	private Date startTime,endTime,createTime,lastTime;
	/**
	 * 获取班级ID。
	 * @return id 班级ID。
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
	 * @return name 班级名称。
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
	 * 获取所属机构ID。
	 * @return agencyId 所属机构ID。
	 */
	public String getAgencyId() {
		return agencyId;
	}
	/**
	 * 设置所属机构ID。
	 * @param agencyId
	 * 所属机构ID。
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
	 * 所属机构名称。
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	/**
	 * 获取所属考试类型ID。
	 * @return 所属考试类型ID。
	 */
	public String getCatalogId() {
		return catalogId;
	}
	/**
	 * 设置所属考试类型ID。
	 * @param catalogId
	 * 所属考试类型ID。
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	/**
	 * 获取所属考试类型名称。
	 * @return 所属考试类型名称。
	 */
	public String getCatalogName() {
		return catalogName;
	}
	/**
	 * 设置所属考试类型名称。
	 * @param catalogName
	 * 所属考试类型名称。
	 */
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
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
	 * 所属考试ID。
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
	 * 所属考试名称。
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}
	/**
	 * 获取所属科目。
	 * @return subjectId 所属科目。
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置所属科目ID。
	 * @param subjectId
	 * 所属科目。
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
	 * 所属科目名称。
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	/**
	 * 获取所属班级类型ID。
	 * @return classTypeId 所属班级类型。
	 */
	public String getClassTypeId() {
		return classTypeId;
	}
	/**
	 * 设置所属班级类型ID。
	 * @param classTypeId
	 * 所属班级类型。
	 */
	public void setClassTypeId(String classTypeId) {
		this.classTypeId = classTypeId;
	}
	/**
	 * 获取所属班级类型名称。
	 * @return 所属班级类型名称。
	 */
	public String getClassTypeName() {
		return classTypeName;
	}
	/**
	 * 设置所属班级类型名称。
	 * @param classTypeName
	 * 所属班级类型名称。
	 */
	public void setClassTypeName(String classTypeName) {
		this.classTypeName = classTypeName;
	}
	/**
	 * 获取套餐原价。
	 * @return price
	 * 套餐原价。
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置套餐原价。
	 * @param price
	 * 套餐原价。
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取套餐批发价。
	 * @return wholesalePrice
	 * 套餐批发价。
	 */
	public BigDecimal getWholesalePrice() {
		return wholesalePrice;
	}
	/**
	 * 设置套餐批发价。
	 * @param wholesalePrice
	 * 套餐批发价。
	 */
	public void setWholesalePrice(BigDecimal wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}
	/**
	 * 获取套餐优惠价格。
	 * @return discountPrice
	 * 套餐优惠价格。
	 */
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}
	/**
	 * 设置套餐优惠价格。
	 * @param discountPrice
	 * 套餐优惠价格。
	 */
	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}
	/**
	 * 获取备注。
	 * @return remarks 备注。
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 设置备注。
	 * @param remarks
	 * 备注。
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取图片地址。
	 * @return imgUrl 图片地址。
	 */
	public String getImgUrl() {
		return imgUrl;
	}
	/**
	 * 设置图片地址。
	 * @param imgUrl
	 * 图片地址。
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 * 获取试听地址。
	 * @return videoUrl 试听地址。
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
	 * @return userYear  使用年份。
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
	 * @return totalHours 班级课时。
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
	 * @return handoutMode 讲义模式。
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
	 * 获取讲义模式名称。
	 * @return 讲义模式名称。
	 */
	public String getHandoutModeName() {
		return handoutModeName;
	}
	/**
	 * 设置讲义模式名称。
	 * @param handoutModeName
	 * 讲义模式名称。
	 */
	public void setHandoutModeName(String handoutModeName) {
		this.handoutModeName = handoutModeName;
	}
	/**
	 * 获取是否免费试听。
	 * @return videoMode 是否免费试听。
	 */
	public Integer getVideoMode() {
		return videoMode;
	}
	/**
	 * 设置是否免费试听。
	 * @param videoMode
	 * 是否免费试听。
	 */
	public void setVideoMode(Integer videoMode) {
		this.videoMode = videoMode;
	}
	/**
	 * 获取是否免费试听名称。
	 * @return 是否免费试听名称。
	 */
	public String getVideoModeName() {
		return videoModeName;
	}
	/**
	 * 设置是否免费试听名称。
	 * @param videoModeName
	 * 是否免费试听名称。
	 */
	public void setVideoModeName(String videoModeName) {
		this.videoModeName = videoModeName;
	}
	/**
	 * 获取班级状态。
	 * @return status 班级状态。
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
	 * 获取班级状态名称。
	 * @return 班级状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 *  设置班级状态名称。
	 * @param statusName
	 * 班级状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取开班时间。
	 * @return startTime 开班时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置开始时间。
	 * @param startTime
	 * 开始时间。
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取班级结束时间。
	 * @return endTime 班级结束时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置班级结束时间。
	 * @param endTime
	 * 班级结束时间。
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取班级创建时间。
	 * @return createTime 班级创建时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置班级创建时间。
	 * @param createTime
	 * 班级创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取最后修改时间。
	 * @return lastTime 最后修改时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
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
	 * 获取当前用户ID。
	 * @return 当前用户ID。
	 */
	public String getCurrentUserId() {
		return currentUserId;
	}
	/**
	 * 设置当前用户ID。
	 * @param currentUserId
	 * 当前用户ID。
	 */
	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}
}