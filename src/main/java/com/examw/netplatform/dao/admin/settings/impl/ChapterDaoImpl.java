package com.examw.netplatform.dao.admin.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.settings.IChapterDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.settings.Chapter;
import com.examw.netplatform.model.admin.settings.ChapterInfo;

/**
 * 章节数据接口实现类
 * @author fengwei.
 * @since 2014年4月30日 下午3:28:14.
 */
public class ChapterDaoImpl extends BaseDaoImpl<Chapter> implements IChapterDao {
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.settings.IChapterDao#findChapters(com.examw.netplatform.model.admin.settings.ChapterInfo)
	 */
	@Override
	public List<Chapter> findChapters(ChapterInfo info) {
		String hql = "from Chapter c where c.parent = null and 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by c." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据汇总
	 * @see com.examw.netplatform.dao.admin.settings.IChapterDao#total(com.examw.netplatform.model.admin.settings.ChapterInfo)
	 */
	@Override
	public Long total(ChapterInfo info) {
		String hql = "select count(*) from Chapter c where c.parent = null and 1 = 1 ";
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
	protected String addWhere(ChapterInfo info,String hql,Map<String, Object> parameters){
		//考试类别
		if(!StringUtils.isEmpty(info.getCatalogId())){
			hql += " and (c.subject.exam.catalog.id = :catalogId) ";
			parameters.put("catalogId", info.getCatalogId());
		}
		//考试
		if(!StringUtils.isEmpty(info.getExamId())){
			hql += " and (c.subject.exam.id = :examId) ";
			parameters.put("examId", info.getExamId());
		}
		//科目ID查询
		if(!StringUtils.isEmpty(info.getSubjectId())){
			hql += " and (c.subject.id = :subjectId)";
			parameters.put("subjectId", info.getSubjectId());
		}
		//名称查询
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and (c.name like :name)";
			parameters.put("name", "%" + info.getName() + "%");
		}
		return hql;
	}
	/*
	 * 加载数据。
	 * @see com.examw.netplatform.dao.admin.settings.IChapterDao#loadChapters(java.lang.String)
	 */
	@Override
	public List<Chapter> loadChapters(String ignoreChapterId) {
		if(StringUtils.isEmpty(ignoreChapterId)) return this.findChapters(new ChapterInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getPage(){return null;}
			@Override
			public Integer getRows(){return null;}
		});
		final String hql = "from Chapter c where (c.parent = null) and (c.id not in (select tmp.id from Chapter tmp where (tmp.parent.id = :chapterId))) order by c.orderNo asc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("chapterId", ignoreChapterId);
		return this.find(hql, parameters, null, null);
	}
}