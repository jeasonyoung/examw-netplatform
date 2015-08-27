package com.examw.netplatform.service.admin.settings.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.settings.AgencyMapper;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.model.admin.settings.AgencyInfo;
import com.examw.netplatform.service.admin.settings.IAgencyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 培训机构服务接口实现类。
 * @author yangyong.
 * @since 2014-04-29.
 */
public class AgencyServiceImpl implements IAgencyService {
	private static final Logger logger = Logger.getLogger(AgencyServiceImpl.class);
	private AgencyMapper agencyDao;
	private Map<Integer, String> statusMap;
	/**
	 * 设置培训机构数据接口。
	 * @param agencyDao
	 * 培训机构数据接口。
	 */
	public void setAgencyDao(AgencyMapper agencyDao) {
		logger.debug("注入培训机构数据接口...");
		this.agencyDao = agencyDao;
	}
	/**
	 * 设置状态值名称集合。
	 * @param statusMap
	 * 状态值名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		logger.debug("注入状态值名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态值名称。
	 * @see com.examw.netplatform.service.admin.IAgencyService#getStatusName(int)
	 */
	@Override
	public String loadStatusName(Integer status) {
		logger.debug(String.format("加载状态值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 加载机构名称。
	 * @see com.examw.netplatform.service.admin.settings.IAgencyService#loadAgencyName(java.lang.String)
	 */
	@Override
	public String loadAgencyName(String agencyId) {
		logger.debug("加载机构["+agencyId+"]名称...");
		if(StringUtils.isNotBlank(agencyId)){
			final Agency agency = this.agencyDao.getAgency(agencyId);
			if(agency != null) return agency.getName();
		}
		return null;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.settings.IAgencyService#datagrid(com.examw.netplatform.model.admin.settings.AgencyInfo)
	 */
	@Override
	public DataGrid<AgencyInfo> datagrid(AgencyInfo info) {
		logger.debug("查询数据...");
		//分页排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getSort()) + " " + StringUtils.trimToEmpty(info.getOrder()));
		//查询数据
		final List<Agency> list = this.agencyDao.findAgencies(info);
		//分页信息
		final PageInfo<Agency> pageInfo = new PageInfo<Agency>(list);
		//初始化
		final DataGrid<AgencyInfo> grid = new DataGrid<AgencyInfo>();
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//类型批量转换
	private List<AgencyInfo> changeModel(List<Agency> agencies){
		final List<AgencyInfo> list = new ArrayList<AgencyInfo>();
		if(agencies != null && agencies.size() > 0){
			for(Agency agency : agencies){
				if(agency == null) continue;
				list.add(this.conversion(agency));
			}
		}
		return list;
	}
	/*
	 * 加载用户所属机构集合。
	 * @see com.examw.netplatform.service.admin.settings.IAgencyService#loadAgencies(java.lang.String)
	 */
	@Override
	public List<AgencyInfo> loadAgencies(String userId) {
		logger.debug("加载用户["+userId+"]所属机构集合...");
		if(StringUtils.isBlank(userId)){
			AgencyInfo info = new AgencyInfo();
			info.setSort("name");
			return this.changeModel(this.agencyDao.findAgencies(info));
		}
		return this.changeModel(this.agencyDao.findAgenciesByUser(userId));
	}
//	/*
//	 * 加载机构数据。
//	 * @see com.examw.netplatform.service.admin.settings.IAgencyService#loadAgency(java.lang.String)
//	 */
//	@Override
//	public Agency loadAgency(String agencyId) {
//		logger.debug("加载机构数据..." + agencyId);
//		return this.agencyDao.getAgency(agencyId);
//	}
	/*
	 * 加载EN简称机构数据。
	 * @see com.examw.netplatform.service.admin.settings.IAgencyService#loadAgencyByAbbr(java.lang.String)
	 */
	@Override
	public Agency loadAgencyByAbbr(String abbr_en) {
		logger.debug("加载EN简称机构数据..." + abbr_en);
		return this.agencyDao.loadAgencyByAbbrEN(abbr_en);
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.admin.settings.IAgencyService#conversion(com.examw.netplatform.domain.admin.settings.Agency)
	 */
	@Override
	public AgencyInfo conversion(Agency data) {
		logger.debug("类型转换[Agency -> AgencyInfo]...");
		if(data != null){
			final AgencyInfo info = new AgencyInfo();
			BeanUtils.copyProperties(data, info);
			info.setStatusName(this.loadStatusName(info.getStatus()));
			return info;
		}
		return null;
	}
	/*
	 * 更新机构数据。
	 * @see com.examw.netplatform.service.admin.settings.IAgencyService#update(com.examw.netplatform.model.admin.settings.AgencyInfo)
	 */
	@Override
	public AgencyInfo update(AgencyInfo info) {
		logger.debug("更新机构数据...");
		if(info == null) return null;
		Agency data = StringUtils.isBlank(info.getId()) ? null : this.agencyDao.getAgency(info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			if(StringUtils.isBlank(info.getId())) info.setId(UUID.randomUUID().toString());
			//检查唯一性
			if(this.agencyDao.hasAgencyByAbbrEN(info.getAbbrEN())){
				throw new RuntimeException("机构EN简称["+info.getAbbrEN()+"]已存在!");
			}
			if(this.agencyDao.hasAgencyByAbbrCN(info.getAbbrCN())){
				throw new RuntimeException("机构CN简称["+info.getAbbrCN()+"]已存在!");
			}
			//初始化
			data = new Agency();
		}
		//赋值
		BeanUtils.copyProperties(info, data);
		//
		if(isAdded){
			logger.debug("新增培训机构...");
			this.agencyDao.insertAgency(data);
		}else {
			logger.debug("更新培训机构...");
			this.agencyDao.updateAgency(data);
		}
		//返回
		return this.conversion(data);
	}
	/*
	 * 删除机构。
	 * @see com.examw.netplatform.service.admin.settings.IAgencyService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除机构..." + StringUtils.join(ids,","));
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id)) continue;
				this.agencyDao.deleteAgency(id);
			}
		}
	}
}