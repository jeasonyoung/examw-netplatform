package com.examw.netplatform.service.admin.courses;
import java.util.List;

import com.examw.model.DataGrid;
import com.examw.netplatform.domain.admin.courses.CategoryHasExamView;
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
	 * 加载培训机构考试下最大排序号。
	 * @param agencyId
	 * 培训机构ID。
	 * @param examId
	 * 考试ID。
	 * @return
	 */
	Integer loadMaxOrder(String agencyId, String examId);
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
	 * @param examId
	 * 考试ID。
	 * @return
	 * 套餐集合。
	 */
	List<PackageInfo> loadPackages(String agencyId, String examId);
	/**
	 * 加载点单下套餐集合。
	 * @param orderId
	 * @return
	 */
	List<PackageInfo> loadPackagesByOrder(String orderId);
	/**
	 * 加载考试分类集合。
	 * @return
	 */
	List<CategoryHasExamView> loadCategoryHasExamViews();
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	PackageInfo update(PackageInfo info);
	/**
	 * 删除数据。
	 * @param ids
	 */
	void delete(String[] ids);
}