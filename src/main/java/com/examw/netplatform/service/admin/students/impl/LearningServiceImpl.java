package com.examw.netplatform.service.admin.students.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.students.ILearningDao;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.domain.admin.students.Learning;
import com.examw.netplatform.model.admin.students.LearningInfo;
import com.examw.netplatform.service.admin.students.ILearningService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;

/**
 * 进度服务接口实现类。
 * @author fengwei.
 * @since 2014年5月29日 上午11:47:36.
 */
public class LearningServiceImpl extends BaseDataServiceImpl<Learning, LearningInfo> implements ILearningService{
	private static final Logger logger = Logger.getLogger(LearningServiceImpl.class);
	private ILearningDao learningDao;
	/**
	 * 设置接口实现类。
	 * @param learningDao
	 * 接口实现类。
	 */
	public void setLearningDao(ILearningDao learningDao) {
		if(logger.isDebugEnabled()) logger.debug("设置接口实现类...");
		this.learningDao = learningDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Learning> find(LearningInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.learningDao.findLearnings(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected LearningInfo changeModel(Learning data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Learning => LearningInfo  ...");
		if(data == null ) return null;
		LearningInfo info = new LearningInfo();
		BeanUtils.copyProperties(data, info);
		//学员
		User user = null;
		if((user = data.getUser()) != null){
			info.setUserId(user.getId());
			info.setUserName(user.getName());
		}
		//机构
		Agency agency = null;
		if((agency = data.getAgency()) != null){
			info.setAgencyId(agency.getId());
			info.setAgencyName(agency.getName());
		}
		//课时
		Lesson lesson = null;
		if((lesson = data.getLesson()) != null){
			info.setLessonId(lesson.getId());
			info.setLessonName(lesson.getName());
			ClassPlan classPlan = null;
			if((classPlan = lesson.getClassPlan()) != null){
				info.setClassId(classPlan.getId());
				info.setClassName(classPlan.getName());
			}
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(LearningInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.learningDao.total(info);
	}
	/*
	 * 数据更新。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public LearningInfo update(LearningInfo info) {
		if(logger.isDebugEnabled()) logger.debug("数据更新...");
		boolean isAdded = false;
		Learning data = StringUtils.isEmpty(info.getId()) ? null : this.learningDao.load(Learning.class, info.getId());
		if(isAdded = (data ==  null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			data = new Learning();
		}else {
			info.setCreateTime(data.getCreateTime());
			if(info.getCreateTime() == null) info.setCreateTime(new Date());
		}
		BeanUtils.copyProperties(info, data);
		if(isAdded) this.learningDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s ...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			Learning data = this.learningDao.load(Learning.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s ...", ids[i]));
				this.learningDao.delete(data);
			}
		}
	}
}