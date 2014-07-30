package com.examw.netplatform.dao.admin.agency.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.agency.IAgencyDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.agency.Agency;
import com.examw.netplatform.model.admin.agency.AgencyInfo;

/**
 * 培训机构数据接口实现。
 * @author yangyong.
 * @since 2014-04-29.
 */
public class AgencyDaoImpl extends BaseDaoImpl<Agency> implements IAgencyDao {
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.IAgencyDao#findAgencies(com.examw.netplatform.model.admin.AgencyInfo)
	 */
	@Override
	public List<Agency> findAgencies(AgencyInfo info) {
		String hql = "from Agency a where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by a." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 数据总数。
	 * @see com.examw.netplatform.dao.admin.IAgencyDao#total(com.examw.netplatform.model.admin.AgencyInfo)
	 */
	@Override
	public Long total(AgencyInfo info) {
		String hql = "select count(*) from Agency a where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件到HQL。
	private String addWhere(AgencyInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getName())){
			hql += "  and ((a.name like :name) or (a.abbr_cn like :name) or (a.abbr_en like :name))";
			parameters.put("name", "%" + info.getName()+ "%");
		}
		return hql;
	}
}