package com.examw.netplatform.service.admin.settings;

import java.util.List;

import com.examw.netplatform.model.admin.settings.AgencyInfo;
import com.examw.netplatform.model.admin.settings.AgencyUserInfo;
import com.examw.netplatform.service.IBaseDataService;

/**
 * 机构用户服务接口。
 * @author yangyong.
 * @since 2014-07-08.
 */
public interface IAgencyUserService extends IBaseDataService<AgencyUserInfo>,IRandomCodeService {
	/**
	 * 获取身份名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadIdentityName(Integer identity);
	/**
	 * 加载用户机构ID。
	 * @param userId
	 * 用户ID。
	 * @return
	 * 所属机构ID。
	 */
	String loadAgencyIdByUser(String userId);
	/**
	 * 加载用户机构集合。
	 * @param userId
	 * 用户ID。
	 * @return
	 * 机构集合。
	 */
	List<AgencyInfo> loadAgenciesByUser(String userId);
}