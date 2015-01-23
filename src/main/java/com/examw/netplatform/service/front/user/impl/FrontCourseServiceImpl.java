package com.examw.netplatform.service.front.user.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.students.IOrderDao;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.students.Order;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.admin.courses.PackageInfo;
import com.examw.netplatform.model.admin.students.OrderInfo;
import com.examw.netplatform.model.front.FrontClassPlanInfo;
import com.examw.netplatform.service.admin.courses.IClassPlanService;
import com.examw.netplatform.service.admin.courses.IPackageService;
import com.examw.netplatform.service.front.user.IFrontCourseService;
import com.examw.service.Status;

/**
 * 前台课程服务接口实现类
 * @author fengwei.
 * @since 2015年1月22日 下午4:33:37.
 */
public class FrontCourseServiceImpl implements IFrontCourseService {
	private static Logger logger = Logger.getLogger(FrontCourseServiceImpl.class);
	private IOrderDao orderDao;
	
	private IPackageService packageService;
	
	private IClassPlanService classPlanService;
	
	/**
	 * 设置 订单数据接口
	 * @param orderDao
	 * 
	 */
	public void setOrderDao(IOrderDao orderDao) {
		this.orderDao = orderDao;
	}

	/**
	 * 设置 套餐数据接口
	 * @param packageService
	 * 
	 */
	public void setPackageService(IPackageService packageService) {
		this.packageService = packageService;
	}

	/**
	 * 设置 班级数据接口
	 * @param classPlanService
	 * 
	 */
	public void setClassPlanService(IClassPlanService classPlanService) {
		this.classPlanService = classPlanService;
	}

	/*
	 * 查询用户套餐
	 * @see com.examw.netplatform.service.front.user.IFrontCourseService#findUserPackages(java.lang.String)
	 */
	@Override
	public List<PackageInfo> findUserPackages(String userId) {
		
		return null;
	}

	@Override
	public List<ClassPlanInfo> findUserClassPlans(String userId) {
		List<Order> orders = this.findUserOrders(userId);
		if(orders!=null&&orders.size()>0)
		{
			if(logger.isDebugEnabled()) logger.debug(String.format("查询用户[%s]的班级信息...",userId));
			List<ClassPlanInfo> result = new ArrayList<ClassPlanInfo>();
			for(Order order:orders)
			{
				if(order.getStatus().equals(Status.ENABLED.getValue()))
				{
					Set<ClassPlan> plans = order.getClasses();
					if(plans != null && plans.size() > 0)
					{
						for(ClassPlan plan:plans)
						{
							if(plan == null) continue;
							ClassPlanInfo info = this.classPlanService.conversion(plan);
							if(info!=null)
							{
								result.add(info);
							}
						}
					}
				}
			}
			return result;
		}
		return null;
	}

	@Override
	public FrontClassPlanInfo findClassPlan(String userId, String classPlanId) {
		return null;
	}
	
	@Override
	public List<Order> findUserOrders(final String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("查询用户[%s]的订单信息...",userId));
		if(StringUtils.isEmpty(userId))
			return null;
		return this.orderDao.findOrders(new OrderInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getStudentId(){ return userId;}
		});
	}
}
