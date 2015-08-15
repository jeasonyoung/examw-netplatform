package com.examw.netplatform.service.admin.teachers;

import com.examw.model.DataGrid;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionDetail;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionDetailInfo;

/**
 * 教师答疑明细服务接口。
 * 
 * @author yangyong
 * @since 2014年11月20日
 */
public interface IAnswerQuestionDetailService{
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	DataGrid<AnswerQuestionDetailInfo> datagrid(AnswerQuestionDetailInfo info);
	/**
	 * 数据模型转换
	 * @param data
	 * @return
	 */
	AnswerQuestionDetailInfo conversion(AnswerQuestionDetail data);
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	AnswerQuestionDetailInfo update(AnswerQuestionDetailInfo info);
	/**
	 * 删除数据。
	 * @param ids
	 */
	void delete(String[] ids);
}