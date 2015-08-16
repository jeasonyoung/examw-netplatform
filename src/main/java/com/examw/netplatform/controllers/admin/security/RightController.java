package com.examw.netplatform.controllers.admin.security;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;
/**
 * 权限控制器。
 * @author yangyong.
 * @since 2014-05-03.
 */
@Controller
@RequestMapping(value = "/admin/security/right")
public class RightController {
	private static final Logger logger = Logger.getLogger(RightController.class);
	/**
	 * 加载列表页面。
	 * @return
	 * 列表页面。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_RIGHT + ":" + Right.VIEW})
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SECURITY_RIGHT + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SECURITY_RIGHT + ":" + Right.DELETE);
		return "/security/right_list";
	}
}