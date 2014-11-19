package com.examw.netplatform.dao.admin.teachers.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.teachers.IAnswerQuestionDetailDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionDetail;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionDetailInfo;

/**
 * 教师答疑明细数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public class AnswerQuestionDetailDaoImpl extends BaseDaoImpl<AnswerQuestionDetail> implements IAnswerQuestionDetailDao {
	private static final Logger logger = Logger.getLogger(AnswerQuestionDetailDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.teachers.IAnswerQuestionDetailDao#findDetails(com.examw.netplatform.model.admin.teachers.AnswerQuestionDetailInfo)
	 */
	@Override
	public List<AnswerQuestionDetail> findDetails(AnswerQuestionDetailInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from AnswerQuestionDetail a where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setSort("asc");
			switch(info.getSort()){
				case "userName":
					info.setSort("user.name");
					break;
			}
			hql += " order by a." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.teachers.IAnswerQuestionDetailDao#total(com.examw.netplatform.model.admin.teachers.AnswerQuestionDetailInfo)
	 */
	@Override
	public Long total(AnswerQuestionDetailInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from AnswerQuestionDetail a where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件
	private String addWhere(AnswerQuestionDetailInfo info, String hql, Map<String, Object> parameters){
		if(info == null) return hql;
		if(!StringUtils.isEmpty(info.getTopicId())){//主题ID
			hql += " and (a.topic.id = :topicId) ";
			parameters.put("topicId", info.getTopicId());
		}
		if(!StringUtils.isEmpty(info.getUserId())){//用户ID
			hql += " and (a.user.id = :userId) ";
			parameters.put("userId", info.getUserId());
		}
		if(!StringUtils.isEmpty(info.getContent())){//内容
			hql += " and (a.content like :content) ";
			parameters.put("content", "%" + info.getContent() +"%");
		}
		return hql;
	}
}