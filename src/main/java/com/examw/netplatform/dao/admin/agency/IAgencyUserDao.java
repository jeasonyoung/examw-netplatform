package com.examw.netplatform.dao.admin.agency;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.agency.Agency;
import com.examw.netplatform.domain.admin.agency.AgencyUser;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.model.admin.agency.AgencyUserInfo;
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
	 * 加载机构集合。
	 * @param userId
	 * 用户ID。
	 * @return
	 */
	List<Agency> loadAgencies(String userId);
	/**
	 * 加载机构下用户集合。
	 * @param agencyId
	 * 所属机构ID。
	 * @param identities
	 * 身份集合。
	 * @return
	 * 用户集合。
	 */
	List<User> loadUsers(String agencyId, Integer[] identities);
}