package com.examw.netplatform.service.admin.settings;

import java.util.List;

import com.examw.model.TreeNode;
import com.examw.netplatform.model.admin.settings.CatalogInfo;
import com.examw.netplatform.service.IBaseDataService;

/**
 * 考试类型服务接口。
 * @author yangyong.
 *	@since 2014-04-28.
 */
public interface ICatalogService extends IBaseDataService<CatalogInfo> {
	/**
	 * 加载考试类型下考试设置树数据。
	 * @return
	 * 考试设置树数据。
	 */
	List<TreeNode> loadAllCatalogExams();
	/**
	 * 加载考试类型下考试科目树数据。
	 * @return
	 * 考试科目树数据。
	 */
	List<TreeNode> loadAllCatalogExamSubjects();
}