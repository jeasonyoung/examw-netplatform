package com.examw.netplatform.service.admin.courses.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.courses.IClassPlanDao;
import com.examw.netplatform.dao.admin.courses.IPackageDao;
import com.examw.netplatform.dao.admin.settings.IAgencyDao;
import com.examw.netplatform.dao.admin.settings.IExamDao;
import com.examw.netplatform.dao.admin.settings.ISubjectDao;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.courses.Package;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.domain.admin.settings.Category;
import com.examw.netplatform.domain.admin.settings.Exam;
import com.examw.netplatform.domain.admin.settings.Subject;
import com.examw.netplatform.model.admin.courses.PackageInfo;
import com.examw.netplatform.service.admin.courses.IPackageService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
/**
 * 套餐服务接口实现类。
 * @author fengwei.
 * @since 2014年5月21日 下午3:16:58.
 */
public class PackageServiceImpl extends BaseDataServiceImpl<Package,PackageInfo> implements IPackageService {
	private static final Logger logger = Logger.getLogger(PackageServiceImpl.class);
	private IPackageDao packageDao;
	private IAgencyDao agencyDao;
	private IExamDao examDao;
	private ISubjectDao subjectDao;
	private IClassPlanDao classPlanDao;
	private Map<Integer, String> statusMap;
	/**
	 * 设置套餐数据接口。
	 * @param packageDao 
	 *	  套餐数据接口。
	 */
	public void setPackageDao(IPackageDao packageDao) {
		if(logger.isDebugEnabled()) logger.debug("注入套餐数据接口...");
		this.packageDao = packageDao;
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
	 * 设置考试数据接口。
	 * @param examDao 
	 *	  考试数据接口。
	 */
	public void setExamDao(IExamDao examDao) {
		if(logger.isDebugEnabled()) logger.debug("注入考试数据接口...");
		this.examDao = examDao;
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
	 * 设置班级数据接口。
	 * @param classPlanDao 
	 *	  班级数据接口。
	 */
	public void setClassPlanDao(IClassPlanDao classPlanDao) {
		if(logger.isDebugEnabled()) logger.debug("注入班级数据接口...");
		this.classPlanDao = classPlanDao;
	}
	/**
	 * 设置状态值名称集合。
	 * @param statusMap 
	 *	  状态值名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("注入状态值名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态值名称。
	 * @see com.examw.netplatform.service.admin.courses.IPackageService#loadStatusName(java.lang.Integer)
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
	protected List<Package> find(PackageInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.packageDao.findPackages(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected PackageInfo changeModel(Package data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Package => PackageInfo ...");
		if(data == null) return null;
		PackageInfo info = new PackageInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getAgency() != null){//机构
			info.setAgencyId(data.getAgency().getId());
			info.setAgencyName(data.getAgency().getName());
		}
		Exam exam = null;
		if((exam = data.getExam()) != null){//所属考试
			info.setExamId(exam.getId());
			info.setExamName(exam.getName());
			Category category = null;
			if((category = exam.getCategory()) != null){//所属考试类别。
				info.setCategoryId(category.getId());
			}
		}
		if(data.getSubjects() != null && data.getSubjects().size() > 0){//科目
			List<String> subjectIdList = new ArrayList<>(),subjectNameList = new ArrayList<>();
			for(Subject subject : data.getSubjects()){
				if(subject == null) continue;
				subjectIdList.add(subject.getId());
				subjectNameList.add(subject.getName());
			}
			info.setSubjectId(subjectIdList.toArray(new String[0]));
			info.setSubjectName(subjectNameList.toArray(new String[0]));
		}
		info.setStatusName(this.loadStatusName(info.getStatus()));
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(PackageInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.packageDao.total(info);
	}
	/*
	 * 数据更新。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public PackageInfo update(PackageInfo info) {
		if(logger.isDebugEnabled()) logger.debug("数据更新...");
		if(info == null) return null;
		boolean isAdded = false;
		Package data = StringUtils.isEmpty(info.getId()) ? null : this.packageDao.load(Package.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			data = new Package();
		}else {
			info.setCreateTime(data.getCreateTime());
			if(info.getCreateTime() == null) info.setCreateTime(new Date());
		}
		info.setLastTime(new Date());
		if(info.getStartTime() != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(info.getStartTime());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			info.setStartTime(calendar.getTime());
		}
		if(info.getEndTime() != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(info.getEndTime());
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 0);
			info.setEndTime(calendar.getTime());
		}
		if(info.getExpireTime() != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(info.getExpireTime());
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 0);
			info.setExpireTime(calendar.getTime());
		}
		BeanUtils.copyProperties(info, data);
		
		if(StringUtils.isEmpty(info.getAgencyId())) throw new RuntimeException("培训机构ID不能为空！");
		Agency agency = this.agencyDao.load(Agency.class, info.getAgencyId());
		if(agency == null) throw new RuntimeException(String.format("培训机构［%s］不存在！", info.getAgencyId()));
		data.setAgency(agency);
		
		if(StringUtils.isEmpty(info.getExamId())) throw new RuntimeException("考试ID不存在！");
		Exam exam = this.examDao.load(Exam.class, info.getExamId());
		if(exam == null) throw new RuntimeException(String.format("考试［%s］不存在！", info.getExamId()));
		data.setExam(exam);
		
		Set<Subject> subjects = null;
		if(info.getSubjectId() != null && info.getSubjectId().length > 0){
			subjects = new HashSet<>();
			for(String subjectId : info.getSubjectId()){
				if(StringUtils.isEmpty(subjectId)) continue;
				Subject subject = this.subjectDao.load(Subject.class, subjectId);
				if(subject != null) subjects.add(subject);
			}
		}
		data.setSubjects(subjects);
		
		Set<ClassPlan> classes = null;
		if(info.getClassId() != null && info.getClassId().length > 0){
			classes = new HashSet<>();
			for(String classId : info.getClassId()){
				if(StringUtils.isEmpty(classId)) continue;
				ClassPlan classPlan = this.classPlanDao.load(ClassPlan.class, classId);
				if(classPlan != null) classes.add(classPlan);
			}
		}
		data.setClasses(classes);
		
		if(isAdded) this.packageDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据 %s...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Package data = this.packageDao.load(Package.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据［%s］...", ids[i]));
				this.packageDao.delete(data);
			}
		}
	}
	/*
	 * 加载培训机构下最大排序号。
	 * @see com.examw.netplatform.service.admin.courses.IPackageService#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String agencyId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载培训机构［%s］下最大排序号...", agencyId));
		return this.packageDao.loadMaxOrder(agencyId);
	}
	/*
	 * 加载机构套餐集合。
	 * @see com.examw.netplatform.service.admin.courses.IPackageService#loadPackages(java.lang.String)
	 */
	@Override
	public List<PackageInfo> loadPackages(final String agencyId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载机构［%s］套餐集合...", agencyId));
		return this.changeModel(this.packageDao.findPackages(new PackageInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getAgencyId() { return agencyId; }
		}));
	}
	/*
	 * 数据模型转换
	 * 2015.01.23
	 * @see com.examw.netplatform.service.admin.courses.IPackageService#conversion(com.examw.netplatform.domain.admin.courses.Package)
	 */
	@Override
	public PackageInfo conversion(Package data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换Package --> PackageInfo...");
		return this.changeModel(data);
	}
}