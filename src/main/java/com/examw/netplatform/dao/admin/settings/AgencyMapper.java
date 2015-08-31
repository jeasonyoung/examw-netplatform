package com.examw.netplatform.dao.admin.settings;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examw.netplatform.domain.admin.settings.Agency;

/**
 * 培训机构数据接口。
 * @author yangyong.
 * @since 2014-04-29.
 */
public interface AgencyMapper {
	/**
	 * 获取机构数据。
	 * @param id
	 * @return
	 */
	Agency getAgency(String id);
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Agency> findAgencies(Agency info);
	/**
	 * 查询用户所属机构集合。
	 * @param userId
	 * @return
	 */
	List<Agency> findAgenciesByUser(String userId);
	/**
	 * 根据英文简称查询机构
	 * @param abbrEN
	 * @return
	 */
	Agency loadAgencyByAbbrEN(String abbrEN);
	/**
	 * 是否存在EN简称。
	 * @param abbrEN
	 * @return
	 */
	boolean hasAgencyByAbbrEN(String abbrEN);
	/**
	 * 是否存在中文简称。
	 * @param abbrCN
	 * @return
	 */
	boolean hasAgencyByAbbrCN(String abbrCN);
	/**
	 * 是否存在机构用户。
	 * @param agencyId
	 * @param userId
	 * @return
	 */
	boolean hasAgencyUser(@Param("agencyId")String agencyId,@Param("userId")String userId);
	/**
	 * 新增机构。
	 * @param data
	 */
	void insertAgency(Agency data);
	/**
	 * 更新机构。
	 * @param data
	 */
	void updateAgency(Agency data);
	/**
	 * 删除机构。
	 * @param id
	 */
	void deleteAgency(String id);
}