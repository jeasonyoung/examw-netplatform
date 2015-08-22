package com.examw.netplatform.controllers.admin.courses;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;
/**
 * 课时资源控制器。
 * @author fengwei.
 * @since 2014年5月22日 下午1:58:48.
 */
@Controller
@RequestMapping(value = "/admin/courses/lesson")
public class LessonController {
	private static final Logger logger  = Logger.getLogger(LessonController.class);
	/**
	 * 列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_RESOURCES + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(String classId,String subjectId, Model model){
		logger.debug("加载列表页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.COURSES_RESOURCES + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.COURSES_RESOURCES + ":" + Right.DELETE);
		
		return "/courses/lesson_list";
	}
	/**
	 * 课时编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_RESOURCES + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String categoryId, String examId, String subjectId, Model model){
		logger.debug("加载编辑页面...");
		
		model.addAttribute("current_category_id", categoryId);//当前考试类别ID。
		model.addAttribute("current_exam_id", examId);//当前考试ID。
		model.addAttribute("current_subject_id", subjectId);//当前科目ID。
		
		return "/courses/lesson_edit";
	}
}