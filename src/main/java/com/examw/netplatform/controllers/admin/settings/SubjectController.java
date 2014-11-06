package com.examw.netplatform.controllers.admin.settings;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.settings.AreaInfo;
import com.examw.netplatform.model.admin.settings.SubjectInfo;
import com.examw.netplatform.service.admin.settings.IExamService;
import com.examw.netplatform.service.admin.settings.ISubjectService;

/**
 * 科目控制器。
 * @author fengwei.
 * @since 2014-08-07.
 */
@Controller
@RequestMapping(value = "/admin/settings/subject")
public class SubjectController {
	private static Logger logger  = Logger.getLogger(SubjectController.class);
	//科目服务接口.
	@Resource
	private ISubjectService subjectService;
	//考试服务接口.
	@Resource
	private IExamService examService;
	/**
	 * 科目列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_SUBJECT + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载科目列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_SUBJECT + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_SUBJECT + ":" + Right.DELETE);
		return "settings/subject_list";
	}
	/**
	 * 科目编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_SUBJECT + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String categoryId,String examId,Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载科目［categoryId = %1$s  examId = %2$s］编辑页面...", categoryId,examId));
		model.addAttribute("current_category_id", categoryId);
		model.addAttribute("current_exam_id", examId);
		return "settings/subject_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_SUBJECT + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<SubjectInfo> datagrid(SubjectInfo info){
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.subjectService.datagrid(info);
	}
	/**
	 * 加载考试下的科目数据。
	 * @param examId
	 * 所属考试ID。
	 * @return
	 */
	@RequestMapping(value="/all", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<SubjectInfo> loadSubjects(String examId)
	{
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试［examId = %s］下的科目数据...", examId));
		return this.subjectService.loadAllSubjects(examId);
	}
	/**
	 * 加载考试科目下地区数据。
	 * @param subjectId
	 * 考试科目ID。
	 * @return
	 */
	@RequestMapping(value = {"/areas"}, method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<AreaInfo> loadSubjectAreas(String subjectId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试科目［subjectId = %s］下地区数据..", subjectId));
		return this.subjectService.loadSubjectAreas(subjectId);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_SUBJECT + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(SubjectInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.subjectService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新科目数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_SUBJECT + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据［id = %s］...", id));
		Json result = new Json();
		try {
			this.subjectService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 加载来源代码值。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_AREA + ":" + Right.VIEW})
	@RequestMapping(value="/code", method = RequestMethod.GET)
	@ResponseBody
	public Integer code(){
		if(logger.isDebugEnabled()) logger.debug("加载来源代码值...");
		Integer max = this.subjectService.loadMaxCode();
		if(max == null) max = 0;
		return max + 1;
	}
}