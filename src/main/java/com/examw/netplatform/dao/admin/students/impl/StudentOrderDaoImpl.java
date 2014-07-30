package com.examw.netplatform.dao.admin.students.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.students.IStudentOrderDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.students.StudentOrder;
import com.examw.netplatform.model.admin.students.StudentOrderInfo;
/**
 * 订单数据接口实现类
 * @author fengwei.
 * @since 2014年5月26日 上午8:17:55.
 */
public class StudentOrderDaoImpl extends BaseDaoImpl<StudentOrder> implements IStudentOrderDao{
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.students.IStudentOrderDao#findOrders(com.examw.netplatform.model.admin.students.StudentOrderInfo)
	 */
	@Override
	public List<StudentOrder> findOrders(StudentOrderInfo info) {
		String hql = "from StudentOrder s where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("agencyName")){
				info.setSort("student.agency.name");
			}
			if(info.getSort().equalsIgnoreCase("studentName")){
				info.setSort("student.user.name");
			}
			if(info.getSort().equalsIgnoreCase("statusName")){
				info.setSort("status");
			}
			hql += " order by s." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.students.IStudentOrderDao#total(com.examw.netplatform.model.admin.students.StudentOrderInfo)
	 */
	@Override
	public Long total(StudentOrderInfo info) {
		String hql = "select count(*) from StudentOrder s where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//查询条件。
	private String addWhere(StudentOrderInfo info, String hql, Map<String, Object> parameters){
		hql +=" and (s.status " + ((info.getStatus() == null) ? ">" : "=")+ " :status) ";
		parameters.put("status", (info.getStatus() == null) ? StudentOrder.STATUS_DELETE : info.getStatus());
		
		if(!StringUtils.isEmpty(info.getCurrentUserId())){
			hql += " and (s.student.agency.id in (select au.agency.id  from AgencyUser au where au.user.id = :currentUserId)) ";
			parameters.put("currentUserId", info.getCurrentUserId());
		}
		if(!StringUtils.isEmpty(info.getOrderNo())){
			hql += " and (s.orderNo like :orderNo) ";
			parameters.put("orderNo", "%"+ info.getOrderNo() +"%");
		}
		if(!StringUtils.isEmpty(info.getStudentName())){
			hql += " and (s.student.user.name like :studentName) ";
			parameters.put("studentName", "%"+ info.getStudentName() +"%");
		}
		return hql;
	}
}