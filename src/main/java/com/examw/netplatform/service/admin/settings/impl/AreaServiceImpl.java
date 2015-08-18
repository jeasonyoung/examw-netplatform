package com.examw.netplatform.service.admin.settings.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.settings.AreaMapper;
import com.examw.netplatform.domain.admin.settings.Area;
import com.examw.netplatform.model.admin.settings.AreaInfo;
import com.examw.netplatform.service.admin.settings.IAreaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 行政地区服务数据接口实现类
 * @author fengwei.
 * @since 2014年8月6日 下午1:49:39.
 */
public class AreaServiceImpl implements IAreaService {
	private static final Logger logger = Logger.getLogger(AreaServiceImpl.class);
	private AreaMapper areaDao;
	/**
	 * 设置地区数据接口。
	 * @param areaDao
	 * 地区数据接口。
	 */
	public void setAreaDao(AreaMapper areaDao) {
		logger.debug("注入地区数据接口...");
		this.areaDao = areaDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.service.IDataService#datagrid(java.lang.Object)
	 */
	@Override
	public DataGrid<AreaInfo> datagrid(AreaInfo info) {
		logger.debug("查询数据...");
		//分页排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getSort()) + " " + StringUtils.trimToEmpty(info.getOrder()));
		//查询数据
		final List<Area> list = this.areaDao.findAreas(info);
		//分页信息
		final PageInfo<Area> pageInfo = new PageInfo<Area>(list);
		//初始化
		final DataGrid<AreaInfo> grid = new DataGrid<AreaInfo>();
		//设置数据
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//批量数据类型转换。
	private List<AreaInfo> changeModel(List<Area> areas){
		final List<AreaInfo> list = new ArrayList<AreaInfo>();
		if(areas != null && areas.size() > 0){
			for (Area area : areas) {
				if(area == null) continue;
				list.add(this.conversion(area));
			}
		}
		return list;
	}
	/*
	 * 数据类型转换。
	 * @see com.examw.netplatform.service.admin.settings.IAreaService#conversion(com.examw.netplatform.domain.admin.settings.Area)
	 */
	@Override
	public AreaInfo conversion(Area data) {
		logger.debug("数据类型转换[Area => AreaInfo]...");
		if(data != null){
				final AreaInfo info = new AreaInfo();
				BeanUtils.copyProperties(data, info);
				return info;
		}
		return null;
	}
	/*
	 * 加载数据。
	 * @see com.examw.netplatform.service.admin.settings.IAreaService#loadAllAreas()
	 */
	@Override
	public List<AreaInfo> loadAllAreas() {
		logger.debug("加载数据...");
		final AreaInfo info = new AreaInfo();
		info.setSort("code");
		return this.changeModel(this.areaDao.findAreas(info));
	}
	/*
	 * 加载最大代码。
	 * @see com.examw.netplatform.service.admin.settings.IAreaService#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		logger.debug("加载最大代码...");
		return this.areaDao.loadMaxCode();
	}
	/*
	 * 加载地区。
	 * @see com.examw.netplatform.service.admin.settings.IAreaService#loadAreaById(java.lang.String)
	 */
	@Override
	public Area loadAreaById(String areaId) {
		logger.debug("加载地区..." + areaId);
		return this.areaDao.getArea(areaId);
	}
	/*
	 * 更新数据。
	 * @see com.examw.service.IDataService#update(java.lang.Object)
	 */
	@Override
	public AreaInfo update(AreaInfo info) {
		logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Area data = StringUtils.isBlank(info.getId()) ?  null : this.areaDao.getArea(info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId()))
				info.setId(UUID.randomUUID().toString());
			//检查唯一性
			if(this.areaDao.hasAreaCode(info.getCode())){
				throw new RuntimeException("地区代码["+info.getCode()+"]已存在!");
			}
			if(this.areaDao.hasAreaAbbr(info.getAbbr())){
				throw new RuntimeException("地区EN简称["+info.getAbbr()+"]已存在！");
			}
			//
			data = new Area();
		}
		//赋值
		BeanUtils.copyProperties(info, data);
		//保存
		if(isAdded){
			logger.debug("新增地区...");
			this.areaDao.insertArea(data);
		}else {
			logger.debug("更新地区");
			this.areaDao.updateArea(data);
		}
		//返回
		return this.conversion(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.service.IDataService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除数据...");
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id)) continue;
				this.areaDao.deleteArea(id);
			}
		}
	}
}