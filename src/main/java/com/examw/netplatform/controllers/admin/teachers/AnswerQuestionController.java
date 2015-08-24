package com.examw.netplatform.controllers.admin.teachers;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;
/**
 * 教师答疑控制器
 * @author fengwei.
 * @since 2014年5月31日 上午11:59:02.
 */
@Controller
@RequestMapping("/admin/teachers/answerquestion")
public class AnswerQuestionController {
	private static final Logger logger = Logger.getLogger(AnswerQuestionController.class);
	/**
	 * 加载列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_ANSWERS + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载列表页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.TEACHERS_ANSWERS + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.TEACHERS_ANSWERS + ":" + Right.DELETE);
	    
		return "/teachers/answerquestion_list";
	}
	/**
	 * 加载编辑页面。
	 * @param classId
	 * @param lessonId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_ANSWERS + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String topicId, String classId, String lessonId, Model model){
		logger.debug("加载编辑页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.TEACHERS_ANSWERS + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.TEACHERS_ANSWERS + ":" + Right.DELETE);
		
		model.addAttribute("current_topic_id", topicId);
		model.addAttribute("current_class_id", classId);
		model.addAttribute("current_lesson_id", lessonId);
		
		return "/teachers/answerquestion_edit";
	}
}