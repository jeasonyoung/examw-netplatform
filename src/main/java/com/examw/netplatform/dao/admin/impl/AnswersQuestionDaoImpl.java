package com.examw.netplatform.dao.admin.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.IAnswersQuestionDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.AnswersQuestion;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.model.admin.AnswersQuestionInfo;

/**
 * 课堂问答数据接口实现类
 * @author fengwei.
 * @since 2014年5月31日 上午9:54:22.
 */
public class AnswersQuestionDaoImpl extends BaseDaoImpl<AnswersQuestion> implements IAnswersQuestionDao{
	@Override
	public List<AnswersQuestion> findAnswersQuestions(AnswersQuestionInfo info) {
		String hql = "from AnswersQuestion aq where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if (info.getSort() != null && !info.getSort().trim().isEmpty()) {
			hql += " order by aq." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	@Override
	public Long total(AnswersQuestionInfo info) {
		String hql = "select count(*) from AnswersQuestion aq where 1 = 1  ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	/**
	 * 添加查询条件到HQL。
	 * 
	 * @param info
	 *            查询条件。
	 * @param hql
	 *            HQL
	 * @param parameters
	 *            参数。
	 * @return HQL
	 */
	protected String addWhere(AnswersQuestionInfo info, String hql,
			Map<String, Object> parameters) {
		if(info.getPid()==null&&(info.getType()==null||info.getType().equals(AnswersQuestion.TYPE_TEACHER))){
			hql += " and (aq.parent is null) ";	//如果查询老师的答题记录就不要加这一条
		}
		if(info.getPid()!=null && !info.getPid().isEmpty()){
			hql += " and (aq.parent.id = :pid)";
			parameters.put("pid", info.getPid());
		}
		if (info.getType() !=null)
		{
			hql += " and (aq.type = :type)";
			parameters.put("type", info.getType());
		}
		if (info.getStatus() !=null)
		{
			hql += " and (aq.status = :status)";
			parameters.put("status", info.getStatus());
		}
		if(info.getUserAccount()!=null && !info.getUserAccount().isEmpty()){
			hql += " and (aq.user.account like :account)";
			parameters.put("account", info.getUserAccount());
		}
		return hql;
	}
	@Override
	public Long totalAnswered(User user,String classPlanIds) {
		if(StringUtils.isEmpty(classPlanIds)) return 0L;
		String hql = "select count(*) from AnswersQuestion aq left join aq.lesson.classPlan cp where cp in("
				+classPlanIds+") and aq.user.id = :userId";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("userId", user.getId());
		return (Long) query.uniqueResult();
	}
	@Override
	public Long totalQuestions(String classPlanIds) {
		if(StringUtils.isEmpty(classPlanIds)) return 0L;
		String hql = "select count(*) from AnswersQuestion aq left join aq.lesson.classPlan cp where cp in("
				+classPlanIds+") and aq.type = :type";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("type", AnswersQuestion.TYPE_STUDENT);
		return (Long) query.uniqueResult();
	}
}
