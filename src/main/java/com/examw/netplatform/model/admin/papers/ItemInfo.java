package com.examw.netplatform.model.admin.papers;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.netplatform.support.CustomDateSerializer;

/**
 * 试题信息
 * @author fengwei.
 * @since 2014年5月6日 上午10:03:58.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ItemInfo extends Paging implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,checkCode,content,answer,analysis,createUsername,typeName,statusName;
	private String subjectId,subjectName, chapterId,chapterName,agencyId,agencyName; 
	private Integer level,type,status,orderNo;
	private Date createTime;
	private Set<ItemInfo> children;
	/**
	 * 获取 试题ID
	 * @return id
	 * 试题ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 试题ID
	 * @param id
	 * 试题ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 试题内容
	 * @return content
	 * 试题内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置 试题内容
	 * @param content
	 * 试题内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取 答案
	 * @return answer
	 * 答案
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * 设置 答案
	 * @param answer
	 * 答案
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/**
	 * 获取 解析
	 * @return analysis
	 * 解析
	 */
	public String getAnalysis() {
		return analysis;
	}
	/**
	 * 设置 解析
	 * @param analysis
	 * 解析
	 */
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	/**
	 * 获取 校验码
	 * @return checkCode
	 * 校验码
	 */
	public String getCheckCode() {
		return checkCode;
	}
	/**
	 * 设置 校验码
	 * @param checkCode
	 * 校验码
	 */
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	/**
	 * 获取 创建人
	 * @return createUser
	 * 创建人
	 */
	public String getCreateUsername() {
		return createUsername;
	}
	/**
	 * 设置 创建人
	 * @param createUser
	 * 创建人
	 */
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}
	/**
	 * 获取 题型名称
	 * @return typeName
	 * 题型名称
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置 题型名称
	 * @param typeName
	 * 题型名称
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取 状态名称
	 * @return statusName
	 * 状态名称
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置 状态名称
	 * @param statusName
	 * 状态名称
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
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
	 * 获取题型
	 * @return type
	 * 题型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置 题型
	 * @param type
	 * 题型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取 状态
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
	@JsonSerialize(using = CustomDateSerializer.class)
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
	 * 获取 章节ID
	 * @return chapterId
	 * 
	 */
	public String getChapterId() {
		return chapterId;
	}
	/**
	 * 设置 章节ID
	 * @param chapterId
	 * 
	 */
	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}
	/**
	 * 获取 章节名称
	 * @return chapterName
	 * 
	 */
	public String getChapterName() {
		return chapterName;
	}
	/**
	 * 设置 章节名称
	 * @param chapterName
	 * 
	 */
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	/**
	 * 获取 机构ID
	 * @return agencyId
	 * 
	 */
	public String getAgencyId() {
		return agencyId;
	}
	/**
	 * 设置 机构ID
	 * @param agencyId
	 * 
	 */
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	/**
	 * 获取 机构名称
	 * @return agencyName
	 * 
	 */
	public String getAgencyName() {
		return agencyName;
	}
	/**
	 * 设置 机构名称
	 * @param agencyName
	 * 
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	/**
	 * 获取 科目ID
	 * @return subjectId
	 * 
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置 科目ID
	 * @param subjectId
	 * 
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取科目名称。
	 * @return 科目名称。
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/**
	 * 设置科目名称。
	 * @param subjectName
	 * 科目名称。
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	/**
	 * 获取 子试题的顺序
	 * @return orderNo
	 * 
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置 子试题的顺序
	 * @param orderNo
	 * 
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取子题目集合。
	 * @return 子题目集合。
	 */
	public Set<ItemInfo> getChildren() {
		return children;
	}
	/**
	 * 设置子题目集合。
	 * @param children
	 * 子题目集合。
	 */
	public void setChildren(Set<ItemInfo> children) {
		this.children = children;
	}
}