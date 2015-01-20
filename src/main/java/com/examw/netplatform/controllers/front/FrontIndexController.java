package com.examw.netplatform.controllers.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author fengwei.
 * @since 2015年1月19日 下午1:36:44.
 */
@Controller
@RequestMapping(value={"/"})
public class FrontIndexController {
	@RequestMapping(value = {"myCourse"}, method = RequestMethod.GET)
	public String myCourse()
	{
		return "my_courses";
	}
	
	@RequestMapping(value = {"courseDetail"}, method = RequestMethod.GET)
	public String courseDetail()
	{
		return "course_detail";
	}
}
