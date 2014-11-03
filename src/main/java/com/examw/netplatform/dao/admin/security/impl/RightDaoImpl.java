package com.examw.netplatform.dao.admin.security.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.security.IRightDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.security.RightInfo;

/**
 * 基本权限数据接口实现。
 * @author yangyong.
 * @since 2014-05-03.
 */
public class RightDaoImpl extends BaseDaoImpl<Right> implements IRightDao {
	private static final Logger logger = Logger.getLogger(RightDaoImpl.class);
	/*
	 * 查询数据。
	 * @see examw.wechat.dao.security.IRightDao#findRights(examw.wechat.model.security.RightInfo)
	 */
	@Override
	public List<Right> findRights(RightInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from Right r where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += " order by r." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据汇总。
	 * @see examw.wechat.dao.security.IRightDao#total(examw.wechat.model.security.RightInfo)
	 */
	@Override
	public Long total(RightInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据汇总...");
		String hql = "select count(*) from Right r where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件
	private String addWhere(RightInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and (r.name like :name)";
			parameters.put("name", info.getName());
		}
		return hql;
	}
}