package com.examw.netplatform.service.admin.settings.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.settings.IAgencyDao;
import com.examw.netplatform.domain.admin.security.Role;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.model.admin.security.RoleInfo;
import com.examw.netplatform.model.admin.settings.AgencyInfo;
import com.examw.netplatform.service.admin.security.IRoleService;
import com.examw.netplatform.service.admin.settings.IAgencyService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
/**
 * 培训机构服务接口实现类。
 * @author yangyong.
 * @since 2014-04-29.
 */
public class AgencyServiceImpl extends BaseDataServiceImpl<Agency, AgencyInfo> implements IAgencyService {
	private static final Logger logger = Logger.getLogger(AgencyServiceImpl.class);
	private IAgencyDao agencyDao; 
	private IRoleService roleService;
	private Map<Integer, String> statusMap;
	/**
	 * 设置培训机构数据接口。
	 * @param agencyDao
	 * 培训机构数据接口。
	 */
	public void setAgencyDao(IAgencyDao agencyDao) {
		if(logger.isDebugEnabled()) logger.debug("注入培训机构数据接口...");
		this.agencyDao = agencyDao;
	}
	/**
	 * 设置角色服务接口。
	 * @param roleService 
	 *	  角色服务接口。
	 */
	public void setRoleService(IRoleService roleService) {
		if(logger.isDebugEnabled()) logger.debug("注入角色服务接口...");
		this.roleService = roleService;
	}
	/**
	 * 设置状态值名称集合。
	 * @param statusMap
	 * 状态值名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("注入状态值名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态值名称。
	 * @see com.examw.netplatform.service.admin.IAgencyService#getStatusName(int)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载状态值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 加载培训机构数据。
	 * @see com.examw.netplatform.service.admin.settings.IAgencyService#loadAgency(java.lang.String)
	 */
	@Override
	public Agency loadAgency(String agencyId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载培训机构［%s］数据...", agencyId));
		if(StringUtils.isEmpty(agencyId)) return null;
		return this.agencyDao.load(Agency.class, agencyId);
	}
	/*
	 * 加载全部的机构数据。
	 * @see com.examw.netplatform.service.admin.settings.IAgencyService#loadAllAgencies()
	 */
	@Override
	public List<AgencyInfo> loadAllAgencies() {
		if(logger.isDebugEnabled()) logger.debug("加载全部的机构数据...");
		return this.changeModel(this.agencyDao.findAgencies(new AgencyInfo()));
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Agency> find(AgencyInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.agencyDao.findAgencies(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected AgencyInfo changeModel(Agency data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Agency => AgencyInfo ...");
		if(data == null) return null;
		AgencyInfo info = new AgencyInfo();
		BeanUtils.copyProperties(data, info);
		info.setStatusName(this.loadStatusName(data.getStatus())); 
		if(data.getRoles() != null){
			List<String> roleIds = new ArrayList<>(), roleNames = new ArrayList<>();
			for(Role role : data.getRoles()){
				if(role == null) continue;
				roleIds.add(role.getId());
				roleNames.add(role.getName());
			}
			info.setRoleId(roleIds.toArray(new String[0]));
			info.setRoleName(roleNames.toArray(new String[0]));
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(AgencyInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.agencyDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public AgencyInfo update(AgencyInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Agency data = StringUtils.isEmpty(info.getId()) ?  null : this.agencyDao.load(Agency.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			info.setCreateTime(new Date());
			data = new Agency();
		}else {
			info.setCreateTime(data.getCreateTime());
		}
		info.setLastTime(new Date());
		BeanUtils.copyProperties(info, data);
		Set<Role> roles = null;
		if(info.getRoleId() != null && info.getRoleId().length > 0){
			roles = new HashSet<>();
			for(String roleId : info.getRoleId()){
				if(StringUtils.isEmpty(roleId)) continue;
				Role role = this.roleService.loadRole(roleId);
				if(role != null) roles.add(role);
			}
		}
		data.setRoles(roles);
		if(isAdded) this.agencyDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s ...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Agency data = this.agencyDao.load(Agency.class, ids[i]);
			if(data != null){
				this.agencyDao.delete(data);
			}
		}
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.admin.settings.IAgencyService#conversion(com.examw.netplatform.domain.admin.settings.Agency)
	 */
	@Override
	public AgencyInfo conversion(Agency agency) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Agency => AgencyInfo...");
		return this.changeModel(agency);
	}
	/*
	 * 加载机构角色集合。
	 * @see com.examw.netplatform.service.admin.settings.IAgencyService#loadRoles(java.lang.String)
	 */
	@Override
	public List<RoleInfo> loadRoles(String agencyId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载机构［%s］角色集合...", agencyId));
		List<RoleInfo> roles = new ArrayList<>();
		if(!StringUtils.isEmpty(agencyId)){
			Agency agency = this.agencyDao.load(Agency.class, agencyId);
			if(agency != null && agency.getRoles() != null && agency.getRoles().size() > 0){
				for(Role role : agency.getRoles()){
					if(role == null) continue;
					RoleInfo roleInfo = this.roleService.conversion(role);
					if(roleInfo != null) roles.add(roleInfo);
				}
			}
		}	
		return roles;
	}
}