package com.examw.netplatform.controllers.front;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.exceptions.NotValidLessonException;
import com.examw.netplatform.model.admin.courses.LessonInfo;
import com.examw.netplatform.model.admin.students.LearningInfo;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo;
import com.examw.netplatform.model.front.FrontClassPlanInfo;
import com.examw.netplatform.model.front.FrontCourseInfo;
import com.examw.netplatform.model.front.FrontPackageInfo;
import com.examw.netplatform.service.front.user.IFrontCategoryService;
import com.examw.netplatform.service.front.user.IFrontCourseService;
import com.examw.netplatform.service.front.user.IFrontQuestionService;
import com.examw.service.Status;

/**
 * 前台控制器
 * 
 * @author fengwei.
 * @since 2015年1月19日 下午1:36:44.
 */
@Controller
@RequestMapping(value = { "/" })
public class FrontIndexController extends FrontBaseController {
	private static final Logger logger = Logger.getLogger(FrontIndexController.class);
	@Resource
	private IFrontCategoryService frontCategoryService;
	@Resource
	private IFrontCourseService frontCourseService;
	@Resource
	private IFrontQuestionService frontQuestionService;

	/**
	 * 我的课程
	 * 
	 * @param abbr
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/{abbr}/myCourse" }, method = RequestMethod.GET)
	public String myCourse(@PathVariable String abbr, HttpServletRequest request, Model model) {
		model.addAttribute("CATEGORYLIST", this.frontCategoryService.loadCategories(this.getAgency(abbr).getId()));
		model.addAttribute("CLASSPLANLIST", this.frontCourseService.findUserClassPlans(this.getUserId(request)));
		model.addAttribute("PACKAGELIST", this.frontCourseService.findUserPackages(this.getUserId(request)));
		return String.format("/%s/usercenter/my_courses", this.getTemplateDir(abbr));
	}

	@RequestMapping(value = { "/{abbr}/myQuestion" }, method = { RequestMethod.GET, RequestMethod.POST })
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
	 * 班级详情
	 * 
	 * @param abbr
	 * @param classId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/{abbr}/course/{classId}" }, method = RequestMethod.GET)
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
	@RequestMapping(value = { "/{abbr}/lesson/{classId}/{lessonId}" }, method = RequestMethod.GET)
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
	@RequestMapping(value = { "/{abbr}/lesson/learning/{lessonId}" }, method = RequestMethod.POST)
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
	@RequestMapping(value = { "/{abbr}/lesson/question/{lessonId}" }, method = RequestMethod.POST)
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

	@RequestMapping(value = { "/index/test/test" }, method = RequestMethod.GET)
	@ResponseBody
	public Object test() {
		return this.frontCategoryService.loadCategories("3eb90228-d7bb-44bc-9253-c0fa927e6796");
	}

	/**
	 * 课程列表页(默认排序)
	 * 
	 * @param abbr
	 * @param model
	 * @param info
	 * @return
	 */
	@RequestMapping(value = { "/index/{abbr}", "/index/{abbr}/default" }, method = RequestMethod.GET)
	public String courseList(@PathVariable String abbr, Model model, FrontCourseInfo info) {
		if (logger.isDebugEnabled())
			logger.debug("查询机构课程列表...");
		if (info == null)
			info = new FrontCourseInfo();
		if (info.getPage() == null)
			info.setPage(1);
		if (info.getRows() == null)
			info.setRows(5);
		info.setAgencyId(this.getAgency(abbr).getId());
		info.setStatus(Status.ENABLED.getValue()); // 启用状态
		info.setSort("orderNo"); // 排序
		info.setOrder("desc"); // 降序
		if (StringUtils.isEmpty(info.getCategoryId()) && StringUtils.isEmpty(info.getExamId())) {
			model.addAttribute("CATEGORYLIST", this.frontCategoryService.loadCategories(info.getAgencyId()));
		} else {
			if (StringUtils.isEmpty(info.getCategoryId())) {
				model.addAttribute("CURRENTEXAM", this.frontCategoryService.loadCategory(info.getAgencyId(), null, info.getExamId(), true));
			}
			model.addAttribute("CURRENTCATEGORY", this.frontCategoryService.loadCategory(info.getAgencyId(), info.getCategoryId(), info.getExamId(), false));
		}
		model.addAttribute("PACKAGELIST", this.frontCourseService.findAgencyPackages(info.toPackageInfo()));
		model.addAttribute("CLASSPLANLIST", this.frontCourseService.findAgencyClassPlans(info.toClassPlanInfo()));
		model.addAttribute("PACKAGETOTAL", this.frontCourseService.totalAgencyPackages(info.toPackageInfo()));
		model.addAttribute("CLASSPLANTOTAL", this.frontCourseService.totalAgencyClassPlans(info.toClassPlanInfo()));
		model.addAttribute("PAGE", info.getPage());
		model.addAttribute("CATEGORYID", info.getCategoryId());
		model.addAttribute("EXAMID", info.getExamId());
		model.addAttribute("FLAG", "default");
		// 推荐课程
		info.setPage(1);
		info.setRows(5);
		info.setCategoryId(null);
		info.setExamId(null);
		model.addAttribute("RECOMMENDPACKAGES", this.frontCourseService.findAgencyPackages(info.toPackageInfo()));
		model.addAttribute("RECOMMENDCLASSPLANS", this.frontCourseService.findAgencyClassPlans(info.toClassPlanInfo()));
		return String.format("/%s/index_list", this.getTemplateDir(abbr));
	}

