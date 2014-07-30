package com.examw.netplatform.dao.admin.students.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.students.ILearningDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.students.Learning;
import com.examw.netplatform.model.admin.students.LearningInfo;

/**
 * 学习进度数据接口实现类
 * @author fengwei.
 * @since 2014年5月29日 上午11:44:06.
 */
public class LearningDaoImpl extends BaseDaoImpl<Learning> implements ILearningDao{
	/*
	 * 查询数据
	 * @see com.examw.netplatform.dao.admin.student.ILearningDao#findLearnings(com.examw.netplatform.model.admin.student.LearningInfo)
	 */
	@Override
	public List<Learning> findLearnings(LearningInfo info) {
		String hql = "from Learning le where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by le." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询统计
	 * @see com.examw.netplatform.dao.admin.student.ILearningDao#total(com.examw.netplatform.model.admin.student.LearningInfo)
	 */
	@Override
	public Long total(LearningInfo info) {
		String hql = "select count(*) from Learning le where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	/**
	 * 添加查询条件到HQL。
	 * @param info
	 * 查询条件。
	 * @param hql
	 * HQL
	 * @param parameters
	 * 参数。
	 * @return
	 * HQL
	 */
	protected String addWhere(LearningInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getUserId())){
			hql += " and (le.user.id = :userId)";
			parameters.put("userId", info.getUserId());
		}
		if(!StringUtils.isEmpty(info.getUsername())){
			hql +=" and (le.user.name like :name  or le.user.account like :name  or le.user.nickName like :name)";
			parameters.put("name", "%"+info.getUsername()+"%");
		}
		if(!StringUtils.isEmpty(info.getPackageId())){
			hql += " and (le.package_.id = :packageId)";
			parameters.put("packageId", info.getPackageId());
		}
		if(!StringUtils.isEmpty(info.getLessonId())){
			hql += " and (le.lesson.id = :lessonId)";
			parameters.put("lessonId", info.getLessonId());
		}
		return hql;
	}
}
