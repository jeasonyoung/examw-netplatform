package com.examw.netplatform.controllers.admin.students;

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
import com.examw.netplatform.service.admin.students.IStudentService;
import com.examw.netplatform.support.EnumMapUtils;
import com.examw.netplatform.support.UserIdentityUtils;
import com.examw.service.Gender;
import com.examw.service.Status;
/**
 * 机构学员控制器
 * @author fengwei.
 * @since 2014年5月24日 上午11:13:21.
 */
@Controller
@RequestMapping(value = "/admin/students/user")
public class StudentController implements IUserAware {
	private static final Logger logger = Logger.getLogger(StudentController.class);
	private String current_user_id;
	//机构学员服务接口。
	@Resource
	private IStudentService studentService;
	//注入用户服务接口。
	@Resource
	private IUserService userService;
	//注入机构用户服务接口。
	@Resource
	private IAgencyUserService agencyUserService;
	/*
	 * 设置当前用户。
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
	public void setUserName(String userName) {}
	/*
	 * 设置当前用户昵称。
	 * @see com.examw.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String userNickName) {}
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_USER + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.STUDENTS_USER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.STUDENTS_USER + ":" + Right.DELETE);
		
		String current_agency_id = this.agencyUserService.loadAgencyIdByUser(this.current_user_id);
	    if(StringUtils.isEmpty(current_agency_id)){
	    	return "error/user_not_agency";
	    }
	    model.addAttribute("current_agency_id", current_agency_id);//当前机构ID
		
		return "students/student_user_list";
	}
	/**
	 * 获取编辑页面。
	 * @param agencyId
	 * 所属培训机构。
	 * @param model
	 * 数据绑定。
	 * @return
	 * 编辑页面地址。
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_USER + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit/{agencyId}", method = RequestMethod.GET)
	public String edit(@PathVariable String agencyId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("current_agency_id", agencyId);//当前机构ID
		//学生身份
		UserIdentityUtils.loadStudentIdentities(this.studentService, model);
		
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
		
		return "students/student_user_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_USER + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid/{agencyId}", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<AgencyUserInfo> datagrid(@PathVariable String agencyId, AgencyUserInfo info){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载机构［%s］学员用户集合...", agencyId));
		info.setAgencyId(agencyId);
		info.setType(UserType.FRONT.getValue());
		info.setIdentity(AgencyUserIdentity.STUDENT.getValue() | AgencyUserIdentity.CARDSTUDENT.getValue());
		return this.studentService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_USER + ":" + Right.UPDATE})
	@RequestMapping(value="/update/{agencyId}", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@PathVariable String agencyId, AgencyUserInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			info.setAgencyId(agencyId);
			result.setData(this.studentService.update(info));
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
	@RequiresPermissions({ModuleConstant.STUDENTS_USER + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据...",Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.studentService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}