package com.examw.netplatform.domain.admin.courses;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.domain.admin.settings.ClassType; 
import com.examw.netplatform.domain.admin.settings.Subject;

/**
 * 开班计划
 * @author fengwei.
 * @since 2014年5月20日 下午4:42:55.
 */
public class ClassPlan implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,remarks,imgUrl,videoUrl;
	private Integer useYear,totalHours,handoutMode,videoMode,status;
	private BigDecimal price,discountPrice,wholesalePrice;
	private Date startTime,endTime,createTime,lastTime;
	private Agency agency;
	private Subject subject;
	private ClassType classType;
	private Set<Lesson> lessons;
	/**
	 * 状态－启用。
	 */
	public static final Integer STATUS_ENABLE = 1;
	/**
	 * 状态－禁用。
	 */
	public static final Integer STATUS_DISABLE = 0;
	/**
	 * 讲义模式－下载。
	 */
	public static final Integer HANDOUT_MODE_DOWNLOAD = 1;
	/**
	 * 讲义模式－在线。
	 */
	public static final Integer HANDOUT_MODE_ONLINE = 2;
	/**
	 * 视频模式－非免费。
	 */
	public static final Integer VIDEO_NOTFREE = 0;
	/**
	 * 视频模式－免费。
	 */
	public static final Integer VIDEO_FREE = 1;
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
	 * 获取所属机构。
	 * @return agency 所属机构。
	 */
	public Agency getAgency() {
		return agency;
	}
	/**
	 * 设置所属机构。
	 * @param agency
	 * 所属机构。
	 */
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	/**
	 * 获取所属科目。
	 * @return subject 所属科目。
	 */
	public Subject getSubject() {
		return subject;
	}
	/**
	 * 设置所属科目。
	 * @param subject
	 * 所属科目。
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	/**
	 * 获取所属班级类型。
	 * @return classType 所属班级类型。
	 */
	public ClassType getClassType() {
		return classType;
	}
	/**
	 * 设置所属班级类型。
	 * @param classType
	 * 所属班级类型。
	 */
	public void setClassType(ClassType classType) {
		this.classType = classType;
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
	 * 获取开班时间。
	 * @return startTime 开班时间。
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置开始时间。
	 * @param startTime
	 * 开始时间。
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取班级结束时间。
	 * @return endTime 班级结束时间。
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置班级结束时间。
	 * @param endTime
	 * 班级结束时间。
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取班级创建时间。
	 * @return createTime 班级创建时间。
	 */
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
	 * 获取课时资源集合。
	 * @return 课时资源集合。
	 */
	public Set<Lesson> getLessons() {
		return lessons;
	}
	/**
	 * 设置课时资源集合。
	 * @param lessons
	 * 课时资源集合。
	 */
	public void setLessons(Set<Lesson> lessons) {
		this.lessons = lessons;
	}
}