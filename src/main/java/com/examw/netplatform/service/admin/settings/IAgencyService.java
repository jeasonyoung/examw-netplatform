package com.examw.netplatform.service.admin.settings;

import java.util.List;

import com.examw.model.DataGrid;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.model.admin.settings.AgencyInfo;
/**
 * 培训机构服务接口。
 * @author yangyong。
 * @since 2014-04-28.
 */
public interface IAgencyService{
	/**
	 * 加载状态值名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载培训机构数据。
	 * @param userId
	 * 用户ID。
	 * @return
	 */
	List<AgencyInfo> loadAgencies(String userId);
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	DataGrid<AgencyInfo> datagrid(AgencyInfo info);
	/**
	 * 加载培训机构。
	 * @param agencyId
	 * 机构ID。
	 * @return
	 * 培训机构。
	 */
	Agency loadAgency(String agencyId);
	/**
	 * 根据英文简称加载培训机构。
	 * @param abbr_en
	 * 机构英文简称
	 * @return
	 * 培训机构。
	 */
	Agency loadAgencyByAbbr(String abbr_en);
	/**
	 * 数据模型转换。
	 * @param agency
	 * 培训机构数据。
	 * @return
	 * 培训机构信息。
	 */
	AgencyInfo conversion(Agency agency);
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	AgencyInfo update(AgencyInfo info);
	/**
	 * 删除数据。
	 * @param ids
	 */
	void delete(String[] ids);
}