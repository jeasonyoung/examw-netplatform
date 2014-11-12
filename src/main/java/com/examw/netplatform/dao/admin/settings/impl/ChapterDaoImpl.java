package com.examw.netplatform.dao.admin.settings.impl;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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
	private static final Logger logger = Logger.getLogger(ChapterDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.settings.IChapterDao#findTopChapters(com.examw.netplatform.model.admin.settings.ChapterInfo)
	 */
	@Override
	public List<Chapter> findTopChapters(ChapterInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from Chapter c where (c.parent is null) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(hql, info, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += " order by c." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	//添加查询条件。
	private String addWhere(String hql,ChapterInfo info,Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getCategoryId())){//考试类别；
			hql += " and (c.subject.exam.category.id = :categoryId) ";
			parameters.put("categoryId", info.getCategoryId());
		}
		if(!StringUtils.isEmpty(info.getExamId())){//考试；
			hql += " and (c.subject.exam.id = :examId) ";
			parameters.put("examId", info.getExamId());
		}
		if(!StringUtils.isEmpty(info.getSubjectId())){//科目；
			hql += " and (c.subject.id = :subjectId) ";
			parameters.put("subjectId", info.getSubjectId());
		}
		if(!StringUtils.isEmpty(info.getAreaId())){//地区
			hql += " and ((c.area is null) or (c.area.code = 1) or (c.area.id = :areaId))";
			parameters.put("areaId", info.getAreaId());
		}
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and (c.name like :name) ";
			parameters.put("name", "%"+ info.getName() +"%");
		}
		return hql;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.settings.IChapterDao#total(com.examw.netplatform.model.admin.settings.ChapterInfo)
	 */
	@Override
	public Long total(ChapterInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from Chapter c where (c.parent is null) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(hql, info, parameters);
		return this.count(hql, parameters);
	}
	/*
	 * 加载最大的章节排序号。
	 * @see com.examw.netplatform.dao.admin.settings.IChapterDao#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String parentChapterId) {
		if(logger.isDebugEnabled()) logger.debug("加载最大的章节排序号...");
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("select max(c.orderNo) from Chapter c where ")
        .append(StringUtils.isEmpty(parentChapterId) ?  " (c.parent is null) " : " (c.parent.id = :pid) ");
		Map<String, Object> parameters = new HashMap<>();
		if(!StringUtils.isEmpty(parentChapterId)){
			parameters.put("pid",  parentChapterId);
		}
		Object obj = this.uniqueResult(hqlBuilder.toString(), parameters);
		if(obj == null && !StringUtils.isEmpty(parentChapterId)){
			Chapter chapter = this.load(Chapter.class, parentChapterId);
			if(chapter == null) return null;
			return new Integer(String.format("%d0", chapter.getOrderNo()));
		}
		return obj == null ? null :  (int)obj;
	}
	/*
	 * 加载章节集合。
	 * @see com.examw.netplatform.dao.admin.settings.IChapterDao#loadChapters(java.lang.String, boolean)
	 */
	@Override
	public List<Chapter> loadChapters(String parentChapterId,boolean isSelf) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载［parentChapterId = ％s］章节集合...", parentChapterId));
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("from Chapter c where ")
		.append(StringUtils.isEmpty(parentChapterId) ?  " (c.parent is null) " : (isSelf ? " (c.id = :pid) " : " (c.parent.id = :pid)"));
		hqlBuilder.append(" order by c.orderNo asc ");
		Map<String, Object> parameters = new HashMap<>();
		if(!StringUtils.isEmpty(parentChapterId)){
			parameters.put("pid", parentChapterId);
		}
		return this.find(hqlBuilder.toString(), parameters, null, null);
	}
	/*
	 * 重载删除数据。
	 * @see com.examw.netplatform.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Chapter data) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(data == null) return;
		int count = 0;
		if(data.getLessons() != null && (count = data.getLessons().size()) > 0){
			throw new RuntimeException(String.format("章节［%1$s］下关联［%2$d］课时资源，暂不能删除！", data.getName(), count));
		}
		if(data.getChildren() != null && data.getChildren().size() > 0){
			for(Chapter chapter : data.getChildren()){
				if(chapter == null) continue;
				this.delete(chapter);
			}
		}
		super.delete(data);
	}
}