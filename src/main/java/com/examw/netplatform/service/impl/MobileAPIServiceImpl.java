package com.examw.netplatform.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.netplatform.dao.admin.courses.LessonMapper;
import com.examw.netplatform.dao.admin.courses.PackageMapper;
import com.examw.netplatform.dao.admin.settings.CategoryMapper;
import com.examw.netplatform.dao.admin.settings.ExamMapper;
import com.examw.netplatform.dao.admin.students.LearningMapper;
import com.examw.netplatform.dao.admin.students.OrderMapper;
import com.examw.netplatform.dao.admin.teachers.AnswerQuestionMapper;
import com.examw.netplatform.domain.admin.courses.BaseLesson;
import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.domain.admin.courses.PackageAndClass;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.security.UserIdentity;
import com.examw.netplatform.domain.admin.settings.Category;
import com.examw.netplatform.domain.admin.settings.Exam;
import com.examw.netplatform.domain.admin.students.BaseLearning;
import com.examw.netplatform.domain.admin.students.Learning;
import com.examw.netplatform.domain.admin.students.UserOrdersView;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionDetail;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionTopic;
import com.examw.netplatform.domain.admin.teachers.Suggest;
import com.examw.netplatform.service.MobileAPIService;
import com.examw.netplatform.service.admin.security.IUserAuthorization;
import com.examw.netplatform.support.PasswordHelper;
import com.examw.service.Status;

/**
 * 移动API接口服务实现类。
 * 
 * @author jeasonyoung
 * @since 2015年8月28日
 */
