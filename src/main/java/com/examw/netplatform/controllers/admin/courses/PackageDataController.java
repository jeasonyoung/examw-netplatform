package com.examw.netplatform.controllers.admin.courses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import com.examw.netplatform.domain.admin.courses.CategoryHasExamView;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.EnumValueName;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.admin.courses.PackageInfo;
import com.examw.netplatform.service.admin.courses.IClassService;
import com.examw.netplatform.service.admin.courses.IPackageService;
import com.examw.netplatform.support.UserAware;
import com.examw.service.Status;

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
	private List<EnumValueName> statusList;
	private String current_agency_id;
	//注入套餐服务接口。
	@Resource
	private IPackageService packageService;
	//注入班级服务接口。
	@Resource
	private IClassService classService;
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
	 * 加载机构套餐集合。
	 * @return
	 */
	@RequestMapping(value="/all")
	public List<PackageInfo> loadPackages(String subjectId){
		logger.debug(String.format("加载机构［%s］套餐集合...", this.current_agency_id));
		return this.packageService.loadPackages(this.current_agency_id, subjectId);
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
	 * 加载状态集合。
	 * @return
	 */
	@RequestMapping(value = "/status")
	public List<EnumValueName> getStatus(){
		logger.debug("加载状态集合...");
		if(this.statusList == null || this.statusList.size() == 0){
			this.statusList = new ArrayList<EnumValueName>();
			for(Status s : Status.values()){
				this.statusList.add(new EnumValueName(s.getValue(), this.packageService.loadStatusName(s.getValue())));
			}
			Collections.sort(this.statusList);
		}
		return this.statusList;
	}
	/**
	 * 加载考试分类数据。
	 * @return
	 */
	@RequestMapping(value = "/category_exam_views")
	public List<CategoryHasExamView> loadCategoryHasExamViews(){
		logger.debug("加载考试分类数据...");
		return this.packageService.loadCategoryHasExamViews();
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
	 * 获取套餐班级数据集合。
	 * @param packageId
	 * 套餐ID。
	 * @return
	 */
	@RequestMapping(value = "/classes")
	public List<ClassPlanInfo> getClassesByPackage(String packageId){
		logger.debug("加载套餐["+packageId+"]下班级数据集合");
		if(StringUtils.isBlank(packageId)) return new ArrayList<ClassPlanInfo>(); 
		return this.classService.loadClassesByPackage(packageId);
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
}