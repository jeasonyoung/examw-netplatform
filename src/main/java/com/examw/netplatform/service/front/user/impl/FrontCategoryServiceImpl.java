package com.examw.netplatform.service.front.user.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.courses.IClassPlanDao;
import com.examw.netplatform.dao.admin.courses.IPackageDao;
import com.examw.netplatform.dao.admin.settings.ICategoryDao;
import com.examw.netplatform.domain.admin.settings.Category;
import com.examw.netplatform.domain.admin.settings.Exam;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.admin.courses.PackageInfo;
import com.examw.netplatform.model.admin.settings.CategoryInfo;
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
	private IPackageDao packageDao;
	private IClassPlanDao classPlanDao;

	/**
	 * 设置考试类别数据接口。
	 * 
	 * @param categoryDao
	 *            考试类别数据接口。
	 */
	public void setCategoryDao(ICategoryDao categoryDao) {
		if (logger.isDebugEnabled())
			logger.debug("注入考试类别数据接口...");
		this.categoryDao = categoryDao;
	}

	/**
	 * 设置考试服务接口。
	 * 
	 * @param examService
	 *            考试服务接口。
	 */
	public void setExamService(IExamService examService) {
		if (logger.isDebugEnabled())
			logger.debug("注入考试服务接口...");
		this.examService = examService;
	}

	/**
	 * 设置 套餐数据接口
	 * 
	 * @param packageDao
	 * 
	 */
	public void setPackageDao(IPackageDao packageDao) {
		this.packageDao = packageDao;
	}

	/**
	 * 设置 班级数据接口
	 * 
	 * @param classPlanDao
	 * 
	 */
	public void setClassPlanDao(IClassPlanDao classPlanDao) {
		this.classPlanDao = classPlanDao;
	}

	/*
	 * 加载全部考试类别。
	 * 
	 * @see
	 * com.examw.test.service.settings.IFrontCategoryService#loadCategories()
	 */
	@Override
	public List<FrontCategoryInfo> loadCategories(String agencyId) {
		if (logger.isDebugEnabled())
			logger.debug("加载考试类别集合...");
		return this.changeModel(this.categoryDao.loadTopCategories(), agencyId);
	}

	/*
	 * 加载考试类别信息。
	 * 
	 * @see
	 * com.examw.test.service.settings.IFrontCategoryService#loadCategory(java
	 * .lang.String)
	 */
	@Override
	public FrontCategoryInfo loadCategory(String categoryId) {
		if (logger.isDebugEnabled())
			logger.debug(String.format("加载考试类别［categoryId ＝%s］的信息...", categoryId));
		if (StringUtils.isEmpty(categoryId))
			return null;
		Category category = this.categoryDao.load(Category.class, categoryId);
		if (category == null)
			throw new RuntimeException(String.format("考试类别［categoryId = %s］不存在!", categoryId));
		return this.changeModel(category, null);
	}

	/**
	 * 数据模型转换。
	 * 
	 * @param catalogs
	 * @param agencyId 机构Id,若机构ID为Null,则不计算课程数
	 * @return
	 */
	protected List<FrontCategoryInfo> changeModel(List<Category> catalogs, String agencyId) {
		List<FrontCategoryInfo> targets = new ArrayList<>();
		if (catalogs != null && catalogs.size() > 0) {
			for (Category category : catalogs) {
				if (category == null)
					continue;
				FrontCategoryInfo info = this.changeModel(category, agencyId);
				if (info != null)
					targets.add(info);
			}
		}
		return targets;
	}

	/**
	 * 数据模型转换。
	 * 
	 * @param category
	 * @return
	 */
	protected FrontCategoryInfo changeModel(Category category, final String agencyId) {
		if (logger.isDebugEnabled())
			logger.debug("数据模型转换: Category => FrontCategoryInfo ...");
		if (category == null)
			return null;
		FrontCategoryInfo info = new FrontCategoryInfo();
		BeanUtils.copyProperties(category, info, new String[] {"parent", "exams", "children" });
		//设置父类
		if(category.getParent()!=null)
		{
			CategoryInfo p = new CategoryInfo();
			BeanUtils.copyProperties(category, p);
			info.setParent(p);
		}
		if (category.getExams() != null && category.getExams().size() > 0) {
			List<FrontExamInfo> exams = new ArrayList<>();
			Long sum = 0L;
			for (Exam exam : category.getExams()) {
				if (exam == null || exam.getStatus() != Status.ENABLED.getValue())
					continue;
				FrontExamInfo examInfo = new FrontExamInfo(this.examService.conversion(exam));
				if (examInfo != null) {
					// 计算课程数,套餐数
					if (agencyId != null) {
						final String examId = exam.getId();
						Long packageTotal = this.packageDao.total(new PackageInfo() {
							private static final long serialVersionUID = 1L;
							@Override
							public String getAgencyId() {return agencyId;}
							@Override
							public String getExamId() {	return examId;}
							@Override
							public Integer getStatus() {return Status.ENABLED.getValue();}
						});
						packageTotal = packageTotal == null ? 0L : packageTotal;
						Long classPlanTotal = this.classPlanDao.total(new ClassPlanInfo() {
							private static final long serialVersionUID = 1L;
							@Override
							public String getAgencyId() {return agencyId;}
							@Override
							public String getExamId() {	return examId;}
							@Override
							public Integer getStatus() {return Status.ENABLED.getValue();}
						});
						classPlanTotal = classPlanTotal == null ? 0L : classPlanTotal;
						examInfo.setCourseTotal(packageTotal + classPlanTotal);
						sum += examInfo.getCourseTotal();
					}
					exams.add(examInfo);

				}
			}
			if (exams.size() > 0) {
				Collections.sort(exams);
				info.setExams(exams);
			}
			if (info.getCourseTotal() == null)
				info.setCourseTotal(sum);
			else
				info.setCourseTotal(sum + info.getCourseTotal());
		}
		if (category.getChildren() != null && category.getChildren().size() > 0) {
			Set<FrontCategoryInfo> children = new TreeSet<FrontCategoryInfo>();
			Long sum = 0L;
			for (Category data : category.getChildren()) {
				if (data == null)
					continue;
				FrontCategoryInfo frontCategoryInfo = this.changeModel(data, agencyId);
				if (frontCategoryInfo != null) {
					frontCategoryInfo.setPid(info.getId());
					children.add(frontCategoryInfo);
					if(agencyId!=null) sum += frontCategoryInfo.getCourseTotal();
				}
			}
			if (children.size() > 0) {
				info.setChildren(children);
			}
			// 计算总数
			if (info.getCourseTotal() == null)
				info.setCourseTotal(sum);
			else
				info.setCourseTotal(sum + info.getCourseTotal());
		}
		return info;
	}
	
	@Override
	public Object loadCategory(String agencyId, String categroyId, String examId,boolean isLoadExam) {
		if(StringUtils.isEmpty(agencyId)) return null;
		if(StringUtils.isEmpty(categroyId)&&StringUtils.isEmpty(examId))
		return null;
		List<FrontCategoryInfo> list = this.loadCategories(agencyId);
		if(list!=null)
		{
			if(isLoadExam)
			{
				for(FrontCategoryInfo info:list)
				{
					FrontExamInfo examInfo = this.getExamInfo(info, examId);
					if(examInfo!=null)
					{
						return examInfo;
					}
				}
			}else
			{
				for(FrontCategoryInfo info:list)
				{
					if(info.getId().equals(categroyId))
					{
						return info;
					}
					FrontExamInfo examInfo = this.getExamInfo(info, examId);
					if(examInfo!=null)
					{
						FrontCategoryInfo child = this.getChildCategoryInfo(info,examInfo.getCategoryId());
						return child;
					}
					FrontCategoryInfo child = this.getChildCategoryInfo(info,categroyId);
					if(child!=null)
					{
						return child;
					}
				}
			}
		}
		return null;
	}
	private FrontCategoryInfo getChildCategoryInfo(FrontCategoryInfo info, String categroyId) {
		if(categroyId == null) return null;
		if(info.getId().equals(categroyId)) return info;
		if(info.getChildren()!=null)
		{
			for(FrontCategoryInfo cInfo:info.getChildren())
			{
				return this.getChildCategoryInfo(cInfo, categroyId);
			}
		}
		return null;
	}

	private FrontExamInfo getExamInfo(FrontCategoryInfo category,String examId)
	{
		if(examId == null) return null;
		FrontExamInfo result = null;
		if(category.getExams()!=null)
		{
			for(FrontExamInfo info:category.getExams())
			{
				if(info.getId().equals(examId))
				{
					result = info;
					break;
				}
			}
		}
		if(result == null)
		{
			if(category.getChildren()!=null)
			{
				for(FrontCategoryInfo cInfo:category.getChildren())
				{
					return this.getExamInfo(cInfo, examId);
				}
			}
		}
		return result;
	}
}