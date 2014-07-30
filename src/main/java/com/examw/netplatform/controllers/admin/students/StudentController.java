package com.examw.netplatform.controllers.admin.students;

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
import com.examw.netplatform.domain.admin.security.Right;  
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.model.admin.students.StudentInfo;
import com.examw.netplatform.service.admin.security.IUserService;
import com.examw.netplatform.service.admin.students.IStudentService;
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
	//用户服务接口。
	@Resource
	private IUserService userService;
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
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String agencyId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("STATUS_ENABLED", this.userService.loadUserStatusName(User.STATUS_ENABLED));
		model.addAttribute("STATUS_DISABLE", this.userService.loadUserStatusName(User.STATUS_DISABLE));
		
		model.addAttribute("GENDER_MALE", this.userService.loadGenderName(User.GENDER_MALE));
		model.addAttribute("GENDER_FEMALE", this.userService.loadGenderName(User.GENDER_FEMALE));
		
		model.addAttribute("CURRENT_AGENCY_ID", StringUtils.isEmpty(agencyId) ? "" : agencyId);
		return "students/student_user_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_USER + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<StudentInfo> datagrid(StudentInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据［current_user_id = "+this.current_user_id +"］...");
		info.setCurrentUserId(this.current_user_id);
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
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(StudentInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
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
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		Json result = new Json();
		try {
			this.studentService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}