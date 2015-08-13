package com.examw.netplatform.service.admin.settings;

import java.util.List;

import com.examw.model.DataGrid;
import com.examw.model.TreeNode;
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
	 * 加载章节树。
	 * @param parentChapterId
	 * 上级章节ID。
	 * @param ignoreChapterId
	 * 忽略的章节ID。
	 * @param isSelf
	 * 是否包含自己。
	 * @return
	 */
	List<TreeNode> loadChapters(String parentChapterId, String ignoreChapterId,boolean isSelf);
	/**
	 * 加载章节树。
	 * @param subjectId
	 * 科目ID。
	 * @return
	 * 章节树。
	 */
	List<TreeNode> loadChapters(String subjectId);
	/**
	 * 加载最大排序号。
	 * @param parentChapterId
	 * 上级章节。
	 * @return
	 * 排序号。
	 */
	Integer loadMaxOrder(String parentChapterId);
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	DataGrid<ChapterInfo> datagrid(ChapterInfo info);
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