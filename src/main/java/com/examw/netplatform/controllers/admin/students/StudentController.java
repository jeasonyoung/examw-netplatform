package com.examw.netplatform.controllers.admin.students;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;
/**
 * 机构学员控制器
 * @author fengwei.
 * @since 2014年5月24日 上午11:13:21.
 */
@Controller
@RequestMapping(value = "/admin/students/user")
public class StudentController {
	private static final Logger logger = Logger.getLogger(StudentController.class);
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_USER + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载列表页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.STUDENTS_USER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.STUDENTS_USER + ":" + Right.DELETE);
		
		return "/students/student_user_list";
	}
	/**
	 * 获取编辑页面。
	 * @param model
	 * 数据绑定。
	 * @return
	 * 编辑页面地址。
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_USER + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model){
		logger.debug("加载编辑页面...");
		
		return "/students/student_user_edit";
	}
}