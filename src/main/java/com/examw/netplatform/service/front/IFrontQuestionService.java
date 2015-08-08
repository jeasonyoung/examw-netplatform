package com.examw.netplatform.service.front;

import java.util.List;

import com.examw.netplatform.model.admin.courses.LessonInfo;
import com.examw.netplatform.model.front.FrontLessonInfo;
import com.examw.netplatform.model.front.FrontQuestionInfo;

/**
 * 前台问题服务接口
 * @author fengwei.
 * @since 2015年1月29日 下午5:03:16.
 */
public interface IFrontQuestionService {
	/**
	 * 查询用户某个课时下的问题集合
	 * @param userId
	 * @param lessonId
	 * @return
	 */
	List<FrontQuestionInfo> findUserLessonQuestions(String userId,String lessonId);
	
	/**
	 * 查询用户含问题的课时集合
	 * @param info
	 * @param userId
	 * @return
	 */
	List<FrontLessonInfo> findQuestionLessonList(LessonInfo info,String userId);
	/**
	 * 查询数据统计
	 * @param userId
	 * @return
	 */
	Long findQuestionLessonTotal(String userId);
}
