package com.examw.netplatform.dao.impl;

import org.apache.log4j.Logger;

import com.examw.netplatform.dao.IAttachmentStorageDao;
import com.examw.netplatform.domain.Attachment;
import com.examw.netplatform.domain.AttachmentStorage;

/**
 * 附件存储数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年10月22日
 */
public class AttachmentStorageDaoImpl extends BaseDaoImpl<AttachmentStorage> implements IAttachmentStorageDao {
	private static final Logger logger = Logger.getLogger(AttachmentStorageDaoImpl.class);
	/*
	 * 附件数据存储。
	 * @see com.examw.netplatform.dao.IAttachmentStorageDao#updateAttachmentStorage(com.examw.netplatform.domain.Attachment, byte[])
	 */
	@Override
	public AttachmentStorage updateAttachmentStorage(Attachment attachment,byte[] data) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("更新附件存储...");
		if(attachment == null) throw new Exception("附件对象 attachment 为空！");
		AttachmentStorage storage = this.load(AttachmentStorage.class, attachment.getCode());
		if(storage == null){
			if(data == null || data.length == 0) throw new Exception("附件数据为空！");
			storage =  new AttachmentStorage();
			storage.setId(attachment.getCode());
			storage.setSize(attachment.getSize());
			storage.setContent(this.getLobHelper().createBlob(data));
			this.save(storage);
		}
		return storage;
	}

}