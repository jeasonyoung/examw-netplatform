package com.examw.netplatform.service.admin.students.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.courses.IClassPlanDao;
import com.examw.netplatform.dao.admin.courses.IPackageDao;
import com.examw.netplatform.dao.admin.security.IUserDao;
import com.examw.netplatform.dao.admin.settings.IAgencyDao;
import com.examw.netplatform.dao.admin.students.IOrderDao;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.domain.admin.students.Order;
import com.examw.netplatform.model.admin.students.OrderInfo;
import com.examw.netplatform.service.admin.students.IOrderService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;

/**
 * 订单服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年12月1日
 */
public class OrderServiceImpl extends BaseDataServiceImpl<Order,OrderInfo> implements IOrderService {
	private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);
	private IOrderDao orderDao;
	private IAgencyDao agencyDao;
	private IUserDao userDao;
	private IPackageDao packageDao;
	private IClassPlanDao classPlanDao;
	private Map<Integer, String> typeMap,sourceMap,statusMap;
	/**
	 * 设置订单数据接口。
	 * @param orderDao 
	 *	  订单数据接口。
	 */
	public void setOrderDao(IOrderDao orderDao) {
		if(logger.isDebugEnabled()) logger.debug("注入订单数据接口...");
		this.orderDao = orderDao;
	}
	/**
	 * 设置培训机构数据接口。
	 * @param agencyDao 
	 *	  培训机构数据接口。
	 */
	public void setAgencyDao(IAgencyDao agencyDao) {
		if(logger.isDebugEnabled()) logger.debug("注入培训机构数据接口...");
		this.agencyDao = agencyDao;
	}
	/**
	 * 设置用户数据接口。
	 * @param userDao 
	 *	  用户数据接口。
	 */
	public void setUserDao(IUserDao userDao) {
		if(logger.isDebugEnabled()) logger.debug("注入用户数据接口...");
		this.userDao = userDao;
	}
	/**
	 * 设置套餐数据接口。
	 * @param packageDao 
	 *	  套餐数据接口。
	 */
	public void setPackageDao(IPackageDao packageDao) {
		if(logger.isDebugEnabled()) logger.debug("注入套餐数据接口...");
		this.packageDao = packageDao;
	}
	/**
	 * 设置班级数据接口。
	 * @param classPlanDao 
	 *	  班级数据接口。
	 */
	public void setClassPlanDao(IClassPlanDao classPlanDao) {
		if(logger.isDebugEnabled()) logger.debug("注入班级数据接口...");
		this.classPlanDao = classPlanDao;
	}
	/**
	 * 设置订单类型值名称集合。
	 * @param typeMap 
	 *	  类型值名称集合。
	 */
	public void setTypeMap(Map<Integer, String> typeMap) {
		if(logger.isDebugEnabled()) logger.debug("注入订单类型值名称集合...");
		this.typeMap = typeMap;
	}
	/**
	 * 设置订单来源值名称集合。
	 * @param sourceMap 
	 *	  订单来源值名称集合。
	 */
	public void setSourceMap(Map<Integer, String> sourceMap) {
		if(logger.isDebugEnabled()) logger.debug("注入订单来源值名称集合...");
		this.sourceMap = sourceMap;
	}
	/**
	 * 设置订单状态值名称集合。
	 * @param statusMap 
	 *	  订单状态值名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("注入订单状态值名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载订单类型值名称。
	 * @see com.examw.netplatform.service.admin.students.IOrderService#loadTypeName(java.lang.Integer)
	 */
	@Override
	public String loadTypeName(Integer type) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载订单类型值［%d］名称...", type));
		if(type == null || this.typeMap == null || this.typeMap.size() == 0) return null;
		return this.typeMap.get(type);
	}
	/*
	 * 加载订单来源值名称。
	 * @see com.examw.netplatform.service.admin.students.IOrderService#loadSourceName(java.lang.Integer)
	 */
	@Override
	public String loadSourceName(Integer source) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载订单来源值［%d］名称...", source));
		if(source == null || this.sourceMap == null || this.sourceMap.size() == 0) return null;
		return this.sourceMap.get(source);
	}
	/*
	 * 加载状态名称值名称。
	 * @see com.examw.netplatform.service.admin.students.IOrderService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载状态名称值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Order> find(OrderInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.orderDao.findOrders(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected OrderInfo changeModel(Order data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Order => OrderInfo ...");
		OrderInfo info = new OrderInfo();
		BeanUtils.copyProperties(data, info);
		//类型名称
		if(info.getType() != null) info.setTypeName(this.loadTypeName(info.getType()));
		//来源名称
		if(info.getSource() != null) info.setSourceName(this.loadSourceName(info.getSource()));
		//状态名称
		if(info.getStatus() != null) info.setStatusName(this.loadStatusName(info.getStatus()));
		Agency agency = null;
		if((agency = data.getAgency()) != null){//机构
			info.setAgencyId(agency.getId());
			info.setAgencyName(agency.getName());
		}
		User user = null;
		if((user = data.getStudent()) != null){//学员
			info.setStudentId(user.getId());
			info.setStudentName(user.getName());
		}
		if(data.getPackages() != null && data.getPackages().size() > 0){//套餐
			List<String> listIds = new ArrayList<>(),listNames = new ArrayList<>();
			for(com.examw.netplatform.domain.admin.courses.Package p : data.getPackages()){
				if(p == null) continue;
				listIds.add(p.getId());
				listNames.add(p.getName());
			}
			info.setPackageId(listIds.toArray(new String[0]));
			info.setPackageName(listNames.toArray(new String[0]));
		}
		if(data.getClasses() != null && data.getClasses().size() > 0){//班级
			List<String> listIds = new ArrayList<>(),listNames = new ArrayList<>();
			for(ClassPlan c : data.getClasses()){
				if(c == null) continue;
				listIds.add(c.getId());
				listNames.add(c.getName());
			}
			info.setClassId(listIds.toArray(new String[0]));
			info.setClassName(listNames.toArray(new String[0]));
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(OrderInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.orderDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public OrderInfo update(OrderInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		boolean isAdded = false;
		Order order = StringUtils.isEmpty(info.getId()) ? null : this.orderDao.load(Order.class, info.getId());
		if(isAdded = (order == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			order = new Order();
		}else {
			info.setCreateTime(order.getCreateTime());
			if(info.getCreateTime() == null) info.setCreateTime(new Date());
		}
		info.setLastTime(new Date());
		
		if(StringUtils.isEmpty(info.getAgencyId())) throw new RuntimeException("培训机构ID为空！");
		order.setAgency(this.agencyDao.load(Agency.class,info.getAgencyId()));
		if(order.getAgency() == null) throw new RuntimeException(String.format("培训机构［%s］不存在！", info.getAgencyId()));
		
		if(StringUtils.isEmpty(info.getStudentId())) throw new RuntimeException("学员ID为空！");
		order.setStudent(this.userDao.load(User.class, info.getStudentId()));
		if(order.getStudent() == null) throw new RuntimeException(String.format("学员［%s］不存在！", info.getStudentId()));
		
		Set<com.examw.netplatform.domain.admin.courses.Package> packages = null;
		if(info.getPackageId() != null && info.getPackageId().length > 0){//套餐
			packages = new HashSet<>();
			for(String packageId : info.getPackageId()){
				if(StringUtils.isEmpty(packageId)) continue;
				com.examw.netplatform.domain.admin.courses.Package pack = this.packageDao.load(com.examw.netplatform.domain.admin.courses.Package.class, packageId);
				if(pack == null) throw new RuntimeException(String.format("套餐［%s］不存在！", packageId));
				packages.add(pack);
			}
		}
		order.setPackages(packages);
		
		Set<ClassPlan> classes = null;
		if(info.getClassId() != null && info.getClassId().length > 0){//班级
			classes = new HashSet<>();
			for(String classId : info.getClassId()){
				if(StringUtils.isEmpty(classId)) continue;
				ClassPlan data = this.classPlanDao.load(ClassPlan.class, classId);
				if(data == null) throw new RuntimeException(String.format("班级［%s］不存在！", classId));
				classes.add(data);
			}
		}
		order.setClasses(classes);
		
		if(isAdded)this.orderDao.save(order);
		
		return this.changeModel(order);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s ...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Order order = this.orderDao.load(Order.class, ids[i]);
			if(order != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s", ids[i]));
				this.orderDao.delete(order);
			}
		}
	}
}