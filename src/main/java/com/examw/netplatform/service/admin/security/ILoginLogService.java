package com.examw.netplatform.service.admin.security;

import com.examw.model.DataGrid;
import com.examw.netplatform.model.admin.security.LoginLogInfo;

/**
 * 登录日志服务接口。
 * @author yangyong.
 * @since 2014-05-17.
 */
public interface ILoginLogService {
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	DataGrid<LoginLogInfo> datagrid(LoginLogInfo info);
	/**
	 * 更新日志。
	 * @param info
	 */
	void update(LoginLogInfo info);
	/**
	 * 删除数据。
	 * @param ids
	 */
	void delete(String[] ids);
}