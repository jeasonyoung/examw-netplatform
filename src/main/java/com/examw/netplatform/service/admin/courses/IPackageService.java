package com.examw.netplatform.service.admin.courses;
import java.util.List;

import com.examw.model.DataGrid;
import com.examw.netplatform.domain.admin.courses.Package;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.admin.courses.PackageInfo;
/**
 * 套餐服务接口
 * @author fengwei.
 * @since 2014年5月21日 下午3:13:14.
 */
public interface IPackageService {
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
	 * 查询数据。
	 * @param info
	 * @return
	 */
	DataGrid<PackageInfo> datagrid(PackageInfo info);
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
	
	/**
	 * 查询套餐下的班级集合
	 * @param packageId 套餐ID
	 * 2015.01.27
	 * @return
	 */
	List<ClassPlanInfo> loadClasses(String packageId);
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	PackageInfo update(PackageInfo info);
	/**
	 * 保存包含班级
	 * @param packageId	套餐ID
	 * @param classId	班级ID数组
	 */
	void saveClasses(String packageId, String[] classId);
	/**
	 * 删除包含班级
	 * @param packageId	套餐ID
	 * @param classId	班级ID数组
	 */
	void deleteClasses(String packageId, String[] classId);
	/**
	 * 删除数据。
	 * @param ids
	 */
	void delete(String[] ids);
}