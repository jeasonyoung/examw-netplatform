package com.examw.netplatform.dao.admin.settings;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.settings.Category;
import com.examw.netplatform.model.admin.settings.CategoryInfo;
/**
 * 考试分类数据接口
 * @author fengwei.
 * @since 2014年8月6日 下午12:00:50.
 */
public interface ICategoryDao extends IBaseDao<Category>{
	/**
	 * 查询考试分类数据
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Category> findCategorys(CategoryInfo info);
	/**
	 * 加载全部一级类别。
	 * @return
	 */
	List<Category> loadTopCategories();
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(CategoryInfo info);
	/**
	 * 加载最大的类别代码。
	 * @param parentCatalogId
	 * @return
	 */
	Integer loadMaxCode(String parentCatalogId);
}