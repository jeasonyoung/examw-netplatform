package com.examw.netplatform.dao.admin.teachers;

import java.util.List;

import com.examw.netplatform.domain.admin.teachers.AnswerQuestionDetail;

/**
 * 教师答疑明细数据接口。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public interface AnswerQuestionDetailMapper {
	/**
	 * 获取答疑明细。
	 * @param id
	 * @return
	 */
	AnswerQuestionDetail getDetail(String id);
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<AnswerQuestionDetail> findDetails(AnswerQuestionDetail info);
	/**
	 * 新增答疑明细。
	 * @param data
	 */
	void insertDetail(AnswerQuestionDetail data);
	/**
	 * 更新答疑明细。
	 * @param data
	 */
	void updateDetail(AnswerQuestionDetail data);
	/**
	 * 删除答疑明细。
	 * @param id
	 */
	void deleteDetail(String id);
}