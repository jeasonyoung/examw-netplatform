package com.examw.netplatform.controllers.front;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.exceptions.NotValidLessonException;
import com.examw.netplatform.model.admin.courses.LessonInfo;
import com.examw.netplatform.model.admin.students.LearningInfo;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo;
import com.examw.netplatform.model.front.FrontUserInfo;
import com.examw.netplatform.service.front.user.IFrontCategoryService;
import com.examw.netplatform.service.front.user.IFrontCourseService;
import com.examw.netplatform.service.front.user.IFrontQuestionService;
import com.examw.netplatform.service.front.user.IFrontUserService;

/**
 * 前台用户接口
 * @author fengwei.
 * @since 2015年1月20日 下午4:04:21.
 */
@Controller
@RequestMapping(value="/{abbr}/user")
public class FrontUserController extends FrontBaseController{
	@Resource
	private IFrontUserService frontUserService;
	@Resource
	private IFrontCategoryService frontCategoryService;
	@Resource
	private IFrontCourseService frontCourseService;
	@Resource
	private IFrontQuestionService frontQuestionService;
	
	/**
	 * 登陆方法
	 * @return
	 */
	@RequestMapping(value = {"/login"}, method = RequestMethod.POST)
	public String login(String username,String password,HttpServletRequest request,Model model)
	{
		String abbr = "";
		try{
			AgencyUser user = this.frontUserService.login(username, password);
			abbr = user.getAgency().getAbbr_en();
			request.getSession().setAttribute("frontUser", user);
			Cookie[] cookies = request.getCookies();
			String lastPage = null;
		    if(cookies!=null){
		    	for(Cookie c:cookies){
		    		if("LastPage".equals(c.getName())){
		    			lastPage = c.getValue();
		    			break;
		    		}
		    	}
		    }
		    if(lastPage!=null) return String.format("redirect:%s",lastPage);
		}catch(Exception e)
		{
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("abbr", abbr);
			return "log";
		}
		return String.format("redirect:/%s/user/myCourse",abbr);
	}

	/**
	 * 注册
	 */
	@RequestMapping(value = {"/register"}, method = RequestMethod.POST)
	public String register(FrontUserInfo user,@PathVariable String abbr,HttpServletRequest request,Model model)
	{
		String template = this.getTemplateDir(abbr);
		return String.format("redirect:/%s/login",abbr);
	}
	
