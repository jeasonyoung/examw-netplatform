package com.examw.netplatform.dao.admin.teachers;

import java.util.List;

import com.examw.netplatform.domain.admin.teachers.AnswerQuestionTopic;

/**
 * 教师答疑主题数据接口。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public interface AnswerQuestionTopicMapper {
	/**
	 * 获取教师答疑主题。
	 * @param id
	 * @return
	 */
	AnswerQuestionTopic getTopic(String id);
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<AnswerQuestionTopic> findTopics(AnswerQuestionTopic info);
	/**
	 * 新增答疑主题。
	 * @param data
	 */
	void insertTopic(AnswerQuestionTopic data);
	/**
	 * 更新答疑主题。
	 * @param data
	 */
	void updateTopic(AnswerQuestionTopic data);
	/**
	 * 删除答疑主题。
	 * @param id
	 */
	void deleteTopic(String id);
}