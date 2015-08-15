package com.examw.netplatform.service.admin.teachers.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.courses.LessonMapper;
import com.examw.netplatform.dao.admin.security.UserMapper;
import com.examw.netplatform.dao.admin.settings.AgencyMapper;
import com.examw.netplatform.dao.admin.teachers.AnswerQuestionTopicMapper;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionTopic;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo;
import com.examw.netplatform.service.admin.teachers.IAnswerQuestionTopicService;
import com.examw.service.Status;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 教师答疑主题服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年11月19日
 */
public class AnswerQuestionTopicServiceImpl implements IAnswerQuestionTopicService {
	private static final Logger logger = Logger.getLogger(AnswerQuestionTopicServiceImpl.class);
	private AnswerQuestionTopicMapper answerQuestionTopicDao;
	private AgencyMapper agencyDao;
	private UserMapper userDao;
	private LessonMapper lessonDao;
	private Map<Integer, String> statusMap;
	/**
	 * 设置教师答疑主题数据接口。
	 * @param answerQuestionTopicDao 
	 *	  教师答疑主题数据接口。
	 */
	public void setAnswerQuestionTopicDao(AnswerQuestionTopicMapper answerQuestionTopicDao) {
		logger.debug("注入教师答疑主题数据接口...");
		this.answerQuestionTopicDao = answerQuestionTopicDao;
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
	 * 设置用户数据接口。
	 * @param userDao 
	 *	  用户数据接口。
	 */
	public void setUserDao(UserMapper userDao) {
		logger.debug("注入用户数据接口...");
		this.userDao = userDao;
	}
	/**
	 * 设置课时资源数据接口。
	 * @param lessonDao 
	 *	  课时资源数据接口。
	 */
	public void setLessonDao(LessonMapper lessonDao) {
		logger.debug("注入课时资源数据接口...");
		this.lessonDao = lessonDao;
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
	 * @see com.examw.netplatform.service.admin.teachers.IAnswerQuestionTopicService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		logger.debug(String.format("加载状态值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.teachers.IAnswerQuestionTopicService#datagrid(com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo)
	 */
	@Override
	public DataGrid<AnswerQuestionTopicInfo> datagrid(AnswerQuestionTopicInfo info) {
		logger.debug("查询数据...");
		//分页排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getOrder()) + " " + StringUtils.trimToEmpty(info.getSort()));
		//查询数据
		final List<AnswerQuestionTopic> list = this.answerQuestionTopicDao.findTopics(info);
		//分页信息
		final PageInfo<AnswerQuestionTopic> pageInfo = new PageInfo<AnswerQuestionTopic>(list);
		//初始化
		final DataGrid<AnswerQuestionTopicInfo> grid = new DataGrid<AnswerQuestionTopicInfo>();
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//批量类型转换
	private List<AnswerQuestionTopicInfo> changeModel(List<AnswerQuestionTopic> topics){
		final List<AnswerQuestionTopicInfo>  list = new ArrayList<AnswerQuestionTopicInfo>();
		if(topics != null && topics.size() > 0){
			for(AnswerQuestionTopic topic : topics){
				if(topic == null) continue;
				list.add(this.conversion(topic));
			}
		}
		return list;
	}
	/*
	 * 数据类型转换。
	 * @see com.examw.netplatform.service.admin.teachers.IAnswerQuestionTopicService#conversion(com.examw.netplatform.domain.admin.teachers.AnswerQuestionTopic)
	 */
	@Override
	public AnswerQuestionTopicInfo conversion(AnswerQuestionTopic data) {
		logger.debug("数据类型转换[AnswerQuestionTopic -> AnswerQuestionTopicInfo]...");
		AnswerQuestionTopicInfo info = (AnswerQuestionTopicInfo)data;
		info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.admin.teachers.IAnswerQuestionTopicService#update(com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo)
	 */
	@Override
	public AnswerQuestionTopicInfo update(AnswerQuestionTopicInfo info) {
		logger.debug("更新数据...");
		if(info == null) return null;
		//检查数据
		if(StringUtils.isBlank(info.getAgencyId()) || this.agencyDao.getAgency(info.getAgencyId()) == null){
			throw new RuntimeException("所属机构["+info.getAgencyId()+"]不存在!");
		}
		if(StringUtils.isBlank(info.getLessonId()) || this.lessonDao.getLesson(info.getLessonId()) == null){
			throw new RuntimeException("所属课程资源["+info.getLessonId()+"]不存在!");
		}
		if(StringUtils.isBlank(info.getStudentId()) || this.userDao.getUser(info.getStudentId()) == null){
			throw new RuntimeException("所属学员["+info.getStudentId()+"]不存在!");
		}
		//
		AnswerQuestionTopic data = StringUtils.isBlank(info.getId()) ? null : this.answerQuestionTopicDao.getTopic(info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			if(StringUtils.isBlank(info.getId()))
				info.setId(UUID.randomUUID().toString());
			
			data = new AnswerQuestionTopic();
		}
		//赋值
		BeanUtils.copyProperties(info, data);
		//保存
		if(isAdded){
			logger.debug("新增答疑主题...");
			this.answerQuestionTopicDao.insertTopic(data);
		}else {
			logger.debug("更新答疑主题...");
			this.answerQuestionTopicDao.updateTopic(data);
		}
		//返回
		return this.conversion(data);
	}
	/*
	 * 更新主题状态。
	 * @see com.examw.netplatform.service.admin.teachers.IAnswerQuestionTopicService#updateStatus(java.lang.String, com.examw.service.Status)
	 */
	@Override
	public void updateStatus(String topicId, Status status) {
		logger.debug("更新主题["+topicId+"]状态["+status+"]..");
		if(StringUtils.isBlank(topicId))return;
		final AnswerQuestionTopic data = this.answerQuestionTopicDao.getTopic(topicId);
		if(data != null){
			data.setStatus(status.getValue());
			this.answerQuestionTopicDao.updateTopic(data);
		}
	}
	/*
	 * 删除主题数据.
	 * @see com.examw.netplatform.service.admin.teachers.IAnswerQuestionTopicService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除主题数据..." + StringUtils.join(ids,","));
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id)) continue;
				this.answerQuestionTopicDao.deleteTopic(id);
			}
		}
	}
}