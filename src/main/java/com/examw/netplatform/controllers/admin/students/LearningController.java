package com.examw.netplatform.controllers.admin.students;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;

/**
 * 学习进度控制器。
 * @author fengwei.
 * @since 2014年5月29日 下午2:23:41.
 */
@Controller
@RequestMapping("/admin/students/learning")
public class LearningController{
	private static final Logger logger = Logger.getLogger(LearningController.class);
	/**
	 * 加载列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_LEARNING + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载列表页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.STUDENTS_LEARNING + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.STUDENTS_LEARNING + ":" + Right.DELETE);
		
		return "/students/student_learning_list";
	}
	/**
	 * 加载编辑页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_LEARNING + ":" + Right.VIEW})
	@RequestMapping(value="edit", method = RequestMethod.GET)
	public String edit(Model model){
		logger.debug("加载编辑页面...");
		
		return "/students/student_learning_edit";
	}
}