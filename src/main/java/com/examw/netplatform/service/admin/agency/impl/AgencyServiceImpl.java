package com.examw.netplatform.service.admin.agency.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.agency.IAgencyDao;
import com.examw.netplatform.dao.admin.security.IRoleDao;
import com.examw.netplatform.domain.admin.agency.Agency;
import com.examw.netplatform.domain.admin.security.Role;
import com.examw.netplatform.model.admin.agency.AgencyInfo;
import com.examw.netplatform.service.admin.agency.IAgencyService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
/**
 * 培训机构服务接口实现类。
 * @author yangyong.
 * @since 2014-04-29.
 */
public class AgencyServiceImpl extends BaseDataServiceImpl<Agency, AgencyInfo> implements IAgencyService {
	private IAgencyDao agencyDao;
	private IRoleDao roleDao;
	private Map<Integer, String> agencyStatusName;
	/**
	 * 设置培训机构数据接口。
	 * @param agencyDao
	 * 培训机构数据接口。
	 */
	public void setAgencyDao(IAgencyDao agencyDao) {
		this.agencyDao = agencyDao;
	}
	/**
	 * 设置角色数据接口。
	 * @param roleDao
	 */
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	/**
	 * 设置机构状态名称。
	 * @param agencyStatusName
	 */
	public void setAgencyStatusName(Map<Integer, String> agencyStatusName) {
		this.agencyStatusName = agencyStatusName;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Agency> find(AgencyInfo info) {
		return this.agencyDao.findAgencies(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected AgencyInfo changeModel(Agency data) {
		if(data == null) return null;
		AgencyInfo info = new AgencyInfo();
		if(data.getRoles() != null){
			List<String> roleIds = new ArrayList<>();
			for(Role role : data.getRoles()){
				if(role == null) continue;
				roleIds.add(role.getId());
			}
			info.setRoleId(roleIds.toArray(new String[0]));
		}
		BeanUtils.copyProperties(data, info);
		info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}
	/*
	 * 统计查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(AgencyInfo info) {
		return this.agencyDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public AgencyInfo update(AgencyInfo info) {
		if(info == null) return null;
		boolean isAdded = false;
		Agency data = StringUtils.isEmpty(info.getId()) ?  null : this.agencyDao.load(Agency.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			info.setCreateTime(new Date());
			data = new Agency();
		}
		if(!isAdded) info.setCreateTime(data.getCreateTime());
		info.setLastTime(new Date());
		BeanUtils.copyProperties(info, data);
		Set<Role> roles = new HashSet<>();
		if(info.getRoleId() != null && info.getRoleId().length > 0){
			for(String roleId : info.getRoleId()){
				if(StringUtils.isEmpty(roleId)) continue;
				Role role = this.roleDao.load(Role.class, roleId);
				if(role != null) roles.add(role);
			}
		}
		data.setRoles(roles);
		if(isAdded) this.agencyDao.save(data);
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
			if(StringUtils.isEmpty(ids[i])) continue;
			Agency data = this.agencyDao.load(Agency.class, ids[i]);
			if(data != null){
				this.agencyDao.delete(data);
			}
		}
	}
	/*
	 * 获取状态类型。
	 * @see com.examw.netplatform.service.admin.IAgencyService#getStatusName(int)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(status == null || this.agencyStatusName == null || this.agencyStatusName.size() == 0) return null;
		return this.agencyStatusName.get(status);
	}
	/*
	 *加载机构角色。
	 * @see com.examw.netplatform.service.admin.agency.IAgencyService#loadRoles(java.lang.String)
	 */
	@Override
	public Role[] loadRoles(String agencyId) {
		if(StringUtils.isEmpty(agencyId)) return null;
		Agency data = this.agencyDao.load(Agency.class, agencyId);
		if(data != null && data.getRoles() != null){
			return  data.getRoles().toArray(new Role[0]);
		}
		return null;
	}
}