package com.examw.netplatform.service.admin.students;

import com.examw.model.DataGrid;
import com.examw.netplatform.model.admin.students.LearningInfo;

/**
 * 学习进度服务接口。
 * @author fengwei.
 * @since 2014年5月29日 上午11:46:50.
 */
public interface ILearningService {
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	DataGrid<LearningInfo> datagrid(LearningInfo info);
	/**
	 * 删除数据。
	 * @param ids
	 */
	void delete(String[] ids);
}