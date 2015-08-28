package com.examw.netplatform.service;

import java.util.List;

import com.examw.netplatform.domain.admin.courses.BaseLesson;
import com.examw.netplatform.domain.admin.students.BaseLearning;
import com.examw.netplatform.domain.admin.students.UserOrdersView;

/**
 * 移动API接口服务。
 * 
 * @author jeasonyoung
 * @since 2015年8月28日
 */
public interface MobileAPIService {
	/**
	 * 验证学员用户。
	 * @param agencyId
	 * 所属机构。
	 * @param username
	 * 用户名。
	 * @param pwd
	 * 密码[加密方式:md5(md5(agencyId + username) + password)]。
	 * @return
	 * 登录成功-true，否则-false.
	 * @throws Exception
	 */
	boolean authen(String agencyId, String username, String pwd) throws Exception;
	/**
	 * 加载用户订单套餐/班级集合。
	 * @param userId
	 * 用户ID。
	 * @return
	 */
	List<UserOrdersView> ordersByUser(String userId);
	/**
	 * 加载班级下课程资源集合。
	 * @param classId
	 * @return
	 */
	List<BaseLesson> lessonsByClass(String classId);
	/**
	 * 上传学习进度。
	 * @param learning
	 * 进度数据。
	 */
	void pushLearning(BaseLearning learning);
}