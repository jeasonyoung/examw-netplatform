package com.examw.netplatform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.netplatform.dao.admin.courses.LessonMapper;
import com.examw.netplatform.dao.admin.students.LearningMapper;
import com.examw.netplatform.dao.admin.students.OrderMapper;
import com.examw.netplatform.domain.admin.courses.BaseLesson;
import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.security.UserIdentity;
import com.examw.netplatform.domain.admin.students.BaseLearning;
import com.examw.netplatform.domain.admin.students.Learning;
import com.examw.netplatform.domain.admin.students.UserOrdersView;
import com.examw.netplatform.service.MobileAPIService;
import com.examw.netplatform.service.admin.security.IUserAuthorization;
import com.examw.netplatform.support.PasswordHelper;

/**
 * 移动API接口服务实现类。
 * 
 * @author jeasonyoung
 * @since 2015年8月28日
 */
public class MobileAPIServiceImpl implements MobileAPIService {
	private static final Logger logger = Logger.getLogger(MobileAPIServiceImpl.class);
	private OrderMapper orderDao;
	private LessonMapper lessonDao;
	private LearningMapper learningDao;
	private IUserAuthorization userAuthorization;
	private PasswordHelper passwordHelper;
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
	 * 设置用户授权服务。
	 * @param userAuthorization 
	 *	  用户授权服务。
	 */
	public void setUserAuthorization(IUserAuthorization userAuthorization) {
		logger.debug("注入用户授权服务...");
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
	public boolean authen(String agencyId, String username, String pwd) throws Exception {
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
		return StringUtils.equalsIgnoreCase(pwd, pwdEncypt);
	}
	/*
	 * 加载用户订单套餐/班级集合。
	 * @see com.examw.netplatform.service.MobileAPIService#ordersByUser(java.lang.String)
	 */
	@Override
	public List<UserOrdersView> ordersByUser(String userId) {
		logger.debug("加载用户["+userId+"]订单套餐/班级集合...");
		return this.orderDao.findOrdersViewsByUser(userId);
	}
	/*
	 * 加载班级下课程资源集合。
	 * @see com.examw.netplatform.service.MobileAPIService#lessonsByClass(java.lang.String)
	 */
	@Override
	public List<BaseLesson> lessonsByClass(String classId) {
		logger.debug("加载班级["+classId+"]下课程资源集合...");
		final List<BaseLesson> list = new ArrayList<BaseLesson>();
		if(StringUtils.isNotBlank(classId)){
			final List<Lesson> lessons = this.lessonDao.findLessonsByClass(classId);
			if(lessons != null && lessons.size() > 0){
				for(Lesson lesson : lessons){
					if(lesson == null) continue;
					final BaseLesson data = new BaseLesson();
					BeanUtils.copyProperties(lesson, data);
					list.add(data);
				}
			}
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

}