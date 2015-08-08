package com.examw.netplatform.domain.admin.courses;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.domain.admin.settings.Area;
import com.examw.netplatform.domain.admin.settings.ClassType; 
import com.examw.netplatform.domain.admin.settings.Subject;
import com.examw.netplatform.domain.admin.students.Order;

/**
 * 开班计划
 * @author fengwei.
 * @since 2014年5月20日 下午4:42:55.
 */
public class ClassPlan implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,description,imgUrl,videoUrl;
	private Integer useYear,totalHours,handoutMode,videoMode,status,orderNo;
	private BigDecimal price,discountPrice,wholesalePrice;
	private Date startTime,endTime,createTime,lastTime;
	private ClassType classType;
	private Agency agency;
	private Subject subject;
	private Area area;
	private Set<Lesson> lessons;
	private Set<Package> packages;
	private Set<AgencyUser> users;
	private Set<Order> orders;
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
	 * 获取所属类型。
	 * @return 所属类型。
	 */
	public ClassType getClassType() {
		return classType;
	}
	/**
	 * 设置所属类型。
	 * @param classType
	 * 所属类型。
	 */
	public void setClassType(ClassType classType) {
		this.classType = classType;
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
	 * 获取所属机构。
	 * @return 所属机构。
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
	 * @return 所属科目。
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
	 * 获取所属地区。
	 * @return 所属地区。
	 */
	public Area getArea() {
		return area;
	}
	/**
	 * 设置所属地区。
	 * @param area 
	 *	  所属地区。
	 */
	public void setArea(Area area) {
		this.area = area;
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
	 * 获取关联课时资源集合。
	 * @return 关联课时资源集合。
	 */
	public Set<Lesson> getLessons() {
		return lessons;
	}
	/**
	 * 设置关联课时资源集合。
	 * @param lessons
	 * 关联课时资源集合。
	 */
	public void setLessons(Set<Lesson> lessons) {
		this.lessons = lessons;
	}
	/**
	 * 获取关联套餐集合。
	 * @return 关联套餐集合。
	 */
	public Set<Package> getPackages() {
		return packages;
	}
	/**
	 * 设置关联套餐集合。
	 * @param packages 
	 *	  关联套餐集合。
	 */
	public void setPackages(Set<Package> packages) {
		this.packages = packages;
	}
	/**
	 * 获取关联机构教师用户集合。
	 * @return 关联机构教师用户集合。
	 */
	public Set<AgencyUser> getUsers() {
		return users;
	}
	/**
	 * 设置关联机构教师用户集合。
	 * @param users 
	 *	  关联机构教师用户集合。
	 */
	public void setUsers(Set<AgencyUser> users) {
		this.users = users;
	}
	/**
	 * 获取关联的订单集合。
	 * @return 关联的订单集合。
	 */
	public Set<Order> getOrders() {
		return orders;
	}
	/**
	 * 设置关联的订单集合。
	 * @param orders 
	 *	  关联的订单集合。
	 */
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
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
	 * 判断是否过期
	 * 2015.01.23
	 * @return
	 */
	public boolean isOverdue()
	{
		//没有结束时间
		if(endTime == null) return false;
		if(endTime.compareTo(new Date()) > 0)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 判断是否开班
	 * 2015.01.23
	 * @return
	 */
	public boolean isClassOpen()
	{
		//没有开班时间
		if(startTime == null) return true;
		if(startTime.compareTo(new Date()) > 0)
		{
			return false;
		}
		return true;
	}
}