package com.examw.netplatform.service.admin.settings.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.settings.IChapterDao;
import com.examw.netplatform.dao.admin.settings.ISubjectDao;
import com.examw.netplatform.domain.admin.settings.Subject;
import com.examw.netplatform.domain.admin.settings.Chapter;
import com.examw.netplatform.model.admin.settings.ChapterInfo;
import com.examw.netplatform.service.admin.settings.IChapterService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;

/**
 * 科目章节服务。
 * @author fengwei.
 * @since 2014年4月30日 下午3:33:55.
 * 
 * 重构代码。
 * @author yangyong.
 * @since 2014-05-24.
 */
public class ChapterServiceImpl  extends BaseDataServiceImpl<Chapter, ChapterInfo> implements IChapterService {
	private IChapterDao chapterDao;
	private ISubjectDao subjectDao;
	/**
	 * 设置章节数据接口。
	 * @param chapterDao
	 * 章节数据接口。
	 */
	public void setChapterDao(IChapterDao chapterDao) {
		this.chapterDao = chapterDao;
	}
	/**
	 * 设置科目数据接口。
	 * @param subjectDao
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Chapter> find(ChapterInfo info) {
		return this.chapterDao.findChapters(info);
	}
	/*
	 * 数据类型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ChapterInfo changeModel(Chapter data) {
		if(data == null) return null;
		ChapterInfo info = new ChapterInfo();
		BeanUtils.copyProperties(data, info, new String[] {"children"});
		if(data.getSubject() != null){
			info.setSubjectId(data.getSubject().getId());
			info.setSubjectName(data.getSubject().getName());
			if(data.getSubject().getExam() != null){
				info.setExamId(data.getSubject().getExam().getId());
				if(data.getSubject().getExam().getCatalog() != null){
					info.setCatalogId(data.getSubject().getExam().getCatalog().getId());
				}
			}
		}
		if(data.getChildren() != null && data.getChildren().size() > 0){
			List<ChapterInfo> children = new ArrayList<>();
			for(Chapter child : data.getChildren()){
				ChapterInfo c = this.changeModel(child);
				if(c != null){
					c.setPid(info.getId());
					children.add(c);
				}
			}
			if(children.size() > 0){
				info.setChildren(children);
			}
		}
		return info;
	}
	/*
	 * 查询数据汇总。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ChapterInfo info) {
		return this.chapterDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ChapterInfo update(ChapterInfo info) {
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
		if(!StringUtils.isEmpty(info.getSubjectId()) && (data.getSubject() == null || !data.getSubject().getId().equalsIgnoreCase(info.getSubjectId()))){
			Subject subject = this.subjectDao.load(Subject.class, info.getSubjectId());
			if(subject != null) data.setSubject(subject);
		}
		if(data.getSubject() != null) info.setSubjectName(data.getSubject().getName());
		if(isAdded)this.chapterDao.save(data);
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
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
	 * 加载章节集合。
	 * @see com.examw.netplatform.service.admin.settings.IChapterService#loadChapters(java.lang.String)
	 */
	@Override
	public List<Chapter> loadChapters(String ignoreChapterId) {
		return this.chapterDao.loadChapters(ignoreChapterId);
	}
}