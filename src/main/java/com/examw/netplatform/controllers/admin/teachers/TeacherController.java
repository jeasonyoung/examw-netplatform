package com.examw.netplatform.controllers.admin.teachers;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.support.UserAware;
/**
 * 教师控制器
 * @author fengwei.
 * @since 2014年5月29日 下午4:44:59.
 */
@Controller
@RequestMapping("/admin/teachers/teacher")
public class TeacherController implements UserAware {
	private static final Logger logger = Logger.getLogger(TeacherController.class);
	private String current_agency_id;
	/*
	 * 设置当前用户机构ID。
	 * @see com.examw.netplatform.support.UserAware#setAgencyId(java.lang.String)
	 */
	@Override
	public void setAgencyId(String agencyId) {
		logger.debug("注入当前用户机构ID..." + agencyId);
		this.current_agency_id = agencyId;
	}
	/*
	 * 设置当前用户ID。
	 * @see com.examw.netplatform.support.UserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		logger.debug("注入当前用户ID...");
	}
	/**
	 * 加载列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_USER + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载列表页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.TEACHERS_USER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.TEACHERS_USER + ":" + Right.DELETE);
		
		return "/teachers/teacher_list";
	}
	/**
	 * 加载编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_USER + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String teacherId, Model model){
		logger.debug("加载编辑页面...");
		
		model.addAttribute("current_teacher_id", teacherId);
		
		model.addAttribute("PER_UPDATE", ModuleConstant.TEACHERS_USER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.TEACHERS_USER + ":" + Right.DELETE);
		
		return "/teachers/teacher_edit";
	}
	/**
	 * 导入班级关联。
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/import")
	public String importClasses(Model model){
		logger.debug("导入机构["+this.current_agency_id+"]下班级关联...");
		
		model.addAttribute("current_agency_id", this.current_agency_id);
		
		return "/teachers/teacher_import_classes";
	}
}