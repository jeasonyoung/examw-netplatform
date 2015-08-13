package com.examw.netplatform.service.admin.teachers.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.courses.ILessonDao;
import com.examw.netplatform.dao.admin.security.UserMapper;
import com.examw.netplatform.dao.admin.settings.AgencyMapper;
import com.examw.netplatform.dao.admin.teachers.IAnswerQuestionTopicDao;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionTopic;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo;
import com.examw.netplatform.service.admin.teachers.IAnswerQuestionTopicService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
import com.examw.service.Status;

/**
 * 教师答疑主题服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public class AnswerQuestionTopicServiceImpl extends BaseDataServiceImpl<AnswerQuestionTopic,AnswerQuestionTopicInfo> implements IAnswerQuestionTopicService {
	private static final Logger logger = Logger.getLogger(AnswerQuestionTopicServiceImpl.class);
	private IAnswerQuestionTopicDao answerQuestionTopicDao;
	private AgencyMapper agencyDao;
	private UserMapper userDao;
	private ILessonDao lessonDao;
	private Map<Integer, String> statusMap;
	/**
	 * 设置教师答疑主题数据接口。
	 * @param answerQuestionTopicDao 
	 *	  教师答疑主题数据接口。
	 */
	public void setAnswerQuestionTopicDao(IAnswerQuestionTopicDao answerQuestionTopicDao) {
		if(logger.isDebugEnabled()) logger.debug("注入教师答疑主题数据接口...");
		this.answerQuestionTopicDao = answerQuestionTopicDao;
	}
	/**
	 * 设置机构数据接口。
	 * @param agencyDao 
	 *	  机构数据接口。
	 */
	public void setAgencyDao(AgencyMapper agencyDao) {
		if(logger.isDebugEnabled()) logger.debug("注入机构数据接口...");
		this.agencyDao = agencyDao;
	}
	/**
	 * 设置用户数据接口。
	 * @param userDao 
	 *	  用户数据接口。
	 */
	public void setUserDao(UserMapper userDao) {
		if(logger.isDebugEnabled()) logger.debug("注入用户数据接口...");
		this.userDao = userDao;
	}
	/**
	 * 设置课时资源数据接口。
	 * @param lessonDao 
	 *	  课时资源数据接口。
	 */
	public void setLessonDao(ILessonDao lessonDao) {
		if(logger.isDebugEnabled()) logger.debug("注入课时资源数据接口...");
		this.lessonDao = lessonDao;
	}
	/**
	 * 设置状态值名称集合。
	 * @param statusMap 
	 *	  状态值名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("注入状态值名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态值名称。
	 * @see com.examw.netplatform.service.admin.teachers.IAnswerQuestionTopicService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载状态值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<AnswerQuestionTopic> find(AnswerQuestionTopicInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.answerQuestionTopicDao.findTopics(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected AnswerQuestionTopicInfo changeModel(AnswerQuestionTopic data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 AnswerQuestionTopic => AnswerQuestionTopicInfo ...");
		AnswerQuestionTopicInfo info = new AnswerQuestionTopicInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getAgency() != null){//机构
			info.setAgencyId(data.getAgency().getId());
			info.setAgencyName(data.getAgency().getName());
		}
		if(data.getUser() != null){//用户
			info.setUserId(data.getUser().getId());
			info.setUserName(data.getUser().getName());
		}
		Lesson lesson = null;
		if((lesson = data.getLesson()) != null){//课时资源
			info.setLessonId(lesson.getId());
			info.setLessonName(lesson.getName());
			ClassPlan classPlan = null;
			if((classPlan = lesson.getClassPlan()) != null){
				info.setClassId(classPlan.getId());
				info.setClassName(classPlan.getName());
			}
		}
		//状态
		info.setStatusName(this.loadStatusName(info.getStatus()));
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(AnswerQuestionTopicInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.answerQuestionTopicDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public AnswerQuestionTopicInfo update(AnswerQuestionTopicInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		boolean isAdded = false;
		AnswerQuestionTopic topic = StringUtils.isEmpty(info.getId()) ? null : this.answerQuestionTopicDao.load(AnswerQuestionTopic.class, info.getId());
		if(isAdded = (topic == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			topic = new AnswerQuestionTopic();
		}else{
			info.setCreateTime(topic.getCreateTime());
			if(info.getCreateTime() == null) info.setCreateTime(new Date());
			if(info.getStatus() == null) info.setStatus(topic.getStatus());
		}
		if(info.getStatus() == null)info.setStatus(Status.DISABLE.getValue());
		
		info.setLastTime(new Date());
		BeanUtils.copyProperties(info, topic);
		
		if(StringUtils.isEmpty(info.getAgencyId())) throw new RuntimeException("机构ID不存在！");
		topic.setAgency(this.agencyDao.load(Agency.class, info.getAgencyId()));
		if(topic.getAgency() == null) throw new RuntimeException(String.format("机构［%s］不存在！", info.getAgencyId()));
		
		//用户
		topic.setUser(StringUtils.isEmpty(info.getUserId()) ? null : this.userDao.load(User.class, info.getUserId()));
		
		topic.setLesson(StringUtils.isEmpty(info.getLessonId()) ? null : this.lessonDao.load(Lesson.class, info.getLessonId()));
		
		if(isAdded) this.answerQuestionTopicDao.save(topic);
		
		return this.changeModel(topic);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据: %s...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			AnswerQuestionTopic topic = this.answerQuestionTopicDao.load(AnswerQuestionTopic.class, ids[i]);
			if(topic != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据: %s...", ids[i]));
				this.answerQuestionTopicDao.delete(topic);
			}
		}
	}
	/*
	 * 更新教师答疑主题状态。
	 * @see com.examw.netplatform.service.admin.teachers.IAnswerQuestionTopicService#updateStatus(java.lang.String, com.examw.service.Status)
	 */
	@Override
	public void updateStatus(String topicId, Status status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("更新教师答疑主题［%1$s］状态［%2$s］...", topicId, status));
		if(StringUtils.isEmpty(topicId)) throw new RuntimeException("教师答疑主题ID不存在！");
		AnswerQuestionTopic topic = this.answerQuestionTopicDao.load(AnswerQuestionTopic.class, topicId);
		if(topic == null) throw new RuntimeException(String.format("教师答疑主题［%s］不存在！", topicId));
		topic.setStatus(status.getValue());
		topic.setLastTime(new Date());
	}
	
	@Override
	public AnswerQuestionTopicInfo conversion(AnswerQuestionTopic data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换,AnswerQuestionTopic -->AnswerQuestionTopicInfo ...");
		return this.changeModel(data);
	}
}