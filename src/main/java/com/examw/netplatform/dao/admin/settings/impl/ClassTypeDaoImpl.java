package com.examw.netplatform.dao.admin.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.settings.IClassTypeDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.settings.ClassType;
import com.examw.netplatform.model.admin.settings.ClassTypeInfo;

/**
 * 班级类型数据接口实现类。
 * @author yangyong.
 * @since 2014-05-20.
 */
public class ClassTypeDaoImpl extends BaseDaoImpl<ClassType> implements IClassTypeDao {
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.IClassTypeDao#findClassTypes(com.examw.netplatform.model.admin.ClassTypeInfo)
	 */
	@Override
	public List<ClassType> findClassTypes(ClassTypeInfo info) {
		String hql = "from ClassType c where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by c." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.IClassTypeDao#total(com.examw.netplatform.model.admin.ClassTypeInfo)
	 */
	@Override
	public Long total(ClassTypeInfo info) {
		String hql = "select count(*) from ClassType c where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	/**
	 * 添加查询条件到HQL。
	 * @param info
	 * 查询条件。
	 * @param hql
	 * HQL
	 * @param parameters
	 * 参数。
	 * @return
	 * HQL
	 */
	protected String addWhere(ClassTypeInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getName())){
			hql += "  and (c.name like :name)";
			parameters.put("name", "%" + info.getName()+ "%");
		}
		return hql;
	}
}