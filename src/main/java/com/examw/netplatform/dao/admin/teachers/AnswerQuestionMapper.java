package com.examw.netplatform.dao.admin.teachers;

import java.util.List;

import com.examw.netplatform.domain.admin.teachers.AnswerQuestionDetail;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionTopic;
import com.examw.netplatform.domain.admin.teachers.ClassLessonView;

/**
 * 教师答疑主题数据接口。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public interface AnswerQuestionMapper {
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
	 * 查询班级/课程资源视图。
	 * @param agencyId
	 * @return
	 */
	List<ClassLessonView> findClassLessonViews(String agencyId);
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
	/**
	 * 加载答疑明细数据。
	 * @param id
	 * 明细ID。
	 * @return
	 */
	AnswerQuestionDetail getDetail(String id);
	/**
	 * 查询答疑明细集合。
	 * @param topicId
	 * 主题ID。
	 * @return
	 */
	List<AnswerQuestionDetail> findDetails(String topicId);
	/**
	 * 删除答疑明细。
	 * @param id
	 */
	void deleteDetail(String id);
	/**
	 * 删除答疑主题下的明细。
	 * @param topicId
	 */
	void deleteDetailsByTopic(String topicId);
}