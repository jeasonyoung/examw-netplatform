package com.examw.netplatform.service.admin.students.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.students.LearningMapper;
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
	/**
	 * 设置接口实现类。
	 * @param learningDao
	 * 接口实现类。
	 */
	public void setLearningDao(LearningMapper learningDao) {
		logger.debug("设置接口实现类...");
		this.learningDao = learningDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.students.ILearningService#datagrid(com.examw.netplatform.model.admin.students.LearningInfo)
	 */
	@Override
	public DataGrid<LearningInfo> datagrid(LearningInfo info) {
		logger.debug("查询数据...");
		//分页排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getOrder()) + " " + StringUtils.trimToEmpty(info.getSort()));
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
		return (LearningInfo)data;
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.admin.students.ILearningService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除数据..." + StringUtils.join(ids,","));
//		if(ids != null && ids.length > 0){
//			for(String id : ids){
//				if(StringUtils.isBlank(id)) continue;
//				this.learningDao.deleteLearning(studentId, lessonId);
//			}
//		}
	}
	    
//	/*
//	 * 删除数据。
//	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
//	 */
//	@Override
//	public void delete(String[] ids) {
//		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s ...", Arrays.toString(ids)));
//		if(ids == null || ids.length == 0) return;
//		for(int i = 0; i < ids.length; i++){
//			Learning data = this.learningDao.load(Learning.class, ids[i]);
//			if(data != null){
//				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s ...", ids[i]));
//				this.learningDao.delete(data);
//			}
//		}
//	}
}