package com.examw.netplatform.controllers.admin.courses;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.courses.PackageInfo;
import com.examw.netplatform.service.admin.courses.IPackageService;
import com.examw.netplatform.support.UserAware;

/**
 * 套餐数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月22日
 */
@RestController
@RequestMapping(value = "/admin/courses/package/data")
public class PackageDataController implements UserAware {
	private static final Logger logger = Logger.getLogger(PackageDataController.class);
	private String current_agency_id;
	//注入套餐服务接口。
	@Resource
	private IPackageService packageService;
	/*
	 * 设置当前配置机构ID。
	 * @see com.examw.netplatform.support.UserAware#setAgencyId(java.lang.String)
	 */
	@Override
	public void setAgencyId(String agencyId) {
		logger.debug("注入当前配置机构ID..." + agencyId);
		this.current_agency_id = agencyId;
	}
	/*
	 * 设置当前用户ID。
	 * @see com.examw.netplatform.support.UserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		logger.debug("注入当前用户ID..." + userId);
	}
	/**
	 * 加载机构最大排序号。
	 * @param agencyId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.VIEW})
	@RequestMapping(value="/order")
	public Integer loadMaxOrder(String examId){
		logger.debug(String.format("加载机构［%s］最大排序号...", this.current_agency_id));
		Integer order = this.packageService.loadMaxOrder(this.current_agency_id, examId);
		if(order == null) order = 0;
		return order + 1;
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = {RequestMethod.POST,RequestMethod.GET})
	public DataGrid<PackageInfo> datagrid(PackageInfo info){
		logger.debug("加载列表数据...");
		info.setAgencyId(this.current_agency_id);
		return this.packageService.datagrid(info);
	}
	/**
	 * 加载机构套餐集合。
	 * @return
	 */
	@RequestMapping(value="/all")
	public List<PackageInfo> loadPackages(String subjectId){
		logger.debug(String.format("加载机构［%s］套餐集合...", this.current_agency_id));
		return this.packageService.loadPackages(this.current_agency_id, subjectId);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public Json update(PackageInfo info){
		logger.debug("更新数据...");
		Json result = new Json();
		try {
			if(StringUtils.isBlank(info.getAgencyId())){
				info.setAgencyId(this.current_agency_id);
			}
			result.setData(this.packageService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新数据发生异常:", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.packageService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:", e.getMessage()), e);
		}
		return result;
	}
	
//	/**
//	 * 加载套餐班级列表页面。
//	 * @param agencyId
//	 * @param agencyUserId
//	 * @return
//	 */
//	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.VIEW})
//	@RequestMapping(value = "/{agencyId}/{packageId}/classes/list", method = RequestMethod.GET)
//	public String teacherClassesList(@PathVariable String packageId,String examId,@PathVariable String agencyId, Model model){
//		if(logger.isDebugEnabled()) logger.debug(String.format("加载套餐［%1$s］下班级列表页面...", packageId));
//		
//		model.addAttribute("PER_UPDATE", ModuleConstant.COURSES_PACKAGE + ":" + Right.UPDATE);
//		model.addAttribute("PER_DELETE", ModuleConstant.COURSES_PACKAGE + ":" + Right.DELETE);
//		
//		model.addAttribute("current_package_id", packageId);
//		model.addAttribute("current_agency_id",agencyId);
//		model.addAttribute("package_exam_id", examId);
//		return "courses/package_classes_list";
//	}
//	/**
//	 * 加载机构教师班级编辑页面。
//	 * @param agencyId
//	 * @param model
//	 * @return
//	 */
//	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.VIEW})
//	@RequestMapping(value = "/{agencyId}/{packageId}/classes/edit", method = RequestMethod.GET)
//	public String teacherClassesEdit(@PathVariable String packageId,@PathVariable String agencyId,String examId, Model model){
//		if(logger.isDebugEnabled()) logger.debug(String.format("加载套餐［%s］包含班级编辑页面...", packageId));
//		model.addAttribute("current_agency_id", agencyId);
//		model.addAttribute("package_exam_id", examId);
//		return "courses/package_classes_edit";
//	}
//	/**
//	 * 查询机构用户下班级集合。
//	 * @param agencyUserId
//	 * 机构用户ID。
//	 * 2015.01.27
//	 * @return
//	 */
//	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.VIEW})
//	@RequestMapping(value="/{packageId}/classes", method = RequestMethod.POST)
//	@ResponseBody
//	public DataGrid<ClassPlanInfo> loadClasses(@PathVariable String packageId){
//		if(logger.isDebugEnabled()) logger.debug(String.format("查询套餐［%s］下班级集合...", packageId));
//		DataGrid<ClassPlanInfo> grid = new DataGrid<ClassPlanInfo>();
//		grid.setRows(this.packageService.loadClasses(packageId));
//		return grid;
//	}
//	/**
//	 * 添加班级。
//	 * @param packageId
//	 * @param classId
//	 * 2015.01.27
//	 * @return
//	 */
//	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.UPDATE})
//	@RequestMapping(value="/{packageId}/addClasses", method = RequestMethod.POST)
//	@ResponseBody
//	public Json addUserClasses(@PathVariable String packageId,@RequestBody String[] classId){
//		if(logger.isDebugEnabled()) logger.debug(String.format("添加教师用户［%s］班级: %s...", packageId, Arrays.toString(classId)));
//		Json result = new Json();
//		try{
//			this.packageService.saveClasses(packageId, classId);
//			result.setSuccess(true);
//		}catch(Exception e){
//			result.setSuccess(false);
//			result.setMsg(e.getMessage());
//		}
//		return result;
//	}
//	/**
//	 * 移除班级。
//	 * @param packageId
//	 * @param classId
//	 * 2015.01.27
//	 * @return
//	 */
//	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.UPDATE})
//	@RequestMapping(value="/{packageId}/removeClasses", method = RequestMethod.POST)
//	@ResponseBody
//	public Json removeUserClasses(@PathVariable String packageId,@RequestBody String[] classId){
//		if(logger.isDebugEnabled()) logger.debug(String.format("移除教师用户［%s］班级: %s...", packageId, Arrays.toString(classId)));
//		Json result = new Json();
//		try{
//			this.packageService.deleteClasses(packageId, classId);
//			result.setSuccess(true);
//		}catch(Exception e){
//			result.setSuccess(false);
//			result.setMsg(e.getMessage());
//		}
//		return result;
//	}
}