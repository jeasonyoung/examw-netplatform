package com.examw.netplatform.service.front.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.courses.IClassPlanDao;
import com.examw.netplatform.dao.admin.courses.IPackageDao;
import com.examw.netplatform.dao.admin.settings.CategoryMapper;
import com.examw.netplatform.dao.admin.students.ILearningDao;
import com.examw.netplatform.dao.admin.students.IOrderDao;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.domain.admin.courses.Package;
import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.domain.admin.settings.Category;
import com.examw.netplatform.domain.admin.students.Learning;
import com.examw.netplatform.domain.admin.students.Order;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionTopic;
import com.examw.netplatform.exceptions.NotValidLessonException;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.admin.courses.PackageInfo;
import com.examw.netplatform.model.admin.students.LearningInfo;
import com.examw.netplatform.model.admin.students.OrderInfo;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo;
import com.examw.netplatform.model.front.FrontClassPlanInfo;
import com.examw.netplatform.model.front.FrontLessonInfo;
import com.examw.netplatform.model.front.FrontPackageInfo;
import com.examw.netplatform.service.admin.courses.IClassPlanService;
import com.examw.netplatform.service.admin.courses.ILessonService;
import com.examw.netplatform.service.admin.courses.IPackageService;
import com.examw.netplatform.service.admin.students.ILearningService;
import com.examw.netplatform.service.admin.students.LearningStatus;
import com.examw.netplatform.service.admin.students.OrderStatus;
import com.examw.netplatform.service.admin.teachers.IAnswerQuestionTopicService;
import com.examw.netplatform.service.front.IFrontCourseService;
import com.examw.service.Status;

/**
 * 前台课程服务接口实现类
 * @author fengwei.
 * @since 2015年1月22日 下午4:33:37.
 */
public class FrontCourseServiceImpl implements IFrontCourseService {
	private static Logger logger = Logger.getLogger(FrontCourseServiceImpl.class);
	private IOrderDao orderDao;
	
	private IPackageDao packageDao;
	private IPackageService packageService;
	
	private IClassPlanDao classPlanDao;
	private IClassPlanService classPlanService;
	
	
	private ILessonService lessonService;
	
	private ILearningDao learningDao;
	private ILearningService learningService;
	private IAnswerQuestionTopicService answerQuestionTopicService;
	
	private CategoryMapper categoryDao;
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
	
	/**
	 * 设置 套餐数据接口
	 * @param packageDao
	 * 
	 */
	public void setPackageDao(IPackageDao packageDao) {
		this.packageDao = packageDao;
	}

	/**
	 * 设置 班级数据接口
	 * @param classPlanDao
	 * 
	 */
	public void setClassPlanDao(IClassPlanDao classPlanDao) {
		this.classPlanDao = classPlanDao;
	}
	
	/**
	 * 设置 课程分类数据接口
	 * @param categoryDao
	 * 
	 */
	public void setCategoryDao(CategoryMapper categoryDao) {
		this.categoryDao = categoryDao;
	}

