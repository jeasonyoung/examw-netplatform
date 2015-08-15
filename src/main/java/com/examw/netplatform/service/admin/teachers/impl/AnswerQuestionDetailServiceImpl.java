package com.examw.netplatform.service.admin.teachers.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.security.UserMapper;
import com.examw.netplatform.dao.admin.teachers.AnswerQuestionDetailMapper;
import com.examw.netplatform.dao.admin.teachers.AnswerQuestionTopicMapper;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionDetail;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionDetailInfo;
import com.examw.netplatform.service.admin.teachers.IAnswerQuestionDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 教师答疑明细服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年11月20日
 */
public class AnswerQuestionDetailServiceImpl implements IAnswerQuestionDetailService {
	private static final Logger logger = Logger.getLogger(AnswerQuestionDetailServiceImpl.class);
	private AnswerQuestionDetailMapper answerQuestionDetailDao;
	private AnswerQuestionTopicMapper answerQuestionTopicDao;
	private UserMapper userDao;
	/**
	 * 设置教师答疑明细数据接口。
	 * @param answerQuestionDetailDao 
	 *	  教师答疑明细数据接口。
	 */
	public void setAnswerQuestionDetailDao(AnswerQuestionDetailMapper answerQuestionDetailDao) {
		logger.debug("注入教师答疑明细数据接口...");
		this.answerQuestionDetailDao = answerQuestionDetailDao;
	}
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
	 * 设置用户数据接口。
	 * @param userDao 
	 *	  用户数据接口。
	 */
	public void setUserDao(UserMapper userDao) {
		logger.debug("注入用户数据接口...");
		this.userDao = userDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.teachers.IAnswerQuestionDetailService#datagrid(com.examw.netplatform.model.admin.teachers.AnswerQuestionDetailInfo)
	 */
	@Override
	public DataGrid<AnswerQuestionDetailInfo> datagrid(AnswerQuestionDetailInfo info) {
		logger.debug("查询数据...");
		//分页排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getOrder()) + " " + StringUtils.trimToEmpty(info.getSort()));
		//查询数据
		final List<AnswerQuestionDetail> list = this.answerQuestionDetailDao.findDetails(info);
		//分页信息
		final PageInfo<AnswerQuestionDetail> pageInfo = new PageInfo<AnswerQuestionDetail>(list);
		//初始化
		DataGrid<AnswerQuestionDetailInfo> grid = new DataGrid<AnswerQuestionDetailInfo>();
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//批量数据转换。
	private List<AnswerQuestionDetailInfo> changeModel(List<AnswerQuestionDetail> details){
		final List<AnswerQuestionDetailInfo> list = new ArrayList<AnswerQuestionDetailInfo>();
		if(details != null && details.size() > 0){
			for(AnswerQuestionDetail data : details){
				if(data == null) continue;
				list.add(this.conversion(data));
			}
		}
		return list;
	}
	/*
	 * 数据类型转换。
	 * @see com.examw.netplatform.service.admin.teachers.IAnswerQuestionDetailService#conversion(com.examw.netplatform.domain.admin.teachers.AnswerQuestionDetail)
	 */
	@Override
	public AnswerQuestionDetailInfo conversion(AnswerQuestionDetail data) {
		return (AnswerQuestionDetailInfo)data;
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.admin.teachers.IAnswerQuestionDetailService#update(com.examw.netplatform.model.admin.teachers.AnswerQuestionDetailInfo)
	 */
	@Override
	public AnswerQuestionDetailInfo update(AnswerQuestionDetailInfo info) {
		logger.debug("更新数据...");
		if(info == null) return null;
		//检查数据
		if(StringUtils.isBlank(info.getTopicId()) || this.answerQuestionTopicDao.getTopic(info.getTopicId()) == null){
			throw new RuntimeException("所属答疑主题["+info.getTopicId()+"]不存在!");
		}
		if(StringUtils.isBlank(info.getUserId()) || this.userDao.getUser(info.getUserId()) == null){
			throw new RuntimeException("所属用户["+info.getTopicId()+"]不存在!");
		}
		//
		AnswerQuestionDetail data = StringUtils.isBlank(info.getId()) ? null : this.answerQuestionDetailDao.getDetail(info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			if(StringUtils.isBlank(info.getId()))
				info.setId(UUID.randomUUID().toString());
			data = new AnswerQuestionDetail();
		}
		//赋值
		BeanUtils.copyProperties(info, data);
		//保存数据
		if(isAdded){
			logger.debug("新增答疑明细...");
			this.answerQuestionDetailDao.insertDetail(data);
		}else {
			logger.debug("更新答疑明细...");
			this.answerQuestionDetailDao.updateDetail(data);
		}
		//返回
		return this.conversion(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.admin.teachers.IAnswerQuestionDetailService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除数据..." + StringUtils.join(ids,","));
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id)) continue;
				this.answerQuestionDetailDao.deleteDetail(id);
			}
		}
	}
}