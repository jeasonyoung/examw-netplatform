package com.examw.netplatform.controllers.admin.security;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.service.admin.security.IUserService;
/**
 * 用户管理控制器。
 * @author yangyong.
 * @since 2014-05-09.
 */
@Controller
@RequestMapping(value = "/admin/security/user")
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	//注入用户服务接口。
	@Resource
	private IUserService userService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SECURITY_USER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SECURITY_USER + ":" + Right.DELETE);
		
		return "/security/user_list";
	}
	/**
	 * 获取编辑页面。
	 * @param model
	 * 数据绑定。
	 * @return
	 * 编辑页面地址。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Boolean modify,Model model){
		logger.debug("加载编辑页面...");
		//是否修改
		model.addAttribute("current_is_modify", modify == null ? false : modify);
		//
		return "/security/user_edit";
	}
}