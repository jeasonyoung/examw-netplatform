package com.examw.netplatform.service.admin.courses;
import java.util.List;

import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.admin.courses.PackageInfo;
import com.examw.netplatform.service.IBaseDataService;
/**
 * 套餐服务接口
 * @author fengwei.
 * @since 2014年5月21日 下午3:13:14.
 */
public interface IPackageService extends IBaseDataService<PackageInfo>{
	/**
	 * 加载状态的名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载套餐下班级集合。
	 * @param packageId
	 * 套餐ID。
	 * @return
	 * 班级集合。
	 */
	List<ClassPlanInfo> loadClasses(String packageId);
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
	List<PackageInfo> findPackages(String agencyId,String catalogId,String examId,String packageName);
}