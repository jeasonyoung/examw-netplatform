package com.examw.netplatform.domain.admin.papers;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.examw.netplatform.domain.admin.agency.Agency;
import com.examw.netplatform.domain.admin.settings.Subject;
import com.examw.netplatform.domain.admin.settings.Chapter;

/**
 * 考试题目
 * @author fengwei.
 * @since 2014年5月3日 下午4:00:06.
 */
public class Item implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id,checkCode,content,answer,analysis,createUsername;
	private Integer type,level,status,orderNo;
	private Date createTime;
	
	private Item parent;
	private Set<Item> children;
	
	private Agency agency;
	private Subject subject;
	private Chapter chapter;
	/**
	 * 启用状态。
	 */
	public static final int STATUS_ENABLED = 1;
	/**
	 * 停用状态。
	 */
	public static final int STATUS_DISABLE = 0;
	/**
	 * 类型－单选。
	 */
	public static final int TYPE_SINGLE = 1;
	/**
	 * 类型－多选。
	 */
	public static final int TYPE_MULTY = 2;
	/**
	 * 类型－不定向选。
	 */
	public static final int 	TYPE_UNCERTAIN =3;
	/**
	 * 类型－判断题。
	 */
	public static final int TYPE_JUDGE = 4;
	/**
	 * 类型－问答题。
	 */
	public static final int TYPE_QANDA = 5;
	/**
	 * 类型－共享题干题。
	 */
	public static final int TYPE_SHARE_TITLE = 6;
	/**
	 * 类型－共享答案题。
	 */
	public static final int TYPE_SHARE_ANSWER = 7;
	/**
	 * 判断题答案[Type=TYPE_JUDGE] 1-正确。
	 */
	public static final int ANSWER_JUDGE_RIGTH = 1;
	/**
	 * 判断题答案[Type=TYPE_JUDGE] 0-错误。
	 */
	public static final int ANSWER_JUDGE_WRONG = 0;
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
	 * 获取 md5码[检验重复]
	 * @return checkCode
	 * md5码
	 */
	public String getCheckCode() {
		return checkCode;
	}
	/**
	 * 设置 md5码
	 * @param checkCode
	 * md5码
	 */
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
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
	 * 获取 题型
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
	 * 获取题目难度
	 * @return level
	 * 题目难度
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置 题目难度
	 * @param level
	 * 题目难度
	 */
	public void setLevel(Integer level) {
		this.level = level;
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
	 * 获取 所属章节
	 * @return chapter
	 * 所属章节
	 */
	public Chapter getChapter() {
		return chapter;
	}
	/**
	 * 设置 所属章节
	 * @param chapter
	 * 所属章节
	 */
	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}
	/**
	 * 获取 上级题目
	 * @return parent
	 * 上级题目
	 */
	public Item getParent() {
		return parent;
	}
	/**
	 * 设置 上级题目
	 * @param parent
	 * 上级题目
	 */
	public void setParent(Item parent) {
		this.parent = parent;
	}
	/**
	 * 获取 下属题目
	 * @return children
	 * 下属题目
	 */
	public Set<Item> getChildren() {
		return children;
	}
	/**
	 * 设置 下属题目
	 * @param children
	 * 下属题目
	 */
	public void setChildren(Set<Item> children) {
		this.children = children;
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
	 * 获取 机构
	 * @return agency
	 * 
	 */
	public Agency getAgency() {
		return agency;
	}
	/**
	 * 设置 机构
	 * @param agency
	 * 
	 */
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	/**
	 * 获取 科目
	 * @return subject
	 * 
	 */
	public Subject getSubject() {
		return subject;
	}
	/**
	 * 设置 科目
	 * @param subject
	 * 
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	/**
	 * 获取 题目顺序
	 * @return orderNo
	 * 
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置 题目顺序
	 * @param orderNo
	 * 
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
//	
//	//2014.05.19
//	private String contentForPreview; //预览的内容
//	/**
//	 * 获取 预览的题干
//	 * @return contentForPreview
//	 * 
//	 */
//	public String getContentForPreview() {
//		StringBuffer buf = new StringBuffer();
//		switch(type){
//		case Item.ITEM_SINGLE:
//		case Item.ITEM_MULTY:
//		case Item.ITEM_UNCERTAIN:
//			String[] arr = content.split("[A-Z][.]");
//			buf.append(arr[0]);
//			for(int i=1;i<arr.length;i++)
//			{
//				buf.append("<br/>").append((char)(i+64)).append("、").append(arr[i]);
//			}
//			return buf.toString();
//		case Item.ITEM_JUDGE:
//			buf.append(content).append("<br/>").append("<input value='1' type='radio'>是<br/><input value='0' type='radio'>否");
//		}
//		return contentForPreview;
//	}
//	/**
//	 * 设置 预览的内容
//	 * @param contentForPreview
//	 * 
//	 */
//	public void setContentForPreview(String contentForPreview) {
//		this.contentForPreview = contentForPreview;
//	}	
}