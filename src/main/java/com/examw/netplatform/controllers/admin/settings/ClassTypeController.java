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
import com.examw.netplatform.model.admin.settings.ClassTypeInfo;
import com.examw.netplatform.service.admin.settings.IClassTypeService;
/**
 * 班级类型控制器。
 * @author yangyong.
 * @since 2014-05-20.
 */
@Controller
@RequestMapping(value = "/admin/settings/class/type")
public class ClassTypeController {
	private static final Logger logger = Logger.getLogger(ClassTypeController.class);
	//班级类型服务接口
	@Resource
	private IClassTypeService classTypeService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CLASS_TYPE + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_CLASS_TYPE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_CLASS_TYPE + ":" + Right.DELETE);
		return "settings/class_type_list";
	}
	/**
	 * 加载最大代码。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CLASS_TYPE + ":" + Right.VIEW})
	@RequestMapping(value={"/code"}, method = RequestMethod.GET)
	@ResponseBody
	public Integer loadMaxCode(){
		if(logger.isDebugEnabled()) logger.debug("加载最大代码...");
		Integer code = this.classTypeService.loadMaxOrder();
		if(code == null) code = 0;
		return code + 1;
	}
	/**
	 * 获取编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CLASS_TYPE + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		return "settings/class_type_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CLASS_TYPE + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ClassTypeInfo> datagrid(ClassTypeInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.classTypeService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CLASS_TYPE + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(ClassTypeInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			 result.setData(this.classTypeService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新班级类型数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CLASS_TYPE + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.classTypeService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 找出全部的班级，下拉数据
	 * @return
	 */
	@RequestMapping(value="/all", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<ClassTypeInfo> loadAll(){
		if(logger.isDebugEnabled()) logger.debug("加载全部班级类型数据...");
		return this.classTypeService.loadAll();
	}
}