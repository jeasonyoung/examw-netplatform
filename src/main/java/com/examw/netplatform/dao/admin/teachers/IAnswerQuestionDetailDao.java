package com.examw.netplatform.dao.admin.teachers;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionDetail;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionDetailInfo;

/**
 * 教师答疑明细数据接口。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public interface IAnswerQuestionDetailDao extends IBaseDao<AnswerQuestionDetail> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<AnswerQuestionDetail> findDetails(AnswerQuestionDetailInfo info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	Long total(AnswerQuestionDetailInfo info);
}