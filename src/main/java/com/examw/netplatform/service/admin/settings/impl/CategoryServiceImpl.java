package com.examw.netplatform.service.admin.settings.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.model.TreeNode;
import com.examw.netplatform.dao.admin.settings.CategoryMapper;
import com.examw.netplatform.domain.admin.settings.Category;
import com.examw.netplatform.model.admin.settings.CategoryInfo;
import com.examw.netplatform.service.admin.settings.ICategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 考试类别服务接口实现类
 * @author fengwei.
 * @since 2014年8月6日 下午3:36:37.
 */
public class CategoryServiceImpl implements ICategoryService {
	private static final Logger logger = Logger.getLogger(CategoryServiceImpl.class);
	private CategoryMapper categoryDao;
	/**
	 * 设置考试分类数据接口。
	 * @param categoryDao
	 * 考试分类数据接口。
	 */
	public void setCategoryDao(CategoryMapper categoryDao) {
		logger.debug("注入考试分类数据接口...");
		this.categoryDao = categoryDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.settings.ICategoryService#datagrid(com.examw.netplatform.model.admin.settings.CategoryInfo)
	 */
	@Override
	public DataGrid<CategoryInfo> datagrid(CategoryInfo info) {
		logger.debug("查询数据...");
		//分页
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getSort()) + " " + StringUtils.trimToEmpty(info.getOrder()));
		//查询数据
		final List<Category> list = this.categoryDao.findCategorys(info);
		//获取分页信息
		final PageInfo<Category> pageInfo = new PageInfo<Category>(list);
		//初始化
		final DataGrid<CategoryInfo> grid = new DataGrid<CategoryInfo>();
		//设置数据
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	/*
	 * 加载最大的代码值
	 * @see com.examw.test.service.settings.ICategoryService#loadMaxCode(java.lang.String)
	 */
	@Override
	public Integer loadMaxCode(String parentCatalogId) {
		logger.debug("加载最大代码值...");
		return this.categoryDao.loadMaxCode(parentCatalogId);
	}
	//批量类型转换
	private List<CategoryInfo> changeModel(List<Category> categories){
		final List<CategoryInfo> list = new ArrayList<CategoryInfo>();
		if(categories != null && categories.size() > 0){
			for(Category category : categories){
				if(category == null) continue;
				list.add(this.convertion(category));
			}
		}
		return list;
	}
	//数据类型转换
	private CategoryInfo convertion(Category data){
		if(data != null){
			final CategoryInfo info = new CategoryInfo();
			BeanUtils.copyProperties(data, info);
			return info;
		}
		return null;
	}
	/*
	 * 加载全部的考试分类。
	 * @see com.examw.netplatform.service.admin.settings.ICategoryService#loadAllCategorys(java.lang.String)
	 */
	@Override
	public List<CategoryInfo> loadAllCategorys(String ignoreCategoryId) {
		logger.debug("加载全部的考试分类(忽略["+ignoreCategoryId+"]及其子节点)...");
		List<Category> list = null;
		if(StringUtils.isBlank(ignoreCategoryId)){
			//排序
			PageHelper.orderBy("code");
			//查询数据
			list = this.categoryDao.findCategorys(null);
		}else {
			list = this.categoryDao.findCategorysByIgnore(ignoreCategoryId);
		}
		return this.changeModel(list);
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.netplatform.service.admin.settings.ICategoryService#loadAllCategoryExams()
	 */
	@Override
	public List<TreeNode> loadAllCategoryExams() {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.netplatform.service.admin.settings.ICategoryService#loadAllCategoryExamSubjects()
	 */
	@Override
	public List<TreeNode> loadAllCategoryExamSubjects() {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.admin.settings.ICategoryService#update(com.examw.netplatform.model.admin.settings.CategoryInfo)
	 */
	@Override
	public CategoryInfo update(CategoryInfo info) {
		logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Category data = StringUtils.isBlank(info.getId()) ? null : this.categoryDao.getCategory(info.getId());
		if(isAdded = (data == null)){
			//判断唯一性
			if(this.categoryDao.hasCategoryCode(info.getCode())){
				throw new RuntimeException("考试分类代码已存在!" + info.getCode());
			} 
			if(this.categoryDao.hasCategoryName(info.getName())){
				throw new RuntimeException("考试分类名称已存在!" + info.getName());
			}
			if(this.categoryDao.hasCategoryAbbr(info.getAbbr())){
				throw new RuntimeException("考试分类EN简称已存在!" + info.getAbbr());
			}
			if(StringUtils.isBlank(info.getId()))
				info.setId(UUID.randomUUID().toString());
			data = new Category();
		}
		//赋值
		BeanUtils.copyProperties(info, data);
		//判断
		if(!StringUtils.isBlank(data.getPid())){
		    final	 Category parent = this.categoryDao.getCategory(data.getPid());
		    if(parent == null) throw new RuntimeException("上级分类ID["+data.getPid()+"]不存在！");
		    if(StringUtils.equalsIgnoreCase(parent.getId(), data.getId())){
		    	throw new RuntimeException("自己不能是自己的上级节点！");
		    }
		}else {
			data.setPid(null);
		}
		//保存数据
		if(isAdded){
			logger.debug("新增考试分类...");
			this.categoryDao.insertCategory(data);
		}else {
			logger.debug("更新考试分类...");
			this.categoryDao.updateCategory(data);
		}
		//返回
		return this.convertion(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.admin.settings.ICategoryService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除数据...." + StringUtils.join(ids,","));
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id)) continue;
				this.categoryDao.deleteCategory(id);
			}
		}
	}
}