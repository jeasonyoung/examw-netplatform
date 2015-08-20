package com.examw.netplatform.service.admin.settings.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.settings.CategoryMapper;
import com.examw.netplatform.dao.admin.settings.ExamMapper;
import com.examw.netplatform.dao.admin.settings.SubjectMapper;
import com.examw.netplatform.domain.admin.settings.Area;
import com.examw.netplatform.domain.admin.settings.Category;
import com.examw.netplatform.domain.admin.settings.Exam;
import com.examw.netplatform.model.admin.settings.AreaInfo;
import com.examw.netplatform.model.admin.settings.ExamInfo;
import com.examw.netplatform.service.admin.settings.IAreaService;
import com.examw.netplatform.service.admin.settings.IExamService;
import com.examw.service.Status;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 考试服务接口实现类
 * @author fengwei.
 * @since 2014年8月7日 上午10:00:32.
 */
public class ExamServiceImpl implements IExamService {
	private static final Logger logger = Logger.getLogger(ExamServiceImpl.class);
	private ExamMapper examDao;
	private SubjectMapper subjectDao;
	private CategoryMapper categoryDao;
	private IAreaService areaService;
	private Map<Integer, String> statusMap;
	/**
	 * 设置考试类别数据接口。
	 * @param examDao
	 * 考试类别数据接口。
	 */
	public void setExamDao(ExamMapper examDao) {
		logger.debug("注入考试类别数据接口...");
		this.examDao = examDao;
	}
	/**
	 * 设置科目数据接口。
	 * @param subjectDao 
	 *	  科目数据接口。
	 */
	public void setSubjectDao(SubjectMapper subjectDao) {
		logger.debug("注入科目数据接口...");
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置考试分类数据接口。
	 * @param categoryDao 
	 *	  考试分类数据接口。
	 */
	public void setCategoryDao(CategoryMapper categoryDao) {
		logger.debug("注入考试分类数据接口...");
		this.categoryDao = categoryDao;
	}
	/**
	 * 设置地区服务接口。
	 * @param areaService 
	 *	  地区服务接口。
	 */
	public void setAreaService(IAreaService areaService) {
		logger.debug("注入地区服务接口...");
		this.areaService = areaService;
	}
	/**
	 * 设置考试状态值名集合。
	 * @param statusMap 
	 *	  考试状态值名集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		logger.debug("注入考试状态值名集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态名称。
	 * @see com.examw.test.service.settings.IExamService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		logger.debug(String.format("加载状态［%d］名称...", status));
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.service.IDataService#datagrid(java.lang.Object)
	 */
	@Override
	public DataGrid<ExamInfo> datagrid(ExamInfo info) {
		logger.debug("查询数据...");
		//分页排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getSort()) + " " + StringUtils.trimToEmpty(info.getOrder()));
		//查询数据
		final List<Exam> list = this.examDao.findExams(info);
		//分页信息
		final PageInfo<Exam> pageInfo = new PageInfo<Exam>(list);
		//初始化
		final DataGrid<ExamInfo> grid = new DataGrid<ExamInfo>();
		//设置数据
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//类型转换
	private List<ExamInfo> changeModel(List<Exam> exams){
		final List<ExamInfo> list = new ArrayList<ExamInfo>();
		if(exams != null && exams.size() > 0){
			for(Exam exam : exams){
				if(exam == null) continue;
				list.add(this.conversion(exam));
			}
		}
		return list;
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.admin.settings.IExamService#conversion(com.examw.netplatform.domain.admin.settings.Exam)
	 */
	@Override
	public ExamInfo conversion(Exam data) {
		logger.debug("类型转换[Exam -> ExamInfo]...");
		if(data != null){
			final ExamInfo info = new ExamInfo();
			BeanUtils.copyProperties(data, info);
			info.setStatusName(this.loadStatusName(info.getStatus()));
			
			//考试分类
			if(StringUtils.isNotBlank(info.getCategoryId()) && StringUtils.isBlank(info.getCategoryName())){
				final Category category = this.categoryDao.getCategory(info.getCategoryId());
				if(category != null) info.setCategoryName(category.getName());
			}
			
			//考试地区
			final List<String> idList = new ArrayList<String>(), nameList = new ArrayList<String>();
			final List<AreaInfo> areas = this.areaService.loadAreasByExam(info.getId());
			if(areas != null && areas.size() > 0){
				for(AreaInfo areaInfo : areas){
					if(areaInfo == null) continue;
					idList.add(areaInfo.getId());
					nameList.add(areaInfo.getName());
				}
			}
			info.setAreaIds(idList.toArray(new String[0]));
			info.setAreaNames(nameList.toArray(new String[0]));
			//
			return info;
		}
		return null;
	}
	/*
	 * 加载最大的代码值。
	 * @see com.examw.netplatform.service.admin.settings.IExamService#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		logger.debug("加载最大的代码值...");
		return this.examDao.loadMaxCode();
	}
	/*
	 * 加载考试所属地区集合。
	 * @see com.examw.netplatform.service.admin.settings.IExamService#loadExamAreas(java.lang.String)
	 */
	@Override
	public List<AreaInfo> loadExamAreas(String examId) {
		logger.debug("加载考试["+examId+"]所属地区集合...");
		return this.areaService.loadAreasByExam(examId);
	}
	/*
	 * 加载分类状态下的考试集合
	 * @see com.examw.netplatform.service.admin.settings.IExamService#loadExams(java.lang.String, com.examw.service.Status)
	 */
	@Override
	public List<ExamInfo> loadExams(String categoryId, Status status) {
		logger.debug("加载分类["+categoryId+"]状态["+status+"]下的考试集合...");
		return this.changeModel(this.examDao.findExamsByCategory(categoryId, status == null ? null : status.getValue()));
	}
	/*
	 * 加载考试数据。
	 * @see com.examw.netplatform.service.admin.settings.IExamService#loadExam(java.lang.String)
	 */
	@Override
	public ExamInfo loadExam(String examId) {
		logger.debug("加载考试数据..." + examId);
		return this.conversion(this.examDao.getExam(examId));
	}
	/*
	 * 更新数据。
	 * @see com.examw.service.IDataService#update(java.lang.Object)
	 */
	@Override
	public ExamInfo update(ExamInfo info) {
		logger.debug("更新数据...");
		if(info == null) return null;
		Exam data = StringUtils.isBlank(info.getId()) ? null : this.examDao.getExam(info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			if(StringUtils.isBlank(info.getId()))
				info.setId(UUID.randomUUID().toString());
			//检查唯一性
			if(this.examDao.hasExamCode(info.getCode())){
				throw new RuntimeException("考试代码["+info.getCode()+"]已存在!");
			}
			if(this.examDao.hasExamAbbr(info.getAbbr())){
				throw new RuntimeException("考试EN简称["+info.getAbbr()+"]已存在!");
			}
			//初始化
			data = new Exam();
		}
		//赋值
		BeanUtils.copyProperties(info, data);
		//保存
		if(isAdded){
			logger.debug("新增考试...");
			this.examDao.insertExam(data);
		}else {
			logger.debug("更新考试...");
			this.examDao.updateExam(data);
			//删除原有地区
			this.examDao.deleteExamAreas(data.getId());
		}
		//检查地区
		if(data.getAreaIds() != null && data.getAreaIds().length > 0){
			for(String areaId : data.getAreaIds()){
				if(StringUtils.isBlank(areaId)) continue;
				//检查地区
				final Area area = this.areaService.loadAreaById(areaId);
				if(area != null){
					this.examDao.insertExamArea(data.getId(), areaId);
				}
			}
		}
		//返回
		return this.conversion(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.service.IDataService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除数据..." + StringUtils.join(ids,","));
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id)) continue;
				//是否存在科目
				if(!this.subjectDao.hasSubjectByExam(id)){
					//删除考试地区
					this.examDao.deleteExamAreas(id);
					//删除考试
					this.examDao.deleteExam(id);
				}
			}
		}
	}
}