package com.examw.netplatform.dao.admin.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.settings.ISubjectDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.settings.Subject;
import com.examw.netplatform.model.admin.settings.SubjectInfo;
/**
 * 
 * @author fengwei.
 * @since 2014年8月6日 下午1:45:49.
 */
public class SubjectDaoImpl extends BaseDaoImpl<Subject> implements ISubjectDao{
	private static final Logger logger = Logger.getLogger(SubjectDaoImpl.class);
	/*
	 * 查询数据
	 * @see com.examw.test.dao.settings.ISubjectDao#findSubjects(com.examw.test.model.settings.SubjectInfo)
	 */
	@Override
	public List<Subject> findSubjects(SubjectInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[科目]数据...");
		String hql = "from Subject s where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += String.format(" order by s.%1$s  %2$s", info.getSort(),info.getOrder());
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据总数
	 * @see com.examw.test.dao.settings.ISubjectDao#total(com.examw.test.model.settings.SubjectInfo)
	 */
	@Override
	public Long total(SubjectInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[科目]数据统计...");
		String hql = "select count(*) from Subject s where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	// 添加查询条件到HQL。
	private String addWhere(SubjectInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getName())) {
			hql += " and (s.name like :name)";
			parameters.put("name", "%" + info.getName() + "%");
		}
		if (!StringUtils.isEmpty(info.getExamId())) {
			hql += " and (s.exam.id = :examId)";
			parameters.put("examId", info.getExamId());
		}
		if (!StringUtils.isEmpty(info.getCategoryId())) {
			hql += " and (s.exam.category.id = :categoryId)";
			parameters.put("categoryId", info.getCategoryId());
		}
		if (!StringUtils.isEmpty(info.getAreaId())) {
			hql += " and (s.area.id = :areaId)";
			parameters.put("areaId", info.getAreaId());
		}
		return hql;
	}
	/*
	 * 加载最大代码值。
	 * @see com.examw.test.dao.settings.ISubjectDao#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		final String hql = "select max(s.code) from Subject s ";
		Object obj = this.uniqueResult(hql, null);
		return (obj == null) ? null : (int)obj;
	}
	/*
	 * 加载考试下的科目数据。
	 * @see com.examw.test.dao.settings.ISubjectDao#loadAllSubjects(java.lang.String)
	 */
	@Override
	public List<Subject> loadAllSubjects(String examId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试[examId=%s]下的科目...", examId));
		final String hql = "from Subject s where s.exam.id = :examId order by s.code ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("examId", examId);
		return this.find(hql, parameters, null, null);
	}
	/*
	 * 重载数据删除。
	 * @see com.examw.netplatform.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Subject data) {
		if(logger.isDebugEnabled()) logger.debug("重载数据删除...");
		if(data == null) return;
		int count = 0;
		if(data.getChapters() != null && (count = data.getChapters().size()) > 0){
			throw new RuntimeException(String.format("科目［%1$s］下关联［%2$d］章节，暂不能删除！", data.getName(), count));
		}
		if(data.getClasses() != null && (count = data.getClasses().size()) > 0){
			throw new RuntimeException(String.format("科目［%1$s］下关联［%2$d］班级，暂不能删除！", data.getName(), count));
		}
		if(data.getPackages() != null && (count = data.getPackages().size()) > 0){
			throw new RuntimeException(String.format("科目［%1$s］下关联［%2$d］套餐，暂不能删除！", data.getName(), count));
		}
		super.delete(data);
	}
}