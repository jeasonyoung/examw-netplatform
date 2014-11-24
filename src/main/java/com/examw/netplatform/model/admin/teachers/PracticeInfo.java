package com.examw.netplatform.model.admin.teachers;

import java.math.BigDecimal;
import java.util.Date;

import com.examw.model.Paging;
/**
 * 随堂练习信息。
 * 
 * @author yangyong
 * @since 2014年11月22日
 */
public class PracticeInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,name,description,userId,userName,typeName,statusName,
		lessonId,lessonName,classId,className,agencyId;
	private Integer year,type,time,status;
	private BigDecimal score;
	private Date createTime,lastTime;
	/**
	 * 获取练习ID。
	 * @return 练习ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置练习ID。
	 * @param id 
	 *	  练习ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取练习名称。
	 * @return 练习名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置练习名称。
	 * @param name 
	 *	  练习名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取描述。
	 * @return 描述。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置描述。
	 * @param description 
	 *	  描述。
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取当前用户ID。
	 * @return 当前用户ID。
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置当前用户ID。
	 * @param userId 
	 *	  当前用户ID。
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取当前用户名称。
	 * @return 当前用户名称。
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置当前用户名称。
	 * @param userName 
	 *	  当前用户名称。
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取所属课时资源ID。
	 * @return 所属课时资源ID。
	 */
	public String getLessonId() {
		return lessonId;
	}
	/**
	 * 设置所属课时资源ID。
	 * @param lessonId 
	 *	  所属课时资源ID。
	 */
	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
	}
	/**
	 * 获取所属课时资源名称。
	 * @return 所属课时资源名称。
	 */
	public String getLessonName() {
		return lessonName;
	}
	/**
	 * 设置所属课时资源名称。
	 * @param lessonName 
	 *	  所属课时资源名称。
	 */
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}
	/**
	 * 获取所属班级ID。
	 * @return 所属班级ID。
	 */
	public String getClassId() {
		return classId;
	}
	/**
	 * 设置所属班级ID。
	 * @param classId 
	 *	  所属班级ID。
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}
	/**
	 * 获取所属班级名称。
	 * @return 所属班级名称。
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * 设置所属班级名称。
	 * @param className 
	 *	  所属班级名称。
	 */
	public void setClassName(String className) {
		this.className = className;
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
	 * 获取使用年份。
	 * @return 使用年份。
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * 设置使用年份。
	 * @param year 
	 *	  使用年份。
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	/**
	 * 获取类型。
	 * @return 类型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置类型。
	 * @param type 
	 *	  类型。
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取类型名称。
	 * @return 类型名称。
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置类型名称。
	 * @param typeName 
	 *	  类型名称。
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取时长。
	 * @return 时长。
	 */
	public Integer getTime() {
		return time;
	}
	/**
	 * 设置时长。
	 * @param time 
	 *	  时长。
	 */
	public void setTime(Integer time) {
		this.time = time;
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
	 * 获取总分。
	 * @return 总分。
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置总分。
	 * @param score 
	 *	  总分。
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
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
	 *	  最后修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
}