package com.examw.netplatform.controllers.admin.settings;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.settings.AgencyInfo;
import com.examw.netplatform.service.admin.settings.IAgencyService;

/**
 * 培训机构数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月17日
 */
@RestController
@RequestMapping(value = "/admin/settings/agency/data")
public class AgencyDataController {
	private static final Logger logger = Logger.getLogger(AgencyDataController.class);
	//注入培训机构服务接口。
	@Resource
	private IAgencyService agencyService;
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.SETTINGS_AGENCY + ":" +Right.VIEW)
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<AgencyInfo> datagrid(AgencyInfo info){
		logger.debug("加载列表数据...");
		return this.agencyService.datagrid(info);
	}
	/**
	 * 加载全部的培训机构数据。
	 * @return
	 */
	@RequestMapping(value="/all")
	public List<AgencyInfo> loadAgencies(String userId){
		logger.debug("加载全部的培训机构数据...");
		return this.agencyService.loadAgencies(userId);
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
	public Json update(AgencyInfo info){
		logger.debug("更新数据...");
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
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
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