package com.examw.netplatform.dao.admin.students.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.students.IOrderDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.students.Order;
import com.examw.netplatform.model.admin.students.OrderInfo;
/**
 * 订单数据接口实现类
 * @author fengwei.
 * @since 2014年5月26日 上午8:17:55.
 */
public class OrderDaoImpl extends BaseDaoImpl<Order> implements IOrderDao{
	private static final Logger logger = Logger.getLogger(OrderDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.students.IOrderDao#findOrders(com.examw.netplatform.model.admin.students.OrderInfo)
	 */
	@Override
	public List<Order> findOrders(OrderInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from Order o where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += " order by o." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.students.IOrderDao#total(com.examw.netplatform.model.admin.students.OrderInfo)
	 */
	@Override
	public Long total(OrderInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from Order o where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//查询条件。
	private String addWhere(OrderInfo info, String hql, Map<String, Object> parameters){
		if(info == null) return hql;
		if(!StringUtils.isEmpty(info.getAgencyId())){//机构ID。
			hql += " and (o.agency.id = :agencyId) ";
			parameters.put("agencyId", info.getAgencyId());
		}
		if(info.getSource() != null){//来源
			hql += " and (o.source = :source) ";
			parameters.put("source", info.getSource());
		}
		if(info.getStatus() != null){//状态
			hql += " and (o.status = :status) ";
			parameters.put("status", info.getStatus());
		}
		if(!StringUtils.isEmpty(info.getNumber())){//订单号码
			hql += " and ((o.number like :number) or (o.name like :number)) ";
			parameters.put("number", "%"+ info.getNumber() +"%");
		}
		if(!StringUtils.isEmpty(info.getStudentName())){//学员
			hql += " and (o.student.name like :studentName)";
			parameters.put("studentName", "%"+info.getStudentName()+"%");
		}
		if(!StringUtils.isEmpty(info.getUserName())){//操作用户
			hql += " and (o.userName like :userName) ";
			parameters.put("userName", "%"+ info.getUserName() +"%");
		}
		return hql;
	}
}