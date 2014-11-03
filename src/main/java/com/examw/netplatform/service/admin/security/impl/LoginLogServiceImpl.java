package com.examw.netplatform.service.admin.security.impl;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.security.ILoginLogDao;
import com.examw.netplatform.domain.admin.security.LoginLog;
import com.examw.netplatform.model.admin.security.LoginLogInfo;
import com.examw.netplatform.service.admin.security.ILoginLogService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
/**
 * 登录日志服务实现。
 * @author yangyong.
 * @since 2014-05-17.
 */
public class LoginLogServiceImpl extends BaseDataServiceImpl<LoginLog, LoginLogInfo> implements ILoginLogService {
	private static final Logger logger = Logger.getLogger(LoginLogServiceImpl.class);
	private ILoginLogDao loginLogDao;
	/**
	 * 设置登录日志数据接口。
	 * @param loginLogDao
	 * 数据接口。
	 */
	public void setLoginLogDao(ILoginLogDao loginLogDao) {
		if(logger.isDebugEnabled()) logger.debug("注入登录日志数据接口...");
		this.loginLogDao = loginLogDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<LoginLog> find(LoginLogInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.loginLogDao.findLoginLogs(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected LoginLogInfo changeModel(LoginLog data) {
		if(logger.isDebugEnabled()) logger.debug(" 数据模型转换 LoginLog => LoginLogInfo");
		if(data == null) return null;
		LoginLogInfo info = new LoginLogInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(LoginLogInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.loginLogDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public LoginLogInfo update(LoginLogInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		LoginLog data = StringUtils.isEmpty(info.getId()) ? null : this.loginLogDao.load(LoginLog.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new LoginLog();
		}
		BeanUtils.copyProperties(info, data);
		if(isAdded)this.loginLogDao.save(data);
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			LoginLog data = this.loginLogDao.load(LoginLog.class, ids[i]);
			if(data != null) this.loginLogDao.delete(data);
		}
	}
}