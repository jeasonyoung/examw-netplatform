package com.examw.netplatform.dao.admin.teachers.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.teachers.IPracticeDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.teachers.Practice;
import com.examw.netplatform.model.admin.teachers.PracticeInfo;

/**
 * 随堂练习数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年11月22日
 */
public class PracticeDaoImpl extends BaseDaoImpl<Practice> implements IPracticeDao {
	private static final Logger logger = Logger.getLogger(PracticeDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.teachers.IPracticeDao#findPractices(com.examw.netplatform.model.admin.teachers.PracticeInfo)
	 */
	@Override
	public List<Practice> findPractices(PracticeInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from Practice p where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			switch(info.getOrder()){
				case "className":
					info.setSort("lesson.classPlan.name");
					break;
				case "lessonName":
					info.setSort("lesson.name");
					break;
				case "statusName":
					info.setSort("status");
					break;
			}
			hql += " order by p." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.teachers.IPracticeDao#total(com.examw.netplatform.model.admin.teachers.PracticeInfo)
	 */
	@Override
	public Long total(PracticeInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from Practice p where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件。
	private String addWhere(PracticeInfo info,String hql, Map<String, Object> parameters){
		if(info == null) return hql;
		if(!StringUtils.isEmpty(info.getAgencyId())){//机构ID
			hql += " and (p.lesson.classPlan.agency.id = :agencyId) ";
			parameters.put("agencyId", info.getAgencyId());
		}
		if(!StringUtils.isEmpty(info.getClassId())){//班级ID
			hql += " and (p.lesson.classPlan.id = :classId) ";
			parameters.put("classId", info.getClassId());
		}
		if(!StringUtils.isEmpty(info.getLessonId())){//课时资源ID
			hql += " and (p.lesson.id = :lessonId) ";
			parameters.put("lessonId", info.getLessonId());
		}
		if(info.getStatus() != null){//状态
			hql += " and (p.status = :status) ";
			parameters.put("status", info.getStatus());
		}
		if(!StringUtils.isEmpty(info.getName())){//名称
			hql += " and (p.name like :name) ";
			parameters.put("name", "%"+info.getName()+"%");
		}
		return hql;
	}
	/*
	 * 重载删除。
	 * @see com.examw.netplatform.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Practice data) {
		if(logger.isDebugEnabled()) logger.debug("重载删除...");
		if(data == null) return;
		int count = 0;
		if(data.getStructures() != null && (count = data.getStructures().size()) > 0){
			throw new RuntimeException(String.format("随堂练习［%1$s］关联［%2$d］结构，暂不能删除！", data.getName(), count));
		}
		super.delete(data);
	}
}