package com.examw.netplatform.service.admin.courses.impl;

import java.util.Arrays;
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
import com.examw.netplatform.domain.admin.settings.Category;
import com.examw.netplatform.domain.admin.settings.ClassType;
import com.examw.netplatform.domain.admin.settings.Exam;
import com.examw.netplatform.domain.admin.settings.Subject;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.service.admin.courses.IClassPlanService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
import com.examw.service.Status;
/**
 * 开班计划服务接口实现类。
 * @author fengwei
 * 2014年5月20日 下午9:12:45
 */
public class ClassPlanServiceImpl  extends BaseDataServiceImpl<ClassPlan, ClassPlanInfo> implements IClassPlanService {
	private static final Logger logger = Logger.getLogger(ClassPlanServiceImpl.class);
	private IClassPlanDao classPlanDao;
	private IClassTypeDao classTypeDao;
	private IAgencyDao agencyDao;
	private ISubjectDao subjectDao;
	private Map<Integer, String> handoutModeMap,videoModeMap,statusMap;
	/**
	 * 设置开办计划数据接口。
	 * @param classPlanDao 
	 *	  开办计划数据接口。
	 */
	public void setClassPlanDao(IClassPlanDao classPlanDao) {
		if(logger.isDebugEnabled()) logger.debug("注入开办计划数据接口...");
		this.classPlanDao = classPlanDao;
	}
	/**
	 * 设置班级类型数据接口。
	 * @param classTypeDao 
	 *	  班级类型数据接口。
	 */
	public void setClassTypeDao(IClassTypeDao classTypeDao) {
		if(logger.isDebugEnabled()) logger.debug("注入班级类型数据接口...");
		this.classTypeDao = classTypeDao;
	}
	/**
	 * 设置培训机构数据接口。
	 * @param agencyDao 
	 *	  培训机构数据接口。
	 */
	public void setAgencyDao(IAgencyDao agencyDao) {
		if(logger.isDebugEnabled()) logger.debug("注入培训机构数据接口...");
		this.agencyDao = agencyDao;
	}
	/**
	 * 设置考试科目数据接口。
	 * @param subjectDao 
	 *	  考试科目数据接口。
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		if(logger.isDebugEnabled()) logger.debug("注入考试科目数据接口...");
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置讲义模式值名称集合。
	 * @param handoutModeMap 
	 *	  讲义模式值名称集合。
	 */
	public void setHandoutModeMap(Map<Integer, String> handoutModeMap) {
		if(logger.isDebugEnabled()) logger.debug("注入讲义模式值名称集合...");
		this.handoutModeMap = handoutModeMap;
	}
	/**
	 * 设置视频模式值名称集合。
	 * @param videoModeMap 
	 *	  视频模式值名称集合。
	 */
	public void setVideoModeMap(Map<Integer, String> videoModeMap) {
		if(logger.isDebugEnabled()) logger.debug("注入视频模式值名称集合...");
		this.videoModeMap = videoModeMap;
	}
	/**
	 * 设置班级状态值名称集合。
	 * @param statusMap 
	 *	  班级状态值名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("注入班级状态值名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载讲义模式值名称。
	 * @see com.examw.netplatform.service.admin.courses.IClassPlanService#loadHandoutModeName(java.lang.Integer)
	 */
	@Override
	public String loadHandoutModeName(Integer handoutMode) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载讲义模式值［%d］名称...", handoutMode));
		if(handoutMode == null || this.handoutModeMap == null || this.handoutModeMap.size() == 0) return null;
		return this.handoutModeMap.get(handoutMode);
	}
	/*
	 * 加载试听模式值名称。
	 * @see com.examw.netplatform.service.admin.courses.IClassPlanService#loadVideoModeName(java.lang.Integer)
	 */
	@Override
	public String loadVideoModeName(Integer videoMode) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试听模式值［%d］名称...", videoMode));
		if(videoMode == null || this.videoModeMap == null || this.videoModeMap.size() == 0) return null;
		return this.videoModeMap.get(videoMode);
	}
	/*
	 * 加载状态值名称。
	 * @see com.examw.netplatform.service.admin.courses.IClassPlanService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载状态值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<ClassPlan> find(ClassPlanInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.classPlanDao.findClassPlans(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ClassPlanInfo changeModel(ClassPlan data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 ClassPlan => ClassPlanInfo ... ");
		if(data == null) return null;
		ClassPlanInfo info = new ClassPlanInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getClassType() != null){//班级类型
			info.setClassTypeId(data.getClassType().getId());
			info.setClassTypeName(data.getClassType().getName());
		}
		if(data.getAgency() != null){//所属培训机构
			info.setAgencyId(data.getAgency().getId());
			info.setAgencyName(data.getAgency().getName());
		}
		Subject subject = null;
		if((subject = data.getSubject()) != null){//所属科目
			info.setSubjectId(subject.getId());
			info.setSubjectName(subject.getName());
			Exam exam = null;
			if((exam = subject.getExam()) != null){//所属考试
				info.setExamId(exam.getId());
				info.setExamName(exam.getName());
				Category category = null;
				if((category = exam.getCategory()) != null){//考试类别
					info.setCategoryId(category.getId());
				}
			}
		}
		info.setHandoutModeName(this.loadHandoutModeName(info.getHandoutMode()));//讲义模型
		info.setVideoModeName(this.loadVideoModeName(info.getVideoMode()));//视频模型
		info.setStatusName(this.loadStatusName(info.getStatus()));//状态
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ClassPlanInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.classPlanDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ClassPlanInfo update(ClassPlanInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		ClassPlan data = StringUtils.isEmpty(info.getId()) ? null : this.classPlanDao.load(ClassPlan.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new ClassPlan();
			info.setCreateTime(new Date());
			if(info.getStatus() == null) info.setStatus(Status.ENABLED.getValue());
		}else {
			info.setCreateTime(data.getCreateTime());
			if(info.getCreateTime() == null) info.setCreateTime(new Date());
		}
		info.setLastTime(new Date());
		BeanUtils.copyProperties(info, data);
		
		if(StringUtils.isEmpty(info.getClassTypeId())) throw new RuntimeException("班级类型ID为空！");
		ClassType classType = this.classTypeDao.load(ClassType.class, info.getClassTypeId());
		if(classType == null) throw new RuntimeException(String.format("班级类型［%s］不存在！", info.getClassTypeId()));
		data.setClassType(classType);
		
		if(StringUtils.isEmpty(info.getAgencyId())) throw new RuntimeException("所属培训机构ID为空！");
		Agency agency = this.agencyDao.load(Agency.class, info.getAgencyId());
		if(agency == null) throw new RuntimeException(String.format("所属培训机构［%s］不存在！", info.getAgencyId()));
		data.setAgency(agency);
		
		if(StringUtils.isEmpty(info.getSubjectId())) throw new RuntimeException("所属科目ID为空！");
		Subject subject = this.subjectDao.load(Subject.class, info.getSubjectId());
		if(subject == null) throw new RuntimeException(String.format("所属科目［%s］不存在！", info.getSubjectId()));
		data.setSubject(subject);
		
		if(isAdded) this.classPlanDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			ClassPlan data = this.classPlanDao.load(ClassPlan.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s", ids[i]));
				this.classPlanDao.delete(data);
			}
		}
	}
	/*
	 * 加载培训机构下最大排序号。
	 * @see com.examw.netplatform.service.admin.courses.IClassPlanService#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String agencyId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载培训机构［%s］下最大排序号...", agencyId));
		return this.classPlanDao.loadMaxOrder(agencyId);
	}
	/*
	 * 加载机构下班级集合。
	 * @see com.examw.netplatform.service.admin.courses.IClassPlanService#loadClasses(java.lang.String)
	 */
	@Override
	public List<ClassPlanInfo> loadClasses(final String agencyId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载机构［%s］下班级集合...", agencyId));
		return this.changeModel(this.classPlanDao.findClassPlans(new ClassPlanInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getAgencyId() {return agencyId;}
			@Override
			public String getSort() {return "orderNo";}
			@Override
			public String getOrder() {return "asc";}
		}));
	}
}