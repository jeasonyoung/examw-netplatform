package com.examw.netplatform.dao;

import org.apache.ibatis.annotations.Param;

import com.examw.netplatform.domain.Attachment;


/**
 * 附件数据接口。
 * 
 * @author yangyong
 * @since 2014年10月22日
 */
public interface AttachmentMapper {
	/**
	 * 加载附件。
	 * @param id
	 * 附件ID。
	 * @return
	 */
	Attachment getAttachment(String id);
	/**
	 * 附件是否存在。
	 * @param id
	 * @return
	 */
	boolean hasAttachment(String id);
	/**
	 * 附件存储是否存在。
	 * @param code
	 * 附件校验码。
	 * @return 
	 */
	boolean hasAttachmentStorage(String code);
	/**
	 * 插入附件。
	 * @param attachment
	 */
	void insertAttachment(Attachment attachment);
	/**
	 * 更新附件。
	 * @param attachment
	 */
	void updateAttachment(Attachment attachment);
	/**
	 * 插入附件存储。
	 * @param code
	 * 	附件校验码。
	 * @param size
	 *  附件尺寸。
	 * @param content
	 * 附件内容。
	 */
	void insertAttachmentStorage(@Param("code")String code,@Param("size")long size, @Param("content")byte[] content);
	/**
	 * 删除附件。
	 * @param id
	 * 附件ID。
	 */
	void deleteAttachment(String id);
	/**
	 * 删除附件存储。
	 * @param code
	 * 附件校验码。
	 */
	void deleteAttachmentStorage(String code);
}