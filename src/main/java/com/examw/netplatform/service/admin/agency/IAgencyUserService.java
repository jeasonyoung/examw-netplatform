package com.examw.netplatform.service.admin.agency;

import java.util.List;

import com.examw.netplatform.model.admin.agency.AgencyUserInfo;
import com.examw.netplatform.model.admin.security.UserInfo;
import com.examw.netplatform.model.admin.settings.AgencyInfo;
import com.examw.netplatform.service.IBaseDataService;

/**
 * 机构用户服务接口。
 * @author yangyong.
 * @since 2014-07-08.
 */
public interface IAgencyUserService extends IBaseDataService<AgencyUserInfo> {
	/**
	 * 获取身份名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadIdentityName(Integer identity);
	/**
	 * 加载机构集合。
	 * @param userId
	 * 用户ID。
	 * @return
	 */
	List<AgencyInfo> loadAgencies(String userId);
	/**
	 * 加载机构下的用户集合。
	 * @param agencyId
	 * 机构ID。
	 * @param identities
	 * 身份类型。
	 * @return
	 * 用户集合。
	 */
	List<UserInfo> loadUsers(String agencyId,Integer[] identities);
}