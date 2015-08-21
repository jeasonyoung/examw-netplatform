package com.examw.netplatform.dao.admin.settings;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examw.netplatform.domain.admin.settings.Chapter;

/**
 * 章节数据接口
 * @author fengwei.
 * @since 2014年4月30日 下午3:26:28.
 */
public interface ChapterMapper {
	/**
	 * 获取章节数据。
	 * @param id
	 * @return
	 */
	Chapter getChapter(String id);
	/**
	 * 查询数据。
	 * @param subjectId
	 * 所属科目ID。
	 * @param ignoreId
	 * 应忽略的章节ID及其子孙。
	 * @return
	 */
	List<Chapter> findChapters(@Param("subjectId")String subjectId,@Param("ignoreId")String ignoreId);
	/**
	 * 加载最大的章节排序号。
	 * @param pid
	 * 上级章节ID。
	 * @return
	 */
	Integer loadMaxOrder(String pid);
	/**
	 * 加载最大的章节排序号。
	 * @param subjectId
	 * @return
	 */
	Integer loadMaxOrderBySubject(String subjectId);
	/**
	 * 是否有子章节数据。
	 * @param id
	 * @return
	 */
	boolean hasChildChapters(String id);
	/**
	 * 是否存在科目下章节。
	 * @param subjectId
	 * @return
	 */
	boolean hasChaptersBySubject(String subjectId);
	/**
	 * 新增章节。
	 * @param data
	 */
	void insertChapter(Chapter data);
	/**
	 * 更新章节。
	 * @param data
	 */
	void updateChapter(Chapter data);
	/**
	 * 删除章节。
	 * @param id
	 */
	void deleteChapter(String id);
}