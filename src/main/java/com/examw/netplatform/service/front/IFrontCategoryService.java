package com.examw.netplatform.service.front;

import java.util.List;

import com.examw.netplatform.model.front.FrontCategoryInfo;

/**
 * 前端类别服务接口。
 * 
 * @author yangyong
 * @since 2014年9月25日
 */
public interface IFrontCategoryService {
	/**
	 * 加载机构下所有考试类别。
	 * @return
	 */
	List<FrontCategoryInfo> loadCategories(String agencyId);
	/**
	 * 加载考试类别。
	 * @param categoryId
	 * @return
	 */
	FrontCategoryInfo loadCategory(String categoryId);
	/**
	 * 获取考试或考试分类
	 * @param agencyId		机构ID
	 * @param categroyId	课程分类ID
	 * @param examId		考试ID
	 * @param isLoadExam	是否是加载考试
	 * @return
	 */
	Object loadCategory(String agencyId, String categroyId, String examId,boolean isLoadExam);
}