package com.examw.netplatform.controllers.admin.agency;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.agency.AgencyUser;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.agency.AgencyInfo;
import com.examw.netplatform.model.admin.agency.AgencyUserInfo;
import com.examw.netplatform.model.admin.security.UserInfo;
import com.examw.netplatform.service.admin.agency.IAgencyUserService;
import com.examw.netplatform.service.admin.security.IUserService;
/**
 * 机构用户控制器。
 * @author yangyong.
 * @since 2014-06-09.
 */
@Controller
@RequestMapping(value="/admin/agency/users")
public class AgencyUserController implements IUserAware {
	private static final Logger logger  = Logger.getLogger(AgencyUserController.class);
	private String current_user_id;
	//用户服务接口。
	@Resource
	private IUserService userService;
	//机构用户服务。
	@Resource
	private IAgencyUserService agencyUserService;
	/*
	 * 设置当前用户ID。
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
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
	public void setUserNickName(String userNickName) {}
	/**
	 * 列表页面。
	 * @param model
	 */
	@RequiresPermissions(ModuleConstant.AGENCY_USER + ":" +Right.VIEW)
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled())logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.AGENCY_USER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.AGENCY_USER + ":" + Right.DELETE);
		return "agency/agency_user_list";
	}
	/**
	 * 编辑页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.AGENCY_USER + ":" +Right.UPDATE)
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String agencyId,Model model){
		if(logger.isDebugEnabled())logger.debug("加载编辑页面...");
//		model.addAttribute("STATUS_ENABLED", this.userService.loadUserStatusName(User.STATUS_ENABLED));
//		model.addAttribute("STATUS_DISABLE", this.userService.loadUserStatusName(User.STATUS_DISABLE));
//		
//		model.addAttribute("GENDER_MALE", this.userService.loadGenderName(User.GENDER_MALE));
//		model.addAttribute("GENDER_FEMALE", this.userService.loadGenderName(User.GENDER_FEMALE));
		
		model.addAttribute("IDENTITY_USER", AgencyUser.IDENTITY_USER);//机构管理员
		model.addAttribute("IDENTITY_USER_NAME", this.agencyUserService.loadIdentityName(AgencyUser.IDENTITY_USER));
		model.addAttribute("IDENTITY_TEACHER", AgencyUser.IDENTITY_TEACHER);//机构教师
		model.addAttribute("IDENTITY_TEACHER_NAME", this.agencyUserService.loadIdentityName(AgencyUser.IDENTITY_TEACHER));
		model.addAttribute("IDENTITY_STUDENT", AgencyUser.IDENTITY_STUDENT);//机构学员
		model.addAttribute("IDENTITY_STUDENT_NAME", this.agencyUserService.loadIdentityName(AgencyUser.IDENTITY_STUDENT));
		model.addAttribute("IDENTITY_CARD", AgencyUser.IDENTITY_CARD);//学习卡用户
		model.addAttribute("IDENTITY_CARD_NAME", this.agencyUserService.loadIdentityName(AgencyUser.IDENTITY_CARD));
		
		model.addAttribute("CURRENT_AGENCY_ID", StringUtils.isEmpty(agencyId) ? "" : agencyId);
		
		return "agency/agency_user_edit";
	}
	/**
	 * 数据列表。
	 * @param info
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.AGENCY_USER + ":" +Right.VIEW)
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<AgencyUserInfo> datagrid(AgencyUserInfo info){
		if(logger.isDebugEnabled())logger.debug("加载列表数据...");
		return this.agencyUserService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.AGENCY_USER + ":" +Right.UPDATE)
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(AgencyUserInfo info){
		if(logger.isDebugEnabled())logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.agencyUserService.update(info));
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
	@RequiresPermissions(ModuleConstant.AGENCY_USER + ":" +Right.DELETE)
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled())logger.debug("删除数据...");
		Json result = new Json();
		try {
			this.agencyUserService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 加载机构集合。
	 * @return
	 */
	@RequestMapping(value = "/agencies", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<AgencyInfo> loadAgencies(){
		if(logger.isDebugEnabled())logger.debug("加载机构集合...");
		List<AgencyInfo> list =  this.agencyUserService.loadAgencies(this.current_user_id);
		if(list == null || list.size() == 0) list = new ArrayList<>();
		return list;
	}
	/**
	 * 加载机构学员集合。
	 * @param agencyId
	 * 机构ID。
	 * @return
	 * 学员集合。
	 */
	@RequestMapping(value = "/students", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<UserInfo> loadStudentUsers(String agencyId){
		if(logger.isDebugEnabled())logger.debug("加载机构［agencyId = "+agencyId+"］学员集合...");
		return this.agencyUserService.loadUsers(agencyId, new Integer[]{ AgencyUser.IDENTITY_STUDENT, AgencyUser.IDENTITY_STUDENT});
	}
}