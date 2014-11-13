package com.examw.netplatform.service.admin.courses;

import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.service.IBaseDataService;
/**
 * 开班计划服务接口。
 * @author fengwei
 * 2014年5月20日 下午9:10:40
 */
public interface IClassPlanService extends IBaseDataService<ClassPlanInfo>{
	/**
	 * 加载讲义模式值名称。
	 * @param handoutMode
	 * 讲义模式。
	 * @return
	 * 讲义模式名称。
	 */
	String loadHandoutModeName(Integer handoutMode);
	/**
	 * 加载试听模式值名称。
	 * @param videoMode
	 * 试听模式。
	 * @return
	 * 试听模式名称。
	 */
	String loadVideoModeName(Integer videoMode);
	/**
	 * 加载状态值名称。
	 * @param status
	 * 班级状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载培训机构下最大排序号。
	 * @param agencyId
	 * 培训机构ID。
	 * @return
	 */
	Integer loadMaxOrder(String agencyId);
}