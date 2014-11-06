package com.examw.netplatform.service.admin.agency.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.agency.IAgencyUserDao;
import com.examw.netplatform.dao.admin.settings.IAgencyDao;
import com.examw.netplatform.domain.admin.agency.AgencyUser;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.model.admin.agency.AgencyUserInfo;
import com.examw.netplatform.model.admin.security.UserInfo;
import com.examw.netplatform.model.admin.settings.AgencyInfo;
import com.examw.netplatform.service.admin.agency.IAgencyUserService;
import com.examw.netplatform.service.admin.security.IUserService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
/**
 * 机构用户服务接口实现。
 * @author yangyong.
 * @since 2014-07-08.
 */
public class AgencyUserServiceImpl extends BaseDataServiceImpl<AgencyUser, AgencyUserInfo> implements IAgencyUserService {
	private IUserService userService;
	private IAgencyDao agencyDao;
	private IAgencyUserDao agencyUserDao;
	private Map<Integer, String> identitiesMap;
	/**
	 * 设置用户服务接口。
	 * @param userService
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	/**
	 * 设置机构数据接口。
	 * @param agencyDao
	 */
	public void setAgencyDao(IAgencyDao agencyDao) {
		this.agencyDao = agencyDao;
	}
	/**
	 * 设置机构用户数据接口。
	 * @param agencyUserDao
	 * 机构用户数据接口。
	 */
	public void setAgencyUserDao(IAgencyUserDao agencyUserDao) {
		this.agencyUserDao = agencyUserDao;
	}
	/**
	 * 设置身份名称集合。
	 * @param identitiesMap
	 */
	public void setIdentitiesMap(Map<Integer, String> identitiesMap) {
		this.identitiesMap = identitiesMap;
	}
	/*
	 * 加载身份名称。
	 * @see com.examw.netplatform.service.admin.agency.IAgencyUserService#loadIdentityName(java.lang.Integer)
	 */
	@Override
	public String loadIdentityName(Integer identity) {
		if(this.identitiesMap == null || this.identitiesMap.size() == 0 || identity == null )
			return null;
		return this.identitiesMap.get(identity);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<AgencyUser> find(AgencyUserInfo info) {
		return this.agencyUserDao.findAgencieUsers(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected AgencyUserInfo changeModel(AgencyUser data) {
		if(data == null) return null;
		AgencyUserInfo info = new AgencyUserInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getAgency() != null){
			info.setAgencyId(data.getAgency().getId());
			info.setAgencyName(data.getAgency().getName());
		}
		if(data.getUser() != null){
			UserInfo usr = this.userService.conversion(data.getUser(),false);
			if(usr != null){
				info.setUserId(usr.getId());
				info.setUserName(usr.getName());
				BeanUtils.copyProperties(usr, info, new String[]{"id","createTime"});
			}
		}
		info.setIdentityName(this.loadIdentityName(data.getIdentity()));
		return info;
	}
	/*
	 * 查询统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(AgencyUserInfo info) {
		return this.agencyUserDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public AgencyUserInfo update(AgencyUserInfo info) {
		if(info == null) return null;
		boolean isAdded = false;
		AgencyUser data = this.agencyUserDao.load(AgencyUser.class, StringUtils.isEmpty(info.getId()) ? null : info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId()))info.setId(UUID.randomUUID().toString());
			data = new AgencyUser();
			info.setCreateTime(new Date());
		}
		if(!isAdded) info.setCreateTime(data.getCreateTime());
		info.setLastTime(new Date());
		BeanUtils.copyProperties(info, data);
		if(!StringUtils.isEmpty(info.getAgencyId())){
			Agency agency = this.agencyDao.load(Agency.class, info.getAgencyId());
			if(agency != null) data.setAgency(agency);
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setId(info.getUserId());
		userInfo.setName(info.getUserName());
		BeanUtils.copyProperties(info, userInfo, new String[]{"id"});
		//data.setUser(this.userService.update(userInfo));
		if(data.getUser() != null){
			info.setUserId(data.getUser().getId());
			info.setUserName(data.getUser().getName());
		}
		if(data.getAgency() != null) info.setAgencyName(data.getAgency().getName());
		if(isAdded)this.agencyUserDao.save(data);
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			AgencyUser data = this.agencyUserDao.load(AgencyUser.class, ids[i]);
			if(data != null){
				 this.agencyUserDao.delete(data);
			}
		}
	}
	/*
	 * 加载用户机构集合。
	 * @see com.examw.netplatform.service.admin.agency.IAgencyUserService#loadAgencies(java.lang.String)
	 */
	@Override
	public List<AgencyInfo> loadAgencies(String userId) {
		if(StringUtils.isEmpty(userId)) return null;
		List<Agency> agencies = this.agencyUserDao.loadAgencies(userId);
		if(agencies == null || agencies.size() == 0) return null;
		List<AgencyInfo> list = new ArrayList<>();
		for(Agency data : agencies){
			if(data == null) continue;
			AgencyInfo info = new AgencyInfo();
			BeanUtils.copyProperties(data, info);
			list.add(info);
		}
		return list;
	}
	/*
	 *  加载机构下的用户集合。
	 * @see com.examw.netplatform.service.admin.agency.IAgencyUserService#loadUsers(java.lang.String, java.lang.Integer[])
	 */
	@Override
	public List<UserInfo> loadUsers(String agencyId, Integer[] identities) {
		List<UserInfo> list = new ArrayList<>(); 
		if(!StringUtils.isEmpty(agencyId) && identities != null && identities.length > 0){
			List<User> users = this.agencyUserDao.loadUsers(agencyId, identities);
			if(users != null){
				for(int i = 0; i < users.size(); i++){
					//UserInfo info = this.userService.conversion(users.get(i));
					//if(info != null) list.add(info);
				}
			}
		}
		return list;
	}
}