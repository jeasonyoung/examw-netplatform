package com.examw.netplatform.service.admin.settings.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.settings.AreaMapper;
import com.examw.netplatform.dao.admin.settings.ChapterMapper;
import com.examw.netplatform.dao.admin.settings.SubjectMapper;
import com.examw.netplatform.domain.admin.settings.Area;
import com.examw.netplatform.domain.admin.settings.Exam;
import com.examw.netplatform.domain.admin.settings.ExamSubjectView;
import com.examw.netplatform.domain.admin.settings.Subject;
import com.examw.netplatform.model.admin.settings.SubjectInfo;
import com.examw.netplatform.service.admin.settings.IExamService;
import com.examw.netplatform.service.admin.settings.ISubjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 科目服务接口实现类
 * @author fengwei.
 * @since 2014年8月7日 上午10:27:40.
 */
public class SubjectServiceImpl implements ISubjectService {
	private static final Logger logger = Logger.getLogger(SubjectServiceImpl.class);
	private SubjectMapper subjectDao;
	private AreaMapper areaDao;
	private ChapterMapper chapterDao;
	private IExamService examService;
	private Map<Integer, String> statusMap;
	/**
	 * 设置科目数据接口。
	 * @param subjectDao
	 * 科目数据接口。
	 */
	public void setSubjectDao(SubjectMapper subjectDao) {
		logger.debug("注入科目数据接口...");
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置地区数据接口。
	 * @param areaDao 
	 *	  地区数据接口。
	 */
	public void setAreaDao(AreaMapper areaDao) {
		logger.debug("注入地区数据接口...");
		this.areaDao = areaDao;
	}
	/**
	 * 设置章节数据接口。
	 * @param chapterDao 
	 *	  章节数据接口。
	 */
	public void setChapterDao(ChapterMapper chapterDao) {
		logger.debug("注入章节数据接口...");
		this.chapterDao = chapterDao;
	}
	/**
	 * 设置考试服务接口。
	 * @param examService 
	 *	  考试服务接口。
	 */
	public void setExamService(IExamService examService) {
		logger.debug("注入考试服务接口...");
		this.examService = examService;
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
	 * @see com.examw.netplatform.service.admin.settings.ISubjectService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		logger.debug(String.format("加载状态值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 加载最大代码。
	 * @see com.examw.test.service.settings.ISubjectService#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		logger.debug("加载最大代码值...");
		return this.subjectDao.loadMaxCode();
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.settings.ISubjectService#datagrid(com.examw.netplatform.model.admin.settings.SubjectInfo)
	 */
	@Override
	public DataGrid<SubjectInfo> datagrid(SubjectInfo info) {
		logger.debug("查询数据...");
		//分页/排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getSort()) + " " + StringUtils.trimToEmpty(info.getOrder()));
		//查询数据
		final List<Subject> list = this.subjectDao.findSubjects(info);
		//分页信息
		final PageInfo<Subject> pageInfo = new PageInfo<Subject>(list);
		//初始化
		final DataGrid<SubjectInfo> grid = new DataGrid<SubjectInfo>();
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//类型转换
	private List<SubjectInfo> changeModel(List<Subject> subjects){
		final List<SubjectInfo> list = new ArrayList<SubjectInfo>();
		if(subjects != null && subjects.size() > 0){
			for(Subject subject : subjects){
				if(subject == null) continue;
				list.add(this.conversion(subject));
			}
		}
		return list;
	}
	//数据类型转换
	private SubjectInfo conversion(Subject data) {
		logger.debug("数据类型Subject -> SubjectInfo ...");
		if(data != null){
			final SubjectInfo info = new SubjectInfo();
			BeanUtils.copyProperties(data, info);
			info.setStatusName(this.loadStatusName(info.getStatus()));
			//考试
			if(StringUtils.isNotBlank(info.getExamId()) && StringUtils.isBlank(info.getExamName())){
				final Exam exam = this.examService.loadExam(info.getExamId());
				if(exam != null) info.setExamName(exam.getName());
			}
			//地区
			final List<String> idList = new ArrayList<String>(), nameList = new ArrayList<String>();
			final List<Area> areas = this.areaDao.findAreasBySubject(info.getId());
			if(areas != null && areas.size() > 0){
				for(Area area : areas){
					if(area == null) continue;
					idList.add(area.getId());
					nameList.add(area.getName());
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
	 * 加载考试科目数据。
	 * @see com.examw.netplatform.service.admin.settings.ISubjectService#loadAllSubjects(java.lang.String)
	 */
	@Override
	public List<SubjectInfo> loadAllSubjects(String examId) {
		logger.debug("加载考试["+examId+"]科目...");
		return this.changeModel(this.subjectDao.findSubjectsByExam(examId));
	}
	/*
	 * 加载考试科目树视图数据集合。
	 * @see com.examw.netplatform.service.admin.settings.ISubjectService#findExamSubjectViews()
	 */
	@Override
	public List<ExamSubjectView> findExamSubjectViews() {
		logger.debug("加载考试科目树视图数据集合...");
		return this.subjectDao.findExamSubjectViews();
	}
	/*
	 * 更新科目。
	 * @see com.examw.netplatform.service.admin.settings.ISubjectService#update(com.examw.netplatform.model.admin.settings.SubjectInfo)
	 */
	@Override
	public SubjectInfo update(SubjectInfo info) {
		logger.debug("更新科目...");
		if(info == null) return null;
		boolean isAdded = false;
		//检查考试
		if(StringUtils.isBlank(info.getExamId()) || this.examService.loadExam(info.getExamId()) == null){
			throw new RuntimeException("科目所属考试["+info.getExamId()+"]不存在!");
		}
		//
		Subject data = StringUtils.isBlank(info.getId()) ? null : this.subjectDao.getSubject(info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId()))
				info.setId(UUID.randomUUID().toString());
			//检查唯一性
			if(this.subjectDao.hasSubjectCode(info.getCode())){
				throw new RuntimeException("科目代码["+info.getCode()+"]已存在!");
			}
			//
			data = new Subject();
		}
		//
		BeanUtils.copyProperties(info, data);
		//保存
		if(isAdded){
			logger.debug("新增科目...");
			this.subjectDao.insertSubject(data);
		}else {
			logger.debug("更新科目...");
			this.subjectDao.updateSubject(data);
			//删除科目地区
			this.subjectDao.deleteSubjectAreas(data.getId());
		}
		//添加地区
		if(data.getAreaIds() != null && data.getAreaIds().length > 0){
			for(String areaId : data.getAreaIds()){
				if(StringUtils.isBlank(areaId) || this.areaDao.getArea(areaId) == null) continue;
				this.subjectDao.insertSubjectArea(data.getId(), areaId);
			}
		}
		//
		return this.conversion(data);
	}
	/*
	 * 删除科目数据。
	 * @see com.examw.netplatform.service.admin.settings.ISubjectService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除科目..." + StringUtils.join(ids,","));
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id) || this.chapterDao.hasChaptersBySubject(id)) continue;
				//删除科目地区
				this.subjectDao.deleteSubjectAreas(id);
				//删除科目
				this.subjectDao.deleteSubject(id);
			}
		}
	}
}