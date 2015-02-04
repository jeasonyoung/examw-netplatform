package com.examw.netplatform.service.front.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.courses.ILessonDao;
import com.examw.netplatform.dao.admin.teachers.IAnswerQuestionTopicDao;
import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionDetail;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionTopic;
import com.examw.netplatform.model.admin.courses.LessonInfo;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionDetailInfo;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo;
import com.examw.netplatform.model.front.FrontLessonInfo;
import com.examw.netplatform.model.front.FrontQuestionInfo;
import com.examw.netplatform.service.admin.teachers.IAnswerQuestionDetailService;
import com.examw.netplatform.service.admin.teachers.IAnswerQuestionTopicService;
import com.examw.netplatform.service.front.user.IFrontQuestionService;

/**
 * 
 * @author fengwei.
 * @since 2015年1月29日 下午5:03:33.
 */
public class FrontQuestionServiceImpl implements IFrontQuestionService {
	
	private IAnswerQuestionTopicDao answerQuestionTopicDao;
	private ILessonDao lessonDao;
	private IAnswerQuestionDetailService answerQuestionDetailService;
	private IAnswerQuestionTopicService answerQuestionTopicService;
	/**
	 * 设置 问题数据接口
	 * @param answerQuestionTopicDao
	 * 
	 */
	public void setAnswerQuestionTopicDao(IAnswerQuestionTopicDao answerQuestionTopicDao) {
		this.answerQuestionTopicDao = answerQuestionTopicDao;
	}

	/**
	 * 设置 问题回答数据接口
	 * 
	 * @param answerQuestionDetailService
	 * 
	 */
	public void setAnswerQuestionDetailService(IAnswerQuestionDetailService answerQuestionDetailService) {
		this.answerQuestionDetailService = answerQuestionDetailService;
	}
	
	/**
	 * 设置 问题服务接口
	 * @param answerQuestionTopicService
	 * 
	 */
	public void setAnswerQuestionTopicService(IAnswerQuestionTopicService answerQuestionTopicService) {
		this.answerQuestionTopicService = answerQuestionTopicService;
	}
	
	/**
	 * 设置 课时数据接口
	 * @param lessonDao
	 * 
	 */
	public void setLessonDao(ILessonDao lessonDao) {
		this.lessonDao = lessonDao;
	}
	
	/*
	 * 查询用户课时提问集合
	 * @see com.examw.netplatform.service.front.user.IFrontQuestionService#findUserLessonQuestions(java.lang.String, java.lang.String)
	 */
	@Override
	public List<FrontQuestionInfo> findUserLessonQuestions(final String userId, final String lessonId) {
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(lessonId))
			return null;
		List<AnswerQuestionTopic> questions = this.answerQuestionTopicDao.findTopics(new AnswerQuestionTopicInfo() {
			private static final long serialVersionUID = 1L;
			@Override
			public String getLessonId() { return lessonId;}
			@Override
			public String getUserId() { return userId;}
			@Override
			public String getSort() { return "createTime";}
			@Override
			public String getOrder() {return "desc";}
		});
		if(questions!=null)
		{
			List<FrontQuestionInfo> result = new ArrayList<FrontQuestionInfo>();
			for(AnswerQuestionTopic question:questions)
			{
				FrontQuestionInfo info = new FrontQuestionInfo();
				BeanUtils.copyProperties(this.answerQuestionTopicService.conversion(question), info);
				if(question.getDetails()!=null)
				{
					List<AnswerQuestionDetailInfo> details = new ArrayList<AnswerQuestionDetailInfo>();
					for(AnswerQuestionDetail detail:question.getDetails())
					{
						if(detail == null) continue;
						AnswerQuestionDetailInfo detailInfo = this.answerQuestionDetailService.conversion(detail);
						details.add(detailInfo);
					}
					info.setAnswers(details);
				}
				result.add(info);
			}
			return result;
		}
		return null;
	}
	/*
	 * 查询用户提问的课时的集合
	 * @see com.examw.netplatform.service.front.user.IFrontQuestionService#findQuestionLessonList(com.examw.netplatform.model.admin.courses.LessonInfo, java.lang.String)
	 */
	@Override
	public List<FrontLessonInfo> findQuestionLessonList(LessonInfo info, String userId) {
		if(StringUtils.isEmpty(userId))
		return null;
		List<Lesson> lessons = this.lessonDao.findLessonWithQuestions(info, userId);
		if(lessons == null || lessons.isEmpty()) return null;
		List<FrontLessonInfo> lessonList = new ArrayList<FrontLessonInfo>();
		for(Lesson less:lessons)
		{
			if(less == null) continue;
			FrontLessonInfo lessInfo = new FrontLessonInfo();
			lessInfo.setId(less.getId());
			lessInfo.setName(less.getName());
			lessInfo.setClassId(less.getClassPlan().getId());
			//问答 [多次查询]
			lessInfo.setQuestions(this.findUserLessonQuestions(userId, less.getId()));
			lessonList.add(lessInfo);
		}
		return lessonList;
	}
	/*
	 * 查询用户提问的课时的统计
	 * @see com.examw.netplatform.service.front.user.IFrontQuestionService#findQuestionLessonTotal(java.lang.String)
	 */
	@Override
	public Long findQuestionLessonTotal(String userId) {
		if(StringUtils.isEmpty(userId))
			return 0L;
		return this.lessonDao.totalLessonWithQuestions(userId);
	}
}
