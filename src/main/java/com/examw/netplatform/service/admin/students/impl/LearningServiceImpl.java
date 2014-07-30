package com.examw.netplatform.service.admin.students.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.examw.netplatform.dao.admin.students.ILearningDao;
import com.examw.netplatform.domain.admin.students.Learning;
import com.examw.netplatform.model.admin.students.LearningInfo;
import com.examw.netplatform.service.admin.students.ILearningService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;

/**
 * 学习进度服务接口实现类
 * @author fengwei.
 * @since 2014年5月29日 上午11:47:36.
 */
public class LearningServiceImpl extends BaseDataServiceImpl<Learning, LearningInfo> implements ILearningService{
	private ILearningDao learningDao;
	
	/**
	 * 设置 学习进度数据接口
	 * @param learningDao
	 * 
	 */
	public void setLearningDao(ILearningDao learningDao) {
		this.learningDao = learningDao;
	}

	@Override
	protected List<Learning> find(LearningInfo info) {
		return this.learningDao.findLearnings(info);
	}

	@Override
	protected LearningInfo changeModel(Learning data) {
		if(data == null ) return null;
		LearningInfo info = new LearningInfo();
		BeanUtils.copyProperties(data, info);
		//学员
		if(data.getUser()!=null)
		{
			info.setUserId(data.getUser().getId());
			info.setUsername(data.getUser().getName());
			info.setUseraccount(data.getUser().getAccount());
		}
		//套餐
		if(data.getPackage_()!=null)
		{
			info.setPackageId(data.getPackage_().getId());
			info.setPackageName(data.getPackage_().getName());
		}
		//课时
		if(data.getLesson()!=null)
		{
			info.setLessonId(data.getLesson().getId());
			info.setLessonName(data.getLesson().getName());
		}
		return info;
	}

	@Override
	protected Long total(LearningInfo info) {
		return this.learningDao.total(info);
	}
 
	@Override
	public LearningInfo update(LearningInfo info) {
		
		return null;
	}

	@Override
	public void delete(String[] ids) {
		
	}
}
