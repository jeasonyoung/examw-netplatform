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
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getOrder()) + " " + StringUtils.trimToEmpty(info.getSort()));
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
	//类型转换
	private List<CategoryInfo> changeModel(List<Category> categories){
		final List<CategoryInfo> list = new ArrayList<CategoryInfo>();
		if(categories != null && categories.size() > 0){
			for(Category category : categories){
				if(category == null) continue;
				list.add((CategoryInfo)category);
			}
		}
		return list;
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.netplatform.service.admin.settings.ICategoryService#loadAllCategorys(java.lang.String)
	 */
	@Override
	public List<TreeNode> loadAllCategorys(String ignoreCategoryId) {
		// TODO Auto-generated method stub
		return null;
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
		return (CategoryInfo)data;
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
	
 
  
//	/*
//	 * 更新数据。
//	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
//	 */
//	@Override
//	public CategoryInfo update(CategoryInfo info) {
//		if(logger.isDebugEnabled()) logger.debug("更新数据...");
//		if(info == null) return null;
//		boolean isAdded = false;
//		Category data = StringUtils.isEmpty(info.getId()) ? null :this.categoryDao.load(Category.class, info.getId());
//		if(isAdded = (data == null)){
//			if(StringUtils.isEmpty(info.getId())){
//				info.setId(UUID.randomUUID().toString());
//			}
//			data = new Category();
//		}
//		
//		
//		if(StringUtils.isEmpty(info.getPid())){
//			data.setParent(null);
//		}
//		if(isAdded) this.categoryDao.save(data);
//		return this.changeModel(data);
//	}
//	/*
//	 * 删除数据
//	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
//	 */
//	@Override
//	public void delete(String[] ids) {
//		if(logger.isDebugEnabled()) logger.debug("删除数据...");
//		if(ids == null || ids.length == 0) return;
//		for(int i = 0; i < ids.length; i++){
//			Category data = this.categoryDao.load(Category.class, ids[i]);
//			if(data != null){
//				if(logger.isDebugEnabled()) logger.debug(String.format("［%1$d］删除数据［%2$s］...", i+1, data.getId()));
//				this.categoryDao.delete(data);
//			}
//		}
//	}

//	/*
//	 * 加载全部考试类别。
//	 * @see com.examw.test.service.settings.ICategoryService#loadAllCategorys()
//	 */
//	@Override
//	public List<TreeNode> loadAllCategorys(String ignoreCategoryId) {
//		if(logger.isDebugEnabled()) logger.debug(String.format("加载全部考试类别［ignore = %s］", ignoreCategoryId));
//		List<TreeNode> result = new ArrayList<>();
//		List<Category> topCategories = this.categoryDao.loadTopCategories();
//		if(topCategories != null && topCategories.size() > 0){
//			for(Category data : topCategories){
//				if(data == null) continue;
//				TreeNode e = this.createTreeNode(data,ignoreCategoryId,false,false);
//				if(e != null)result.add(e);
//			}
//		}
//		return result;
//	}
//	//创建考试类别树。
//	private TreeNode createTreeNode(Category data,String ignoreCategoryId,boolean withExam,boolean withSubject){
//		if(data == null || (!StringUtils.isEmpty(ignoreCategoryId) && data.getId().equalsIgnoreCase(ignoreCategoryId))) return null;
//		TreeNode node = new TreeNode();
//		node.setId(data.getId());
//		node.setText(data.getName());
//		Map<String, Object> attributes = new HashMap<>();
//		attributes.put("type", "category");
//		node.setAttributes(attributes);
//		//考试
//		if(withExam && data.getExams() != null && data.getExams().size() > 0){
//			List<TreeNode> examTreeNodes = new ArrayList<>();
//			for(final Exam exam : data.getExams()){
//				if(exam == null) continue;
//				TreeNode tn_exam = new TreeNode();
//				tn_exam.setId(exam.getId());
//				tn_exam.setText(exam.getName());
//				Map<String, Object> exam_attributes = new HashMap<>();
//				exam_attributes.put("type", "exam");
//				tn_exam.setAttributes(exam_attributes);
//				//科目
//				if(withSubject && exam.getSubjects() != null && exam.getSubjects().size() > 0){
//					List<TreeNode> subjectNodes = new ArrayList<>();
//					for(final Subject subject : exam.getSubjects()){
//						if(subject == null) continue;
//						TreeNode tn_subject = new TreeNode();
//						tn_subject.setId(subject.getId());
//						tn_subject.setText(subject.getName());
//						Map<String, Object> subject_attributes = new HashMap<>();
//						subject_attributes.put("type", "subject");
//						tn_subject.setAttributes(subject_attributes);
//						subjectNodes.add(tn_subject);
//					}
//					if(subjectNodes.size() > 0){
//						tn_exam.setChildren(subjectNodes);
//					}
//				}
//				examTreeNodes.add(tn_exam);
//			}
//			if(examTreeNodes.size() > 0){
//				if(node.getChildren()==null)
//					node.setChildren(examTreeNodes);
//				else{
//					node.getChildren().addAll(examTreeNodes);
//				}
//			}
//		}
//		if(data.getChildren() != null && data.getChildren().size() > 0){
//			List<TreeNode> children = new ArrayList<>();
//			for(Category child : data.getChildren()){
//				if(child == null) continue;
//				TreeNode e = this.createTreeNode(child, ignoreCategoryId, withExam, withSubject);
//				if(e != null) children.add(e);
//			}
//			if(children.size() > 0) {
//				if(node.getChildren()==null)
//					node.setChildren(children);
//				else
//					node.getChildren().addAll(children);
//			}
//		}
//		return node;
//	}
//	/*
//	 * 加载全部考试类别/考试树。
//	 * @see com.examw.test.service.settings.ICategoryService#loadAllCategoryExams()
//	 */
//	@Override
//	public List<TreeNode> loadAllCategoryExams() {
//		if(logger.isDebugEnabled()) logger.debug("加载全部考试类别/考试树...");
//		List<TreeNode> treeNodes = new ArrayList<>();
//		List<Category> list =  this.categoryDao.loadTopCategories();
//		if(list != null && list.size() > 0){
//			for(final Category data : list){
//				if(data == null) continue;
//				TreeNode e = this.createTreeNode(data,null,true,false);
//				if(e != null)treeNodes.add(e);
//			}
//		}
//		return treeNodes;
//	}
//	/*
//	 * 加载全部考试类别/考试/科目树。
//	 * @see com.examw.test.service.settings.ICategoryService#loadAllCategoryExamSubjects()
//	 */
//	@Override
//	public List<TreeNode> loadAllCategoryExamSubjects() {
//		if(logger.isDebugEnabled()) logger.debug("加载全部考试类别/考试/科目树...");
//		List<TreeNode> treeNodes = new ArrayList<>();
//		List<Category> list = this.categoryDao.loadTopCategories();
//		if(list != null && list.size() > 0){
//			for(final Category data : list){
//				if(data == null) continue;
//				TreeNode e = this.createTreeNode(data,null,true,true);
//				if(e != null)treeNodes.add(e);
//			}
//		}
//		return treeNodes;
//	}
}