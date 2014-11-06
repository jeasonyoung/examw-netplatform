package com.examw.netplatform.dao.admin.settings;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.settings.Subject;
import com.examw.netplatform.model.admin.settings.SubjectInfo;
/**
 * 科目数据接口
 * @author fengwei.
 * @since 2014年8月6日 下午1:44:30.
 */
public interface ISubjectDao extends IBaseDao<Subject>{
	/**
	 * 查询科目数据
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Subject> findSubjects(SubjectInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(SubjectInfo info);
	/**
	 * 加载最大代码值。
	 * @return 最大代码值。
	 */
	Integer loadMaxCode();
	/**
	 * 加载考试下的科目集合。
	 * @param examId
	 * 所属考试ID。
	 * @return
	 * 科目集合。
	 */
	List<Subject> loadAllSubjects(String examId);
}