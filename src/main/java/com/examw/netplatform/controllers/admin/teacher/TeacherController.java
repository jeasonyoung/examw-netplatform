package com.examw.netplatform.controllers.admin.teacher;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.model.TreeNode;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.model.admin.agency.AgencyInfo;
import com.examw.netplatform.model.admin.teacher.TeacherInfo;
import com.examw.netplatform.service.admin.agency.IAgencyService;
import com.examw.netplatform.service.admin.teacher.ITeacherService;
/**
 * 教师控制器
 * @author fengwei.
 * @since 2014年5月29日 下午4:44:59.
 */
@Controller
@RequestMapping("/admin/teacher/user")
public class TeacherController {
	private static Logger logger = Logger.getLogger(TeacherController.class);
	@Resource
	private ITeacherService teacherService;
	@Resource
	private IAgencyService agencyService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHER_USER + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("PER_UPDATE", ModuleConstant.TEACHER_USER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.TEACHER_USER + ":" + Right.DELETE);
		
		model.addAttribute("STATUS_ENABLED", this.teacherService.loadUserStatusName(User.STATUS_ENABLED));
		model.addAttribute("STATUS_DISABLE", this.teacherService.loadUserStatusName(User.STATUS_DISABLE));
		return "teacher/teacher_list";
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
	@RequiresPermissions({ModuleConstant.TEACHER_USER + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model){
		
		model.addAttribute("STATUS_ENABLED", this.teacherService.loadUserStatusName(User.STATUS_ENABLED));
		model.addAttribute("STATUS_DISABLE", this.teacherService.loadUserStatusName(User.STATUS_DISABLE));
		
		model.addAttribute("GENDER_MALE", this.teacherService.loadGenderName(User.GENDER_MALE));
		model.addAttribute("GENDER_FEMALE", this.teacherService.loadGenderName(User.GENDER_FEMALE));
		
		//model.addAttribute("USER_TYPE_AGENCY", User.USER_TYPE_AGENCY);
		
		DataGrid<AgencyInfo> agencies =this.agencyService.datagrid(new AgencyInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getPage(){return null;}
			@Override
			public Integer getRows(){return null;}
			@Override
			public String getSort(){
				return "name";
			}
			@Override
			public String getOrder() {
				return "asc";
			}
		});
		model.addAttribute("agencies", agencies.getRows());
		
		return "teacher/teacher_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHER_USER + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<TeacherInfo> datagrid(TeacherInfo info){
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
	public Json update(TeacherInfo info, HttpServletRequest request){
		Json result = new Json();
		try {
			// info.setLastLoginIP(request.getRemoteAddr());
			 result.setData(this.teacherService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新用户数据发生异常", e);
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
	public Json delete(String id,String agencyId){
		Json result = new Json();
		try {
			this.teacherService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 选择班级
	 * @param agencyId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHER_USER + ":" + Right.VIEW})
	@RequestMapping(value="/chooseclass", method = RequestMethod.GET)
	public String choosepackage(String agencyId,Model model){
		model.addAttribute("agencyId", agencyId);
		return "student/student_choose_package";
	}
	/**
	 * 教师树。
	 * @return
	 */
	@RequestMapping(value = "/teacher-tree", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<TreeNode> allCatalogExams(String agencyId){
		return this.teacherService.loadAllTeachers(agencyId);
	}
}
