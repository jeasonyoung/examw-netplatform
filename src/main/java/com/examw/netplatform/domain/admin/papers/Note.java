package com.examw.netplatform.domain.admin.papers;

import java.io.Serializable;
import java.util.Date;

import com.examw.netplatform.domain.admin.security.User;
/**
 * 试卷笔记
 * @author fengwei.
 * @since 2014年5月13日 上午11:33:53.
 */
public class Note  implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,note;
	private User user;
	private Paper paper;
	private StructureItem item;
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
	 * 获取试卷笔记内容。
	 * @return note
	 * 试卷笔记内容。
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置试卷笔记内容。
	 * @param note
	 * 试卷笔记内容。
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取所属用户。
	 * @return user
	 * 所属用户。
	 */
	public User getUser() {
		return user;
	}
	/**
	 * 设置所属用户。
	 * @param user
	 * 所属用户。
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * 获取所属试卷。
	 * @return paper
	 * 所属试卷。
	 */
	public Paper getPaper() {
		return paper;
	}
	/**
	 * 设置所属试卷。
	 * @param paper
	 * 所属试卷。
	 */
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	/**
	 * 获取所属试题。
	 * @return item
	 * 所属试题。
	 */
	public StructureItem getItem() {
		return item;
	}
	/**
	 * 设置所属试题。
	 * @param item
	 * 所属试题。
	 */
	public void setItem(StructureItem item) {
		this.item = item;
	}
	/**
	 * 获取创建时间。
	 * @return createTime
	 * 创建时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime
	 * 创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}