package com.examw.netplatform.dao.admin.settings;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.settings.Catalog;
import com.examw.netplatform.model.admin.settings.CatalogInfo;

/**
 * 考试类别数据接口。
 * @author yangyong.
 * @since 2014-04-28.
 */
public interface ICatalogDao extends IBaseDao<Catalog> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Catalog> findCatalogs(CatalogInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(CatalogInfo info);
}