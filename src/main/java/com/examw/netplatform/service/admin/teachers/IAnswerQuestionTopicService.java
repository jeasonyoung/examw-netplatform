package com.examw.netplatform.service.admin.teachers;

import com.examw.model.DataGrid;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionTopic;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo;
import com.examw.service.Status;

/**
 * 教师答疑主题服务接口。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public interface IAnswerQuestionTopicService {
	/**
	 * 加载状态值名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	DataGrid<AnswerQuestionTopicInfo> datagrid(AnswerQuestionTopicInfo info);
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	AnswerQuestionTopicInfo update(AnswerQuestionTopicInfo info);
	/**
	 * 更新数据。
	 * @param topicId
	 * 主题ID。
	 * @param status
	 * 状态值。
	 */
	void updateStatus(String topicId, Status status);
	/**
	 * 模型转换
	 * @param data
	 * @return
	 */
	AnswerQuestionTopicInfo conversion(AnswerQuestionTopic data);
	/**
	 * 删除数据。
	 * @param ids
	 */
	void delete(String[] ids);
}