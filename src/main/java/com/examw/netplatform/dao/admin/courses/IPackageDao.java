package com.examw.netplatform.dao.admin.courses;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.courses.Package;
import com.examw.netplatform.model.admin.courses.PackageInfo;
/**
 * 套餐数据接口
 * @author fengwei.
 * @since 2014年5月21日 下午3:03:08.
 */
public interface IPackageDao extends IBaseDao<Package> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Package> findPackages(PackageInfo info);
	/**
	 * 查询数据。
	 * @param agencyId
	 * 所属机构ID。
	 * @param catalogId
	 * 所属考试类别ID。
	 * @param examId
	 * 所属考试ID。
	 * @param packageName
	 * 套餐名称。
	 * @return
	 * 结果数据。
	 */
	List<Package> findPackages(String agencyId,String catalogId,String examId,String packageName);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(PackageInfo info);
}