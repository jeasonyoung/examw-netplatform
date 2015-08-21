package com.examw.netplatform.service.admin.courses.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.courses.ClassMapper;
import com.examw.netplatform.dao.admin.settings.AgencyMapper;
import com.examw.netplatform.dao.admin.settings.ClassTypeMapper;
import com.examw.netplatform.dao.admin.settings.ExamMapper;
import com.examw.netplatform.dao.admin.settings.SubjectMapper;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.settings.ClassType;
import com.examw.netplatform.domain.admin.settings.Exam;
import com.examw.netplatform.domain.admin.settings.Subject;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.service.admin.courses.IClassService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 班级服务接口实现类。
 * @author fengwei
 * 2014年5月20日 下午9:12:45
 */
public class ClassServiceImpl implements IClassService {
	private static final Logger logger = Logger.getLogger(ClassServiceImpl.class);
	private AgencyMapper agencyDao;
	private ExamMapper examDao;
	private SubjectMapper subjectDao;
	private ClassMapper classPlanDao;
	private ClassTypeMapper classTypeDao;
	private Map<Integer, String> handoutModeMap,videoModeMap,statusMap;
	/**
	 * 设置班级数据接口。
	 * @param classPlanDao 
	 *	  开办计划数据接口。
	 */
	public void setClassPlanDao(ClassMapper classPlanDao) {
		logger.debug("注入开办计划数据接口...");
		this.classPlanDao = classPlanDao;
	}
	/**
	 * 设置班级类型数据接口。
	 * @param classTypeDao 
	 *	  班级类型数据接口。
	 */
	public void setClassTypeDao(ClassTypeMapper classTypeDao) {
		logger.debug("注入班级类型数据接口...");
		this.classTypeDao = classTypeDao;
	}
	/**
	 * 设置培训机构数据接口。
	 * @param agencyDao 
	 *	  培训机构数据接口。
	 */
	public void setAgencyDao(AgencyMapper agencyDao) {
		logger.debug("注入培训机构数据接口...");
		this.agencyDao = agencyDao;
	}
	/**
	 * 设置考试数据接口。
	 * @param examDao 
	 *	  考试数据接口。
	 */
	public void setExamDao(ExamMapper examDao) {
		logger.debug("注入考试数据接口...");
		this.examDao = examDao;
	}
	/**
	 * 设置考试科目数据接口。
	 * @param subjectDao 
	 *	  考试科目数据接口。
	 */
	public void setSubjectDao(SubjectMapper subjectDao) {
		logger.debug("注入考试科目数据接口...");
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置讲义模式值名称集合。
	 * @param handoutModeMap 
	 *	  讲义模式值名称集合。
	 */
	public void setHandoutModeMap(Map<Integer, String> handoutModeMap) {
		logger.debug("注入讲义模式值名称集合...");
		this.handoutModeMap = handoutModeMap;
	}
	/**
	 * 设置视频模式值名称集合。
	 * @param videoModeMap 
	 *	  视频模式值名称集合。
	 */
	public void setVideoModeMap(Map<Integer, String> videoModeMap) {
		logger.debug("注入视频模式值名称集合...");
		this.videoModeMap = videoModeMap;
	}
	/**
	 * 设置班级状态值名称集合。
	 * @param statusMap 
	 *	  班级状态值名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		logger.debug("注入班级状态值名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载讲义模式值名称。
	 * @see com.examw.netplatform.service.admin.courses.IClassPlanService#loadHandoutModeName(java.lang.Integer)
	 */
	@Override
	public String loadHandoutModeName(Integer handoutMode) {
		logger.debug(String.format("加载讲义模式值［%d］名称...", handoutMode));
		if(handoutMode == null || this.handoutModeMap == null || this.handoutModeMap.size() == 0) return null;
		return this.handoutModeMap.get(handoutMode);
	}
	/*
	 * 加载试听模式值名称。
	 * @see com.examw.netplatform.service.admin.courses.IClassPlanService#loadVideoModeName(java.lang.Integer)
	 */
	@Override
	public String loadVideoModeName(Integer videoMode) {
		logger.debug(String.format("加载试听模式值［%d］名称...", videoMode));
		if(videoMode == null || this.videoModeMap == null || this.videoModeMap.size() == 0) return null;
		return this.videoModeMap.get(videoMode);
	}
	/*
	 * 加载状态值名称。
	 * @see com.examw.netplatform.service.admin.courses.IClassPlanService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		logger.debug(String.format("加载状态值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.courses.IClassService#datagrid(com.examw.netplatform.model.admin.courses.ClassPlanInfo)
	 */
	@Override
	public DataGrid<ClassPlanInfo> datagrid(ClassPlanInfo info) {
		logger.debug("查询数据...");
		//分页排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getSort()) + " " + StringUtils.trimToEmpty(info.getOrder()));
		//查询数据
		final List<ClassPlan> list = this.classPlanDao.findClassPlans(info);
		//分页信息
		final PageInfo<ClassPlan> pageInfo = new PageInfo<ClassPlan>(list);
		//初始化
		final DataGrid<ClassPlanInfo> grid = new DataGrid<ClassPlanInfo>();
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//批量数据类型转换。
	private List<ClassPlanInfo> changeModel(List<ClassPlan> classPlans){
		final List<ClassPlanInfo> list = new ArrayList<ClassPlanInfo>();
		if(classPlans != null && classPlans.size() > 0){
			for(ClassPlan data : classPlans){
				if(data == null) continue;
				list.add(this.conversion(data));
			}
		}
		return list;
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.admin.courses.IClassService#conversion(com.examw.netplatform.domain.admin.courses.ClassPlan)
	 */
	@Override
	public ClassPlanInfo conversion(ClassPlan data) {
		logger.debug("类型转换[ClassPlan -> ClassPlanInfo]... ");
		if(data != null){
			final ClassPlanInfo info = new ClassPlanInfo();
			BeanUtils.copyProperties(data, info);
			
			info.setHandoutModeName(this.loadHandoutModeName(data.getHandoutMode()));
			info.setVideoModeName(this.loadStatusName(data.getHandoutMode()));
			info.setStatusName(this.loadStatusName(data.getStatus()));
			
			//班级类型
			if(StringUtils.isNotBlank(info.getTypeId()) && StringUtils.isBlank(info.getTypeName())){
				final ClassType classType = this.classTypeDao.getClassType(info.getTypeId());
				if(classType != null) info.setTypeName(classType.getName());
			}
			//所属考试
			if(StringUtils.isNotBlank(info.getExamId()) && StringUtils.isBlank(info.getExamName())){
				final Exam exam = this.examDao.getExam(info.getExamId());
				if(exam != null) info.setExamName(exam.getName());
			}
			//科目
			if(StringUtils.isNotBlank(info.getSubjectId()) && StringUtils.isBlank(info.getSubjectName())){
				final Subject subject = this.subjectDao.getSubject(info.getSubjectId());
				if(subject != null) info.setSubjectName(subject.getName());
			}
			
			return info;
		}
		return null;
	}
	/*
	 * 加载培训机构下最大排序号。
	 * @see com.examw.netplatform.service.admin.courses.IClassService#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String agencyId) {
		logger.debug("加载培训机构下最大排序号...");
		return this.classPlanDao.loadMaxOrder(agencyId);
	}
	/*
	 * 加载机构下班级集合。
	 * @see com.examw.netplatform.service.admin.courses.IClassService#loadClasses(java.lang.String)
	 */
	@Override
	public List<ClassPlanInfo> loadClasses(String agencyId) {
		logger.debug("加载机构["+agencyId+"]下班级集合...");
		final ClassPlan info = new ClassPlan();
		info.setAgencyId(agencyId);
		return this.changeModel(this.classPlanDao.findClassPlans(info));
	}
	/*
	 * 加载班级。
	 * @see com.examw.netplatform.service.admin.courses.IClassService#loadClassPlan(java.lang.String)
	 */
	@Override
	public ClassPlan loadClassPlan(String classId) {
		logger.debug("加载班级..." + classId);
		return this.classPlanDao.getClassPlan(classId);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.admin.courses.IClassService#update(com.examw.netplatform.model.admin.courses.ClassPlanInfo)
	 */
	@Override
	public ClassPlanInfo update(ClassPlanInfo info) {
		logger.debug("更新数据");
		if(info == null) return null;
		//检查数据
		if(StringUtils.isBlank(info.getTypeId()) || this.classTypeDao.getClassType(info.getTypeId()) == null){
			throw new RuntimeException("班级类型["+info.getTypeId()+"]不存在!");
		}
		if(StringUtils.isBlank(info.getAgencyId()) || this.agencyDao.getAgency(info.getAgencyId()) == null){
			throw new RuntimeException("所属机构["+info.getAgencyId()+"]不存在!");
		}
		if(StringUtils.isBlank(info.getSubjectId()) || this.subjectDao.getSubject(info.getSubjectId()) == null){
			throw new RuntimeException("所属科目["+info.getSubjectId()+"]不存在!");
		}
		//
		ClassPlan data = StringUtils.isBlank(info.getId()) ? null : this.classPlanDao.getClassPlan(info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			if(StringUtils.isBlank(info.getId()))
				info.setId(UUID.randomUUID().toString());
			//初始化
			data = new ClassPlan();
		}
		//赋值
		BeanUtils.copyProperties(info, data);
		//保存
		if(isAdded){
			logger.debug("新增班级...");
			this.classPlanDao.insertClassPlan(data);
		}else {
			logger.debug("更新班级...");
			this.classPlanDao.updateClassPlan(data);
		}
		//返回
		return this.conversion(data);
	}
	/*
	 * 删除班级。
	 * @see com.examw.netplatform.service.admin.courses.IClassService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除班级..." + StringUtils.join(ids, ","));
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id)) continue;
				this.classPlanDao.deleteClassPlan(id);
			}
		}
	}
}