public class MobileAPIServiceImpl implements MobileAPIService {
	private static final Logger logger = Logger.getLogger(MobileAPIServiceImpl.class);
	private Cache cache;
	private CategoryMapper categoryDao;
	private ExamMapper examDao;
	private PackageMapper packageDao;
	private OrderMapper orderDao;
	private LessonMapper lessonDao;
	private LearningMapper learningDao;
	private AnswerQuestionMapper answerQuestionDao;
	private IUserAuthorization userAuthorization;
	private PasswordHelper passwordHelper;
	/**
	 * 设置Cache缓存。
	 * @param cache 
	 *	  Cache缓存。
	 */
	public void setCache(Cache cache) {
		logger.debug("注入Cache缓存...");
		this.cache = cache;
	}
	/**
	 * 设置考试类别数据接口。
	 * @param categoryDao 
	 *	  考试类别数据接口。
	 */
	public void setCategoryDao(CategoryMapper categoryDao) {
		logger.debug("注入考试类别数据接口...");
		this.categoryDao = categoryDao;
	}
	/**
	 * 设置考试数据接口。
	 * @param examDao 
	 *	  考试数据接口。
	 */
	public void setExamDao(ExamMapper examDao) {
		logger.debug("注入考试数据接口...");
		this.examDao = examDao;
	}
	/**
	 * 设置套餐数据接口。
	 * @param packageDao 
	 *	  套餐数据接口。
	 */
	public void setPackageDao(PackageMapper packageDao) {
		logger.debug("注入套餐数据接口...");
		this.packageDao = packageDao;
	}
	/**
	 * 设置订单数据接口。
	 * @param orderDao 
	 *	  订单数据接口。
	 */
	public void setOrderDao(OrderMapper orderDao) {
		logger.debug("注入订单数据接口...");
		this.orderDao = orderDao;
	}
	/**
	 * 设置课时资源数据接口。
	 * @param lessonDao 
	 *	  课时资源数据接口。
	 */
	public void setLessonDao(LessonMapper lessonDao) {
		logger.debug("注入课时资源数据接口...");
		this.lessonDao = lessonDao;
	}
	/**
	 * 设置进度数据接口。
	 * @param learningDao 
	 *	  进度数据接口。
	 */
	public void setLearningDao(LearningMapper learningDao) {
		logger.debug("注入进度数据接口...");
		this.learningDao = learningDao;
	}
	/**
	 * 设置答疑数据接口。
	 * @param answerQuestionDao 
	 *	  答疑数据接口。
	 */
	public void setAnswerQuestionDao(AnswerQuestionMapper answerQuestionDao) {
		logger.debug("注入答疑数据接口...");
		this.answerQuestionDao = answerQuestionDao;
	}
	/**
	 * 设置学员用户认证服务接口。
	 * @param userAuthorization 
	 *	  学员用户认证服务接口。
	 */
	public void setUserAuthorization(IUserAuthorization userAuthorization) {
		logger.debug("注入学员用户认证服务接口....");
		this.userAuthorization = userAuthorization;
	}
	/**
	 * 设置密码帮助类。
	 * @param passwordHelper 
	 *	  密码帮助类。
	 */
	public void setPasswordHelper(PasswordHelper passwordHelper) {
		logger.debug("注入密码帮助类...");
		this.passwordHelper = passwordHelper;
	}
	/*
	 * 验证学员用户。
	 * @see com.examw.netplatform.service.MobileAPIService#authen(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String authen(String agencyId, String username, String pwd) throws Exception {
		logger.debug("验证机构["+agencyId+"]用户["+username+","+pwd+"]登录...");
		//加载用户
		final User user = this.userAuthorization.loadUserByAccount(agencyId, username);
		if(user == null) throw new Exception("机构["+agencyId+"]下用户["+username+"]账号不存在!");
		//检查用户身份
		if(user.getIdentity() != UserIdentity.STUDENT.getValue()){
			throw new Exception("用户身份["+UserIdentity.parse(user.getIdentity())+"]不是学员!");
		}
		//解密密码
		final String password = this.passwordHelper.decryptAESPassword(user);
		//重构密码
		final String pwdEncypt = DigestUtils.md5Hex(DigestUtils.md5Hex(agencyId + username) + password);
		//比较密码
		if(!StringUtils.equalsIgnoreCase(pwd, pwdEncypt)){
			throw new Exception("密码错误!");
		}
		return user.getId();
	}
	/*
	 * 加载用户订单套餐/班级集合。
	 * @see com.examw.netplatform.service.MobileAPIService#ordersByUser(java.lang.String)
	 */
	@Override
	public synchronized List<UserOrdersView> ordersByUser(String userId) {
		logger.debug("加载用户["+userId+"]订单套餐/班级集合...");
		if(StringUtils.isBlank(userId)) return new ArrayList<UserOrdersView>();
		//从缓存中加载数据。
		final String key = "UserOrdersView_" + userId;
		final Element element = this.cache.get(key);
		if(element != null && element.getObjectValue() != null){
			logger.debug("从缓存中加载数据...");
			final UserOrdersView[] ordersViews = (UserOrdersView[])element.getObjectValue();
			if(ordersViews != null && ordersViews.length > 0) return Arrays.asList(ordersViews);
		}
		//从数据库中加载数据
		final List<UserOrdersView> ordersViewsList = this.orderDao.findOrdersViewsByUser(userId);
		if(ordersViewsList != null && ordersViewsList.size() > 0){
			//添加到缓存
			this.cache.put(new Element(key, ordersViewsList.toArray(new UserOrdersView[0])));
		}
		return ordersViewsList;
	}
	/*
	 * 加载班级下课程资源集合。
	 * @see com.examw.netplatform.service.MobileAPIService#lessonsByClass(java.lang.String)
	 */
	@Override
	public List<BaseLesson> loadLessonsByClass(String classId) {
		logger.debug("加载班级["+classId+"]下课程资源集合...");
		if(StringUtils.isBlank(classId)) return new ArrayList<BaseLesson>();
		//从缓存中加载
		final String key = "BaseLesson_" + classId;
		final Element element = this.cache.get(key);
		if(element != null && element.getObjectValue() != null){
			logger.debug("从缓存中加载数据...");
			final BaseLesson[] lessons = (BaseLesson[])element.getObjectValue();
			if(lessons != null && lessons.length > 0) return Arrays.asList(lessons);
		}
		//从数据库中加载数据
		final List<BaseLesson> list = new ArrayList<BaseLesson>();
		if(StringUtils.isNotBlank(classId)){
			//加载数据。
			final List<Lesson> lessons = this.lessonDao.findLessonsByClass(classId);
			if(lessons != null && lessons.size() > 0){
				//数据类型转换
				for(Lesson lesson : lessons){
					if(lesson == null) continue;
					final BaseLesson data = new BaseLesson();
					BeanUtils.copyProperties(lesson, data);
					list.add(data);
				}
			}
			//添加到缓存中
			this.cache.put(new Element(key, list.toArray(new BaseLesson[0])));
		}
		return list;
	}
	/*
	 * 加载班级下免费课程资源。
	 * @see com.examw.netplatform.service.MobileAPIService#loadFreeLessonsByClass(java.lang.String)
	 */
	@Override
	public List<BaseLesson> loadFreeLessonsByClass(String classId) {
		logger.debug("加载班级["+classId+"]下免费课程资源...");
		if(StringUtils.isBlank(classId))return new ArrayList<BaseLesson>();
		//从缓存中加载
		final String key = "free_BaseLesson_" + classId;
		final Element element = this.cache.get(key);
		if(element != null && element.getObjectValue() != null){
			logger.debug("从缓存中加载数据...");
			final BaseLesson[] lessons = (BaseLesson[])element.getObjectValue();
			if(lessons != null && lessons.length > 0) return Arrays.asList(lessons);
		}
		//从数据库中加载数据
		final List<BaseLesson> list = new ArrayList<BaseLesson>();
		if(StringUtils.isNotBlank(classId)){
			//加载数据。
			final List<Lesson> lessons = this.lessonDao.findFreeLessonsByClass(classId);
			if(lessons != null && lessons.size() > 0){
				//数据类型转换
				for(Lesson lesson : lessons){
					if(lesson == null) continue;
					final BaseLesson data = new BaseLesson();
					BeanUtils.copyProperties(lesson, data);
					list.add(data);
				}
			}
			//添加到缓存中
			this.cache.put(new Element(key, list.toArray(new BaseLesson[0])));
		}
		return list;
	}
	/*
	 * 上传学习进度。
	 * @see com.examw.netplatform.service.MobileAPIService#pushLearning(com.examw.netplatform.domain.admin.students.BaseLearning)
	 */
	@Override
	public void pushLearning(BaseLearning data) {
		logger.debug("上传学习进度...");
		if(data != null){
			final Learning learning = new Learning();
			BeanUtils.copyProperties(data, learning);
			this.learningDao.updateLearning(learning);
		}
	}
	/*
	 * 考试类别集合。
	 * @see com.examw.netplatform.service.MobileAPIService#getCategories()
	 */
	@Override
	public List<Category> getCategories() {
		logger.debug("考试类别集合...");
		//从缓存中加载
		final String key = "Category";
		final Element element = this.cache.get(key);
		if(element != null && element.getObjectValue() != null){
			logger.debug("从缓存中加载数据...");
			final Category[] categories = (Category[])element.getObjectValue();
			if(categories != null && categories.length > 0) return Arrays.asList(categories);
		}
		//从数据库中加载数据
		final List<Category> categories = this.categoryDao.findCategorysHasExams();
		if(categories != null && categories.size() > 0){
			//
			for(Category category : categories){
				if(category == null) continue;
				category.setPid(null);
			}
			//添加到缓存
			this.cache.put(new Element(key, categories.toArray(new Category[0])));
			//返回
			return categories;
		}
		//
		return new ArrayList<Category>();
	}
	/*
	 * 加载考试类别下的考试集合。
	 * @see com.examw.netplatform.service.MobileAPIService#loadExamsByCategory(java.lang.String)
	 */
	@Override
	public List<Exam> loadExamsByCategory(String categoryId) {
		logger.debug("加载考试类别["+categoryId+"]下的考试集合...");
		//从缓存中加载
		final String key = "Exam_" + categoryId;
		final Element element = this.cache.get(key);
		if(element != null && element.getObjectValue() != null){
			logger.debug("从缓存中加载数据...");
			final Exam[] exams = (Exam[])element.getObjectValue();
			if(exams != null && exams.length > 0) return Arrays.asList(exams);
		}
		//从数据库中加载数据
		final List<Exam> exams = this.examDao.findExamsByCategory(categoryId, Status.ENABLED.getValue());
		if(exams != null && exams.size() > 0){
			for(Exam exam : exams){
				if(exam == null) continue;
				exam.setCategoryId(null);
				exam.setCategoryName(null);
				exam.setStatus(null);
			}
			//添加到缓存
			this.cache.put(new Element(key, exams.toArray(new Exam[0])));
			//返回
			return exams;
		}
		//
		return new ArrayList<Exam>();
	}
	/*
	 * 加载机构考试下套餐/班级集合。
	 * @see com.examw.netplatform.service.MobileAPIService#loadPackageAndClassesByExam(java.lang.String)
	 */
	@Override
	public List<PackageAndClass> loadPackageAndClassesByAgencyExam(String agencyId, String examId){
		logger.debug("加载机构["+agencyId+"]考试["+examId+"]下套餐/班级集合...");
		//从缓存中加载
		final String key = "PackageAndClass_"+agencyId+"_exam_" + examId;
		final Element element = this.cache.get(key);
		if(element != null && element.getObjectValue() != null){
			logger.debug("从缓存中加载数据...");
			final PackageAndClass[] packageClasses = (PackageAndClass[])element.getObjectValue();
			if(packageClasses != null && packageClasses.length > 0) return Arrays.asList(packageClasses);
		}
		//从数据库中加载数据
		final List<PackageAndClass> list = this.packageDao.loadPackageAndClassesByAgencyExam(agencyId, examId);
		if(list != null && list.size() > 0){
			for(PackageAndClass data : list){
				if(data == null) continue;
				data.setAgencyId(null);
				data.setExamId(null);
			}
			//添加到缓存
			this.cache.put(new Element(key, list.toArray(new PackageAndClass[0])));
			//返回
			return list;
		}
		//
		return new ArrayList<PackageAndClass>();
	}
	/*
	 * 加载机构学员下答疑主题。
	 * @see com.examw.netplatform.service.MobileAPIService#loadAnswerQuestionTopic(java.lang.String, java.lang.String)
	 */
	@Override
	public List<AnswerQuestionTopic> loadAnswerQuestionTopic(String agencyId, String userId) {
		logger.debug("加载机构["+agencyId+"]学员["+userId+"]下答疑主题...");
		if(StringUtils.isNotBlank(agencyId) && StringUtils.isNotBlank(userId)){
			//缓存键
			final String key = "AnswerQuestionTopic_" + agencyId + "_" + userId;
			//从缓存中获取数据
			final Element element = this.cache.get(key);
			if(element != null && element.getObjectValue() != null){
				logger.debug("从缓存中加载数据...");
				final AnswerQuestionTopic[] topics = (AnswerQuestionTopic[])element.getObjectValue();
				if(topics != null) return Arrays.asList(topics);
			}
			//查询条件
			final AnswerQuestionTopic where = new AnswerQuestionTopic();
			where.setAgencyId(agencyId);
			where.setStudentId(userId);
			//初始化
			final List<AnswerQuestionTopic> results = new ArrayList<AnswerQuestionTopic>();
			//查询数据
			final List<AnswerQuestionTopic> topics = this.answerQuestionDao.findTopics(where);
			if(topics != null && topics.size() > 0){
				for(AnswerQuestionTopic topic : topics){
					if(topic == null) continue;
					//初始化
					final AnswerQuestionTopic data = new AnswerQuestionTopic();
					//主题ID
					data.setId(topic.getId());
					//所属课程资源ID
					data.setLessonId(topic.getLessonId());
					//所属课程资源名称
					data.setLessonName(topic.getLessonName());
					//标题
					data.setTitle(topic.getTitle());
					//内容
					data.setContent(topic.getContent());
					//时间
					data.setLastTime(data.getLastTime());
					//
					results.add(data);
				}
			}
			//添加到缓存
			this.cache.put(new Element(key, results.toArray(new AnswerQuestionTopic[0])));
			//返回
			return results;
		}
		return null;
	}
	/*
	 * 新增机构答疑主题。
	 * @see com.examw.netplatform.service.MobileAPIService#addAnswerQuestionTopic(com.examw.netplatform.domain.admin.teachers.AnswerQuestionTopic)
	 */
	@Override
	public void addAnswerQuestionTopic(AnswerQuestionTopic topic) throws Exception {
		logger.debug("新增机构答疑主题...");
		if(topic == null)  throw new Exception("未获取数据!");
		if(StringUtils.isBlank(topic.getAgencyId())) throw new Exception("所属机构ID不能为空!");
		if(StringUtils.isBlank(topic.getLessonId())) throw new Exception("所属课程资源ID不能为空!");
		if(StringUtils.isBlank(topic.getStudentId())) throw new Exception("所属学员ID不能为空!");
		//设置ID
		topic.setId(UUID.randomUUID().toString());
		//插入数据
		this.answerQuestionDao.insertTopic(topic);
		//缓存键
		final String key = "AnswerQuestionTopic_" + topic.getAgencyId() + "_" + topic.getStudentId();
		//清除缓存
		this.cache.remove(key);
	}
	/*
	 * 加载机构答疑主题明细集合。
	 * @see com.examw.netplatform.service.MobileAPIService#loadAnswerQuestionDetails(java.lang.String)
	 */
	@Override
	public List<AnswerQuestionDetail> loadAnswerQuestionDetails(String topicId) {
		logger.debug("加载机构答疑主题["+ topicId+"]明细集合...");
		if(StringUtils.isNotBlank(topicId)){
			//缓存键
			final String key = "AnswerQuestionDetail_" + topicId;
			//从缓存中获取数据
			final Element element = this.cache.get(key);
			if(element != null && element.getObjectValue() != null){
				logger.debug("从缓存中加载数据...");
				final AnswerQuestionDetail [] details = (AnswerQuestionDetail[])element.getObjectValue();
				if(details != null) return Arrays.asList(details);
			}
			//从数据库中加载数据
			final List<AnswerQuestionDetail> list = this.answerQuestionDao.findDetails(topicId);
			if(list != null && list.size() > 0){
				//缓存处理
				this.cache.put(new Element(key, list.toArray(new AnswerQuestionDetail[0])));
				//返回
				return list;
			}
		}
		return null;
	}
	/*
	 * 新增答疑明细。
	 * @see com.examw.netplatform.service.MobileAPIService#addAnswerQuestionDetail(com.examw.netplatform.domain.admin.teachers.AnswerQuestionDetail)
	 */
	@Override
	public void addAnswerQuestionDetail(AnswerQuestionDetail detail) throws Exception {
		logger.debug("新增答疑明细...");
		if(detail == null)  throw new Exception("未获取数据!");
		if(StringUtils.isNotBlank(detail.getTopicId())) throw new Exception("所属答疑主题ID不能为空!");
		if(StringUtils.isNotBlank(detail.getUserId())) throw new Exception("所属学员ID不能为空!");
		//设置ID
		detail.setId(UUID.randomUUID().toString());
		//插入数据
		this.answerQuestionDao.insertDetail(detail);
		//缓存键
		final String key = "AnswerQuestionDetail_" + detail.getTopicId();
		//清除缓存
		this.cache.remove(key);
	}
	/*
	 * 新增学员建议。
	 * @see com.examw.netplatform.service.MobileAPIService#addSuggest(com.examw.netplatform.domain.admin.teachers.Suggest)
	 */
	@Override
	public void addSuggest(Suggest suggest) throws Exception {
		logger.debug("新增学员建议...");
		if(suggest == null) throw new Exception("未获取数据!");
		if(StringUtils.isBlank(suggest.getAgencyId())) throw new Exception("所属机构ID不能为空!");
		if(StringUtils.isBlank(suggest.getStudentId())) throw new Exception("所属学员ID不能为空!");
		
		///TODO:建议数据处理
	}
}