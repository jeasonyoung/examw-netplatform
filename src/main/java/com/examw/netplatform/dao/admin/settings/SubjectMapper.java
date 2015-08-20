package com.examw.netplatform.dao.admin.settings;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examw.netplatform.domain.admin.settings.ExamSubjectView;
import com.examw.netplatform.domain.admin.settings.Subject;
/**
 * 科目数据接口
 * @author fengwei.
 * @since 2014年8月6日 下午1:44:30.
 */
public interface SubjectMapper{
	/**
	 * 获取数据。
	 * @param id
	 * @return
	 */
	Subject getSubject(String id);
	/**
	 * 查询科目数据
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Subject> findSubjects(Subject info);
	/**
	 * 查询考试科目数据集合。
	 * @param examId
	 * @return
	 */
	List<Subject> findSubjectsByExam(String examId);
	/**
	 * 查询考试科目视图数据集合。
	 * @return
	 */
	List<ExamSubjectView> findExamSubjectViews();
	/**
	 * 加载最大代码值。
	 * @return 最大代码值。
	 */
	Integer loadMaxCode();
	/**
	 * 是否存在科目代码。
	 * @param code
	 * @return
	 */
	boolean hasSubjectCode(int code);
	/**
	 * 是否存在考试科目数据。
	 * @param examId
	 * @return
	 */
	boolean hasSubjectByExam(String examId);
	/**
	 * 新增科目。
	 * @param data
	 */
	void insertSubject(Subject data);
	/**
	 * 更新科目。
	 * @param data
	 */
	void updateSubject(Subject data);
	/**
	 * 删除科目。
	 * @param id
	 */
	void deleteSubject(String id);
	
	/**
	 * 是否存在科目地区。
	 * @param subjectId
	 * @param areaId
	 * @return
	 */
	boolean hasSubjectArea(@Param("subjectId")String subjectId,@Param("areaId")String areaId);
	/**
	 * 新增科目地区。
	 * @param subjectId
	 * @param areaId
	 */
	void insertSubjectArea(@Param("subjectId")String subjectId,@Param("areaId")String areaId);
	/**
	 * 删除科目地区。
	 * @param subjectId
	 */
	void deleteSubjectAreas(String subjectId);
}