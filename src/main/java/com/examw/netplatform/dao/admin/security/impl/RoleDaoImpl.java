package com.examw.netplatform.dao.admin.security.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.security.IRoleDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.security.Role;
import com.examw.netplatform.model.admin.security.RoleInfo;

/**
 * 角色数据接口实现类。
 * @author yangyong.
 * @since 2014-05-05.
 */
public class RoleDaoImpl extends BaseDaoImpl<Role> implements IRoleDao {
	private static final Logger logger = Logger.getLogger(RoleDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.IRoleDao#findRoles(com.examw.netplatform.model.admin.RoleInfo)
	 */
	@Override
	public List<Role> findRoles(RoleInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from Role r  where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += " order by r." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据汇总。
	 * @see com.examw.netplatform.dao.admin.IRoleDao#total(com.examw.netplatform.model.admin.RoleInfo)
	 */
	@Override
	public Long total(RoleInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据汇总...");
		String hql = "select count(*) from Role r where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件到HQL。
	private String addWhere(RoleInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getName())){
			hql += "  and (r.name like :name)";
			parameters.put("name", "%" + info.getName()+ "%");
		}
		if(info.getStatus() != null){
			hql += " and (r.status = :status)";
			parameters.put("status", info.getStatus());
		}
		return hql;
	}
	/*
	 * 重载删除数据。
	 * @see com.examw.netplatform.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Role data) {
		if(logger.isDebugEnabled()) logger.debug("重载删除数据...");
		if(data == null) return;
		int count = 0;
		if(data.getUsers() != null && (count = data.getUsers().size()) > 0){
			throw new RuntimeException(String.format("角色［%1$s］已被［%2$d］用户关联，暂不能删除！",data.getName(),count));
		}
		if(data.getAgencies() != null && (count = data.getAgencies().size()) > 0){
			throw new RuntimeException(String.format("角色［%1$s］已被［%2$d］机构关联，暂不能删除！",data.getName(),count));
		}
		if(data.getRights() != null && data.getRights().size() > 0){
			data.getRights().clear();
		}
		super.delete(data);
	}
}