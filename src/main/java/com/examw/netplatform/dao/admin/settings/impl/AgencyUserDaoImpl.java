package com.examw.netplatform.dao.admin.settings.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.settings.IAgencyUserDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.model.admin.settings.AgencyUserInfo;
import com.examw.netplatform.service.admin.security.UserType;
import com.examw.netplatform.service.admin.settings.AgencyUserIdentity;
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
				case "nickName":
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
		if(info.getIdentity() != null){
			List<Integer> identities = new ArrayList<>();
			for(AgencyUserIdentity identity : AgencyUserIdentity.values()){
				if((identity.getValue() & (int)info.getIdentity()) == identity.getValue()){
					identities.add(identity.getValue());
				}
			}
			if(identities.size() > 0){
				hql += " and (a.identity in (:identity)) ";
				parameters.put("identity", identities.toArray(new Integer[0]));
			}
		}
		if(!StringUtils.isEmpty(info.getAgencyName())){
			hql += " and (a.agency.name like :agencyName)";
			parameters.put("agencyName", "%"+ info.getAgencyName() +"%");
		} 
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and ((a.user.name like :userName) or (a.user.account like :userName)) ";
			parameters.put("userName", "%" + info.getName() + "%");
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
	/*
	 * 加载用户下的机构集合。
	 * @see com.examw.netplatform.dao.admin.settings.IAgencyUserDao#loadAgenciesByUser(java.lang.String)
	 */
	@Override
	public List<Agency> loadAgenciesByUser(String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［%s］下的机构集合...", userId));
		if(StringUtils.isEmpty(userId)) return null;
		final String hql = "select au.agency from AgencyUser au where au.user.id = :userId order by au.createTime desc,au.lastTime";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		List<?> list =  this.query(hql, parameters, null, null);
		if(list != null && list.size() > 0){
			List<Agency> agencies = new ArrayList<>();
			for(Object obj : list){
				if(obj == null) continue;
				if(obj instanceof Agency){
					agencies.add((Agency)obj);
				}
			}
			return agencies;
		}
		return null;
	}
	
	/*
	 * 根据学员ID,加载机构学员对象
	 * @see com.examw.netplatform.dao.admin.settings.IAgencyUserDao#loadStudent(java.lang.String)
	 */
	@Override
	public AgencyUser loadStudent(String account) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［%s］...", account));
		if(StringUtils.isEmpty(account)) return null;
		final String hql = "from AgencyUser a where (a.user.account = :account) and (a.user.type = :type)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("account", account);
		parameters.put("type", UserType.FRONT.getValue());
		List<AgencyUser> list = this.find(hql, parameters, null, null);
		if(list !=null && list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}
}