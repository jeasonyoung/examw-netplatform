package com.examw.netplatform.controllers.admin.settings;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;

/**
 * 科目控制器。
 * @author fengwei.
 * @since 2014-08-07.
 */
@Controller
@RequestMapping(value = "/admin/settings/subject")
public class SubjectController {
	private static final Logger logger  = Logger.getLogger(SubjectController.class);
	/**
	 * 科目列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_SUBJECT + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载科目列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_SUBJECT + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_SUBJECT + ":" + Right.DELETE);
		return "/settings/subject_list";
	}
	/**
	 * 科目编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_SUBJECT + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String categoryId,String examId,Model model){
		logger.debug(String.format("加载科目［categoryId = %1$s  examId = %2$s］编辑页面...", categoryId,examId));
		model.addAttribute("current_category_id", categoryId);
		model.addAttribute("current_exam_id", examId);
		return "/settings/subject_edit";
	}
}