package com.examw.netplatform.dao.admin.security;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.security.RightInfo;

/**
 * 基础权限数据接口。
 * @author yangyong.
 * @since 2014-05-03.
 */
public interface IRightDao extends IBaseDao<Right> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Right> findRights(RightInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(RightInfo info);
}