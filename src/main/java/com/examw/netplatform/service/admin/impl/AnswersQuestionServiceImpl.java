package com.examw.netplatform.service.admin.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.IAnswersQuestionDao;
import com.examw.netplatform.dao.admin.security.IUserDao;
import com.examw.netplatform.domain.admin.AnswersQuestion;
import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.model.admin.AnswersQuestionInfo;
import com.examw.netplatform.service.admin.IAnswersQuestionService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;

/**
 * 课堂问答服务接口实现类
 * @author fengwei.
 * @since 2014年5月31日 上午10:00:06.
 */
public class AnswersQuestionServiceImpl extends BaseDataServiceImpl<AnswersQuestion,AnswersQuestionInfo> implements IAnswersQuestionService{
	private IAnswersQuestionDao answersQuestionDao;
//	private ILessonDao lessonDao;
	private IUserDao userDao;
	private Map<Integer,String> statusMap;
	/**
	 * 设置 答疑数据接口
	 * @param answerQuestionDao
	 * 
	 */
	public void setAnswersQuestionDao(IAnswersQuestionDao answersQuestionDao) {
		this.answersQuestionDao = answersQuestionDao;
	}

	/**
	 * 设置 课时数据接口
	 * @param lessonDao
	 * 
	 */
//	public void setLessonDao(ILessonDao lessonDao) {
//		this.lessonDao = lessonDao;
//	}

	/**
	 * 设置 用户数据接口
	 * @param userDao
	 * 
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 设置 状态映射
	 * @param statusMap
	 * 
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		this.statusMap = statusMap;
	}

	@Override
	protected List<AnswersQuestion> find(AnswersQuestionInfo info) {
		return this.answersQuestionDao.findAnswersQuestions(info);
	}

	@Override
	protected AnswersQuestionInfo changeModel(AnswersQuestion data) {
		if(data == null) return null;
		AnswersQuestionInfo info = new AnswersQuestionInfo();
		BeanUtils.copyProperties(data, info,new String[]{"children"});
		info.setStatusName(this.loadStatusName(data.getStatus()));
		if(data.getUser()!=null){
			info.setUserAccount(data.getUser().getAccount());
		}
		if(data.getParent()==null){
			if(data.getLesson()!=null){
				Lesson lesson = data.getLesson();
				info.setLessonId(lesson.getId());
				info.setLessonName(lesson.getClassPlan().getName()+"--"+lesson.getName());
			}
		}
		if(data.getChildren() != null && data.getChildren().size() > 0){
			List<AnswersQuestionInfo> children = new ArrayList<>();
			for(AnswersQuestion child : data.getChildren()){
				AnswersQuestionInfo c = this.changeModel(child);
				if(c != null){
					c.setPid(info.getId());
					children.add(c);
				}
			}
			if(children.size() > 0){
				info.setChildren(children);
			}
		}
		return info;
	}

	@Override
	protected Long total(AnswersQuestionInfo info) {
		return this.answersQuestionDao.total(info);
	}

	/*
	 * 教师回答学员的提问
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public AnswersQuestionInfo update(AnswersQuestionInfo info) {
		if(info == null) return null;
		if(StringUtils.isEmpty(info.getId())) return null;
		AnswersQuestion parent = this.answersQuestionDao.load(AnswersQuestion.class, info.getId());
		if(parent==null) return null;
		//只能加不能改
		AnswersQuestion data = new AnswersQuestion();
		data.setId(UUID.randomUUID().toString());
		data.setContent(info.getContent());
		data.setCreateTime(new Date());
		data.setType(AnswersQuestion.TYPE_TEACHER);
		data.setStatus(AnswersQuestion.STATUS_ANSWER);
		//课时
//		if(!StringUtils.isEmpty(info.getLessonId())){
//			Lesson lesson = lessonDao.load(Lesson.class, info.getLessonId());
//			if(lesson!=null) data.setLesson(lesson);
//		}
		//提问
		data.setParent(parent);
		parent.setCreateTime(new Date());
		parent.setStatus(AnswersQuestion.STATUS_ANSWER); //父问题的状态
		//用户
		if(!StringUtils.isEmpty(info.getUserId())){
			User user = this.userDao.load(User.class, info.getUserId());
			if(user == null) return null;
			data.setUser(user);
		}
		this.answersQuestionDao.save(data);
		return info;
	}

	@Override
	public void delete(String[] ids) {
	}
	
	@Override
	public String loadStatusName(Integer status) {
		if(statusMap ==null || status==null)
		return null;
		return statusMap.get(status);
	}
	@Override
	public AnswersQuestionInfo findById(String id) {
		if(StringUtils.isEmpty(id))
		return null;
		return this.changeModel(this.answersQuestionDao.load(AnswersQuestion.class, id));
	}
}
