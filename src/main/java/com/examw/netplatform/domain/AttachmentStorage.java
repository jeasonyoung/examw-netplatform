package com.examw.netplatform.domain;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.Set;

/**
 * 附件数据存储。
 * 
 * @author yangyong
 * @since 2014年10月22日
 */
public class AttachmentStorage implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	private String id;
	private Long size;
	private Date createTime;
	private Blob content;
	private Set<Attachment> attachments;
	/**
	 * 构造函数。
	 */
	public AttachmentStorage(){
		this.setCreateTime(new Date());
	}
	/**
	 * 获取存储ID。
	 * @return 存储ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置存储ID。
	 * @param id 
	 *	 存储ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取附件大小（K）。
	 * @return 附件大小（K）。
	 */
	public Long getSize() {
		return size;
	}
	/**
	 * 设置附件大小（K）。
	 * @param size 
	 *	  附件大小（K）。
	 */
	public void setSize(Long size) {
		this.size = size;
	}
	/**
	 * 获取二进制内容。
	 * @return 二进制内容。
	 */
	public Blob getContent() {
		return content;
	}
	/**
	 * 设置二进制内容。
	 * @param content 
	 *	  二进制内容。
	 */
	public void setContent(Blob content) {
		this.content = content;
	}
	/**
	 * 获取所属附件集合。
	 * @return 所属附件集合。
	 */
	public Set<Attachment> getAttachments() {
		return attachments;
	}
	/**
	 * 设置所属附件集合。
	 * @param attachments 
	 *	  所属附件集合。
	 */
	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}
	/**
	 * 获取上传时间。
	 * @return 上传时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置上传时间。
	 * @param createTime 
	 *	  上传时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}