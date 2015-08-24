package com.examw.netplatform.service.admin.teachers;

import java.util.List;

import com.examw.model.DataGrid;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionDetail;
import com.examw.netplatform.domain.admin.teachers.ClassLessonView;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionDetailInfo;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo;
import com.examw.service.Status;

/**
 * 教师答疑主题服务接口。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public interface IAnswerQuestionService {
	/**
	 * 加载状态值名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 查询答疑主题数据。
	 * @param info
	 * @return
	 */
	DataGrid<AnswerQuestionTopicInfo> datagrid(AnswerQuestionTopicInfo info);
	/**
	 * 查询答疑详细数据。
	 * @param info
	 * @return
	 */
	DataGrid<AnswerQuestionDetailInfo> datagridByDetails(AnswerQuestionDetailInfo info);
	/**
	 * 加载答疑明细集合。
	 * @param topicId
	 * 答疑主题ID。
	 * @return
	 */
	List<AnswerQuestionDetail> loadDetailsByTopic(String topicId);
	/**
	 * 查询班级/课程资源。
	 * @param agencyId
	 * @return
	 */
	List<ClassLessonView> findClassLessonViews(String agencyId);
	/**
	 * 更新答疑主题数据。
	 * @param info
	 * @return
	 */
	AnswerQuestionTopicInfo update(AnswerQuestionTopicInfo info);
	/**
	 * 更新答疑明细数据。
	 * @param info
	 * @return
	 */
	AnswerQuestionDetailInfo updateDetail(AnswerQuestionDetailInfo info);
	/**
	 * 更新数据。
	 * @param topicId
	 * 主题ID。
	 * @param status
	 * 状态值。
	 */
	void updateStatus(String topicId, Status status);
	/**
	 * 删除数据。
	 * @param ids
	 */
	void delete(String[] ids);
	/**
	 * 删除明细数据。
	 * @param ids
	 */
	void deleteDetails(String[] ids);
}