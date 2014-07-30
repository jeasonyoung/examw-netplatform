package com.examw.netplatform.dao.admin.courses.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.courses.IClassPlanDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
/**
 * 开班计划数据接口实现类
 * @author fengwei.
 * @since 2014年5月20日 下午5:14:59.
 */
public class ClassPlanDaoImpl  extends BaseDaoImpl<ClassPlan> implements IClassPlanDao {
	/*
	 * 查询数据
	 * @see com.examw.netplatform.dao.admin.IClassTypeDao#findClassTypes(com.examw.netplatform.model.admin.ClassTypeInfo)
	 */
	@Override
	public List<ClassPlan> findClassPlans(ClassPlanInfo info) {
		String hql = "from ClassPlan c where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("agencyName")){
				info.setSort("agency.name");
			}
			if(info.getSort().equalsIgnoreCase("subjectName")){
				info.setSort("subject.name");
			}
			if(info.getSort().equalsIgnoreCase("classTypeName")){
				info.setSort("classType.name");
			}
			hql += " order by c." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.IClassTypeDao#total(com.examw.netplatform.model.admin.ClassTypeInfo)
	 */
	@Override
	public Long total(ClassPlanInfo info) {
		String hql = "select count(*) from ClassPlan c where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件到HQL。
	private String addWhere(ClassPlanInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getCurrentUserId())){
			hql += " and (c.agency.id in (select au.agency.id  from AgencyUser au where au.user.id = :userId))";
			parameters.put("userId", info.getCurrentUserId());
		}
		if(!StringUtils.isEmpty(info.getAgencyId())){
			hql += " and (c.agency.id = :agencyId) ";
			parameters.put("agencyId", info.getAgencyId());
		}
		if(!StringUtils.isEmpty(info.getExamId())){
			hql += " and (c.subject.exam.id = :examId) ";
			parameters.put("examId", info.getExamId());
		}
		if(!StringUtils.isEmpty(info.getSubjectId())){
			hql += " and (c.subject.id = :subjectId) ";
			parameters.put("subjectId", info.getSubjectId());
		}
		if(!StringUtils.isEmpty(info.getClassTypeId())){
			hql += " and (c.classType.id = :classTypeId) ";
			parameters.put("classTypeId", info.getClassTypeId());
		}
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and (c.name = :name) ";
			parameters.put("name", "%"+ info.getName() +"%");
		}
		return hql;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.courses.IClassPlanDao#findClassPlans(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<ClassPlan> findClassPlans(String agencyId, String catalogId, String examId,String className) {
		String hql = "from ClassPlan c where (c.status = :status) and (c.agency.id = :agencyId) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("status", ClassPlan.STATUS_ENABLE);
		parameters.put("agencyId", agencyId);
		if(!StringUtils.isEmpty(catalogId)){
			hql += " and (c.subject.exam.catalog.id = :catalogId) ";
			parameters.put("catalogId", catalogId);
		}
		if(!StringUtils.isEmpty(examId)){
			hql += " and (c.subject.exam.id = :examId) ";
			parameters.put("examId", examId);
		}
		if(!StringUtils.isEmpty(className)){
			hql += " and (c.name like :className) ";
			parameters.put("className", "%"+ className +"%");
		}
		hql += " order by c.startTime desc";
		return this.find(hql,parameters, null, null);
	}
}