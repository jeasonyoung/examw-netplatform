package com.examw.netplatform.controllers.admin;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.netplatform.service.admin.settings.IAgencyUserService;

/**
 * 工具控制器。
 * @author yangyong.
 * @since 2014-06-10.
 */
@Controller
@RequestMapping(value = "/admin")
public class HelperController {
	//注入机构用户服务接口。
	@Resource
	private IAgencyUserService agencyUserService;
	/**
	 * 构建UUID字符串。
	 * @return
	 * UUID字符串。
	 */
	@RequestMapping(value = "/UUID", method = RequestMethod.GET)
	@ResponseBody
	public String[] buildUUID(Integer count){
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
	@RequestMapping(value = "/RandomCode", method = RequestMethod.GET)
	@ResponseBody
	public String[] randomCode(Integer length){
		return new String[] { this.agencyUserService.loadRandomCode(length) };
	}
}