package com.examw.netplatform.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * 附件数据。
 * 
 * @author yangyong
 * @since 2014年10月22日
 */
public class Attachment implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,code,extension,contentType;
	private Long size;
	private AttachmentStorage storage;
	private Date createTime;
	/**
	 * 构造函数。
	 */
	public Attachment(){
		this.setId(UUID.randomUUID().toString());
		this.setCreateTime(new Date());
	}
	/**
	 * 获取附件ID。
	 * @return 附件ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置附件ID。
	 * @param id 
	 *	  附件ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取附件摘要码。
	 * @return 附件摘要码。
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置附件摘要码。
	 * @param code 
	 *	  附件摘要码。
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取附件名称（包含后缀名）。
	 * @return 附件名称（包含后缀名）。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置附件名称（包含后缀名）。
	 * @param name 
	 *	  附件名称（包含后缀名）。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取扩展名。
	 * @return 扩展名。
	 */
	public String getExtension() {
		return extension;
	}
	/**
	 * 设置扩展名。
	 * @param extension 
	 *	  扩展名。
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}
	/**
	 * 获取内容类型。
	 * @return 内容类型。
	 */
	public String getContentType() {
		return contentType;
	}
	/**
	 * 设置内容类型。
	 * @param contentType 
	 *	  内容类型。
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	/**
	 * 获取附件大小。
	 * @return 附件大小。
	 */
	public Long getSize() {
		return size;
	}
	/**
	 * 设置附件大小。
	 * @param size 
	 *	  附件大小。
	 */
	public void setSize(Long size) {
		this.size = size;
	}
	/**
	 * 获取附件储存。
	 * @return 附件储存。
	 */
	public AttachmentStorage getStorage() {
		return storage;
	}
	/**
	 * 设置附件储存。
	 * @param storage 
	 *	  附件储存。
	 */
	public void setStorage(AttachmentStorage storage) {
		this.storage = storage;
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