package com.examw.netplatform.service.admin.teachers;

import com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo;
import com.examw.netplatform.service.IBaseDataService;

/**
 * 教师答疑主题服务接口。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public interface IAnswerQuestionTopicService extends IBaseDataService<AnswerQuestionTopicInfo> {
	/**
	 * 加载状态值名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
}