	/**
	 * 课程列表页(热门课程)
	 * 
	 * @param abbr
	 * @param model
	 * @param info
	 * @return
	 */
	@RequestMapping(value = { "/index/{abbr}/hot" }, method = RequestMethod.GET)
	public String hotCourseList(@PathVariable String abbr, Model model, FrontCourseInfo info) {
		if (logger.isDebugEnabled())
			logger.debug("查询机构课程列表...");
		if (info == null)
			info = new FrontCourseInfo();
		if (info.getPage() == null)
			info.setPage(1);
		if (info.getRows() == null)
			info.setRows(5);
		info.setAgencyId(this.getAgency(abbr).getId());
		info.setStatus(Status.ENABLED.getValue()); // 启用状态
		if (StringUtils.isEmpty(info.getCategoryId()) && StringUtils.isEmpty(info.getExamId())) {
			model.addAttribute("CATEGORYLIST", this.frontCategoryService.loadCategories(info.getAgencyId()));
		} else {
			if (StringUtils.isEmpty(info.getCategoryId())) {
				model.addAttribute("CURRENTEXAM", this.frontCategoryService.loadCategory(info.getAgencyId(), null, info.getExamId(), true));
			}
			model.addAttribute("CURRENTCATEGORY", this.frontCategoryService.loadCategory(info.getAgencyId(), info.getCategoryId(), info.getExamId(), false));
		}
		model.addAttribute("PACKAGELIST", this.frontCourseService.findHotAgencyPackages(info.toPackageInfo()));
		model.addAttribute("CLASSPLANLIST", this.frontCourseService.findHotAgencyClassPlans(info.toClassPlanInfo()));
		model.addAttribute("PACKAGETOTAL", this.frontCourseService.totalAgencyPackages(info.toPackageInfo()));
		model.addAttribute("CLASSPLANTOTAL", this.frontCourseService.totalAgencyClassPlans(info.toClassPlanInfo()));
		model.addAttribute("PAGE", info.getPage());
		model.addAttribute("CATEGORYID", info.getCategoryId());
		model.addAttribute("EXAMID", info.getExamId());
		model.addAttribute("FLAG", "hot");
		// 推荐课程
		info.setPage(1);
		info.setRows(5);
		info.setSort("orderNo"); // 排序
		info.setOrder("desc"); // 降序
		info.setCategoryId(null);
		info.setExamId(null);
		model.addAttribute("RECOMMENDPACKAGES", this.frontCourseService.findAgencyPackages(info.toPackageInfo()));
		model.addAttribute("RECOMMENDCLASSPLANS", this.frontCourseService.findAgencyClassPlans(info.toClassPlanInfo()));
		return String.format("/%s/index_list", this.getTemplateDir(abbr));
	}

