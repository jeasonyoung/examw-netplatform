package com.examw.netplatform.dao.admin.settings;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.model.admin.settings.AgencyUserInfo;
/**
 * 机构用户数据访问接口。
 * @author  yangyong.
 * @since 2014-07-08.
 */
public interface IAgencyUserDao extends IBaseDao<AgencyUser> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 */
	List<AgencyUser> findAgencieUsers(AgencyUserInfo info);
	/**
	 * 统计查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 */
	Long total(AgencyUserInfo info);
	/**
	 * 加载培训机构数据。
	 * @param agencyId
	 * 培训机构ID。
	 * @param userId
	 * 用户ID。
	 * @return
	 */
	AgencyUser loadAgencyUser(String agencyId,String userId);
}