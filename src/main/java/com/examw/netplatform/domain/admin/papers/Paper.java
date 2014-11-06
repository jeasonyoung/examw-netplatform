package com.examw.netplatform.domain.admin.papers;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.domain.admin.settings.Subject;

/**
 * 试卷
 * @author fengwei.
 * @since 2014年5月3日 上午10:49:40.
 * 
 * 调整字段，添加地区码。
 * @author yangyong.
 * @since 2014-05-27.
 */
public class Paper implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id,name,source,areaCode,createUsername,lastUsername;
	private Integer type,time,score,level,status;
	private Date createTime,lastTime;
	private BigDecimal price;
	private Agency agency;
	private Subject subject;
	private Set<Structure> structures;
	/**
	 * 真题。
	 */
	public static final int TYPE_REAL = 1;
	/**
	 * 模拟题。
	 */
	public static final int TYPE_SIMULATE = 2;
	/**
	 * 预测题。
	 */
	public static final int TYPE_FORECAST = 3;
	/**
	 * 练习题。
	 */
	public static final int TYPE_PRACTICE =4;
	/**
	 * 课堂练习。
	 */
	public static final int TYPE_CLASS_PRACTICE = 5;
	/**
	 * 启用状态。
	 */
	public static final int STATUS_ENABLED = 1;
	/**
	 * 停用状态。
	 */
	public static final int STATUS_DISABLE = 0;
	/**
	 * 获取 试卷ID
	 * @return id
	 * 试卷ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 试卷ID
	 * @param id
	 * 试卷ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 试卷名称
	 * @return name
	 * 试卷名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 试卷名称
	 * @param name
	 * 试卷名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取 试卷来源
	 * @return source
	 * 试卷来源
	 */
	public String getSource() {
		return source;
	}
	/**
	 * 设置 试卷来源
	 * @param source
	 * 试卷来源
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * 获取 创建人
	 * @return createUsername
	 * 创建人
	 */
	public String getCreateUsername() {
		return createUsername;
	}
	/**
	 * 设置 创建人
	 * @param createUsername
	 * 创建人
	 */
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}
	/**
	 * 获取 最后编辑人
	 * @return lastUsername
	 * 上次编辑人
	 */
	public String getLastUsername() {
		return lastUsername;
	}
	/**
	 * 设置 最后编辑人
	 * @param lastUsername
	 * 上次编辑人
	 */
	public void setLastUsername(String lastUsername) {
		this.lastUsername = lastUsername;
	}
	/**
	 * 获取 试卷类型 【1-真题,2-模拟题,3-预测题,4-练习题,5-课堂练习】
	 * @return type
	 * 试卷类型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置 试卷类型
	 * @param type
	 * 试卷类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取 考试时间
	 * @return time
	 * 考试时间
	 */
	public Integer getTime() {
		return time;
	}
	/**
	 * 设置 考试时间
	 * @param time
	 * 考试时间
	 */
	public void setTime(Integer time) {
		this.time = time;
	}
	/**
	 * 获取 试卷总分
	 * @return score
	 * 试卷总分
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * 设置 试卷总分
	 * @param score
	 * 试卷总分
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	/**
	 * 获取所属地区码。
	 * @return 所属地区码。
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * 设置所属地区码。
	 * @param areaCode
	 * 所属地区码。
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * 获取 难度
	 * @return level
	 * 难度
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置 难度
	 * @param level
	 * 难度
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * 获取 状态【1-启用,2-停用】
	 * @return status
	 * 状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置 状态
	 * @param status
	 * 状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取 创建时间
	 * @return createTime
	 * 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置 创建时间
	 * @param createTime
	 * 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取 上次编辑时间
	 * @return lastTime
	 * 上次编辑时间
	 */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置 上次编辑时间
	 * @param lastTime
	 * 上次编辑时间
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * 获取 试卷价格
	 * @return price
	 * 试卷价格
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置 试卷价格
	 * @param price
	 * 试卷价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取 培训机构
	 * @return agency
	 * 培训机构
	 */
	public Agency getAgency() {
		return agency;
	}
	/**
	 * 设置 培训机构
	 * @param agency
	 * 培训机构
	 */
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	/**
	 * 获取 科目
	 * @return subject
	 * 科目
	 */
	public Subject getSubject() {
		return subject;
	}
	/**
	 * 设置 科目
	 * @param subject
	 * 科目
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	/**
	 * 获取 试卷大题集合
	 * @return structures
	 * 试卷大题集合
	 */
	public Set<Structure> getStructures() {
		return structures;
	}
	/**
	 * 设置 试卷大题集合
	 * @param structures
	 * 试卷大题集合
	 */
	public void setStructures(Set<Structure> structures) {
		this.structures = structures;
	}	
}