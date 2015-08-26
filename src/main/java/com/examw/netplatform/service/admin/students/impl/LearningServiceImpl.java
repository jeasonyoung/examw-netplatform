package com.examw.netplatform.service.admin.students.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.courses.LessonMapper;
import com.examw.netplatform.dao.admin.security.UserMapper;
import com.examw.netplatform.dao.admin.settings.AgencyMapper;
import com.examw.netplatform.dao.admin.students.LearningMapper;
import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.domain.admin.students.Learning;
import com.examw.netplatform.model.admin.students.LearningInfo;
import com.examw.netplatform.service.admin.students.ILearningService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 进度服务接口实现类。
 * @author fengwei.
 * @since 2014年5月29日 上午11:47:36.
 */
public class LearningServiceImpl implements ILearningService{
	private static final Logger logger = Logger.getLogger(LearningServiceImpl.class);
	private LearningMapper learningDao;
	private LessonMapper lessonDao;
	private UserMapper userDao;
	private AgencyMapper agencyDao;
	private Map<Integer, String> statusMap;
	/**
	 * 设置进度数据接口。
	 * @param learningDao
	 * 进度数据接口。
	 */
	public void setLearningDao(LearningMapper learningDao) {
		logger.debug("注入进度数据接口...");
		this.learningDao = learningDao;
	}
	/**
	 * 设置机构数据接口。
	 * @param agencyDao 
	 *	  机构数据接口。
	 */
	public void setAgencyDao(AgencyMapper agencyDao) {
		logger.debug("注入机构数据接口...");
		this.agencyDao = agencyDao;
	}
	/**
	 * 设置课程资源数据接口。
	 * @param lessonDao 
	 *	  课程资源数据接口。
	 */
	public void setLessonDao(LessonMapper lessonDao) {
		logger.debug("注入课程资源数据接口...");
		this.lessonDao = lessonDao;
	}
	/**
	 * 设置学员数据接口。
	 * @param userDao 
	 *	  学员数据接口。
	 */
	public void setUserDao(UserMapper userDao) {
		logger.debug("注入学员数据接口...");
		this.userDao = userDao;
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
	 * @see com.examw.netplatform.service.admin.students.ILearningService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		logger.debug(String.format("加载状态值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.students.ILearningService#datagrid(com.examw.netplatform.model.admin.students.LearningInfo)
	 */
	@Override
	public DataGrid<LearningInfo> datagrid(LearningInfo info) {
		logger.debug("查询数据...");
		//分页排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getSort()) + " " + StringUtils.trimToEmpty(info.getOrder()));
		//查询数据
		final List<Learning> list = this.learningDao.findLearnings(info);
		//分页信息
		final PageInfo<Learning> pageInfo = new PageInfo<Learning>(list);
		//初始化
		final DataGrid<LearningInfo> grid = new DataGrid<LearningInfo>();
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//批量类型转换
	private List<LearningInfo> changeModel(List<Learning> learnings){
		final List<LearningInfo> list = new ArrayList<LearningInfo>();
		if(learnings != null && learnings.size() > 0){
			for(Learning learning : learnings){
				if(learning == null) continue;
				list.add(this.conversion(learning));
			}
		}
		return list;
	}
	//数据类型转换
	private LearningInfo conversion(Learning data){
		if(data != null){
			final LearningInfo info = new LearningInfo();
			BeanUtils.copyProperties(data, info);
			//机构
			if(StringUtils.isNotBlank(info.getAgencyId()) && StringUtils.isBlank(info.getAgencyName())){
				final Agency agency = this.agencyDao.getAgency(info.getAgencyId());
				if(agency != null) info.setAgencyName(agency.getName());
			}
			//课程资源
			if(StringUtils.isNotBlank(info.getLessonId()) && StringUtils.isBlank(info.getLessonName())){
				final Lesson lesson = this.lessonDao.getLesson(info.getLessonId());
				if(lesson != null) info.setLessonName(lesson.getName());
			}
			//学员
			if(StringUtils.isNotBlank(info.getStudentId()) && StringUtils.isBlank(info.getStudentName())){
				final User user = this.userDao.getUser(info.getStudentId());
				if(user != null) info.setStudentName(user.getName());
			}
			//状态
			info.setStatusName(this.loadStatusName(data.getStatus()));
			//
			return info;
		}
		return null;
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.admin.students.ILearningService#update(com.examw.netplatform.model.admin.students.LearningInfo)
	 */
	@Override
	public LearningInfo update(LearningInfo info) {
		logger.debug("更新数据...");
		if(info == null) return null;
		//学员/课时资源
		if(StringUtils.isBlank(info.getStudentId()) || StringUtils.isBlank(info.getLessonId())){
			throw new RuntimeException("学员ID或课程资源ID!");
		}
		//机构
		if(StringUtils.isBlank(info.getAgencyId()) || this.agencyDao.getAgency(info.getAgencyId()) == null){
			throw new RuntimeException("机构ID为空或不存在!");
		}
		//
		Learning data = this.learningDao.getLearning(info.getStudentId(), info.getLessonId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			data = new Learning();
		}
		BeanUtils.copyProperties(info, data);
		//
		if(isAdded){
			logger.debug("新增学习进度...");
			this.learningDao.insertLearning(data);
		}else {
			logger.debug("更新学习进度");
			this.learningDao.updateLearning(data);
		}
		return this.conversion(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.admin.students.ILearningService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除数据..." + StringUtils.join(ids,","));
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id)) continue;
				final String[] array = id.split("#");
				if(array != null && array.length == 2){
					this.learningDao.deleteLearning(array[0], array[1]);
				}
			}
		}
	}
}