package com.examw.netplatform.domain.admin.courses;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.examw.netplatform.domain.admin.agency.Agency;
import com.examw.netplatform.domain.admin.settings.Exam;

/**
 * 课程套餐
 * @author fengwei.
 * @since 2014年5月21日 下午2:19:55.
 */
public class Package implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,description,imgUrl,videoUrl;
	private Integer status;
	private BigDecimal price,discountPrice,wholesalePrice;
	private Date startTime,endTime,expireTime,createTime,lastTime;
	private Exam exam;
	private Agency agency;
	private Set<ClassPlan> classes;
	/**
	 * 状态－启用。
	 */
	public static final Integer STATUS_ENABLED = 1;
	/**
	 * 状态－停用。
	 */
	public static final Integer STATUS_DISABLE = 0;
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
	 * 套餐ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取套餐名称。
	 * @return name
	 * 套餐名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置套餐名称。
	 * @param name
	 * 套餐名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取所属考试。
	 * @return exam
	 * 所属考试。
	 */
	public Exam getExam() {
		return exam;
	}
	/**
	 * 设置所属考试。
	 * @param exam
	 * 所属考试。
	 */
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	/**
	 * 获取所属机构。
	 * @return agency
	 * 所属机构。
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
	 * 获取套餐描述。
	 * @return description
	 * 套餐描述。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置套餐描述。
	 * @param description
	 * 套餐描述。
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取套餐图片地址。
	 * @return imgUrl
	 * 套餐图片地址。
	 */
	public String getImgUrl() {
		return imgUrl;
	}
	/**
	 * 设置套餐图片地址。
	 * @param imgUrl
	 * 套餐图片地址。
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 * 获取套餐试听地址。
	 * @return videoUrl
	 * 套餐试听地址。
	 */
	public String getVideoUrl() {
		return videoUrl;
	}
	/**
	 * 设置套餐试听地址。
	 * @param videoUrl
	 * 套餐试听地址。
	 */
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	/**
	 * 获取套餐状态。
	 * @return status
	 * 套餐状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置套餐状态。
	 * @param status
	 * 套餐状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	 * 获取套餐招生开始时间。
	 * @return 套餐招生开始时间。
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置套餐招生开始时间。
	 * @param startTime
	 * 套餐招生开始时间。
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取套餐招生结束时间。
	 * @return 套餐招生结束时间。
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置套餐招生结束时间。
	 * @param endTime
	 * 套餐招生结束时间。
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取套餐到期时间。
	 * @return 套餐到期时间。
	 */
	public Date getExpireTime() {
		return expireTime;
	}
	/**
	 * 设置套餐到期时间。
	 * @param expireTime
	 * 套餐到期时间。
	 */
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	/**
	 * 获取套餐创建时间。
	 * @return 套餐创建时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置套餐创建时间。
	 * @param createTime
	 * 套餐创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取套餐最后修改时间。
	 * @return 套餐最后修改时间。
	 */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置套餐最后修改时间。
	 * @param lastTime
	 * 套餐最后修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * 获取套餐下班级集合。
	 * @return 套餐下班级集合。
	 */
	public Set<ClassPlan> getClasses() {
		return classes;
	}
	/**
	 * 设置套餐下班级集合。
	 * @param classes
	 * 套餐下班级集合。
	 */
	public void setClasses(Set<ClassPlan> classes) {
		this.classes = classes;
	}
}