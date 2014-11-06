package com.examw.netplatform.service.admin.settings;

import java.util.List;

import com.examw.model.TreeNode;
import com.examw.netplatform.model.admin.settings.CategoryInfo;
import com.examw.netplatform.service.IBaseDataService;
/**
 * 考试类别服务接口
 * @author fengwei.
 * @since 2014年8月6日 下午3:35:16.
 */
public interface ICategoryService extends IBaseDataService<CategoryInfo>{
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
	List<TreeNode> loadAllCategorys(String ignoreCategoryId);
	/**
	 * 加载全部考试类别/考试树。
	 * @return
	 * 树结构数据。
	 */
	List<TreeNode> loadAllCategoryExams();
	/**
	 * 加载全部考试类别/考试/科目树。
	 * @return
	 * 树结构数据。
	 */
	List<TreeNode> loadAllCategoryExamSubjects();
}