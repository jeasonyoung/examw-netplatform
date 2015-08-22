package com.examw.netplatform.dao.admin.courses;

import java.util.List;

import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.domain.admin.courses.SubjectHasClassView;
/**
 * 课时资源数据接口。
 * @author fengwei.
 * @since 2014年5月22日 上午11:37:44.
 */
public interface LessonMapper {
	/**
	 * 获取课时资源。
	 * @param id
	 * @return
	 */
	Lesson getLesson(String id);
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Lesson> findLessons(Lesson info);
	/**
	 * 查询班级下课时资源集合。
	 * @param classId
	 * @return
	 */
	List<Lesson> findLessonsByClass(String classId);
	/**
	 * 查询培训机构下科目班级(有班级的科目)数据集合
	 * @param agencyId
	 * @return
	 */
	List<SubjectHasClassView> findSubjectHasClassViews(String agencyId);
	/**
	 * 加载班级下的最大排序号。
	 * @param classId
	 * 班级ID。
	 * @return
	 */
	Integer loadMaxOrder(String classId);
	/**
	 * 新增课时资源。
	 * @param data
	 */
	void insertLesson(Lesson data);
	/**
	 * 更新课时资源。
	 * @param data
	 */
	void updateLesson(Lesson data);
	/**
	 * 删除课时资源。
	 * @param id
	 */
	void deleteLesson(String id);
}