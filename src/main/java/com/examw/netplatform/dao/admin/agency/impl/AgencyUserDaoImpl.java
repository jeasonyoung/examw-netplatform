package com.examw.netplatform.dao.admin.agency.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.agency.IAgencyUserDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.agency.AgencyUser;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.model.admin.agency.AgencyUserInfo;
/**
 * 培训机构数据接口实现类。
 * @author yangyong.
 * @since 2014-07-08.
 */
public class AgencyUserDaoImpl extends BaseDaoImpl<AgencyUser> implements IAgencyUserDao {
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.agency.IAgencyUserDao#findAgencieUsers(com.examw.netplatform.model.admin.agency.AgencyUserInfo)
	 */
	@Override
	public List<AgencyUser> findAgencieUsers(AgencyUserInfo info) {
		String hql = "from AgencyUser a where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("agencyId")){
				info.setSort("agency.id");
			}
			if(info.getSort().equalsIgnoreCase("agencyName")){
				info.setSort("agency.name");
			}
			if(info.getSort().equalsIgnoreCase("userName")){
				info.setSort("user.name");
			}
			if(info.getSort().equalsIgnoreCase("account")){
				info.setSort("user.acount");
			}
			if(info.getSort().equalsIgnoreCase("nickName")){
				info.setSort("user.nickName");
			}
			if(info.getSort().equalsIgnoreCase("genderName")){
				info.setSort("user.gender");
			}
			if(info.getSort().equalsIgnoreCase("statusName")){
				info.setSort("user.status");
			}
			if(info.getSort().equalsIgnoreCase("identityName")){
				info.setSort("identity");
			}
			hql += " order by a." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	//查询条件。
	private String addWhere(AgencyUserInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getCurrentUserId())){
			hql += " and (a.agency.id in (select au.agency.id  from AgencyUser au where au.user.id = :currentUserId)) ";
			parameters.put("currentUserId", info.getCurrentUserId());
		}
		if(!StringUtils.isEmpty(info.getAgencyId())){
			hql += " and (a.agency.id = :agencyId) ";
			parameters.put("agencyId", info.getAgencyId());
		}
		if(!StringUtils.isEmpty(info.getAgencyName())){
			hql += " and (a.agency.name like :agencyName) ";
			parameters.put("agencyName", "%"+ info.getAgencyName() +"%");
		}
		if(!StringUtils.isEmpty(info.getUserName())){
			hql += " and (a.user.name like :userName) ";
			parameters.put("userName", "%"+ info.getUserName() +"%");
		}
		if(info.getIdentity() != null){
			hql +=" and (a.identity = :identity) ";
			parameters.put("identity", info.getIdentity());
		}
		return hql;
	}
	/*
	 * 查询统计。
	 * @see com.examw.netplatform.dao.admin.agency.IAgencyUserDao#total(com.examw.netplatform.model.admin.agency.AgencyUserInfo)
	 */
	@Override
	public Long total(AgencyUserInfo info) {
		String hql = "select count(*) from AgencyUser a where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	/*
	 * 根据用户ID加载所属机构。
	 * @see com.examw.netplatform.dao.admin.agency.IAgencyUserDao#loadAgencies(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Agency> loadAgencies(String userId) {
		final String hql = "select a.agency  from AgencyUser a  where a.user.id = :userId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		Query query = this.getCurrentSession().createQuery(hql);
		if(query != null){
			this.addParameters(query, parameters);
			return query.list();
		}
		return null;
	}
	/*
	 * 加载机构下用户集合。
	 * @see com.examw.netplatform.dao.admin.agency.IAgencyUserDao#loadUsers(java.lang.String, java.lang.Integer[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> loadUsers(String agencyId, Integer[] identities) {
		final String hql = "select a.user  from AgencyUser a  where (a.agency.id = :agencyId) and (a.identity in (:identities)) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("agencyId", agencyId);
		parameters.put("identities", identities);
		Query query = this.getCurrentSession().createQuery(hql);
		if(query != null){
			this.addParameters(query, parameters);
			return query.list();
		}
		return null;
	}
}