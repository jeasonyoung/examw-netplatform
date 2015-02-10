package com.examw.netplatform.controllers.front;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.model.front.FrontClassPlanInfo;
import com.examw.netplatform.model.front.FrontCourseInfo;
import com.examw.netplatform.model.front.FrontPackageInfo;
import com.examw.netplatform.service.front.IFrontCategoryService;
import com.examw.netplatform.service.front.IFrontCourseService;
import com.examw.netplatform.service.front.IFrontQuestionService;
import com.examw.netplatform.service.front.IFrontUserService;
import com.examw.service.Status;

/**
 * 前台控制器
 * 
 * @author fengwei.
 * @since 2015年1月19日 下午1:36:44.
 */
@Controller
@RequestMapping(value = { "/{abbr}" })
public class FrontIndexController extends FrontBaseController {
	private static final Logger logger = Logger.getLogger(FrontIndexController.class);
	@Resource
	private IFrontUserService frontUserService;
	@Resource
	private IFrontCategoryService frontCategoryService;
	@Resource
	private IFrontCourseService frontCourseService;
	@Resource
	private IFrontQuestionService frontQuestionService;
	
	
	/**
	 * 登陆页面
	 * @return
	 */
	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String loginPage(@PathVariable String abbr,Model model)
	{
		return "log";
	}
	/**
	 * 登陆方法
	 * @return
	 */
	@RequestMapping(value = {"/login"}, method = RequestMethod.POST)
	public String login(@PathVariable String abbr,String username,String password,HttpServletRequest request,Model model)
	{
		try{
			AgencyUser user = this.frontUserService.login(username, password);
			abbr = user.getAgency().getAbbr_en();
			request.getSession().setAttribute("frontUser", user);
			Cookie[] cookies = request.getCookies();
			Cookie lastPage = null;
		    if(cookies!=null){
		    	for(Cookie c:cookies){
		    		if("LastPage".equals(c.getName())){
		    			lastPage = c;
		    			break;
		    		}
		    	}
		    }
		    if(lastPage!=null){
		    	lastPage.setMaxAge(0); //删除该Cookie
		    	return String.format("redirect:%s",lastPage.getValue());
		    }
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
	 * 注册页面
	 * @return
	 */
	@RequestMapping(value = {"/{abbr}/register"}, method = RequestMethod.GET)
	public String regPage(@PathVariable String abbr,Model model)
	{
		model.addAttribute("abbr",abbr);
		return String.format("/%s/reg",this.getTemplateDir(abbr));
	}
	
	/**
	 * 退出
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/logout"}, method = {RequestMethod.GET,RequestMethod.POST})
	public String logout(@PathVariable String abbr,HttpServletRequest request,Model model)
	{
		request.getSession().removeAttribute("frontUser");
		return String.format("redirect:/%s/login",abbr);
	}
	
	
	@RequestMapping(value = { "/index/test/test" }, method = RequestMethod.GET)
	@ResponseBody
	public Object test() {
		return this.frontCourseService.findFrontClassPlanInfo("92387c06-7df0-4dd1-bd4a-e9d83eace0f0");
	}

	/**
	 * 课程列表页(默认排序)
	 * 
	 * @param abbr
	 * @param model
	 * @param info
	 * @return
	 */
	@RequestMapping(value = {"", "/course", "/course/default" }, method = RequestMethod.GET)
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
	@RequestMapping(value = { "/course/hot" }, method = RequestMethod.GET)
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
	@RequestMapping(value = { "/course/new" }, method = RequestMethod.GET)
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

	@RequestMapping(value = { "/package/{packageId}" }, method = RequestMethod.GET)
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

	@RequestMapping(value = { "/class/{classId}" }, method = RequestMethod.GET)
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
