package com.examw.netplatform.controllers.front;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.service.front.user.IFrontCategoryService;

/**
 * 
 * @author fengwei.
 * @since 2015年1月19日 下午1:36:44.
 */
@Controller
@RequestMapping(value={"/"})
public class FrontIndexController {
	@Resource
	private IFrontCategoryService frontCategoryService;
	
	/**
	 * 设置 前台考试分类服务接口
	 * @param frontCategoryService
	 * 
	 */
	public void setFrontCategoryService(IFrontCategoryService frontCategoryService) {
		this.frontCategoryService = frontCategoryService;
	}
	
	
	@RequestMapping(value = {"myCourse"}, method = RequestMethod.GET)
	public String myCourse(Model model)
	{
		model.addAttribute("CATEGORYLIST", this.frontCategoryService.loadCategories());
		return "usercenter/my_courses";
	}
	
	@RequestMapping(value = {"courseDetail"}, method = RequestMethod.GET)
	public String courseDetail()
	{
		return "usercenter/course_detail";
	}
}
