package com.examw.netplatform.domain.admin.teachers;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.examw.netplatform.domain.admin.courses.Lesson;
/**
 * 随堂练习。
 * 
 * @author yangyong
 * @since 2014年11月21日
 */
public class Practice implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,description,userId,userName;
	private Integer year,time,status;
	private BigDecimal score;
	private Lesson lesson;
	private Date createTime,lastTime;
	private Set<Structure> structures;
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
	 * 获取用户ID。
	 * @return 用户ID。
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置用户ID。
	 * @param userId 
	 *	  用户ID。
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取用户名称。
	 * @return 用户名称。
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置用户名称。
	 * @param userName 
	 *	  用户名称。
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * 获取分数。
	 * @return 分数。
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置分数。
	 * @param score 
	 *	  分数。
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/**
	 * 获取课时资源。
	 * @return 课时资源。
	 */
	public Lesson getLesson() {
		return lesson;
	}
	/**
	 * 设置课时资源。
	 * @param lesson 
	 *	  课时资源。
	 */
	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
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
	/**
	 * 获取练习题结构集合。
	 * @return 练习题结构集合。
	 */
	public Set<Structure> getStructures() {
		return structures;
	}
	/**
	 * 设置练习题结构集合。
	 * @param structures 
	 *	  练习题结构集合。
	 */
	public void setStructures(Set<Structure> structures) {
		this.structures = structures;
	}
}