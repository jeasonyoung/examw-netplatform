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
import com.examw.netplatform.model.admin.settings.ClassTypeInfo;
import com.examw.netplatform.service.admin.settings.IClassTypeService;
import com.examw.netplatform.support.UserAware;

/**
 * 班级类型数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月18日
 */
@RestController
@RequestMapping(value = "/admin/settings/class/type/data")
public class ClassTypeDataController implements UserAware {
	private static final Logger logger = Logger.getLogger(ClassTypeDataController.class);
	private String current_agency_id;
	//注入班级类型服务接口
	@Resource
	private IClassTypeService classTypeService;
	/*
	 * 设置当前用户所属机构ID。
	 * @see com.examw.netplatform.support.UserAware#setAgencyId(java.lang.String)
	 */
	@Override
	public void setAgencyId(String agencyId) {
		 logger.debug("注入当前用户所属机构ID:" + agencyId);
		 this.current_agency_id = agencyId;
	}
	/*
	 * 设置当前用户ID。
	 * @see com.examw.netplatform.support.UserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		logger.debug("注入当前用户ID:" + userId);
	}
	/**
	 * 加载最大代码。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CLASS_TYPE + ":" + Right.VIEW})
	@RequestMapping(value={"/code"}, method = RequestMethod.GET)
	public Integer loadMaxCode(){
		logger.debug("加载最大代码...");
		Integer code = this.classTypeService.loadMaxOrder();
		if(code == null) code = 0;
		return code + 1;
	}
	/**
	 * 全部的班级类型数据。
	 * @return
	 */
	@RequestMapping(value="/all")
	public List<ClassTypeInfo> loadAll(){
		logger.debug("加载全部班级类型数据...");
		return this.classTypeService.loadAll(this.current_agency_id);
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CLASS_TYPE + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<ClassTypeInfo> datagrid(ClassTypeInfo info){
		logger.debug("加载列表数据...");
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
	public Json update(ClassTypeInfo info){
		logger.debug("更新数据...");
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
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format("删除数据：%s...", Arrays.toString(ids)));
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
}