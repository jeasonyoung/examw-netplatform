package com.examw.netplatform.service.admin.teachers.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.security.IUserDao;
import com.examw.netplatform.dao.admin.teachers.IAnswerQuestionDetailDao;
import com.examw.netplatform.dao.admin.teachers.IAnswerQuestionTopicDao;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionDetail;
import com.examw.netplatform.domain.admin.teachers.AnswerQuestionTopic;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionDetailInfo;
import com.examw.netplatform.service.admin.teachers.IAnswerQuestionDetailService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
import com.examw.service.Status;

/**
 * 教师答疑明细服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年11月20日
 */
public class AnswerQuestionDetailServiceImpl extends BaseDataServiceImpl<AnswerQuestionDetail, AnswerQuestionDetailInfo> implements IAnswerQuestionDetailService {
	private static final Logger logger = Logger.getLogger(AnswerQuestionDetailServiceImpl.class);
	private IAnswerQuestionDetailDao answerQuestionDetailDao;
	private IAnswerQuestionTopicDao answerQuestionTopicDao;
	private IUserDao userDao;
	/**
	 * 设置教师答疑明细数据接口。
	 * @param answerQuestionDetailDao 
	 *	  教师答疑明细数据接口。
	 */
	public void setAnswerQuestionDetailDao(IAnswerQuestionDetailDao answerQuestionDetailDao) {
		if(logger.isDebugEnabled()) logger.debug("注入教师答疑明细数据接口...");
		this.answerQuestionDetailDao = answerQuestionDetailDao;
	}
	/**
	 * 设置教师答疑主题数据接口。
	 * @param answerQuestionTopicDao 
	 *	  教师答疑主题数据接口。
	 */
	public void setAnswerQuestionTopicDao(IAnswerQuestionTopicDao answerQuestionTopicDao) {
		if(logger.isDebugEnabled()) logger.debug("注入教师答疑主题数据接口...");
		this.answerQuestionTopicDao = answerQuestionTopicDao;
	}
	/**
	 * 设置用户数据接口。
	 * @param userDao 
	 *	  用户数据接口。
	 */
	public void setUserDao(IUserDao userDao) {
		if(logger.isDebugEnabled()) logger.debug("注入用户数据接口...");
		this.userDao = userDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<AnswerQuestionDetail> find(AnswerQuestionDetailInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.answerQuestionDetailDao.findDetails(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected AnswerQuestionDetailInfo changeModel(AnswerQuestionDetail data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换  AnswerQuestionDetail => AnswerQuestionDetailInfo ...");
		if(data == null) return null;
		AnswerQuestionDetailInfo info = new AnswerQuestionDetailInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getTopic() != null){
			info.setTopicId(data.getTopic().getId());
		}
		if(data.getUser() != null){
			info.setUserId(data.getUser().getId());
			info.setUserName(data.getUser().getName());
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(AnswerQuestionDetailInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.answerQuestionDetailDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public AnswerQuestionDetailInfo update(AnswerQuestionDetailInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		AnswerQuestionDetail detail = StringUtils.isEmpty(info.getId()) ? null : this.answerQuestionDetailDao.load(AnswerQuestionDetail.class, info.getId());
		if(isAdded = (detail == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			detail = new AnswerQuestionDetail();
		}else {
			info.setCreateTime(detail.getCreateTime());
			if(info.getCreateTime() == null) info.setCreateTime(new Date());
		}
		info.setLastTime(new Date());
		BeanUtils.copyProperties(info, detail);
		
		if(StringUtils.isEmpty(info.getTopicId())) throw new RuntimeException("教师答疑主题ID不存在！");
		//修改状态 2015.01.30
		AnswerQuestionTopic topic = (this.answerQuestionTopicDao.load(AnswerQuestionTopic.class, info.getTopicId()));
		if(topic == null) throw new RuntimeException(String.format("教师答疑主题［%s］不存在！", info.getTopicId()));
		if(topic.getStatus().equals(Status.DISABLE.getValue())) topic.setStatus(Status.ENABLED.getValue());
		detail.setTopic(topic);
		//修改状态 2015.01.30
		detail.setUser(StringUtils.isEmpty(info.getUserId()) ? null : this.userDao.load(User.class, info.getUserId()));
		 
		if(isAdded) this.answerQuestionDetailDao.save(detail);
		return this.changeModel(detail);
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
			if(StringUtils.isEmpty(ids[i])) continue;
			AnswerQuestionDetail detail = this.answerQuestionDetailDao.load(AnswerQuestionDetail.class, ids[i]);
			if(detail != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除答疑明细：%s", ids[i]));
				this.answerQuestionDetailDao.delete(detail);
			}
		}
	}
	@Override
	public AnswerQuestionDetailInfo conversion(AnswerQuestionDetail data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换,AnswerQuestionDetail -->AnswerQuestionDetailInfo ...");
		return this.changeModel(data);
	}
}