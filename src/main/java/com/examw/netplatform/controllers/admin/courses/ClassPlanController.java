package com.examw.netplatform.controllers.admin.courses;

import java.util.List;

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
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.service.admin.courses.IClassPlanService;
/**
 * 开班计划控制器
 * @author fengwei
 * 2014年5月20日 下午9:52:06
 */
@Controller
@RequestMapping(value = "/admin/courses/classplan")
public class ClassPlanController implements IUserAware {
	private static final Logger logger  = Logger.getLogger(ClassPlanController.class);
	private String current_user_id;
	//开班计划服务接口。
	//@Resource
	private IClassPlanService classPlanService;
	//考试服务接口。
	//@Resource
	//private IExamService examService;
	//科目服务接口。
	//@Resource
	//private ISubjectService subjectService;
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
	public void setUserName(String userName) {}
	/*
	 * 设置当前用户昵称。
	 * @see com.examw.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String userNickName){}
	/**
	 * 开班计划列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PLAN + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.COURSES_PLAN + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.COURSES_PLAN + ":" + Right.DELETE);
		return "courses/classplan_list";
	}
	/**
	 * 开班计划编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PLAN + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String catalogId,String examId,String subjectId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.COURSES_PLAN + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.COURSES_PLAN + ":" + Right.DELETE);
		
		if(!StringUtils.isEmpty(subjectId)){
//			 ExamInfo examInfo =	 this.subjectService.loadExam(subjectId);
//			  if(examInfo != null){
////				  catalogId = examInfo.getCatalogId();
////				  examId = examInfo.getId();
//			  }
		}else if(!StringUtils.isEmpty(examId)){
//			 CatalogInfo catalogInfo = this.examService.loadCatalog(examId);
//			 if(catalogInfo != null){
//				 catalogId = catalogInfo.getId();
//			 }
		}
		
		model.addAttribute("CURRENT_CATALOG_ID", StringUtils.isEmpty(catalogId) ? "" : catalogId);
		model.addAttribute("CURRENT_EXAM_ID", StringUtils.isEmpty(examId) ? "" : examId);
		model.addAttribute("CURRENT_SUBJECT_ID", StringUtils.isEmpty(subjectId) ? "" : subjectId);
		
//		model.addAttribute("STATUS_ENABLE_VALUE", ClassPlan.STATUS_ENABLE);
//		model.addAttribute("STATUS_ENABLE_NAME", this.classPlanService.loadStatusName(ClassPlan.STATUS_ENABLE));
//		model.addAttribute("STATUS_DISABLE_VALUE", ClassPlan.STATUS_DISABLE);
//		model.addAttribute("STATUS_DISABLE_NAME", this.classPlanService.loadStatusName(ClassPlan.STATUS_DISABLE));
//		
//		model.addAttribute("HANDOUT_MODE_DOWNLOAD_VALUE", ClassPlan.HANDOUT_MODE_DOWNLOAD);
//		model.addAttribute("HANDOUT_MODE_DOWNLOAD_NAME", this.classPlanService.loadHandoutModeName(ClassPlan.HANDOUT_MODE_DOWNLOAD));
//		model.addAttribute("HANDOUT_MODE_ONLINE_VALUE", ClassPlan.HANDOUT_MODE_ONLINE);
//		model.addAttribute("HANDOUT_MODE_ONLINE_NAME", this.classPlanService.loadHandoutModeName(ClassPlan.HANDOUT_MODE_ONLINE));
//		
//		model.addAttribute("VIDEO_NOTFREE_VALUE", ClassPlan.VIDEO_NOTFREE);
//		model.addAttribute("VIDEO_NOTFREE_NAME", this.classPlanService.loadVideoModeName(ClassPlan.VIDEO_NOTFREE));
//		model.addAttribute("VIDEO_FREE_VALUE", ClassPlan.VIDEO_FREE);
//		model.addAttribute("VIDEO_FREE_NAME", this.classPlanService.loadVideoModeName(ClassPlan.VIDEO_FREE));
//		
		return "courses/classplan_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PLAN + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ClassPlanInfo> datagrid(ClassPlanInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		info.setCurrentUserId(this.current_user_id);
		return this.classPlanService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.COURSES_PLAN + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(ClassPlanInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			if(info.getStartTime().getTime() - info.getEndTime().getTime() > 0){
				result.setSuccess(false);
				result.setMsg("开班时间必须早于结束时间！");
				return result;
			}
			result.setData(this.classPlanService.update(info));
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
	@RequiresPermissions({ModuleConstant.COURSES_PLAN + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		Json result = new Json();
		try {
			this.classPlanService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 加载当前用户下全部班级。
	 * @return
	 */
	@RequestMapping(value = "/all", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<ClassPlanInfo> all(){
		if(logger.isDebugEnabled()) logger.debug("加载当前用户［"+ this.current_user_id +"］下全部班级...");
		final String currentUserID = this.current_user_id;
		return this.classPlanService.datagrid(new ClassPlanInfo(){
			private static final long serialVersionUID = 1L; 
			@Override
			public String getCurrentUserId(){ return currentUserID;}
			@Override
			public String getSort(){return "startTime";}
			@Override
			public String getOrder(){ return "desc";}
			@Override
			public Integer getPage(){ return null;}
			@Override
			public Integer getRows(){return null;}
		}).getRows();
	}
//	/**
//	 * 加载机构下数据。
//	 * @param agencyId
//	 * @param catalogId
//	 * @param examId
//	 * @return
//	 */
//	@RequestMapping(value = "/all/{agencyId}", method = {RequestMethod.GET, RequestMethod.POST})
//	@ResponseBody
//	public List<ClassPlanInfo> all(@PathVariable String agencyId, String catalogId, String examId,String className){
//		if(logger.isDebugEnabled()) logger.debug("加载机构［"+ agencyId +"］下数据...");
//		return this.classPlanService.findClassPlans(agencyId, catalogId, examId,className);
//	}
}