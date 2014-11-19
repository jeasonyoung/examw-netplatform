package com.examw.netplatform.dao.admin.teachers.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.teachers.IAnswerQuestionTopicDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionTopic;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo;

/**
 * 教师答疑主题数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public class AnswerQuestionTopicDaoImpl extends BaseDaoImpl<AnswerQuestionTopic> implements IAnswerQuestionTopicDao {
	private static final Logger logger = Logger.getLogger(AnswerQuestionTopicDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.teachers.IAnswerQuestionTopicDao#findTopics(com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo)
	 */
	@Override
	public List<AnswerQuestionTopic> findTopics(AnswerQuestionTopicInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from AnswerQuestionTopic a where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			switch(info.getSort()){
				case "statusName":
					info.setSort("status");
					break;
				case "userName":
					info.setSort("user.name");
					break;
				case "lessonName":
					info.setSort("lesson.name");
					break;
			}
			hql += " order by a." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.teachers.IAnswerQuestionTopicDao#total(com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo)
	 */
	@Override
	public Long total(AnswerQuestionTopicInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from AnswerQuestionTopic a where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件。
	private String addWhere(AnswerQuestionTopicInfo info,String hql, Map<String, Object> parameters){
		if(info == null) return hql;
		if(!StringUtils.isEmpty(info.getAgencyId())){//机构ID
			hql += " and (a.agency.id = :agencyId) ";
			parameters.put("agencyId", info.getAgencyId());
		}
		if(!StringUtils.isEmpty(info.getUserId())){//用户ID
			hql += " and (a.user.id = :userId) ";
			parameters.put("userId", info.getUserId());
		}
		if(!StringUtils.isEmpty(info.getLessonId())){//课时资源ID
			hql += " and (a.lesson.id = :lessonId) ";
			parameters.put("lessonId", info.getLessonId());
		}
		if(info.getStatus() != null){//状态
			hql += " and (a.status = :status) ";
			parameters.put("status", info.getStatus());
		}
		return hql;
	}
}