	/**
	 * 用户信息页面
	 * @param abbr
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/info"}, method = RequestMethod.GET)
	public String userInfoPage(@PathVariable String abbr,Model model)
	{
		return String.format("/%s/usercenter/user_info",this.getTemplateDir(abbr));
	}
	/**
	 * 修改用户信息
	 * @param info
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/modifyInfo"}, method = RequestMethod.POST)
	@ResponseBody
	public Json modifyInfo(FrontUserInfo info,HttpServletRequest request){
		Json json = new Json();
		try{
			AgencyUser user = this.getAgencyUser(request);
			info.setId(user.getUser().getId());
			if(info.checkLittle())
			{
				User newUser = (this.frontUserService.updateInfo(info));
				user.setUser(newUser);
				json.setSuccess(true);
			}
		}catch(Exception e)
		{
			json.setMsg(e.getMessage());
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 修改密码
	 * @param oldPwd
	 * @param newPwd
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/modifyPwd"}, method = RequestMethod.POST)
	@ResponseBody
	public Json modifyInfo(String oldPwd,String newPwd,HttpServletRequest request){
		Json json = new Json();
		try{
			AgencyUser user = this.getAgencyUser(request);
			this.frontUserService.updatePwd(user.getUser().getId(),oldPwd,newPwd);
			json.setSuccess(true);
		}catch(Exception e)
		{
			json.setMsg(e.getMessage());
			json.setSuccess(false);
		}
		return json;
	}
	
	
	/**
	 * 我的课程
	 * 
	 * @param abbr
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/myCourse" }, method = RequestMethod.GET)
	public String myCourse(@PathVariable String abbr, HttpServletRequest request, Model model) {
		model.addAttribute("CATEGORYLIST", this.frontCategoryService.loadCategories(this.getAgency(abbr).getId()));
		model.addAttribute("CLASSPLANLIST", this.frontCourseService.findUserClassPlans(this.getUserId(request)));
		model.addAttribute("PACKAGELIST", this.frontCourseService.findUserPackages(this.getUserId(request)));
		return String.format("/%s/usercenter/my_courses", this.getTemplateDir(abbr));
	}
	/**
	 * 我的问题
	 * @param abbr
	 * @param info
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/myQuestion" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String myQuestion(@PathVariable String abbr, LessonInfo info, HttpServletRequest request, Model model) {
		String userId = this.getUserId(request);
		if (info == null) {
			info = new LessonInfo();
			info.setPage(1);
			info.setRows(10);
		}
		if (info.getRows() == null)
			info.setRows(10);
		if (info.getPage() == null)
			info.setPage(1);
		model.addAttribute("LESSONLIST", this.frontQuestionService.findQuestionLessonList(info, userId));
		model.addAttribute("TOTAL", this.frontQuestionService.findQuestionLessonTotal(userId));
		// 页码
		model.addAttribute("PAGE", info.getPage());
		return String.format("/%s/usercenter/my_questions", this.getTemplateDir(abbr));
	}

	/**
	 * 班级详情[带用户信息]
	 * 
	 * @param abbr
	 * @param classId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/course/{classId}" }, method = RequestMethod.GET)
	public String courseDetail(@PathVariable String abbr, @PathVariable String classId, HttpServletRequest request, Model model) {
		model.addAttribute("CLASSPLAN", this.frontCourseService.findClassPlan(this.getUserId(request), classId));
		return String.format("/%s/usercenter/course_detail", this.getTemplateDir(abbr));
	}

	/**
	 * 课时详情
	 * 
	 * @param abbr
	 * @param classId
	 * @param lessonId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/lesson/{classId}/{lessonId}" }, method = RequestMethod.GET)
	public String lessonDetail(@PathVariable String abbr, @PathVariable String classId, @PathVariable String lessonId, HttpServletRequest request, Model model) {
		// 判断订单里是否有该课程,是否过期
		try {
			this.frontCourseService.findLessonInfo(((AgencyUser) (request.getSession().getAttribute("frontUser"))), classId, lessonId, model.asMap());
			model.addAttribute("QUESTIONLIST", this.frontQuestionService.findUserLessonQuestions(this.getUserId(request), lessonId));
		} catch (NotValidLessonException e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			return "not_valid_lesson";
		}
		return String.format("/%s/usercenter/video", this.getTemplateDir(abbr));
	}

	/**
	 * 添加学习进度
	 * 
	 * @param abbr
	 * @param lessonId
	 * @param info
	 * @return
	 */
	@RequestMapping(value = { "/lesson/learning/{lessonId}" }, method = RequestMethod.POST)
	@ResponseBody
	public Json learning(@PathVariable String abbr, @PathVariable String lessonId, LearningInfo info) {
		Json json = new Json();
		json.setSuccess(this.frontCourseService.saveLearningRecord(info));
		return json;
	}

	/**
	 * 保存提问
	 * 
	 * @param abbr
	 * @param lessonId
	 * @param request
	 * @param info
	 * @return
	 */
	@RequestMapping(value = { "/lesson/question/{lessonId}" }, method = RequestMethod.POST)
	@ResponseBody
	public Json learning(@PathVariable String abbr, @PathVariable String lessonId, HttpServletRequest request, AnswerQuestionTopicInfo info) {
		Json json = new Json();
		try {
			AgencyUser user = (AgencyUser) request.getSession().getAttribute("frontUser");
			info.setUserId(user.getUser().getId());
			info.setAgencyId(user.getAgency().getId());
			json.setData(this.frontCourseService.saveQuestionTopic(info));
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

}
