package com.examw.netplatform.service.admin.teacher;

import java.util.List;

import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.admin.settings.AgencyUserInfo;
import com.examw.netplatform.service.IBaseDataService;

/**
 * 教师服务接口
 * @author fengwei.
 * @since 2014年5月29日 下午3:25:45.
 */
public interface ITeacherService extends IBaseDataService<AgencyUserInfo>{
	/**
	 * 加载机构用户下班级集合。
	 * @param agencyUserId
	 * 机构用户ID。
	 * @return
	 * 班级集合。
	 */
	List<ClassPlanInfo> loadClasses(String agencyUserId);
	/**
	 * 更新机构用户班级集合。
	 * @param agencyUserId
	 * 机构用户ID。
	 * @param classId
	 * 班级集合ID。
	 */
	void saveClasses(String agencyUserId,String[] classId);
	/**
	 * 删除机构用户班级集合。
	 * @param agencyUserId
	 * 机构用户ID。
	 * @param classId
	 * 班级集合ID。
	 */
	void deleteClasses(String agencyUserId,String[] classId);
}