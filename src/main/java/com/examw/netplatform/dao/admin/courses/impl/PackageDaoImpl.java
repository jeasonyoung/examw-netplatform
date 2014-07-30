package com.examw.netplatform.dao.admin.courses.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.courses.IPackageDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.courses.Package;
import com.examw.netplatform.model.admin.courses.PackageInfo;
/**
 * 套餐数据接口实现类
 * @author fengwei.
 * @since 2014年5月21日 下午3:08:21.
 */
public class PackageDaoImpl extends BaseDaoImpl<Package> implements IPackageDao {
	/*
	 *  查询数据。
	 * @see com.examw.netplatform.dao.admin.courses.IPackageDao#findPackages(com.examw.netplatform.model.admin.courses.PackageInfo)
	 */
	@Override
	public List<Package> findPackages(PackageInfo info) {
		String hql = "from Package p where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("statusName")){
				info.setSort("status");
			}
			if(info.getSort().equalsIgnoreCase("agencyName")){
				info.setSort("agency.name");
			}
			if(info.getSort().equalsIgnoreCase("examName")){
				info.setSort("exam.name");
			}
			hql += " order by p." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询统计。
	 * @see com.examw.netplatform.dao.admin.courses.IPackageDao#total(com.examw.netplatform.model.admin.courses.PackageInfo)
	 */
	@Override
	public Long total(PackageInfo info) {
		String hql = "select count(*) from Package p where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//查询条件。
	private String addWhere(PackageInfo info,String hql,Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getCurrentUserId())){
			hql += " and (p.agency.id in (select au.agency.id  from AgencyUser au where au.user.id = :userId)) ";
			parameters.put("userId", info.getCurrentUserId());
		}
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and (p.name like :name)";
			parameters.put("name", "%"+ info.getName() +"%");
		}
		if(!StringUtils.isEmpty(info.getAgencyId())){
			hql += " and (p.agency.id = :agencyId) ";
			parameters.put("agencyId", info.getAgencyId());
		}
		if(!StringUtils.isEmpty(info.getCatalogId())){
			hql += " and (p.exam.catalog.id = :catalogId) ";
			parameters.put("catalogId", info.getCatalogId());
		}
		if(!StringUtils.isEmpty(info.getExamId())){
			hql += " and (p.exam.id = :examId) ";
			parameters.put("examId", info.getExamId());
		}
		if(info.getStatus() != null){
			hql += " and (p.status = :status)";
			parameters.put("status", info.getStatus());
		}
		return hql;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.courses.IPackageDao#findPackages(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Package> findPackages(String agencyId, String catalogId,String examId, String packageName) {
		String hql = "from Package p where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		if(!StringUtils.isEmpty(agencyId)){
			hql += " and (p.agency.id = :agencyId) ";
			parameters.put("agencyId", agencyId);
		}
		if(!StringUtils.isEmpty(catalogId)){
			hql += " and (p.exam.catalog.id = :catalogId) ";
			parameters.put("catalogId", catalogId);
		}
		if(!StringUtils.isEmpty(examId)){
			hql += " and (p.exam.id = :examId) ";
			parameters.put("examId", examId);
		}
		if(!StringUtils.isEmpty(packageName)){
			hql += " and (p.name like :packageName) ";
			parameters.put("packageName", "%"+ packageName +"%");
		}
		hql += " order by p.startTime desc";
		return this.find(hql, parameters, null, null);
	}
}