package com.examw.netplatform.dao.admin.settings;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.model.admin.settings.AgencyInfo;

/**
 * 培训机构数据接口。
 * @author yangyong.
 * @since 2014-04-29.
 */
public interface IAgencyDao extends IBaseDao<Agency> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Agency> findAgencies(AgencyInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(AgencyInfo info);
}