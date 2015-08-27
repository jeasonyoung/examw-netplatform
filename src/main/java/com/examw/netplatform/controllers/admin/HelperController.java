package com.examw.netplatform.controllers.admin;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examw.netplatform.service.admin.security.IUserAuthentication;

/**
 * 工具控制器。
 * @author yangyong.
 * @since 2014-06-10.
 */
@RestController
@RequestMapping(value = "/admin")
public class HelperController {
	private static final Logger logger = Logger.getLogger(HelperController.class);
	//注入用户认证服务。
	@Resource
	private IUserAuthentication userAuthentication;
	/**
	 * 构建UUID字符串。
	 * @return
	 * UUID字符串。
	 */
	@RequestMapping(value = {"/UUID","/uuid"})
	public String[] buildUUID(Integer count){
		logger.debug("机构UUID字符串...");
		if(count == null || count < 1) count = 1;
		String[] result = new String[count];
		for(int i = 0; i < count; i++){
			 result[i] = UUID.randomUUID().toString();
		} 
		return result;
	}
	/**
	 * 加载随机码。
	 * @param length
	 * @return
	 */
	@RequestMapping(value = {"/RandomCode", "/randomcode"})
	public String[] randomCode(){
		logger.debug("创建随机码...");
		return new String[] {  this.userAuthentication.createVerifyCode() };
	}
}