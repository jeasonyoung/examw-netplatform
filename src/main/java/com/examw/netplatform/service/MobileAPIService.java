package com.examw.netplatform.service;

import java.util.List;

import com.examw.netplatform.domain.admin.courses.BaseLesson;
import com.examw.netplatform.domain.admin.courses.PackageAndClass;
import com.examw.netplatform.domain.admin.settings.Category;
import com.examw.netplatform.domain.admin.settings.Exam;
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
	 * 当前用户ID。
	 * @throws Exception
	 */
	String authen(String agencyId, String username, String pwd) throws Exception;
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
	 * 班级ID。
	 * @return
	 */
	List<BaseLesson> loadLessonsByClass(String classId);
	/**
	 * 加载班级下的免费课程资源集合。
	 * @param classId
	 * 班级ID。
	 * @return
	 */
	List<BaseLesson> loadFreeLessonsByClass(String classId);
	/**
	 * 上传学习进度。
	 * @param learning
	 * 进度数据。
	 */
	void pushLearning(BaseLearning learning);
	/**
	 * 考试类别集合。
	 * @return
	 */
	List<Category> getCategories();
	/**
	 * 加载考试类别下的考试集合。
	 * @param categoryId
	 * 考试类别ID。
	 * @return
	 */
	List<Exam> loadExamsByCategory(String categoryId);
	/**
	 * 加载机构考试下的套餐/班级集合。
	 * @param agencyId
	 * 机构ID。
	 * @param examId
	 * 考试ID。
	 * @return
	 */
	List<PackageAndClass> loadPackageAndClassesByAgencyExam(String agencyId, String examId);
}