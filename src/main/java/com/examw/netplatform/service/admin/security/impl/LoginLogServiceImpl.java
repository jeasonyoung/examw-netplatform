package com.examw.netplatform.service.admin.security.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.security.LoginLogMapper;
import com.examw.netplatform.domain.admin.security.LoginLog;
import com.examw.netplatform.model.admin.security.LoginLogInfo;
import com.examw.netplatform.service.admin.security.ILoginLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 登录日志服务实现。
 * @author yangyong.
 * @since 2014-05-17.
 */
public class LoginLogServiceImpl implements ILoginLogService {
	private static final Logger logger = Logger.getLogger(LoginLogServiceImpl.class);
	private LoginLogMapper loginLogDao;
	/**
	 * 设置登录日志数据接口。
	 * @param loginLogDao
	 * 数据接口。
	 */
	public void setLoginLogDao(LoginLogMapper loginLogDao) {
		logger.debug("注入登录日志数据接口...");
		this.loginLogDao = loginLogDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.security.ILoginLogService#datagrid(com.examw.netplatform.model.admin.security.LoginLogInfo)
	 */
	@Override
	public DataGrid<LoginLogInfo> datagrid(LoginLogInfo info) {
		logger.debug("查询数据...");
		//启用分页
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getSort()) + " " + StringUtils.trimToEmpty(info.getOrder()));
		//查询数据
		final List<LoginLog> list = this.loginLogDao.findLoginLogs(info);
		//分页信息
		final PageInfo<LoginLog> pageInfo = new PageInfo<LoginLog>(list);
		//初始化
		final DataGrid<LoginLogInfo> grid = new DataGrid<LoginLogInfo>();
		//设置返回数据
		grid.setRows(this.changeModel(list));
		//设置总数
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//批量数据类型转换
	private List<LoginLogInfo> changeModel(List<LoginLog> loginLogs){
		final List<LoginLogInfo> list = new ArrayList<LoginLogInfo>();
		if(loginLogs != null && loginLogs.size() > 0){
			for(LoginLog loginLog : loginLogs){
				if(loginLog == null) continue;
				list.add(this.conversion(loginLog));
			}
		}
		return list;
	}
	private LoginLogInfo conversion(LoginLog data){
		if(data != null){
			final LoginLogInfo info = new LoginLogInfo();
			BeanUtils.copyProperties(data, info);
			return info;
		}
		return null;
	}
	/*
	 * 更新日志。
	 * @see com.examw.netplatform.service.admin.security.ILoginLogService#update(com.examw.netplatform.model.admin.security.LoginLogInfo)
	 */
	@Override
	public void update(LoginLogInfo info) {
		logger.debug("新增日志...");
		info.setId(UUID.randomUUID().toString());
		this.loginLogDao.insertLoginLog(info);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.admin.security.ILoginLogService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除数据.." + StringUtils.join(ids, ","));
		if(ids != null && ids.length > 0){
			this.loginLogDao.deleteLoginLog(ids);
		}
	}
}