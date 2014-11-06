package com.examw.netplatform.service.admin.settings.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.settings.ICategoryDao;
import com.examw.netplatform.dao.admin.settings.IExamDao;
import com.examw.netplatform.domain.admin.settings.Area;
import com.examw.netplatform.domain.admin.settings.Category;
import com.examw.netplatform.domain.admin.settings.Exam;
import com.examw.netplatform.model.admin.settings.AreaInfo;
import com.examw.netplatform.model.admin.settings.ExamInfo;
import com.examw.netplatform.service.admin.settings.IAreaService;
import com.examw.netplatform.service.admin.settings.IExamService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
import com.examw.service.Status;

/**
 * 考试服务接口实现类
 * @author fengwei.
 * @since 2014年8月7日 上午10:00:32.
 */
public class ExamServiceImpl extends BaseDataServiceImpl<Exam, ExamInfo> implements IExamService {
	private static final Logger logger = Logger.getLogger(ExamServiceImpl.class);
	private IExamDao examDao;
	private ICategoryDao categoryDao;
	private IAreaService areaService;
	private Map<Integer, String> statusMap;
	/**
	 * 设置考试类别数据接口。
	 * @param categoryDao
	 * 考试类别数据接口。
	 */
	public void setCategoryDao(ICategoryDao categoryDao) {
		if(logger.isDebugEnabled()) logger.debug("注入考试类别数据接口...");
		this.categoryDao = categoryDao;
	}
	/**
	 * 设置考试类别数据接口。
	 * @param examDao
	 * 考试类别数据接口。
	 */
	public void setExamDao(IExamDao examDao) {
		if(logger.isDebugEnabled()) logger.debug("注入考试类别数据接口...");
		this.examDao = examDao;
	}
	/**
	 * 设置地区服务接口。
	 * @param areaService 
	 *	  地区服务接口。
	 */
	public void setAreaService(IAreaService areaService) {
		if(logger.isDebugEnabled()) logger.debug("注入地区服务接口...");
		this.areaService = areaService;
	}
	/**
	 * 设置考试状态值名集合。
	 * @param statusMap 
	 *	  考试状态值名集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("注入考试状态值名集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态名称。
	 * @see com.examw.test.service.settings.IExamService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载状态［%d］名称...", status));
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Exam> find(ExamInfo info) {
		if (logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.examDao.findExams(info);
	}
	/*
	 * 数据模型转换
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ExamInfo changeModel(Exam data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换  Exam => ExamInfo ...");
		if(data == null) return null;
		ExamInfo info = new ExamInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getCategory() != null){
			info.setCategoryId(data.getCategory().getId());
			info.setCategoryName(data.getCategory().getName());
		}
		info.setStatusName(this.loadStatusName(info.getStatus()));
		if(data.getAreas() != null){
			List<String> listAreaId = new ArrayList<>(), listAreaName = new ArrayList<>();
			for(Area area : data.getAreas()){
				if(area == null) continue;
				listAreaId.add(area.getId());
				listAreaName.add(area.getName());
			}
			info.setAreaId(listAreaId.toArray(new String[0]));
			info.setAreaName(listAreaName.toArray(new String[0]));
		}
		return info;
	}
	/*
	 * 类型转换。
	 * @see com.examw.test.service.settings.IExamService#conversion(com.examw.test.domain.settings.Exam)
	 */
	@Override
	public ExamInfo conversion(Exam exam) {
		if(logger.isDebugEnabled()) logger.debug("数据模型类型转换...");
		return this.changeModel(exam);
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ExamInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.examDao.total(info);		
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ExamInfo update(ExamInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Exam data = StringUtils.isEmpty(info.getId()) ? null : this.examDao.load(Exam.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Exam();
		}
		BeanUtils.copyProperties(info, data);
		//设置考试分类
		data.setCategory(StringUtils.isEmpty(info.getCategoryId()) ?  null : this.categoryDao.load(Category.class, info.getCategoryId()));
		//设置地区
		Set<Area> areas = null;
		if(info.getAreaId() != null && info.getAreaId().length > 0){
			areas = new HashSet<>();
			for(String areaId : info.getAreaId()){
				if(StringUtils.isEmpty(areaId)) continue;
				Area area = this.areaService.loadAreaById(areaId);
				if(area != null)areas.add(area);
			}
		}
		data.setAreas(areas);
		if(isAdded)this.examDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据," + ids);
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			Exam data = this.examDao.load(Exam.class, ids[i]);
			if(data != null){
				this.examDao.delete(data);
			}
		}
	}
	/*
	 * 加载最大的代码值。
	 * @see com.examw.test.service.settings.IExamService#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大的代码值...");
		return this.examDao.loadMaxCode();
	}
	/*
	 * 加载考试所在地区集合。
	 * @see com.examw.test.service.settings.IExamService#loadExamAreas(java.lang.String)
	 */
	@Override
	public List<AreaInfo> loadExamAreas(String examId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试［%s］所在地区集合...", examId));
		List<AreaInfo> list = new ArrayList<>();
		if(!StringUtils.isEmpty(examId)){
			Exam exam = this.examDao.load(Exam.class, examId);
			if(exam != null && exam.getAreas() != null && exam.getAreas().size() > 0){
				for(Area area : exam.getAreas()){
					AreaInfo  info = this.areaService.conversion(area);
					if(info != null){
						list.add(info);
					}
				}
				Collections.sort(list);
			}
		}
		return list;
	}
	/*
	 * 加载考试信息集合。
	 * @see com.examw.netplatform.service.admin.settings.IExamService#loadExams(java.lang.String, com.examw.service.Status)
	 */
	@Override
	public List<ExamInfo> loadExams(final String categoryId,final Status status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载［categoryId = %s］考试信息...", categoryId));
		return this.changeModel(this.examDao.findExams(new ExamInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getCategoryId() { return categoryId; }
			@Override
			public Integer getStatus() {return status == null ? null : status.getValue();}
			@Override
			public String getSort() { return "code";}
			@Override
			public String getOrder() {return "asc";}
		}));
	}
	/*
	 * 加载考试信息。
	 * @see com.examw.test.service.settings.IExamService#loadExam(java.lang.String)
	 */
	@Override
	public ExamInfo loadExam(String examId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试［examId = %s］信息...", examId));
		if(StringUtils.isEmpty(examId)) return null;
		return this.changeModel(this.examDao.load(Exam.class, examId));
	}
}