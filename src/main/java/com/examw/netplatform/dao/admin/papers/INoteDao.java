package com.examw.netplatform.dao.admin.papers;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.papers.Note;
import com.examw.netplatform.model.admin.papers.NoteInfo;

/**
 * 试卷笔记数据接口
 * @author fengwei.
 * @since 2014年5月13日 上午11:53:20.
 */
public interface INoteDao extends IBaseDao<Note>{
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Note> findNotes(NoteInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(NoteInfo info);
}