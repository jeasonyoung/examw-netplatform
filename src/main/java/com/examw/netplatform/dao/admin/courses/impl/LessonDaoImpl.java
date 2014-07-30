package com.examw.netplatform.dao.admin.courses.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.courses.ILessonDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.model.admin.courses.LessonInfo;

/**
 * 课时数据接口实现类
 * @author fengwei.
 * @since 2014年5月22日 上午11:38:23.
 */
public class LessonDaoImpl extends BaseDaoImpl<Lesson> implements ILessonDao {
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.courses.ILessonDao#findLessons(com.examw.netplatform.model.admin.courses.LessonInfo)
	 */
	@Override
	public List<Lesson> findLessons(LessonInfo info) {
		String hql = "from Lesson l where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if (!StringUtils.isEmpty(info.getSort())) {
			if(info.getSort().equalsIgnoreCase("className")){
				info.setSort("classPlan.name");
			}
			if(info.getSort().equalsIgnoreCase("videoModeName")){
				info.setSort("videoMode");
			}
			hql += " order by l." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询统计
	 * @see com.examw.netplatform.dao.admin.ILessonDao#total(com.examw.netplatform.model.admin.LessonInfo)
	 */
	@Override
	public Long total(LessonInfo info) {
		String hql = "select count(*) from Lesson l where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件到HQL。
	private String addWhere(LessonInfo info, String hql, Map<String, Object> parameters) {
		//当前用户ID。
		if(!StringUtils.isEmpty(info.getCurrentUserId())){
			hql += " and (l.classPlan.agency.id in (select au.agency.id from AgencyUser au where au.user.id = :userId)) ";
			parameters.put("userId", info.getCurrentUserId());
		}
		//所属班级ID。
		if(!StringUtils.isEmpty(info.getClassId())){
			hql += " and (l.classPlan.id = :classId) ";
			parameters.put("classId", info.getClassId());
		}
		//资源名称。
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and (l.name like :name) ";
			parameters.put("name", "%"+ info.getName() +"%");
		}
		return hql;
	}
}