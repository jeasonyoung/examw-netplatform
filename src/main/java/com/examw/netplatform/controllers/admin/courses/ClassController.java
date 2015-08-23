package com.examw.netplatform.controllers.admin.courses;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;
/**
 * 班级管理控制器。
 * @author fengwei
 * 2014年5月20日 下午9:52:06
 */
@Controller
@RequestMapping(value = "/admin/courses/class")
public class ClassController{
	private static final Logger logger  = Logger.getLogger(ClassController.class);
	/**
	 * 加载列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_CLASS + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载列表页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.COURSES_CLASS + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.COURSES_CLASS + ":" + Right.DELETE);
		
		return "/courses/class_list";
	}
	
	/**
	 * 加载编辑编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_CLASS + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String categoryId, String examId,String subjectId,Model model){
		logger.debug("加载编辑页面...");
		
		model.addAttribute("current_category_id", categoryId);//当前考试类别ID。
		model.addAttribute("current_exam_id", examId);//当前考试ID。
		model.addAttribute("current_subject_id", subjectId);//当前科目ID
		
		return "/courses/class_edit";
	}
}