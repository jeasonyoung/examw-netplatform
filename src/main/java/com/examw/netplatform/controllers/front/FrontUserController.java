package com.examw.netplatform.controllers.front;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.model.front.FrontUserInfo;
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
	 * 用户信息页面
	 * @param abbr
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/{abbr}/userInfo"}, method = RequestMethod.GET)
	public String userInfoPage(@PathVariable String abbr,Model model)
	{
		model.addAttribute("abbr",abbr);
		return String.format("/%s/usercenter/user_info",this.getTemplateDir(abbr));
	}
	/**
	 * 修改用户信息
	 * @param info
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/user/modifyInfo"}, method = RequestMethod.POST)
	@ResponseBody
	public Json modifyInfo(FrontUserInfo info,HttpServletRequest request){
		Json json = new Json();
		try{
			AgencyUser user = this.getAgencyUser(request);
			info.setId(user.getUser().getId());
			if(info.checkLittle())
			{
				User newUser = (this.frontUserService.updateInfo(info));
				user.setUser(newUser);
				json.setSuccess(true);
			}
		}catch(Exception e)
		{
			json.setMsg(e.getMessage());
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 修改密码
	 * @param oldPwd
	 * @param newPwd
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/user/modifyPwd"}, method = RequestMethod.POST)
	@ResponseBody
	public Json modifyInfo(String oldPwd,String newPwd,HttpServletRequest request){
		Json json = new Json();
		try{
			AgencyUser user = this.getAgencyUser(request);
			this.frontUserService.updatePwd(user.getUser().getId(),oldPwd,newPwd);
			json.setSuccess(true);
		}catch(Exception e)
		{
			json.setMsg(e.getMessage());
			json.setSuccess(false);
		}
		return json;
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
			Cookie[] cookies = request.getCookies();
			String lastPage = null;
		    if(cookies!=null){
		    	for(Cookie c:cookies){
		    		if("LastPage".equals(c.getName())){
		    			lastPage = c.getValue();
		    			break;
		    		}
		    	}
		    }
		    if(lastPage!=null) return String.format("redirect:%s",lastPage);
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
	public String register(FrontUserInfo user,@PathVariable String abbr,HttpServletRequest request,Model model)
	{
		String template = this.getTemplateDir(abbr);
		return "redirect:/login";
	}
}
