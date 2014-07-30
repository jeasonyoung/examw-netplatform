package com.examw.netplatform.service.admin.teacher;

import com.examw.model.DataGrid;
import com.examw.netplatform.model.admin.teacher.StatisticsInfo;

/**
 * 答疑统计服务接口
 * @author fengwei.
 * @since 2014年6月17日 下午3:10:32.
 */
public interface IAnswersQuestionStatisticsService {
	DataGrid<StatisticsInfo> datagrid(StatisticsInfo info);
}
