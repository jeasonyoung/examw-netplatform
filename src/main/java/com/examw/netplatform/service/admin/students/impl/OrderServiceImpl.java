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
	private ClassMapper classPlanDao;
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
	public void setClassPlanDao(ClassMapper classPlanDao) {
		logger.debug("注入班级数据接口...");
		this.classPlanDao = classPlanDao;
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
		String en = agency.getAbbr_en();
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
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getOrder()) + " " + StringUtils.trimToEmpty(info.getSort()));
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
		OrderInfo info = (OrderInfo)data;
		info.setSourceName(this.loadSourceName(data.getSource()));
		info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
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
			this.orderDao.updateOrder(data);
		}
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
				this.orderDao.deleteOrder(id);
			}
		}
	}
}