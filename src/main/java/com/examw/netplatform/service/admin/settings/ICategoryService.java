package com.examw.netplatform.service.admin.settings;

import java.util.List;

import com.examw.model.DataGrid;
import com.examw.netplatform.model.admin.settings.CategoryInfo;
/**
 * 考试类别服务接口
 * @author fengwei.
 * @since 2014年8月6日 下午3:35:16.
 */
public interface ICategoryService {
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	DataGrid<CategoryInfo> datagrid(CategoryInfo info);
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	CategoryInfo update(CategoryInfo info);
	/**
	 * 加载最大代码值
	 * @return
	 */
	Integer loadMaxCode(String parentCatalogId);
	/**
	 * 加载全部考试类别树。
	 * @return
	 * 树结构数据。
	 */
	List<CategoryInfo> loadAllCategories(String ignoreCategoryId);
	/**
	 * 加载有考试的全部考试分类集合。
	 * @return
	 * 树结构数据。
	 */
	List<CategoryInfo> loadCategoriesHasExam();
	/**
	 * 删除数据。
	 * @param ids
	 */
	void delete(String[] ids);
}