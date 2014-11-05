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
 * 套餐信息
 * @author fengwei.
 * @since 2014年5月21日 下午2:38:05.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class PackageInfo extends Paging implements IUser {
	private static final long serialVersionUID = 1L;
	private String id,name,agencyId,agencyName,catalogId,examId,examName,statusName,description,imgUrl,videoUrl,currentUserId;
	private Integer status;
	private BigDecimal price,discountPrice,wholesalePrice;
	private Date startTime,endTime,expireTime,createTime,lastTime;
	private String[] classes;
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
	 * 获取所属考试类别ID。
	 * @return 所属考试类别ID。
	 */
	public String getCatalogId() {
		return catalogId;
	}
	/**
	 * 设置所属考试类别ID。
	 * @param catalogId
	 * 所属考试类别ID。
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
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
	 * 获取套餐状态名称。
	 * @return 套餐状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置套餐状态名称。
	 * @param statusName
	 * 套餐状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
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
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置套餐招生开始时间。
	 * @param startTime
	 * 套餐招生开始时间。
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取套餐招生结束时间。
	 * @return 套餐招生结束时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置套餐招生结束时间。
	 * @param endTime
	 * 套餐招生结束时间。
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取套餐到期时间。
	 * @return 套餐到期时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getExpireTime() {
		return expireTime;
	}
	/**
	 * 设置套餐到期时间。
	 * @param expireTime
	 * 套餐到期时间。
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	/**
	 * 获取套餐创建时间。
	 * @return 套餐创建时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
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
	@JsonSerialize(using = CustomDateSerializer.class)
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
	public String[] getClasses() {
		return classes;
	}
	/**
	 * 设置套餐下班级集合。
	 * @param classes
	 * 套餐下班级集合。
	 */
	public void setClasses(String[] classes) {
		this.classes = classes;
	}
}