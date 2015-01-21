package com.examw.netplatform.service.front.user.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.settings.ICategoryDao;
import com.examw.netplatform.domain.admin.settings.Category;
import com.examw.netplatform.domain.admin.settings.Exam;
import com.examw.netplatform.model.front.FrontCategoryInfo;
import com.examw.netplatform.model.front.FrontExamInfo;
import com.examw.netplatform.service.admin.settings.IExamService;
import com.examw.netplatform.service.front.user.IFrontCategoryService;
import com.examw.service.Status;

/**
 * 前端考试类别服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年9月25日
 */
public class FrontCategoryServiceImpl implements IFrontCategoryService {
	private static Logger logger = Logger.getLogger(FrontCategoryServiceImpl.class);
	private ICategoryDao categoryDao;
	private IExamService examService;
	/**
	 * 设置考试类别数据接口。
	 * @param categoryDao 
	 *	  考试类别数据接口。
	 */
	public void setCategoryDao(ICategoryDao categoryDao) {
		if(logger.isDebugEnabled()) logger.debug("注入考试类别数据接口...");
		this.categoryDao = categoryDao;
	}
	/**
	 * 设置考试服务接口。
	 * @param examService 
	 *	  考试服务接口。
	 */
	public void setExamService(IExamService examService) {
		if(logger.isDebugEnabled()) logger.debug("注入考试服务接口...");
		this.examService = examService;
	}
	/*
	 * 加载全部考试类别。
	 * @see com.examw.test.service.settings.IFrontCategoryService#loadCategories()
	 */
	@Override
	public List<FrontCategoryInfo> loadCategories() {
		if(logger.isDebugEnabled()) logger.debug("加载考试类别集合...");
		return this.changeModel(this.categoryDao.loadTopCategories());
	}
	/*
	 * 加载考试类别信息。
	 * @see com.examw.test.service.settings.IFrontCategoryService#loadCategory(java.lang.String)
	 */
	@Override
	public FrontCategoryInfo loadCategory(String categoryId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试类别［categoryId ＝%s］的信息...", categoryId));
		if(StringUtils.isEmpty(categoryId)) return null;
		Category category = this.categoryDao.load(Category.class, categoryId);
		if(category == null) throw new RuntimeException(String.format("考试类别［categoryId = %s］不存在!", categoryId));
		return this.changeModel(category);
	}
	/**
	 * 数据模型转换。
	 * @param catalogs
	 * @return
	 */
	protected List<FrontCategoryInfo> changeModel(List<Category> catalogs){
		List<FrontCategoryInfo> targets = new ArrayList<>();
		if(catalogs != null && catalogs.size() > 0){
			for(Category category : catalogs){
				if(category == null) continue;
				FrontCategoryInfo info = this.changeModel(category);
				if(info != null) targets.add(info);
			}
		}
		return targets;
	}
	/**
	 * 数据模型转换。
	 * @param category
	 * @return
	 */
	protected FrontCategoryInfo changeModel(Category category){
		if(logger.isDebugEnabled()) logger.debug("数据模型转换: Category => FrontCategoryInfo ...");
		if(category == null) return null;
		FrontCategoryInfo info = new FrontCategoryInfo();
		BeanUtils.copyProperties(category, info, new String[]{"exams","children"});
		if(category.getExams() != null && category.getExams().size() > 0){
			List<FrontExamInfo> exams = new ArrayList<>();
			for(Exam exam : category.getExams()){
				if(exam == null || exam.getStatus() !=  Status.ENABLED.getValue()) continue;
				FrontExamInfo examInfo = new FrontExamInfo(this.examService.conversion(exam));
				if(examInfo != null) exams.add(examInfo);
			}
			if(exams.size() > 0){
				Collections.sort(exams);
				info.setExams(exams);
			}
		}
		if(category.getChildren() != null && category.getChildren().size() > 0){
			Set<FrontCategoryInfo> children = new TreeSet<FrontCategoryInfo>();
			for(Category data : category.getChildren()){
				if(data == null) continue;
				FrontCategoryInfo frontCategoryInfo = this.changeModel(data);
				if(frontCategoryInfo != null){
					frontCategoryInfo.setPid(info.getId());
					children.add(frontCategoryInfo);
				}
			}
			if(children.size() > 0){
				info.setChildren(children);
			}
		}
		return info;
	}
}