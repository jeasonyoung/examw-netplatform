package com.examw.netplatform.controllers.front;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.model.admin.students.LearningInfo;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo;
import com.examw.netplatform.service.front.user.IFrontCategoryService;
import com.examw.netplatform.service.front.user.IFrontCourseService;

/**
 * 
 * @author fengwei.
 * @since 2015年1月19日 下午1:36:44.
 */
@Controller
@RequestMapping(value={"/"})
public class FrontIndexController extends FrontBaseController{
	@Resource
	private IFrontCategoryService frontCategoryService;
	@Resource
	private IFrontCourseService frontCourseService;
	
	/**
	 * 设置 前台考试分类服务接口
	 * @param frontCategoryService
	 * 
	 */
	public void setFrontCategoryService(IFrontCategoryService frontCategoryService) {
		this.frontCategoryService = frontCategoryService;
	}
	
	/**
	 * 我的课程
	 * @param abbr
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/{abbr}/myCourse"}, method = RequestMethod.GET)
	public String myCourse(@PathVariable String abbr,HttpServletRequest request,Model model)
	{
		model.addAttribute("CATEGORYLIST", this.frontCategoryService.loadCategories());
		model.addAttribute("CLASSPLANLIST", this.frontCourseService.findUserClassPlans(this.getUserId(request)));
		model.addAttribute("PACKAGELIST", this.frontCourseService.findUserPackages(this.getUserId(request)));
		return String.format("/%s/usercenter/my_courses",this.getTemplateDir(abbr));
	}
	/**
	 * 班级详情
	 * @param abbr
	 * @param classId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/{abbr}/course/{classId}"}, method = RequestMethod.GET)
	public String courseDetail(@PathVariable String abbr,@PathVariable String classId,HttpServletRequest request,Model model)
	{
		model.addAttribute("CLASSPLAN", this.frontCourseService.findClassPlan(this.getUserId(request),classId));
		return String.format("/%s/usercenter/course_detail",this.getTemplateDir(abbr));
	}
	/**
	 * 课时详情
	 * @param abbr
	 * @param classId
	 * @param lessonId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/{abbr}/lesson/{classId}/{lessonId}"}, method = RequestMethod.GET)
	public String lessonDetail(@PathVariable String abbr,@PathVariable String classId,@PathVariable String lessonId,HttpServletRequest request,Model model)
	{
		this.frontCourseService.findLessonInfo(((AgencyUser)(request.getSession().getAttribute("frontUser"))),classId,lessonId,model.asMap());
		return String.format("/%s/usercenter/video",this.getTemplateDir(abbr));
	}
	/**
	 * 添加学习进度
	 * @param abbr
	 * @param lessonId
	 * @param info
	 * @return
	 */
	@RequestMapping(value = {"/{abbr}/lesson/learning/{lessonId}"}, method = RequestMethod.POST)
	@ResponseBody
	public Json learning(@PathVariable String abbr,@PathVariable String lessonId,LearningInfo info)
	{
		Json json = new Json();
		json.setSuccess(this.frontCourseService.saveLearningRecord(info));
		return json;
	}
	
	@RequestMapping(value = {"/{abbr}/lesson/question/{lessonId}"}, method = RequestMethod.POST)
	@ResponseBody
	public Json learning(@PathVariable String abbr,@PathVariable String lessonId,HttpServletRequest request,AnswerQuestionTopicInfo info)
	{
		Json json = new Json();
		try{
			AgencyUser user = (AgencyUser)request.getSession().getAttribute("frontUser");
			info.setUserId(user.getUser().getId());
			info.setAgencyId(user.getAgency().getId());
			json.setData(this.frontCourseService.saveQuestionTopic(info));
			json.setSuccess(true);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return json;
	}
	/**
	 * 获取用户的ID
	 * @param request
	 * @return
	 */
	private String getUserId(HttpServletRequest request)
	{
		AgencyUser user = (AgencyUser) request.getSession().getAttribute("frontUser");
		if(user==null) return null;
		return user.getUser().getId();
	}
}
