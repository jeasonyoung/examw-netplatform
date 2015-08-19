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
import com.examw.netplatform.model.admin.settings.CategoryInfo;
import com.examw.netplatform.service.admin.settings.ICategoryService;

/**
 * 考试类别数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月18日
 */
@RestController
@RequestMapping(value = "/admin/settings/category/data")
public class CategoryDataController {
	private static final Logger logger = Logger.getLogger(CategoryDataController.class);
	//注入考试类别服务接口。
	@Resource
	private ICategoryService categroyService;
	/**
	 * 加载考试类别最大代码。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_AREA + ":" + Right.VIEW})
	@RequestMapping(value="/code", method = RequestMethod.GET)
	public Integer code(String pid){
		logger.debug(String.format("加载考试类别最大代码［%s］...", pid));
		Integer max = this.categroyService.loadMaxCode(pid);
		if(max == null) max = 0;
		return max+1; 
	}
	/**
	 * 加载列表数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CATEGORY + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<CategoryInfo> datagrid(CategoryInfo info){
		logger.debug("加载列表数据...");
		return this.categroyService.datagrid(info);
	}
	/**
	 * 加载考试分类数据。
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/all")
	public List<CategoryInfo> loadAllCategories(String ignoreId){
		logger.debug("加载考试分类数据(忽略["+ignoreId+"]及其子节点)...");
		return this.categroyService.loadAllCategorys(ignoreId);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CATEGORY + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public Json update(CategoryInfo info){
		logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.categroyService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新考试类型数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CATEGORY + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format("删除数据［%s］...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.categroyService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}