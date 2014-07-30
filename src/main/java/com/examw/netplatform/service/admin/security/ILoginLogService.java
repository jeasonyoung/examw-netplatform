package com.examw.netplatform.service.admin.security;

import com.examw.netplatform.model.admin.security.LoginLogInfo;
import com.examw.netplatform.service.IBaseDataService;

/**
 * 登录日志服务接口。
 * @author yangyong.
 * @since 2014-05-17.
 */
public interface ILoginLogService extends IBaseDataService<LoginLogInfo> {
	/**
	 * 添加日志。
	 * @param account
	 * 登录账号。
	 * @param ip
	 * 登录IP。
	 * @param browser
	 * 浏览器信息。
	 */
	void addLog(String account,String ip,String browser);
}