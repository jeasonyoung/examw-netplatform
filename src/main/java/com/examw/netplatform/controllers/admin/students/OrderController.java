package com.examw.netplatform.controllers.admin.students;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;
/**
 * 订单控制器。
 * 
 * @author yangyong
 * @since 2014年12月2日
 */
@Controller
@RequestMapping(value = "/admin/students/order")
public class OrderController{
	private static final Logger logger = Logger.getLogger(OrderController.class);
	/**
	 * 加载列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载列表页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.STUDENTS_ORDER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.STUDENTS_ORDER + ":" + Right.DELETE);
		
		return "/students/student_order_list";
	}
	/**
	 * 加载编辑页面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String orderId, Model model){
		logger.debug("加载编辑页面..."); 
		
		model.addAttribute("PER_UPDATE", ModuleConstant.STUDENTS_ORDER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.STUDENTS_ORDER + ":" + Right.DELETE);
		
		model.addAttribute("current_order_id", orderId);
		
		return "/students/student_order_edit";
	}
	/**
	 * 加载导入学生页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.VIEW})
	@RequestMapping(value = "/import_students")
	public String students(Model model){
		logger.debug("加载导入学生页面...");
		return "/students/student_order_importstudents";
	}
	/**
	 * 加载导入班级页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.VIEW})
	@RequestMapping(value = "/import_classes")
	public String classes(Model model){
		logger.debug("加载导入班级页面...");
		return "/students/student_order_importclasses";
	}
	/**
	 * 加载导入套餐页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.VIEW})
	@RequestMapping(value = "/import_packages")
	public String packages(Model model){
		logger.debug("加载导入套餐页面...");
		return "/students/student_order_importpackages";
	}
	
}