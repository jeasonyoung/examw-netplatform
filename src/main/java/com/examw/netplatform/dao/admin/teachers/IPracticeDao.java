package com.examw.netplatform.dao.admin.teachers;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.teachers.Practice;
import com.examw.netplatform.model.admin.teachers.PracticeInfo;

/**
 * 随堂练习数据接口。
 * 
 * @author yangyong
 * @since 2014年11月22日
 */
public interface IPracticeDao extends IBaseDao<Practice> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Practice> findPractices(PracticeInfo info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 统计结果。
	 */
	Long total(PracticeInfo info);
}