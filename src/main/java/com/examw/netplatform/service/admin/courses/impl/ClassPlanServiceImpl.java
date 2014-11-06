package com.examw.netplatform.service.admin.courses.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.courses.IClassPlanDao;
import com.examw.netplatform.dao.admin.settings.IAgencyDao;
import com.examw.netplatform.dao.admin.settings.IClassTypeDao;
import com.examw.netplatform.dao.admin.settings.ISubjectDao;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.domain.admin.settings.ClassType;
import com.examw.netplatform.domain.admin.settings.Subject;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.service.admin.courses.IClassPlanService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
/**
 * 开班计划服务接口实现类
 * @author fengwei
 * 2014年5月20日 下午9:12:45
 */
public class ClassPlanServiceImpl  extends BaseDataServiceImpl<ClassPlan, ClassPlanInfo> implements IClassPlanService {
	private static final Logger logger = Logger.getLogger(ClassPlanServiceImpl.class);
	private IAgencyDao agencyDao;
	private ISubjectDao subjectDao;
	private IClassTypeDao classTypeDao;
	private IClassPlanDao classPlanDao;
	private Map<Integer, String> handoutModeMap,videoModeMap,statusMap;
	/**
	 * 设置培训机构接口。
	 * @param agencyDao
	 */
	public void setAgencyDao(IAgencyDao agencyDao) {
		this.agencyDao = agencyDao;
	}
	/**
	 * 设置科目数据接口。
	 * @param subjectDao
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置班级类型数据接口。
	 * @param classTypeDao
	 */
	public void setClassTypeDao(IClassTypeDao classTypeDao) {
		this.classTypeDao = classTypeDao;
	}
	/**
	 * 设置开班计划数据接口。
	 * @param classPlanDao
	 */
	public void setClassPlanDao(IClassPlanDao classPlanDao) {
		this.classPlanDao = classPlanDao;
	}
	/**
	 * 设置讲义模式集合。
	 * @param handoutModeMap
	 */
	public void setHandoutModeMap(Map<Integer, String> handoutModeMap) {
		this.handoutModeMap = handoutModeMap;
	}
	/**
	 * 设置试听模式集合。
	 * @param videoModeMap
	 */
	public void setVideoModeMap(Map<Integer, String> videoModeMap) {
		this.videoModeMap = videoModeMap;
	}
	/**
	 * 设置班级状态集合。
	 * @param statusMap
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		this.statusMap = statusMap;
	}
	/*
	 * 加载讲义模式集合。
	 * @see com.examw.netplatform.service.admin.courses.IClassPlanService#loadHandoutModeName(java.lang.Integer)
	 */
	@Override
	public String loadHandoutModeName(Integer handoutMode) {
		if(this.handoutModeMap == null || this.handoutModeMap.size() == 0 || handoutMode == null) return null;
		return this.handoutModeMap.get(handoutMode);
	}
	/*
	 * 加载试听模式集合。
	 * @see com.examw.netplatform.service.admin.courses.IClassPlanService#loadVideoMode(java.lang.Integer)
	 */
	@Override
	public String loadVideoModeName(Integer videoMode) {
		if(this.videoModeMap == null || this.videoModeMap.size() == 0 || videoMode == null) return null;
		return this.videoModeMap.get(videoMode);
	}
	/*
	 * 加载班级状态集合。
	 * @see com.examw.netplatform.service.admin.courses.IClassPlanService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(this.statusMap == null || this.statusMap.size() == 0 || status == null) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<ClassPlan> find(ClassPlanInfo info) {
		return this.classPlanDao.findClassPlans(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.admin.courses.IClassPlanService#trans(com.examw.netplatform.domain.admin.courses.ClassPlan)
	 */
	@Override
	public ClassPlanInfo trans(ClassPlan data) {
		return this.changeModel(data);
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ClassPlanInfo changeModel(ClassPlan data) {
		if(data == null) return null;
		ClassPlanInfo info = new ClassPlanInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getAgency() != null){
			info.setAgencyId(data.getAgency().getId());
			info.setAgencyName(data.getAgency().getName());
		}
		if(data.getClassType() != null){
			info.setClassTypeId(data.getClassType().getId());
			info.setClassTypeName(data.getClassType().getName());
		}
		if(data.getSubject() != null){
			info.setSubjectId(data.getSubject().getId());
			info.setSubjectName(data.getSubject().getName());
			if(data.getSubject().getExam() != null){
				info.setExamId(data.getSubject().getExam().getId());
				info.setExamName(data.getSubject().getExam().getName());
//				if(data.getSubject().getExam().getCatalog() != null){
//					info.setCatalogId(data.getSubject().getExam().getCatalog().getId());
//					info.setCatalogName(data.getSubject().getExam().getCatalog().getName());
//				}
			}
		}
		info.setHandoutModeName(this.loadHandoutModeName(data.getHandoutMode()));
		info.setVideoModeName(this.loadVideoModeName(data.getVideoMode()));
		info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}
	/*
	 * 查询统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ClassPlanInfo info) {
		return this.classPlanDao.total(info);
	}
	/*
	 * 数据更新。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ClassPlanInfo update(ClassPlanInfo info) {
		if(info == null) return null;
		boolean isAdded = false;
		ClassPlan data = StringUtils.isEmpty(info.getId()) ?  null : this.classPlanDao.load(ClassPlan.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new ClassPlan();
			info.setCreateTime(new Date());
		}
		if(!isAdded)info.setCreateTime(data.getCreateTime());
		info.setLastTime(new Date());
		BeanUtils.copyProperties(info, data);
		if(!StringUtils.isEmpty(info.getAgencyId()) && (data.getAgency() == null || !info.getAgencyId().equalsIgnoreCase(data.getAgency().getId()))){
			Agency agency = this.agencyDao.load(Agency.class, info.getAgencyId());
			if(agency != null) data.setAgency(agency);
		}
		if(!StringUtils.isEmpty(info.getSubjectId()) && (data.getSubject() ==  null || !info.getSubjectId().equalsIgnoreCase(data.getSubject().getId()))){
			Subject subject = this.subjectDao.load(Subject.class, info.getSubjectId());
			if(subject != null) data.setSubject(subject);
		}
		if(!StringUtils.isEmpty(info.getClassTypeId()) && (data.getClassType() == null || !info.getClassTypeId().equalsIgnoreCase(data.getClassType().getId()))){
			ClassType classType = this.classTypeDao.load(ClassType.class, info.getClassTypeId());
			if(classType != null) data.setClassType(classType);
		}
		if(isAdded)this.classPlanDao.save(data);
		if(data.getAgency() != null) info.setAgencyName(data.getAgency().getName());
		if(data.getSubject() != null){
			info.setSubjectName(data.getSubject().getName());
			if(data.getSubject().getExam() != null){
				info.setExamId(data.getSubject().getExam().getId());
				info.setExamName(data.getSubject().getExam().getName());
			}
		}
		if(data.getClassType() != null) info.setClassTypeName(data.getClassType().getName());
		return info;
	}
	/*
	 * 数据删除。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			ClassPlan data = classPlanDao.load(ClassPlan.class, ids[i]);
			if(data != null) {
				if(logger.isDebugEnabled())logger.debug("删除数据［"+ ids[i] +"］");
				this.classPlanDao.delete(data);
			}
		}
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.courses.IClassPlanService#findClassPlans(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<ClassPlanInfo> findClassPlans(String agencyId,String catalogId, String examId,String className) {
		return this.changeModel(this.classPlanDao.findClassPlans(agencyId, catalogId, examId,className));
	}
	/*
	 * 根据ID加载对象。
	 * @see com.examw.netplatform.service.admin.courses.IClassPlanService#loadClassPlan(java.lang.String)
	 */
	@Override
	public ClassPlan loadClassPlan(String id) {
		if(StringUtils.isEmpty(id)) return null;
		return this.classPlanDao.load(ClassPlan.class, id);
	}
}