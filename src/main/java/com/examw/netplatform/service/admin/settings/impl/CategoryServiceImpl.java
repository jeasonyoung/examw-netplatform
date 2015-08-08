package com.examw.netplatform.service.admin.settings.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.model.TreeNode;
import com.examw.netplatform.dao.admin.settings.ICategoryDao;
import com.examw.netplatform.domain.admin.settings.Category;
import com.examw.netplatform.domain.admin.settings.Exam;
import com.examw.netplatform.domain.admin.settings.Subject;
import com.examw.netplatform.model.admin.settings.CategoryInfo;
import com.examw.netplatform.service.admin.settings.ICategoryService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
/**
 * 考试类别服务接口实现类
 * @author fengwei.
 * @since 2014年8月6日 下午3:36:37.
 */
public class CategoryServiceImpl extends BaseDataServiceImpl<Category, CategoryInfo> implements ICategoryService {
	private static final Logger logger = Logger.getLogger(CategoryServiceImpl.class);
	private ICategoryDao categoryDao;
	/**
	 * 设置考试分类数据接口。
	 * @param categoryDao
	 * 考试分类数据接口。
	 */
	public void setCategoryDao(ICategoryDao categoryDao) {
		if(logger.isDebugEnabled()) logger.debug("注入考试分类数据接口...");
		this.categoryDao = categoryDao;
	}
	/*
	 * 查询数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Category> find(CategoryInfo info) {
		if (logger.isDebugEnabled())logger.debug("查询数据...");
		return categoryDao.findCategorys(info);
	}
	/*
	 * 数据模型转换
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected CategoryInfo changeModel(Category data) {
		if (logger.isDebugEnabled())logger.debug("数据模型转换［Category => CategoryInfo ］...");
		if(data == null) return null;
		CategoryInfo info = new CategoryInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getParent() != null){
			info.setPid(data.getParent().getId());
		}
		info.setFullName(this.loadFullName(data));
		return info;
	}
	//加载类别全称。
	private String loadFullName(Category data){
		if(data == null) return null;
		if(data.getParent() == null) return data.getName();
		StringBuilder builder = new StringBuilder(data.getName());
		builder.insert(0, this.loadFullName(data.getParent()) + " >> ");
		return builder.toString();
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(CategoryInfo info) {
		if (logger.isDebugEnabled())logger.debug("查询数据统计...");
		return this.categoryDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public CategoryInfo update(CategoryInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Category data = StringUtils.isEmpty(info.getId()) ? null :this.categoryDao.load(Category.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Category();
		}
		BeanUtils.copyProperties(info, data);
		if(!StringUtils.isEmpty(info.getPid()) && (data.getParent() == null || !data.getParent().getId().equalsIgnoreCase(info.getPid()))){
			Category parent = this.categoryDao.load(Category.class, info.getPid());
			//自己不能是自己的父类
			if(parent != null && !parent.getId().equalsIgnoreCase(data.getId())){
				data.setParent(parent);
			}
		}
		if(StringUtils.isEmpty(info.getPid())){
			data.setParent(null);
		}
		if(isAdded) this.categoryDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			Category data = this.categoryDao.load(Category.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("［%1$d］删除数据［%2$s］...", i+1, data.getId()));
				this.categoryDao.delete(data);
			}
		}
	}
	/*
	 * 加载最大的代码值
	 * @see com.examw.test.service.settings.ICategoryService#loadMaxCode(java.lang.String)
	 */
	@Override
	public Integer loadMaxCode(String parentCatalogId) {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		return this.categoryDao.loadMaxCode(parentCatalogId);
	}
	/*
	 * 加载全部考试类别。
	 * @see com.examw.test.service.settings.ICategoryService#loadAllCategorys()
	 */
	@Override
	public List<TreeNode> loadAllCategorys(String ignoreCategoryId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载全部考试类别［ignore = %s］", ignoreCategoryId));
		List<TreeNode> result = new ArrayList<>();
		List<Category> topCategories = this.categoryDao.loadTopCategories();
		if(topCategories != null && topCategories.size() > 0){
			for(Category data : topCategories){
				if(data == null) continue;
				TreeNode e = this.createTreeNode(data,ignoreCategoryId,false,false);
				if(e != null)result.add(e);
			}
		}
		return result;
	}
	//创建考试类别树。
	private TreeNode createTreeNode(Category data,String ignoreCategoryId,boolean withExam,boolean withSubject){
		if(data == null || (!StringUtils.isEmpty(ignoreCategoryId) && data.getId().equalsIgnoreCase(ignoreCategoryId))) return null;
		TreeNode node = new TreeNode();
		node.setId(data.getId());
		node.setText(data.getName());
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("type", "category");
		node.setAttributes(attributes);
		//考试
		if(withExam && data.getExams() != null && data.getExams().size() > 0){
			List<TreeNode> examTreeNodes = new ArrayList<>();
			for(final Exam exam : data.getExams()){
				if(exam == null) continue;
				TreeNode tn_exam = new TreeNode();
				tn_exam.setId(exam.getId());
				tn_exam.setText(exam.getName());
				Map<String, Object> exam_attributes = new HashMap<>();
				exam_attributes.put("type", "exam");
				tn_exam.setAttributes(exam_attributes);
				//科目
				if(withSubject && exam.getSubjects() != null && exam.getSubjects().size() > 0){
					List<TreeNode> subjectNodes = new ArrayList<>();
					for(final Subject subject : exam.getSubjects()){
						if(subject == null) continue;
						TreeNode tn_subject = new TreeNode();
						tn_subject.setId(subject.getId());
						tn_subject.setText(subject.getName());
						Map<String, Object> subject_attributes = new HashMap<>();
						subject_attributes.put("type", "subject");
						tn_subject.setAttributes(subject_attributes);
						subjectNodes.add(tn_subject);
					}
					if(subjectNodes.size() > 0){
						tn_exam.setChildren(subjectNodes);
					}
				}
				examTreeNodes.add(tn_exam);
			}
			if(examTreeNodes.size() > 0){
				if(node.getChildren()==null)
					node.setChildren(examTreeNodes);
				else{
					node.getChildren().addAll(examTreeNodes);
				}
			}
		}
		if(data.getChildren() != null && data.getChildren().size() > 0){
			List<TreeNode> children = new ArrayList<>();
			for(Category child : data.getChildren()){
				if(child == null) continue;
				TreeNode e = this.createTreeNode(child, ignoreCategoryId, withExam, withSubject);
				if(e != null) children.add(e);
			}
			if(children.size() > 0) {
				if(node.getChildren()==null)
					node.setChildren(children);
				else
					node.getChildren().addAll(children);
			}
		}
		return node;
	}
	/*
	 * 加载全部考试类别/考试树。
	 * @see com.examw.test.service.settings.ICategoryService#loadAllCategoryExams()
	 */
	@Override
	public List<TreeNode> loadAllCategoryExams() {
		if(logger.isDebugEnabled()) logger.debug("加载全部考试类别/考试树...");
		List<TreeNode> treeNodes = new ArrayList<>();
		List<Category> list =  this.categoryDao.loadTopCategories();
		if(list != null && list.size() > 0){
			for(final Category data : list){
				if(data == null) continue;
				TreeNode e = this.createTreeNode(data,null,true,false);
				if(e != null)treeNodes.add(e);
			}
		}
		return treeNodes;
	}
	/*
	 * 加载全部考试类别/考试/科目树。
	 * @see com.examw.test.service.settings.ICategoryService#loadAllCategoryExamSubjects()
	 */
	@Override
	public List<TreeNode> loadAllCategoryExamSubjects() {
		if(logger.isDebugEnabled()) logger.debug("加载全部考试类别/考试/科目树...");
		List<TreeNode> treeNodes = new ArrayList<>();
		List<Category> list = this.categoryDao.loadTopCategories();
		if(list != null && list.size() > 0){
			for(final Category data : list){
				if(data == null) continue;
				TreeNode e = this.createTreeNode(data,null,true,true);
				if(e != null)treeNodes.add(e);
			}
		}
		return treeNodes;
	}
}