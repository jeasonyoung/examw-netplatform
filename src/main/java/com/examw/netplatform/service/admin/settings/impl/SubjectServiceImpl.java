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
import com.examw.netplatform.dao.admin.settings.SubjectMapper;
import com.examw.netplatform.domain.admin.settings.Area;
import com.examw.netplatform.domain.admin.settings.Subject;
import com.examw.netplatform.model.admin.settings.AreaInfo;
import com.examw.netplatform.model.admin.settings.SubjectInfo;
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
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getOrder()) + " " + StringUtils.trimToEmpty(info.getSort()));
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
	/*
	 * 数据类型转换。
	 * @see com.examw.netplatform.service.admin.settings.ISubjectService#conversion(com.examw.netplatform.domain.admin.settings.Subject)
	 */
	@Override
	public SubjectInfo conversion(Subject subject) {
		logger.debug("数据类型Subject -> SubjectInfo ...");
		SubjectInfo info = (SubjectInfo)subject;
		info.setStatusName(this.loadStatusName(subject.getStatus()));
		return info;
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
	 * 加载科目地区。
	 * @see com.examw.netplatform.service.admin.settings.ISubjectService#loadSubjectAreas(java.lang.String)
	 */
	@Override
	public List<AreaInfo> loadSubjectAreas(String subjectId) {
		logger.debug("加载科目["+subjectId+"]地区...");
		final List<AreaInfo> list = new ArrayList<AreaInfo>();
		final List<Area> areas = this.areaDao.findAreasBySubject(subjectId);
		if(areas != null && areas.size() > 0){
			for(Area area : areas){
				list.add((AreaInfo)area);
			}
		}
		return list;
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
		}
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
				if(StringUtils.isBlank(id)) continue;
				this.subjectDao.deleteSubject(id);
			}
		}
	}
}