	/*
	 * 查询用户套餐
	 * @see com.examw.netplatform.service.front.user.IFrontCourseService#findUserPackages(java.lang.String)
	 */
	@Override
	public List<FrontPackageInfo> findUserPackages(String userId) {
		List<Order> orders = this.findUserOrders(userId);
		if(orders!=null&&orders.size()>0)
		{
			if(logger.isDebugEnabled()) logger.debug(String.format("查询用户[%s]的班级信息...",userId));
			List<FrontPackageInfo> result = new ArrayList<FrontPackageInfo>();
			for(Order order:orders)
			{
				if(order.getStatus().equals(OrderStatus.OPENED.getValue()))
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
								FrontPackageInfo f_info = new FrontPackageInfo();
								BeanUtils.copyProperties(info, f_info);
								Set<ClassPlan> classes = p.getClasses();
								if(classes!=null)
								{
									List<FrontClassPlanInfo> infoClasses = new ArrayList<FrontClassPlanInfo>();
									for(ClassPlan plan:classes)
									{
										if(plan == null) continue;
										infoClasses.add(this.changeClassPlanModel(plan));
									}
									f_info.setClasses(infoClasses);
								}
								result.add(f_info);
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
				if(order.getStatus().equals(OrderStatus.OPENED.getValue()))
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
		ClassPlan data = this.classPlanDao.load(ClassPlan.class,classPlanId);
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
			Map<String, Object> model) throws NotValidLessonException {
		if(logger.isDebugEnabled()) logger.debug(String.format("查询用户[%1$s]的班级[%2$s],课时[lessonId]信息...",user,classId,lessonId));
		if(user == null || StringUtils.isEmpty(classId)) return;
		ClassPlan data = this.classPlanDao.load(ClassPlan.class,classId);
		if(data == null) throw new NotValidLessonException("课程不存在");
		//查询用户正常订单下是否有该班级,或者有效套餐下包含该班级
		if(!this.isValidClass(user.getUser().getId(), classId))
			throw new NotValidLessonException("课程不存在");
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
			if(currentLesson == null) throw new NotValidLessonException("该链接课时不存在");
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
	/*
	 * 增加问题
	 * @see com.examw.netplatform.service.front.user.IFrontCourseService#saveQuestionTopic(com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo)
	 */
	@Override
	public AnswerQuestionTopicInfo saveQuestionTopic(AnswerQuestionTopicInfo info)
	{
		if(logger.isDebugEnabled()) logger.debug("保存学员提问");
		return this.answerQuestionTopicService.update(info);
	}
	
	/**
	 * 判断是否是有效的课程
	 * @param userId
	 * @param classId
	 * @return
	 */
	private boolean isValidClass(String userId,String classId)
	{
		List<Order> orders = this.findUserOrders(userId);
		if(orders!=null&&orders.size()>0)
		{
			for(Order order:orders)
			{
				if(order.getStatus().equals(OrderStatus.OPENED.getValue()))
				{
					Set<ClassPlan> classes = order.getClasses();
					if(classes !=null && !classes.isEmpty())
					{
						for(ClassPlan plan:classes)
						{
							if(plan.getId().equals(classId))
							{
								return true;
							}
						}
					}
					Set<com.examw.netplatform.domain.admin.courses.Package> packages = order.getPackages();
					if(packages != null && packages.size() > 0)
					{
						for(Package p:packages)
						{
							if(p == null) continue;
							Set<ClassPlan> plans = p.getClasses();
							for(ClassPlan plan:plans)
							{
								if(plan == null) continue;
								if(plan.getId().equals(classId))
								{
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	
	
	/*
	 * 查询机构的班级
	 * @see com.examw.netplatform.service.front.user.IFrontCourseService#findAgencyClassPlans(com.examw.netplatform.model.admin.courses.ClassPlanInfo)
	 */
	@Override
	public List<FrontClassPlanInfo> findAgencyClassPlans(ClassPlanInfo info) {
		if(info==null || StringUtils.isEmpty(info.getAgencyId()))
			return null;
		if(!StringUtils.isEmpty(info.getCategoryId()))
		{
			info.setCategoryId(this.getAllCategoryId(info.getCategoryId()));
		}
		List<ClassPlan> list = this.classPlanDao.findClassPlans(info);
		if(list==null) return null;
		return this.changeClassPlanModel(list);
	}
	
	private String getAllCategoryId(String categoryId) {
		Category category = this.categoryDao.load(Category.class, categoryId);
		if(category == null) return null;
		return getChildCategoryId(category);
	}
	
	private String getChildCategoryId(Category category)
	{
		if(category.getChildren()==null || category.getChildren().size()==0) return category.getId();
		StringBuffer buf = new StringBuffer();
		buf.append(category.getId()).append(",");
		for(Category data:category.getChildren())
		{
			if(data==null) continue;
			buf.append(this.getChildCategoryId(data)).append(",");
		}
		return buf.toString();
	}

	@Override
	public List<FrontPackageInfo> findAgencyPackages(PackageInfo info) {
		if(info==null || StringUtils.isEmpty(info.getAgencyId()))
			return null;
		if(!StringUtils.isEmpty(info.getCategoryId()))
		{
			info.setCategoryId(this.getAllCategoryId(info.getCategoryId()));
		}
		List<Package> list = this.packageDao.findPackages(info);
		if(list==null) return null;
		return this.changePackageModel(list);
	}
	@Override
	public Long totalAgencyClassPlans(ClassPlanInfo info) {
		return this.classPlanDao.totalEnableClassPlan(info);
	}
	@Override
	public Long totalAgencyPackages(PackageInfo info) {
		return this.packageDao.totalEnablePackage(info);
	}
	/**
	 * 套餐集合转换
	 * @param list
	 * @return
	 */
	private List<FrontPackageInfo> changePackageModel(List<Package> list){
		List<FrontPackageInfo> result = new ArrayList<FrontPackageInfo>();
		for(Package data:list)
		{
			if(data == null) continue;	//不存在
			if(data.getStatus().equals(Status.DISABLE.getValue())) continue;	//没有启用
			if(data.isOverdue()) continue;	//已过期
			//所属考试状态为禁用
			if(data.getExam()!=null&& !data.getExam().getStatus().equals(Status.ENABLED.getValue())) continue;
			//
			result.add(this.changePackageModel(data,false));
		}
		return result;
	}
	/**
	 * 套餐类型转换[这里不再进行为空等条件判断]
	 * @param data
	 * @return
	 */
	private FrontPackageInfo changePackageModel(Package data,boolean isLoadAll)
	{
		FrontPackageInfo frontInfo = new FrontPackageInfo();
		BeanUtils.copyProperties(this.packageService.conversion(data), frontInfo);
		frontInfo.setTotalStudents(data.getOrders()==null?0:data.getOrders().size());
		//计算课时
		if(data.getClasses()!=null)
		{
			int totalHours = 0;
			StringBuffer buf = new StringBuffer();
			StringBuffer idBuf = new StringBuffer();
			List<FrontClassPlanInfo> classList = null;
			if(isLoadAll) classList = new ArrayList<FrontClassPlanInfo>();
			for(ClassPlan plan : data.getClasses())
			{
				if(plan ==null) continue;
				totalHours += plan.getTotalHours()==null?0:plan.getTotalHours();
				if(plan.getUsers()!=null)
				{
					for(AgencyUser user:plan.getUsers())
					{
						if(idBuf.toString().contains(user.getUser().getId())) continue;
						idBuf.append(user.getUser().getId()).append(",");
						buf.append(user.getUser().getName());
						buf.append(",");
					}
				}
				//如果加载全部
				if(isLoadAll)
				{
					FrontClassPlanInfo classInfo = this.changeClassPlanModel(plan);
					//加载课时
					Set<Lesson> lessons = plan.getLessons();
					if(lessons!=null && lessons.size()>0)
					{
						List<FrontLessonInfo> lessonList = new ArrayList<FrontLessonInfo>();
						for(Lesson less:lessons)
						{
							if(less == null) continue;
							FrontLessonInfo lessInfo = new FrontLessonInfo();
							BeanUtils.copyProperties(this.lessonService.conversion(less), lessInfo);
							lessonList.add(lessInfo);
						}
						classInfo.setLessons(lessonList);
					}
					classList.add(classInfo);
				}
			}
			frontInfo.setClasses(classList);
			frontInfo.setTotalHours(totalHours);
			if(buf.length()>0)
			{
				//获取老师
				frontInfo.setTeacherName(buf.substring(0,buf.length()-1));
			}
			buf = null; idBuf = null;
		}
		return frontInfo;
	}
	/**
	 * 班级集合转换
	 * @param list
	 * @return
	 */
	private List<FrontClassPlanInfo> changeClassPlanModel(List<ClassPlan> list){
		List<FrontClassPlanInfo> result = new ArrayList<FrontClassPlanInfo>();
		for(ClassPlan data:list)
		{
			if(data == null) continue;
			if(data.getStatus().equals(Status.DISABLE.getValue())) continue;	//没有启用
			if(data.isOverdue()) continue;	//已过期
			//所属考试状态为禁用
			if(data.getSubject()!=null&& !data.getSubject().getExam().getStatus().equals(Status.ENABLED.getValue())) continue;
			//
			result.add(this.changeClassPlanModel(data));
		}
		return result;
	}
	/**
	 * 班级类型转换[这里不再进行为空等条件判断]
	 * @param list
	 * @return
	 */
	private FrontClassPlanInfo changeClassPlanModel(ClassPlan data)
	{
		FrontClassPlanInfo frontInfo = new FrontClassPlanInfo();
		BeanUtils.copyProperties(this.classPlanService.conversion(data), frontInfo);
		frontInfo.setTotalStudents(data.getOrders()==null?0:data.getOrders().size());
		//获取老师
		if(data.getUsers()!=null)
		{
			StringBuffer buf = new StringBuffer();
			for(AgencyUser user:data.getUsers())
			{
				buf.append(user.getUser().getName());
				buf.append(",");
			}
			if(buf.length()>0)
			{
				frontInfo.setTeacherName(buf.substring(0,buf.length()-1));
			}
			buf = null;
		}
		return frontInfo;
	}
	/*
	 * 查询热门班级
	 * @see com.examw.netplatform.service.front.user.IFrontCourseService#findHotAgencyClassPlans(com.examw.netplatform.model.admin.courses.ClassPlanInfo)
	 */
	@Override
	public List<FrontClassPlanInfo> findHotAgencyClassPlans(ClassPlanInfo info) {
		if(info==null || StringUtils.isEmpty(info.getAgencyId()))
			return null;
		if(!StringUtils.isEmpty(info.getCategoryId()))
		{
			info.setCategoryId(this.getAllCategoryId(info.getCategoryId()));
		}
		List<ClassPlan> list = this.classPlanDao.findHotClassPlans(info);
		if(list==null) return null;
		return this.changeClassPlanModel(list);
	}
	/*
	 * 查询热门套餐
	 * @see com.examw.netplatform.service.front.user.IFrontCourseService#findHotAgencyPackages(com.examw.netplatform.model.admin.courses.PackageInfo)
	 */
	@Override
	public List<FrontPackageInfo> findHotAgencyPackages(PackageInfo info) {
		if(info==null || StringUtils.isEmpty(info.getAgencyId()))
			return null;
		if(!StringUtils.isEmpty(info.getCategoryId()))
		{
			info.setCategoryId(this.getAllCategoryId(info.getCategoryId()));
		}
		List<Package> list = this.packageDao.findHotPackages(info);
		if(list==null) return null;
		return this.changePackageModel(list);
	}
	@Override
	public FrontClassPlanInfo findFrontClassPlanInfo(String classId) {
		if(StringUtils.isEmpty(classId)) return null;
		ClassPlan data = this.classPlanDao.load(ClassPlan.class,classId);
		if(data == null) return null;
		FrontClassPlanInfo info = this.changeClassPlanModel(data);
		Set<Lesson> lessons = data.getLessons();
		if(lessons!=null && lessons.size()>0)
		{
			List<FrontLessonInfo> lessonList = new ArrayList<FrontLessonInfo>();
			for(Lesson less:lessons)
			{
				if(less == null) continue;
				FrontLessonInfo lessInfo = new FrontLessonInfo();
				BeanUtils.copyProperties(this.lessonService.conversion(less), lessInfo);
				lessonList.add(lessInfo);
			}
			info.setLessons(lessonList);
		}
		return info;
	}
	@Override
	public FrontPackageInfo findFrontPackageInfo(String packageId) {
		if(StringUtils.isEmpty(packageId)) return null;
		Package data = this.packageDao.load(Package.class, packageId);
		if(data == null) return null;
		return this.changePackageModel(data, true);
	}
}
