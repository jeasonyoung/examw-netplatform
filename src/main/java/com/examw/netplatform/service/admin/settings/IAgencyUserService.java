package com.examw.netplatform.service.admin.settings;

import com.examw.netplatform.model.admin.settings.AgencyUserInfo;
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
}