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
import com.examw.netplatform.model.admin.settings.AreaInfo;
import com.examw.netplatform.service.admin.settings.IAreaService;

/**
 * 行政地区数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月18日
 */
@RestController
@RequestMapping(value = "/admin/settings/area/data")
public class AreaDataController {
	private static final Logger logger = Logger.getLogger(AreaDataController.class);
	//注入地区服务接口。
	@Resource
	private IAreaService areaService;
	/**
	 * 加载全部地区数据。
	 * @return
	 */
	@RequestMapping(value = {"/all"}) 
	public List<AreaInfo> all(){
		logger.debug("加载全部地区数据...");
		return this.areaService.loadAllAreas();
	}
	/**
	 * 加载考试下的地区集合。
	 * @param examId
	 * @return
	 */
	@RequestMapping(value = "/exam")
	public List<AreaInfo> loadAreasByExam(String examId){
		logger.debug("加载考试["+examId+"]下的地区集合...");
		return this.areaService.loadAreasByExam(examId);
	}
	/**
	 * 加载地区最大代码值。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_AREA + ":" + Right.VIEW})
	@RequestMapping(value="/code", method = RequestMethod.GET)
	public Integer code(){
		logger.debug("加载地区最大代码值...");
		Integer max = this.areaService.loadMaxCode();
		if(max == null) max = 0;
		return max+1;
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_AREA + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<AreaInfo> datagrid(AreaInfo info){
		logger.debug("加载列表数据...");
		return this.areaService.datagrid(info);
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
	public Json update(AreaInfo info){
		 logger.debug("更新数据...");
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
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format("删除数据：%s...", Arrays.toString(ids)));
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
}