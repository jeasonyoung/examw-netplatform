package com.examw.netplatform.dao.admin.settings;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.settings.Exam;
import com.examw.netplatform.model.admin.settings.ExamInfo;

/**
 * 考试数据接口
 * @author fengwei.
 * @since 2014年8月6日 下午1:43:30.
 */
public interface IExamDao extends IBaseDao<Exam>{
	/**
	 * 查询考试数据
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Exam> findExams(ExamInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(ExamInfo info);
	/**
	 * 加载最大考试代码值。
	 * @return
	 */
	Integer loadMaxCode();
}