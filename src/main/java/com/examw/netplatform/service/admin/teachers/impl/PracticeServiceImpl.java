package com.examw.netplatform.service.admin.teachers.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.courses.ILessonDao;
import com.examw.netplatform.dao.admin.teachers.IPracticeDao;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.domain.admin.teachers.Practice;
import com.examw.netplatform.model.admin.teachers.PracticeInfo;
import com.examw.netplatform.service.admin.teachers.IPracticeService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
import com.examw.service.Status;
/**
 * 随堂练习服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年11月24日
 */
public class PracticeServiceImpl extends BaseDataServiceImpl<Practice, PracticeInfo> implements IPracticeService {
	private static final Logger logger = Logger.getLogger(PracticeServiceImpl.class);
	private IPracticeDao practiceDao;
	private ILessonDao lessonDao;
	private Map<Integer, String> statusMap;
	/**
	 * 设置随堂练习数据接口。
	 * @param practiceDao 
	 *	  随堂练习数据接口。
	 */
	public void setPracticeDao(IPracticeDao practiceDao) {
		if(logger.isDebugEnabled()) logger.debug("注入随堂练习数据接口...");
		this.practiceDao = practiceDao;
	}
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
	 * 设置状态值名称集合。
	 * @param statusMap 
	 *	  状态值名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("注入状态值名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态值名称集合。
	 * @see com.examw.netplatform.service.admin.teachers.IPracticeService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载状态值［%d］名称集合...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Practice> find(PracticeInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.practiceDao.findPractices(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected PracticeInfo changeModel(Practice data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Practice => PracticeInfo...");
		if(data == null) return null;
		PracticeInfo info = new PracticeInfo();
		BeanUtils.copyProperties(data, info);
		Lesson lesson = null;
		if((lesson = data.getLesson()) != null){//所属课时资源。
			info.setLessonId(lesson.getId());
			info.setLessonName(lesson.getName());
			ClassPlan classPlan = null;
			if((classPlan = lesson.getClassPlan()) != null){//所属班级
				info.setClassId(classPlan.getId());
				info.setClassName(classPlan.getName());
				Agency agency = null;
				if((agency = classPlan.getAgency()) != null){//所属机构
					info.setAgencyId(agency.getId());
				}
			}
		}
		info.setStatusName(this.loadStatusName(info.getStatus()));//状态名称
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(PracticeInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.practiceDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public PracticeInfo update(PracticeInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		boolean isAdded = false;
		Practice practice = StringUtils.isEmpty(info.getId()) ? null : this.practiceDao.load(Practice.class, info.getId());
		if(isAdded = (practice == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			practice = new Practice();
		}else{
			info.setCreateTime(practice.getCreateTime());
			if(info.getCreateTime() == null) info.setCreateTime(new Date());
			info.setStatus(practice.getStatus());
			if(info.getStatus() == null) info.setStatus(Status.DISABLE.getValue());
		}
		info.setLastTime(new Date());
		BeanUtils.copyProperties(info, practice);
		if(StringUtils.isEmpty(info.getLessonId())) throw new RuntimeException("所属课时资源ID为空！");
		Lesson lesson = this.lessonDao.load(Lesson.class, info.getLessonId());
		if(lesson == null) throw new RuntimeException(String.format("课时资源［%s］不存在！", info.getLessonId()));
		practice.setLesson(lesson);
		if(isAdded) this.practiceDao.save(practice);
		return this.changeModel(practice);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Practice practice = this.practiceDao.load(Practice.class, ids[i]);
			if(practice != null){
				this.practiceDao.delete(practice);
			}
		}
	}
}