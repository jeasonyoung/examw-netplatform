package com.examw.netplatform.controllers.admin.security;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.service.admin.security.IRightService;
/**
 * 菜单权限控制器。
 * @author yangyong.
 * @since 2014-05-04.
 */
@Controller
@RequestMapping(value = "/admin/security/menu/right")
public class MenuRightController {
	private static final Logger logger = Logger.getLogger(MenuRightController.class);
	//权限服务。
	@Resource
	private IRightService rightService;
	/**
	 * 列表页面。
	 * @return
	 */
	@RequiresPermissions({ ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.VIEW})
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.DELETE);
		return "/security/menuright_list";
	}
	/**
	 * 添加页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.UPDATE})
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String edit(String menuId, Model model){
		logger.debug("加载添加页面...");
		model.addAttribute("current_menu_id", menuId);
		model.addAttribute("rights", this.rightService.loadAllRights());
		return "/security/menuright_add";
	}
}