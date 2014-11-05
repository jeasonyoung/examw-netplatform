package com.examw.netplatform.model.admin.papers;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 试卷信息
 * @author fengwei.
 * @since 2014年5月4日 上午10:41:45.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PaperInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,name,source,areaCode,createUsername,lastUsername;
	private Integer type,time,score,level,status;
	private Date createTime,lastTime;
	private BigDecimal price;
	private String agencyId,agencyName;
	private String catalogId,examId;
	private String subjectId,subjectName;
	private String statusName,typeName;
	/**
	 * 获取试卷ID。
	 * @return id
	 * 试卷ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置试卷ID。
	 * @param id
	 * 试卷ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取试卷名称。
	 * @return name
	 * 试卷名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置试卷名称。
	 * @param name
	 * 试卷名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取试卷来源。
	 * @return source
	 * 试卷来源。
	 */
	public String getSource() {
		return source;
	}
	/**
	 * 设置试卷来源。
	 * @param source
	 * 试卷来源。
	 */
	public void setSource(String source) {
		this.source = source;
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
	 * 获取创建用户。
	 * @return createUsername
	 * 创建用户。
	 */
	public String getCreateUsername() {
		return createUsername;
	}
	/**
	 * 设置创建用户。
	 * @param createUsername
	 * 创建用户。
	 */
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}
	/**
	 * 获取最后修改时间。
	 * @return lastUsername
	 * 最后修改时间。
	 */
	public String getLastUsername() {
		return lastUsername;
	}
	/**
	 * 设置最后修改时间。
	 * @param lastUsername
	 * 最后修改时间。
	 */
	public void setLastUsername(String lastUsername) {
		this.lastUsername = lastUsername;
	}
	/**
	 * 获取试卷类型。
	 * @return type
	 * 试卷类型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置试卷类型。
	 * @param type
	 * 试卷类型。
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取试卷类型名称。
	 * @return typeName
	 * 试卷类型名称。
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置试卷类型名称。
	 * @param typeName
	 * 试卷类型名称。
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取试卷考试时长(分钟)。
	 * @return time
	 * 试卷考试时长(分钟)。
	 */
	public Integer getTime() {
		return time;
	}
	/**
	 * 设置试卷考试时长(分钟)。
	 * @param time
	 * 试卷考试时长(分钟)。
	 */
	public void setTime(Integer time) {
		this.time = time;
	}
	/**
	 * 获取试卷总分。
	 * @return score
	 * 试卷总分。
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * 设置试卷总分。
	 * @param score
	 * 试卷总分。
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	/**
	 * 获取试卷难度。
	 * @return level
	 * 试卷难度。
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置试卷难度。
	 * @param level
	 * 试卷难度。
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * 获取试卷状态。
	 * @return status
	 * 试卷状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置试卷状态。
	 * @param status
	 * 试卷状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取试卷状态名称。
	 * @return statusName
	 * 试卷状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置试卷状态名称。
	 * @param statusName
	 * 试卷状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取试卷创建时间。
	 * @return createTime
	 * 试卷创建时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置试卷创建时间。
	 * @param createTime
	 * 试卷创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取试卷最后修改时间。
	 * @return lastTime
	 * 试卷最后修改时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置试卷最后修改时间。
	 * @param lastTime
	 * 试卷最后修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * 获取试卷价格。
	 * @return price
	 * 试卷价格。
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置试卷价格。
	 * @param price
	 * 试卷价格。
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取试卷所属机构ID。
	 * @return agencyId
	 * 试卷所属机构ID。
	 */
	public String getAgencyId() {
		return agencyId;
	}
	/**
	 * 设置试卷所属机构ID。
	 * @param agencyId
	 * 试卷所属机构ID。
	 */
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	/**
	 * 获取试卷所属机构名称。
	 * @return
	 * 试卷所属机构名称。
	 */
	public String getAgencyName() {
		return agencyName;
	}
	/**
	 * 设置试卷所属机构名称。
	 * @param agencyName
	 * 试卷所属机构名称。
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	/**
	 * 获取试卷所属考试类别ID。
	 * @return
	 * 试卷所属考试类别ID。
	 */
	public String getCatalogId() {
		return catalogId;
	}
	/**
	 * 设置试卷所属考试类别ID。
	 * @param catalogId
	 * 试卷所属考试类别ID。
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	/**
	 * 获取试卷所属考试ID。
	 * @return
	 * 试卷所属考试ID。
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置试卷所属考试ID。
	 * @param examId
	 * 试卷所属考试ID。
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取试卷所属考试科目ID。
	 * @return subjectId
	 * 试卷所属考试科目ID。
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置试卷所属考试科目ID。
	 * @param subjectId
	 * 试卷所属考试科目ID。
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取试卷所属考试科目名称。
	 * @return subjectName
	 * 试卷所属考试科目名称。
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/**
	 * 设置试卷所属考试科目名称。
	 * @param subjectName
	 * 试卷所属考试科目名称。
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
}