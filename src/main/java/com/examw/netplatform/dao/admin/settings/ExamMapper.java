package com.examw.netplatform.dao.admin.settings;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examw.netplatform.domain.admin.settings.Exam;

/**
 * 考试数据接口
 * @author fengwei.
 * @since 2014年8月6日 下午1:43:30.
 */
public interface ExamMapper{
	/**
	 * 获取考试。
	 * @param id
	 * @return
	 */
	Exam getExam(String id);
	/**
	 * 查询考试数据
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Exam> findExams(Exam info);
	/**
	 * 加载分类状态下的考试集合。
	 * @param categoryId
	 * @param status
	 * @return
	 */
	List<Exam> findExamsByCategory(@Param("categoryId")String categoryId, @Param("status")int status);
	/**
	 * 加载最大考试代码值。
	 * @return
	 */
	Integer loadMaxCode();
	/**
	 * 是否存在考试代码。
	 * @param code
	 * @return
	 */
	boolean hasExamCode(int code);
	/**
	 * 是否存在考试EN简称。
	 * @param abbr
	 * @return
	 */
	boolean hasExamAbbr(String abbr);
	/**
	 * 新增考试数据。
	 * @param data
	 */
	void insertExam(Exam data);
	/**
	 * 更新考试数据。
	 * @param data
	 */
	void updateExam(Exam data);
	/**
	 * 删除考试数据。
	 * @param id
	 */
	void deleteExam(String id);
	/**
	 * 是否存在考试地区。
	 * @param examId
	 * @param areaId
	 * @return
	 */
	boolean hasExamArea(@Param("examId")String examId, @Param("areaId")String areaId);
	/**
	 * 新增考试地区。
	 * @param examId
	 * @param areaId
	 */
	void insertExamArea(@Param("examId")String examId, @Param("areaId")String areaId);
	/**
	 * 删除考试地区。
	 * @param examId
	 * @param areaId
	 */
	void deleteExamArea(@Param("examId")String examId, @Param("areaId")String areaId);
	/**
	 * 删除考试地区。
	 * @param examId
	 */
	void deleteExamAreas(String examId);
}