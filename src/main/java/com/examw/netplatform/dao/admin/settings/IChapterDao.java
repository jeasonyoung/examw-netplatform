package com.examw.netplatform.dao.admin.settings;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.settings.Chapter;
import com.examw.netplatform.model.admin.settings.ChapterInfo;

/**
 * 章节数据接口
 * @author fengwei.
 * @since 2014年4月30日 下午3:26:28.
 */
public interface IChapterDao extends IBaseDao<Chapter> {
	/**
	 * 根据科目查询章节数据。
	 * @return
	 * 结果数据。
	 */
	List<Chapter> findChapters(ChapterInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(ChapterInfo info);
	/**
	 * 加载章节数据集合。
	 * @param ignoreChapterId
	 * 需要忽略的章节ID。
	 * @return
	 */
	List<Chapter> loadChapters(String ignoreChapterId);
}