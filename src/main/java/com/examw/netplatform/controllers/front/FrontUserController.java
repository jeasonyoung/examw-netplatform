package com.examw.netplatform.controllers.front;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.model.front.FrontUser;
import com.examw.netplatform.service.front.user.IFrontUserService;

/**
 * 前台用户接口
 * @author fengwei.
 * @since 2015年1月20日 下午4:04:21.
 */
@Controller
@RequestMapping(value="/")
public class FrontUserController extends FrontBaseController{
	@Resource
	private IFrontUserService frontUserService;
	/**
	 * 登陆页面
	 * @return
	 */
	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String loginPage(Model model)
	{
		return "log";
	}
	
	/**
	 * 注册页面
	 * @return
	 */
	@RequestMapping(value = {"/{abbr}/register"}, method = RequestMethod.GET)
	public String regPage(@PathVariable String abbr,Model model)
	{
		model.addAttribute("abbr",abbr);
		return String.format("/%s/reg",this.getTemplateDir(abbr));
	}
	
	/**
	 * 登陆方法
	 * @return
	 */
	@RequestMapping(value = {"/login"}, method = RequestMethod.POST)
	public String login(String username,String password,HttpServletRequest request,Model model)
	{
		String abbr = "";
		try{
			AgencyUser user = this.frontUserService.login(username, password);
			abbr = user.getAgency().getAbbr_en();
			request.getSession().setAttribute("frontUser", user);
		}catch(Exception e)
		{
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "log";
		}
		return String.format("redirect:/%s/myCourse",abbr);
	}
	/**
	 * 退出
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/logout"}, method = {RequestMethod.GET,RequestMethod.POST})
	public String logout(HttpServletRequest request,Model model)
	{
		request.getSession().removeAttribute("frontUser");
		return "redirect:login";
	}
	/**
	 * 注册
	 */
	@RequestMapping(value = {"/{abbr}/register"}, method = RequestMethod.POST)
	public String register(FrontUser user,@PathVariable String abbr,HttpServletRequest request,Model model)
	{
		String template = this.getTemplateDir(abbr);
		return "redirect:/login";
	}
}
