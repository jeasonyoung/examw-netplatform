package com.examw.netplatform.controllers.admin.settings;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;
/**
 * 班级类型控制器。
 * @author yangyong.
 * @since 2014-05-20.
 */
@Controller
@RequestMapping(value = "/admin/settings/class/type")
public class ClassTypeController {
	private static final Logger logger = Logger.getLogger(ClassTypeController.class);
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CLASS_TYPE + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_CLASS_TYPE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_CLASS_TYPE + ":" + Right.DELETE);
		return "/settings/class_type_list";
	}
	/**
	 * 获取编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CLASS_TYPE + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(){
		logger.debug("加载编辑页面...");
		return "/settings/class_type_edit";
	}
}