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
import com.examw.netplatform.dao.admin.courses.PackageMapper;
import com.examw.netplatform.dao.admin.settings.AgencyMapper;
import com.examw.netplatform.dao.admin.settings.ExamMapper;
import com.examw.netplatform.dao.admin.settings.SubjectMapper;
import com.examw.netplatform.domain.admin.courses.Package;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.admin.courses.PackageInfo;
import com.examw.netplatform.service.admin.courses.IClassService;
import com.examw.netplatform.service.admin.courses.IPackageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 套餐服务接口实现类。
 * @author fengwei.
 * @since 2014年5月21日 下午3:16:58.
 */
public class PackageServiceImpl implements IPackageService {
	private static final Logger logger = Logger.getLogger(PackageServiceImpl.class);
	private PackageMapper packageDao;
	private AgencyMapper agencyDao;
	private ExamMapper examDao;
	private SubjectMapper subjectDao;
	private ClassMapper classPlanDao;
	private Map<Integer, String> statusMap;
	private IClassService classPlanService;
	/**
	 * 设置班级服务接口。
	 * @param classPlanService 
	 *	  班级服务接口。
	 */
	public void setClassPlanService(IClassService classPlanService) {
		logger.debug("注入班级服务接口...");
		this.classPlanService = classPlanService;
	}
	/**
	 * 设置套餐数据接口。
	 * @param packageDao 
	 *	  套餐数据接口。
	 */
	public void setPackageDao(PackageMapper packageDao) {
		 logger.debug("注入套餐数据接口...");
		this.packageDao = packageDao;
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
	 * 设置班级数据接口。
	 * @param classPlanDao 
	 *	  班级数据接口。
	 */
	public void setClassPlanDao(ClassMapper classPlanDao) {
		logger.debug("注入班级数据接口...");
		this.classPlanDao = classPlanDao;
	}
	/**
	 * 设置状态值名称集合。
	 * @param statusMap 
	 *	  状态值名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		logger.debug("注入状态值名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态值名称。
	 * @see com.examw.netplatform.service.admin.courses.IPackageService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		logger.debug(String.format("加载状态值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 加载培训机构下最大排序号。
	 * @see com.examw.netplatform.service.admin.courses.IPackageService#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String agencyId) {
		logger.debug("加载培训机构下最大排序号...");
		return this.packageDao.loadMaxOrder(agencyId);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.courses.IPackageService#datagrid(com.examw.netplatform.model.admin.courses.PackageInfo)
	 */
	@Override
	public DataGrid<PackageInfo> datagrid(PackageInfo info) {
		logger.debug("查询数据...");
		//分页排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getOrder()) + " " + StringUtils.trimToEmpty(info.getSort()));
		//查询数据
		final List<Package> list = this.packageDao.findPackages(info);
		//分页信息
		final PageInfo<Package> pageInfo = new PageInfo<Package>(list);
		//初始化
		final DataGrid<PackageInfo> grid = new DataGrid<PackageInfo>();
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//批量数据类型转换。
	private List<PackageInfo> changeModel(List<Package> packages){
		final List<PackageInfo> list = new ArrayList<PackageInfo>();
		if(packages != null && packages.size() > 0){
			for(Package p : packages){
				if(p == null) continue;
				list.add(this.conversion(p));
			}
		}
		return list;
	}
	/*
	 * 数据类型转换。
	 * @see com.examw.netplatform.service.admin.courses.IPackageService#conversion(com.examw.netplatform.domain.admin.courses.Package)
	 */
	@Override
	public PackageInfo conversion(Package data) {
		logger.debug("数据类型转换[Package -> PackageInfo]...");
		PackageInfo info = (PackageInfo)data;
		info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}
	/*
	 * 加载机构下套餐集合。
	 * @see com.examw.netplatform.service.admin.courses.IPackageService#loadPackages(java.lang.String)
	 */
	@Override
	public List<PackageInfo> loadPackages(String agencyId) {
		logger.debug("加载机构["+agencyId+"]下套餐集合...");
		return this.changeModel(this.packageDao.findPackagesByAgency(agencyId, null));
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.examw.netplatform.service.admin.courses.IPackageService#loadClasses(java.lang.String)
	 */
	@Override
	public List<ClassPlanInfo> loadClasses(String packageId) {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * 更新套餐数据。
	 * @see com.examw.netplatform.service.admin.courses.IPackageService#update(com.examw.netplatform.model.admin.courses.PackageInfo)
	 */
	@Override
	public PackageInfo update(PackageInfo info) {
		logger.debug("更新套餐数据...");
		if(info == null) return null;
		//检查数据
		if(StringUtils.isBlank(info.getAgencyId()) || this.agencyDao.getAgency(info.getAgencyId()) == null){
			throw new RuntimeException("所属机构["+info.getAgencyId()+"]不存在!");
		}
		if(StringUtils.isBlank(info.getExamId()) || this.examDao.getExam(info.getExamId()) == null){
			throw new RuntimeException("所属考试["+info.getExamId()+"]不存在!");
		}
		//
		Package data = StringUtils.isBlank(info.getId()) ? null : this.packageDao.getPackage(info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			if(StringUtils.isBlank(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new Package();
		}
		//赋值
		BeanUtils.copyProperties(info, data);
		//保存
		if(isAdded){
			logger.debug("新增套餐...");
			this.packageDao.insertPackage(data);
		}else {
			logger.debug("保存套餐...");
			this.packageDao.updatePackage(data);
		}
		//返回
		return this.conversion(data);
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.netplatform.service.admin.courses.IPackageService#saveClasses(java.lang.String, java.lang.String[])
	 */
	@Override
	public void saveClasses(String packageId, String[] classId) {
		// TODO Auto-generated method stub
		
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.netplatform.service.admin.courses.IPackageService#deleteClasses(java.lang.String, java.lang.String[])
	 */
	@Override
	public void deleteClasses(String packageId, String[] classId) {
		// TODO Auto-generated method stub
		
	}
	/*
	 * 删除套餐。
	 * @see com.examw.netplatform.service.admin.courses.IPackageService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除套餐..." + StringUtils.join(ids,","));
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id)) continue;
				this.packageDao.deletePackage(id);
			}
		}
	}  
//	/*
//	 * 查询套餐下的班级集合
//	 * @see com.examw.netplatform.service.admin.courses.IPackageService#loadClasses(java.lang.String)
//	 */
//	@Override
//	public List<ClassPlanInfo> loadClasses(String packageId) {
//		if(logger.isDebugEnabled()) logger.debug(String.format("加载套餐［%s］下班级集合...", packageId));
//		List<ClassPlanInfo> list = new ArrayList<>();
//		if(!StringUtils.isEmpty(packageId)){
//			Package pack = this.packageDao.load(Package.class, packageId);
//			if(pack == null) throw new RuntimeException(String.format("套餐[%d]不存在",packageId));
//			for(ClassPlan classPlan : pack.getClasses()){
//				if(classPlan == null) continue;
//				ClassPlanInfo info = this.classPlanService.conversion(classPlan);
//				if(info != null){ list.add(info); }
//			}
//		}
//		return list;
//	}
//	/*
//	 * 更新套餐班级集合。
//	 * @see com.examw.netplatform.service.admin.teacher.ITeacherService#saveClasses(java.lang.String, java.lang.String[])
//	 */
//	@Override
//	public void saveClasses(String packageId, String[] classId) {
//		if(logger.isDebugEnabled()) logger.debug(String.format("更新套餐［%1$s］班级集合 %2$s ...", packageId, Arrays.toString(classId)));
//		if(StringUtils.isEmpty(packageId)) throw new RuntimeException("套餐ID不存在！");
//		if(classId == null || classId.length == 0) return;
//		Package pack = this.packageDao.load(Package.class, packageId);
//		if(pack == null) throw new RuntimeException(String.format("套餐[%d]不存在",packageId));
//		if(pack.getClasses() == null) pack.setClasses(new HashSet<ClassPlan>());
//		for(int i = 0; i < classId.length; i++){
//			if(StringUtils.isEmpty(classId[i])) continue;
//			ClassPlan classPlan = this.classPlanService.loadClassPlan(classId[i]);
//			if(classPlan != null){
//				pack.getClasses().add(classPlan);
//			}
//		}
//	}
//	/*
//	 * 删除机构用户班级集合。
//	 * @see com.examw.netplatform.service.admin.teacher.ITeacherService#deleteClasses(java.lang.String, java.lang.String[])
//	 */
//	@Override
//	public void deleteClasses(String packageId, String[] classId) {
//		if(logger.isDebugEnabled()) logger.debug(String.format("删除机构用户［%1$s］班级集合 %2$s ...", packageId, Arrays.toString(classId)));
//		if(StringUtils.isEmpty(packageId) || classId == null || classId.length == 0) return;
//		Package pack = this.packageDao.load(Package.class, packageId);
//		if(pack == null) throw new RuntimeException(String.format("套餐[%d]不存在",packageId));
//		if(pack.getClasses() == null || pack.getClasses().size() == 0) return;
//		List<ClassPlan> removeClasses = new ArrayList<>();
//		for(ClassPlan classPlan : pack.getClasses()){
//			if(classPlan == null) continue;
//			if(Arrays.binarySearch(classId, classPlan.getId()) > -1){
//				removeClasses.add(classPlan);
//			}
//		}
//		if(removeClasses.size() > 0) pack.getClasses().removeAll(removeClasses);
//	}
}