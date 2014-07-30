package com.examw.netplatform.service.admin.agency;

import com.examw.netplatform.domain.admin.security.Role;
import com.examw.netplatform.model.admin.agency.AgencyInfo;
import com.examw.netplatform.service.IBaseDataService;
/**
 * 培训机构服务接口。
 * @author yangyong。
 * @since 2014-04-28.
 */
public interface IAgencyService extends IBaseDataService<AgencyInfo> {
	/**
	 * 获取状态名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载机构下角色。
	 * @param agencyId
	 * 机构ID。
	 * @return
	 * 角色集合。
	 */
	Role[] loadRoles(String agencyId);
}