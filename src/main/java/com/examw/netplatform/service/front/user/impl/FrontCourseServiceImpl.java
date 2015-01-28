package com.examw.netplatform.service.front.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.students.ILearningDao;
import com.examw.netplatform.dao.admin.students.IOrderDao;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.domain.admin.courses.Package;
import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.domain.admin.students.Learning;
import com.examw.netplatform.domain.admin.students.Order;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionTopic;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.admin.courses.PackageInfo;
import com.examw.netplatform.model.admin.students.LearningInfo;
import com.examw.netplatform.model.admin.students.OrderInfo;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo;
import com.examw.netplatform.model.front.FrontClassPlanInfo;
import com.examw.netplatform.model.front.FrontLessonInfo;
import com.examw.netplatform.service.admin.courses.IClassPlanService;
import com.examw.netplatform.service.admin.courses.ILessonService;
import com.examw.netplatform.service.admin.courses.IPackageService;
import com.examw.netplatform.service.admin.students.ILearningService;
import com.examw.netplatform.service.admin.students.LearningStatus;
import com.examw.netplatform.service.admin.teachers.IAnswerQuestionTopicService;
import com.examw.netplatform.service.front.user.IFrontCourseService;
import com.examw.service.Status;

/**
 * 前台课程服务接口实现类
 * @author fengwei.
 * @since 2015年1月22日 下午4:33:37.
 */
public class FrontCourseServiceImpl implements IFrontCourseService {
	private static Logger logger = Logger.getLogger(FrontCourseServiceImpl.class);
	private IOrderDao orderDao;
	
	private IPackageService packageService;
	
	private IClassPlanService classPlanService;
	private ILessonService lessonService;
	
	private ILearningDao learningDao;
	private ILearningService learningService;
	private IAnswerQuestionTopicService answerQuestionTopicService;
	/**
	 * 设置 订单数据接口
	 * @param orderDao
	 * 
	 */
	public void setOrderDao(IOrderDao orderDao) {
		this.orderDao = orderDao;
	}

	/**
	 * 设置 套餐数据接口
	 * @param packageService
	 * 
	 */
	public void setPackageService(IPackageService packageService) {
		this.packageService = packageService;
	}
	
	/**
	 * 设置 学习进度服务接口
	 * @param learningService
	 * 
	 */
	public void setLearningService(ILearningService learningService) {
		this.learningService = learningService;
	}

	/**
	 * 设置 班级数据接口
	 * @param classPlanService
	 * 
	 */
	public void setClassPlanService(IClassPlanService classPlanService) {
		this.classPlanService = classPlanService;
	}
	
	/**
	 * 设置 课时服务接口
	 * @param lessonService
	 * 
	 */
	public void setLessonService(ILessonService lessonService) {
		this.lessonService = lessonService;
	}

	/**
	 * 设置 学习进度数据接口
	 * @param learningDao
	 * 
	 */
	public void setLearningDao(ILearningDao learningDao) {
		this.learningDao = learningDao;
	}
	
	/**
	 * 设置 问答主题服务接口
	 * @param answerQuestionTopicService
	 * 
	 */
	public void setAnswerQuestionTopicService(
			IAnswerQuestionTopicService answerQuestionTopicService) {
		this.answerQuestionTopicService = answerQuestionTopicService;
	}

	/*
	 * 查询用户套餐
	 * @see com.examw.netplatform.service.front.user.IFrontCourseService#findUserPackages(java.lang.String)
	 */
	@Override
	public List<PackageInfo> findUserPackages(String userId) {
		List<Order> orders = this.findUserOrders(userId);
		if(orders!=null&&orders.size()>0)
		{
			if(logger.isDebugEnabled()) logger.debug(String.format("查询用户[%s]的班级信息...",userId));
			List<PackageInfo> result = new ArrayList<PackageInfo>();
			for(Order order:orders)
			{
				if(order.getStatus().equals(Status.ENABLED.getValue()))
				{
					Set<com.examw.netplatform.domain.admin.courses.Package> packages = order.getPackages();
					if(packages != null && packages.size() > 0)
					{
						for(Package p:packages)
						{
							if(p == null) continue;
							PackageInfo info = this.packageService.conversion(p);
							if(info!=null)
							{
								result.add(info);
							}
						}
					}
				}
			}
			return result;
		}
		return null;
	}
	
