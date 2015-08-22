package com.examw.netplatform.controllers.admin.courses;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;
/**
 * 套餐控制器。
 * @author fengwei.
 * @since 2014年5月21日 下午4:26:55.
 */
@Controller
@RequestMapping(value = "/admin/courses/package")
public class PackageController{
	private static final Logger logger = Logger.getLogger(PackageController.class);
	/**
	 * 列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载列表页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.COURSES_PACKAGE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.COURSES_PACKAGE + ":" + Right.DELETE);
		
		return "/courses/package_list";
	}
	
	/**
	 * 编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String categoryId,String examId,Model model){
		logger.debug("加载编辑页面...");
		
		model.addAttribute("current_category_id", categoryId);//考试类别ID
		model.addAttribute("current_exam_id", examId);//考试ID
		
		return "/courses/package_edit";
	}
}