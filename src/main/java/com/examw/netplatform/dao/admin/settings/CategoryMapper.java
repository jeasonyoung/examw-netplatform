package com.examw.netplatform.dao.admin.settings;

import java.util.List;

import com.examw.netplatform.domain.admin.settings.Category;
/**
 * 考试分类数据接口
 * @author fengwei.
 * @since 2014年8月6日 下午12:00:50.
 */
public interface CategoryMapper {
	/**
	 * 获取考试分类。
	 * @param id
	 * @return
	 */
	Category getCategory(String id);
	/**
	 * 查询考试分类数据
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Category> findCategorys(Category info);
	/**
	 * 查询考试分类。
	 * @param ignoreId
	 * 将忽略的及其子孙。
	 * @return
	 */
	List<Category> findCategorysByIgnore(String ignoreId);
	/**
	 * 加载有考试的考试分类集合。
	 * @return
	 */
	List<Category> findCategorysHasExams();
	/**
	 * 加载最大的类别代码。
	 * @param pid
	 * @return
	 */
	Integer loadMaxCode(String pid);
	/**
	 * 考试分类代码是否存在。
	 * @param code
	 * @return
	 */
	boolean hasCategoryCode(int code);
	/**
	 * 考试分类名称是否存在。
	 * @param name
	 * @return
	 */
	boolean hasCategoryName(String name);
	/**
	 * 考试分类简称是否存在。
	 * @param abbr
	 * @return
	 */
	boolean hasCategoryAbbr(String abbr);
	/**
	 * 是否有子分类。
	 * @param id
	 * @return
	 */
	boolean hasChildCategorys(String id);
	/**
	 * 插入考试分类。
	 * @param data
	 */
	void insertCategory(Category data);
	/**
	 * 更新考试分类。
	 * @param data
	 */
	void updateCategory(Category data);
	/**
	 * 删除考试分类。
	 * @param id
	 */
	void deleteCategory(String id);
}