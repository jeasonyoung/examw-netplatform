package com.examw.netplatform.service.front.user;

import java.util.List;
import java.util.Map;

import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.domain.admin.students.Order;
import com.examw.netplatform.exceptions.NotValidLessonException;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.admin.students.LearningInfo;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo;
import com.examw.netplatform.model.front.FrontClassPlanInfo;
import com.examw.netplatform.model.front.FrontPackageInfo;

/**
 * 前台课程服务接口
 * @author fengwei.
 * @since 2015年1月22日 下午4:25:21.
 */
public interface IFrontCourseService {
	
	/**
	 * 查找用户套餐
	 * @param userId
	 * @return
	 */
	List<FrontPackageInfo> findUserPackages(String userId);
	
	/**
	 * 查找用户班级
	 * @param userId
	 * @return
	 */
	List<ClassPlanInfo> findUserClassPlans(String userId);
	
	/**
	 * 查询班级信息
	 * @param userId
	 * @param classPlanId
	 * @return
	 */
	FrontClassPlanInfo findClassPlan(String userId,String classPlanId);
	
	/**
	 * 查询用户订单
	 * @param userId
	 * @return
	 */
	List<Order> findUserOrders(String userId);
	
	/**
	 * 查询课时信息
	 * @param userId	用户ID
	 * @param classId	班级ID
	 * @param lessonId	课时ID
	 * @param model		传到页面的模型数据
	 * [所有课时,当前课时,上一课时,下一课时]
	 */
	void findLessonInfo(AgencyUser user, String classId, String lessonId,
			Map<String, Object> model) throws NotValidLessonException;
	/**
	 * 保存学习进度
	 * @param info
	 * @return
	 */
	boolean saveLearningRecord(LearningInfo info);
	
	/**
	 * 保存提问
	 * @param info
	 * @return
	 */
	AnswerQuestionTopicInfo saveQuestionTopic(AnswerQuestionTopicInfo info);
}
