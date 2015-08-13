package com.examw.netplatform.service.admin.security;

import java.util.List;

import com.examw.model.DataGrid;
import com.examw.netplatform.model.admin.security.RightInfo;

/**
 * 基础权限服务接口。
 * @author yangyong.
 * @since 2014-05-03.
 */
public interface IRightService {
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
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	DataGrid<RightInfo> datagrid(RightInfo info);
	/**
	 * 删除权限。
	 * @param ids
	 */
	void delete(String[] ids);
}