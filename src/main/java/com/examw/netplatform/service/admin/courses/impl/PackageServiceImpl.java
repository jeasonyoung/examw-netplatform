package com.examw.netplatform.service.admin.courses.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.courses.IPackageDao;
import com.examw.netplatform.dao.admin.settings.IAgencyDao;
import com.examw.netplatform.dao.admin.settings.IExamDao;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.courses.Package;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.domain.admin.settings.Exam;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.admin.courses.PackageInfo;
import com.examw.netplatform.service.admin.courses.IClassPlanService;
import com.examw.netplatform.service.admin.courses.IPackageService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
/**
 * 套餐服务实现类
 * @author fengwei.
 * @since 2014年5月21日 下午3:16:58.
 */
public class PackageServiceImpl extends BaseDataServiceImpl<Package,PackageInfo> implements IPackageService {
	private static final Logger logger = Logger.getLogger(PackageServiceImpl.class);
	private IPackageDao packageDao;
	private IAgencyDao agencyDao;
	private IExamDao examDao;
	private IClassPlanService classPlanService;
	private Map<Integer,String> statusMap;
	/**
	 * 设置套餐数据接口。
	 * @param packageDao
	 * 套餐数据接口。
	 */
	public void setPackageDao(IPackageDao packageDao) {
		this.packageDao = packageDao;
	}
	/**
	 * 设置机构数据接口。
	 * @param agencyDao
	 */
	public void setAgencyDao(IAgencyDao agencyDao) {
		this.agencyDao = agencyDao;
	}
	/**
	 * 设置考试数据接口。
	 * @param examDao
	 * 考试数据接口。
	 */
	public void setExamDao(IExamDao examDao) {
		this.examDao = examDao;
	}
	/**
	 * 设置班级服务接口。
	 * @param classPlanService
	 * 班级服务接口。
	 */
	public void setClassPlanService(IClassPlanService classPlanService) {
		this.classPlanService = classPlanService;
	}
	/**
	 * 设置状态集合。
	 * @param statusMap
	 */
	public void setStatusMap(Map<Integer,String> statusMap) {
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态名称。
	 * @see com.examw.netplatform.service.admin.courses.IPackageService#loadStatusName(java.lang.Integer)
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
	protected List<Package> find(PackageInfo info) {
		return this.packageDao.findPackages(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected PackageInfo changeModel(Package data) {
		if(data == null) return null;
		PackageInfo info = new PackageInfo();
		BeanUtils.copyProperties(data, info, new String[]{"classes"});
		if(data.getAgency() != null){
			info.setAgencyId(data.getAgency().getId());
			info.setAgencyName(data.getAgency().getName());
		}
		if(data.getExam() != null){
			info.setExamId(data.getExam().getId());
			info.setExamName(data.getExam().getName());
//			if(data.getExam().getCatalog() != null){
//				info.setCatalogId(data.getExam().getCatalog().getId());
//			}
		}
		info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}
	/*
	 * 查询统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(PackageInfo info) {
		return this.packageDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public PackageInfo update(PackageInfo info) {
		if(info == null) return null;
		boolean isAdded = false;
		Package data = StringUtils.isEmpty(info.getId()) ? null : this.packageDao.load(Package.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			data = new Package();
		}
		if(!isAdded) info.setCreateTime(data.getCreateTime());
		info.setLastTime(new Date());
		BeanUtils.copyProperties(info, data, new String[]{"classes"});
		if(!StringUtils.isEmpty(info.getAgencyId()) && (data.getAgency() == null || !data.getAgency().getId().equalsIgnoreCase(info.getAgencyId()))){
			Agency agency = this.agencyDao.load(Agency.class, info.getAgencyId());
			if(agency != null) data.setAgency(agency);
		}
		if(!StringUtils.isEmpty(info.getExamId()) && (data.getExam() == null || !data.getExam().getId().equalsIgnoreCase(info.getExamId()))){
			Exam exam = this.examDao.load(Exam.class, info.getExamId());
			if(exam != null) data.setExam(exam);
		}
		Set<ClassPlan> classPlans = new HashSet<>();
		if(info.getClasses() != null && info.getClasses().length > 0){
			for(String id : info.getClasses()){
				if(StringUtils.isEmpty(id)) continue;
				ClassPlan cp = this.classPlanService.loadClassPlan(id);
				if(cp != null) classPlans.add(cp);
			}
		}
		data.setClasses(classPlans);
		if(isAdded) this.packageDao.save(data);
		if(data.getAgency() != null){
			info.setAgencyId(data.getAgency().getId());
			info.setAgencyName(data.getAgency().getName());
		}
		if(data.getExam() != null){
			info.setExamId(data.getExam().getId());
			info.setExamName(data.getExam().getName());
//			if(data.getExam().getCatalog() != null){
//				info.setCatalogId(data.getExam().getCatalog().getId());
//			}
		}
		info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			Package data = this.packageDao.load(Package.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled())logger.debug("删除［"+ ids[i] + "］数据。");
				this.packageDao.delete(data);
			}
		}
	}
	/*
	 * 加载套餐下班级集合。
	 * @see com.examw.netplatform.service.admin.courses.IPackageService#loadClasses(java.lang.String)
	 */
	@Override
	public List<ClassPlanInfo> loadClasses(String packageId) {
		List<ClassPlanInfo> list = new ArrayList<>();
		if(!StringUtils.isEmpty(packageId)){
			Package data = this.packageDao.load(Package.class, packageId);
			if(data != null && data.getClasses() != null){
				for(ClassPlan cp : data.getClasses()){
					if(cp == null) continue;
					ClassPlanInfo e = this.classPlanService.trans(cp);
					if(e != null) list.add(e);
				}
			}
		}
		return list;
	}
	/*
	 * 加载套餐数据集合。
	 * @see com.examw.netplatform.service.admin.courses.IPackageService#findPackages(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<PackageInfo> findPackages(String agencyId, String catalogId,String examId, String packageName) {
		return this.changeModel(this.packageDao.findPackages(agencyId, catalogId, examId, packageName));
	}
}