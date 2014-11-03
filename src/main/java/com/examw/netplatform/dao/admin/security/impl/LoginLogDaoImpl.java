package com.examw.netplatform.dao.admin.security.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.security.ILoginLogDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.security.LoginLog;
import com.examw.netplatform.model.admin.security.LoginLogInfo;

/**
 * 登录日志数据接口实现。
 * @author yangyong.
 * @since 2014-05-17.
 */
public class LoginLogDaoImpl extends BaseDaoImpl<LoginLog> implements ILoginLogDao{
	private static final Logger logger = Logger.getLogger(LoginLogDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.ILoginLogDao#findLoginLogs(com.examw.netplatform.model.admin.LoginLogInfo)
	 */
	@Override
	public List<LoginLog> findLoginLogs(LoginLogInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from LoginLog l where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getOrder() == null) info.setOrder("");
			hql += " order by l." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.ILoginLogDao#total(com.examw.netplatform.model.admin.LoginLogInfo)
	 */
	@Override
	public Long total(LoginLogInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from LoginLog l where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	// 添加查询条件.
	private String addWhere(LoginLogInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getAccount())){
			hql += " and ((l.account like :account) or (l.ip like :account) or (l.browser like :account)) ";
			parameters.put("account", "%" + info.getAccount() + "%");
		}
		return hql;
	}
}