package com.examw.netplatform.controllers.front;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.service.front.user.IFrontUserService;

/**
 * 前台用户接口
 * @author fengwei.
 * @since 2015年1月20日 下午4:04:21.
 */
@Controller
@RequestMapping(value="/")
public class FrontUserController {
	@Resource
	private IFrontUserService frontUserService;
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
	
	/**
	 * 登陆页面
	 * @return
	 */
	@RequestMapping(value = {"login"}, method = RequestMethod.POST)
	public String login(String username,String password,HttpServletRequest request,Model model)
	{
		try{
			AgencyUser user = this.frontUserService.login(username, password);
			request.getSession().setAttribute("frontUser", user);
		}catch(Exception e)
		{
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "log";
		}
		return "redirect:myCourse";
	}
	
}
