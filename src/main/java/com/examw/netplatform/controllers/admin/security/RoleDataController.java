package com.examw.netplatform.controllers.admin.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.domain.admin.security.RoleStatus;
import com.examw.netplatform.model.EnumValueName;
import com.examw.netplatform.model.admin.security.RoleInfo;
import com.examw.netplatform.service.admin.security.IRoleService;

/**
 * 角色数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月16日
 */
@RestController
@RequestMapping(value = "/admin/security/role/data")
public class RoleDataController {
	private static final Logger logger = Logger.getLogger(RoleDataController.class);
	private List<EnumValueName> roleStatusList;
	//注入角色服务接口。
	@Resource
	private IRoleService roleService;
	/**
	 * 获取角色状态。
	 * @return
	 */
	@RequestMapping(value="/rolestatus")
	public List<EnumValueName> getRoleStatus(){
		logger.debug("加载角色状态...");
		if(this.roleStatusList == null || this.roleStatusList.size() == 0){
			this.roleStatusList = new ArrayList<EnumValueName>();
			for(RoleStatus status : RoleStatus.values()){
				this.roleStatusList.add(new EnumValueName(status.getValue(), this.roleService.loadStatusName(status.getValue())));
			}
			Collections.sort(this.roleStatusList);
		}
		return this.roleStatusList;
	}
	/**
	 * 获取全部的角色数据。
	 * @return
	 */
	@RequestMapping(value="/all")
	public List<RoleInfo> all(){
		logger.debug("加载全部的角色数据...");
		return this.roleService.loadAll();
	}
	/**
	 * 加载角色权限ID数据。
	 * @param roleId
	 * 角色ID。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.VIEW})
	@RequestMapping(value="/right/{roleId}/ids", method = RequestMethod.GET)
	public String[] loadRoleRightIds(@PathVariable String roleId){
		logger.debug(String.format("加载角色［%s］权限ID数据...", roleId));
		String [] ids = this.roleService.loadRoleRightIds(roleId);
		return ids == null ? new String[0] : ids;
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<RoleInfo> datagrid(RoleInfo info){
		logger.debug("加载列表数据...");
		return this.roleService.datagrid(info);
	}
	/**
	 * 更新角色权限。
	 * @param roleId
	 * 所属角色ID。
	 * @param rightIds
	 * 角色权限ID集合。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.UPDATE})
	@RequestMapping(value="/{roleId}/rights", method = RequestMethod.POST)
	public Json updateRoleRights(@PathVariable String roleId,@RequestBody String[] rightIds){
		logger.debug("更新角色["+roleId+"]权限..." + StringUtils.join(rightIds,","));
		Json json = new Json();
		try {
			this.roleService.updateRoleRights(roleId, rightIds);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg(e.getMessage());
			logger.error(String.format("更新角色权限时发生异常：%s", e.getMessage()), e);
		}
		return json;
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public Json update(RoleInfo info){
		 logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.roleService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新角色数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format("删除数据： %s ...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.roleService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}