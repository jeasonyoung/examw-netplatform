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
import com.examw.netplatform.service.admin.courses.HandoutMode;
import com.examw.netplatform.service.admin.courses.IClassPlanService;
import com.examw.netplatform.service.admin.courses.VideoMode;
import com.examw.netplatform.service.admin.settings.IAgencyUserService;
import com.examw.netplatform.support.EnumMapUtils;
import com.examw.service.Status;
/**
 * 开班计划控制器。
 * @author fengwei
 * 2014年5月20日 下午9:52:06
 */
@Controller
@RequestMapping(value = "/admin/courses/classplan")
public class ClassPlanController implements IUserAware {
	private static final Logger logger  = Logger.getLogger(ClassPlanController.class);
	private String current_user_id;
	//注入开班计划服务接口。
	@Resource
	private IClassPlanService classPlanService;
	//注入机构用户服务接口。
	@Resource
	private IAgencyUserService agencyUserService;
	/*
	 * 设置当前用户ID。
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		if(logger.isDebugEnabled()) logger.debug("注入当前用户ID...");
		this.current_user_id = userId;
	}
	/*
	 * 设置当前用户名称。
	 * @see com.examw.aware.IUserAware#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {}
	/*
	 * 设置当前用户昵称。
	 * @see com.examw.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String userNickName){}
	/**
	 * 加载列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_CLASS + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.COURSES_CLASS + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.COURSES_CLASS + ":" + Right.DELETE);
		
	    String current_agency_id = this.agencyUserService.loadAgencyIdByUser(this.current_user_id);
	    if(StringUtils.isEmpty(current_agency_id)){
	    	return "error/user_not_agency";
	    }
	    model.addAttribute("current_agency_id", current_agency_id);
		
		return "courses/classplan_list";
	}
	/**
	 * 加载机构最大排序号。
	 * @param agencyId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_CLASS + ":" + Right.VIEW})
	@RequestMapping(value="/{agencyId}/order", method = RequestMethod.GET)
	@ResponseBody
	public Integer loadMaxOrder(@PathVariable String agencyId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载机构［%s］排序号...",agencyId));
		Integer max = this.classPlanService.loadMaxOrder(agencyId);
		if(max == null) max = 0;
		return max + 1;
	}
	/**
	 * 加载编辑编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_CLASS + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String currentAgencyId, String categoryId, String examId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		
		model.addAttribute("current_user_id", this.current_user_id);//当前用户ID。
		model.addAttribute("current_agency_id", currentAgencyId);//当前培训机构ID。
		model.addAttribute("current_category_id", categoryId);//当前考试类别ID。
		model.addAttribute("current_exam_id", examId);//当前考试ID。
		
		Map<String, String> handoutModeMap = EnumMapUtils.createTreeMap(), videoModeMap = EnumMapUtils.createTreeMap(),statusMap = EnumMapUtils.createTreeMap();
		for(HandoutMode mode : HandoutMode.values()){
			handoutModeMap.put(String.format("%d", mode.getValue()), this.classPlanService.loadHandoutModeName(mode.getValue()));
		}
		model.addAttribute("handoutModeMap", handoutModeMap);
		
		for(VideoMode mode : VideoMode.values()){
			videoModeMap.put(String.format("%d", mode.getValue()), this.classPlanService.loadVideoModeName(mode.getValue()));
		}
		model.addAttribute("videoModeMap", videoModeMap);
		
		for(Status status : Status.values()){
			statusMap.put(String.format("%d", status.getValue()), this.classPlanService.loadStatusName(status.getValue()));
		}
		model.addAttribute("statusMap", statusMap);
		
		return "courses/classplan_edit";
	}
	/**
	 * 加载列表数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_CLASS + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ClassPlanInfo> datagrid(ClassPlanInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.classPlanService.datagrid(info);
	}
	/**
	 * 加载机构下班级集合。
	 * @param agencyId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_CLASS + ":" + Right.VIEW})
	@RequestMapping(value="/all", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<ClassPlanInfo> loadAll(String agencyId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载机构［%s］下班级集合...", agencyId));
		return this.classPlanService.loadClasses(agencyId);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.COURSES_CLASS + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(ClassPlanInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			if(info.getStartTime().getTime() - info.getEndTime().getTime() > 0){
				result.setSuccess(false);
				result.setMsg("［开班时间］必须早于［结班时间］！");
				return result;
			}
			result.setData(this.classPlanService.update(info));
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
	@RequiresPermissions({ModuleConstant.COURSES_CLASS + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.classPlanService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}