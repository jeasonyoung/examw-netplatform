package com.examw.netplatform.dao.admin.teachers.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.teachers.IItemDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.teachers.Item;
import com.examw.netplatform.model.admin.teachers.ItemInfo;

/**
 * 随堂练习试题数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年11月22日
 */
public class ItemDaoImpl extends BaseDaoImpl<Item> implements IItemDao {
	private static final Logger logger = Logger.getLogger(ItemDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.teachers.IItemDao#findItems(com.examw.netplatform.model.admin.teachers.ItemInfo)
	 */
	@Override
	public List<Item> findItems(ItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from Item i where (i.parent is null)";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += " order by i." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.teachers.IItemDao#total(com.examw.netplatform.model.admin.teachers.ItemInfo)
	 */
	@Override
	public Long total(ItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from Item i where (i.parent is null)";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加查询条件
	private String addWhere(ItemInfo info,String hql,Map<String, Object> parameters){
		if(info == null) return hql;
		if(!StringUtils.isEmpty(info.getPracticeId())){//随堂练习ID。
			hql += " and (i.structure.practice.id = :practiceId) ";
			parameters.put("practiceId", info.getPracticeId());
		}
		if(!StringUtils.isEmpty(info.getStructureId())){//结构ID。
			hql += " and (i.structure.id = :structureId) ";
			parameters.put("structureId", info.getStructureId());
		}
		if(info.getType() != null){//类型
			hql += " and (i.type = :type) ";
			parameters.put("type", info.getType());
		}
		if(info.getYear() != null){//使用年份
			hql += " and (i.year = :year) ";
			parameters.put("year", info.getYear());
		}
		if(!StringUtils.isEmpty(info.getContent())){
			hql += " and (i.content like :content) ";
			parameters.put("content", "%"+ info.getContent() +"%");
		}
		return hql;
	}
}