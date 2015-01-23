package com.examw.netplatform.controllers.front;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.domain.admin.settings.AgencyUser;
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
	
	
	@RequestMapping(value = {"/{abbr}/myCourse"}, method = RequestMethod.GET)
	public String myCourse(@PathVariable String abbr,HttpServletRequest request,Model model)
	{
		model.addAttribute("CATEGORYLIST", this.frontCategoryService.loadCategories());
		model.addAttribute("CLASSPLANLIST", this.frontCourseService.findUserClassPlans(this.getUserId(request)));
		return String.format("/%s/usercenter/my_courses",this.getTemplateDir(abbr));
	}
	
	@RequestMapping(value = {"/{abbr}/courseDetail"}, method = RequestMethod.GET)
	public String courseDetail(@PathVariable String abbr)
	{
		return String.format("/%s/usercenter/course_detail",this.getTemplateDir(abbr));
	}
	
	private String getUserId(HttpServletRequest request)
	{
		AgencyUser user = (AgencyUser) request.getSession().getAttribute("frontUser");
		if(user==null) return null;
		return user.getUser().getId();
	}
}
