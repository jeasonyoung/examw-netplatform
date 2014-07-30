package com.examw.netplatform.service.admin.courses.impl;

import java.util.Date;
import java.util.List; 
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.courses.IClassPlanDao;
import com.examw.netplatform.dao.admin.courses.ILessonDao;
import com.examw.netplatform.dao.admin.papers.IPaperDao;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.domain.admin.papers.Paper;
import com.examw.netplatform.model.admin.courses.LessonInfo;
import com.examw.netplatform.service.admin.courses.ILessonService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;

/**
 * 课时资源服务接口实现类
 * @author fengwei.
 * @since 2014年5月22日 下午1:45:13.
 */
public class LessonServiceImpl extends BaseDataServiceImpl<Lesson, LessonInfo> implements ILessonService{
	private ILessonDao lessonDao;
	private IClassPlanDao classPlanDao;
	private IPaperDao paperDao;
	private Map<Integer, String> videoModeMap;
	/**
	 * 设置课时资源数据接口。
	 * @param lessonDao
	 * 课时资源数据接口。
	 */
	public void setLessonDao(ILessonDao lessonDao) {
		this.lessonDao = lessonDao;
	}
	/**
	 * 设置班级数据接口。
	 * @param classPlanDao
	 * 班级数据接口。
	 */
	public void setClassPlanDao(IClassPlanDao classPlanDao) {
		this.classPlanDao = classPlanDao;
	}
	/**
	 * 设置随堂练习数据接口。
	 * @param paperDao
	 * 随堂练习数据接口。
	 */
	public void setPaperDao(IPaperDao paperDao) {
		this.paperDao = paperDao;
	}
	/**
	 * 设置视频模式集合。
	 * @param videoModeMap
	 */
	public void setVideoModeMap(Map<Integer, String> videoModeMap) {
		this.videoModeMap = videoModeMap;
	}
	/*
	 * 加载视频模式名称。
	 * @see com.examw.netplatform.service.admin.courses.ILessonService#loadFreeVideoName(java.lang.Integer)
	 */
	@Override
	public String loadVideoModeName(Integer freeVideoMode) {
		if(freeVideoMode == null || this.videoModeMap == null || this.videoModeMap.size() == 0) return null;
		return this.videoModeMap.get(freeVideoMode);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Lesson> find(LessonInfo info) {
		return this.lessonDao.findLessons(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected LessonInfo changeModel(Lesson data) {
		if(data == null) return null;
		LessonInfo info = new LessonInfo();
		BeanUtils.copyProperties(data,info);
		if(data.getClassPlan() != null){
			info.setClassId(data.getClassPlan().getId());
			info.setClassName(data.getClassPlan().getName());
		}
		if(data.getTestPaper() != null){
			info.setTestPaperId(data.getTestPaper().getId());
			info.setTestPaperName(data.getTestPaper().getName());
		}
		info.setVideoModeName(this.loadVideoModeName(data.getVideoMode()));
		return info;
	}
	/*
	 * 数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(LessonInfo info) {
		return this.lessonDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public LessonInfo update(LessonInfo info) {
		if(info == null) return null;
		boolean isAdded = false;
		Lesson data = StringUtils.isEmpty(info.getId()) ?  null : this.lessonDao.load(Lesson.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new Lesson();
			info.setCreateTime(new Date());
		}
		if(!isAdded) info.setCreateTime(data.getCreateTime());
		info.setLastTime(new Date());
		BeanUtils.copyProperties(info, data);
		if(!StringUtils.isEmpty(info.getClassId()) && (data.getClassPlan() == null || !info.getClassId().equalsIgnoreCase(data.getClassPlan().getId()))){
			ClassPlan plan = this.classPlanDao.load(ClassPlan.class, info.getClassId());
			if(plan != null) data.setClassPlan(plan);
		}
		if(!StringUtils.isEmpty(info.getTestPaperId()) && (data.getTestPaper() == null || !info.getTestPaperId().equalsIgnoreCase(data.getTestPaper().getId()))){
			Paper testPaper = this.paperDao.load(Paper.class, info.getTestPaperId());
			if(testPaper != null) data.setTestPaper(testPaper);
		}
		if(isAdded)this.lessonDao.save(data);
		info.setVideoModeName(this.loadVideoModeName(data.getVideoMode()));
		if(data.getClassPlan() != null){
			info.setClassId(data.getClassPlan().getId());
			info.setClassName(data.getClassPlan().getName());
		}
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
			Lesson lesson = this.lessonDao.load(Lesson.class, ids[i]);
			if(lesson != null){
				this.lessonDao.delete(lesson);
			}
		}
	}
}