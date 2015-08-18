package com.examw.netplatform.controllers.admin.settings;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;

/**
 * 培训机构控制器。
 * @author yangyong.
 * @since 2014-04-28.
 */
@Controller
@RequestMapping(value = "/admin/settings/agency")
public class AgencyController {
	private static final Logger logger  = Logger.getLogger(AgencyController.class);
	/**
	 * 培训机构列表页面。
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.SETTINGS_AGENCY + ":" +Right.VIEW)
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_AGENCY + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_AGENCY + ":" + Right.DELETE);
		return "/settings/agency_list";
	}
	/**
	 * 培训机构编辑页面。
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.SETTINGS_AGENCY + ":" +Right.UPDATE)
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(Model model){
		logger.debug("加载编辑页面...");
		return "/settings/agency_edit";
	}
}