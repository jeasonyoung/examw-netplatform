package com.examw.netplatform.service.admin.security;

import java.util.List;

import com.examw.netplatform.model.admin.security.RightInfo;
import com.examw.netplatform.service.IBaseDataService;

/**
 * 基础权限服务接口。
 * @author yangyong.
 * @since 2014-05-03.
 */
public interface IRightService extends IBaseDataService<RightInfo> {
	/**
	 * 初始化数据。
	 * @throws Exception
	 */
	void init() throws Exception;
	/**
	 * 加载全部权限数据集合。
	 * @return 全部权限数据集合。
	 */
	List<RightInfo> loadAllRights();
}