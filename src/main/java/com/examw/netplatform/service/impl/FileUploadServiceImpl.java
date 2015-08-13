package com.examw.netplatform.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.AttachmentMapper;
import com.examw.netplatform.domain.Attachment;
import com.examw.netplatform.model.AttachmentInfo;
import com.examw.netplatform.service.IFileUploadService;
import com.examw.utils.IOUtil;
/**
 * 文件上传服务实现。
 * @author yangyong.
 * @since 2014-05-01.
 */
public class FileUploadServiceImpl implements IFileUploadService {
	private static final Logger logger = Logger.getLogger(FileUploadServiceImpl.class);
	private String tempStoragePath;
	private AttachmentMapper attachmentDao;
	/**
	 * 设置附件下载临时存储路径。
	 * @param tempStoragePath 
	 *	  附件下载临时存储路径。
	 */
	public void setTempStoragePath(String tempStoragePath) {
		logger.debug(String.format("附件下载临时存储路径［%s］...", tempStoragePath));
		this.tempStoragePath = tempStoragePath;
	}
	/**
	 * 设置附件数据接口。
	 * @param attachmentDao 
	 *	  附件数据接口。
	 */
	public void setAttachmentDao(AttachmentMapper attachmentDao) {
		if(logger.isDebugEnabled()) logger.debug("注入附件数据接口...");
		this.attachmentDao = attachmentDao;
	}
	/*
	 * 上传。
	 * @see com.examw.test.service.IFileUploadService#upload(java.lang.String, java.lang.String, byte[])
	 */
	@Override
	public synchronized String addUpload(String fileName, String contentType, byte[] data)throws Exception {
		logger.debug(String.format("上传附件［%1$s  %2$s］...", fileName, contentType));
		String msg = null;
		if(StringUtils.isEmpty(fileName)){
			logger.error(msg = "附件文件名称为空！");
			throw new Exception(msg);
		}
		if(data == null || data.length == 0){
			logger.error(msg = "附件文件内容为空！");
			throw new Exception(msg);
		}
		Attachment model = new Attachment();
		model.setName(fileName);
		model.setExtension(IOUtil.getExtension(model.getName()));
		model.setSize((long)data.length);
		model.setCode(DigestUtils.md5DigestAsHex(data));
		model.setContentType(contentType);
		
		//判断附件存储是否存在
		if(!this.attachmentDao.hasAttachmentStorage(model.getCode())){
			logger.debug("插入附件存储..." + model.getCode());
			//附件未存储过，则保存附件存储
			this.attachmentDao.insertAttachmentStorage(model.getCode(), model.getSize(), data);
		}
		//判断附件是否存在
		if(this.attachmentDao.hasAttachment(model.getId())){
			//存在，更新
			this.attachmentDao.updateAttachment(model);
		}else {
			//不存在，插入
			this.attachmentDao.insertAttachment(model);
		}
		return model.getId();
	}
	/*
	 * 下载。
	 * @see com.examw.test.service.IFileUploadService#download(java.lang.String)
	 */
	@Override
	public synchronized AttachmentInfo download(String fileId) throws Exception {
		logger.debug(String.format("下载附件［%s］...", fileId));
		String msg = null;
		if(StringUtils.isEmpty(fileId)){
			logger.error(msg = "附件ID为空！");
			throw new Exception(msg);
		}
		Attachment data = this.attachmentDao.getAttachment(fileId);
		if(data == null){
			logger.error(msg = String.format("附件［fileId ＝%s］不存在！", fileId));
			throw new Exception(msg);
		}
		final String temp_file_name = String.format("%1$s%2$s", data.getCode(), data.getExtension());
		logger.debug(String.format("临时存储附件文件名称：［%s］", temp_file_name));
		final String temp_dir = this.tempStoragePath + File.pathSeparator + new SimpleDateFormat("yyyy-MM-dd").format(data.getCreateTime());
		logger.debug(String.format("临时存储附件目录：［%s］", temp_dir));
		final String temp_path =  temp_dir + File.pathSeparator + temp_file_name;
		logger.debug(String.format("临时存储附件路径：［%s］", temp_path));
		File file = new File(temp_path);
		if(!file.exists()){
			File dir = new File(temp_dir);
			if(!dir.exists()) dir.mkdirs();
			if(data.getContent() == null || data.getContent().length == 0){
				logger.error(msg = String.format("附件［%s］不存在！", data.getName()));
				throw new Exception(msg);
			}
			//生成临时文件。
		   FileCopyUtils.copy(data.getContent(), file);
		}
		AttachmentInfo info = new AttachmentInfo(); 
		BeanUtils.copyProperties(data, info);
		info.setPath(temp_path);
		return info;
	}
}