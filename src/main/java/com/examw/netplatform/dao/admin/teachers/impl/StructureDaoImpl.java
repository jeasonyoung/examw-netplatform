package com.examw.netplatform.dao.admin.teachers.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.netplatform.dao.admin.teachers.IStructureDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.teachers.Structure;

/**
 * 随堂练习结构数据接口。
 * 
 * @author yangyong
 * @since 2014年11月22日
 */
public class StructureDaoImpl extends BaseDaoImpl<Structure> implements IStructureDao {
	private static final Logger logger = Logger.getLogger(StructureDaoImpl.class);
	/*
	 * 查询随堂练习下的结构数据集合。
	 * @see com.examw.netplatform.dao.admin.teachers.IStructureDao#findStructures(java.lang.String)
	 */
	@Override
	public List<Structure> findStructures(String practiceId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("查询随堂练习［%s］下的结构数据集合...", practiceId));
		final String hql = "from Structure s where s.practice.id = :practiceId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("practiceId", practiceId);
		return this.find(hql, parameters, null, null);
	}
	/*
	 * 重载删除数据。
	 * @see com.examw.netplatform.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Structure data) {
		if(logger.isDebugEnabled()) logger.debug("重载删除数据...");
		if(data == null) return;
		int count = 0;
		if(data.getItems() != null && (count = data.getItems().size()) > 0){
			throw new RuntimeException(String.format("结构［%1$s］关联［%2$d］试题，暂不能删除！", data.getTitle(), count));
		}
		super.delete(data);
	}
}