package com.examw.netplatform.controllers.admin.security;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.service.admin.security.IRoleService;
import com.examw.netplatform.support.EnumMapUtils;
import com.examw.service.Status;

/**
 * 角色控制器。
 * @author yangyong.
 *
 */
@Controller
@RequestMapping(value = "/admin/security/role")
public class RoleController {
	private static final Logger logger = Logger.getLogger(RoleController.class);
	//注入角色服务接口。
	@Resource
	private IRoleService roleService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SECURITY_ROLE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SECURITY_ROLE + ":" + Right.DELETE);
		return "/security/role_list";
	}
	/**
	 * 获取编辑页面。
	 * @return
	 * 编辑页面。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(Model model){
		logger.debug("加载编辑页面...");
		Map<String, String> statusMap = EnumMapUtils.createTreeMap();
		for(Status status : Status.values()){
			statusMap.put(String.format("%d", status.getValue()), this.roleService.loadStatusName(status.getValue()));	
		}
		model.addAttribute("statusMap", statusMap);
		return "/security/role_edit";
	}
	/**
	 * 获取角色权限页面。
	 * @return
	 * 角色权限页面。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.VIEW})
	@RequestMapping(value="/right/{roleId}", method = RequestMethod.GET)
	public String roleRight(@PathVariable String roleId, Model model){
		logger.debug("加载角色权限页面...");
		model.addAttribute("current_role_id", roleId);
		return "/security/role_right";
	}
}