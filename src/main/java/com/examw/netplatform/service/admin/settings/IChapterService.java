package com.examw.netplatform.service.admin.settings;

import java.util.List;

import com.examw.netplatform.domain.admin.settings.Chapter;
import com.examw.netplatform.model.admin.settings.ChapterInfo;
import com.examw.netplatform.service.IBaseDataService;

/**
 * 章节服务接口
 * @author fengwei.
 * @since 2014年4月30日 下午3:32:45.
 */
public interface IChapterService extends IBaseDataService<ChapterInfo>{
	/**
	 * 加载章节数据集合。
	 * @param ignoreChapterId
	 * 需忽略的科目ID及其子节点。
	 * @return
	 * 章节数据集合。
	 */
	List<Chapter> loadChapters(String ignoreChapterId);
}