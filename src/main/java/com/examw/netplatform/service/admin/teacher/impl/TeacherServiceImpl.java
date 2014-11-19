package com.examw.netplatform.service.admin.teacher.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.admin.settings.AgencyUserInfo;
import com.examw.netplatform.service.admin.courses.IClassPlanService;
import com.examw.netplatform.service.admin.settings.AgencyUserIdentity;
import com.examw.netplatform.service.admin.settings.impl.AgencyUserServiceImpl;
import com.examw.netplatform.service.admin.teacher.ITeacherService;
/**
 * 教师服务接口实现类。
 * 
 * @author fengwei.
 * @since 2014年5月29日 下午3:27:21.
 */
public class TeacherServiceImpl extends AgencyUserServiceImpl implements ITeacherService {
	private static final Logger logger = Logger.getLogger(TeacherServiceImpl.class);
	private IClassPlanService classPlanService;
	/**
	 * 设置班级服务接口。
	 * @param classPlanService 
	 *	  班级服务接口。
	 */
	public void setClassPlanService(IClassPlanService classPlanService) {
		if(logger.isDebugEnabled()) logger.debug("注入班级服务接口...");
		this.classPlanService = classPlanService;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.settings.impl.AgencyUserServiceImpl#find(com.examw.netplatform.model.admin.settings.AgencyUserInfo)
	 */
	@Override
	protected List<AgencyUser> find(AgencyUserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		info.setIdentity(AgencyUserIdentity.TEACHER.getValue());
		return super.find(info);
	}
	/*
	 * 加载机构用户下班级集合。
	 * @see com.examw.netplatform.service.admin.teacher.ITeacherService#loadClasses(java.lang.String)
	 */
	@Override
	public List<ClassPlanInfo> loadClasses(String agencyUserId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载机构用户［%s］下班级集合...", agencyUserId));
		List<ClassPlanInfo> list = new ArrayList<>();
		if(!StringUtils.isEmpty(agencyUserId)){
			AgencyUser agencyUser = this.agencyUserDao.load(AgencyUser.class, agencyUserId);
			if(agencyUser != null){
				for(ClassPlan classPlan : agencyUser.getClasses()){
					if(classPlan == null) continue;
					ClassPlanInfo info = this.classPlanService.conversion(classPlan);
					if(info != null){ list.add(info); }
				}
			}
		}
		return list;
	}
	/*
	 * 更新机构用户班级集合。
	 * @see com.examw.netplatform.service.admin.teacher.ITeacherService#saveClasses(java.lang.String, java.lang.String[])
	 */
	@Override
	public void saveClasses(String agencyUserId, String[] classId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("更新机构用户［%1$s］班级集合 %2$s ...", agencyUserId, Arrays.toString(classId)));
		if(StringUtils.isEmpty(agencyUserId)) throw new RuntimeException("机构用户ID不存在！");
		if(classId == null || classId.length == 0) return;
		AgencyUser agencyUser = this.agencyUserDao.load(AgencyUser.class, agencyUserId);
		if(agencyUser == null) return;
		if(agencyUser.getClasses() == null) agencyUser.setClasses(new HashSet<ClassPlan>());
		for(int i = 0; i < classId.length; i++){
			if(StringUtils.isEmpty(classId[i])) continue;
			ClassPlan classPlan = this.classPlanService.loadClassPlan(classId[i]);
			if(classPlan != null){
				agencyUser.getClasses().add(classPlan);
			}
		}
	}
	/*
	 * 删除机构用户班级集合。
	 * @see com.examw.netplatform.service.admin.teacher.ITeacherService#deleteClasses(java.lang.String, java.lang.String[])
	 */
	@Override
	public void deleteClasses(String agencyUserId, String[] classId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除机构用户［%1$s］班级集合 %2$s ...", agencyUserId, Arrays.toString(classId)));
		if(StringUtils.isEmpty(agencyUserId) || classId == null || classId.length == 0) return;
		AgencyUser agencyUser = this.agencyUserDao.load(AgencyUser.class, agencyUserId);
		if(agencyUser == null || agencyUser.getClasses() == null || agencyUser.getClasses().size() == 0) return;
		List<ClassPlan> removeClasses = new ArrayList<>();
		for(ClassPlan classPlan : agencyUser.getClasses()){
			if(classPlan == null) continue;
			if(Arrays.binarySearch(classId, classPlan.getId()) > -1){
				removeClasses.add(classPlan);
			}
		}
		if(removeClasses.size() > 0) agencyUser.getClasses().removeAll(removeClasses);
	}
}