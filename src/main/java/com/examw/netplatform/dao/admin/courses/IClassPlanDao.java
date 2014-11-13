package com.examw.netplatform.dao.admin.courses;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;

/**
 * 开班计划数据接口
 * @author fengwei.
 * @since 2014年5月20日 下午5:14:10.
 */
public interface IClassPlanDao extends IBaseDao<ClassPlan>{
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<ClassPlan> findClassPlans(ClassPlanInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(ClassPlanInfo info);
	/**
	 * 加载培训机构下最大排序号。
	 * @param agencyId
	 * 培训机构ID。
	 * @return
	 */
	Integer loadMaxOrder(String agencyId);
}