package com.examw.netplatform.service.admin.settings.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.netplatform.dao.admin.settings.ChapterMapper;
import com.examw.netplatform.dao.admin.settings.SubjectMapper;
import com.examw.netplatform.domain.admin.settings.Chapter;
import com.examw.netplatform.domain.admin.settings.Subject;
import com.examw.netplatform.model.admin.settings.ChapterInfo;
import com.examw.netplatform.service.admin.settings.IChapterService;

/**
 * 科目章节服务。
 * @author fengwei.
 * @since 2014年4月30日 下午3:33:55.
 */
public class ChapterServiceImpl  implements IChapterService {
	private static final Logger logger = Logger.getLogger(ChapterServiceImpl.class);
	private ChapterMapper chapterDao;
	private SubjectMapper subjectDao;
	private Map<Integer, String> statusMap;
	/**
	 * 设置章节数据接口。
	 * @param chapterDao
	 * 章节数据接口。
	 */
	public void setChapterDao(ChapterMapper chapterDao) {
		logger.debug("注入章节数据接口...");
		this.chapterDao = chapterDao;
	}
	/**
	 * 设置科目数据接口。
	 * @param subjectDao 
	 *	  科目数据接口。
	 */
	public void setSubjectDao(SubjectMapper subjectDao) {
		logger.debug("注入科目数据接口...");
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置状态值名称集合。
	 * @param statusMap 
	 *	  状态值名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		logger.debug("注入状态值名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态值名称。
	 * @see com.examw.netplatform.service.admin.settings.IChapterService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		logger.debug(String.format("加载状态值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 *  加载最大排序号。
	 * @see com.examw.netplatform.service.admin.settings.IChapterService#loadMaxOrder(java.lang.String, java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String subjectId, String pid) {
		logger.debug("加载最大排序号...");
		Integer max = this.chapterDao.loadMaxOrder(pid);
		if(max == null && StringUtils.isNotBlank(pid)){
			final Chapter data = this.chapterDao.getChapter(pid);
			if(data != null){
				max = data.getOrderNo() * 10;
			}
		}
		if(max == null && StringUtils.isNotBlank(subjectId)){
			max = this.chapterDao.loadMaxOrderBySubject(subjectId);
		}
		return max;
	}
	/*
	 * 查询科目下的所有章节数据。
	 * @see com.examw.netplatform.service.admin.settings.IChapterService#loadAllChapters(java.lang.String)
	 */
	@Override
	public List<ChapterInfo> loadAllChapters(String subjectId) {
		logger.debug("查询科目["+subjectId+"]下的所有章节数据...");
		return this.loadAllChapters(subjectId, null);
	}
	/*
	 * 查询科目下的所有章节数据。
	 * @see com.examw.netplatform.service.admin.settings.IChapterService#loadAllChapters(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ChapterInfo> loadAllChapters(String subjectId, String ignoreId) {
		logger.debug("查询科目["+subjectId+"]下的所有章节数据[忽略章节["+ignoreId+"]及其子孙]...");
		return this.changeModel(this.chapterDao.findChapters(subjectId, ignoreId));
	}
	//类型批量转换。
	private List<ChapterInfo> changeModel(List<Chapter> chapters){
		final List<ChapterInfo> list = new ArrayList<ChapterInfo>();
		if(chapters != null && chapters.size() > 0){
			for(Chapter chapter : chapters){
				if(chapter == null) continue;
				list.add(this.conversion(chapter));
			}
		}
		return list;
	}
	//类型转换
	private ChapterInfo conversion(Chapter data){
		if(data != null){
			final ChapterInfo info = new ChapterInfo();
			BeanUtils.copyProperties(data, info);
			info.setStatusName(this.loadStatusName(data.getStatus()));
			//科目
			if(StringUtils.isNotBlank(info.getSubjectId()) && StringUtils.isBlank(info.getSubjectName())){
				final Subject subject = this.subjectDao.getSubject(info.getSubjectId());
				if(subject != null) info.setSubjectName(subject.getName());
			}
			return info;
		}
		return null;
	}
	/*
	 *  更新数据。
	 * @see com.examw.netplatform.service.admin.settings.IChapterService#update(com.examw.netplatform.model.admin.settings.ChapterInfo)
	 */
	@Override
	public ChapterInfo update(ChapterInfo info) {
		logger.debug("更新数据....");
		if(info == null) return null;
		//检查数据
		if(StringUtils.isBlank(info.getPid())){
			if(StringUtils.isBlank(info.getSubjectId())) throw new RuntimeException("所属科目为空!");
			if(this.subjectDao.getSubject(info.getSubjectId()) == null) throw new RuntimeException("所属科目["+info.getSubjectId()+"]不存在!");
		}
		
		Chapter data = StringUtils.isBlank(info.getId()) ? null : this.chapterDao.getChapter(info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			if(StringUtils.isBlank(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new Chapter();
		}
		//赋值
		BeanUtils.copyProperties(info, data);
		//判断
		if(StringUtils.isNotBlank(data.getPid())){
		    final	 Chapter parent = this.chapterDao.getChapter(data.getPid());
		    if(parent == null) throw new RuntimeException("上级章节ID["+data.getPid()+"]不存在！");
		    if(StringUtils.equalsIgnoreCase(parent.getId(), data.getId())){
		    	throw new RuntimeException("自己不能是自己的上级节点！");
		    }
		    //清空所属科目
		    data.setSubjectId(null);
		}
		//保存数据
		if(isAdded){
			logger.debug("新增章节...");
			this.chapterDao.insertChapter(data);
		}else {
			logger.debug("更新章节...");
			this.chapterDao.updateChapter(data);
		}
		//返回
		return this.conversion(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.admin.settings.IChapterService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除数据..." + StringUtils.join(ids,","));
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id)) continue;
				this.chapterDao.deleteChapter(id);
			}
		}
	}
}