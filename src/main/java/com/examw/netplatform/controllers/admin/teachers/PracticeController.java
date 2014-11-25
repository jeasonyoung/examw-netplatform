package com.examw.netplatform.controllers.admin.teachers;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.teachers.PracticeInfo;
import com.examw.netplatform.service.admin.settings.IAgencyUserService;
import com.examw.netplatform.service.admin.teachers.IPracticeService;
/**
 * 随堂练习控制器。
 * 
 * @author yangyong
 * @since 2014年11月25日
 */
@Controller
@RequestMapping("/admin/teachers/practice")
public class PracticeController implements IUserAware {
	private static final Logger logger = Logger.getLogger(PracticeController.class);
	private String current_user_id,current_user_name;
	//注入机构用户服务接口。
	@Resource
	private IAgencyUserService agencyUserService;
	//注入随堂练习服务接口。
	@Resource
	private IPracticeService practiceService;
	/*
	 * 设置当前用户ID。
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入当前用户ID：%s", userId));
		this.current_user_id = userId;
	}
	/*
	 * 设置当前用户名称。
	 * @see com.examw.aware.IUserAware#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入当前用户名称：%s", userName));
		this.current_user_name = userName;
	}
	/*
	 * 设置当前用户昵称。
	 * @see com.examw.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String userNickName) {}
	/**
	 * 加载列表页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.TEACHERS_PRACTICE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.TEACHERS_PRACTICE + ":" + Right.DELETE);
		
		String current_agency_id = this.agencyUserService.loadAgencyIdByUser(this.current_user_id);
	    if(StringUtils.isEmpty(current_agency_id)){
	    	return "error/user_not_agency";
	    }
	    model.addAttribute("current_agency_id", current_agency_id);//当前机构ID
	    
		return "teachers/practice_list";
	}
	/**
	 * 加载列表数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<PracticeInfo> datagrid(PracticeInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.practiceService.datagrid(info);
	}
	/**
	 *  加载编辑页面。
	 * @param agencyId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.VIEW})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String agencyId,String classId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.TEACHERS_PRACTICE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.TEACHERS_PRACTICE + ":" + Right.DELETE);
		
		model.addAttribute("current_agency_id", agencyId);//当前机构ID
		model.addAttribute("current_class_id", classId);//当前班级ID
		
		return "teachers/practice_edit";
	}
	/**
	 * 加载结构试题列表页面。
	 * @param agencyId
	 * @param practiceId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.VIEW})
	@RequestMapping(value="/structure/list", method = RequestMethod.GET)
	public String structureItemsEdit(String agencyId,String practiceId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载随堂练习结构列表页面...");
		model.addAttribute("current_agency_id", agencyId);//当前机构ID
		model.addAttribute("current_practice_id", practiceId);//当前随堂练习ID
		return "teachers/practice_structure_list";
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(PracticeInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			info.setUserId(this.current_user_id);
			info.setUserName(this.current_user_name);
			result.setData(this.practiceService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 更新反转随堂练习状态。
	 * @param practiceId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.UPDATE})
	@RequestMapping(value="/reverse/status", method = RequestMethod.POST)
	@ResponseBody
	public Json reverseStatus(String practiceId){
		if(logger.isDebugEnabled()) logger.debug("更新随堂练习状态数据...");
		Json result = new Json();
		try {
			this.practiceService.updateReverseStatus(practiceId);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新随堂练习状态数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param ids
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s ...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.practiceService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}