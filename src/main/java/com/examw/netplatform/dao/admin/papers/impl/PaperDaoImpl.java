package com.examw.netplatform.dao.admin.papers.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.papers.IPaperDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.papers.Paper;
import com.examw.netplatform.model.admin.papers.PaperInfo;

/**
 * 试卷数据接口实现类
 * @author fengwei.
 * @since 2014年5月3日 下午5:02:09.
 */
public class PaperDaoImpl  extends BaseDaoImpl<Paper> implements IPaperDao{
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.papers.IPaperDao#findPapers(com.examw.netplatform.model.admin.papers.PaperInfo)
	 */
	@Override
	public List<Paper> findPapers(PaperInfo info) {
		String hql = "from Paper p where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by p." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 *  查询数据统计。
	 * @see com.examw.netplatform.dao.admin.papers.IPaperDao#total(com.examw.netplatform.model.admin.papers.PaperInfo)
	 */
	@Override
	public Long total(PaperInfo info) {
		String hql = "select count(*) from Paper p where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	/**
	 * 添加查询条件到HQL。
	 * @param info
	 * 查询条件。
	 * @param hql
	 * HQL
	 * @param parameters
	 * 参数。
	 * @return
	 * HQL
	 */
	protected String addWhere(PaperInfo info,String hql,Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and (p.name like :name) ";
			parameters.put("name", "%"+ info.getName() +"%");
		}
		if(!StringUtils.isEmpty(info.getAgencyId())){
			hql += " and (p.agency.id = :agencyId)";
			parameters.put("agencyId", info.getAgencyId());
		}
		if(!StringUtils.isEmpty(info.getCatalogId())){
			hql += " and (p.subject.exam.catalog.id = :catalogId) ";
			parameters.put("catalogId", info.getCatalogId());
		}
		if(!StringUtils.isEmpty(info.getExamId())){
			hql += " and (p.subject.exam.id = :examId) ";
			parameters.put("examId", info.getExamId());
		}
		if(!StringUtils.isEmpty(info.getSubjectId())){
			hql += " and (p.subject.id = :subjectId)";
			parameters.put("subjectId", info.getSubjectId());
		}
		if(!StringUtils.isEmpty(info.getSubjectName())){
			hql += " and (p.subject.name like :subjectName)";
			parameters.put("subjectName", "%"+ info.getSubjectName() +"%");
		}
		if(info.getType() != null){
			hql += " and (p.type = :type)";
			parameters.put("type", info.getType());
		}
		if(info.getStatus() != null){
			hql += " and (p.status = :status)";
			parameters.put("status", info.getStatus());
		}
		return hql;
	}
}