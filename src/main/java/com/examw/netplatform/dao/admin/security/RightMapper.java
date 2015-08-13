package com.examw.netplatform.dao.admin.security;

import java.util.List;

import com.examw.netplatform.domain.admin.security.Right;

/**
 * 基础权限数据接口。
 * @author yangyong.
 * @since 2014-05-03.
 */
public interface RightMapper {
	/**
	 * 加载权限。
	 * @param id
	 * @return
	 */
	Right getRight(String id);
	/**
	 * 查询数据。
	 * @param name
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Right> findRights(String name);
	/**
	 * 插入权限。
	 * @param data
	 */
	void insertRight(Right data);
	/**
	 * 更新权限。
	 * @param data
	 */
	void updateRight(Right data);
	/**
	 * 删除权限。
	 * @param id
	 */
	void deleteRight(String id);
}