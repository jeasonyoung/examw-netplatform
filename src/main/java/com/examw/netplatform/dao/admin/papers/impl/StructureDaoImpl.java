package com.examw.netplatform.dao.admin.papers.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.papers.IStructureDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.papers.Structure;

/**
 * 试卷结构数据接口实现类
 * @author fengwei.
 * @since 2014年5月5日 下午5:25:14.
 */
public class StructureDaoImpl extends BaseDaoImpl<Structure> implements IStructureDao{
	/*
	 * 查询试卷结构数据。
	 * @see com.examw.netplatform.dao.admin.papers.IStructureDao#findStructures(java.lang.String)
	 */
	@Override
	public List<Structure> findStructures(String paperId) {
		if(StringUtils.isEmpty(paperId))  return null;
		String hql = "from Structure s where s.parent =  null and  s.paper.id = :paperId order by s.orderNo asc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("paperId", paperId);
		return this.find(hql, parameters, null, null);
	}
}