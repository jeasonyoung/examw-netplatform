package com.examw.netplatform.service.admin.settings;

import com.examw.netplatform.model.admin.settings.CatalogInfo;
import com.examw.netplatform.model.admin.settings.ExamInfo;
import com.examw.netplatform.service.IBaseDataService;
/**
 * 考试服务接口
 * @author fengwei.
 * @since 2014年4月29日 上午11:58:16.
 */
public interface IExamService extends IBaseDataService<ExamInfo> {
	/**
	 * 根据考试设置ID获取考试类别信息。
	 * @param examId
	 * 考试设置ID
	 * @return
	 * 考试类别信息。
	 */
	CatalogInfo loadCatalog(String examId);
}