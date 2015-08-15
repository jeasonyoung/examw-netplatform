package com.examw.netplatform.dao.admin.courses;

import java.util.List;

import com.examw.netplatform.domain.admin.courses.ClassPlan;

/**
 * 开班计划数据接口
 * @author fengwei.
 * @since 2014年5月20日 下午5:14:10.
 */
public interface ClassMapper{
	/**
	 * 获取班级。
	 * @param id
	 * @return
	 */
	ClassPlan getClassPlan(String id);
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<ClassPlan> findClassPlans(ClassPlan info);
	/**
	 * 加载培训机构下最大排序号。
	 * @param agencyId
	 * 培训机构ID。
	 * @return
	 */
	Integer loadMaxOrder(String agencyId);
	/**
	 * 新增班级。
	 * @param data
	 */
	void insertClassPlan(ClassPlan data);
	/**
	 * 更新班级。
	 * @param data
	 */
	void updateClassPlan(ClassPlan data);
	/**
	 * 删除班级。
	 * @param id
	 */
	void deleteClassPlan(String id);
}