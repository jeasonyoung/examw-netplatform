package com.examw.netplatform.dao.admin.teachers;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionTopic;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo;

/**
 * 教师答疑主题数据接口。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public interface IAnswerQuestionTopicDao extends IBaseDao<AnswerQuestionTopic> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<AnswerQuestionTopic> findTopics(AnswerQuestionTopicInfo info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 */
	Long total(AnswerQuestionTopicInfo info);
}