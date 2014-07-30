package com.examw.netplatform.service.admin.security;

import com.examw.netplatform.model.admin.security.RightInfo;
import com.examw.netplatform.service.IBaseDataService;

/**
 * 基础权限服务接口。
 * @author yangyong.
 * @since 2014-05-03.
 */
public interface IRightService extends IBaseDataService<RightInfo> {
	/**
	 *  加载权限名称。
	 * @param right
	 * 权限值。
	 * @return
	 * 权限名称。
	 */
	String getRightName(int right);
	/**
	 * 初始化数据。
	 * @throws Exception
	 */
	void init() throws Exception;
}