package com.examw.netplatform.controllers.admin.settings;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;
/**
 * 考试类别控制器
 * @author fengwei.
 * @since 2014年8月6日 下午3:48:01.
 */
@Controller
@RequestMapping(value = "/admin/settings/category")
public class CategoryController {
	private static final Logger logger = Logger.getLogger(CategoryController.class);
	/**
	 * 加载列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CATEGORY + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_CATEGORY + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_CATEGORY + ":" + Right.DELETE);
		return "/settings/category_list";
	}
	/**
	 *加载编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CATEGORY + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String id,Model model){
		logger.debug("加载编辑页面...");
		model.addAttribute("current_category_id", id);
		return "/settings/category_edit";
	}
}