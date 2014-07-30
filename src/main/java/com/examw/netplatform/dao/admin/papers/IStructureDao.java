package com.examw.netplatform.dao.admin.papers;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.papers.Structure;

/**
 * 试卷结构数据接口
 * @author fengwei.
 * @since 2014年5月5日 下午5:24:22.
 */
public interface IStructureDao extends IBaseDao<Structure>{
	/**
	 * 根据试卷id查询所属的大题集合
	 * @param paperId
	 * @return
	 */
	List<Structure> findStructures(String paperId);
}