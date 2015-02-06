package com.examw.netplatform.service.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.front.FrontClassPlanInfo;
import com.examw.netplatform.service.front.IFrontCourseService;
/**
 * 
 * @author fengwei.
 * @since 2015年2月4日 上午10:20:42.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-netplatform.xml" })
public class FrontTest {
	
	@Resource
	private IFrontCourseService frontCourseService;
	
	@Test
	@Transactional
	public void testCategory()
	{
		ClassPlanInfo info = new ClassPlanInfo();
		info.setAgencyId("3eb90228-d7bb-44bc-9253-c0fa927e6796");
		//info.setCategoryId("005c732f-d392-44b1-9533-9bd788f958cf");
		List<FrontClassPlanInfo> list = frontCourseService.findHotAgencyClassPlans(info);
		System.out.println(frontCourseService.totalAgencyClassPlans(info));
		System.out.println(list.size());
	}
}
