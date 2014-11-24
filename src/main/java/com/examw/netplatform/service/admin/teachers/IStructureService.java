package com.examw.netplatform.service.admin.teachers;

import java.util.List;

import com.examw.netplatform.model.admin.teachers.StructureInfo;

/**
 * 随堂练习结构服务接口。
 * 
 * @author yangyong
 * @since 2014年11月24日
 */
public interface IStructureService {
	/**
	 * 加载随堂练习结构集合。
	 * @param practiceId
	 * 随堂练习ID。
	 * @return
	 * 结构信息集合。
	 */
	List<StructureInfo> loadPracticeStructures(String practiceId);
	/**
	 * 更新随堂练习结构。
	 * @param info
	 * 随堂练习结构信息。
	 * @return
	 */
	StructureInfo update(StructureInfo info);
	/**
	 * 删除随堂练习结构。
	 * @param ids
	 * 结构ID集合。
	 */
	void delete(String[] ids);
}