package com.examw.netplatform.dao.admin.students.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.students.ILearningDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.students.Learning;
import com.examw.netplatform.model.admin.students.LearningInfo;

/**
 * 学习进度数据接口实现类。
 * @author fengwei.
 * @since 2014年5月29日 上午11:44:06.
 */
public class LearningDaoImpl extends BaseDaoImpl<Learning> implements ILearningDao{
	private static final Logger logger = Logger.getLogger(LearningDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.student.ILearningDao#findLearnings(com.examw.netplatform.model.admin.student.LearningInfo)
	 */
	@Override
	public List<Learning> findLearnings(LearningInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from Learning l where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += " order by l." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询统计。
	 * @see com.examw.netplatform.dao.admin.student.ILearningDao#total(com.examw.netplatform.model.admin.student.LearningInfo)
	 */
	@Override
	public Long total(LearningInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询统计...");
		String hql = "select count(*) from Learning l where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件。
	private String addWhere(LearningInfo info,String hql,Map<String, Object> parameters){
		if(info == null) return hql;
		if(!StringUtils.isEmpty(info.getAgencyId())){//机构ID
			hql += " and (l.agency.id = :agencyId) ";
			parameters.put("agencyId", info.getAgencyId());
		}
		if(!StringUtils.isEmpty(info.getUserId())){//用户ID
			hql += " and (l.user.id = :userId) ";
			parameters.put("userId", info.getUserId());
		}
		if(!StringUtils.isEmpty(info.getLessonId())){//课时资源ID
			hql += " and (l.lesson.id = :lessonId) ";
			parameters.put("lessonId", info.getLessonId());
		}
		if(!StringUtils.isEmpty(info.getUserName())){//用户名
			hql += " and (l.user.name like :userName) ";
			parameters.put("userName", "%"+info.getUserName()+"%");
		}
		return hql;
	}
}