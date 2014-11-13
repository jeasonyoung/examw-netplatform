package com.examw.netplatform.controllers.admin.courses;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.courses.PackageInfo;
import com.examw.netplatform.service.admin.courses.IPackageService;
/**
 * 套餐控制器
 * @author fengwei.
 * @since 2014年5月21日 下午4:26:55.
 */
@Controller
@RequestMapping(value = "/admin/courses/package")
public class PackageController implements IUserAware{
	private static final Logger logger  = Logger.getLogger(PackageController.class);
	private String current_user_id;
	//套餐服务接口。
	//@Resource
	private IPackageService packageService;
	//考试服务接口。
	//@Resource
	//private IExamService examService;
	/*
	 * 注入当前用户ID。
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
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
		return "courses/package_list";
	}
	/**
	 * 编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String packageId,String agencyId, String catalogId,String examId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		if(!StringUtils.isEmpty(examId)){
//			CatalogInfo c = this.examService.loadCatalog(examId);
//			if(c != null) catalogId = c.getId();
		}
		model.addAttribute("CURRENT_PACKAGE_ID", StringUtils.isEmpty(packageId) ? "" : packageId);
		model.addAttribute("CURRENT_AGENCY_ID", StringUtils.isEmpty(agencyId) ? "" : agencyId);
		
		model.addAttribute("CURRENT_CATALOG_ID", StringUtils.isEmpty(catalogId) ?  "" : catalogId);
		model.addAttribute("CURRENT_EXAM_ID", StringUtils.isEmpty(examId) ? "" : examId);
		
//		model.addAttribute("STATUS_ENABLED_VALUE", Package.STATUS_ENABLED);
//		model.addAttribute("STATUS_ENABLED_NAME", this.packageService.loadStatusName(Package.STATUS_ENABLED));
//		model.addAttribute("STATUS_DISABLE_VALUE", Package.STATUS_DISABLE);
//		model.addAttribute("STATUS_DISABLE_NAME", this.packageService.loadStatusName(Package.STATUS_DISABLE));
//		
		return "courses/package_edit";
	}
//	/**
//	 * 获取套餐下的班级数据。
//	 * @param packageId
//	 * @return
//	 */
//	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.UPDATE})
//	@RequestMapping(value="/classes", method = {RequestMethod.GET, RequestMethod.POST})
//	@ResponseBody
//	public List<ClassPlanInfo> loadClasses(String packageId){
//		if(logger.isDebugEnabled()) logger.debug("加载套餐下的班级数据...");
//		return this.packageService.loadClasses(packageId);
//	}
//	/**
//	 * 加载所属机构下的套餐集合。
//	 * @param agencyId
//	 * @param catalogId
//	 * @param examId
//	 * @param packageName
//	 * @return
//	 */
//	@RequestMapping(value="/all/{agencyId}", method = {RequestMethod.GET, RequestMethod.POST})
//	@ResponseBody
//	public List<PackageInfo> findPackages(@PathVariable String agencyId, String catalogId,String examId, String packageName){
//		return this.packageService.findPackages(agencyId, catalogId, examId, packageName);
//	}
	/**
	 * 选择班级数据
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.UPDATE})
	@RequestMapping(value="/classes/{agencyId}/{catalogId}", method = RequestMethod.GET)
	public String optClasses(@PathVariable String agencyId,@PathVariable String catalogId,String examId,Model model){
		if(logger.isDebugEnabled()) logger.debug("选择［agencyId＝"+agencyId+"，catalogId＝"+catalogId+"］班级数据...");
		model.addAttribute("CURRENT_AGENCY_ID", agencyId);
		model.addAttribute("CURRENT_CATALOG_ID", catalogId);
		model.addAttribute("CURRENT_EXAM_ID", StringUtils.isEmpty(examId) ? "" : examId);
		return "courses/package_classes";
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
		info.setCurrentUserId(this.current_user_id);
		return this.packageService.datagrid(info);
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
//			if(info.getClasses() != null && info.getClasses().length > 0){
//				Pattern p = Pattern.compile("\"(.+?)\"");
//				Matcher m = p.matcher(info.getClasses()[0]);
//				List<String> list = new ArrayList<>();
//				while(m.find()){
//				     list.add(m.group(1));
//				}
//				info.setClasses(list.toArray(new String[0]));
//			}
			result.setData(this.packageService.update(info));
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
	@RequiresPermissions({ModuleConstant.COURSES_PACKAGE + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		Json result = new Json();
		try {
			this.packageService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}