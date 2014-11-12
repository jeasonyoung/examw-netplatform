package com.examw.netplatform.dao.admin.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.settings.IAgencyUserDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.model.admin.settings.AgencyUserInfo;
/**
 * 培训机构数据接口实现类。
 * @author yangyong.
 * @since 2014-07-08.
 */
public class AgencyUserDaoImpl extends BaseDaoImpl<AgencyUser> implements IAgencyUserDao {
	private static final Logger logger = Logger.getLogger(AgencyUserDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.settings.IAgencyUserDao#findAgencieUsers(com.examw.netplatform.model.admin.settings.AgencyUserInfo)
	 */
	@Override
	public List<AgencyUser> findAgencieUsers(AgencyUserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from AgencyUser a where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			switch(info.getSort()){
				case "identityName":
					info.setSort("identity");
					break;
				case "name":
				case "account":
					info.setSort(String.format("user.%s", info.getSort()));
					break;
				case "typeName":
					info.setSort("user.type");
					break;
				case "genderName":
					info.setSort("user.gender");
					break;
				case "statusName":
					info.setSort("user.status");
					break;
				case "agencyName":
					info.setSort("agency.name");
					break;
			}
			hql += " order by a." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.settings.IAgencyUserDao#total(com.examw.netplatform.model.admin.settings.AgencyUserInfo)
	 */
	@Override
	public Long total(AgencyUserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from AgencyUser a where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加查询条件。
	private String addWhere(AgencyUserInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getAgencyId())){
			hql += " and (a.agency.id = :agencyId)";
			parameters.put("agencyId", info.getAgencyId());
		}
		if(!StringUtils.isEmpty(info.getAgencyName())){
			hql += " and (a.agency.name like :agencyName)";
			parameters.put("agencyName", "%"+ info.getAgencyName() +"%");
		} 
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and (a.user.name like :userName or a.user.account like :userName) ";
			parameters.put("userName", "%" + info.getName() + "%");
		}
		if(info.getIdentity() != null){
			hql += " and (a.identity = :identity) ";
			parameters.put("identity", info.getIdentity());
		}
		return hql;
	}
	/*
	 * 加载机构用户数据。
	 * @see com.examw.netplatform.dao.admin.settings.IAgencyUserDao#loadAgencyUser(java.lang.String, java.lang.String)
	 */
	@Override
	public AgencyUser loadAgencyUser(String agencyId, String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载机构［%1$s］用户［%2$s］数据...", agencyId, userId));
		if(StringUtils.isEmpty(agencyId) || StringUtils.isEmpty(userId)) return null;
		final String hql = "from AgencyUser a where (a.agency.id = :agencyId) and (a.user.id = :userId) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("agencyId", agencyId);
		parameters.put("userId", userId);
		List<AgencyUser> list = this.find(hql, parameters, null, null);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}
	/*
	 * 删除机构用户。
	 * @see com.examw.netplatform.dao.admin.settings.IAgencyUserDao#deleteAgencyUser(java.lang.String)
	 */
	@Override
	public Integer deleteAgencyUser(String agencyUserId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除机构用户［%s］...", agencyUserId));
		if(StringUtils.isEmpty(agencyUserId)) return 0;
		final String hql = "delete from AgencyUser a where a.id = :agencyUserId ";
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("agencyUserId", agencyUserId);
		return this.executeUpdate(hql, parameters);
	}
}