package com.examw.netplatform.controllers;
  
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.examw.netplatform.model.AttachmentInfo;
import com.examw.netplatform.service.IFileUploadService;

/**
 * 文件上传控制器。
 * @author yangyong.
 * @since 2014-04-30.
 */
@RestController
@RequestMapping(value = {"/ueditor/upload"})
public class UEditorUploadController {
	private static Logger logger = Logger.getLogger(UEditorUploadController.class);
	//注入附件上传服务接口。
	@Resource
	private IFileUploadService fileUploadService;
	/**
	 * 加载上传配置。
	 * @param action
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value="/controller",params = {"action=config"})
	public Map<String, Object> loadUploadConfig(String callback) throws Exception{
		logger.debug(String.format("加载上传配置［callback = %s］...", callback));
		Map<String, Object> config = new HashMap<>();
		config.put("imageActionName", "uploadimage");
		config.put("imageFieldName", "upfile");
		config.put("imageMaxSize", 20480000);
		config.put("imageAllowFiles", new String[]{".png",".jpg",".jpeg",".gif",".bmp"});
		config.put("imageCompressEnable", true);
		config.put("imageCompressBorder", 1600);
		config.put("imageInsertAlign", "none");
		config.put("imageUrlPrefix", "");
		config.put("imagePathFormat", "/upload/preview/");
		return config;
	}
	/**
	 * 上传图片附件。
	 * @param upfile
	 * @param callback
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value={"/controller"},params={"action=uploadimage"},method = {RequestMethod.POST})
	public Map<String, Object> uploadImage(MultipartFile upfile,String callback, HttpServletRequest request) throws Exception {
		logger.debug(String.format("上传图片附件［callback ＝ %s］...", callback));
		String contentType = upfile.getContentType();  
		if(StringUtils.isBlank(contentType)) contentType = "application/octet-stream"; 
		Map<String, Object> resultMap = new HashMap<>();
		try {
			String attach_id = this.fileUploadService.addUpload(upfile.getOriginalFilename(), contentType,upfile.getBytes());
			resultMap.put("state", "SUCCESS");
			resultMap.put("url", String.format("%1$s/ueditor/upload/preview/%2$s.do", request.getContextPath(), attach_id));
			resultMap.put("title", upfile.getOriginalFilename());
			resultMap.put("original", upfile.getOriginalFilename());
		} catch (Exception e) {
			 logger.error(String.format("上传图片附件时发生异常：%s", e.getMessage()), e);
			 e.printStackTrace();
			 resultMap.put("state", e.getMessage());
		}
		return resultMap;
	}
	/**
	 * 预览附件。
	 * @param attachementId
	 * @return
	 */
	@RequestMapping(value = {"/preview/{attachementId}"}, method = {RequestMethod.GET })
	public void previewAttachment(@PathVariable String attachementId, HttpServletResponse resp) throws Exception {
		logger.debug(String.format("下载附件：%s", attachementId));
		String msg = null;
		AttachmentInfo info = this.fileUploadService.download(attachementId);
		if(info == null){
			logger.error(msg = String.format("附件［%s］不存在！", attachementId));
			throw new Exception(msg);
		}
		logger.debug(info);
		//String displayName = URLEncoder.encode(info.getName(), "UTF-8");
		File imgFile = new File(info.getPath());
		if(!imgFile.exists()){
			logger.error(msg = String.format("预览文件［%s］不存在！", info.getPath()));
			throw new Exception(msg);
		}
		OutputStream out = null; 
		FileInputStream in = null;
		try {
			resp.setContentType(info.getContentType());
			
			out = resp.getOutputStream();
			in = new FileInputStream(imgFile);
			FileCopyUtils.copy(in, out);
			
			out.flush();
			
		} catch (Exception e) {
			logger.error(msg = String.format("生成文件时发生异常：%s", e.getMessage()));
			throw e;
		}finally{
			if(in != null){
				in.close();
			}
			if(out != null ){
				out.close();
			}
		}
	}
}