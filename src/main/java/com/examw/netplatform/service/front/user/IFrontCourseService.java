package com.examw.netplatform.service.front.user;

import java.util.List;

import com.examw.netplatform.domain.admin.students.Order;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.admin.courses.PackageInfo;
import com.examw.netplatform.model.front.FrontClassPlanInfo;

/**
 * 前台课程服务接口
 * @author fengwei.
 * @since 2015年1月22日 下午4:25:21.
 */
public interface IFrontCourseService {
	
	/**
	 * 查找用户套餐
	 * @param userId
	 * @return
	 */
	List<PackageInfo> findUserPackages(String userId);
	
	/**
	 * 查找用户班级
	 * @param userId
	 * @return
	 */
	List<ClassPlanInfo> findUserClassPlans(String userId);
	
	/**
	 * 查询班级信息
	 * @param userId
	 * @param classPlanId
	 * @return
	 */
	FrontClassPlanInfo findClassPlan(String userId,String classPlanId);
	
	/**
	 * 查询用户订单
	 * @param userId
	 * @return
	 */
	List<Order> findUserOrders(String userId);
}
