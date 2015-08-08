package com.examw.netplatform.controllers.admin.courses;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.admin.courses.PackageInfo;
import com.examw.netplatform.service.admin.courses.IPackageService;
import com.examw.netplatform.service.admin.settings.IAgencyUserService;
import com.examw.netplatform.support.EnumMapUtils;
import com.examw.service.Status;
/**
 * 套餐控制器
 * @author fengwei.
 * @since 2014年5月21日 下午4:26:55.
 */
@Controller
@RequestMapping(value = "/admin/courses/package")
public class PackageController implements IUserAware{
	private static final Logger logger = Logger.getLogger(PackageController.class);
	private String current_user_id;
	//注入套餐服务接口。
	@Resource
	private IPackageService packageService;
	//注入机构用户服务接口。
	@Resource
	private IAgencyUserService agencyUserService;
	/*
	 * 注入当前用户ID。
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入当前用户ID:%s", userId));
		this.current_user_id = userId;
	}
	/*
	 * 注入当前用户名。
	 * @see com.examw.aware.IUserAware#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName){}
	/*
	 * 注入当前用户昵称。
	 * @see com.examw.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String userNickName){}
	/**
	 * 列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.COURSES_PACKAGE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.COURSES_PACKAGE + ":" + Right.DELETE);
		String current_agency_id = this.agencyUserService.loadAgencyIdByUser(this.current_user_id);
	    if(StringUtils.isEmpty(current_agency_id)){
	    	return "error/user_not_agency";
	    }
	    model.addAttribute("current_agency_id", current_agency_id);//当前机构ID
		return "courses/package_list";
	}
	/**
	 * 加载机构最大排序号。
	 * @param agencyId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.VIEW})
	@RequestMapping(value="/{agencyId}/order", method = RequestMethod.GET)
	@ResponseBody
	public Integer loadMaxOrder(@PathVariable String agencyId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载机构［%s］最大排序号...", agencyId));
		Integer order = this.packageService.loadMaxOrder(agencyId);
		if(order == null) order = 0;
		return order + 1;
	}
	/**
	 * 编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String agencyId,String categoryId,String examId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("current_agency_id", agencyId);//当前机构ID
		model.addAttribute("current_category_id", categoryId);//考试类别ID
		model.addAttribute("current_exam_id", examId);//考试ID
		model.addAttribute("current_user_id", this.current_user_id);//当前用户ID
		
		Map<String, String> statusMap = EnumMapUtils.createTreeMap();
		for(Status status : Status.values()){
			statusMap.put(String.format("%d", status.getValue()), this.packageService.loadStatusName(status.getValue()));
		}
		model.addAttribute("statusMap", statusMap);
		
		return "courses/package_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public DataGrid<PackageInfo> datagrid(PackageInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.packageService.datagrid(info);
	}
	/**
	 * 加载机构套餐集合。
	 * @param agencyId
	 * 机构ID。
	 * @return
	 */
	@RequestMapping(value="/all/{agencyId}", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<PackageInfo> loadPackages(@PathVariable String agencyId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载机构［%s］套餐集合...", agencyId));
		return this.packageService.loadPackages(agencyId);
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
	@ResponseBody
	public Json update(PackageInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
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
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
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
	
	/**
	 * 加载套餐班级列表页面。
	 * @param agencyId
	 * @param agencyUserId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.VIEW})
	@RequestMapping(value = "/{agencyId}/{packageId}/classes/list", method = RequestMethod.GET)
	public String teacherClassesList(@PathVariable String packageId,String examId,@PathVariable String agencyId, Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载套餐［%1$s］下班级列表页面...", packageId));
		
		model.addAttribute("PER_UPDATE", ModuleConstant.COURSES_PACKAGE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.COURSES_PACKAGE + ":" + Right.DELETE);
		
		model.addAttribute("current_package_id", packageId);
		model.addAttribute("current_agency_id",agencyId);
		model.addAttribute("package_exam_id", examId);
		return "courses/package_classes_list";
	}
	/**
	 * 加载机构教师班级编辑页面。
	 * @param agencyId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.VIEW})
	@RequestMapping(value = "/{agencyId}/{packageId}/classes/edit", method = RequestMethod.GET)
	public String teacherClassesEdit(@PathVariable String packageId,@PathVariable String agencyId,String examId, Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载套餐［%s］包含班级编辑页面...", packageId));
		model.addAttribute("current_agency_id", agencyId);
		model.addAttribute("package_exam_id", examId);
		return "courses/package_classes_edit";
	}
	
	/**
	 * 查询机构用户下班级集合。
	 * @param agencyUserId
	 * 机构用户ID。
	 * 2015.01.27
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.VIEW})
	@RequestMapping(value="/{packageId}/classes", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ClassPlanInfo> loadClasses(@PathVariable String packageId){
		if(logger.isDebugEnabled()) logger.debug(String.format("查询套餐［%s］下班级集合...", packageId));
		DataGrid<ClassPlanInfo> grid = new DataGrid<ClassPlanInfo>();
		grid.setRows(this.packageService.loadClasses(packageId));
		return grid;
	}
	/**
	 * 添加班级。
	 * @param packageId
	 * @param classId
	 * 2015.01.27
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.UPDATE})
	@RequestMapping(value="/{packageId}/addClasses", method = RequestMethod.POST)
	@ResponseBody
	public Json addUserClasses(@PathVariable String packageId,@RequestBody String[] classId){
		if(logger.isDebugEnabled()) logger.debug(String.format("添加教师用户［%s］班级: %s...", packageId, Arrays.toString(classId)));
		Json result = new Json();
		try{
			this.packageService.saveClasses(packageId, classId);
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 移除班级。
	 * @param packageId
	 * @param classId
	 * 2015.01.27
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.UPDATE})
	@RequestMapping(value="/{packageId}/removeClasses", method = RequestMethod.POST)
	@ResponseBody
	public Json removeUserClasses(@PathVariable String packageId,@RequestBody String[] classId){
		if(logger.isDebugEnabled()) logger.debug(String.format("移除教师用户［%s］班级: %s...", packageId, Arrays.toString(classId)));
		Json result = new Json();
		try{
			this.packageService.deleteClasses(packageId, classId);
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
}