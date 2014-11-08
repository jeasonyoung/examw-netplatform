package com.examw.netplatform.service.admin.settings.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.model.TreeNode;
import com.examw.netplatform.dao.admin.settings.IAreaDao;
import com.examw.netplatform.dao.admin.settings.IChapterDao;
import com.examw.netplatform.dao.admin.settings.ISubjectDao;
import com.examw.netplatform.domain.admin.settings.Area;
import com.examw.netplatform.domain.admin.settings.Chapter;
import com.examw.netplatform.domain.admin.settings.Subject;
import com.examw.netplatform.model.admin.settings.ChapterInfo;
import com.examw.netplatform.service.admin.settings.IChapterService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;

/**
 * 科目章节服务。
 * @author fengwei.
 * @since 2014年4月30日 下午3:33:55.
 */
public class ChapterServiceImpl  extends BaseDataServiceImpl<Chapter, ChapterInfo> implements IChapterService {
	private static final Logger logger = Logger.getLogger(ChapterServiceImpl.class);
	private IChapterDao chapterDao;
	private ISubjectDao subjectDao;
	private IAreaDao areaDao;
	private Map<Integer, String> statusMap;
	/**
	 * 设置章节数据接口。
	 * @param chapterDao
	 * 章节数据接口。
	 */
	public void setChapterDao(IChapterDao chapterDao) {
		if(logger.isDebugEnabled()) logger.debug("注入章节数据接口...");
		this.chapterDao = chapterDao;
	}
	/**
	 * 设置科目数据接口。
	 * @param subjectDao
	 * 科目数据接口。
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		if(logger.isDebugEnabled()) logger.debug("注入科目数据接口...");
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置地区数据接口。
	 * @param areaDao 
	 *	  地区数据接口。
	 */
	public void setAreaDao(IAreaDao areaDao) {
		if(logger.isDebugEnabled()) logger.debug("注入地区数据接口...");
		this.areaDao = areaDao;
	}
	/**
	 * 设置状态值名称集合。
	 * @param statusMap 
	 *	  状态值名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("注入状态值名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态值名称。
	 * @see com.examw.netplatform.service.admin.settings.IChapterService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载状态值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Chapter> find(ChapterInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据..");
		return this.chapterDao.findTopChapters(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ChapterInfo changeModel(Chapter data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换...");
		return this.changeModel(data, false);
	}
	//数据模型转换。
	private ChapterInfo changeModel(Chapter data, boolean isAll){
		if(data == null) return null;
		ChapterInfo info = new ChapterInfo();
		BeanUtils.copyProperties(data, info, new String[] {"children"});
		if(data.getSubject() != null){//科目
			info.setSubjectId(data.getSubject().getId());
			info.setSubjectName(data.getSubject().getName());
			if(data.getSubject().getExam() != null){//考试
				info.setExamId(data.getSubject().getExam().getId());
				if(data.getSubject().getExam().getCategory() != null){//类别
					info.setCategoryId(data.getSubject().getExam().getCategory().getId());
				}
			}
		}
		if(data.getArea() != null){//地区
			info.setAreaId(data.getArea().getId());
			info.setAreaName(data.getArea().getName());
		}
		if(info.getStatus() != null){//状态
			info.setStatusName(this.loadStatusName(info.getStatus()));
		}
		if(isAll & data.getChildren() != null && data.getChildren().size() > 0){
			Set<ChapterInfo> children = new TreeSet<>();
			for(Chapter chapter : data.getChildren()){
				if(chapter == null) continue;
				ChapterInfo chapterInfo = this.changeModel(chapter, isAll);
				if(chapterInfo != null) children.add(chapterInfo);
			}
			if(children.size() > 0) info.setChildren(children);
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ChapterInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.chapterDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ChapterInfo update(ChapterInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		return this.changeModel(this.updateChapter(info));
	}
	//更新数据。
	private Chapter updateChapter(ChapterInfo info){
		if(info == null) return null;
		boolean isAdded = false;
		Chapter data = StringUtils.isEmpty(info.getId()) ? null : this.chapterDao.load(Chapter.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Chapter();
		}
		BeanUtils.copyProperties(info, data,new String[] {"children"});
		if(!StringUtils.isEmpty(info.getPid()) && (data.getParent() == null || !data.getParent().getId().equalsIgnoreCase(info.getPid()))){
			Chapter parent = this.chapterDao.load(Chapter.class, info.getPid());
			if(parent != null) data.setParent(parent);
		}
		data.setSubject(StringUtils.isEmpty(info.getSubjectId()) ?  null :  this.subjectDao.load(Subject.class, info.getSubjectId()));
		data.setArea(StringUtils.isEmpty(info.getAreaId()) ? null : this.areaDao.load(Area.class, info.getAreaId()));
		if(isAdded)this.chapterDao.save(data);
		return data;
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Chapter data = this.chapterDao.load(Chapter.class, ids[i]);
			if(data != null){
				this.chapterDao.delete(data);
			}
		}
	}
	/*
	 * 加载章节树。
	 * @see com.examw.netplatform.service.admin.settings.IChapterService#loadChapters(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public List<TreeNode> loadChapters(String parentChapterId,String ignoreChapterId, boolean isSelf) {
		if(logger.isDebugEnabled()) logger.debug("加载章节集合...");
		List<TreeNode> nodes = new ArrayList<>();
		List<Chapter> chapters = this.chapterDao.loadChapters(parentChapterId,isSelf);
		if(chapters != null && chapters.size() > 0){
			for(Chapter chapter : chapters){
				 TreeNode node = this.createChapterNode(chapter, ignoreChapterId);
				 if(node != null) nodes.add(node);
			}
		}
		return nodes;
	}
	//创建章节节点。
	private TreeNode createChapterNode(Chapter chapter, String ignoreChapterId){
		if(chapter == null || (!StringUtils.isEmpty(ignoreChapterId) && chapter.getId().equalsIgnoreCase(ignoreChapterId))) return null;
		TreeNode node = new TreeNode(chapter.getId(), chapter.getName());
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("id", chapter.getId());
		attributes.put("name", chapter.getName());
		attributes.put("description", chapter.getDescription());
		attributes.put("orderNo", chapter.getOrderNo());
		if(chapter.getParent() != null){
			attributes.put("pid", chapter.getParent().getId());
		}
		node.setAttributes(attributes);
		if(chapter.getChildren() != null && chapter.getChildren().size() > 0){
			List<TreeNode> childrenNodes = new ArrayList<>();
			for(Chapter child : chapter.getChildren()){
				if(child == null) continue;
				TreeNode e = this.createChapterNode(child, ignoreChapterId);
				if(e != null) childrenNodes.add(e);
			}
			if(childrenNodes.size() > 0) node.setChildren(childrenNodes);
		}
		return node;
	}
	/*
	 * 加载最大排序号。
	 * @see com.examw.netplatform.service.admin.settings.IChapterService#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String parentChapterId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载最大排序号［parentChapterId = %s］...", parentChapterId));
		return this.chapterDao.loadMaxOrder(parentChapterId);
	}
}