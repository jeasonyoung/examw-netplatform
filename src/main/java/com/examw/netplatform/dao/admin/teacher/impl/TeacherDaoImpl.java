package com.examw.netplatform.dao.admin.teacher.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.teacher.ITeacherDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.teacher.Teacher;
import com.examw.netplatform.model.admin.teacher.TeacherInfo;

/**
 * 教师数据接口实现类
 * @author fengwei.
 * @since 2014年5月29日 下午3:23:13.
 */
public class TeacherDaoImpl extends BaseDaoImpl<Teacher> implements ITeacherDao{

	@Override
	public List<Teacher> findTeachers(TeacherInfo info) {
		String hql = "from Teacher t where t.identity = :identity ";
		Map<String, Object> parameters = new HashMap<>();
		//parameters.put("identity", AgencyUser.IDENTITY_TEACHER);
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by t.user." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}

	@Override
	public Long total(TeacherInfo info) {
		String hql = "select count(*) from Teacher t where t.identity = :identity ";
		Map<String, Object> parameters = new HashMap<>();
		//parameters.put("identity", AgencyUser.IDENTITY_TEACHER);
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件到HQL。
	private String addWhere(TeacherInfo info, String hql, Map<String, Object> parameters){
//		if(info.getAgencyId() != null && info.getAgencyId().length > 0){
//			hql += " and (t.agency.id in (:agencyId))";
//			parameters.put("agencyId", info.getAgencyId());
//		}
		if(info.getStatus() != null){
			hql += " and (t.user.status = :status)";
			parameters.put("status", info.getStatus());
		}
		if(!StringUtils.isEmpty(info.getName())){
			hql +=" and (t.user.name like :name  or t.user.account like :name  or t.user.nickName like :name)";
			parameters.put("name", "%" + info.getName() +  "%");
		}
		if(!StringUtils.isEmpty(info.getPhone())){
			hql +=" and (t.user.phone like :phone like :phone)";
			parameters.put("phone", "%" + info.getPhone() +  "%");
		}
		return hql;
	}
	/*
	 * 批量加
	 * @see com.examw.netplatform.dao.admin.IAgencyUserDao#batchAdd(java.util.List)
	 */
	@Override
	public void batchAdd(List<Teacher> list) {
		if(list==null||list.size()==0) return ;
		Session session = this.getCurrentSession();
		int i = 0;
		for(Teacher item:list)
		{
			i++;
			session.save(item);
			if(i%10==0)
			{
				session.flush();
				session.clear();
			}
		}
	}
	/*
	 * 根据用户ID删除关联关系
	 * @see com.examw.netplatform.dao.admin.IAgencyUserDao#delete(java.lang.String)
	 */
	@Override
	public void delete(String userId) {
		if(StringUtils.isEmpty(userId)) return;
		String hql = "delete from Teacher t where t.user.id = :userId";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("userId", userId);
		query.executeUpdate();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> find(String userId) {
		String hql = "from Teacher t where t.user.id = :userId";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("userId", userId);
		return query.list();
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<User> loadTeacher(String agencyId) {
		String hql = "select t.user from Teacher t where  t.identity = :identity and t.agency.id=:agencyId";
		Query query = this.getCurrentSession().createQuery(hql);
		//query.setParameter("identity", AgencyUser.IDENTITY_TEACHER);
		query.setParameter("agencyId", agencyId);
		return query.list();
	}
}