	/**
	 * 查询用户班级
	 */
	@Override
	public List<ClassPlanInfo> findUserClassPlans(String userId) {
		List<Order> orders = this.findUserOrders(userId);
		if(orders!=null&&orders.size()>0)
		{
			if(logger.isDebugEnabled()) logger.debug(String.format("查询用户[%s]的班级信息...",userId));
			List<ClassPlanInfo> result = new ArrayList<ClassPlanInfo>();
			for(Order order:orders)
			{
				if(order.getStatus().equals(Status.ENABLED.getValue()))
				{
					Set<ClassPlan> plans = order.getClasses();
					if(plans != null && plans.size() > 0)
					{
						for(ClassPlan plan:plans)
						{
							if(plan == null) continue;
							ClassPlanInfo info = this.classPlanService.conversion(plan);
							if(info!=null)
							{
								result.add(info);
							}
						}
					}
				}
			}
			return result;
		}
		return null;
	}
	/*
	 * 查询班级信息
	 * @see com.examw.netplatform.service.front.user.IFrontCourseService#findClassPlan(java.lang.String, java.lang.String)
	 */
	@Override
	public FrontClassPlanInfo findClassPlan(String userId, String classPlanId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("查询用户[%1$s]的班级[%2$s]信息...",userId,classPlanId));
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(classPlanId)) return null;
		ClassPlan data = this.classPlanService.loadClassPlan(classPlanId);
		if(data == null) return null;
		FrontClassPlanInfo info = new FrontClassPlanInfo();
		BeanUtils.copyProperties(this.classPlanService.conversion(data), info);
		info.setTotalStudents(data.getOrders()==null?0:data.getOrders().size());
		Set<Lesson> lessons = data.getLessons();
		if(lessons!=null && lessons.size()>0)
		{
			List<FrontLessonInfo> lessonList = new ArrayList<FrontLessonInfo>();
			for(Lesson less:lessons)
			{
				if(less == null) continue;
				FrontLessonInfo lessInfo = new FrontLessonInfo();
				BeanUtils.copyProperties(this.lessonService.conversion(less), lessInfo);
				Learning learning = this.learningDao.findLastLearning(userId,less.getId());
				if(learning == null || learning.getStatus()==null)
					lessInfo.setLearningStatus(LearningStatus.NONE.getValue());
				else
					lessInfo.setLearningStatus(learning.getStatus());
				lessonList.add(lessInfo);
			}
			info.setLessons(lessonList);
		}
		return info;
	}
	/*
	 * 查询用户订单
	 * @see com.examw.netplatform.service.front.user.IFrontCourseService#findUserOrders(java.lang.String)
	 */
	@Override
	public List<Order> findUserOrders(final String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("查询用户[%s]的订单信息...",userId));
		if(StringUtils.isEmpty(userId))
			return null;
		return this.orderDao.findOrders(new OrderInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getStudentId(){ return userId;}
		});
	}
	/*
	 * 查询课时信息
	 * @see com.examw.netplatform.service.front.user.IFrontCourseService#findLessonInfo(java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public void findLessonInfo(AgencyUser user, String classId, String lessonId,
			Map<String, Object> model) {
		if(logger.isDebugEnabled()) logger.debug(String.format("查询用户[%1$s]的班级[%2$s],课时[lessonId]信息...",user,classId,lessonId));
		if(user == null || StringUtils.isEmpty(classId)) return;
		ClassPlan data = this.classPlanService.loadClassPlan(classId);
		if(data == null) return;
		FrontClassPlanInfo info = new FrontClassPlanInfo();
		BeanUtils.copyProperties(this.classPlanService.conversion(data), info);
		info.setTotalStudents(data.getOrders()==null?0:data.getOrders().size());
		Set<Lesson> lessons = data.getLessons();
		FrontLessonInfo currentLesson = null;
		int currentIndex = 0;
		if(lessons!=null && lessons.size()>0)
		{
			List<FrontLessonInfo> lessonList = new ArrayList<FrontLessonInfo>();
			for(Lesson less:lessons)
			{
				if(less == null) continue;
				FrontLessonInfo lessInfo = new FrontLessonInfo();
				BeanUtils.copyProperties(this.lessonService.conversion(less), lessInfo);
				Learning learning = this.learningDao.findLastLearning(user.getUser().getId(),less.getId());
				if(learning == null || learning.getStatus()==null)
					lessInfo.setLearningStatus(LearningStatus.NONE.getValue());
				else
					lessInfo.setLearningStatus(learning.getStatus());
				if(less.getId().equals(lessonId))
				{
					currentLesson = lessInfo;
					currentIndex = lessonList.size();
					if(learning == null ||learning.getStatus().equals(LearningStatus.LEARNED.getValue())) //播放记录
					{
						//增加播放记录
						Integer status = LearningStatus.LEARNING.getValue();
						if(learning != null && learning.getStatus().equals(LearningStatus.LEARNED.getValue())) 
							status = LearningStatus.LEARNED.getValue();
						learning = new Learning();
						learning.setAgency(user.getAgency());
						learning.setCreateTime(new Date());
						learning.setId(UUID.randomUUID().toString());
						learning.setUser(user.getUser());
						learning.setLesson(less);
						learning.setStatus(status);
						learning.setLearnedTime(0);
						learningDao.save(learning);
					}
					model.put("LEARNING_RECORD_ID",learning.getId());
					model.put("INIT_SECOND", learning.getLearnedTime());
					Set<AnswerQuestionTopic> questions = less.getTopics();
					if(questions!=null)
					{
						List<AnswerQuestionTopicInfo> topics = new ArrayList<AnswerQuestionTopicInfo>();
						for(AnswerQuestionTopic question:questions)
						{
							if(question == null) continue;
							AnswerQuestionTopicInfo questionInfo = this.answerQuestionTopicService.conversion(question);
							topics.add(questionInfo);
						}
						model.put("", "");
					}
				}
				lessonList.add(lessInfo);
			}
			info.setLessons(lessonList);
			if(currentIndex > 0)
			{
				model.put("LAST_LESSON_ID",lessonList.get(currentIndex-1).getId());
			}
			if(currentIndex < lessonList.size()-1)
			{
				model.put("NEXT_LESSON_ID",lessonList.get(currentIndex+1).getId());
			}
		}
		model.put("CLASSPLAN", info);
		model.put("CURRENTLESSON", currentLesson);
	}
	/*
	 * 增加进度
	 * @see com.examw.netplatform.service.front.user.IFrontCourseService#saveLearningRecord(com.examw.netplatform.model.admin.students.LearningInfo)
	 */
	@Override
	public boolean saveLearningRecord(LearningInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新学习进度");
		this.learningService.update(info);
		return true;
	}
	@Override
	public AnswerQuestionTopicInfo saveQuestionTopic(AnswerQuestionTopicInfo info)
	{
		if(logger.isDebugEnabled()) logger.debug("保存学员提问");
		return this.answerQuestionTopicService.update(info);
	}
}
