package com.examw.netplatform.dao.admin.papers.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.papers.IItemDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.papers.Item;
import com.examw.netplatform.model.admin.papers.ItemInfo;

/**
 * 试题数据接口实现类
 * @author fengwei.
 * @since 2014年5月6日 上午9:57:43.
 */
public class ItemDaoImpl extends BaseDaoImpl<Item> implements IItemDao {
	/*
	 * 根据校验码加载题目。
	 * @see com.examw.netplatform.dao.admin.papers.IItemDao#loadItem(java.lang.String)
	 */
	@Override
	public Item loadItem(String checkCode) {
		if(StringUtils.isEmpty(checkCode)) return null;
		final String hql = "from Item i where i.parent = null and i.checkCode = :checkCode";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("checkCode", checkCode);
		List<Item> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
	/*
	 * 查询试题
	 * @see com.examw.netplatform.dao.admin.IItemDao#findExams(com.examw.netplatform.model.admin.ItemInfo)
	 */
	@Override
	public List<Item> findItems(ItemInfo info) {
		String hql = "from Item i where i.parent = null ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if (info.getSort() != null && !info.getSort().trim().isEmpty()) {
			hql += " order by i." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询总数
	 * @see com.examw.netplatform.dao.admin.IItemDao#total(com.examw.netplatform.model.admin.ItemInfo)
	 */
	@Override
	public Long total(ItemInfo info) {
		String hql = "select count(*) from Item i where i.parent = null ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	/**
	 * 添加查询条件到HQL。
	 * 
	 * @param info
	 *            查询条件。
	 * @param hql
	 *            HQL
	 * @param parameters
	 *            参数。
	 * @return HQL
	 */
	protected String addWhere(ItemInfo info, String hql, Map<String, Object> parameters) {
		// 校验码
		if (!StringUtils.isEmpty(info.getCheckCode())) {
			hql += " and (i.checkCode = :checkCode)";
			parameters.put("checkCode", info.getCheckCode());
		}
		//机构
		if(!StringUtils.isEmpty(info.getAgencyId()))
		{
			hql += "and (i.agency.id = :agencyId)";
			parameters.put("agencyId", info.getAgencyId());
		}
		//科目
		if(!StringUtils.isEmpty(info.getSubjectId()))
		{
			hql += "and (i.subject.id = :subjectId)";
			parameters.put("subjectId", info.getSubjectId());
		}
		if (info.getType() !=null)
		{
			hql += " and (i.type = :type)";
			parameters.put("type", info.getType());
		}
		return hql;
	}
	/*
	 * 删除题目。
	 * @see com.examw.netplatform.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Item data){
		if(data == null) return;
		if(data.getChildren() != null && data.getChildren().size() > 0){
			for(Item child : data.getChildren()){
				this.delete(child);
			}
		}
		super.delete(data);
	}
}