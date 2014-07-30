package com.examw.netplatform.model.admin.papers;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.netplatform.support.CustomDateSerializer;
/**
 * 试卷笔记信息
 * @author fengwei.
 * @since 2014年5月13日 上午11:38:42.
 */
public class NoteInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,paperId,paperName,userId,username,itemId,itemSerial,note;
	private Date createTime;
	/**
	 * 获取试卷笔记ID。
	 * @return id
	 * 试卷笔记ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置试卷笔记ID。
	 * @param id
	 * 试卷笔记ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取笔记内容。
	 * @return note
	 * 笔记内容。
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置笔记内容。
	 * @param note
	 * 笔记内容。
	 */
	public void setNote(String note) {
		this.note = note;
	}	
	/**
	 * 获取所属试卷ID。
	 * @return paperId
	 * 所属试卷ID。
	 * 
	 */
	public String getPaperId() {
		return paperId;
	}
	/**
	 * 设置所属试卷ID。
	 * @param paperId
	 * 所属试卷ID。
	 * 
	 */
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}
	/**
	 * 获取所属试卷名称。
	 * @return paperName
	 * 所属试卷名称。
	 */
	public String getPaperName() {
		return paperName;
	}
	/**
	 * 设置所属试卷名称。
	 * @param paperName
	 * 所属试卷名称。
	 */
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	/**
	 * 获取所属试题ID。
	 * @return itemId
	 * 所属试题ID。
	 */
	public String getItemId() {
		return itemId;
	}
	/**
	 * 设置所属试题ID。
	 * @param itemId
	 * 所属试题ID。
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	/**
	 * 获取试题题号。
	 * @return
	 * 试题题号。
	 */
	public String getItemSerial() {
		return itemSerial;
	}
	/**
	 * 设置试题题号。
	 * @param itemSerial
	 * 试题题号。
	 */
	public void setItemSerial(String itemSerial) {
		this.itemSerial = itemSerial;
	}
	/**
	 * 获取所属用户ID。
	 * @return userId
	 * 所属用户ID。
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置所属用户ID。
	 * @param userId
	 * 所属用户ID。
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取所属用户名。
	 * @return username
	 * 所属用户名。
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置所属用户名。
	 * @param username
	 * 所属用户名。
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取笔记创建时间。
	 * @return createTime
	 * 笔记创建时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置笔记创建时间。
	 * @param createTime
	 * 笔记创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}