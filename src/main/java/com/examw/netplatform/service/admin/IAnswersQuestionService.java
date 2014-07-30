package com.examw.netplatform.service.admin;

import com.examw.netplatform.model.admin.AnswersQuestionInfo;
import com.examw.netplatform.service.IBaseDataService;

/**
 * 课堂问答服务接口
 * @author fengwei.
 * @since 2014年5月31日 上午9:58:39.
 */
public interface IAnswersQuestionService extends IBaseDataService<AnswersQuestionInfo>{
	/**
	 * 获取状态的名称
	 * @param stuatus
	 * @return
	 */
	String loadStatusName(Integer status);
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	AnswersQuestionInfo findById(String id);
}
