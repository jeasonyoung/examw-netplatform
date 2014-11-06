package com.examw.netplatform.controllers.admin.settings;

import java.util.Arrays;
import java.util.List;

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
import com.examw.netplatform.model.admin.settings.AreaInfo;
import com.examw.netplatform.service.admin.settings.IAreaService;

/**
 * 行政地区控制器
 * @author fengwei.
 * @since 2014年8月6日 下午1:46:31.
 */
@Controller
@RequestMapping(value = "/admin/settings/area")
public class AreaController {
	private static final Logger logger = Logger.getLogger(AreaController.class);
	//注入地区服务接口。
	@Resource
	private IAreaService areaService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_AREA + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_AREA + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_AREA + ":" + Right.DELETE);
		return "settings/area_list";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_AREA + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<AreaInfo> datagrid(AreaInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.areaService.datagrid(info);
	}
	/**
	 * 获取编辑页面。
	 * @param model
	 * 数据绑定。
	 * @return
	 * 编辑页面地址。
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_AREA + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		return "settings/area_edit";
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_AREA + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(AreaInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			 result.setData(this.areaService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新地区数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_AREA + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.areaService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常：%s",e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 加载全部地区数据。
	 * @return
	 */
	@RequestMapping(value = {"/all"}, method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public List<AreaInfo> all(){
		if(logger.isDebugEnabled()) logger.debug("加载全部地区数据...");
		return this.areaService.loadAllAreas();
	}
	/**
	 * 加载地区最大代码值。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_AREA + ":" + Right.VIEW})
	@RequestMapping(value="/code", method = RequestMethod.GET)
	@ResponseBody
	public Integer code(){
		if(logger.isDebugEnabled()) logger.debug("加载地区最大代码值...");
		Integer max = this.areaService.loadMaxCode();
		if(max == null) max = 0;
		return max+1;
	}
}