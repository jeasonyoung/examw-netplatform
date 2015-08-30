package com.examw.netplatform.controllers.admin.settings;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;

/**
 * 消息处理控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月30日
 */
@Controller
@RequestMapping(value = "/admin/settings/msg")
public class MsgController {
	private static final Logger logger  = Logger.getLogger(MsgController.class);
	/**
	 * 加载消息列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_MSG + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载科目列表页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_MSG + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_MSG + ":" + Right.DELETE);
		
		return "/settings/msg_list";
	}
	/**
	 * 加载消息编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_MSG + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String msgId, Model model){
		logger.debug("加载消息编辑页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_MSG + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_MSG + ":" + Right.DELETE);
		
		model.addAttribute("current_msg_id", msgId);
		
		return "/settings/msg_edit";
	}
	
	/**
	 * 加载导入学员页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_MSG + ":" + Right.VIEW})
	@RequestMapping(value = "/import_students")
	public String students(Model model){
		logger.debug("加载导入学生页面...");
		return "/settings/msg_importstudents";
	}
}