package com.examw.netplatform.controllers.admin.settings;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;
/**
 * 章节控制器
 * @author fengwei.
 * @since 2014年4月30日 下午4:25:13.
 * 
 * 重构并添加权限控制。
 * @author yangyong.
 * @since 2014-05-24.
 */
@Controller
@RequestMapping(value = "/admin/settings/chapter")
public class ChapterController {
	private static final Logger logger  = Logger.getLogger(ChapterController.class);
	/**
	 * 章节列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CHAPTER + ":" + Right.VIEW})
	@RequestMapping(value ={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_CHAPTER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_CHAPTER + ":" + Right.DELETE);
		return "/settings/chapter_list";
	}
	/**
	 * 章节编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CHAPTER + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String categoryId,String examId,String subjectId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("current_category_id", categoryId);
		model.addAttribute("current_exam_id", examId);
		model.addAttribute("current_subject_id", subjectId);
		return "/settings/chapter_edit";
	}
}