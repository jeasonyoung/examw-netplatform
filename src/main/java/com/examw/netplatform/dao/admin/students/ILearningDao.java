package com.examw.netplatform.dao.admin.students;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.students.Learning;
import com.examw.netplatform.model.admin.students.LearningInfo;

/**
 * 学习进度数据接口
 * @author fengwei.
 * @since 2014年5月29日 上午11:43:17.
 */
public interface ILearningDao extends IBaseDao<Learning>{
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Learning> findLearnings(LearningInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(LearningInfo info);
}
