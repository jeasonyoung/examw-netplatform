package com.examw.netplatform.service.admin.students.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.courses.ClassMapper;
import com.examw.netplatform.dao.admin.courses.PackageMapper;
import com.examw.netplatform.dao.admin.security.UserMapper;
import com.examw.netplatform.dao.admin.settings.AgencyMapper;
import com.examw.netplatform.dao.admin.students.OrderMapper;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.courses.Package;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.domain.admin.students.Order;
import com.examw.netplatform.model.admin.students.OrderInfo;
import com.examw.netplatform.service.admin.students.IOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 订单服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年12月1日
 */
public class OrderServiceImpl implements IOrderService {
	private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);
	private OrderMapper orderDao;
	private AgencyMapper agencyDao;
	private UserMapper userDao;
	private PackageMapper packageDao;
	private ClassMapper classDao;
	private Map<Integer, String> sourceMap,statusMap;
	/**
	 * 设置订单数据接口。
	 * @param orderDao 
	 *	  订单数据接口。
	 */
	public void setOrderDao(OrderMapper orderDao) {
		logger.debug("注入订单数据接口...");
		this.orderDao = orderDao;
	}
	/**
	 * 设置培训机构数据接口。
	 * @param agencyDao 
	 *	  培训机构数据接口。
	 */
	public void setAgencyDao(AgencyMapper agencyDao) {
		logger.debug("注入培训机构数据接口...");
		this.agencyDao = agencyDao;
	}
	/**
	 * 设置用户数据接口。
	 * @param userDao 
	 *	  用户数据接口。
	 */
	public void setUserDao(UserMapper userDao) {
		logger.debug("注入用户数据接口...");
		this.userDao = userDao;
	}
	/**
	 * 设置套餐数据接口。
	 * @param packageDao 
	 *	  套餐数据接口。
	 */
	public void setPackageDao(PackageMapper packageDao) {
		logger.debug("注入套餐数据接口...");
		this.packageDao = packageDao;
	}
	/**
	 * 设置班级数据接口。
	 * @param classPlanDao 
	 *	  班级数据接口。
	 */
	public void setClassDao(ClassMapper classDao) {
		logger.debug("注入班级数据接口...");
		this.classDao = classDao;
	}
	/**
	 * 设置订单来源值名称集合。
	 * @param sourceMap 
	 *	  订单来源值名称集合。
	 */
	public void setSourceMap(Map<Integer, String> sourceMap) {
		logger.debug("注入订单来源值名称集合...");
		this.sourceMap = sourceMap;
	}
	/**
	 * 设置订单状态值名称集合。
	 * @param statusMap 
	 *	  订单状态值名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		logger.debug("注入订单状态值名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载订单来源值名称。
	 * @see com.examw.netplatform.service.admin.students.IOrderService#loadSourceName(java.lang.Integer)
	 */
	@Override
	public String loadSourceName(Integer source) {
		logger.debug(String.format("加载订单来源值［%d］名称...", source));
		if(source == null || this.sourceMap == null || this.sourceMap.size() == 0) return null;
		return this.sourceMap.get(source);
	}
	/*
	 * 加载状态名称值名称。
	 * @see com.examw.netplatform.service.admin.students.IOrderService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		logger.debug(String.format("加载状态名称值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 创建机构订单号码。
	 * @see com.examw.netplatform.service.admin.students.IOrderService#createOrderNumber(java.lang.String)
	 */
	@Override
	public String createOrderNumber(String agencyId) {
		logger.debug(String.format("创建机构［%s］订单号码...", agencyId));
		if(StringUtils.isBlank(agencyId)) return null;
		final Agency agency = this.agencyDao.getAgency(agencyId);
		if(agency == null) throw new RuntimeException(String.format("培训机构［%s］不存在！", agencyId));
		final int total = this.orderDao.getOrderTotal(agencyId);		 
		String en = agency.getAbbrEN();
		if(!StringUtils.isEmpty(en)){
			en = en.toUpperCase();
		}
		return String.format("%1$s-%2$X-%3$02d", en, new Date().getTime(), total + 1);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.students.IOrderService#datagrid(com.examw.netplatform.model.admin.students.OrderInfo)
	 */
	@Override
	public DataGrid<OrderInfo> datagrid(OrderInfo info) {
		logger.debug("查询数据...");
		//分页排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getSort()) + " " + StringUtils.trimToEmpty(info.getOrder()));
		//查询数据
		final List<Order> list = this.orderDao.findOrders(info);
		//分页信息
		final PageInfo<Order> pageInfo = new PageInfo<Order>(list);
		//初始化
		final DataGrid<OrderInfo> grid = new DataGrid<OrderInfo>();
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//批量类型转换
	private List<OrderInfo> changeModel(List<Order> orders){
		final List<OrderInfo> list = new ArrayList<OrderInfo>();
		if(orders != null && orders.size() > 0){
			for(Order order : orders){
				if(order == null) continue;
				list.add(this.conversion(order));
			}
		}
		return list;
	}
	//数据类型转换
	private OrderInfo conversion(Order data){
		logger.debug("数据类型转换[Order -> OrderInfo]...");
		if(data != null){
			final OrderInfo info = new OrderInfo();
			BeanUtils.copyProperties(data, info);
			//机构
			if(StringUtils.isNotBlank(info.getAgencyId()) && StringUtils.isBlank(info.getAgencyName())){
				final Agency agency = this.agencyDao.getAgency(info.getAgencyId());
				if(agency != null) info.setAgencyName(agency.getName());
			}
			//用户
			if(StringUtils.isNotBlank(info.getUserId()) && StringUtils.isBlank(info.getUserName())){
				final User user = this.userDao.getUser(info.getUserId());
				if(user != null) info.setUserName(user.getName());
			}
			//订单学员
			final List<String> stuIds = new ArrayList<String>(), stuNames = new ArrayList<String>();
			final List<User> students = this.userDao.findUsersByOrder(info.getId());
			if(students != null && students.size() > 0){
				for(User user : students){
					if(user == null) continue;
					stuIds.add(user.getId());
					stuNames.add(user.getName());
				}
			}
			info.setStudentIds(stuIds.toArray(new String[0]));
			info.setStudentNames(stuNames.toArray(new String[0]));
			//订单班级
			final List<String> classIds = new ArrayList<String>(), classNames = new ArrayList<String>();
			final List<ClassPlan> classPlans = this.classDao.findClassByOrder(info.getId());
			if(classPlans != null && classPlans.size() > 0){
				for(ClassPlan cp : classPlans){
					if(cp == null) continue;
					classIds.add(cp.getId());
					classNames.add(cp.getName());
				}
			}
			info.setClassIds(classIds.toArray(new String[0]));
			info.setClassNames(classNames.toArray(new String[0]));
			//订单套餐
			final List<String> packageIds = new ArrayList<String>(), packageNames = new ArrayList<String>();
			final List<Package> packages = this.packageDao.findPackagesByOrder(info.getId());
			if(packages != null && packages.size() > 0){
				for(Package p : packages){
					if(p == null) continue;
					packageIds.add(p.getId());
					packageNames.add(p.getName());
				}
			}
			info.setPackageIds(packageIds.toArray(new String[0]));
			info.setPackageNames(packageNames.toArray(new String[0]));
			//来源
			info.setSourceName(this.loadSourceName(data.getSource()));
			//状态
			info.setStatusName(this.loadStatusName(data.getStatus()));
			//返回
			return info;
		}
		return null;
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.admin.students.IOrderService#update(com.examw.netplatform.model.admin.students.OrderInfo)
	 */
	@Override
	public OrderInfo update(OrderInfo info) {
		logger.debug("更新数据...");
		if(info == null) return null;
		//检查订单号码
		if(StringUtils.isNotBlank(info.getNumber()) && this.orderDao.hasOrderNumber(info.getNumber())){
			throw new RuntimeException("订单号码["+info.getNumber()+"]已存在!");
		}
		//查检机构
		if(StringUtils.isBlank(info.getAgencyId()) || this.agencyDao.getAgency(info.getAgencyId()) == null){
			throw new RuntimeException("订单所属培训机构不存在!");
		}
		//检查用户
		if(StringUtils.isBlank(info.getUserId()) || this.userDao.getUser(info.getUserId()) == null){
			throw new RuntimeException("订单所属用户ID不存在!");
		}
		//
		Order data = StringUtils.isBlank(info.getId()) ? null : this.orderDao.getOrder(info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			if(StringUtils.isBlank(info.getId())) 
				info.setId(UUID.randomUUID().toString());
			info.setNumber(this.createOrderNumber(info.getAgencyId()));
			data = new Order();
		}
		//赋值
		BeanUtils.copyProperties(info, data);
		//保存
		if(isAdded){
			logger.debug("新增订单..");
			this.orderDao.insertOrder(data);
		}else {
			logger.debug("保存订单...");
			//删除订单学员
			this.orderDao.deleteOrderAllStudents(data.getId());
			//删除订单班级
			this.orderDao.deleteOrderAllClasses(data.getId());
			//删除订单套餐
			this.orderDao.deleteOrderAllPackages(data.getId());
			//保存订单
			this.orderDao.updateOrder(data);
		}
		//订单学员
		if(info.getStudentIds() != null && info.getStudentIds().length > 0){
			for(String stuId : info.getStudentIds()){
				if(StringUtils.isBlank(stuId)) continue;
				final User user = this.userDao.getUser(stuId);
				if(user != null){
					this.orderDao.insertOrderStudent(data.getId(), stuId);
				}
			}
		}
		//订单班级
		if(info.getClassIds() != null && info.getClassIds().length > 0){
			for(String classId : info.getClassIds()){
				if(StringUtils.isBlank(classId)) continue;
				final ClassPlan classPlan = this.classDao.getClassPlan(classId);
				if(classPlan != null){
					this.orderDao.insertOrderClass(data.getId(), classId);
				}
			}
		}
		//订单套餐
		if(info.getPackageIds() != null && info.getPackageIds().length > 0){
			for(String packageId : info.getPackageIds()){
				if(StringUtils.isBlank(packageId)) continue;
				final Package p = this.packageDao.getPackage(packageId);
				if(p != null){
					this.orderDao.insertOrderPackage(data.getId(), packageId);
				}
			}
		}
		//返回
		return this.conversion(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.admin.students.IOrderService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除数据..." + StringUtils.join(ids,","));
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id)) continue;
				//删除订单学员
				this.orderDao.deleteOrderAllStudents(id);
				//删除订单班级
				this.orderDao.deleteOrderAllClasses(id);
				//删除订单套餐
				this.orderDao.deleteOrderAllPackages(id);
				//删除订单
				this.orderDao.deleteOrder(id);
			}
		}
	}
}