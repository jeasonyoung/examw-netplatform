package com.examw.netplatform.dao.admin.courses;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examw.netplatform.domain.admin.courses.Package;
/**
 * 套餐数据接口
 * @author fengwei.
 * @since 2014年5月21日 下午3:03:08.
 */
public interface PackageMapper {
	/**
	 * 获取套餐。
	 * @param id
	 * @return
	 */
	Package getPackage(String id);
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Package> findPackages(Package info);
	/**
	 * 查询机构考试套餐。
	 * @param agencyId
	 * @param examId
	 * @return
	 */
	List<Package> findPackagesByAgency(@Param("agencyId")String agencyId,@Param("examId")String examId);
	/**
	 * 加载机构下最大排序号。
	 * @param agencyId
	 * 机构ID。
	 * @return
	 * 最大排序号。
	 */
	Integer loadMaxOrder(String agencyId);
	/**
	 * 新增套餐。
	 * @param data
	 */
	void insertPackage(Package data);
	/**
	 * 更新套餐。
	 * @param data
	 */
	void updatePackage(Package data);
	/**
	 * 删除套餐。
	 * @param id
	 */
	void deletePackage(String id);
}