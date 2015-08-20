package com.examw.netplatform.service.admin.settings;

import java.util.List;

import com.examw.netplatform.model.admin.settings.ChapterInfo;

/**
 * 章节服务接口
 * @author fengwei.
 * @since 2014年4月30日 下午3:32:45.
 */
public interface IChapterService {
	/**
	 * 加载状态名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载最大排序号。
	 * @param pid
	 * 上级章节。
	 * @return
	 * 排序号。
	 */
	Integer loadMaxOrder(String pid);
	/**
	 * 加载科目下全部的章节数据。
	 * @param subjectId
	 * 科目ID。
	 * @return
	 * 章节集合。
	 */
	List<ChapterInfo> loadAllChapters(String subjectId);
	/**
	 * 加载科目下全部的章节数据。
	 * @param subjectId
	 * 科目ID。
	 * @param ignoreId
	 * 应忽略的章节ID。
	 * @return
	 */
	List<ChapterInfo> loadAllChapters(String subjectId, String ignoreId);
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	ChapterInfo update(ChapterInfo info);
	/**
	 * 删除数据。
	 * @param ids
	 */
	void delete(String[] ids);
}