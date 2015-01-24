package com.examw.netplatform.service.admin.courses;
import java.util.List;

import com.examw.netplatform.domain.admin.courses.Package;
import com.examw.netplatform.model.admin.courses.PackageInfo;
import com.examw.netplatform.service.IBaseDataService;
/**
 * 套餐服务接口
 * @author fengwei.
 * @since 2014年5月21日 下午3:13:14.
 */
public interface IPackageService extends IBaseDataService<PackageInfo>{
	/**
	 * 加载状态值名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载培训机构下最大排序号。
	 * @param agencyId
	 * 培训机构ID。
	 * @return
	 */
	Integer loadMaxOrder(String agencyId);
	/**
	 * 加载机构套餐集合。
	 * @param agencyId
	 * 机构ID。
	 * @return
	 * 套餐集合。
	 */
	List<PackageInfo> loadPackages(String agencyId);
	/**
	 * 数据模型转换
	 * @param data 套餐数据
	 * @return
	 * 套餐信息
	 * 2015.01.23
	 */
	PackageInfo conversion(Package data);
}