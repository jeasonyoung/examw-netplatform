package com.examw.netplatform.dao.admin.papers.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query; 
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.papers.IStructureItemDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.papers.Paper;
import com.examw.netplatform.domain.admin.papers.Structure;
import com.examw.netplatform.domain.admin.papers.StructureItem; 
import com.examw.netplatform.model.admin.papers.StructureItemInfo;

/**
 * 试卷题目数据接口实现类。
 * @author fengwei.
 * @since 2014年5月11日 下午4:07:31.
 */
public class StructureItemDaoImpl extends BaseDaoImpl<StructureItem> implements IStructureItemDao{
	/*
	 * 加载试卷题目。
	 * @see com.examw.netplatform.dao.admin.papers.IStructureItemDao#loadStructureItem(java.lang.String, java.lang.String)
	 */
	@Override
	public StructureItem loadStructureItem(String structureId, String itemId) {
		if(StringUtils.isEmpty(structureId) || StringUtils.isEmpty(itemId)) return null;
		final String hql = "from StructureItem s where s.structure.id = :structureId  and s.item.id = :itemId";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("structureId", structureId);
		parameters.put("itemId", itemId);
		
		List<StructureItem> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
	/*
	 * 题目是否在试卷中存在。
	 * @see com.examw.netplatform.dao.admin.papers.IStructureItemDao#exists(java.lang.String)
	 */
	@Override
	public int exists(String itemId) {
		if(StringUtils.isEmpty(itemId)) return 0;
		final String hql = "select count(*) from StructureItem s where s.item.id = :itemId";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("itemId", itemId);
		return  this.count(hql, parameters).intValue();
	}
	/*
	 * 随机加载题目所在的试卷。
	 * @see com.examw.netplatform.dao.admin.papers.IStructureItemDao#loadRandomPaper(java.lang.String)
	 */
	@Override
	public Paper loadRandomPaper(String itemId) {
		if(StringUtils.isEmpty(itemId)) return null;
		final String hql = "select top 1  *  from StructureItem s where s.item.id = :itemId ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("itemId", itemId);
		List<StructureItem> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		Structure data = list.get(0).getStructure();
		if(data == null) return null;
		Paper paper = null;
		while(((paper = data.getPaper()) == null) && (data.getParent() != null)){
			data = data.getParent();
		}
		return paper;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.papers.IStructureItemDao#findStructureItems(com.examw.netplatform.model.admin.papers.StructureItemInfo)
	 */
	@Override
	public List<StructureItem> findStructureItems(String paperId, StructureItemInfo info) {
		String hql = "from StructureItem s  where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(paperId, info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("typeName")){
				info.setSort("item.type");
			}
			hql += " order by s." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.papers.IStructureItemDao#total(com.examw.netplatform.model.admin.papers.StructureItemInfo)
	 */
	@Override
	public Long total(String paperId, StructureItemInfo info) {
		String hql = "select count(*) from StructureItem s where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(paperId, info, hql, parameters);
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
	protected String addWhere(String paperId, StructureItemInfo info,String hql,Map<String, Object> parameters){
		if(!StringUtils.isEmpty(paperId)){
			hql += "  and (s.structure.paper.id = :paperId) ";
			parameters.put("paperId", paperId);
		}
		if(!StringUtils.isEmpty(info.getStructureId())){
			hql += " and (s.structure.id = :structureId or s.structure.parent.id = :structureId) ";
			parameters.put("structureId", info.getStructureId());
		}
		if(info.getItem() != null && !StringUtils.isEmpty(info.getItem().getContent())){
			hql += " and (s.item.content like :content or s.item.checkCode like :content)";
			parameters.put("content", "%" + info.getItem().getContent() + "%");
		}
		return hql;
	}
	/*
	 * 加载试卷下最大的排序号。
	 * @see com.examw.netplatform.dao.admin.papers.IStructureItemDao#loadMaxOrderNo(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrderNo(String paperId) {
		if(StringUtils.isEmpty(paperId)) return null;
		final String s_hql = "select max(s.orderNo) from StructureItem s where s.structure.paper.id = :paperId",
				          score_hql ="select max(s.orderNo) from StructureItemScore s where s.structureItem.structure.paper.id = :paperId";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("paperId", paperId); 
		Integer structure_max =  this.loadMaxOrderNo(s_hql, parameters),
				    structure_score_max =  this.loadMaxOrderNo(score_hql, parameters);
		if(structure_score_max == null) return structure_max;
		if(structure_score_max > structure_max) return structure_score_max;
		return structure_max;
	}
	/**
	 * 
	 * @param hql
	 * @param parameters
	 * @return
	 */
	private Integer loadMaxOrderNo(String hql, Map<String, Object> parameters){
		if(StringUtils.isEmpty(hql) || parameters == null || parameters.size() == 0) return 0;
		Query query = this.getCurrentSession().createQuery(hql);
		if(query != null){
			this.addParameters(query, parameters);
			 return (Integer)query.uniqueResult();
		}
		return 0;
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(StructureItem data){
		if(data == null) return;
		super.delete(data);
		final String del_hql = "delete from StructureItemScore s where s.structureItem is null";
		this.getCurrentSession().createQuery(del_hql).executeUpdate();
	}
}