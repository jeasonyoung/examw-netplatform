package com.examw.netplatform.dao.admin.courses.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.courses.ILessonDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.courses.Lesson;
import com.examw.netplatform.model.admin.courses.LessonInfo;

/**
 *课时资源数接口实现类。
 * @author fengwei.
 * @since 2014年5月22日 上午11:38:23.
 */
public class LessonDaoImpl extends BaseDaoImpl<Lesson> implements ILessonDao {
	private static final Logger logger = Logger.getLogger(LessonDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.courses.ILessonDao#findLessons(com.examw.netplatform.model.admin.courses.LessonInfo)
	 */
	@Override
	public List<Lesson> findLessons(LessonInfo info) {
		if(logger.isDebugEnabled()) logger.debug("数据查询...");
		String hql = "from Lesson l where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if (!StringUtils.isEmpty(info.getSort())) {
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += " order by l." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.ILessonDao#total(com.examw.netplatform.model.admin.LessonInfo)
	 */
	@Override
	public Long total(LessonInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from Lesson l where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件到HQL。
	private String addWhere(LessonInfo info, String hql, Map<String, Object> parameters) {
		if(!StringUtils.isEmpty(info.getAgencyId())){//培训机构ID。
			hql += " and (l.classPlan.agency.id = :agencyId) ";
			parameters.put("agencyId", info.getAgencyId());
		}
		if(!StringUtils.isEmpty(info.getClassId())){//班级ID。
			hql += " and (l.classPlan.id = :classId) ";
			parameters.put("classId", info.getClassId());
		}
		if(!StringUtils.isEmpty(info.getName())){//资源名称。
			hql += " and (l.name like :name) ";
			parameters.put("name", "%"+ info.getName() +"%");
		}
		return hql;
	}
	/*
	 * 加载班级下的最大排序号。
	 * @see com.examw.netplatform.dao.admin.courses.ILessonDao#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String classId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载班级［%s］下的最大排序号...", classId));
		final String hql = "select max(l.orderNo) from Lesson l where (l.classPlan.id = :classId) order by l.orderNo desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("classId", classId);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? null : (int)obj;
	}
	/*
	 * 重载删除。
	 * @see com.examw.netplatform.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Lesson data) {
		if(logger.isDebugEnabled()) logger.debug("重载删除...");
		if(data == null)return;
		int count = 0;
		if(data.getTopics() != null && (count = data.getTopics().size()) > 0){
			throw new RuntimeException(String.format("课时资源［%1$s］关联［%2$d］教师答疑主题，暂不能删除！", data.getName(),count));
		}
		if(data.getPractices() != null && (count = data.getPractices().size()) > 0){
			throw new RuntimeException(String.format("课时资源［%1$s］关联［%2$d］随堂练习，暂不能删除！", data.getName(),count));
		}
		if(data.getChapters() != null && data.getChapters().size() > 0){
			data.getChapters().clear();
		}
		super.delete(data);
	}
	/*
	 * 查询某用户带提问的课时列表
	 * 2015.01.30
	 * @see com.examw.netplatform.dao.admin.courses.ILessonDao#findLessonWithQuestions(com.examw.netplatform.model.admin.courses.LessonInfo, java.lang.String)
	 */
	@Override
	public List<Lesson> findLessonWithQuestions(LessonInfo info, String userId) {
		if(logger.isDebugEnabled()) logger.debug("查询带有用户[%1$s]提问的课时数据...");
		String hql = "select distinct l from Lesson l , AnswerQuestionTopic q where l.id = q.lesson.id and q.user.id = :userId order by q.createTime desc";
		Map<String, Object> parameters = new HashMap<>();
		if(!StringUtils.isEmpty(userId)){//资源名称。
			parameters.put("userId", userId);
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询某用户带提问的课时数据统计
	 * 2015.01.30
	 * @see com.examw.netplatform.dao.admin.courses.ILessonDao#totalLessonWithQuestions(java.lang.String)
	 */
	@Override
	public Long totalLessonWithQuestions(String userId) {
		if(logger.isDebugEnabled()) logger.debug("查询带有用户[%1$s]提问的课时数据统计...");
		String hql = "select count(distinct l) from Lesson l, AnswerQuestionTopic q where l.id = q.lesson.id and q.user.id = :userId ";
		Map<String, Object> parameters = new HashMap<>();
		if(!StringUtils.isEmpty(userId)){//资源名称。
			parameters.put("userId", userId);
		}
		return this.count(hql, parameters);
	}
}