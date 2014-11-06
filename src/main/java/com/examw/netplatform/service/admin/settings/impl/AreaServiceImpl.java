package com.examw.netplatform.service.admin.settings.impl;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.settings.IAreaDao;
import com.examw.netplatform.domain.admin.settings.Area;
import com.examw.netplatform.model.admin.settings.AreaInfo;
import com.examw.netplatform.service.admin.settings.IAreaService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;

/**
 * 行政地区服务数据接口实现类
 * @author fengwei.
 * @since 2014年8月6日 下午1:49:39.
 */
public class AreaServiceImpl extends BaseDataServiceImpl<Area, AreaInfo> implements IAreaService {
	private static final Logger logger = Logger.getLogger(AreaServiceImpl.class);
	private IAreaDao areaDao;
	/**
	 * 设置地区数据接口。
	 * @param areaDao
	 * 地区数据接口。
	 */
	public void setAreaDao(IAreaDao areaDao) {
		if(logger.isDebugEnabled()) logger.debug("注入地区数据接口...");
		this.areaDao = areaDao;
	}
	/*
	 * 根据地区ID加载地区。
	 * @see com.examw.netplatform.service.admin.settings.IAreaService#loadAreaById(java.lang.String)
	 */
	@Override
	public Area loadAreaById(String areaId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("根据地区ID［%s］加载地区...", areaId));
		if(StringUtils.isEmpty(areaId)) return null;
		return this.areaDao.load(Area.class, areaId);
	}
	/*
	 *  加载全部地区数据。
	 * @see com.examw.test.service.settings.IAreaService#loadAllAreas()
	 */
	@Override
	public List<AreaInfo> loadAllAreas() {
		if(logger.isDebugEnabled()) logger.debug(" 加载全部地区数据...");
		return this.changeModel(this.areaDao.loadAreas());
	}
	/*
	 * 根据条件查找地区集合。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Area> find(AreaInfo info) {
		if (logger.isDebugEnabled())logger.debug("查询[地区]数据...");
		return areaDao.findAreas(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected AreaInfo changeModel(Area data) {
		if (logger.isDebugEnabled())logger.debug("[地区]数据模型转换...");
		if (data == null) return null;
		AreaInfo info = new AreaInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.settings.IAreaService#conversion(com.examw.test.domain.settings.Area)
	 */
	@Override
	public AreaInfo conversion(Area area) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换...");
		return this.changeModel(area);
	}
	/*
	 * 数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(AreaInfo info) {
		if (logger.isDebugEnabled())logger.debug("查询统计...");
		return areaDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public AreaInfo update(AreaInfo info) {
		if (logger.isDebugEnabled())logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Area  data = StringUtils.isEmpty(info.getId()) ?  null : this.areaDao.load(Area.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Area();
		}
		BeanUtils.copyProperties(info, data);
		//新增数据。
		if(isAdded) this.areaDao.save(data);
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if (logger.isDebugEnabled())logger.debug("删除数据...");
		if (ids == null || ids.length == 0) return;
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.isEmpty(ids[i]))continue;
			Area data = this.areaDao.load(Area.class, ids[i]);
			if (data != null) {
				logger.debug("删除地区数据：" + ids[i]);
				this.areaDao.delete(data);
			}
		}
	}
	/*
	 * 加载最大的代码值
	 * @see com.examw.test.service.settings.IAreaService#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		return this.areaDao.loadMaxCode();
	}
}