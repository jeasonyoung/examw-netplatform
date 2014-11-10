package com.examw.netplatform.controllers.admin.settings;

import java.util.Arrays;
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
import com.examw.netplatform.model.admin.settings.AgencyUserInfo;
import com.examw.netplatform.service.admin.security.IUserService;
import com.examw.netplatform.service.admin.security.UserType;
import com.examw.netplatform.service.admin.settings.AgencyUserIdentity;
import com.examw.netplatform.service.admin.settings.IAgencyUserService;
import com.examw.netplatform.support.EnumMapUtils;
import com.examw.service.Gender;
import com.examw.service.Status;
/**
 * 机构用户控制器。
 * @author yangyong.
 * @since 2014-06-09.
 */
@Controller
@RequestMapping(value="/admin/settings/agency/users")
public class AgencyUserController {
	private static final Logger logger  = Logger.getLogger(AgencyUserController.class);
	//注入机构用户服务接口。
	@Resource
	private IAgencyUserService agencyUserService;
	//注入用户服务接口。
	@Resource
	private IUserService userService;
	/**
	 * 加载列表页面。
	 * @param model
	 */
	@RequiresPermissions(ModuleConstant.SETTINGS_AGENCYUSER + ":" +Right.VIEW)
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled())logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_AGENCYUSER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_AGENCYUSER + ":" + Right.DELETE);
		return "settings/agency_user_list";
	}
	/**
	 * 加载编辑页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.SETTINGS_AGENCYUSER + ":" +Right.UPDATE)
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String agencyId,Model model){
		if(logger.isDebugEnabled())logger.debug("加载编辑页面...");
		model.addAttribute("current_agency_id", agencyId);
		
		Map<String, String> genderMap = EnumMapUtils.createTreeMap(),userTypeMap = EnumMapUtils.createTreeMap(),
				statusMap = EnumMapUtils.createTreeMap(),agencyUserIdentityMap = EnumMapUtils.createTreeMap();
		//用户性别。
		for(Gender gender : Gender.values()){
			genderMap.put(String.format("%d", gender.getValue()), this.userService.loadGenderName(gender.getValue()));
		}
		model.addAttribute("genderMap", genderMap);
		//用户类型。
		for(UserType userType : UserType.values()){
			userTypeMap.put(String.format("%d", userType.getValue()), this.userService.loadTypeName(userType.getValue()));
		}
		model.addAttribute("userTypeMap", userTypeMap);
		//用户状态。 
		for(Status status : Status.values()){
			statusMap.put(String.format("%d", status.getValue()), this.userService.loadStatusName(status.getValue()));	
		}
		model.addAttribute("statusMap", statusMap);
		//机构用户身份
		for(AgencyUserIdentity agencyUserIdentity : AgencyUserIdentity.values()){
			agencyUserIdentityMap.put(String.format("%d", agencyUserIdentity.getValue()), this.agencyUserService.loadIdentityName(agencyUserIdentity.getValue()));	
		}
		model.addAttribute("agencyUserIdentityMap", agencyUserIdentityMap);
		
		return "settings/agency_user_edit";
	}
	/**
	 * 数据列表。
	 * @param info
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.SETTINGS_AGENCYUSER + ":" +Right.VIEW)
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<AgencyUserInfo> datagrid(AgencyUserInfo info){
		if(logger.isDebugEnabled())logger.debug("加载列表数据...");
		return this.agencyUserService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.SETTINGS_AGENCYUSER + ":" +Right.UPDATE)
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody AgencyUserInfo info){
		if(logger.isDebugEnabled())logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.agencyUserService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.SETTINGS_AGENCYUSER + ":" +Right.DELETE)
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled())logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.agencyUserService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}