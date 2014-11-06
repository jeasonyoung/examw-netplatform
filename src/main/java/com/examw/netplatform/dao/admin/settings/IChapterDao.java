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
	 * 查询数据。
	 * @return
	 * 结果数据。
	 */
	List<Chapter> findTopChapters(ChapterInfo info);
	/**
	 * 加载章节集合。
	 * @param parentChapterId
	 * 上级章节。
	 * @return
	 */
	List<Chapter> loadChapters(String parentChapterId);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(ChapterInfo info);
	/**
	 * 加载最大的章节排序号。
	 * @param parentChapterId
	 * 上级章节ID。
	 * @return
	 */
	Integer loadMaxOrder(String parentChapterId);
}