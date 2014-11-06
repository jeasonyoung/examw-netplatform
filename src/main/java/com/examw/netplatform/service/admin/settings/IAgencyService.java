package com.examw.netplatform.service.admin.settings;

import com.examw.netplatform.model.admin.settings.AgencyInfo;
import com.examw.netplatform.service.IBaseDataService;
/**
 * 培训机构服务接口。
 * @author yangyong。
 * @since 2014-04-28.
 */
public interface IAgencyService extends IBaseDataService<AgencyInfo> {
	/**
	 * 加载状态值名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
}