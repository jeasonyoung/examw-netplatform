package com.examw.netplatform.dao.admin.courses;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.model.admin.courses.LessonInfo;
/**
 * 课时数据接口
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
}