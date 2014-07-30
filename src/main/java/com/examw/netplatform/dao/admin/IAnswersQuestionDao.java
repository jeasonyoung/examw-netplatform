package com.examw.netplatform.dao.admin;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.AnswersQuestion;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.model.admin.AnswersQuestionInfo;

/**
 * 课堂问答数据接口
 * @author fengwei.
 * @since 2014年5月31日 上午9:53:15.
 */
public interface IAnswersQuestionDao extends IBaseDao<AnswersQuestion>{
	/**
	 * 查询数据
	 * @param info
	 * @return
	 */
	List<AnswersQuestion> findAnswersQuestions(AnswersQuestionInfo info);
	/**
	 * 查询统计
	 * @param info
	 * @return
	 */
	Long total(AnswersQuestionInfo info);
	/**
	 * 查询统计问题总个数
	 * @param classPlanIds
	 * @return
	 */
	Long totalQuestions(String classPlanIds);
	/**
	 * 查询统计问题(已回答)总个数
	 * @param classPlanIds
	 * @return
	 */
	Long totalAnswered(User user,String classPlanIds);
}
