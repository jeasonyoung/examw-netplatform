package com.examw.netplatform.dao.admin.security;

import java.util.List;

import com.examw.netplatform.domain.admin.security.LoginLog;
import com.examw.netplatform.model.admin.security.LoginLogInfo;

/**
 * 登录日志数据接口。
 * @author yangyong.
 * @since 2014-04-17.
 */
public interface LoginLogMapper {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<LoginLogInfo> findLoginLogs(LoginLogInfo info);
	/**
	 * 加载日志。
	 * @param id
	 * @return
	 */
	LoginLogInfo getLoginLog(String id);
	/**
	 * 插入日志。
	 * @param data
	 */
	void insertLoginLog(LoginLog data);
	/**
	 * 删除日志。
	 * @param id
	 */
	void deleteLoginLog(String[] ids);
}