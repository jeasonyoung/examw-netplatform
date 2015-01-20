package com.examw.netplatform.controllers.front;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 前台用户接口
 * @author fengwei.
 * @since 2015年1月20日 下午4:04:21.
 */
public class FrontUserController {
	/**
	 * 登陆页面
	 * @return
	 */
	@RequestMapping(value = {"login"}, method = RequestMethod.GET)
	public String loginPage()
	{
		return "log";
	}
	
	/**
	 * 注册页面
	 * @return
	 */
	@RequestMapping(value = {"register"}, method = RequestMethod.GET)
	public String regPage()
	{
		return "reg";
	}
}
