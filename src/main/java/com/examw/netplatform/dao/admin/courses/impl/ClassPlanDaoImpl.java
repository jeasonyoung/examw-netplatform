package com.examw.netplatform.dao.admin.courses.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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
	private static final Logger logger = Logger.getLogger(ClassPlanDaoImpl.class);
	/*
	 * 查询数据
	 * @see com.examw.netplatform.dao.admin.IClassTypeDao#findClassTypes(com.examw.netplatform.model.admin.ClassTypeInfo)
	 */
	@Override
	public List<ClassPlan> findClassPlans(ClassPlanInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from ClassPlan c where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			switch(info.getSort()){
				case "classTypeName":
					info.setSort("classType.name");
					break;
				case "agencyName":
					info.setSort("agency.name");
					break;
				case "examName":
					info.setSort("subject.exam.name");
					break;
				case "subjectName":
					info.setSort("subject.name");
					break;
				case "statusName":
					info.setSort("status");
					break;
			}
			
			hql += " order by c." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.IClassTypeDao#total(com.examw.netplatform.model.admin.ClassTypeInfo)
	 */
	@Override
	public Long total(ClassPlanInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from ClassPlan c where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加查询条件到HQL。
	private String addWhere(ClassPlanInfo info, String hql, Map<String, Object> parameters){
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
	 * 加载机构下最大排序号。
	 * @see com.examw.netplatform.dao.admin.courses.IClassPlanDao#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String agencyId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载机构［%s］下最大排序号...", agencyId));
		final String hql = "select max(c.orderNo) from ClassPlan c  where c.agency.id = :agencyId order by c.orderNo desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("agencyId", agencyId);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? null : (int)obj;
	}
	/*
	 * 重载删除数据。
	 * @see com.examw.netplatform.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(ClassPlan data) {
		if(logger.isDebugEnabled()) logger.debug("重载删除数据...");
		if(data == null) return;
		int count = 0;
		if(data.getLessons() != null && (count = data.getLessons().size()) > 0){
			throw new RuntimeException(String.format("班级［%1$s］关联［%2$d］课时资源，暂不能删除！", data.getName(), count));
		}
		if(data.getPackages() != null && (count = data.getPackages().size()) > 0){
			throw new RuntimeException(String.format("班级［%1$s］关联［%2$d］套餐，暂不能删除！", data.getName(), count));
		}
		if(data.getUsers() != null && (count = data.getUsers().size()) > 0){
			throw new RuntimeException(String.format("班级［%1$s］关联［%2$d］用户，暂不能删除！", data.getName(), count));
		}
		super.delete(data);
	}
}