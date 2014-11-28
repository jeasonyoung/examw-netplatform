package com.examw.netplatform.service.admin.settings.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.settings.IAgencyUserDao;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.model.admin.security.UserInfo;
import com.examw.netplatform.model.admin.settings.AgencyInfo;
import com.examw.netplatform.model.admin.settings.AgencyUserInfo;
import com.examw.netplatform.service.admin.security.IUserService;
import com.examw.netplatform.service.admin.settings.IAgencyService;
import com.examw.netplatform.service.admin.settings.IAgencyUserService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
/**
 * 机构用户服务接口实现。
 * @author yangyong.
 * @since 2014-07-08.
 */
public class AgencyUserServiceImpl extends BaseDataServiceImpl<AgencyUser, AgencyUserInfo> implements IAgencyUserService {
	private static final Logger logger = Logger.getLogger(AgencyUserServiceImpl.class);
	protected IAgencyUserDao agencyUserDao;
	private IAgencyService agencyService;
	private IUserService userService;
	private Map<Integer, String> identityNameMap;
	/**
	 * 设置机构用户数据接口。
	 * @param agencyUserDao 
	 *	  机构用户数据接口。
	 */
	public void setAgencyUserDao(IAgencyUserDao agencyUserDao) {
		if(logger.isDebugEnabled()) logger.debug("注入机构用户数据接口...");
		this.agencyUserDao = agencyUserDao;
	}
	/**
	 * 设置机构服务接口。
	 * @param agencyService 
	 *	  机构服务接口。
	 */
	public void setAgencyService(IAgencyService agencyService) {
		if(logger.isDebugEnabled()) logger.debug("注入机构服务接口...");
		this.agencyService = agencyService;
	}
	/**
	 * 设置用户服务接口。
	 * @param userService 
	 *	  用户服务接口。
	 */
	public void setUserService(IUserService userService) {
		if(logger.isDebugEnabled()) logger.debug("注入用户服务接口...");
		this.userService = userService;
	}
	/**
	 * 设置机构用户身份名称集合。
	 * @param identityNameMap 
	 *	  机构用户身份名称集合。
	 */
	public void setIdentityNameMap(Map<Integer, String> identityNameMap) {
		if(logger.isDebugEnabled()) logger.debug("注入机构用户身份名称集合...");
		this.identityNameMap = identityNameMap;
	}
	/*
	 * 加载机构用户身份名称。
	 * @see com.examw.netplatform.service.admin.settings.IAgencyUserService#loadIdentityName(java.lang.Integer)
	 */
	@Override
	public String loadIdentityName(Integer identity) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载机构用户身份［%d］名称...", identity));
		if(identity == null || this.identityNameMap == null || this.identityNameMap.size() == 0) return null;
		return this.identityNameMap.get(identity);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<AgencyUser> find(AgencyUserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.agencyUserDao.findAgencieUsers(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected AgencyUserInfo changeModel(AgencyUser data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 AgencyUser => AgencyUserInfo ...");
		if(data == null) return null;
		AgencyUserInfo info = new AgencyUserInfo();
		if(data.getAgency() != null){
			info.setAgencyId(data.getAgency().getId());
			info.setAgencyName(data.getAgency().getName());
		}
		if(data.getUser() != null){
			 UserInfo userInfo  = this.userService.conversion(data.getUser(), this.isViewPwd());
			 BeanUtils.copyProperties(userInfo, info);
			 info.setUserId(userInfo.getId());
		}
		BeanUtils.copyProperties(data, info);
		info.setIdentityName(this.loadIdentityName(info.getIdentity()));
		return info;
	}
	/**
	 * 获取是否显示密码。
	 * @return
	 */
	protected boolean isViewPwd(){
		return false;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(AgencyUserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.agencyUserDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public AgencyUserInfo update(AgencyUserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		AgencyUser data = StringUtils.isEmpty(info.getId()) ? null : this.agencyUserDao.load(AgencyUser.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getAgencyId())) throw new RuntimeException("所属机构ID不能为空！");
			if(!StringUtils.isEmpty(info.getUserId()))
			{
				isAdded = (null == (data = this.agencyUserDao.loadAgencyUser(info.getAgencyId(), info.getUserId())));
			}
		}
		if(isAdded){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			data = new AgencyUser();
		}else {
			info.setCreateTime(data.getCreateTime());
			if(info.getCreateTime() == null)info.setCreateTime(new Date());
		}
		info.setLastTime(new Date());
		BeanUtils.copyProperties(info, data);
		data.setAgency(this.agencyService.loadAgency(info.getAgencyId()));
		if(data.getAgency() == null) throw new RuntimeException(String.format("培训机构［%s］不存在！", info.getAgencyId()));
		if(StringUtils.isEmpty(info.getUserId())){
			info.setId(null);
		}else {
			info.setId(info.getUserId());
		}
		data.setUser(this.userService.updateUser(info));
		if(data.getUser() == null) throw new RuntimeException("更新用户数据失败！");
		if(isAdded) this.agencyUserDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除机构用户数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除机构用户数据：%s ...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			AgencyUser agencyUser = this.agencyUserDao.load(AgencyUser.class, ids[i]);
			if(agencyUser != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除机构用户：%s",ids[i]));
				String userId = agencyUser.getUser().getId();
				if(this.agencyUserDao.deleteAgencyUser(ids[i]) > 0){
					this.userService.deleteUser(userId);
				}
			}
		}
	}
	/*
	 * 加载用户所属机构ID。
	 * @see com.examw.netplatform.service.admin.settings.IAgencyUserService#loadAgencyIdByUser(java.lang.String)
	 */
	@Override
	public String loadAgencyIdByUser(String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［%s］所属机构ID...", userId));
		if(StringUtils.isEmpty(userId)) return null; 
		List<Agency> agencies = this.agencyUserDao.loadAgenciesByUser(userId);
		return (agencies == null || agencies.size() == 0) ? null : agencies.get(0).getId();
	}
	/*
	 * 加载用户机构集合。
	 * @see com.examw.netplatform.service.admin.settings.IAgencyUserService#loadAgenciesByUser(java.lang.String)
	 */
	@Override
	public List<AgencyInfo> loadAgenciesByUser(String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［%s］机构集合...", userId));
		List<AgencyInfo> list = new ArrayList<>();
		List<Agency> agencies = this.agencyUserDao.loadAgenciesByUser(userId);
		if(agencies != null && agencies.size() > 0){
			for(Agency agency : agencies){
				if(agency == null) continue;
				AgencyInfo info = this.agencyService.conversion(agency);
				if(info != null) list.add(info);
			}
		}
		return list;
	}
}