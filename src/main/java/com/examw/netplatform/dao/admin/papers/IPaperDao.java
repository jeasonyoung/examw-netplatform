package com.examw.netplatform.dao.admin.papers;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.papers.Paper;
import com.examw.netplatform.model.admin.papers.PaperInfo;

/**
 * 试卷数据接口
 * @author fengwei.
 * @since 2014年5月3日 下午5:01:30.
 */
public interface IPaperDao extends IBaseDao<Paper> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Paper> findPapers(PaperInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(PaperInfo info);
}