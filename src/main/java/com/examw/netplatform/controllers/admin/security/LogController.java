package com.examw.netplatform.controllers.admin.security;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;
/**
 * 登录日志管理控制器。
 * @author yangyong.
 * @since 2014-05-19.
 */
@Controller
@RequestMapping(value = "/admin/security/log")
public class LogController {
	private static final Logger logger = Logger.getLogger(LogController.class);
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_LOGIN_LOG + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载列表页面...");
		model.addAttribute("PER_DELETE", ModuleConstant.SECURITY_LOGIN_LOG + ":" + Right.DELETE);
		return "/security/log_list";
	}
}