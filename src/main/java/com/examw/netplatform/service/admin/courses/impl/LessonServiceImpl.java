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
import com.examw.netplatform.dao.admin.courses.LessonMapper;
import com.examw.netplatform.dao.admin.settings.ChapterMapper;
import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.model.admin.courses.LessonInfo;
import com.examw.netplatform.service.admin.courses.ILessonService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 课时资源服务接口实现类
 * @author fengwei.
 * @since 2014年5月22日 下午1:45:13.
 */
public class LessonServiceImpl implements ILessonService{
	private static final Logger logger = Logger.getLogger(LessonServiceImpl.class);
	private LessonMapper lessonDao;
	private ClassMapper classPlanDao;
	private ChapterMapper chapterDao;
	private Map<Integer, String> handoutModeMap,videoModeMap;
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
	 * 设置班级数据接口。
	 * @param classPlanDao 
	 *	  班级数据接口。
	 */
	public void setClassPlanDao(ClassMapper classPlanDao) {
		logger.debug("注入班级数据接口...");
		this.classPlanDao = classPlanDao;
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
	 * 设置讲义模式值名称集合。
	 * @param handoutModeMap 
	 *	  讲义模式值名称集合。
	 */
	public void setHandoutModeMap(Map<Integer, String> handoutModeMap) {
		logger.debug("注入讲义模式值名称集合...");
		this.handoutModeMap = handoutModeMap;
	}
	/**
	 * 设置视频模式值名称集合。
	 * @param videoModeMap 
	 *	  视频模式值名称集合。
	 */
	public void setVideoModeMap(Map<Integer, String> videoModeMap) {
		logger.debug("注入视频模式值名称集合...");
		this.videoModeMap = videoModeMap;
	}
	/*
	 * 加载讲义模式值名称。
	 * @see com.examw.netplatform.service.admin.courses.ILessonService#loadHandoutModeName(java.lang.Integer)
	 */
	@Override
	public String loadHandoutModeName(Integer handoutMode) {
		logger.debug(String.format("加载讲义模式值［%d］名称...", handoutMode));
		if(handoutMode == null || this.handoutModeMap == null || this.handoutModeMap.size() == 0) return null;
		return this.handoutModeMap.get(handoutMode);
	}
	/*
	 * 加载试听模式值名称。
	 * @see com.examw.netplatform.service.admin.courses.ILessonService#loadVideoModeName(java.lang.Integer)
	 */
	@Override
	public String loadVideoModeName(Integer videoMode) {
		logger.debug(String.format("", videoMode));
		if(videoMode == null || this.videoModeMap == null || this.videoModeMap.size() == 0) return null;
		return this.videoModeMap.get(videoMode);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.courses.ILessonService#datagrid(com.examw.netplatform.model.admin.courses.LessonInfo)
	 */
	@Override
	public DataGrid<LessonInfo> datagrid(LessonInfo info) {
		logger.debug("查询数据...");
		//分页排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getOrder()) + " " + StringUtils.trimToEmpty(info.getSort()));
		//查询数据
		final List<Lesson> list = this.lessonDao.findLessons(info);
		//分页信息
		final PageInfo<Lesson> pageInfo = new PageInfo<Lesson>(list);
		//初始化
		final DataGrid<LessonInfo> grid = new DataGrid<LessonInfo>();
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//批量数据类型转换。
	private List<LessonInfo> changeModel(List<Lesson> lessons){
		final List<LessonInfo> list = new ArrayList<LessonInfo>();
		if(lessons != null && lessons.size() > 0){
			for(Lesson lesson : lessons){
				if(lesson == null) continue;
				list.add(this.conversion(lesson));
			}
		}
		return list;
	}
	/*
	 * 数据类型转换。
	 * @see com.examw.netplatform.service.admin.courses.ILessonService#conversion(com.examw.netplatform.domain.admin.courses.Lesson)
	 */
	@Override
	public LessonInfo conversion(Lesson data) {
		logger.debug("数据类型转换[Lesson -> LessonInfo]...");
		LessonInfo info = (LessonInfo)data;
		info.setVideoModeName(this.loadVideoModeName(data.getVideoMode()));
		info.setHandoutModeName(this.loadHandoutModeName(data.getHandoutMode()));
		return info;
	}
	/*
	 * 加载班级下课时资源集合数据。
	 * @see com.examw.netplatform.service.admin.courses.ILessonService#loadLessons(java.lang.String)
	 */
	@Override
	public List<LessonInfo> loadLessons(String classId) {
		logger.debug("加载班级下课时资源集合数据...");
		return this.changeModel(this.lessonDao.findLessonsByClass(classId));
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.courses.ILessonService#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String classId) {
		logger.debug("查询数据..." + classId);
		return this.lessonDao.loadMaxOrder(classId);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.admin.courses.ILessonService#update(com.examw.netplatform.model.admin.courses.LessonInfo)
	 */
	@Override
	public LessonInfo update(LessonInfo info) {
		logger.debug("更新数据...");
		if(info == null)return null;
		Lesson data = StringUtils.isBlank(info.getId()) ? null : this.lessonDao.getLesson(info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			if(StringUtils.isBlank(info.getId()))info.setId(UUID.randomUUID().toString());
			data = new Lesson();
		}
		//赋值
		BeanUtils.copyProperties(info, data);
		//保存
		if(isAdded){
			logger.debug("新增课时资源...");
			this.lessonDao.insertLesson(data);
		}else {
			logger.debug("更新课时资源...");
			this.lessonDao.updateLesson(data);
		}
		//返回数据
		return this.conversion(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.admin.courses.ILessonService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除数据..." + StringUtils.join(ids,","));
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id)) continue;
				this.lessonDao.deleteLesson(id);
			}
		}
	}
}