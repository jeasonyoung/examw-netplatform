package com.examw.netplatform.dao.admin.courses;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.model.admin.courses.LessonInfo;
/**
 * 课时资源数据接口。
 * @author fengwei.
 * @since 2014年5月22日 上午11:37:44.
 */
public interface ILessonDao extends IBaseDao<Lesson> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Lesson> findLessons(LessonInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(LessonInfo info);
	/**
	 * 加载班级下的最大排序号。
	 * @param classId
	 * 班级ID。
	 * @return
	 */
	Integer loadMaxOrder(String classId);
	/**
	 * 查询某用户带提问的课时列表
	 * @param info
	 * @param userId
	 * @return
	 * 2015.01.30
	 */
	List<Lesson> findLessonWithQuestions(LessonInfo info,String userId);
	Long totalLessonWithQuestions(String userId);
}