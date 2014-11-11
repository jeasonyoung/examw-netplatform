package com.examw.netplatform.controllers.admin.settings;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.security.RoleInfo;
import com.examw.netplatform.model.admin.settings.AgencyInfo;
import com.examw.netplatform.service.admin.security.IRoleService;
import com.examw.netplatform.service.admin.settings.IAgencyService;
import com.examw.netplatform.support.EnumMapUtils;
import com.examw.service.Status;

/**
 * 培训机构控制器。
 * @author yangyong.
 * @since 2014-04-28.
 */
@Controller
@RequestMapping(value = "/admin/settings/agency")
public class AgencyController {
	private static final Logger logger  = Logger.getLogger(AgencyController.class);
	//培训机构服务接口。
	@Resource
	private IAgencyService agencyService;
	//角色服务接口。
	@Resource
	private IRoleService roleService;
	/**
	 * 培训机构列表页面。
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.SETTINGS_AGENCY + ":" +Right.VIEW)
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled())logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_AGENCY + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_AGENCY + ":" + Right.DELETE);
		return "settings/agency_list";
	}
	/**
	 * 培训机构编辑页面。
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.SETTINGS_AGENCY + ":" +Right.UPDATE)
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(Model model){
		if(logger.isDebugEnabled())logger.debug("加载编辑页面...");
		Map<String, String> statusMap = EnumMapUtils.createTreeMap();
		for(Status status : Status.values()){
			statusMap.put(String.format("%d", status.getValue()), this.agencyService.loadStatusName(status.getValue()));
		}
		model.addAttribute("statusMap", statusMap);
		model.addAttribute("roles", this.roleService.loadAll());
		return "settings/agency_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.SETTINGS_AGENCY + ":" +Right.VIEW)
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<AgencyInfo> datagrid(AgencyInfo info){
		if(logger.isDebugEnabled())logger.debug("加载列表数据...");
		return this.agencyService.datagrid(info);
	}
	/**
	 * 加载全部的培训机构数据。
	 * @return
	 */
	@RequestMapping(value="/all", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<AgencyInfo> loadAllAgencies(){
		if(logger.isDebugEnabled()) logger.debug("加载全部的培训机构数据...");
		return this.agencyService.loadAllAgencies();
	}
	/**
	 * 加载机构下角色集合。
	 * @param agencyId
	 * 机构ID。
	 * @return
	 */
	@RequestMapping(value="/roles", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<RoleInfo> loadAgencyRoles(String agencyId){
		if(logger.isDebugEnabled()) logger.debug("加载机构下角色集合...");
		return this.agencyService.loadRoles(agencyId);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions(ModuleConstant.SETTINGS_AGENCY + ":" +Right.UPDATE)
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(AgencyInfo info){
		if(logger.isDebugEnabled())logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.agencyService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新数据发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.SETTINGS_AGENCY + ":" +Right.DELETE)
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled())logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.agencyService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s...", e.getMessage()), e);
		}
		return result;
	}
}