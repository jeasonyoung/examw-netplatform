package com.examw.netplatform.dao;

import com.examw.netplatform.domain.Attachment;
import com.examw.netplatform.domain.AttachmentStorage;

/**
 * 附件存储数据接口。
 * 
 * @author yangyong
 * @since 2014年10月22日
 */
public interface IAttachmentStorageDao extends IBaseDao<AttachmentStorage> {
	/**
	 * 附件数据存储。
	 * @param attachment
	 * 附件信息。
	 * @param data
	 * 附件数据。
	 * @return
	 * @throws Exception
	 */
	AttachmentStorage updateAttachmentStorage(Attachment attachment,byte[] data) throws Exception;
}