	/**
	 * 课程列表页(最新发布)
	 * 
	 * @param abbr
	 * @param model
	 * @param info
	 * @return
	 */
	@RequestMapping(value = { "/index/{abbr}/new" }, method = RequestMethod.GET)
	public String newCourseList(@PathVariable String abbr, Model model, FrontCourseInfo info) {
		if (logger.isDebugEnabled())
			logger.debug("查询机构课程列表...");
		if (info == null)
			info = new FrontCourseInfo();
		if (info.getPage() == null)
			info.setPage(1);
		if (info.getRows() == null)
			info.setRows(5);
		info.setAgencyId(this.getAgency(abbr).getId());
		info.setStatus(Status.ENABLED.getValue()); // 启用状态
		info.setSort("createTime"); // 排序
		info.setOrder("desc"); // 降序
		if (StringUtils.isEmpty(info.getCategoryId()) && StringUtils.isEmpty(info.getExamId())) {
			model.addAttribute("CATEGORYLIST", this.frontCategoryService.loadCategories(info.getAgencyId()));
		} else {
			if (StringUtils.isEmpty(info.getCategoryId())) {
				model.addAttribute("CURRENTEXAM", this.frontCategoryService.loadCategory(info.getAgencyId(), null, info.getExamId(), true));
			}
			model.addAttribute("CURRENTCATEGORY", this.frontCategoryService.loadCategory(info.getAgencyId(), info.getCategoryId(), info.getExamId(), false));
		}
		model.addAttribute("PACKAGELIST", this.frontCourseService.findAgencyPackages(info.toPackageInfo()));
		model.addAttribute("CLASSPLANLIST", this.frontCourseService.findAgencyClassPlans(info.toClassPlanInfo()));
		model.addAttribute("PACKAGETOTAL", this.frontCourseService.totalAgencyPackages(info.toPackageInfo()));
		model.addAttribute("CLASSPLANTOTAL", this.frontCourseService.totalAgencyClassPlans(info.toClassPlanInfo()));
		model.addAttribute("PAGE", info.getPage());
		model.addAttribute("CATEGORYID", info.getCategoryId());
		model.addAttribute("EXAMID", info.getExamId());
		model.addAttribute("FLAG", "new");
		// 推荐课程
		info.setPage(1);
		info.setRows(5);
		info.setSort("orderNo"); // 排序
		info.setOrder("desc"); // 降序
		info.setCategoryId(null);
		info.setExamId(null);
		model.addAttribute("RECOMMENDPACKAGES", this.frontCourseService.findAgencyPackages(info.toPackageInfo()));
		model.addAttribute("RECOMMENDCLASSPLANS", this.frontCourseService.findAgencyClassPlans(info.toClassPlanInfo()));
		return String.format("/%s/index_list", this.getTemplateDir(abbr));
	}

	@RequestMapping(value = { "/index/{abbr}/package/{packageId}" }, method = RequestMethod.GET)
	public String packageDetail(@PathVariable String abbr, @PathVariable String packageId, Model model) {
		// 查询具体的套餐
		FrontPackageInfo pack = this.frontCourseService.findFrontPackageInfo(packageId);
		model.addAttribute("PACKAGE", pack);
		if (pack != null) {
			model.addAttribute("CATEGORY", this.frontCategoryService.loadCategory(pack.getCategoryId()));
		}
		// 推荐课程
		FrontCourseInfo info = new FrontCourseInfo();
		info.setAgencyId(this.getAgency(abbr).getId());
		info.setPage(1);
		info.setRows(5);
		info.setSort("orderNo"); // 排序
		info.setOrder("desc"); // 降序
		info.setCategoryId(null);
		info.setExamId(null);
		model.addAttribute("RECOMMENDPACKAGES", this.frontCourseService.findAgencyPackages(info.toPackageInfo()));
		model.addAttribute("RECOMMENDCLASSPLANS", this.frontCourseService.findAgencyClassPlans(info.toClassPlanInfo()));
		return String.format("/%s/package_detail", this.getTemplateDir(abbr));
	}

	@RequestMapping(value = { "/index/{abbr}/class/{classId}" }, method = RequestMethod.GET)
	public String classPlanDetail(@PathVariable String abbr, @PathVariable String classId, Model model) {
		// 查询具体的班级
		FrontClassPlanInfo plan = this.frontCourseService.findFrontClassPlanInfo(classId);
		model.addAttribute("CLASSPLAN", plan);
		if (plan != null) {
			model.addAttribute("CATEGORY", this.frontCategoryService.loadCategory(plan.getCategoryId()));
		}
		// 推荐课程
		FrontCourseInfo info = new FrontCourseInfo();
		info.setAgencyId(this.getAgency(abbr).getId());
		info.setPage(1);
		info.setRows(5);
		info.setSort("orderNo"); // 排序
		info.setOrder("desc"); // 降序
		info.setCategoryId(null);
		info.setExamId(null);
		model.addAttribute("RECOMMENDPACKAGES", this.frontCourseService.findAgencyPackages(info.toPackageInfo()));
		model.addAttribute("RECOMMENDCLASSPLANS", this.frontCourseService.findAgencyClassPlans(info.toClassPlanInfo()));
		return String.format("/%s/classplan_detail", this.getTemplateDir(abbr));
	}
}
