package com.examw.netplatform.dao.admin.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.settings.IAreaDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.settings.Area;
import com.examw.netplatform.model.admin.settings.AreaInfo;
/**
 * 行政地区数据接口实现类
 * @author fengwei.
 * @since 2014年8月6日 上午11:55:00.
 */
public class AreaDaoImpl extends BaseDaoImpl<Area> implements IAreaDao {
	private static final Logger logger = Logger.getLogger(AreaDaoImpl.class);
	/*
	 * 加载全部地区数据。
	 * @see com.examw.test.dao.settings.IAreaDao#loadAreas()
	 */
	@Override
	public List<Area> loadAreas() {
		if(logger.isDebugEnabled()) logger.debug("加载全部地区数据...");
		final String hql = "from Area a order by a.code";
		return this.find(hql, null, null, null);
	}
	/*
	 * 查询数据
	 * @see com.examw.test.dao.settings.IAreaDao#findAreas(com.examw.test.model.settings.AreaInfo)
	 */
	@Override
	public List<Area> findAreas(AreaInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[地区]数据...");
		String hql = "from Area a where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by a." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据总数
	 * @see com.examw.test.dao.settings.IAreaDao#total(com.examw.test.model.settings.AreaInfo)
	 */
	@Override
	public Long total(AreaInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[地区]数据统计...");
		String hql = "select count(*) from Area a where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	// 添加查询条件到HQL。
	private String addWhere(AreaInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getName())) {
			hql += " and ((a.name like :name) or (a.code like :name))";
			parameters.put("name", "%" + info.getName() + "%");
		}
		return hql;
	}
	/*
	 * 加载最大的代码值.
	 * @see com.examw.test.dao.settings.IAreaDao#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		final String hql = "select max(a.code) from Area a ";
		Object obj = this.uniqueResult(hql, null);
		return obj == null ? null : (int)obj;
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Area data) {
		if(data == null) return;
		int count = 0;
		if(data.getExams() != null && (count = data.getExams().size()) > 0){
			throw new RuntimeException(String.format("地区［%1$s］关联［%2$d］考试，暂不能删除！", data.getName(), count));
		}
		if(data.getSubjects() != null && (count = data.getSubjects().size()) > 0){
			throw new RuntimeException(String.format("地区［%1$s］关联［%2$d］考试科目，暂不能删除！", data.getName(), count));
		}
		if(data.getChapters() != null && (count = data.getChapters().size()) > 0){
			throw new RuntimeException(String.format("地区［%1$s］关联［%2$d］章节，暂不能删除！", data.getName(), count));
		}
		super.delete(data);
	}
}