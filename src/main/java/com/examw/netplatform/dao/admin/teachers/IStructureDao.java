package com.examw.netplatform.dao.admin.teachers;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.teachers.Structure;
/**
 * 随堂练习结构数据接口。
 * 
 * @author yangyong
 * @since 2014年11月22日
 */
public interface IStructureDao extends IBaseDao<Structure> {
	/**
	 * 查询随堂练习下的结构数据集合。
	 * @param practiceId
	 * 随堂练习ID。
	 * @return
	 * 结构数据集合。
	 */
	List<Structure> findStructures(String practiceId);
}