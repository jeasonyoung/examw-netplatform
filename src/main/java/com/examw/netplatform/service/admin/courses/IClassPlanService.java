package com.examw.netplatform.service.admin.courses;

import java.util.List;

import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.service.IBaseDataService;
/**
 * 开班计划服务接口
 * @author fengwei
 * 2014年5月20日 下午9:10:40
 */
public interface IClassPlanService extends IBaseDataService<ClassPlanInfo>{
	/**
	 * 加载讲义模式名称。
	 * @param handoutMode
	 * 讲义模式。
	 * @return
	 * 讲义模式名称。
	 */
	String loadHandoutModeName(Integer handoutMode);
	/**
	 * 加载试听模式名称。
	 * @param videoMode
	 * 试听模式。
	 * @return
	 * 试听模式名称。
	 */
	String loadVideoModeName(Integer videoMode);
	/**
	 * 加载状态名称。
	 * @param status
	 * 班级状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 查询数据。
	 * @param agencyId
	 * 机构ID。
	 * @param catalogId
	 * 所属考试类别ID。
	 * @param examId
	 * 所属考试ID。
	 * @param className
	 * 班级名称。
	 * @return
	 * 查询数据。
	 */
	List<ClassPlanInfo> findClassPlans(String agencyId, String catalogId, String examId,String className);
	/**
	 * 根据ID加载对象。
	 * @param id
	 * @return
	 */
	ClassPlan loadClassPlan(String id);
	/**
	 * 类型转换。
	 * @param data
	 * @return
	 */
	ClassPlanInfo trans(ClassPlan data);
}