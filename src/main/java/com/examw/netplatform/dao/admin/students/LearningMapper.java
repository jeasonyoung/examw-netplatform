package com.examw.netplatform.dao.admin.students;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examw.netplatform.domain.admin.students.Learning;

/**
 * 进度数据接口。
 * @author fengwei.
 * @since 2014年5月29日 上午11:43:17.
 */
public interface LearningMapper{
	/**
	 * 获取学员学习进度。
	 * @param id
	 * @return
	 */
	Learning getLearning(String id);
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Learning> findLearnings(Learning info);
	/**
	 * 获取机构学员学习进度ID。
	 * @param studentId
	 * @return
	 */
	List<Learning> findLearningsByStudent(@Param("agencyId")String agencyId, @Param("studentId")String studentId);
	/**
	 * 新增学习进度。
	 * @param data
	 */
	void insertLearning(Learning data);
	/**
	 * 更新学习进度。
	 * @param data
	 */
	void updateLearning(Learning data);
	/**
	 * 删除学习进度。
	 * @param studentId
	 * @param lessonId
	 */
	void deleteLearning(@Param("studentId")String studentId,@Param("lessonId")String lessonId);
}