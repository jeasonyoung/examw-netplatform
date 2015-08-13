package com.examw.netplatform.service.admin.courses.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.courses.IClassPlanDao;
import com.examw.netplatform.dao.admin.courses.ILessonDao;
import com.examw.netplatform.dao.admin.settings.ChapterMapper;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.domain.admin.settings.Chapter;
import com.examw.netplatform.domain.admin.settings.Subject;
import com.examw.netplatform.model.admin.courses.LessonInfo;
import com.examw.netplatform.service.admin.courses.ILessonService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;

/**
 * 课时资源服务接口实现类
 * @author fengwei.
 * @since 2014年5月22日 下午1:45:13.
 */
public class LessonServiceImpl extends BaseDataServiceImpl<Lesson, LessonInfo> implements ILessonService{
	private static final Logger logger = Logger.getLogger(LessonServiceImpl.class);
	private ILessonDao lessonDao;
	private IClassPlanDao classPlanDao;
	private ChapterMapper chapterDao;
	private Map<Integer, String> handoutModeMap,videoModeMap;
	/**
	 * 设置课时资源数据接口。
	 * @param lessonDao 
	 *	  课时资源数据接口。
	 */
	public void setLessonDao(ILessonDao lessonDao) {
		if(logger.isDebugEnabled()) logger.debug("注入课时资源数据接口...");
		this.lessonDao = lessonDao;
	}
	/**
	 * 设置班级数据接口。
	 * @param classPlanDao 
	 *	  班级数据接口。
	 */
	public void setClassPlanDao(IClassPlanDao classPlanDao) {
		if(logger.isDebugEnabled()) logger.debug("注入班级数据接口...");
		this.classPlanDao = classPlanDao;
	}
	/**
	 * 设置章节数据接口。
	 * @param chapterDao 
	 *	  章节数据接口。
	 */
	public void setChapterDao(ChapterMapper chapterDao) {
		if(logger.isDebugEnabled()) logger.debug("注入章节数据接口...");
		this.chapterDao = chapterDao;
	}
	/**
	 * 设置讲义模式值名称集合。
	 * @param handoutModeMap 
	 *	  讲义模式值名称集合。
	 */
	public void setHandoutModeMap(Map<Integer, String> handoutModeMap) {
		if(logger.isDebugEnabled()) logger.debug("注入讲义模式值名称集合...");
		this.handoutModeMap = handoutModeMap;
	}
	/**
	 * 设置视频模式值名称集合。
	 * @param videoModeMap 
	 *	  视频模式值名称集合。
	 */
	public void setVideoModeMap(Map<Integer, String> videoModeMap) {
		if(logger.isDebugEnabled()) logger.debug("注入视频模式值名称集合...");
		this.videoModeMap = videoModeMap;
	}
	/*
	 * 加载讲义模式值名称。
	 * @see com.examw.netplatform.service.admin.courses.ILessonService#loadHandoutModeName(java.lang.Integer)
	 */
	@Override
	public String loadHandoutModeName(Integer handoutMode) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载讲义模式值［%d］名称...", handoutMode));
		if(handoutMode == null || this.handoutModeMap == null || this.handoutModeMap.size() == 0) return null;
		return this.handoutModeMap.get(handoutMode);
	}
	/*
	 * 加载试听模式值名称。
	 * @see com.examw.netplatform.service.admin.courses.ILessonService#loadVideoModeName(java.lang.Integer)
	 */
	@Override
	public String loadVideoModeName(Integer videoMode) {
		if(logger.isDebugEnabled()) logger.debug(String.format("", videoMode));
		if(videoMode == null || this.videoModeMap == null || this.videoModeMap.size() == 0) return null;
		return this.videoModeMap.get(videoMode);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Lesson> find(LessonInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.lessonDao.findLessons(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected LessonInfo changeModel(Lesson data) {
		return this.changeModel(data, true);
	}
	//数据模型转换。
	private  LessonInfo changeModel(Lesson data,boolean isAll){
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Lesson => LessonInfo ...");
		if(data == null) return null;
		LessonInfo info = new LessonInfo();
		BeanUtils.copyProperties(data, info);
		ClassPlan classPlan = null;
		if((classPlan = data.getClassPlan()) != null){
			info.setClassId(classPlan.getId());
			info.setClassName(classPlan.getName());
			Subject subject = null;
			if((subject = classPlan.getSubject()) != null){
				info.setSubjectId(subject.getId());
			}
		}
		info.setHandoutModeName(this.loadHandoutModeName(info.getHandoutMode()));
		info.setVideoModeName(this.loadVideoModeName(info.getVideoMode()));
		if(isAll && data.getChapters() != null && data.getChapters().size() > 0){
			List<String> chapterIdList = new ArrayList<>(),chapterNameList = new ArrayList<>();
			for(Chapter chapter : data.getChapters()){
				if(chapter == null) continue;
				chapterIdList.add(chapter.getId());
				chapterNameList.add(chapter.getName());
			}
			info.setChapterId(chapterIdList.toArray(new String[0]));
			info.setChapterName(chapterNameList.toArray(new String[0]));
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(LessonInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.lessonDao.total(info);
	}
	/*
	 * 加载班级下的课时资源集合。
	 * @see com.examw.netplatform.service.admin.courses.ILessonService#loadLessons(java.lang.String)
	 */
	@Override
	public List<LessonInfo> loadLessons(final String classId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载班级［%s］下的课时资源集合", classId));
		return this.changeModel(this.lessonDao.findLessons(new LessonInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getClassId() { return classId; }
		}));
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public LessonInfo update(LessonInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Lesson data = StringUtils.isEmpty(info.getId()) ? null : this.lessonDao.load(Lesson.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			data = new Lesson();
		}else {
			info.setCreateTime(data.getCreateTime());
			if(info.getCreateTime() == null) info.setCreateTime(new Date());
		}
		info.setLastTime(new Date());
		BeanUtils.copyProperties(info, data);
		
		if(StringUtils.isEmpty(info.getClassId())) throw new RuntimeException("所属班级ID为空！");
		ClassPlan classPlan = this.classPlanDao.load(ClassPlan.class, info.getClassId());
		if(classPlan == null) throw new RuntimeException(String.format("班级［%s］不存在！", info.getClassId()));
		data.setClassPlan(classPlan);
		
		Set<Chapter> chapters = null;
		if(info.getChapterId() != null && info.getChapterId().length > 0){
			chapters = new HashSet<>();
			 for(String id : info.getChapterId()){
				 if(StringUtils.isEmpty(id)) continue;
				 Chapter chapter = this.chapterDao.load(Chapter.class, id);
				 if(chapter != null){
					 chapters.add(chapter);
				 }
			 }
		}
		data.setChapters(chapters);
		
		if(isAdded) this.lessonDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据 %s...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Lesson lesson = this.lessonDao.load(Lesson.class, ids[i]);
			if(lesson != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s", ids[i]));
				this.lessonDao.delete(lesson);
			}
		}
	}
	/*
	 * 加载班级下的最大排序号。
	 * @see com.examw.netplatform.service.admin.courses.ILessonService#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String classId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载班级［%s］下的最大排序号...", classId));
		return this.lessonDao.loadMaxOrder(classId);
	}
	/*
	 * 数据模型转换
	 */
	@Override
	public LessonInfo conversion(Lesson lesson) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Lesson => LessonInfo ...");
		return this.changeModel(lesson);
	}
}