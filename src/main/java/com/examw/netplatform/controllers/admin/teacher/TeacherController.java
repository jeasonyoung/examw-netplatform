package com.examw.netplatform.controllers.admin.teacher;

import java.util.Arrays;
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
import com.examw.netplatform.model.admin.settings.AgencyUserInfo;
import com.examw.netplatform.service.admin.security.IUserService;
import com.examw.netplatform.service.admin.security.UserType;
import com.examw.netplatform.service.admin.settings.AgencyUserIdentity;
import com.examw.netplatform.service.admin.settings.IAgencyUserService;
import com.examw.netplatform.service.admin.teacher.ITeacherService;
import com.examw.netplatform.support.EnumMapUtils;
import com.examw.service.Gender;
import com.examw.service.Status;
/**
 * 教师控制器
 * @author fengwei.
 * @since 2014年5月29日 下午4:44:59.
 */
@Controller
@RequestMapping("/admin/teacher/user")
public class TeacherController implements IUserAware {
	private static final Logger logger = Logger.getLogger(TeacherController.class);
	private String current_user_id;
	//注入教师服务接口。
	@Resource
	private ITeacherService teacherService;
	//注入用户服务接口。
	@Resource
	private IUserService userService;
	//注入机构用户服务接口。
	@Resource
	private IAgencyUserService agencyUserService;
	/*
	 * 设置当前用户ID。
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("设置当前用户［%s］...", userId));
		 this.current_user_id = userId;
	}
	/*
	 * 设置当前用户名称。
	 * @see com.examw.aware.IUserAware#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName){}
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
	@RequiresPermissions({ModuleConstant.TEACHER_USER + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.TEACHER_USER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.TEACHER_USER + ":" + Right.DELETE);
		
		String current_agency_id = this.agencyUserService.loadAgencyIdByUser(this.current_user_id);
	    if(StringUtils.isEmpty(current_agency_id)){
	    	return "error/user_not_agency";
	    }
	    model.addAttribute("current_agency_id", current_agency_id);//当前机构ID
		
		return "teacher/teacher_list";
	}
	/**
	 * 加载编辑页面。
	 * @param agencyId
	 * @param modify
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHER_USER + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String agencyId,Boolean modify,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("current_agency_id", agencyId);
		//是否修改
		model.addAttribute("current_is_modify", modify == null ? false : modify);
		Map<String, String> genderMap = EnumMapUtils.createTreeMap(),statusMap = EnumMapUtils.createTreeMap();
		//用户性别。
		for(Gender gender : Gender.values()){
			genderMap.put(String.format("%d", gender.getValue()), this.userService.loadGenderName(gender.getValue()));
		}
		model.addAttribute("genderMap", genderMap);
		//用户状态。 
		for(Status status : Status.values()){
			statusMap.put(String.format("%d", status.getValue()), this.userService.loadStatusName(status.getValue()));	
		}
		model.addAttribute("statusMap", statusMap);
		
		return "teacher/teacher_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHER_USER + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<AgencyUserInfo> datagrid(AgencyUserInfo info){
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.teacherService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.TEACHER_USER + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(AgencyUserInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			info.setIdentity(AgencyUserIdentity.TEACHER.getValue());//机构教师
			info.setType(UserType.BACKGROUND.getValue());//后台用户类型
			result.setData(this.teacherService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新教师数据发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHER_USER + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s ...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.teacherService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 重置用户密码。
	 * @param userId
	 * @param password
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.TEACHER_USER + ":" +Right.UPDATE)
	@RequestMapping(value="/{userId}/modifyPwd", method = RequestMethod.POST)
	@ResponseBody
	public Json modifyUserPwd(@PathVariable String userId,String password){
		if(logger.isDebugEnabled())logger.debug(String.format("重置用户［%s］密码...", userId));
		Json result = new Json();
		try {
			 this.userService.modifyPassword(userId, null, password);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新数据发生异常", e);
		}
		return result;
	}
}