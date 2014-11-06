package com.examw.netplatform.dao.admin.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.settings.IExamDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.settings.Exam;
import com.examw.netplatform.model.admin.settings.ExamInfo;

/**
 * 考试数据接口实现类
 * 
 * @author fengwei.
 * @since 2014年8月6日 下午1:45:01.
 */
public class ExamDaoImpl extends BaseDaoImpl<Exam> implements IExamDao {
	private static final Logger logger = Logger.getLogger(ExamDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.settings.IExamDao#findExams(com.examw.netplatform.model.admin.settings.ExamInfo)
	 */
	@Override
	public List<Exam> findExams(ExamInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[考试]数据...");
		String hql = "from Exam e where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			if(info.getSort().equalsIgnoreCase("categoryName")){
				info.setSort("category.name");
			}else if(info.getSort().equalsIgnoreCase("statusName")){
				info.setSort("status");
			}
			hql += " order by e." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.settings.IExamDao#total(com.examw.netplatform.model.admin.settings.ExamInfo)
	 */
	@Override
	public Long total(ExamInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from Exam e where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	// 添加查询条件到HQL。
	private String addWhere(ExamInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getName())) {
			hql += " and (e.name like :name)";
			parameters.put("name", "%" + info.getName() + "%");
		}
		if (!StringUtils.isEmpty(info.getCategoryId())) {
			hql += " and (e.category.id = :categoryId)";
			parameters.put("categoryId", info.getCategoryId());
		}
		if(info.getStatus() != null){
			hql += " and (e.status = :status) ";
			parameters.put("status", info.getStatus());
		}
		return hql;
	}
	/*
	 * 加载最大考试代码值。
	 * @see com.examw.test.dao.settings.IExamDao#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		final String hql = "select max(e.code) from Exam e order by e.code desc ";
		Object obj = this.uniqueResult(hql, null);
		return obj == null ? null : (int)obj;
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Exam data) {
		if(data == null) return;
		int count = 0;
		if(data.getSubjects() != null && (count = data.getSubjects().size()) > 0){
			throw new RuntimeException(String.format("考试［%1$s］关联有［%2$d］科目，暂不能删除！", data.getName(), count));
		}
		super.delete(data);
	}
}