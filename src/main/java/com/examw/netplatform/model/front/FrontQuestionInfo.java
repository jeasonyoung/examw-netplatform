package com.examw.netplatform.model.front;

import java.util.List;

import com.examw.netplatform.model.admin.teachers.AnswerQuestionDetailInfo;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo;

/**
 * 前台问题信息
 * @author fengwei.
 * @since 2015年1月29日 下午4:48:20.
 */
public class FrontQuestionInfo extends AnswerQuestionTopicInfo{
	private static final long serialVersionUID = 1L;
	
	private List<AnswerQuestionDetailInfo> answers;
	/**
	 * 获取 回复列表
	 * @return answers
	 * 回复列表
	 */
	public List<AnswerQuestionDetailInfo> getAnswers() {
		return answers;
	}
	/**
	 * 设置 回复列表
	 * @param answers
	 * 回复列表
	 */
	public void setAnswers(List<AnswerQuestionDetailInfo> answers) {
		this.answers = answers;
	}
	
}
